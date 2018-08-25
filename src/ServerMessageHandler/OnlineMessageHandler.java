package ServerMessageHandler;

import java.util.Arrays;

/**
 * A handler to ONLINE message
 */
public class OnlineMessageHandler implements IServerMessageHandler {
    private static final int ONLINE_COMMAND_NUM_OF_PARAMS = 1;
    private static final int COMMAND_INDEX = 0;
    private IServerMessageObserver _serverMessageObserver;

    public OnlineMessageHandler(IServerMessageObserver serverMessageObserver) {
        _serverMessageObserver = serverMessageObserver;
    }
    
    @Override
    public void HandleMessage(String message) {
        String[] parsedCommand = message.split("\\s+");
        if (parsedCommand.length < ONLINE_COMMAND_NUM_OF_PARAMS) {
            System.out.println("Failed to parse leaved command from server with message: " + message);
            return;
        }

        System.out.println("Online users are " + message.substring(parsedCommand[COMMAND_INDEX].length() + 1));
        _serverMessageObserver.OnOnlineClientList(Arrays.copyOfRange(parsedCommand, 1, parsedCommand.length));
    }
}
