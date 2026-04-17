import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;

public class App {
    private static final String FILE_PATH = "/data/inventory.txt";

    public static void main(String[] args) throws IOException {
        // Create a server on port 8080 (matches your Docker/K8s config)
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                Map<String, Integer> inventory = loadInventory();
                
                // Update inventory logic
                inventory.put("Sensors", 100);
                saveInventory(inventory);

                String response = "<h1>Inventory V3</h1><p>Data saved: " + inventory + "</p>";
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

    // Keep your loadInventory and saveInventory methods exactly as they were below...
    private static void saveInventory(Map<String, Integer> inventory) { /* same as before */ }
    private static Map<String, Integer> loadInventory() { /* same as before */ }
}