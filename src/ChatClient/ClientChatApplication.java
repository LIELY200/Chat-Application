package ChatClient;

import ServerMessageHandler.IServerMessageObserver;
import java.io.*;
import java.net.Socket;

/**
 * This class represents a chat client
 * Its responsible for sending and receiving messages from/to the server
 */
public class ClientChatApplication {
    // The server address
    private String _serverAddress;
    private int _port;

    // The socket to the server
    private Socket _clientSocket;
    private InputStream _in_stream;
    private PrintWriter _out_stream;

    // Listener thread to server messages
    private ClientMessageListener _listener;
    private Thread _listenerThread;

    /**
     * Consturctor of the client chat application
     * @param serverAddress The server address
     * @param port The chat server port
     * @param serverMessageObserver observer to server messages notifications
     * @throws IOException when failed to connect to the remote server
     */
    public ClientChatApplication(String serverAddress, int port, IServerMessageObserver serverMessageObserver) throws IOException {
        _port = port;
        _serverAddress = serverAddress;

        _clientSocket = new Socket(_serverAddress, _port);
        _in_stream = _clientSocket.getInputStream();
        _out_stream = new PrintWriter(_clientSocket.getOutputStream(), true);

        _listener = new ClientMessageListener(_in_stream, serverMessageObserver);
        _listenerThread =  new Thread(_listener);
        _listenerThread.start();
    }

    /**
     * Sends CONNECT message to the chat server
     * @param username the username of the client
     * @throws IOException when failed to send the message to the server
     */
    public void Connect(String username) throws IOException {
        _out_stream.println(String.format(ClientConfiguration.CONNECT_MESSAGE_FORMAT, username));
    }

    /**
     * Sends DISCONNECT message to the chat server
     * @throws IOException when failed to send the message to the server
     * @throws InterruptedException when failed to stop the listening thread
     */
    public void Disconnect() throws IOException, InterruptedException {
        _listener.Stop();
        _listenerThread.interrupt();

        _out_stream.println(ClientConfiguration.DISCONNECT_MESSAGE_FORMAT);
    }

    /**
     * Sends SENDTO message to the chat server
     * @param targetUsername the username the message is directed to
     * @param message the message to send to the other client
     * @throws IOException when failed to send the message
     */
    public void SendTo(String targetUsername, String message) throws IOException {
        _out_stream.println(String.format(ClientConfiguration.SEND_TO_MESSAGE_FORMAT, targetUsername, message));
    }

    /**
     * Sends SEND message to the chat server
     * @param message the message to send to the other client
     * @throws IOException when failed to send the message
     */    public void Send(String message) throws IOException {
        _out_stream.println(String.format(ClientConfiguration.SEND_MESSAGE_FORMAT, message));
    }

    /**
     * Sends GETONLINE message to the chat server
     * @throws IOException when failed to send the message
     */
    public void GetOnline() throws IOException {
        _out_stream.println(ClientConfiguration.GET_ONLINE_MESSAGE_FORMAT);
    }
}