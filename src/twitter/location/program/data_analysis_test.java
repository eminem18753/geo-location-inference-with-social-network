package twitter.location.program;

public class data_analysis_test {
	public static void main(String[] args) {
		data_analysis instance = data_analysis.getInstance();

		String basePath="./";

		String homesFilePath = basePath + "dataset1/homes.txt";
		String edgesFilePath = basePath + "dataset1/friends.txt";
		String outFilePath = basePath + "test_results.txt";

		DatasetRetriever reader = instance.GetDatasetReader(homesFilePath,
				edgesFilePath);
		boolean ok = reader.read();

		if (!ok) {
			System.out.println("Failed to read dataset1, exiting.");
			return;
		}

		SocialNetwork network = reader.getSocialNetwork();
		GroundTruth gt = reader.getGroundTruth();

		if (network == null || gt == null) {
			return;
		}
		
		DatasetAnalyzer analyzer = instance.GetDatasetAnalyzer(network);
		analyzer.printResult(outFilePath);

	}
}
