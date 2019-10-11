
// abstract class that allows specifying which algorithm the model should use
public abstract class Modifier {
	
	public String description;
    
    protected KNearestNeighborModel model;
    
    
    public Modifier(KNearestNeighborModel model) {
        this.model = model;
    }
    
    // function to run the algorithm on training set
    abstract void modifyTrainingSet(int numNeighbors);
    
    // specifies whether or not the algo supports regression
    abstract boolean supportsRegression();
	
}