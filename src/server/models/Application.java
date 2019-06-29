package server.models;

import javafx.concurrent.ScheduledService;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    public int port;
    public boolean isNecessary = true;
    public ArrayList<URL> urls = new ArrayList<>();

    public void start() {
        System.out.println("application started");
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Server port: " + serverSocket.getLocalPort());
                while (true) {
                    Socket socket = serverSocket.accept();


                    handleRequest(socket);


                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (isNecessary)
                    System.exit(1);
            }
        }).start();
    }

    protected void handleRequest(Socket socket) {
        try {
            DataInputStream inputStream = new DataInputStream(System.in);
            Scanner scanner = new Scanner(socket.getInputStream());
            StringBuilder requestText = new StringBuilder();
            while (scanner.hasNext()) {
                String line = scanner.nextLine() + "\n";
                requestText.append(line);
                System.out.print(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
