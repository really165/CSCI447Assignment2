import java.util.ArrayList;
import java.util.Collections;

public class EditedModifier extends Modifier {

	public EditedModifier(KNearestNeighborModel model) {
        super(model);
        description = "Edited Nearest Neighbor Algorithm";
    }
    
    @Override
    public void modifyTrainingSet(int numNeighbors) {
        
        ArrayList<Example> editedTrainingSet = new ArrayList<Example>(model.trainingSet);
        
        int i = 0;
        boolean changed = true;

        System.out.println("editing training set...");
        System.out.println("\tinitial size: " + model.trainingSet.size());

        while (i < 100 && changed) {
            
            changed = false;
            ArrayList<Example> toRemove = new ArrayList<Example>();

            Collections.shuffle(editedTrainingSet);
            for (Example e : editedTrainingSet) {
                model.trainingSet.remove(e);
                double actual = e.c;
                double predicted = model.classify(e, numNeighbors);
                if (predicted != actual) {
                    toRemove.add(e);
                    changed = true;
                }
                model.trainingSet.add(e);
            }

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
