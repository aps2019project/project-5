package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        Socket socket = serverSocket.accept();

        Scanner scanner = new Scanner(socket.getInputStream());
        String method = scanner.next();
        String requestedURL = scanner.next();

        StringBuilder response = new StringBuilder();

        int status = 404;
        if (requestedURL.equals("/"))
            status = 200;

        response.append("HTTP/1.1 ").append(status).append(" OK\n");
        response.append("Content-type: text/html\n");

        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(response.toString());
        out.flush();

        socket.close();
    }
}
