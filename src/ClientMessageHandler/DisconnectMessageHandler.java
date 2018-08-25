package ClientMessageHandler;

import ChatServer.ChatClient;
import ChatServer.ChatClientsManager;
import ChatServer.ClientDisconnectedException;

import java.io.IOException;

/**
 * A handler to DISCONNECT message
 */
public class DisconnectMessageHandler implements IClientMessageHandler {
    private static final int DISCONNECT_COMMAND_NUM_OF_PARAMS = 1;
    
    private ChatClient _client;
    private ChatClientsManager _chatClientManager;
    private IClientMessageObserver _clientMessageObserver;

    public DisconnectMessageHandler(ChatClient client, ChatClientsManager chatClientsManager, IClientMessageObserver clientMessageObserver) {
        _client = client;
        _chatClientManager = chatClientsManager;
        _clientMessageObserver = clientMessageObserver;
    }

    @Override
    public void HandleMessage(String message) throws ClientDisconnectedException {
        String[] parsedCommand = message.split("\\s+");
        if (parsedCommand.length != DISCONNECT_COMMAND_NUM_OF_PARAMS) {
            System.out.println("Failed to parse disconnect command from client with message: " + message);
            return;
        }

        try {
            _client.Disconnect();
        } catch (IOException ex) {
            // If we failed to disconnect it means that the client has already disconnected
            // Therefore we do nothing here
        }

        _chatClientManager.RemoveClient(_client);

        // When client is disconnected from the server
        // we want to interrupt its logic immediately, to free all its resources.
        throw new ClientDisconnectedException("Client " + _client.Username() + " Disconnected");
    }
}
