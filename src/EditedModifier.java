import java.util.ArrayList;
import java.util.Collections;

public class EditedModifier extends Modifier {

	public EditedModifier(KNearestNeighborModel model) {
        super(model);
        description = "Edited Nearest Neighbor Algorithm";
    }
    
    @Override
    public void modifyTrainingSet(int numNeighbors) {
        
        // copy training set
        ArrayList<Example> editedTrainingSet = new ArrayList<Example>(model.trainingSet);
        
        int i = 0;
        boolean changed = true;

        System.out.println("editing training set...");
        System.out.println("\tinitial size: " + model.trainingSet.size());

        // repeat until no change
        while (i < 100 && changed) {
            
            changed = false;
            ArrayList<Example> toRemove = new ArrayList<Example>();

            Collections.shuffle(editedTrainingSet);
            // iterate over copy
            for (Example e : editedTrainingSet) {
                // remove current example from training set
                model.trainingSet.remove(e);
                // classify the example
                double actual = e.c;
                double predicted = model.classify(e, numNeighbors);
                // if prediction is wrong add the example to the list to be removed
                if (predicted != actual) {
                    toRemove.add(e);
                    changed = true;
                }
                // add point back to training set
                model.trainingSet.add(e);
            }

            // remove flagged examples
            editedTrainingSet.removeAll(toRemove);
            model.trainingSet.removeAll(toRemove);
            
            i++;
        }

        System.out.println("\tfinal size: " + model.trainingSet.size());
        System.out.println("\tnumber of iterations: " + i);
        
    }

    @Override
    boolean supportsRegression() {
        return false;
    }

}
