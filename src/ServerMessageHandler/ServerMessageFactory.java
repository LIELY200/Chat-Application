package ServerMessageHandler;


/**
 * A factory pattern of server message handler
 */
public class ServerMessageFactory {
    private IServerMessageObserver _serverMessageObserver;
    public ServerMessageFactory(IServerMessageObserver serverMessageObserver) {
        _serverMessageObserver = serverMessageObserver;
    }

    /**
     * Get a server message handler by message
     * @param rawServerMessage from the server
     * @return a handler to the server message
     */
    public IServerMessageHandler GetServerMessageHandler(String rawServerMessage) {
        String command = parseCommandFromMessage(rawServerMessage);
        switch (command) {
            case "JOINED":
                return new JoinedMessageHandler(_serverMessageObserver);
            case "LEAVED":
                return new LeavedMessageHandler(_serverMessageObserver);
            case "MSG":
                return new IncomingMessageHandler(_serverMessageObserver);
            case "ONLINE":
                return new OnlineMessageHandler(_serverMessageObserver);
            default:
                System.out.println("UNKNOWN MESSAGE");
                return null;
        }
    }

    private static String parseCommandFromMessage(String clientMessage) {
        // Split by space
        String[] parsedCommand = clientMessage.split("\\s+");
        return parsedCommand[0];
    }
}
