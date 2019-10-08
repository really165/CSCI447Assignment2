import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class KMeansModifier extends Modifier {

    public KMeansModifier(KNearestNeighborModel model) {
        super(model);
        this.description = "K-Means K-Nearest Neighbor Algorithm";
    }

    @Override
    void modifyTrainingSet(int numNeighbors) {
    	
        //copy original dataset
    	//ArrayList<Example> editedTrainingSet = new ArrayList(model.trainingSet);
    	
    	//get dataset info
    	Example sample = model.trainingSet.get(0);
    	int[] dataTypes = sample.dataType;
    	double[] min = sample.min;
    	double[] max = sample.max;
    	int numberOfAttributes = sample.data.length;
    	//get k(number of clusters)
    	int k = 4;
    	int iterations = 100;
    	
    	//make k centroids randomly
    	//declare an arrayList of centroids(2d to store examples assigned to them)
    	ArrayList<Example> centroids = new ArrayList<Example>();
    	//for k times:
    	for(int i = 0; i < k; i++) {
    		//array to store initial centroid data
    		double[] centroidData = new double[numberOfAttributes];
    		//for the length of the data array(to fill centroid Example with random data)
    		for(int j = 0; j < numberOfAttributes; j++) {
    			//if categorical
    			if(dataTypes[j] == 0) {
    				//get random number between 0 and 1 and store it in the centroid data array
    				centroidData[j] = Math.random();
    			}
    			//if numerical
    			else {
    				//get min and max of the column
    				double minValue = min[j];
    				double maxValue = max[j];
    				//get a random number between the min and max
    				Random random = new Random();
    				double centroidValue = minValue + (maxValue - minValue) * random.nextDouble();
    				//store the number in the centroid data array
    				centroidData[j] = centroidValue;
    			}
    		}
    		//make an Example using the new attribute array
			Example newCentroid = new Example(sample.c, centroidData, dataTypes, min, max);
			//add the new centroid to the centroid arraylist
			centroids.add(newCentroid);
    	}
    	//sort to compare later
    	Collections.sort(centroids);
    	//while centroids have changed or i < iterations
    	boolean centroidsHaveChanged = true;
    	int i = 0;
    	while(centroidsHaveChanged) {
    		//limit to how many times centroids can be updated 
    		if(i > iterations) {
    			break;
    		}
    		//make an arraylist of arraylists of length k(to store which centroid each example is assigned to)
    		ArrayList<ArrayList<Example>> centroidExamples = new ArrayList<ArrayList<Example>>();
    		//construct initial lists
    		for(int j = 0; j < k; j++) {
    			centroidExamples.add(new ArrayList<Example>());
    		}
    		
    		//for each example in the training set
    		for (Example example : model.trainingSet) {
    			//keep track of centroid with min distance(int position in arraylist of arraylists)
    			int minCentroid = 0;
    			//keep track of min distance
    			double currentMin = Double.MAX_VALUE;
    			//for each centroid(0 to k)
    			for(int j = 0; j < centroids.size(); j++) {
    				//get distance between centroid and example
    				example.distanceFrom(centroids.get(j));
    				double newMin = example.distance;
    				//if less than current min distance
    				if(newMin < currentMin) {
    					//centroid number = index(the 0 to k number)
    					minCentroid = j;
    					//min distance = distance between centroid and example
    					currentMin = newMin;
    				}
    			}
    			//add example to arraylist of arraylists at centroid number index
    			centroidExamples.get(minCentroid).add(example);
    		}
    		
    		//make new arrayList of examples to hold final centroids
    		ArrayList<Example> finalCentroids = new ArrayList<Example>();
    		//get the number of Examples
    		double numberOfExamples = (double)model.trainingSet.size();
    		//for each centroid
    		for(int j = 0; j < k; j++) {
    			//keep track of sum of the class doubles
    			double classTotal = 0;
    			//make array of sum of the example attributes, each started at 0
    			double[] averageAttributes = new double[numberOfAttributes];
    			Arrays.fill(averageAttributes, 0);
    			//get the arraylist of examples assigned to current centroid
    			ArrayList<Example> currentCluster = centroidExamples.get(j);
    			//get total number of examples assigned to centroid
    			double clusterSize = currentCluster.size();
    			//for each example assigned to that centroid
    			while(!currentCluster.isEmpty()) {
    				//get the current example
    				Example currentExample = currentCluster.remove(0);
    				//add class double to total
    				classTotal += currentExample.c;
    				//for length of attribute array(for each attribute)
    				for(int m = 0; m < numberOfAttributes; m++) {
    					//add to attribute total
    					averageAttributes[m] += currentExample.data[m];
    				}
    			}
    			//calculate average class and round the double
    			double averageClass = Math.round((classTotal/clusterSize));
    			//calculate averages for each attribute
    			for(int m = 0; m < numberOfAttributes; m++) {
    				averageAttributes[m] = averageAttributes[m]/clusterSize;
    			}
    			//add average centroid to the final centroid list
    			if(clusterSize != 0) {
    				finalCentroids.add(new Example(averageClass, averageAttributes, dataTypes, min, max));
    			}
    		}
    		
    		//compare arraylist of original centroids to arraylist of final centroids
    		Collections.sort(finalCentroids);
    		
    		//if the arraylists are the same or there have been enough iterations
    		if(centroids.equals(finalCentroids) || i+1==iterations) {
    			centroidsHaveChanged = false;
    			//update the old centroid list
    			centroids = new ArrayList<Example>(finalCentroids);
    			for(int p = 0; p < 4; p++) {
    				centroids.addAll(centroids);
    			}
    			//increment i
    			i++;
    		}
    		//if the arraylists are the same
    		else {
    			//update old centroid list
    			centroids = new ArrayList<Example>(finalCentroids);
    			//increment i
    			i++;
    		}
    	}
    	
    	//update the training set to have the k centroids
    	model.trainingSet = new ArrayList<Example>(centroids);
    }

    @Override
    boolean supportsRegression() {
        return true;
    }
    
}
