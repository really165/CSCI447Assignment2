import java.util.ArrayList;

public class EditedModifier extends Modifier {

	public EditedModifier(KNearestNeighborModel model) {
        super(model);
        description = "Edited Nearest Neighbor Algorithm";
    }
    
    @Override
    public void modifyTrainingSet(int numNeighbors) {
        
        ArrayList<Example> editedTrainingSet = new ArrayList(model.trainingSet);
        
        int i = 0;
        boolean changed = true;

        while (i < 100 && changed) {
            
            changed = false;
            ArrayList<Example> toRemove = new ArrayList();
            
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
        
    }

    @Override
    boolean supportsRegression() {
        return false;
    }

}
