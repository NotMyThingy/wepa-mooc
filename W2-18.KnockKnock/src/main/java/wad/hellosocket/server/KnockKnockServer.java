package wad.hellosocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class KnockKnockServer extends Thread {

    private final ServerSocket serverSocket;
    private final KnockKnockServerListener listener;

    public KnockKnockServer(int port) throws IOException {
        this(port, null);
    }

    public KnockKnockServer(int port, KnockKnockServerListener listener) throws IOException {
        serverSocket = new ServerSocket(port);
        this.listener = listener;
    }

    @Override
    public void run() {
        // käsitellään vain yksi pyyntö, jotta ohjelman suoritus loppuu sen jälkeen
        try {
            Socket clientSocket = serverSocket.accept();

            ClientHandler clientHandler
                    = new ClientHandler(clientSocket, listener);
            clientHandler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
