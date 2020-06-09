package twitter.location.program;

public class data_analysis {
    private data_analysis() {}
    private static data_analysis instance = null;
    
    public static synchronized data_analysis getInstance()
    {
	if (instance == null) { instance = new data_analysis(); }
	return instance;
    }
    
    public DatasetRetriever GetDatasetReader(String homesFilePath, String edgesFilePath)
    {
	return new DatasetRetriever(edgesFilePath, homesFilePath);
    }
    
    public DatasetAnalyzer GetDatasetAnalyzer(SocialNetwork network)
    {
	return new DatasetAnalyzer(network);
    }
}
