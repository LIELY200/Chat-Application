package ChatClient;
import ServerMessageHandler.IServerMessageHandler;
import ServerMessageHandler.IServerMessageObserver;
import ServerMessageHandler.ServerMessageFactory;

import java.io.*;

/**
 * This class listens to messages from the server
 * It implements runnable, to allow listening on a different thread
 */
public class ClientMessageListener implements Runnable {

    private InputStream _in_stream;
    private ServerMessageFactory _serverMessageFactory;
    private boolean _is_running = false;

    /**
     * Constructor of client message listener
     * @param inputStream a stream to the input from the server
     * @param serverMessageObserver an oberserver to server messages
     */
    public ClientMessageListener(InputStream inputStream, IServerMessageObserver serverMessageObserver) {
        _in_stream = inputStream;
        _serverMessageFactory = new ServerMessageFactory(serverMessageObserver);
    }

    @Override
    public void run() {
        _is_running = true;

        while(_is_running) {
            try {
                String serverMessage = readMessage();
                IServerMessageHandler serverMessageHandler = _serverMessageFactory.GetServerMessageHandler(serverMessage);
                serverMessageHandler.HandleMessage(serverMessage);
            } catch (IOException ex) {
                _is_running = false;
            }
        }
    }

    private String readMessage() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(_in_stream));
        String message = reader.readLine();

        // This is the easiest way to detect remote disconnection when reading from the input stream
        if (message == null) {
            throw new IOException("Server disconnected");
        }

        return message;
    }

    /**
     * Stops the client messages listener from listening to messages from the server
     */
    public void Stop() {
        _is_running = false;
    }
}
