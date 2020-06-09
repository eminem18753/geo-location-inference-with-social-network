package twitter.location.program;

public final class location_analysis
{
    private location_analysis() {}
    private static location_analysis instance = null;
    
    public static synchronized location_analysis getInstance()
    {
	if (instance == null) { instance = new location_analysis(); }
	return instance;
    }
    
    public DatasetRetriever GetDatasetReader(String homesFilePath, String edgesFilePath)
    {
	return new DatasetRetriever(edgesFilePath, homesFilePath);
    }
    
    public InferenceAlgorithm GetSimpleInferenceAlgorithm(SocialNetwork network)
    {
	return new SimpleAlgorithm(network);
    }
    
    public InferenceAlgorithm GetPart2InferenceAlgorithm(SocialNetwork network)
    {
	return new designedInferenceAlgorithm(network);
    }
}
