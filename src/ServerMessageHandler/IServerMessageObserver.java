/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerMessageHandler;

/**
 * Server messages observer
 */
public interface IServerMessageObserver {

    /**
     * When a client received a message
     * @param username of the client who sent the message
     * @param message
     */
    public void OnIncomingMessage(String username, String message);

    /**
     * When a client joined the chat
     * @param username
     */
    public void OnJoinedClient(String username);

    /**
     * When a client leaved the chat
     * @param username
     */
    public void OnLeavedClient(String username);

    /**
     * When information on active users arrived
     * @param onlineUsers list of active user names
     */
    public void OnOnlineClientList(String[] onlineUsers);    
}
