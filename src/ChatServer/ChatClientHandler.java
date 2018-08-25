package ChatServer;

import ClientMessageHandler.IClientMessageObserver;
import ClientMessageHandler.ClientMessageHandlerFactory;
import ClientMessageHandler.IClientMessageHandler;

import java.io.IOException;

/**
 * This class handles a single chat client in the server
 * It implements runnable to perfom its job asynchronously 
 */
public class ChatClientHandler implements Runnable {
    private ChatClient _client;
    private ChatClientsManager _chatClientsManager;
    private ClientMessageHandlerFactory _clientMessageFactory;

    /**
     * A constructor to chat client handler
     * @param client a client to handle
     * @param chatClientsManager a manager instance of the clients
     * @param clientMessageObserver an observer to client messages
     */
    public ChatClientHandler(ChatClient client, ChatClientsManager chatClientsManager, IClientMessageObserver clientMessageObserver) {
        _client = client;
        _chatClientsManager = chatClientsManager;
        _clientMessageFactory = new ClientMessageHandlerFactory(client, chatClientsManager, clientMessageObserver);
    }

    @Override
    public void run() {
        boolean clientConnected = true;
        while (clientConnected) {
            try {
                String clientMessage = _client.readMessage();
                System.out.println("RECV " + clientMessage);
                IClientMessageHandler handler = _clientMessageFactory.GetClientMessageHandler(clientMessage);
                if (handler != null) {
                    handler.HandleMessage(clientMessage);
                }
            } catch (ClientDisconnectedException ex) {
            	// This happens when a client disconnected by clicking on "disconnect" button.
                clientConnected = false;
            } catch (IOException ex) {
            	// This happens when a client disconnected by closing the client application.
                _chatClientsManager.RemoveClient(_client);
                clientConnected = false;
            }
        }
    }
}
