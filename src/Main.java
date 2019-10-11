import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
        
        // k-values for knn
        int[] kValues = new int[] {5,10,50};
        // k-values for clusters
        int[] k2Values = new int[] {500,1200,15,50,125,1500};
        int i = 0;
        
        try {
            // process data files
            ArrayList<Example> abaloneExamples = DataPreprocessor.processAbaloneSet();
            ArrayList<Example> carExamples = DataPreprocessor.processCarSet();
            ArrayList<Example> segmentationExamples = DataPreprocessor.processSegmentationSet();
            ArrayList<Example> machineExamples = DataPreprocessor.processMachineSet();
            ArrayList<Example> forestExamples = DataPreprocessor.processForestSet();
            ArrayList<Example> redwineExamples = DataPreprocessor.processWineQualitySet();
            
            // create models
            KNearestNeighborModel abaloneClassifier = new KNearestNeighborModel(abaloneExamples, 29);
            KNearestNeighborModel carClassifier = new KNearestNeighborModel(carExamples, 4);
            KNearestNeighborModel segmentationModel = new KNearestNeighborModel(segmentationExamples, 19);
            KNearestNeighborModel machineRegression = new KNearestNeighborModel(machineExamples, true);
            KNearestNeighborModel forestRegression = new KNearestNeighborModel(forestExamples, true);
            KNearestNeighborModel redwineRegression = new KNearestNeighborModel(redwineExamples, true);
            
            ArrayList<KNearestNeighborModel> models = new ArrayList<KNearestNeighborModel>();
            models.add(abaloneClassifier);
            models.add(carClassifier);
            models.add(segmentationModel);
            models.add(machineRegression);
            models.add(forestRegression);
            models.add(redwineRegression);
            
            // iterate over models
            for (KNearestNeighborModel model : models) {
                // create modifiers
                ArrayList<Modifier> modifiers = new ArrayList<Modifier>();
                modifiers.add(null);
                modifiers.add(new EditedModifier(model));
                modifiers.add(new CondensedModifier(model));
                modifiers.add(new KMeansModifier(model, k2Values[i]));
                modifiers.add(new PAMModifier(model, k2Values[i]));
                //  iterate over modifiers
                for (Modifier modifier : modifiers) {
                    model.setModifier(modifier);
                    if (modifier != null) System.out.println(modifier.description + "\n");
                    else System.out.println("K-Nearest Neighbor Algorithm\n");
                    // iterate over k values
                    if (modifier == null || model.isClassification() || (modifier.supportsRegression())) {
                        for (int k : kValues) {
                            System.out.println("[K = " + k + "]");
                            double[][]table = model.kFoldCrossValidation(10, k);
                            if (model.isClassification()) System.out.printf("0-1 Loss: %.2f%%\n", Loss.classification(table)*100);
                            else System.out.printf("MSE: %.2f\n", Loss.regression(table));
                            System.out.println();
                        }
                    }
                }
                i++;
            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
