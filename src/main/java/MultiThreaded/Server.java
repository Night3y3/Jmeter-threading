import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer(){
        return (clientSocket)->{
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the server");
                toClient.flush();
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("From client " + clientSocket.getLocalSocketAddress() + " " + fromClient.readLine());
                fromClient.close();
                clientSocket.close();

            }catch (IOException ex){
                ex.printStackTrace();
            }
        };
    }

    public static void main(String[] args) throws Exception {
        int port = 8010;
        Server server = new Server();
        try {
            ServerSocket socket = new ServerSocket(port);
            socket.setSoTimeout(10000);
            System.out.println("Server is listening on port "+ port);
            while (true){
                Socket acceptedConnection = socket.accept();
                Thread thread = new Thread(()->server.getConsumer().accept(acceptedConnection));
                thread.start();
            }

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}