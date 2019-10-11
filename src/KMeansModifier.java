import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class KMeansModifier extends Modifier {

	public int k;

    public KMeansModifier(KNearestNeighborModel model, int k) {
		super(model);
		this.k = k;
        this.description = "K-Means K-Nearest Neighbor Algorithm";
	}

    @Override
    void modifyTrainingSet(int numNeighbors) {
		ArrayList<Example> trainingSet = new ArrayList<Example>(model.trainingSet);
		Collections.shuffle(trainingSet);

		// array to hold centroids
		Example[] centroids = new Example[k];
		
		// start with centroids as random points
		for (int i = 0; i < centroids.length; i++) {
			Example point = trainingSet.get(i);
			Example centroid = new Example(0, point.data, point.dataType, point.min, point.max);
			centroids[i] = centroid;
		}

		// hold sums and counts
		double[][] sums = new double[k][centroids[0].data.length];
		int[] counts = new int[k];
		int[][] classes = new int[k][model.numClasses];
		double[] means = new double[k];

		boolean change = true;
		int iterations = 0;

		System.out.println("creating " + k + " centroids...");

		// repeat until no change
		while (change && iterations < 100) {
			change = false;
			double min;
			int c;
			// iterate over training set
			for (Example e : trainingSet) {
				min = Double.MAX_VALUE;
				c = -1;
				// find centroid closest to the current example
				for (int i = 0; i < centroids.length; i++) {
					e.distanceFrom(centroids[i]);
					if (e.distance < min) {
						min = e.distance;
						c = i;
					}
				}
				// add the data from the example to that centroids counts and means
				counts[c]++;
				if (model.isClassification()) classes[c][(int)e.c]++;
				else means[c] += e.c;
				
				for (int j = 0; j < sums[c].length; j++) {
					sums[c][j] += e.data[j];
				}
			}

			// iterate over the centroids and recalculate the means and class/regression value
			for (int i = 0; i < centroids.length; i++) {
				double[] current = centroids[i].data;
				double[] mean = sums[i];
				int count = counts[i];
				for (int j = 0; j < mean.length; j++) mean[j] = mean[j]/count;
				if (!change) {
					for (int j = 0; j < mean.length; j++) {
						if (Math.abs(current[j] - mean[j]) > 0.000001) {
							change = true;
							break;
						}
					}
				}
				centroids[i].data = mean;
				int cl = -1;
				int num = 0;
				if (model.isClassification()) {
					for (int j = 0; j < classes[i].length; j++) {
						if (classes[i][j] > num) {
							cl = j;
							num = classes[i][j];
						}
					}
					centroids[i].c = cl;
				}
				else {
					for (int j = 0; j < centroids.length; j++) {
						centroids[j].c = means[j]/counts[j];
					}
				}
			}
			iterations++;
		}

		System.out.println("\tnumber of iterations: " + iterations);

		model.trainingSet = new ArrayList<Example>(Arrays.asList(centroids));
    }

    @Override
    boolean supportsRegression() {
        return true;
    }
    
}
