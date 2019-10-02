import java.util.ArrayList;

public abstract class Modifier {
	
	public String description;
    
    protected KNearestNeighborModel model;
    
    
    public Modifier(KNearestNeighborModel model) {
        this.model = model;
    }
    
    
    abstract void modifyTrainingSet(int numNeighbors);
    
    abstract boolean supportsRegression();
	
}