package twitter.location.program;

import java.util.Set;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DatasetAnalyzer {
	private SocialNetwork network;

	public DatasetAnalyzer(SocialNetwork network) {
		this.network=network;
	}
	public boolean printResult(String file) {
		BufferedWriter w;
		NumberFormat formatter = new DecimalFormat("#0.00");
		try {
			w = new BufferedWriter(new FileWriter(file));
			w.write("Total number of users: " + getSize());
			w.newLine();
			w.write("Number of users with location unknown: " + getUnknownCount());
			w.newLine();
			w.write("Number of users with location unknown and no friends: " + getUnknownIsolatedCount());
			w.newLine();
			w.write("Baseline accuracy (predicting all unknown locations as (0,0)): " + formatter.format(getBaseAccuracy()));
			w.newLine();
			w.write("Upper bound accuracy 1 (assume that we can know all the users locations if they have friends: " + formatter.format(getMaxAccuracy1()));
			w.newLine();
			w.write("Upper bound accuracy 2 (assume that we can know all the users locations if they have friends sharing locations: " + formatter.format(getMaxAccuracy2()));
			w.flush();
			w.close();
		} catch (IOException e) {
			System.err.println("DatasetAnalyzer printResult(" + file
					+ ") IO error: " + e.getLocalizedMessage());
			return false;
		}
		System.out.println("Output file: " + file);
		return true;
	}

	private long getSize() {
		return network.getSize();
	}

	private long getUnknownCount() {
		return network.getNotShareSize();
	}

	private long getUnknownIsolatedCount() {
		return network.getSizeIsolated();
	}

	private double getBaseAccuracy() {
		return 1-(double)network.getNotShareSize()/(double)network.getSize();
	}

	private double getMaxAccuracy1() {
		return 1-(double)network.getSizeIsolated()/(double)network.getSize();
	}

	private double getMaxAccuracy2() {

		return 1-(double)network.getSizeNoShareFriends()/(double)network.getSize();
	}
}
