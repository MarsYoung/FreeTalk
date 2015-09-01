package freeTalk4.server;

import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.io.*;

public class Server {
	Server server = this;// 让这个server持有当前对象

	ServerSocket talkServer = null;
	ServerSocket registerServer = null;
	Socket talkSocket = null;
	Socket registerSocket = null;
	int talkPort;
	int registerPort;
	BufferedReader in;
	BufferedWriter out;
	ServerTalkThread talkThread[];
	ServerRegisterThread registerThread[];
	int i = 0;// 设置登录和对话线程
	int j = 0;// 设置注册线程
	SendMessageThread messageThread;

	Map<String, Integer> map;// 用来记录所有上线用户

	Server() {
		setTalkPort(1024);
		setRegisterPort(9797);
		map = new HashMap<String, Integer>();
		registerThread = new ServerRegisterThread[100];
		talkThread = new ServerTalkThread[100];
		messageThread = new SendMessageThread(this);
		// connect();我起初在这里做连接，但是由于serverThread要持有server对象来获得messageThread
		// 所以在serverThread必须new一个server出来，如果把connect放这儿，会多次连接，导致异常
	}

	public void talkConnect() {
		try {
			talkServer = new ServerSocket(talkPort);
			new RunTalkThread().start();
			// new RunRegisterThread().start();
			// 自此该服务器中公有三条线程（message,talkthread,registerthread）,
			// 其中talkthread和registerthread还会生成别的线程来服务
		} catch (IOException e) {
			System.out.println("Server Error!");
		}
	}

	public void registerConnect() {
		try {
			registerServer = new ServerSocket(registerPort);
			new RunRegisterThread().start();
		} catch (IOException e) {
			System.out.println("Server Error!");
		}
	}

	class RunTalkThread extends Thread {
		public void run() {

			while (true) {
				try {
					// 每新开一个线程，要new一个server出来，
					// 不然的话会有空指针异常
					// （事实不是这样的，一开始会出错，是由于server没有初始化，因为类中的server没有持有当前对象）
					System.out.println("talk server " + i + " begin to start");
					talkSocket = talkServer.accept();
					System.out.println("talk server have start");
					talkThread[i] = new ServerTalkThread(server, i);// 这里我让服务器线程持有socket，也可以持有server，然后点socket
					talkThread[i].start();
					System.out.println("talkThread[" + i + "] have already connected");
					i++;
					// this.yield();
					RunTalkThread.sleep(5000);// 睡一会儿，以免注册抢不到权利
				} catch (IOException e) {
					System.out.println("talk thread error");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class RunRegisterThread extends Thread {
		public void run() {
			while (true) {
				try {
					System.out.println("register server" + j + " begin to start");
					registerSocket = registerServer.accept();
					System.out.println("register server have start");
					registerThread[j] = new ServerRegisterThread(server);
					registerThread[j].start();
					System.out.println("registerThread[" + j + "] have already connected");
					j++;
					RunRegisterThread.yield();// Thread.yield()方法作用是：暂停当前正在执行的线程对象，并执行其他线程。
				} catch (IOException e) {
					System.out.println("register thread error");
				}
			}
		}
	}

	public void setTalkPort(int talkPort) {
		this.talkPort = talkPort;
	}

	public void setRegisterPort(int registerPort) {
		this.registerPort = registerPort;
	}

	public static void main(String args[]) {
		// new 两个server分别启动register和talk功能
		Server server = new Server();
		server.talkConnect();

		server.registerConnect();
	}

	public int checkClientLoginID(String loginuser) {
		int loginid = -1;

		Set<?> set = map.keySet();
		Iterator<?> it = set.iterator();
		while (it.hasNext()) {
			String key = (String) (it.next());
			System.out.println("Check:this is " + key);
			Integer threadid = (Integer) (map.get(key));
			if (key.equals(loginuser))// 不能用==，得用equals
				loginid = threadid;
		}
		return loginid;
	}
}