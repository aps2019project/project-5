package server.models;

import server.models.http.HttpRequest;
import server.models.http.HttpResponseText;

import java.io.IOException;
import java.io.PrintWriter;
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

    }
}
