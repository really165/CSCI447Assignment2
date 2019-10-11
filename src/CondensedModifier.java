import java.util.ArrayList;
import java.util.Collections;

public class CondensedModifier extends Modifier {

    public CondensedModifier(KNearestNeighborModel model) {
        super(model);
        this.description = "Condensed K-Nearest Neighbor Algorithm";
    }

    @Override
    void modifyTrainingSet(int numNeighbors) {
		// start with empty training set
		ArrayList<Example> condensedTrainingSet = new ArrayList<Example>();

		Example min = null;
		double distance = Double.MAX_VALUE;
    	boolean change = true;
		
		Collections.shuffle(model.trainingSet);
		// add random example
		condensedTrainingSet.add(model.trainingSet.get(model.trainingSet.size()-1));

		int iterations = 0;

		System.out.println("condensing training set...");
        System.out.println("\tinitial size: " + model.trainingSet.size());

		// repeat until no change
    	while (change && iterations < 100) {
			change = false;

			// iterate of training set
    		for (Example e : model.trainingSet) {
				distance = Double.MAX_VALUE;
				// find the closest point in our condensed set
    			for (Example x: condensedTrainingSet) {
					// handle if equal here
					x.distanceFrom(e);
					if (x.distance < distance) {
						distance = x.distance;
						min = x;
					}
				}

				// if they aren't the same class add the example to our condensed set
				if (min.c != e.c) {
					condensedTrainingSet.add(e);
					change = true;
				}
			}

			Collections.shuffle(model.trainingSet);
			iterations++;
		}

		System.out.println("\tfinal size: " + condensedTrainingSet.size());
        System.out.println("\tnumber of iterations: " + iterations);
		
		// change the training set to the condensed set
		model.trainingSet = condensedTrainingSet;
    }

    @Override
    boolean supportsRegression() {
        return false;
    }
    
}
