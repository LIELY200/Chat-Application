package ClientMessageHandler;

import ChatServer.ChatClient;
import ChatServer.ChatClientsManager;
import ChatServer.ServerConfiguration;

/**
 * A handler to GETONLINE message
 */
public class GetOnlineMessageHandler implements IClientMessageHandler {
    private static final int GET_ONLINE_COMMAND_NUM_OF_PARAMS = 1;
    
    private ChatClient _client;
    private ChatClientsManager _chatClientManager;
    private IClientMessageObserver _clientMessageObserver;


    public GetOnlineMessageHandler(ChatClient client, ChatClientsManager chatClientsManager, IClientMessageObserver clientMessageObserver) {
        _client = client;
        _chatClientManager = chatClientsManager;
        _clientMessageObserver = clientMessageObserver;
    }

    @Override
    public void HandleMessage(String message) {
        String[] parsedCommand = message.split("\\s+");
        if (parsedCommand.length != GET_ONLINE_COMMAND_NUM_OF_PARAMS) {
            System.out.println("Failed to parse getonline command from client with message: " + message);
            return;
        }

        String[] onlineUsers = _chatClientManager.GetOnlineUsers();

        _chatClientManager.SendMessageToClient(_client,
                String.format(ServerConfiguration.ONLINE_COMMAND_FORMAT, String.join(" ", onlineUsers)));
        
        _clientMessageObserver.OnUserAskForOnlineList(_client.Username());
    }
}
