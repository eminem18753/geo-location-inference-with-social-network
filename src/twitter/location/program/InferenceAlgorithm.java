package twitter.location.program;

public abstract class InferenceAlgorithm
{
    private SocialNetwork network = null;
    
    public InferenceAlgorithm(SocialNetwork network) { this.network = network; }

    public SocialNetwork getNetwork()
    {
	return network;
    }

    public abstract Inference_GPS_Point inferHomeLocation(long userId);
}
