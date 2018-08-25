package ClientMessageHandler;

import ChatServer.ClientDisconnectedException;

/**
 * Interface to message handler
 */
public interface IClientMessageHandler {
    
    /**
     * Handles a message from the client
     * @param message from the client
     * @throws ClientDisconnectedException when client disconnected
     */
    public void HandleMessage(String message) throws ClientDisconnectedException;
}