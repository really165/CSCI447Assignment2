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
    private static final int SEGMENTATION_NUM_ATTRIBUTES = 19;
    private static final int MACHINE_NUM_ATTRIBUTES = 6;
    
    public static ArrayList<Example> processAbaloneSet() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("data/abalone.data"));
        ArrayList<Example> examples = new ArrayList<Example>();
        String line = "";
        
        double maxValue = 10000000000.0;
        double[] min = new double[]{ maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue };
        double[] max = new double[]{ 0,0,0,0,0,0,0,0 };
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            double[] data = new double[ABALONE_NUM_ATTRIBUTES];
            String[] parts = line.split(",");
            
            if (parts.length < ABALONE_NUM_ATTRIBUTES+1) continue;
            
            if (parts[0].equals("M")) data[0] = 0;
            else if (parts[0].equals("F")) data[0] = 1;
            else data[0] = 2;
            
            for (int i = 1; i < parts.length-1; i++) {
            	data[i] = Double.parseDouble(parts[i]);
            	if(data[i] < min[i]) {
            		min[i] = data[i];
            	}
            	if(data[i] > max[i]) {
            		max[i] = data[i];
            	}
            }
            
            int c = Integer.parseInt(parts[parts.length-1]) - 1;
            int[] types = new int[]{ 0,1,1,1,1,1,1,1 };
            Example e = new Example(c, data, types, min, max);
            examples.add(e);
        } sc.close();
        
        return examples;
    }
    
    public static ArrayList<Example> processWineQualitySet() throws FileNotFoundException {
        //scan the red wine data
    	Scanner sc = new Scanner(new File("data/winequality-red.csv"));
        ArrayList<Example> examples = new ArrayList<Example>();
        String line = "";
        
        line = sc.nextLine();
        
        double maxValue = 10000000000.0;
        double[] min = new double[]{ maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue };
        double[] max = new double[]{ 0,0,0,0,0,0,0,0,0,0,0 };
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            double[] data = new double[REDWINE_NUM_ATTRIBUTES];
            String[] parts = line.split(";");
            
            if (parts.length < REDWINE_NUM_ATTRIBUTES+1) continue;
            
            for (int i = 0; i < parts.length-1; i++) {
            	data[i] = Double.parseDouble(parts[i]);
            	if(data[i] < min[i]) {
            		min[i] = data[i];
            	}
            	if(data[i] > max[i]) {
            		max[i] = data[i];
            	}
            }
            
            double c = Double.parseDouble(parts[parts.length-1]);
            int[] types = new int[]{ 1,1,1,1,1,1,1,1,1,1,1 };
            Example e = new Example(c, data, types, min, max);
            examples.add(e);
        } sc.close();
        
        //scan the white wine data
        Scanner sc2 = new Scanner(new File("data/winequality-white.csv"));
        String line2 = "";
        
        line2 = sc2.nextLine();
        
        while (sc2.hasNextLine()) {
            line2 = sc2.nextLine();
            double[] data = new double[WHITEWINE_NUM_ATTRIBUTES];
            String[] parts = line2.split(";");
            
            if (parts.length < WHITEWINE_NUM_ATTRIBUTES+1) continue;
            
            for (int i = 0; i < parts.length-1; i++) {
            	data[i] = Double.parseDouble(parts[i]);
            	if(data[i] < min[i]) {
            		min[i] = data[i];
            	}
            	if(data[i] > max[i]) {
            		max[i] = data[i];
            	}
            }
            
            double c = Double.parseDouble(parts[parts.length-1]);
            int[] types = new int[]{ 1,1,1,1,1,1,1,1,1,1,1 };
            Example e = new Example(c, data, types, min, max);
            examples.add(e);
        } sc2.close();
        
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
        ArrayList<Example> examples = new ArrayList<Example>();
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
            
            int[] types = new int[]{ 1,1,1,1,1,1 };
            double[] min = new double[]{ 1,1,1,1,1,1};
            double[] max = new double[]{ 4,4,4,3,3,3};
            Example e = new Example(c, data, types, min, max);
            examples.add(e);
        } sc.close();
        
        return examples;
    }
    
    public static ArrayList<Example> processForestSet() throws FileNotFoundException {
    	//X: 1 to 9
    	//Y: 2 to 9
    	//month("jan" to "dec"): 1 to 12 
    	//day("mon" to "sun"): 1 to 7
    	//FFMC: 18.7 to 96.20
    	//DMC: 1.1 to 291.3 
    	//DC: 7.9 to 860.6 
    	//ISI: 0.0 to 56.10
    	//temp: 2.2 to 33.30
    	//RH: 15.0 to 100
    	//wind: 0.40 to 9.40 
    	//rain: 0.0 to 6.4 
    	//area: 0.00 to 1090.84 
    	//scan the red wine data
    	Scanner sc = new Scanner(new File("data/forestfires.data"));
        ArrayList<Example> examples = new ArrayList<Example>();
        String line = "";
        
        //skip the first line
        line = sc.nextLine();
        
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            double[] data = new double[FOREST_NUM_ATTRIBUTES];
            String[] parts = line.split(",");
            
            if (parts.length < FOREST_NUM_ATTRIBUTES+1) continue;
            
            double maxValue = 10000000000.0;
            int[] types = new int[]{ 1,1,1,1,1,1,1,1,1,1,1,1 };
            double[] min = new double[]{ maxValue,maxValue,1,1,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue };
            double[] max = new double[]{ 0,0,12,7,0,0,0,0,0,0,0,0 };
            
            //first two columns
            for (int i = 0; i < 2; i++) data[i] = Double.parseDouble(parts[i]);
            
            //third column
            if (parts[2].equals("jan")) data[5] = 1;
            else if (parts[2].equals("feb")) data[2] = 2;
            else if (parts[2].equals("mar")) data[2] = 3;
            else if (parts[2].equals("apr")) data[2] = 4;
            else if (parts[2].equals("may")) data[2] = 5;
            else if (parts[2].equals("jun")) data[2] = 6;
            else if (parts[2].equals("jul")) data[2] = 7;
            else if (parts[2].equals("aug")) data[2] = 8;
            else if (parts[2].equals("sep")) data[2] = 9;
            else if (parts[2].equals("oct")) data[2] = 10;
            else if (parts[2].equals("nov")) data[2] = 11;
            else if (parts[2].equals("dec")) data[2] = 12;
            else data[2] = 0;
            
            //fourth column
            if (parts[3].equals("sat")) data[3] = 1;
            else if (parts[3].equals("sun")) data[3] = 2;
            else if (parts[3].equals("mon")) data[3] = 3;
            else if (parts[3].equals("tue")) data[3] = 4;
            else if (parts[3].equals("wed")) data[3] = 5;
            else if (parts[3].equals("thu")) data[3] = 6;
            else if (parts[3].equals("fri")) data[3] = 7;
            else data[3] = 0;
            
            //fifth through twelfth columns
            for (int j = 4; j < 12; j++) {
            	data[j] = Double.parseDouble(parts[j]);
            	if(data[j] < min[j]) {
            		min[j] = data[j];
            	}
            	if(data[j] > max[j]) {
            		max[j] = data[j];
            	}
            }
            
            double c = Double.parseDouble(parts[parts.length-1]);
            Example e = new Example(c, data, types, min, max);
            examples.add(e);
        } sc.close();
        
        return examples;
    }
    
    public static ArrayList<Example> processSegmentationSet() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("data/segmentation.data"));
        ArrayList<Example> examples = new ArrayList<Example>();
        String line = "";
        
        //skip the first 5 lines
        for (int j = 0; j < 5; j++) line = sc.nextLine();
        
        double maxValue = 10000000000.0;
        double[] min = new double[]{ maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue,maxValue };
        double[] max = new double[]{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
        
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            double[] data = new double[SEGMENTATION_NUM_ATTRIBUTES];
            String[] parts = line.split(",");
            
            if (parts.length < SEGMENTATION_NUM_ATTRIBUTES+1) continue;
            
            double c;
            if (parts[0].equals("BRICKFACE")) c = 0;
            else if (parts[0].equals("SKY")) c = 1;
            else if (parts[0].equals("FOLIAGE")) c = 2;
            else if (parts[0].equals("CEMENT")) c = 3;
            else if (parts[0].equals("WINDOW")) c = 4;
            else if (parts[0].equals("PATH")) c = 5;
            else if (parts[0].equals("GRASS")) c = 6;
            else c = -1;
            
            for (int i = 1; i < parts.length; i++) {
            	data[i-1] = Double.parseDouble(parts[i]);
            	if(data[i-1] < min[i-1]) {
            		min[i-1] = data[i-1];
            	}
            	if(data[i-1] > max[i-1]) {
            		max[i-1] = data[i-1];
            	}
            }
            
            
            int[] types = new int[]{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1 };
            Example e = new Example(c, data, types, min, max);
            examples.add(e);
        } sc.close();
        
        return examples;
    }
    
    public static ArrayList<Example> processMachineSet() throws FileNotFoundException {
    	Scanner sc = new Scanner(new File("data/machine.data"));
        ArrayList<Example> examples = new ArrayList<Example>();
        String line = "";
        
        double maxValue = 10000000000.0;
        double minValue = -10000000000.0;
        double[] min = new double[]{ maxValue,maxValue,maxValue,maxValue,maxValue,maxValue };
        double[] max = new double[]{ minValue,minValue,minValue,minValue,minValue,minValue };
        
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            double[] data = new double[MACHINE_NUM_ATTRIBUTES];
            String[] parts = line.split(",");
            
            if (parts.length < MACHINE_NUM_ATTRIBUTES+1) continue;
            
            //columns 3-8
            for (int i = 2; i < 8; i++) {
            	data[i-2] = Double.parseDouble(parts[i]);
            	if(data[i-2] < min[i-2]) {
            		min[i-2] = data[i-2];
            	}
            	if(data[i-2] > max[i-2]) {
            		max[i-2] = data[i-2];
            	}
            }
            
            //second to last column, PRP
            int[] types = new int[]{ 1,1,1,1,1,1 };
            double c = Double.parseDouble(parts[parts.length-2]);
            Example e = new Example(c, data, types, min, max);
            examples.add(e);
        } sc.close();
            
        return examples;
    }
}
