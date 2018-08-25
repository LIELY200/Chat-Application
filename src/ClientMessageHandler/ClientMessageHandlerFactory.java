package ClientMessageHandler;

import ChatServer.ChatClient;
import ChatServer.ChatClientsManager;

/**
 * A factory pattern for client messages handler
 */
public class ClientMessageHandlerFactory {
    private ChatClient _client;
    private ChatClientsManager _chatClientManager;
    private IClientMessageObserver _clientMessageObserver;
    
    private static final int COMMAND_INDEX_IN_MESSAGE = 0;

    public ClientMessageHandlerFactory(ChatClient client, ChatClientsManager chatClientsManager, IClientMessageObserver clientMessageObserver) {
        _client = client;
        _chatClientManager = chatClientsManager;
        _clientMessageObserver = clientMessageObserver;
    }

    /**
     * Get a client message handler for a message
     * @param rawClientMessage a message from the client
     * @return a handler to the message
     */
    public IClientMessageHandler GetClientMessageHandler(String rawClientMessage) {
        String command = parseCommandFromMessage(rawClientMessage);
        switch (command) {
            case "CONNECT":
                return new ConnectMessageHandler(_client, _chatClientManager, _clientMessageObserver);
            case "DISCONNECT":
                return new DisconnectMessageHandler(_client, _chatClientManager, _clientMessageObserver);
            case "SENDTO":
                return new SendToMessageHandler(_client, _chatClientManager, _clientMessageObserver);
            case "SEND":
                return new SendMessageHandler(_client, _chatClientManager, _clientMessageObserver);
            case "GETONLINE":
                return new GetOnlineMessageHandler(_client, _chatClientManager, _clientMessageObserver);
            default:
                System.out.println("UNKNOWN MESSAGE");
                return null;
        }
    }

    private static String parseCommandFromMessage(String clientMessage) {
        // Split by space
        String[] parsedCommand = clientMessage.split("\\s+");
        return parsedCommand[COMMAND_INDEX_IN_MESSAGE];
    }
}
