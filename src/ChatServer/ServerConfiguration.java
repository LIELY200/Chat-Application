/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatServer;

/**
 * Server configurations
 */
public class ServerConfiguration {
    public static final String JOINED_COMMAND_FORMAT = "JOINED %s\r\n";
    public static final String LEAVED_COMMAND_FORMAT = "LEAVED %s\r\n";
    public static final String ONLINE_COMMAND_FORMAT = "ONLINE %s\r\n";
    public static final String MSG_COMMAND_FORMAT = "MSG %s %s\r\n";
    
    
    public static final int MAX_NUMBER_OF_THREADS = 10;
    
    public static final int SERVER_PORT = 8080;
    
    // server GUI message formats
    public static final String CONNECT_MESSAGE_FORMAT = "%s connected\r\n";
    public static final String DISCONNECT_MESSAGE_FORMAT = "%s disconnected\r\n";
    public static final String GETONLINE_MESSAGE_FORMAT = "%s asked for online clients\r\n";
    public static final String SEND_TO_ALL_MESSAGE_FORMAT = "%s sent to all: %s\r\n";
    public static final String SEND_TO_MESSAGE_FORMAT = "%s sent to %s: %s\r\n";
}
