package ChatServer;

/**
 * Exception of client diconnection
 */
public class ClientDisconnectedException extends Exception {
    public ClientDisconnectedException(String message) {
        super(message);
    }
}
