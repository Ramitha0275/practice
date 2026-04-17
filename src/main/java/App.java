import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class App {
    // The path where the data will be stored inside the Kubernetes volume
    private static final String FILE_PATH = "/data/inventory.txt";

    public static void main(String[] args) {
        Map<String, Integer> inventory = loadInventory();

        // Sample Input (In a real app, this comes from a request)
        String itemName = "Sensors";
        int quantity = 100;

        // Version 3 Logic: Update and then Save to Disk
        inventory.put(itemName, quantity);
        saveInventory(inventory);

        System.out.println("V3: Data saved to persistent storage.");
        System.out.println("Current Inventory: " + inventory);
    }

    private static void saveInventory(Map<String, Integer> inventory) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static Map<String, Integer> loadInventory() {
        Map<String, Integer> inventory = new HashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return inventory;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                inventory.put(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (IOException e) {
            System.out.println("Error loading data.");
        }
        return inventory;
    }
}