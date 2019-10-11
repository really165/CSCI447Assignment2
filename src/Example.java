import java.util.Arrays;

public class Example implements Comparable<Example> {

	public double c;
    public double[] data;
    public double distance;
    
    //0 for categorical, 1 for numerical
    public int[] dataType;
    public double[] min;
    public double[] max;
    
    public Example(double c, double[] data) {
        this.c = c;
        this.data = data;
    }
    
    public Example(double c, double[] data, int[] types, double[] minValues, double[] maxValues) {
        this.c = c;
        this.data = data;
        this.dataType = types;
        this.min = minValues;
        this.max = maxValues;
    }
    
    public void distanceFrom(Example e) {
        double total = 0;
        for (int i = 0; i < data.length; i++) {
            total += Math.pow(data[i] - e.data[i],2);
            // if categorical
        	if(dataType[i] == 0) {
            	//if the categories are not the same
        		if(data[i] != e.data[i]) {
        			//add one to the total
        			total++;
        		}
        		//don't add anything otherwise
            }
        	//if numerical
        	else {
        		//get min and max
        		double minValue = min[i];
        		double maxValue = max[i];
        		//find difference of max and min
        		double maxMinDifference = maxValue-minValue;
        		//find abs(this.data - e.data)
        		double data1 = data[i];
        		double data2 = e.data[i];
        		double valueDifference = Math.abs(data1-data2);
        		//divide the absolute value distance by the difference between max and min
        		double totalDifference = valueDifference/maxMinDifference;
        		total += totalDifference;
        	}
        }
        this.distance = total;
        // total += Math.pow(data[i] - e.data[i],2);
        // this.distance = Math.sqrt(total);
    }

    @Override
    public int compareTo(Example e) {
        if (this.distance < e.distance) return -1;
        else if (this.distance > e.distance) return 1;
        else return 0;
    }
    
    @Override
    public String toString() {
        return "Actual: " + c + ", Distance: " + distance + ", " + Arrays.toString(data);
    }
}
