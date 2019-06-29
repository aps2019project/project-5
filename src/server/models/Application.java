package server.models;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Application {
    public int port;
    public boolean isNecessary = true;
    public ArrayList<URL> urls = new ArrayList<>();

    public void start() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();

                    // TODO: handle request!

                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (isNecessary)
                    System.exit(1);
            }
        }).start();
    }
}
