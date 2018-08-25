/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientMessageHandler;

/**
 * Client message observer, notify when client sent a message
 */
public interface IClientMessageObserver {

    /**
     * When a user connected
     * @param username of the connected client
     */
    public void OnUserConnected(String username);

    /**
     * When a client disconnected
     * @param username of the disconnected client
     */
    public void OnUserDisconnected(String username);

    /**
     * When a client asked for online list
     * @param username of the client who asked for the list
     */
    public void OnUserAskForOnlineList(String username);

    /**
     * When a client sent a message to all of the users
     * @param username of the client
     * @param message the client sent
     */
    public void OnUserSendMessageToAll(String username, String message);

    /**
     * When a client sent a message to a specific user
     * @param username of the client who sent the message
     * @param targetUsername of the target user
     * @param message the client sent
     */
    public void OnUserSendMessage(String username, String targetUsername, String message);  
}
