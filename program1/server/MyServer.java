import java.io.*;
import java.net.*;

public class MyServer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java MyServer <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = null;
        
        try {
            // Create server socket
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            
            while (true) {
                System.out.println("Waiting for client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + 
                    clientSocket.getInetAddress().getHostAddress());
                
                // Setup streams
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                
                // Read the file to send
                System.out.println("Reading file to send...");
                StringBuilder content = new StringBuilder();
                try (BufferedReader fileReader = new BufferedReader(
                        new FileReader("server_file.txt"))) {
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                }
                
                // Send HTTP response
                System.out.println("Sending file to client...");
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/plain");
                out.println("Content-Length: " + content.length());
                out.println();
                out.println(content.toString());
                out.flush();
                
                // Close connection
                System.out.println("File sent successfully. Closing connection.");
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing server socket: " + e.getMessage());
            }
        }
    }
}