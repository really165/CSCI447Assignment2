public class CondensedModifier extends Modifier {

    public CondensedModifier(KNearestNeighborModel model) {
        super(model);
        this.description = "Condensed K-Nearest Neighbor Algorithm";
    }

    @Override
    void modifyTrainingSet(int numNeighbors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    boolean supportsRegression() {
        return false;
    }
    
}