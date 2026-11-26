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
        int choice = Integer.parseInt(input);

        if (choice == 1) {
            IO.println("Logging data... (coming soon)");
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