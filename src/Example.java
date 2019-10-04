import java.util.Arrays;

public class Example implements Comparable {

	public double c;
    public double[] data;
    public String[] categoricalAttributes;
    public double[] numericalAttributes;
    public int[][] similarityMatrix;
    public double distance;
    
    public Example(double c, double[] data) {
        this.c = c;
        this.data = data;
    }
    
    public void distanceFrom(Example e) {
        double squares = 0;
        for (int i = 0; i < data.length; i++) {
            squares += Math.pow(data[i]-e.data[i], 2);
        }
        this.distance = Math.sqrt(squares);
    }

    @Override
    public int compareTo(Object o) {
        Example e = (Example)o;
        if (this.distance < e.distance) return -1;
        else if (this.distance > e.distance) return 1;
        else return 0;
    }
    
    @Override
    public String toString() {
        return "Actual: " + c + ", Distance: " + distance + ", " + Arrays.toString(data);
    }
}