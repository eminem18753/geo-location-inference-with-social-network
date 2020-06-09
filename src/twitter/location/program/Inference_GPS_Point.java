package twitter.location.program;

public class Inference_GPS_Point extends GPSPoint
{
    public Inference_GPS_Point(GPSPoint homeLocation)
    {
	super(homeLocation);
    }

    public Inference_GPS_Point(double latitude, double longitude)
    {
	super(latitude, longitude);
    }
}
