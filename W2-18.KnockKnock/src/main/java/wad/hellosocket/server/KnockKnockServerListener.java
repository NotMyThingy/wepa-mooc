package wad.hellosocket.server;

public interface KnockKnockServerListener {

    void serverMessageSent(String message);

    void clientMessageReceived(String message);
}
