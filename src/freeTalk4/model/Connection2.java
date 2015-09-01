package freeTalk4.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

//这个是一开始写的，事实证明用object封装socket的inputstream是行不通的！
public class Connection2 {

	/**
	 * @param args
	 */
	Socket socket;
	int port = 1024;
	InputStream is;
	OutputStream os;
	BufferedInputStream bis;
	BufferedOutputStream bos;
	InputStreamReader inreader;
	OutputStreamWriter outwriter;
	// talk
	BufferedReader reader;
	BufferedWriter writer;
	// login
	ObjectOutputStream oos;
	ObjectInputStream ois;

	/*
	 * public int setPort()
	 * {
	 * port = (int) (Math.random() * (65536));
	 * if (port > 1024)
	 * return port;
	 * else
	 * return setPort();
	 * }
	 * 
	 * public int getPort()
	 * {
	 * return port;
	 * }
	 */

	public void connect() {
		try {
			socket = new Socket("127.0.0.1", port);
			socket.getLocalAddress();
			socket.getLocalPort();
			System.out.println("LocalAddress: \t" + socket.getLocalAddress() + "\nLocalPort: \t"
					+ socket.getLocalPort());
			is = socket.getInputStream();
			os = socket.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(os);
		} catch (IOException e) {
			System.out.println("connect error!");
			e.printStackTrace();
		}
		try {
			// loginStream (object stream)
			System.out.println("begin to init stream");
			oos = new ObjectOutputStream(bos);
			if (is != null) {
				ois = new ObjectInputStream(bis);
			}
			System.out.println("loginStream has inited");
			// talkStream (buffer reader)
			inreader = new InputStreamReader(socket.getInputStream());
			outwriter = new OutputStreamWriter(socket.getOutputStream());
			reader = new BufferedReader(inreader);
			writer = new BufferedWriter(outwriter);
			System.out.println("talkStream has inited");
			System.out.println("client stream has inited");
		} catch (IOException e) {
			System.out.println("init Stream error");
			e.printStackTrace();
		}
	}

	public LoginMessage loginCheck(User user) {
		LoginMessage message = new LoginMessage();
		int time = 0;
		// 发送的是User对象，收到的是LoginMessage对象
		try {
			while (true) {
				System.out.println("will write user to server");
				oos.writeObject(user);
				System.out.println("already write user to server");

				message = (LoginMessage) ois.readObject();
				if (message.isLoginRight() == true) {
					oos.close();
					ois.close();
					break;
				}
				time++;
				if (time == 3) {
					System.out.println("登录超时");
					message.setWrongMessage("登录超时");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return message;
	}

	public void talk() {

	}

	public static void main(String[] args) {
		Connection con = new Connection();
		con.connect();
	}

}
