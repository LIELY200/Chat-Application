/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatClient;

/**
 * This class contains all of the client configurations
 */
public class ClientConfiguration {
    // The remote chat server port to connect to
    public static final int SERVER_PORT = 8080;
    
    // Client messages formats
    public static final String CONNECT_MESSAGE_FORMAT = "CONNECT %s";
    public static final String DISCONNECT_MESSAGE_FORMAT = "DISCONNECT";
    public static final String SEND_TO_MESSAGE_FORMAT = "SENDTO %s %s";
    public static final String SEND_MESSAGE_FORMAT = "SEND %s";
    public static final String GET_ONLINE_MESSAGE_FORMAT = "GETONLINE";
    
    // Clients GUI message
    public static final String INCOMING_MESSAGE_FORMAT = "%s: %s\r\n";
    public static final String JOINED_MESSAGE_FORMAT = "%s has joined\r\n";
    public static final String LEAVED_MESSAGE_FORMAT = "%s leaved\r\n";
    public static final String ONLINE_MESSAGE_FORMAT = "Online users: %s\r\n";
}
