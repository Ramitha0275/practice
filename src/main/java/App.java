import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class App {
    // Path for Kubernetes persistent volume
    private static final String FILE_PATH = "/data/inventory.txt";

    public static void main(String[] args) throws IOException {
        // Start server on port 8080 (Matches your deployment.yaml containerPort)
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // 1. Load current data
                Map<String, Integer> inventory = loadInventory();
                
                // 2. Update logic (Simulating an update)
                inventory.put("Sensors", 100);
                saveInventory(inventory);

                // 3. Send HTTP Response
                String response = "<html><body>" +
                                  "<h1>Inventory System V3</h1>" +
                                  "<p>Status: <b>Data Saved to /data/inventory.txt</b></p>" +
                                  "<p>Current Inventory: " + inventory + "</p>" +
                                  "</body></html>";
                
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        System.out.println("Server started on port 8080...");
        server.setExecutor(null); 
        server.start();
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
                if (parts.length == 2) {
                    inventory.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        // This return statement was missing in your previous build!
        return inventory; 
    }
}