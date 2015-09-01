package freeTalk4.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import freeTalk4.model.LoginMessage;

public class ServerTalkThread extends Thread {
	Server server;
	Socket socket = null;
	int port;
	InputStream is;
	OutputStream os;
	InputStreamReader inreader;
	OutputStreamWriter outwriter;
	// login
	BufferedReader loginreader;
	BufferedWriter loginwriter;
	// talk
	BufferedReader talkreader;
	BufferedWriter talkwriter;

	boolean isLogin = false;
	User loginuser;

	int clientID = 0;

	ServerDB serverdb;

	// 下面的俩个构造器都可以初始化该线程，我起初用的是第一种，但是无法定位到要发送的客户端
	public ServerTalkThread(Socket socket) {
		this.socket = socket;
	}

	// 这一种方法持有server，可以从server调用sendMessageThread发送到指定的客户端
	public ServerTalkThread(Server server, int i) {
		// this.server=new Server(); 一开始以为得重新new一个，其实不需要，因为持有的是同一个对象即可
		// 当然也可以持有，利弊有待考察
		this.server = server;
		this.socket = server.talkSocket;
		clientID = i;// 记录线程
	}

	public void run() {
		serverdb = new ServerDB();
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			inreader = new InputStreamReader(is);
			outwriter = new OutputStreamWriter(os);
			// loginStream (object stream)
			System.out.println("begin to init stream");
			loginreader = new BufferedReader(inreader);
			loginwriter = new BufferedWriter(outwriter);
			System.out.println("loginStream has inited");
			// talkStream (buffer reader)
			talkreader = new BufferedReader(inreader);
			talkwriter = new BufferedWriter(outwriter);
			System.out.println("talkStream has inited");
			System.out.println("server stream has inited");
		} catch (IOException e) {
			System.out.println("StreamCreation Error!");
		}

		LoginMessage message = new LoginMessage();
		loginuser = new User();
		while (true) {
			try {
				if (isLogin == false) {
					String si = loginreader.readLine();
					System.out.println(si);
					String s[] = new String[2];
					s = si.split(":");
					loginuser.setUsername(s[0]);
					loginuser.setPassword(s[1]);
					// 生成login答复字符串
					message = serverdb.checkLogin(loginuser);
					String loginResponse;
					if (message.isLoginRight()) {
						loginResponse = "true" + ":" + "null";
					} else {
						loginResponse = "false" + ":" + serverdb.checkLogin(loginuser).getWrongMessage();
					}
					// 控制流程，login应答
					if (message.isLoginRight()) {
						isLogin = true;
						// 记录登录流程
						server.map.put(loginuser.getUsername(), clientID);
						System.out.println("you have logined in");
						loginwriter.write(loginResponse + "\n");
						loginwriter.flush();

					} else {
						loginwriter.write(loginResponse + "\n");
						loginwriter.flush();
					}
					continue;
				} else {
					// talking
					String s = null;
					if (socket != null)
						s = talkreader.readLine();
					if (!s.trim().equals(""))// 不能用null，也不能用等于,if (s.trim() != null)——————if (s.trim() != "")
					{
						String sa[] = s.split("#woshimazhiyu#");
						if (sa[1] != null) {
							System.out.println(s);
							// talk to other client
							if (server.checkClientLoginID(sa[0]) != -1) {
								clientID = server.checkClientLoginID(sa[0]);
							}
							String newstring = loginuser.getUsername() + "#woshimazhiyu#" + sa[1];
							server.messageThread.sendMessage(newstring, clientID);
						}
					}
					// send message to the one you wanna talk to
				}
			} catch (IOException e) {
				System.out.println("server read wrong，Talk has closed,the client is lost");
				break;// 推出该对话（循环），线程自动结束
			}
		}
	}

	public void sendMessage(String s) {
		String message = s;
		try {
			talkwriter.write(message + "\n");
			talkwriter.flush();
		} catch (IOException e) {
			System.out.println("Send Wrong!");
		}
	}

}
