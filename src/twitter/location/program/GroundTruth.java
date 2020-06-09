package twitter.location.program;

import java.util.Map;
import java.util.HashMap;
import java.security.InvalidParameterException;

public class GroundTruth
{
    private Map<Long, GPSPoint> homes;
    private long size;

    public GroundTruth()
    {
	homes=new HashMap<Long,GPSPoint>();
    }
    public void setSize(long size)
    {
	this.size=size;
    }
    public long getSize()
    {
	return size;
    }
    public GPSPoint getHomeLocation(long userId)
    {
        GPSPoint homeLocationById;
	if((homeLocationById=homes.get(userId))!=null)
	{
		return homeLocationById;
	}
       else
	{
		throw new InvalidParameterException("Ground truth not available!");
	}
    }

    public void setHomeLocation(long userId, GPSPoint home)
    {
	homes.put(userId,home);
    }
}
