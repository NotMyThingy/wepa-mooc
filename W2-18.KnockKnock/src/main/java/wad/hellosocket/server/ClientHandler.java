package wad.hellosocket.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private final KnockKnockServerListener listener;

    public ClientHandler(Socket clientSocket, KnockKnockServerListener listener) {
        this.clientSocket = clientSocket;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            handleRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest() throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String knockKnock = "Knock knock!";
        out.println(knockKnock);
        if (listener != null) {
            listener.serverMessageSent(knockKnock);
        }

        Scanner scanner = new Scanner(clientSocket.getInputStream());

        int index = new Random().nextInt(WoodenLeg.NAMES.length);

        waitForMessage(scanner, out, "Who's there?", WoodenLeg.NAMES[index],
                "You are supposed to ask: \"Who's there?\"");

        String expectedMessage = WoodenLeg.NAMES[index] + " who?";
        waitForMessage(scanner, out, expectedMessage, WoodenLeg.ANSWERS[index] + " Bye.",
                "You are supposed to ask: \"" + expectedMessage + "\"");

        out.close();
        scanner.close();
        clientSocket.close();
    }

    private void waitForMessage(Scanner scanner, PrintWriter writer,
            String expectedMessage, String answer, String hint) {

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (listener != null) {
                listener.clientMessageReceived(line);
            }

            if (line.equalsIgnoreCase(expectedMessage)) {
                writer.println(answer);
                if (listener != null) {
                    listener.serverMessageSent(answer);
                }
                break;
            } else {
                writer.println(hint);
                if (listener != null) {
                    listener.serverMessageSent(hint);
                }
            }
        }
    }
}
