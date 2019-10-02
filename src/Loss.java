public class Loss {
	
	// 0-1 loss function for classification
    public static double classification(double[][] table) {
        
        double miss = 0;
        int total = 0;
        
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (i != j) miss += table[i][j];
                total += table[i][j];
            }
        }
        
        return miss/total;
    }
    
    // mean squared error loss function for regression
    public static double regression(double[][] table) {
        double sum = 0;
        for (double[] row : table) sum += Math.pow((row[0] - row[1]), 2);
        return sum/table.length;
    }
}