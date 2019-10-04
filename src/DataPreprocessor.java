import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataPreprocessor {

	private static final int ABALONE_NUM_ATTRIBUTES = 8;
    private static final int REDWINE_NUM_ATTRIBUTES = 11;
    private static final int WHITEWINE_NUM_ATTRIBUTES = 11;
    private static final int CAR_NUM_ATTRIBUTES = 6;
    private static final int FOREST_NUM_ATTRIBUTES = 12;
    
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
        //scan the red wine data
    	Scanner sc = new Scanner(new File("data/winequality-red.csv"));
        ArrayList<Example> examples = new ArrayList();
        String line = "";
        
        line = sc.nextLine();
        
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
        
        //scan the white wine data
        Scanner sc2 = new Scanner(new File("data/winequality-white.csv"));
        String line2 = "";
        
        line2 = sc2.nextLine();
        
        while (sc2.hasNextLine()) {
            line2 = sc2.nextLine();
            double[] data = new double[WHITEWINE_NUM_ATTRIBUTES];
            String[] parts = line2.split(";");
            
            if (parts.length < WHITEWINE_NUM_ATTRIBUTES+1) continue;
            
            for (int i = 0; i < parts.length-1; i++) data[i] = Double.parseDouble(parts[i]);
            
            double c = Double.parseDouble(parts[parts.length-1]);
            Example e = new Example(c, data);
            examples.add(e);
        }
        
        return examples;
    }
    
    public static ArrayList<Example> processCarSet() throws FileNotFoundException {
    	//classification:
    	//---------------unacc = 0
    	//buying: v-high = 4, high = 3, med= 2, low = 1
    	//maint: v-high = 4, high = 3, med = 2, low = 1
    	//doors: 2 = 1, 3 = 2, 4 = 3, 5-more = 4
    	//persons: 2 = 1, 4 = 2, more = 3
    	//lug_boot: small = 1, med = 2, big = 3
    	//safety: low = 1, med = 2, high = 3
    	//class: unacc = 0, acc = 1, good = 2, v-good = 3
        Scanner sc = new Scanner(new File("data/car.data"));
        ArrayList<Example> examples = new ArrayList();
        String line = "";
        
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            double[] data = new double[CAR_NUM_ATTRIBUTES];
            String[] parts = line.split(",");
            
            if (parts.length < CAR_NUM_ATTRIBUTES+1) continue;
            
            //buying
            if (parts[0].equals("v-high")) data[0] = 4;
            else if (parts[0].equals("high")) data[0] = 3;
            else if (parts[0].equals("med")) data[0] = 2;
            else if (parts[0].equals("low")) data[0] = 1;
            else data[0] = 0;
            
            //maint
            if (parts[1].equals("v-high")) data[1] = 4;
            else if (parts[1].equals("high")) data[1] = 3;
            else if (parts[1].equals("med")) data[1] = 2;
            else if (parts[1].equals("low")) data[1] = 1;
            else data[1] = 0;
            
            //doors
            if (parts[2].equals("2")) data[2] = 1;
            else if (parts[2].equals("3")) data[2] = 2;
            else if (parts[2].equals("4")) data[2] = 3;
            else if (parts[2].equals("5-more")) data[2] = 4;
            else data[2] = 0;
            
            //persons
            if (parts[3].equals("2")) data[3] = 1;
            else if (parts[3].equals("4")) data[3] = 2;
            else if (parts[3].equals("more")) data[3] = 3;
            else data[3] = 0;
            
            //lug_boot
            if (parts[4].equals("small")) data[4] = 1;
            else if (parts[4].equals("med")) data[4] = 2;
            else if (parts[4].equals("big")) data[4] = 3;
            else data[4] = 0;
            
            //safety
            if (parts[5].equals("low")) data[5] = 1;
            else if (parts[5].equals("med")) data[5] = 2;
            else if (parts[5].equals("high")) data[5] = 3;
            else data[5] = 0;
            
            String exampleClass = parts[6];
            double c;
            if(exampleClass.equals("acc")) c = 1;
            else if(exampleClass.equals("good")) c = 2;
            else if(exampleClass.equals("v-good")) c = 3;
            else c = 0;
            
            Example e = new Example(c, data);
            examples.add(e);
        }
        
        return examples;
    }
}
