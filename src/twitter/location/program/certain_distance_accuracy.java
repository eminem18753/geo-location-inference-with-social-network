package twitter.location.program;

import java.security.InvalidParameterException;

public class certain_distance_accuracy extends Metric
{
    private double threshold;
    
    public certain_distance_accuracy(double threshold) { this.threshold = threshold; }
    
    // Returns 0.0 if the prediction is within 'threshold' km of the actual location, 1.0 otherwise.
    public double distance(Inference_GPS_Point inferred, GPSPoint actual)
    {
	if(actual == null) { throw new InvalidParameterException("actual location is null!"); }
	if(inferred == null) { throw new InvalidParameterException("inferred location is null!"); }
	
	double dist = actual.distanceTo(inferred);

	return (dist <= threshold) ? 0.0 : 1.0;
    }
}
