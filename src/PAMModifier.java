public class PAMModifier extends Modifier {

    public PAMModifier(KNearestNeighborModel model) {
        super(model);
        this.description = "Partitioning Around Medoids K-Nearest Neighbor Algorithm";
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