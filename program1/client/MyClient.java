import java.io.*;
import java.net.*;

public class MyClient {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java MyClient <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        
        try {
            // Connect to server
            System.out.println("Connecting to server on port " + port);
            Socket socket = new Socket("localhost", port);
            System.out.println("Connected to server");
            
            // Setup streams
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            // Read HTTP response headers
            System.out.println("Reading response from server...");
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                System.out.println("Header: " + line);
            }
            
            // Read and save content
            System.out.println("Receiving file content...");
            try (PrintWriter fileWriter = new PrintWriter("client_file.txt")) {
                while ((line = in.readLine()) != null) {
                    fileWriter.println(line);
                }
            }
            
            System.out.println("File received and saved to client_file.txt");
            
            // Close connection
            socket.close();
            System.out.println("Connection closed");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}