import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            // Array mit Dateipfaden (für mehrere Stationen)
            String[] filenames = {
                    "C:\\Users\\deniz\\Desktop\\produkt_klima_tag_19370101_20181130_04692.txt",
                    "C:\\Users\\deniz\\Downloads\\produkt_klima_tag_19860101_20241231_04978.txt"
            };

            // Jahre, die wir analysieren wollen
            int[] years = {1937, 1950, 1986, 2024};

            // Map mit Stationsnamen
            Map<String, String> stationNames = Map.of(
                    "04692", "Siegen (Klaeranlage)",
                    "04978", "Tann/Roehn"
            );

            System.out.println("Analyse fuer DWD-Daten:\n");

            // Für jede Datei (Station) ...
            for (String filename : filenames) {
                // Zeitreihe aus Datei erstellen
                TemperatureTimeSeries series = new TemperatureTimeSeries(filename);

                // Stationsnummer aus Dateinamen extrahieren
                String stationId = filename.replaceAll(".*_(\\d{5})\\.txt", "$1");
                String stationName = stationNames.getOrDefault(stationId, "Unbekannte Station (" + stationId + ")");

                System.out.println("Station: " + stationName);

                // Für jedes Jahr Durchschnitt berechnen und ausgeben
                for (int year : years) {
                    TemperatureTimeSeries filtered = series.filterByYear(year);
                    double avgTemp = filtered.getOverallAvg();
                    System.out.printf("Jahr %d: Durchschnittstemperatur = %.2f °C\n", year, avgTemp);

                    if (avgTemp == 0) {
                        System.out.println("⚠ Achtung: Moeglicherweise fehlende Werte als 0 Celcius interpretiert.");
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
        }
    }
}
