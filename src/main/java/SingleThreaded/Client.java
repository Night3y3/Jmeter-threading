package SingleThreaded;

import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client{
  public void run() throws UnknownHostException, IOException{
    int port = 8010;
    InetAddress address = InetAddress.getByName("localhost");
    Socket socket = new Socket(address,port);
    PrintWriter toServer = new PrintWriter(socket.getOutputStream());
    // PrintWriter converts string to bits in the output stream whichh is from client to server
    BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    // BufferedReader converts bits to string in the input stream which is from server to client
    toServer.println("Hello from the client");
    
    String line = fromServer.readLine();
    System.out.println("Response from the server is : "+line);
    toServer.flush();
    fromServer.close();
    socket.close();
  }

  public static void main(String[] args) throws Exception{
    
    try{
      Client client = new Client();
      client.run();
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }
}