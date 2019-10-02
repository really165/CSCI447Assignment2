import java.util.ArrayList;

public class KNearestNeighborModel {
	
	private int numClasses;
	private ArrayList<Example> examples;
	private ArrayList<Example> trainingSet;
	private ArrayList<Example> testSet;
	private Modifier modifier;
	
	public void KNearestNeighborModel(ArrayList<Example> testSet, int k) {
		
	}
	
	public void setModifier(Modifier m) {
		
	}
	
	public int[][] kFoldCrossValidation(int , int) {
		
	}
	
	private ArrayList<Example>[][] getKFolds(int k){
		
	}
	
	private double classify(Example example, int k) {
		
	}
	
	private double regress(Example example, int k) {
		
	}
}
