import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
        
        int[] kValues = new int[3];
        kValues[0] = 5;
        kValues[1] = 10;
        kValues[2] = 50;
        
        try {
            // process data files
            ArrayList<Example> abaloneExamples = DataPreprocessor.processAbaloneSet();
            ArrayList<Example> redwineExamples = DataPreprocessor.processWineQualitySet();
            
            // create models
            KNearestNeighborModel abaloneClassifier = new KNearestNeighborModel(abaloneExamples, 29);
            KNearestNeighborModel redwineRegression = new KNearestNeighborModel(redwineExamples, true);
            
            ArrayList<KNearestNeighborModel> models = new ArrayList();
            models.add(abaloneClassifier);
            models.add(redwineRegression);
            
            // iterate over models
            for (KNearestNeighborModel model : models) {
                // create modifiers
                ArrayList<Modifier> modifiers = new ArrayList();
                modifiers.add(null);
                modifiers.add(new EditedModifier(model));
                modifiers.add(new CondensedModifier(model));
                //  iterate over modifiers
                for (Modifier modifier : modifiers) {
                    model.setModifier(modifier);
                    if (modifier != null) System.out.println(modifier.description + "\n");
                    else System.out.println("K-Nearest Neighbor Algorithm\n");
                    // iterate over k values
                    if (modifier == null || model.isClassification() || (modifier.supportsRegression())) {
                        for (int k : kValues) {
                            System.out.println("k = " + k);
                            double[][]table = model.kFoldCrossValidation(10, k);
                            if (model.isClassification()) System.out.printf("0-1 Loss: %.2f%%\n", Loss.classification(table)*100);
                            else System.out.printf("MSE: %.2f%%\n", Loss.regression(table));
                            System.out.println();
                        }
                    }
                }
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
