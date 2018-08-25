package ClientMessageHandler;

import ChatServer.ChatClient;
import ChatServer.ChatClientsManager;
import ChatServer.ServerConfiguration;

/**
 * A handler to SEND message
 */
public class SendMessageHandler implements IClientMessageHandler {
    private static final int SEND_COMMAND_NUM_OF_PARAMS = 2;
    private static final int COMMAND_INDEX = 0;
    private ChatClient _client;
    private ChatClientsManager _chatClientManager;
    
    private IClientMessageObserver _clientMessageObserver;

    public SendMessageHandler(ChatClient client, ChatClientsManager chatClientsManager, IClientMessageObserver clientMessageObserver) {
        _client = client;
        _chatClientManager = chatClientsManager;
        _clientMessageObserver = clientMessageObserver;
    }

    @Override
    public void HandleMessage(String message) {
        String[] parsedCommand = message.split("\\s+");
        if (parsedCommand.length < SEND_COMMAND_NUM_OF_PARAMS) {
            System.out.println("Failed to parse send command from client with message: " + message);
            return;
        }

        int startOfMessageIndex = parsedCommand[COMMAND_INDEX].length() + 1;
        String clientMessage = message.substring(startOfMessageIndex);
        _chatClientManager.SendMessageToAllClients(String.format(ServerConfiguration.MSG_COMMAND_FORMAT, _client.Username(), clientMessage));
        
        _clientMessageObserver.OnUserSendMessageToAll(_client.Username(), clientMessage);
    }

}


