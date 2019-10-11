import java.util.ArrayList;
import java.util.Collections;

public class KNearestNeighborModel {
	
	private final int numClasses;
    
    private final ArrayList<Example> examples;
    public ArrayList<Example> trainingSet;
    private ArrayList<Example> testSet;
    
    private Modifier modifier;
    private final boolean regression;
    
    public KNearestNeighborModel(ArrayList<Example> examples, int numClasses) {
        this.examples = examples;
        this.numClasses = numClasses;
        this.regression = false;
    }
    
    public KNearestNeighborModel(ArrayList<Example> examples, boolean regression) {
        this.examples = examples;
        this.numClasses = 0;
        this.regression = true;
    }
    
    public boolean isClassification() {
        return !regression;
    }
    
    public void setModifier(Modifier modifier) {
        this.modifier = modifier;
    }
    
    public double[][] kFoldCrossValidation(int k1, int k2) {
        
        ArrayList<Example>[][] folds = getKFolds(k1);
        
        int i = 0;
        double[][] table;
        
        if (regression) table = new double[folds.length*folds[0][1].size()][2];
        else table = new double[numClasses][numClasses];
        
        for (ArrayList<Example>[] fold : folds) {
            trainingSet = fold[0];
            testSet = fold[1];
            if (modifier != null) {
                //System.out.println("training set size pre: " + trainingSet.size());
                modifier.modifyTrainingSet(k2);
                //System.out.println("training set size post: " + trainingSet.size());
            }
            for (Example e : testSet) {
                if (regression) {
                    double actual = e.c;
                    double predicted = regression(e, k2);
                    table[i][0] = actual;
                    table[i][1] = predicted;
                    i++;
                }
                else {
                    int actualClass = (int)e.c;
                    int predictedClass = classify(e, k2);
                    table[actualClass][predictedClass]++;
                }
            }
        }
        
        return table;
    }
    
    private ArrayList<Example>[][] getKFolds(int k) {
        
        Collections.shuffle(examples);
        int partitionSize = (int)((double)examples.size() / k);
        ArrayList<Example>[][] folds = new ArrayList[k][2];
        
        for (ArrayList<Example>[] fold : folds) {
            for (int i = 0; i < fold.length; i++) {
                fold[i] = new ArrayList();
            }
        }
        
        for (int fold = 0; fold < k; fold++) {
            int end = partitionSize * (fold+1);
            int start = end - partitionSize;
            ArrayList<Example> trainingSet = new ArrayList(examples.subList(0, start));
            trainingSet.addAll(examples.subList(end + 1, examples.size()));
            ArrayList<Example> testSet = new ArrayList(examples.subList(start, end + 1));
            folds[fold][0] = trainingSet;
            folds[fold][1] = testSet;
        }
        
        return folds;
    }
    
    public int classify(Example example, int k) {
        trainingSet.forEach(e -> e.distanceFrom(example));
        Collections.sort(trainingSet);
        
        int[] counts = new int[numClasses];
        
        for (int i = 0; i < k && i < trainingSet.size(); i++) {
            int val = (int)trainingSet.get(i).c;
            counts[val]++;
        }

        int count = 0;
        int predicted = -1;

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > count) {
                count = counts[i];
                predicted = i;
            }
        }
        
        return predicted;
    }
    
    public double regression(Example example, int k) {
        trainingSet.forEach(e -> e.distanceFrom(example));
        Collections.sort(trainingSet);
        
        //System.out.println("Example: \n\t" + example);
        //System.out.println("Nearest Neighbors: ");
        //for (Example e : trainingSet.subList(0, k)) System.out.println("\t" + e);
        
        double sum = 0;

        for (int i = 0; i < k; i++) {
            double value = trainingSet.get(i).c;
            sum += value;
        }
        
        //System.out.println("Predicted: " + sum/k + "\n");
        
        return sum/k;
    }
}
