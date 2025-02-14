# Simple HTTP Client-Server File Transfer

This project implements a basic HTTP client-server application in Java for transferring files. The server sends a text file to the client upon connection, and the client saves the received file locally.

## Directory Structure
```
program1/
├── client
│   ├── client_file.txt
│   ├── client_log.txt
│   └── MyClient.java
├── server
│   ├── MyServer.java
│   ├── server_file.txt
│   └── server_log.txt
└── status.txt
```

## Requirements
- Java Development Kit (JDK) 8 or higher
- Command-line terminal
- The server_file.txt should be 5-10KB in size

## Compilation Instructions

1. Navigate to the server directory and compile the server:
```bash
cd program1/server
javac MyServer.java
```

2. Navigate to the client directory and compile the client:
```bash
cd ../client
javac MyClient.java
```

## Running the Programs

### Starting the Server
1. Navigate to the server directory
2. Run the server with a port number:
```bash
java MyServer <port_number>
Example: java MyServer 30000
```

### Testing with Telnet
To verify the server is working correctly:
```bash
telnet localhost <port_number>
Example: telnet localhost 30000
```

### Running the Client
1. Navigate to the client directory
2. Run the client with the same port number used for the server:
```bash
java MyClient <port_number>
Example: java MyClient 30000
```

## Program Features
- Single-threaded server (handles one connection at a time)
- Uses Java Socket class for network communication
- Implements proper logging throughout program execution
- Uses relative file paths
- Command-line port number specification
- Proper error handling

## File Descriptions
- `MyServer.java`: Server implementation that sends the file
- `MyClient.java`: Client implementation that receives the file
- `server_file.txt`: Source file to be transferred (5-10KB)
- `client_file.txt`: Destination file where received data is saved
- `server_log.txt`: Log of server operations
- `client_log.txt`: Log of client operations
- `status.txt`: Current status of the program

## Error Handling
- The program handles various exceptions including:
  - Port already in use (BindException)
  - Connection failures
  - File I/O errors
- Both client and server provide detailed error messages

## Testing
1. Start the server first with a port number
2. Test using telnet to verify server functionality
3. Run the client program to test full file transfer
4. Verify the received file in client_file.txt matches server_file.txt

## Common Issues and Solutions
1. "Address already in use" error:
   - Choose a different port number
   - Wait a few minutes for the port to be released
   - Check if another process is using the port

2. Connection refused:
   - Verify the server is running
   - Confirm you're using the correct port number
   - Check if any firewall is blocking the connection

## Notes
- The server must be running before starting the client
- Both programs require a port number as a command-line argument
- The server will continue running until manually terminated
- The client will exit after receiving the file
