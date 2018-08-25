package ClientMessageHandler;

import ChatServer.ChatClient;
import ChatServer.ChatClientsManager;

/**
 * A handler to CONNECT message
 */
public class ConnectMessageHandler implements IClientMessageHandler {
    private static final int CONNECT_COMMAND_NUM_OF_PARAMS = 2;
    private static final int USERNAME_INDEX = 1;
    
    private ChatClient _client;
    private ChatClientsManager _chatClientManager;
    private IClientMessageObserver _clientMessageObserver;

    public ConnectMessageHandler(ChatClient client, ChatClientsManager chatClientsManager, IClientMessageObserver clientMessageObserver) {
        _client = client;
        _chatClientManager = chatClientsManager;
        _clientMessageObserver = clientMessageObserver;
    }

    @Override
    public void HandleMessage(String message) {
        String[] parsedCommand = message.split("\\s+");
        if (parsedCommand.length != CONNECT_COMMAND_NUM_OF_PARAMS) {
            System.out.println("Failed to parse connect command from client with message: " + message);
            return;
        }

        String username = parsedCommand[USERNAME_INDEX];
        _client.SetUsername(username);

        _chatClientManager.AddClient(_client);
    }
}
