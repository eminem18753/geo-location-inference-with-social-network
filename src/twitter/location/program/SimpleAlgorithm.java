package twitter.location.program;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
public class SimpleAlgorithm extends InferenceAlgorithm
{
    public SimpleAlgorithm(SocialNetwork network) { super(network); }
    
    public Inference_GPS_Point inferHomeLocation(long userId)
    {
	User userToInfer=this.getNetwork().getNodeById(userId);
	double latitude=0;
	double longitude=0;
	double count=0;
	if(userToInfer.isHomeLocationKnown())
	{
		GPSPoint inferredGPS=userToInfer.getHomeLocation();
		Inference_GPS_Point result=new Inference_GPS_Point(inferredGPS);
		return result;
	}	
	else
	{
		Set<User> friends=userToInfer.getFriends();
		Set<User> friendsToShare=new HashSet<>();
		for(Iterator<User> it=friends.iterator();it.hasNext();)
		{
			User userNow=it.next();
			if(userNow.isHomeLocationKnown())
			{
				friendsToShare.add(userNow);
			}
		}
		for(Iterator<User> it=friendsToShare.iterator();it.hasNext();)
		{
			User userTemp=it.next();
			GPSPoint friendGPS=userTemp.getHomeLocation();
			latitude=latitude+friendGPS.latitude;
			longitude=longitude+friendGPS.longitude;
			count++;
		}
		latitude=latitude/count;
		longitude=longitude/count;
		GPSPoint inferredLocation=new GPSPoint(latitude,longitude);
		Inference_GPS_Point finalResults=new Inference_GPS_Point(inferredLocation);
		return finalResults;
	}
    }
}
