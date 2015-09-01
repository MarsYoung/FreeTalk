package freeTalk4.server;

public class SendMessageThread extends Thread {
	// 这个线程被服务器持有，负责把消息发给指定的thread,从而传到指定打的view
	public Server server;

	public SendMessageThread(Server server) {
		this.server = server;
	}

	public void run() {
		// 这里不作任何时，只是开启一个线程专门负责sendMessage
	}

	public void sendMessage(String s, int clientID) {
		if (server.talkThread[clientID] != null)
			server.talkThread[clientID].sendMessage(s);
	}
}
