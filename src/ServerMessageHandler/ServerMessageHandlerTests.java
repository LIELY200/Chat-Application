package ServerMessageHandler;

import static org.junit.Assert.*;

import org.junit.Test;

import ChatServer.ClientDisconnectedException;
import ChatServer.ServerConfiguration;

public class ServerMessageHandlerTests {	
	@Test
	public void server_message_factory_test() {		
		ServerMessageFactory serverMessageFactory = new ServerMessageFactory(null);
		
		String incomingMessage = String.format(ServerConfiguration.MSG_COMMAND_FORMAT, "XXX", "ABC");
		String joinedMessage = String.format(ServerConfiguration.JOINED_COMMAND_FORMAT, "XXX");
		String LeavedMessage = String.format(ServerConfiguration.LEAVED_COMMAND_FORMAT, "XXX");
		String OnlineMessage = String.format(ServerConfiguration.ONLINE_COMMAND_FORMAT, "XXX YYY");
		
		IServerMessageHandler dummyHandler;
		
		dummyHandler = serverMessageFactory.GetServerMessageHandler(incomingMessage);
		assertTrue(dummyHandler instanceof IncomingMessageHandler);
		
		dummyHandler = serverMessageFactory.GetServerMessageHandler(joinedMessage);
		assertTrue(dummyHandler instanceof JoinedMessageHandler);
		
		dummyHandler = serverMessageFactory.GetServerMessageHandler(LeavedMessage);
		assertTrue(dummyHandler instanceof LeavedMessageHandler);
		
		dummyHandler = serverMessageFactory.GetServerMessageHandler(OnlineMessage);
		assertTrue(dummyHandler instanceof OnlineMessageHandler);
	}
	
	@Test
	public void incoming_message_with_spaces_test() {
		String originalUsername = "Sapir";
		String originalMessage = "Hello world";
		
		class ServerMessageObserverStub implements IServerMessageObserver {
			@Override
			public void OnIncomingMessage(String username, String message) {
				assertEquals(originalUsername, username);
				assertEquals(originalMessage, message);
			}
			@Override
			public void OnJoinedClient(String username) { }
			@Override
			public void OnLeavedClient(String username) { }
			@Override
			public void OnOnlineClientList(String[] onlineUsers) { }
		}
		
		IncomingMessageHandler incomingMessageHandler = new IncomingMessageHandler(new ServerMessageObserverStub());
		incomingMessageHandler.HandleMessage(String.format(ServerConfiguration.MSG_COMMAND_FORMAT, originalUsername, originalMessage));
	}
}
