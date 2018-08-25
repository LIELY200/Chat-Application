package ServerMessageHandler;

/**
 * A handler to MSG message
 */
public class IncomingMessageHandler implements IServerMessageHandler {
    private static final int MSG_COMMAND_NUM_OF_PARAMS = 3;
    private static final int COMMAND_INDEX = 0;
    private static final int USERNAME_INDEX = 1;
    private IServerMessageObserver _serverMessageObserver;

    public IncomingMessageHandler(IServerMessageObserver serverMessageObserver) {
        _serverMessageObserver = serverMessageObserver;
    }
    
    @Override
    public void HandleMessage(String message) {
        String[] parsedCommand = message.split("\\s+");
        if (parsedCommand.length < MSG_COMMAND_NUM_OF_PARAMS) {
            System.out.println("Failed to parse joined command from server with message: " + message);
            return;
        }

        String username = parsedCommand[USERNAME_INDEX];
        int startMessagePosition = parsedCommand[COMMAND_INDEX].length() + 1 + parsedCommand[USERNAME_INDEX].length() + 1;
        String incomingMessage = message.substring(startMessagePosition);

        System.out.println(username + ": " + incomingMessage);
        _serverMessageObserver.OnIncomingMessage(username, incomingMessage.trim());
    }
}
