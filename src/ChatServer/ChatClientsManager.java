package ChatServer;

import ClientMessageHandler.IClientMessageObserver;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * This class manages all the clients in the server
 * performs actions on them and handles the active clients
 */
public class ChatClientsManager {
    private List<ChatClient> _clients;
    private ThreadPool _thread_pool;
    
    private IClientMessageObserver _clientMessageObserver;

    /**
     * Constructor to chat clients manager
     */
    public ChatClientsManager() {
        _clients = new ArrayList<ChatClient>();
        _thread_pool = new ThreadPool(ServerConfiguration.MAX_NUMBER_OF_THREADS);
    }

    /**
     * Handles a client asynchronously
     * @param client a client socket to handle
     * @param clientMessageObserver an observer to client notifications
     */
    public void HandleAsync(Socket client, IClientMessageObserver clientMessageObserver) {
        ChatClient chatClient = new ChatClient(client);
        try {
            chatClient.Initialize();
        } catch (IOException e) {
            System.out.println("Failed to save client information due to network error");
            return;
        }

        _thread_pool.executeTask(
                    new ChatClientHandler(chatClient, this, clientMessageObserver)
        );
        _clientMessageObserver = clientMessageObserver;
    }

    /**
     * Removes a client from chat
     * @param client a client to remove
     */
    public synchronized void RemoveClient(ChatClient client) {
        System.out.println(client.Username() + " disconnected");
        
        if (_clientMessageObserver != null) {
            _clientMessageObserver.OnUserDisconnected(client.Username());	
        }
        
        _clients.remove(client);

        SendMessageToAllClients(String.format(ServerConfiguration.LEAVED_COMMAND_FORMAT, client.Username()));
    }

    /**
     * Adds a client to the chat
     * @param client a client to add
     */
    public synchronized void AddClient(ChatClient client) {
        System.out.println(client.Username() + " connected");
        _clientMessageObserver.OnUserConnected(client.Username());
        
        SendMessageToAllClients(String.format(ServerConfiguration.JOINED_COMMAND_FORMAT, client.Username()));

        _clients.add(client);
    }

    /**
     * Receive a client by user name
     * @param targetUsername a user name to find
     * @return the username if found and null when the user name is not found
     */
    public ChatClient GetClientByUsername(String targetUsername) {
        for (ChatClient client : _clients) {
            if (client.Username().equals(targetUsername)) {
                return client;
            }
        }

        return null;
    }

    /**
     * Sends a message to all clients
     * @param message a message to send
     */
    public synchronized void SendMessageToAllClients(String message) {
        for (ChatClient client: _clients) {
            try {
                client.sendMessage(message);
            } catch(IOException ex) {
                RemoveClient(client);
            }
        }
    }

    /**
     * Sends a message to client by user name
     * @param targetUsername a user name of client to send to
     * @param message a message to send
     */
    public synchronized void SendMessageToClientByUsername(String targetUsername, String message) {
        ChatClient targetClient = GetClientByUsername(targetUsername);
        if (targetClient != null) {
            SendMessageToClient(targetClient, message);
        }
    }

    /**
     * Sends a message to client
     * @param client a client to send to
     * @param message a message to send
     */
    public synchronized void SendMessageToClient(ChatClient client, String message) {
        try {
            client.sendMessage(message);
        } catch (IOException ex) {
            RemoveClient(client);
        }
    }

    /**
     * Get online users
     * @return online user names list
     */
    public synchronized String[] GetOnlineUsers() {
        String[] OnlineUsers = new String[_clients.size()];
        for (int i = 0; i < OnlineUsers.length; i ++) {
            OnlineUsers[i] = _clients.get(i).Username();
        }
        return OnlineUsers;
    }
    
    /**
     * Disconnects all the active clients
     * @throws IOException when failed to close a client socket
     */
    public void DisconnectAllClients() throws IOException {
        // Close all the clients sockets
        for (ChatClient client: _clients) {
            client.Disconnect();
        }
        
        // empties the clients list
        _clients.clear();
    }
}
