package ServerMessageHandler;

/**
 * A handler to LEAVED message
 */
public class LeavedMessageHandler implements IServerMessageHandler {
    private static final int LEAVED_COMMAND_NUM_OF_PARAMS = 2;
    private static final int USERNAME_INDEX = 1;
    private IServerMessageObserver _serverMessageObserver;

    public LeavedMessageHandler(IServerMessageObserver serverMessageObserver) {
        _serverMessageObserver = serverMessageObserver;
    }
    
    @Override
    public void HandleMessage(String message) {
        String[] parsedCommand = message.split("\\s+");
        if (parsedCommand.length != LEAVED_COMMAND_NUM_OF_PARAMS) {
            System.out.println("Failed to parse leaved command from server with message: " + message);
            return;
        }

        String leavedUsername = parsedCommand[USERNAME_INDEX];

        System.out.println("Leaved " + leavedUsername);
        _serverMessageObserver.OnLeavedClient(leavedUsername.trim());
    }
}
