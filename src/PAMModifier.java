import java.util.ArrayList;
import java.util.Random;

public class PAMModifier extends Modifier {

    public PAMModifier(KNearestNeighborModel model) {
        super(model);
        this.description = "Partitioning Around Medoids K-Nearest Neighbor Algorithm";
    }

    @Override
    void modifyTrainingSet(int numNeighbors) {
    	//number of clusters just for testing
    	int k=4;
    	
    	boolean change=true;
    	ArrayList<Example> PAMTrainingSet= new ArrayList(model.trainingSet);
    	
    	ArrayList<Example> points= new ArrayList();
    	ArrayList<Example> finalCentroids= new ArrayList();
    	
    	int i=0;
    	double distance=0;
    	
    	
    	//Choose m1...mk randomly
    	while(i<k) {
    		Random number=new Random();
    		int p=number.nextInt(PAMTrainingSet.size());
    		//check if the object if already on the points list
    		if(!points.contains(PAMTrainingSet.get(p))) {
    			points.add(PAMTrainingSet.get(p));
    			i++;
    		}
    	}
    	
    	
    	while(change) {
    		change=false;
    		double cost=0;
    		//make an arraylist of arraylists of length k(to store which centroid each example is assigned to)
    		ArrayList<ArrayList<Example>> cluster = new ArrayList<ArrayList<Example>>();
    		//construct initial lists
    		for(int j = 0; j < k; j++) {
    			cluster.add(new ArrayList<Example>());
    		}
    		
    		//Relate examples to their nearest cluster
    		for(Example e: PAMTrainingSet) {
    			double minDistance=Double.MAX_VALUE;
    			int minCluster=0;
    			
    			for(int j=0; j<k; j++) {
    				e.distanceFrom(points.get(j));
    				double temp=e.distance;
    				
    				if(temp<minDistance) {
    					minDistance=temp;
    					minCluster=j;
    				}
    				
    			}
    			cluster.get(minCluster).add(e);
    			
    			//Get total distortion of the k-medoids
    			cost=cost+minDistance;
    		}
    		
    		//temporary Example
    		double newCost=0;
    		Example temp=new Example(0.0, null);
    		for(int j=0; j<k; j++) {
    			for(Example e: PAMTrainingSet) {
    				
    				//While xj is not equal mk
    				if(points.get(j)!=e) {
    					temp=e;
    					e=points.get(j);
    					points.set(j, temp);
    					change=true;
    					newCost=getCost(points, PAMTrainingSet, k);
    					
    					//If newCost if higher than the old cost swap back
    					if(cost<=newCost) {
    						temp=e;
        					e=points.get(j);
        					points.set(j, temp);
        					change=false;
    					}
    				}
    			}
    		}
    	}
    	model.trainingSet = new ArrayList<Example>(points);
    }

    //Return the total cost of the distortion of k-medoids
    double getCost(ArrayList<Example> points, ArrayList<Example> PAMTrainingSet, int k) {
    	double cost=0;
    	for(Example e: PAMTrainingSet) {
			double distance=Double.MAX_VALUE;
			int minCluster=0;
			
			for(int j=0; j<k; j++) {
				e.distanceFrom(points.get(j));
				distance=e.distance;
				
			}
			//Get total distortion of the k-medoids
			cost=cost+distance;
		}
    	return cost;
    }
    
    @Override
    boolean supportsRegression() {
        return true;
    }
    
}