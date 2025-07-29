import java.util.Locale;
public class TemperatureDataPoint {
    private final double min;
    private final double avg;
    private final double max;
    private  final int year;

    // Konstruktor Nr1 mit expliziten Werten
    public TemperatureDataPoint(double min, double avg, double max, int year){
        this.min=min; // this.x = Instanzvariable ,  x = Parameter
        this.avg=avg;
        this.max=max;
        this.year=year;
    }

    public TemperatureDataPoint(String line) throws IllegalArgumentException {
        String[] parts = line.split(";");
        if (parts.length < 17) {
            throw new IllegalArgumentException("Line too short: " + line);
        }
        try {
            this.year = Integer.parseInt(parts[1].trim().substring(0, 4));
            this.avg = parseTemperature(parts[13]);
            this.max = parseTemperature(parts[15]);
            this.min = parseTemperature(parts[16]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid temperature data: " + line, e);
        }
    }

    /*
    private double parseTemperature(String str) {

        str = str.trim();
        if (str.isEmpty() || str.equals("-999.0") || str.equalsIgnoreCase("NA")) {
            return 0.0;
        }
        return Double.parseDouble(str);
    }
*/
    private double parseTemperature(String str) {
        str = str.trim();
        if (str.isEmpty() || str.equalsIgnoreCase("NA")) {
            return Double.NaN;
        }
        try {
            double val = Double.parseDouble(str);
            if ( val == -999) {
                return Double.NaN;
            }
            return val;
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }


    /*
    //Hilfe-Methode, benutzerdefiniert, um die Statistische-Werte der Temperatur-Strings aus der Zeilen zu  Parsen unf 999.0 als missing value zu behandeln
    private double parseTemperature(String str) {
        double value= Double.parseDouble(str.trim());
        return value == -999.0 ? 0.0:value;
    }
*/
    // Returns absolute difference between temperatures, keeping the year anchored at this.x year not the other
    public TemperatureDataPoint getTemperatureDifference(TemperatureDataPoint other){
        double minDiff=Math.abs(this.min-other.min);
        double avgDiff=Math.abs(this.avg-other.avg);
        double maxDiff=Math.abs(this.max-other.max);
        return new TemperatureDataPoint(minDiff,avgDiff,maxDiff,this.year);
    }

    //Getter Methoden, da wir private Attribute haben
    public double getMinTemperature(){
        return this.min;
    }
    public double getAvgTemperature(){
        return this.avg;
    }
    public double getMaxTemperature(){
        return this.max;
    }
    public int getYear(){
        return this.year;
    }
    //Check true or false if the temperature data is from a given speicif year
    public boolean isInYear(int year){
        return this.year==year;
    }
    // toString override
    @Override
    public String toString() {
        return String.format(Locale.US,
                "Temperatures on given day in %d: %.1f - %.1f, average: %.1f.",
                year, min, max, avg);
    }
}
