import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;

    ChatServer() throws IOException{

        // создаем серверный сокет на порту 1234
        serverSocket = new ServerSocket(1234);
    }

    // рассылка текста по всем клиентам
    void sendAll(String message){
        for(Client client: clients){
            client.receive(message);
        }
    }


    public void run(){
        while(true) {
            System.out.println("Waiting connection...");

        try{
            // ждем клиента из сети
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            // создаем клиента на своей стороне и добавляем его в ArrayList
            clients.add(new Client(socket,this));


        } catch (IOException e){
            e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }

}
