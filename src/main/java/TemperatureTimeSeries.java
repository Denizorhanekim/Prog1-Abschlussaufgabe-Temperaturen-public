import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TemperatureTimeSeries {
    private ArrayList<TemperatureDataPoint> dataPoints;

    // Default constructor , der eine leere Zeitreihe erstellt
    public TemperatureTimeSeries() {
        dataPoints = new ArrayList<>();
    }

    // Constructor , der Temperaturdaten aus einer CSV-Datei einliest
    public TemperatureTimeSeries(String filename) throws Exception {
        dataPoints = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    dataPoints.add(new TemperatureDataPoint(line));
                }
            }
        } catch (Exception e) {
            throw new Exception("Fehler beim Einlesen der Datei " + filename);
        }
    }

    // Add a datapoint
    public void addDataPoint(TemperatureDataPoint dataPoint) {
        dataPoints.add(dataPoint);
    }

    // Return all data points
    public ArrayList<TemperatureDataPoint> getDataPoints() {
        return dataPoints;
    }

    // Return number of data points
    public int getNumDatapoints() {
        return dataPoints.size();
    }

    // Filter by year
    public TemperatureTimeSeries filterByYear(int year) {
        TemperatureTimeSeries filtered = new TemperatureTimeSeries();
        for (TemperatureDataPoint dp : dataPoints) {
            if (dp.isInYear(year)) {
                filtered.addDataPoint(dp);
            }
        }
        return filtered;
    }

    // Minimum temperature

    public double getMinTemperature() {

        double min = Double.MAX_VALUE;
        for (TemperatureDataPoint dp : dataPoints) {
            if (dp.getMinTemperature() < min) {
                min = dp.getMinTemperature();
            }
        }
        return min;
    }





       // Maximum temperature
    public double getMaxTemperature() {
        double max = -Double.MAX_VALUE;
        for (TemperatureDataPoint dp : dataPoints) {
            if (dp.getMaxTemperature() > max) {
                max = dp.getMaxTemperature();
            }
        }
        return max;
    }





   /*
   public double getMinTemperature()
       double min = Double.MAX_VALUE;
        for (TemperatureDataPoint dp : dataPoints) {
            double temp = dp.getMinTemperature();
            if (!Double.isNaN(temp) && temp < min) {
                min = temp;
            }
        }
        return min == Double.MAX_VALUE ? Double.NaN : min;
    }
{
    */

    /*
    public double getMaxTemperature()
          double max = -Double.MAX_VALUE;
        for (TemperatureDataPoint dp : dataPoints) {
            double temp = dp.getMaxTemperature();
            if (!Double.isNaN(temp) && temp > max) {
                max = temp;
            }
        }
        return max == -Double.MAX_VALUE ? Double.NaN : max;
    }{
     */



    // Average of average temperatures
    /*
    public double getOverallAvg() {

        if (dataPoints.isEmpty()) return 0;
        double total = 0;
        for (TemperatureDataPoint dp : dataPoints) {
            total += dp.getAvgTemperature();
        }
        return total / dataPoints.size();
    }
*/


    //Korrigierte Methode für die AnalyseAufagbe
    public double getOverallAvg() {
        double sum = 0;
        int count = 0;
        for (TemperatureDataPoint dp : dataPoints) {
            double value = dp.getAvgTemperature();
            if (!Double.isNaN(value)) {
                sum += value;
                count++;
            }
        }
        return count > 0 ? sum / count : Double.NaN;
    }
    // Differences between consecutive data points
    public TemperatureDataPoint[] getTemperatureDifferences() {
        ArrayList<TemperatureDataPoint> diffs = new ArrayList<>();
        for (int i = 1; i < dataPoints.size(); i++) {
            TemperatureDataPoint prev = dataPoints.get(i - 1);
            TemperatureDataPoint current = dataPoints.get(i);
            diffs.add(current.getTemperatureDifference(prev));
        }
        return diffs.toArray(new TemperatureDataPoint[0]);
    }

    // toString for the time series
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Temperature time series with ").append(dataPoints.size()).append(" data points:\n");
        for (TemperatureDataPoint dp : dataPoints) {
            sb.append("* ").append(dp.toString()).append("\n");
        }
        return sb.toString();
    }
}

