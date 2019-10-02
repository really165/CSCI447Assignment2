public class KMeansModifier extends Modifier {

    public KMeansModifier(KNearestNeighborModel model) {
        super(model);
        this.description = "K-Means K-Nearest Neighbor Algorithm";
    }

    @Override
    void modifyTrainingSet(int numNeighbors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean supportsRegression() {
        return true;
    }
    
}