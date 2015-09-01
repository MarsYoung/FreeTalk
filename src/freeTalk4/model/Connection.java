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

public class Connection extends Thread {

	/**
	 * @param args
	 */
	Socket talkSocket;
	Socket registerSocket;
	int talkPort = 1024;
	int registerPort = 9797;
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

	// register
	InputStream is2;
	OutputStream os2;
	BufferedInputStream bis;
	BufferedOutputStream bos;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	BufferedReader registerReader;// 由于ois不能初始化，所以用这个
	BufferedWriter registerWriter;

	public void connect()// login+talk
	{
		while (talkSocket == null) {
			try {
				talkSocket = new Socket("127.0.0.1", talkPort);
				// this.sleep(6000);// 每6秒钟连接一次服务器,时间太长了不好,写在这里逻辑上不健全
				// 因为即使连上了，还会有等待时间，
				// 写在下面的catch块里的话，只要一连上这个服务器，便开始初始化客户端
			} catch (IOException e) {
				System.out.println("No server，请先启动服务器!");
				try {
					Connection.sleep(6000);// 每6秒钟连接一次服务器,时间太长了不好
				} catch (InterruptedException e1) {
					System.out.println("connect thread error");
				}
			}
		}
		talkSocket.getLocalAddress();
		talkSocket.getLocalPort();
		System.out.println("LocalAddress: \t" + talkSocket.getLocalAddress() + "\nLocalPort: \t"
				+ talkSocket.getLocalPort());

		try {

			is = talkSocket.getInputStream();
			os = talkSocket.getOutputStream();
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
			System.out.println("client stream has inited");
		} catch (IOException e) {
			System.out.println("init Stream error");
		}

	}

	public void connect2()// register
	{
		while (registerSocket == null) {
			try {
				registerSocket = new Socket("127.0.0.1", registerPort);
			} catch (IOException e) {
				System.out.println("connect error!");
				try {
					Connection.sleep(6000);
				} catch (InterruptedException e1) {
					System.out.println("connect thread error");
				}
			}
		}

		registerSocket.getLocalAddress();
		registerSocket.getLocalPort();
		System.out.println("LocalAddress: \t" + registerSocket.getLocalAddress() + "\nLocalPort: \t"
				+ registerSocket.getLocalPort());

		if (registerSocket != null) {
			try {
				System.out.println(" before init register stream");
				is2 = registerSocket.getInputStream();
				os2 = registerSocket.getOutputStream();
				// bis=new BufferedInputStream(is2);
				// bos=new BufferedOutputStream(os2);
				// ois=new ObjectInputStream(is2);//貌似客户端不能读取对象只能写对象进去
				registerReader = new BufferedReader(new InputStreamReader(is2));
				registerWriter = new BufferedWriter(new OutputStreamWriter(os2));
				// oos=new ObjectOutputStream(os);
				System.out.println(" after init register stream");
			} catch (IOException e) {
				System.out.println("init Stream error");
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		Connection con = new Connection();
		con.connect();
		con.connect2();
	}

}
