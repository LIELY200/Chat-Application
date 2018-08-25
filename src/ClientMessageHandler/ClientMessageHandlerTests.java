package ClientMessageHandler;

import static org.junit.Assert.*;

import org.junit.Test;

import ChatClient.ClientConfiguration;
import ChatServer.ChatClient;
import ChatServer.ChatClientsManager;
import ChatServer.ClientDisconnectedException;

public class ClientMessageHandlerTests {

	@Test
	public void client_message_handler_factory_test() {
		ClientMessageHandlerFactory clientMessageHandlerFactory = new ClientMessageHandlerFactory(null, null, null);
		
		String connectMessage = String.format(ClientConfiguration.CONNECT_MESSAGE_FORMAT, "XXX");
		String disconnectMessage = String.format(ClientConfiguration.DISCONNECT_MESSAGE_FORMAT);
		String getOnlineMessage = String.format(ClientConfiguration.GET_ONLINE_MESSAGE_FORMAT);
		String sendMessage = String.format(ClientConfiguration.SEND_MESSAGE_FORMAT, "XXX");
		String sendToMessage = String.format(ClientConfiguration.SEND_TO_MESSAGE_FORMAT, "XXX", "ABC");
		
		IClientMessageHandler dummyHandler;
		
		dummyHandler = clientMessageHandlerFactory.GetClientMessageHandler(connectMessage);
		assertTrue(dummyHandler instanceof ConnectMessageHandler);
		
		dummyHandler = clientMessageHandlerFactory.GetClientMessageHandler(disconnectMessage);
		assertTrue(dummyHandler instanceof DisconnectMessageHandler);
		
		dummyHandler = clientMessageHandlerFactory.GetClientMessageHandler(getOnlineMessage);
		assertTrue(dummyHandler instanceof GetOnlineMessageHandler);
		
		dummyHandler = clientMessageHandlerFactory.GetClientMessageHandler(sendMessage);
		assertTrue(dummyHandler instanceof SendMessageHandler);
		
		dummyHandler = clientMessageHandlerFactory.GetClientMessageHandler(sendToMessage);
		assertTrue(dummyHandler instanceof SendToMessageHandler);
	}
	
	@Test(expected=ClientDisconnectedException.class)
	public void disconnect_throws_client_disconnected_exception_test() throws ClientDisconnectedException {
		ChatClient client = new ChatClient(null);
		ChatClientsManager chatClientsManager = new ChatClientsManager();
		
		DisconnectMessageHandler disconnectMessageHandler = new DisconnectMessageHandler(client, chatClientsManager, null);
		disconnectMessageHandler.HandleMessage(String.format(ClientConfiguration.DISCONNECT_MESSAGE_FORMAT));
	}

}
