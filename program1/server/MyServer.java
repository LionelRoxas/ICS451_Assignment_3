/**
 * NAME       : Lionel Derrick S. Roxas
 * HOMEWORK   : 3
 * CLASS      : ICS 451
 * INSTRUCTOR : Ravi Narayan
 * DATE       : February 13, 2025
 * FILE       : MyServer.java
 * DESCRIPTION: A simple HTTP server that sends a text file to connected clients
 **/

import java.io.*;
import java.net.*;

public class MyServer {
    /**
     * Handles the server operation of accepting connections and sending files
     * @param args Command line arguments - expects port number
     */
    public static void main(String[] args) {
        // Variables to hold server components
        ServerSocket serverSocket = null;
        int portNumber = 0;
        boolean isRunning = true;
        
        // Validate command line arguments
        if (args.length != 1) {
            System.out.println("Usage: java MyServer <port>");
            return;
        }
        
        // Parse port number
        try {
            portNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException exception) {
            System.out.println("Error: Port must be a number");
            return;
        }
        
        try {
            // Create server socket
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started on port " + portNumber);
            
            // Main server loop
            while (isRunning) {
                System.out.println("Waiting for client connection...");
                Socket clientSocket = serverSocket.accept();
                handleClientConnection(clientSocket);
            }
        } catch (IOException exception) {
            System.out.println("Error: " + exception.getMessage());
        } finally {
            closeServerSocket(serverSocket);
        }
    }
    
    /**
     * Handles an individual client connection
     * @param clientSocket The socket connected to the client
     */
    private static void handleClientConnection(Socket clientSocket) {
        try {
            System.out.println("Client connected from " + 
                clientSocket.getInetAddress().getHostAddress());
            
            // Setup streams
            PrintWriter outputWriter = new PrintWriter(
                clientSocket.getOutputStream(), true);
            
            // Read file content
            String fileContent = readFileContent();
            
            // Send HTTP response
            System.out.println("Sending file to client...");
            sendHttpResponse(outputWriter, fileContent);
            
            // Cleanup
            System.out.println("File sent successfully. Closing connection.");
            clientSocket.close();
        } catch (IOException exception) {
            System.out.println("Error handling client: " + exception.getMessage());
        }
    }
    
    /**
     * Reads content from the server file
     * @return The content of the file as a string
     */
    private static String readFileContent() throws IOException {
        System.out.println("Reading file to send...");
        StringBuilder content = new StringBuilder();
        
        try (BufferedReader fileReader = new BufferedReader(
                new FileReader("server_file.txt"))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        
        return content.toString();
    }
    
    /**
     * Sends HTTP response with file content
     * @param writer The PrintWriter to send the response
     * @param content The content to send
     */
    private static void sendHttpResponse(PrintWriter writer, String content) {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/plain");
        writer.println("Content-Length: " + content.length());
        writer.println();
        writer.println(content);
        writer.flush();
    }
    
    /**
     * Closes the server socket safely
     * @param serverSocket The ServerSocket to close
     */
    private static void closeServerSocket(ServerSocket serverSocket) {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException exception) {
                System.out.println("Error closing server: " + 
                    exception.getMessage());
            }
        }
    }
}