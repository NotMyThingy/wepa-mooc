package wad.helloserver;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HelloServer {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);

        int requestCounter = 1;

        while (true) {
            // wait for a request from a client
            Socket socket = server.accept();
            System.out.println("Request: " + requestCounter);
            requestCounter++;

            // Read the request
            Scanner requestReader = new Scanner(socket.getInputStream());

            String request = requestReader.nextLine();

            if (request.contains("quit")) {
                break;
            }

            // Write the response
            PrintWriter responseWriter = new PrintWriter(socket.getOutputStream());
            responseWriter.println("HTTP/1.1 301 Moved Permanently");
            responseWriter.println("Location: http://localhost:8080/");
            responseWriter.println();
            responseWriter.flush();

            requestReader.close();
            responseWriter.close();
            socket.close();
        }

        server.close();
    }
}
