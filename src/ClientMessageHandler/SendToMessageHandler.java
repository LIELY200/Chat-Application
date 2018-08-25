package ClientMessageHandler;

import ChatServer.ChatClient;
import ChatServer.ChatClientsManager;
import ChatServer.ServerConfiguration;

/**
 * A handler to SENDTO message
 */
public class SendToMessageHandler implements IClientMessageHandler {
    private static final int SEND_TO_COMMAND_NUM_OF_PARAMS = 3;
    private static final int COMMAND_INDEX = 0;
    private static final int TARGET_USERNAME_INDEX = 1;
    
    private ChatClient _client;
    private ChatClientsManager _chatClientManager;
    
    private IClientMessageObserver _clientMessageObserver;

    public SendToMessageHandler(ChatClient client, ChatClientsManager chatClientsManager, IClientMessageObserver clientMessageObserver) {
        _client = client;
        _chatClientManager = chatClientsManager;
        _clientMessageObserver = clientMessageObserver;
    }

    @Override
    public void HandleMessage(String message) {
        String[] parsedCommand = message.split("\\s+");
        if (parsedCommand.length < SEND_TO_COMMAND_NUM_OF_PARAMS) {
            System.out.println("Failed to parse sendto command from client with message: " + message);
            return;
        }

        String targetUsername = parsedCommand[TARGET_USERNAME_INDEX];

        // We take a substring of the message since the message itself can contain spaces
        int startOfMessageIndex = parsedCommand[COMMAND_INDEX].length() + 1 + parsedCommand[TARGET_USERNAME_INDEX].length() + 1;
        String clientMessage = message.substring(startOfMessageIndex);

        // Since both the client who sent the message and the client who the message is directed to
        // need to display this message we send to both of them.
        _chatClientManager.SendMessageToClientByUsername(targetUsername,
                            String.format(ServerConfiguration.MSG_COMMAND_FORMAT, _client.Username(), clientMessage));

        // This is not mandatory to send to the client who sent the message, but it makes the life easier.
        _chatClientManager.SendMessageToClient(_client,
                            String.format(ServerConfiguration.MSG_COMMAND_FORMAT, _client.Username(), clientMessage));
        
        _clientMessageObserver.OnUserSendMessage(_client.Username(), targetUsername, clientMessage);
    }
}
