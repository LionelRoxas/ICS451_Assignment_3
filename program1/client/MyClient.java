/**
 * NAME       : Lionel Derrick S. Roxas
 * HOMEWORK   : 3
 * CLASS      : ICS 451
 * INSTRUCTOR : Ravi Narayan
 * DATE       : February 13, 2025
 * FILE       : MyClient.java
 * DESCRIPTION: A simple HTTP client that receives a text file from the server
 **/

import java.io.*;
import java.net.*;

public class MyClient {
    /**
     * Main client operation to connect and receive file
     * @param args Command line arguments - expects port number
     */
    public static void main(String[] args) {
        // Variables for connection
        Socket socket = null;
        int portNumber = 0;
        
        // Validate command line arguments
        if (args.length != 1) {
            System.out.println("Usage: java MyClient <port>");
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
            // Connect to server
            System.out.println("Connecting to server on port " + portNumber);
            socket = new Socket("localhost", portNumber);
            System.out.println("Connected to server");
            
            // Handle the connection
            handleServerConnection(socket);
            
        } catch (IOException exception) {
            System.out.println("Error: " + exception.getMessage());
        } finally {
            closeConnection(socket);
        }
    }
    
    /**
     * Handles the connection with the server
     * @param socket The connected socket
     */
    private static void handleServerConnection(Socket socket) 
            throws IOException {
        // Setup streams
        BufferedReader inputReader = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
        
        // Read HTTP headers
        System.out.println("Reading response from server...");
        readHttpHeaders(inputReader);
        
        // Read and save content
        System.out.println("Receiving file content...");
        saveContent(inputReader);
        
        System.out.println("File received and saved to client_file.txt");
    }
    
    /**
     * Reads HTTP headers from the server
     * @param reader The BufferedReader to read from
     */
    private static void readHttpHeaders(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            System.out.println("Header: " + line);
        }
    }
    
    /**
     * Saves the received content to a file
     * @param reader The BufferedReader to read from
     */
    private static void saveContent(BufferedReader reader) throws IOException {
        try (PrintWriter fileWriter = new PrintWriter("client_file.txt")) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileWriter.println(line);
            }
        }
    }
    
    /**
     * Closes the socket connection safely
     * @param socket The socket to close
     */
    private static void closeConnection(Socket socket) {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
                System.out.println("Connection closed");
            } catch (IOException exception) {
                System.out.println("Error closing connection: " + 
                    exception.getMessage());
            }
        }
    }
}