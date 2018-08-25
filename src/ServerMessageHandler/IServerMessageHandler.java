package ServerMessageHandler;

import ClientMessageHandler.IClientMessageObserver;

/**
 * A handler to server messages
 */
public interface IServerMessageHandler {

    /**
     * Handles a server message
     * @param message
     */
    public void HandleMessage(String message);
}
