package ChatServer;

import ClientMessageHandler.IClientMessageObserver;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This clsas represents the logic of the chat server
 */
public class ChatApplication implements Runnable {
    // The server state
    private boolean _is_running;

    // The server socket to accept new clients
    private int _port;
    private ServerSocket _server_socket;
    // The clients manager
    private ChatClientsManager _chatClientsManager;
    
    // Observer to client messages
    private IClientMessageObserver _clientMessageObserver;

    /**
     * Consturctor to chat application
     * @param port to listen to
     * @param clientMessageObserver observer to client messages
     */
    public ChatApplication(int port, IClientMessageObserver clientMessageObserver) {
        this(port);
        _clientMessageObserver = clientMessageObserver;
    }
    
    /**
     * Consturctor to chat application
     * @param port to listen to
     */
    public ChatApplication(int port) {
        _is_running = false;
        _server_socket = null;

        _port = port;

        _chatClientsManager = new ChatClientsManager();
        _clientMessageObserver = null;
    }

    /**
     * Start the chat application
     * @throws IOException when failed to creat a socket or accepting clients
     */
    public void Start() throws IOException {
        _is_running = true;

        if (_server_socket == null) {
            _server_socket = new ServerSocket(_port);
        }

        while (_is_running) {
            Socket client = _server_socket.accept();
            System.out.println("Client accepted");
            _chatClientsManager.HandleAsync(client, _clientMessageObserver);
        }
    }

    /**
     * Stops the chat application
     * @throws IOException when failed to close sockets
     */
    public void Stop() throws IOException {
        _is_running = false;
        _server_socket.close();
        _server_socket = null;
        _chatClientsManager.DisconnectAllClients();
    }

    @Override
    public void run() {
        try {
            Start();
        } catch (IOException ex) {
            
        }
    }
}
