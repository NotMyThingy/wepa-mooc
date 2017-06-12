package wad.hellosocket;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Random;
import static org.junit.Assert.fail;
import org.junit.Test;
import wad.hellosocket.client.KnockKnockClient;
import wad.hellosocket.server.KnockKnockServer;
import wad.hellosocket.server.KnockKnockServerListener;

@Points("18")
public class HelloSocketTest {

    private final Random random = new Random();

    @Test(timeout = 10000)
    public void testKnockKnockClient() throws Exception {
        final int port = random.nextInt(10000) + 22345;

        PipedInputStream stdin = new PipedInputStream();
        final PrintWriter stdinWriter = new PrintWriter(new PipedOutputStream(stdin), true);
        System.setIn(stdin);

        final String[] name = {null};
        final boolean[] knockSent = {false};
        final boolean[] whosThereSent = {false};
        final boolean[] whoNameSent = {false};
        final boolean[] incorrectWhosThereReceived = {false};
        final boolean[] whosThereReceived = {false};
        final boolean[] incorrectWhoNameReceived = {false};
        final boolean[] whoNameReceived = {false};
        final String[] errorMessage = {null};

        KnockKnockServerListener listener = new KnockKnockServerListener() {
            public void serverMessageSent(String message) {
                System.err.println("Test output: Server: " + message);

                if (message.contains("supposed to ask")) {
                    if (!whosThereSent[0]) {
                        incorrectWhosThereReceived[0] = true;
                        stdinWriter.println("Who's there?");
                        whosThereSent[0] = true;
                    } else if (!whoNameSent[0]) {
                        incorrectWhoNameReceived[0] = true;
                        stdinWriter.println(name[0] + " who?");
                        whoNameSent[0] = true;
                    }
                    return;
                }

                if (message.equalsIgnoreCase("Knock knock!")) {
                    knockSent[0] = true;
                } else if (knockSent[0] && !whoNameSent[0]) {
                    name[0] = message;
                    stdinWriter.println("Pardon?");
                }
            }

            public void clientMessageReceived(String message) {
                System.err.println("Test output: Client: " + message);
                if (message.equalsIgnoreCase("What?")
                        || message.equalsIgnoreCase("Pardon?")) {
                    // ignore incorrect message used for testing
                    return;
                }

                if (!whosThereReceived[0]) {
                    String expected = "Who's there?";
                    if (!message.equalsIgnoreCase(expected)) {
                        errorMessage[0]
                                = "Message \"" + expected + "\" was expected, but client sent message: " + message;
                    }
                    whosThereReceived[0] = true;
                } else if (whosThereReceived[0]) {
                    String expected = name[0] + " who?";
                    if (!message.equalsIgnoreCase(expected)) {
                        errorMessage[0]
                                = "Message \"" + expected + "\" was expected, but client sent message: " + message;
                    }
                    whoNameReceived[0] = true;
                }
            }
        };

        KnockKnockServer server = new KnockKnockServer(port, listener);
        server.start();

        Thread.sleep(500);

        Thread clientThread = new Thread(new Runnable() {
            public void run() {
                try {
                    KnockKnockClient client = new KnockKnockClient(port);
                    client.start();
                } catch (NoSuchElementException e) {
                    errorMessage[0] = "The client is probably still waiting "
                            + "for a message from server, although it is supposed "
                            + "to stop after receiving \"Bye.\" from server. Exception: " + e.toString();
                } catch (Exception e) {
                    errorMessage[0] = "Client threw an unexpected exception: " + e.toString();
                }
            }
        });

        clientThread.start();

        // Aloita keskustelu
        stdinWriter.println("What?");

        long timeoutSeconds = 5;
        clientThread.join(timeoutSeconds * 1000);

        if (!clientThread.isAlive()) {
            // Wait for client handler to complete
            Thread.sleep(500);
        }

        if (errorMessage[0] != null) {
            fail(errorMessage[0]);
        }

        if (clientThread.isAlive()) {
            String reason = null;

            if (!whosThereReceived[0]) {
                reason = "Client did not send message \"Who's there?\".";
            } else if (!whoNameReceived[0]) {
                reason = "Client did not send message \"" + name[0] + " who?\".";
            } else {
                reason = "Client sent both messages required by the Knock-knock protocol, but it did not stop for some reason.";
            }

            fail("The client is still running after waiting for "
                    + timeoutSeconds + " seconds. " + reason);
        }
    }
}
