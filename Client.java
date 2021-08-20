import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    //Выносим эти два объекта на уровень класса, чтобы ими пользовались другие функции
    Scanner in;
    PrintStream out;
    ChatServer server;

    public Client(Socket socket, ChatServer server){
        this.socket = socket;
        this.server = server;

        // запускаем поток
       new Thread(this).start();
    }


    void receive(String message){
        //отправка от этого клиента удаленному клиенту(например putty)
        out.println(message);
    }

    @Override
    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to our chat!");
            String input = in.nextLine();
            while (!input.equals("bye")) {
                server.sendAll(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
