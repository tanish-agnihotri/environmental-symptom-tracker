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

        // prints the raw JSON to make sure API works
         IO.println("Raw API Response: " + response.body());

    } catch (Exception e) {
        IO.println("Error connecting to the weather API. Please check your internet connection.");
    }
}