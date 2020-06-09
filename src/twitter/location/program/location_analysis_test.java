package twitter.location.program;

public class location_analysis_test
{
    public static void main(String[] args)
    {
	location_analysis instance = location_analysis.getInstance();
	
	String basePath = "./";
	
	String homesFilePath = basePath + "dataset1/homes.txt"; 
	String edgesFilePath = basePath + "dataset1/friends.txt";
	
	DatasetRetriever reader = instance.GetDatasetReader(homesFilePath, edgesFilePath);
	boolean ok = reader.read();
	
	if(!ok) 
	{
	    System.out.println("Failed to read dataset1, exiting.");
	    return; 
	}
	
	SocialNetwork network = reader.getSocialNetwork();
	GroundTruth gt = reader.getGroundTruth();
	
	if(network == null || gt == null) { return; }
	
	Evaluator evaluator = Evaluator.getInstance();
	Metric metric = new certain_distance_accuracy(25.0);
	
	//InferenceAlgorithm simpleAlgo = instance.GetSimpleInferenceAlgorithm(network);
	InferenceAlgorithm best_algorithm = instance.GetPart2InferenceAlgorithm(network);
	double bestAvgAccuracy = 1.0 - evaluator.EvaluateInference(best_algorithm, gt, metric);
	
	System.out.println("Best mean accuracy: " + bestAvgAccuracy*100.0+"%");
	
	//add
	/*
	Visualizer seeMap=Visualizer.getInstance();
	seeMap.drawInferenceMap("inference.html",network.getNodes(),network.getGroundTruth(),simpleAlgo);
	*/
	//add
    }
}
