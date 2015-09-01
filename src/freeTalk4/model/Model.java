package freeTalk4.model;

import java.io.IOException;

import freeTalk4.control.Controller;
import freeTalk4.view.LoginView;
import freeTalk4.view.TalkView;

public class Model {
	Controller controller;
	LoginView loginView;
	TalkView talkView;
	Connection con;// 让所有的view都持有这个链接

	public Model(Controller controller, LoginView loginView, TalkView talkView) {
		this.controller = controller;
		this.loginView = loginView;
		this.talkView = talkView;
	}

	public void connect() {
		con = new Connection();
		con.start();// 把Connection做成新线程处理，以免干扰view的初始化
		con.connect();
		con.connect2();
	}

	public LoginMessage checkUser(User user) {
		// 连接
		// con.connect();//经过连接，所有的输入输出流都已经初始化,写在这里当你执行完登录功能后，连接会丢失
		// 判断是否可以
		LoginMessage message = new LoginMessage();
		int time = 0;
		try {// 把循环写到try-catch块中，有好处的，可以在抛出异常是跳出循环
			while (true) {
				System.out.println("will write user to server");
				String so = user.getUsername() + ":" + user.getPassword();
				con.loginwriter.write(so + "\n");
				con.loginwriter.flush();
				System.out.println("already write user to server");

				String si = con.loginreader.readLine();
				String s[] = si.split(":");
				if (s[0].equals("true"))
					message.setLoginRight(true);
				else {
					message.setLoginRight(false);
					message.setWrongMessage(s[1]);
				}
				if (message.isLoginRight() == true) {
					// con.loginreader.close();
					// con.loginwriter.close();
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
			System.out.println("已和服务器失去连接");
		}
		return message;
	}

	public void talk(String str) {
		try {
			con.talkwriter.write(str + "\n");
			con.talkwriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String receive() {
		String receiveMessage = null;
		try {
			receiveMessage = con.talkreader.readLine();

		} catch (IOException e) {
			System.out.println("can't receive any message for server has lost!");
			receiveMessage = "serverlost";
		}
		return receiveMessage;
	}

	public String register(User user) {
		String returnMessage = null;
		try {
			String s = user.getUsername() + "," + user.getPassword() + "," + user.getAge() + "," + user.getSex() + ","
					+ user.getSchool() + "," + user.getInformation();
			con.registerWriter.write(s + "\n");
			con.registerWriter.flush();
			System.out.println("already write to server");
			returnMessage = con.registerReader.readLine();

		} catch (IOException e) {
			System.out.println("Stream has not inited or server lost,please wait!");
		}
		return returnMessage;
	}

}
