package server.models;

import server.models.http.HttpRequest;
import server.models.http.HttpResponse;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            StringBuilder requestText = new StringBuilder();
            long time = System.currentTimeMillis();
            new Thread(() -> {
                while (scanner.hasNext()) {
                    requestText.append(scanner.nextLine()).append("\n");
                }
            }).start();
            while (time + 10 > System.currentTimeMillis()) {
            }

            HttpRequest request = new HttpRequest(requestText.toString());
            boolean matches = false;

            if (request.url != null)
                for (URL url : urls) {
                    if (url.matches(request.url)) {
                        matches = true;
                        HttpResponse response = url.viewFunction.apply(request);

                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                        out.print(response);
                        out.flush();

                        break;
                    }
                }

            if (!matches) {
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.print(HttpResponse.notFound(request));
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
