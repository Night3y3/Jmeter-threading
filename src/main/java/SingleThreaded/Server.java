package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public void run() throws IOException {
    int port = 8010;
    ServerSocket socket = new ServerSocket(port);
    socket.setSoTimeout(10000);
    while (true) {
      try {
        System.out.println("Server is listening on port : " + port);
        Socket acceptedConnection = socket.accept();
        System.out.println("Connection established with client " + acceptedConnection.getRemoteSocketAddress());
        PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream());
        // PrintWriter converts string to bits in the output stream which is from
        // server to client
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
        // BufferedReader converts bits to string in the input stream which is from
        // client to server
        toClient.println("Hello from the server");
        toClient.flush();
        String line = fromClient.readLine();
        System.out.println("Message from the Client : " + line);
        
        fromClient.close();
        acceptedConnection.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws Exception {
    // Create a new server
    Server server = new Server();
    try {
      server.run();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}