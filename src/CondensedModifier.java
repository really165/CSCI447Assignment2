import java.util.ArrayList;
import java.util.Collections;

public class CondensedModifier extends Modifier {

    public CondensedModifier(KNearestNeighborModel model) {
        super(model);
        this.description = "Condensed K-Nearest Neighbor Algorithm";
    }

    @Override
    void modifyTrainingSet(int numNeighbors) {
		ArrayList<Example> condensedTrainingSet = new ArrayList<Example>();

		Example min = null;
		double distance = Double.MAX_VALUE;
    	boolean change = true;
		
		Collections.shuffle(model.trainingSet);
		condensedTrainingSet.add(model.trainingSet.get(model.trainingSet.size()-1));

		int iterations = 0;

		System.out.println("condensing training set...");
        System.out.println("\tinitial size: " + model.trainingSet.size());

    	while (change && iterations < 100) {
			change = false;

    		for (Example e : model.trainingSet) {
				distance = Double.MAX_VALUE;

    			for (Example x: condensedTrainingSet) {
					// handle if equal here
					x.distanceFrom(e);
					if (x.distance < distance) {
						distance = x.distance;
						min = x;
					}
				}

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
		
		model.trainingSet = condensedTrainingSet;
    }

    @Override
    boolean supportsRegression() {
        return false;
    }
    
}
