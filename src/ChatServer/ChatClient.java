package ChatServer;

import java.io.*;
import java.net.Socket;

/**
* This class represents a client in the chat.
* holds information about it and performs basic communication.
 */
public class ChatClient {
    private Socket _client;
    private String _username;
    private InputStream _in_stream;
    private OutputStream _out_stream;

    /**
     * Constructor of the chat client
     * @param Client a socket to the client
     */
    public ChatClient(Socket Client) {
        _client = Client;
    }

    /**
     * Initialize the client information
     * @throws IOException when failed to fetch the streams from the socket
     */
    public void Initialize() throws IOException {
        _in_stream = _client.getInputStream();
        _out_stream = _client.getOutputStream();
    }

    /**
     * A getter to the user name of the client
     * @return the user name of the client
     */
    public String Username() {
        return _username;
    }

    /**
     * A setter to the user name of the client
     * @param username the user name of the client
     */
    public void SetUsername(String username) {
        _username = username;
    }

    /**
     * Reads a message from the client socket
     * @return a message from the client
     * @throws IOException when failed to read message from the client
     */
    public String readMessage() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(_in_stream));
        String message = reader.readLine();

        // This is the easiest way to detect remote client disconnection when reading from the input stream
        if (message == null) {
            throw new IOException("Client " + _username + " has been disconnected");
        }

        return message;
    }

    /**
     * Sends a message to the client
     * @param message a message to send
     * @throws IOException when failed to send the message
     */
    public void sendMessage(String message) throws IOException {
        _out_stream.write(message.getBytes());
    }

    /**
     * Disconnet the client from the server
     * @throws IOException when failed to close the client sockets
     */
    public void Disconnect() throws IOException {
        if (_in_stream != null) {
            _in_stream.close();
            _in_stream = null;
        }

        if (_out_stream != null) {
            _out_stream.close();
            _out_stream = null;
        }

        if (_client != null) {
            _client.close();
            _client = null;
        }
    }
}
