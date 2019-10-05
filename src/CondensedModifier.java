import java.util.ArrayList;

public class CondensedModifier extends Modifier {

    public CondensedModifier(KNearestNeighborModel model) {
        super(model);
        this.description = "Condensed K-Nearest Neighbor Algorithm";
    }

    @Override
    void modifyTrainingSet(int numNeighbors) {
    	ArrayList<Example> z= new ArrayList();
    	ArrayList<Example> condensedTrainingSet= new ArrayList(model.trainingSet);
    	Example xPrime=new Example(0.0, null);
    	double distance=100.0;
    	boolean change=true;
    	
    	while(change) {
    		change=false;
    		for (Example e : condensedTrainingSet) {
    			model.trainingSet.remove(e);
    			for (Example x: condensedTrainingSet) {
    				model.trainingSet.remove(x);
    				x.distanceFrom(e);
    				double temp=x.distance;
    				if (distance>temp) {
    					distance=temp;
    					xPrime=x;
    				}
    				model.trainingSet.add(x);
    			}
    			//double distance=e.distanceFrom(e);
    			double actual= model.classify(xPrime, numNeighbors);
                double predicted = model.classify(e, numNeighbors);
                if (actual!=predicted) {
                	z.add(xPrime);
                	change=true;
                }
                model.trainingSet.add(e);
            }
    	}
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean supportsRegression() {
        return false;
    }
    
}
