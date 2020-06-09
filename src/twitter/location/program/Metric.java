package twitter.location.program;

public abstract class Metric
{
    public abstract double distance(Inference_GPS_Point inferred, GPSPoint actual);
}
