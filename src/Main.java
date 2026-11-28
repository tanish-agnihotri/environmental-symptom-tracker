import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

void main() {
    boolean running = true;

    IO.println("Welcome to the Environmental Symptom Tracker!");

    // Main menu loop
    while (running) {
        IO.println("\nMain Menu:");
        IO.println("1. Log Today's Data");
        IO.println("2. Run Analysis");
        IO.println("3. Exit");
        
        String input = IO.readln("Choose an option: ");
        int choice = 0;
        
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // 
        }

        if (choice == 1) {
            logData();
        } else if (choice == 2) {
            IO.println("Analyzing data... (coming soon)");
        } else if (choice == 3) {
            running = false;
            IO.println("Goodbye!");
        } else {
            IO.println("Invalid choice. Please try again.");
        }
    }
}

void logData() {
    String symptom = IO.readln("Did you experience brain fog or a headache today? (Y/N): ").toUpperCase();
    
    IO.println("Fetching current weather data for West Lafayette...");

    try {

        // I chose Java's built-in HttpClient so I wouldn't have to install external 
        // libraries just to make a simple GET request.

         HttpClient client = HttpClient.newHttpClient();
        
        // URL pulls current temperature, humidity, and surface pressure for West Lafayette, IN
        String url = "https://api.open-meteo.com/v1/forecast?latitude=40.4259&longitude=-86.9081&current=temperature_2m,relative_humidity_2m,surface_pressure";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();

        // Extracts the values for temperature, humidity, and pressure from the JSON
        String tempStr = extractJsonValue(json, "\"temperature_2m\":");
        String humidityStr = extractJsonValue(json, "\"relative_humidity_2m\":");
        String pressureStr = extractJsonValue(json, "\"surface_pressure\":");

        String date = LocalDate.now().toString();
        String csvLine = date + "," + symptom + "," + pressureStr + "," + tempStr + "," + humidityStr + "\n";
        Path filePath = Path.of("symptoms_log.csv");

        // adds a header to the CSV file if the CSV doesn't exist yet
        if (!Files.exists(filePath)) {
            Files.writeString(filePath, "Date,Symptom,Pressure,Temp,Humidity\n");
        }

        Files.writeString(filePath, csvLine, StandardOpenOption.APPEND);
        IO.println("Success! Data saved to symptoms_log.csv.");

    } catch (Exception e) {
        IO.println("Error saving data. Make sure you have internet and file permissions.");
    }
}

String extractJsonValue(String json, String key) {
    int keyIndex = json.indexOf(key);
    if (keyIndex == -1) return "0";
    
    int startIndex = keyIndex + key.length();
    int endIndex = json.indexOf(",", startIndex);
    if (endIndex == -1) endIndex = json.indexOf("}", startIndex); // If it's the last item
    
    return json.substring(startIndex, endIndex).trim();
}