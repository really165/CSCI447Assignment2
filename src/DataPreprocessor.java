import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataPreprocessor {

	private static final int ABALONE_NUM_ATTRIBUTES = 8;
    private static final int REDWINE_NUM_ATTRIBUTES = 11;
    
    public static ArrayList<Example> processAbaloneSet() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("data/abalone.data"));
        ArrayList<Example> examples = new ArrayList();
        String line = "";
        
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            double[] data = new double[ABALONE_NUM_ATTRIBUTES];
            String[] parts = line.split(",");
            
            if (parts.length < ABALONE_NUM_ATTRIBUTES+1) continue;
            
            if (parts[0].equals("M")) data[0] = 0;
            else if (parts[0].equals("F")) data[0] = 1;
            else data[0] = 2;
            
            for (int i = 1; i < parts.length-1; i++) data[i] = Double.parseDouble(parts[i]);
            
            int c = Integer.parseInt(parts[parts.length-1]);
            Example e = new Example(c, data);
            examples.add(e);
        }
        
        return examples;
    }
    
    public static ArrayList<Example> processWineQualitySet() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("data/winequality-red.csv"));
        ArrayList<Example> examples = new ArrayList();
        String line = "";
        
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            double[] data = new double[REDWINE_NUM_ATTRIBUTES];
            String[] parts = line.split(";");
            
            if (parts.length < REDWINE_NUM_ATTRIBUTES+1) continue;
            
            for (int i = 0; i < parts.length-1; i++) data[i] = Double.parseDouble(parts[i]);
            
            double c = Double.parseDouble(parts[parts.length-1]);
            Example e = new Example(c, data);
            examples.add(e);
        }
        
        return examples;
    }
}
