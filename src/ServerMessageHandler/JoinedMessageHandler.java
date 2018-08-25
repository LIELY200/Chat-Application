package ServerMessageHandler;

/**
 * A handler to JOINED message
 */
public class JoinedMessageHandler implements IServerMessageHandler {
    private static final int JOINED_COMMAND_NUM_OF_PARAMS = 2;
    private IServerMessageObserver _serverMessageObserver;
    
    public JoinedMessageHandler(IServerMessageObserver serverMessageObserver) {
        _serverMessageObserver = serverMessageObserver;
    }

    @Override
    public void HandleMessage(String message) {
        String[] parsedCommand = message.split("\\s+");
        if (parsedCommand.length != JOINED_COMMAND_NUM_OF_PARAMS) {
            System.out.println("Failed to parse joined command from server with message: " + message);
            return;
        }

        String joinedUsername = parsedCommand[1];

        System.out.println("Joined " + joinedUsername);
        _serverMessageObserver.OnJoinedClient(joinedUsername.trim());
    }


}
