package twitter.location.program.test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twitter.location.program.*;

public class location_analysis_tests
{
    private location_analysis location_analyzer = null;
    
    private DatasetRetriever reader1 = null;
    private DatasetRetriever reader2 = null;
    
    @Before
    public void setUp() throws Exception
    {
	location_analyzer = location_analysis.getInstance();
	if(location_analyzer != null)
	{
	    reader1 = location_analyzer.GetDatasetReader("./dataset1/homes.txt", "./dataset1/friends.txt");
	    if(reader1 != null) { assertTrue("Failed to read dataset1", reader1.read()); }
	    reader2 = location_analyzer.GetDatasetReader("./dataset2/homes.txt", "./dataset2/friends.txt");
	    if(reader2 != null) { assertTrue("Failed to read dataset2", reader2.read()); }
	}
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testGetInstance()
    {
	assertNotNull(location_analyzer);
    }

    @Test
    public void testGetDataset1Reader()
    {
	assertNotNull(reader1);
    }
    
    @Test
    public void testGetDataset2Reader()
    {
	assertNotNull(reader2);
    }

    @Test
    public void testDatasetReader()
    {
	assertNotNull(reader1);
	assertTrue(reader1.read());
	
	GroundTruth gt1 = reader1.getGroundTruth();
	assertNotNull(gt1);
	SocialNetwork network1 = reader1.getSocialNetwork();
	assertNotNull(network1);
	
	assertNotNull(reader2);
	assertTrue(reader2.read());
	
	GroundTruth gt2 = reader2.getGroundTruth();
	assertNotNull(gt2);
	SocialNetwork network2 = reader2.getSocialNetwork();
	assertNotNull(network2);
    }


    @Test
    public void testGetSimpleInferenceAlgorithm()
    {
	assertNotNull(reader1);
	SocialNetwork network1 = reader1.getSocialNetwork();
	assertNotNull(network1);
	
	InferenceAlgorithm simpleAlgo = location_analyzer.GetSimpleInferenceAlgorithm(network1);
	assertNotNull(simpleAlgo);
	assertTrue(simpleAlgo instanceof SimpleAlgorithm);
    }

    @Test
    public void testGetPart2InferenceAlgorithm()
    {
	assertNotNull(reader2);
	SocialNetwork network2 = reader2.getSocialNetwork();
	assertNotNull(network2);
	
	InferenceAlgorithm part2Algo = location_analyzer.GetPart2InferenceAlgorithm(network2);
	assertNotNull(part2Algo);
	assertFalse(part2Algo instanceof SimpleAlgorithm);
    }
    
    @Test
    public void testSocialNetwork()
    {
	assertNotNull(reader1);
	SocialNetwork network1 = reader1.getSocialNetwork();
	assertNotNull(network1);
	
	assertNotNull(network1.getNodes());
	long nodes = 0;
	if (network1.getNodes() instanceof Collection<?>) 
	{  nodes = ((Collection<?>)network1.getNodes()).size(); }
	else { for(User node : network1.getNodes()) { nodes++; } }
	assertEquals(network1.getSize(), nodes);
	assertEquals(network1.getSize(), 107092);
	
	User user1 = network1.getNodeById(1);
	assertNotNull(user1);
	assertTrue(user1.isHomeLocationKnown());
	
	User user3 = network1.getNodeById(3);
	assertNotNull(user3);
	assertFalse(user3.isHomeLocationKnown());
	
	assertEquals(user1.getFriends().size(), 4); // friendships are undirected!!
	
	assertFalse(user1.getFriends().contains(user1)); 
	
	User user43344 = network1.getNodeById(43344);
	assertTrue(user1.getFriends().contains(user43344)); // 1 and 43344 are friends!!
	assertTrue(user43344.getFriends().contains(user1)); 
    }
    
    @Test
    public void testGroundTruth()
    {
	assertNotNull(reader1);
	GroundTruth gt1 = reader1.getGroundTruth();
	assertNotNull(gt1);
	
	GPSPoint home1 = gt1.getHomeLocation(1);
	assertNotNull(home1);
	
	assertTrue(home1.latitude >= 21.9779 && home1.latitude <= 21.9780);
	assertTrue(home1.longitude <= -159.3494 && home1.longitude >= -159.3495);
	
	GPSPoint home43344 = gt1.getHomeLocation(43344);
	assertNotNull(home43344);
	
	double dist = home1.distanceTo(home43344);
	assertTrue(dist >= 4246.5 && dist <= 4247.0);
    }
    
    @Test
    public void testWithin25KmAccuracy()
    {
	assertNotNull(reader1);
	
	SocialNetwork network1 = reader1.getSocialNetwork();
	assertNotNull(network1);
	
	GroundTruth gt1 = reader1.getGroundTruth();
	assertNotNull(gt1);
	
	InferenceAlgorithm simpleAlgo = location_analyzer.GetSimpleInferenceAlgorithm(network1);
	assertNotNull(simpleAlgo);
	assertTrue(simpleAlgo instanceof SimpleAlgorithm);
	
	Evaluator evaluator = Evaluator.getInstance();
	assertNotNull(evaluator);
	
	Metric within25KmAccuracyMetric = new certain_distance_accuracy(25.0);
	double normalizedError = evaluator.EvaluateInference(simpleAlgo, gt1, within25KmAccuracyMetric);
	double averageAccuracy = 1.0 - normalizedError;
	
	assertTrue(averageAccuracy > 0 && averageAccuracy < 1.0);
	
	System.out.println("Original within 25Km Accuracy: " + averageAccuracy * 100.0 + "%");
    }

    @Test (expected = Exception.class)
    public void testNoGroundTruthDataset2()
    {
	assertNotNull(reader2);
	
	GroundTruth gt2 = reader2.getGroundTruth();
	assertNotNull(gt2);
	
	GPSPoint home1 = gt2.getHomeLocation(1);
	assertNull(home1);
	assertNotNull(home1);
	
    }
}
