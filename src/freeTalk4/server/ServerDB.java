package freeTalk4.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import freeTalk4.model.LoginMessage;

public class ServerDB {
	private File file = new File("./src/freeTalk4/server/store/");
	private String storePath;

	ServerSocket server = null;
	Socket serverPort = null;
	InputStream in;
	OutputStream out;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	BufferedReader reader;

	public ServerDB() {

	}

	// 1.use objectwriter

	public void writeToFile(User user) {
		storePath = file.getAbsolutePath();
		System.out.println(storePath);
		try {
			FileOutputStream fo = new FileOutputStream(storePath + "/" + user.getUsername() + ".user");
			ObjectOutputStream o = new ObjectOutputStream(fo);
			o.writeObject(user);
			fo.close();
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public User getFromFile(String username) {
		storePath = file.getAbsolutePath();
		User user = new User();
		try {
			FileInputStream fi = new FileInputStream(storePath + "/" + username + ".user");
			ObjectInputStream in = new ObjectInputStream(fi);
			user = (User) in.readObject();
			in.close();
			fi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null) {
			System.out.println("username:\t" + user.getUsername() + "\nschool:\t\t" + user.getSchool());
			return user;
		} else {
			System.out.println("This user doesn't exist!");
			return user;
		}

	}

	public LoginMessage checkLogin(User user)// 为了简便，把判断登录的东西写在这里
	{
		LoginMessage message = new LoginMessage();
		User u = new User();
		u = getFromFile(user.getUsername());
		if (user.getUsername() != null) {
			if (u.getPassword().equals(user.getPassword())) {
				message.setLoginRight(true);
			} else {
				message.setLoginRight(false);
				message.setWrongMessage("密码不正确");
			}
		} else {
			message.setLoginRight(false);
			message.setWrongMessage("用户名不存在");
		}
		return message;
	}

	public boolean createNewUser(int id, String username, String password, String sex, int age, String school,
			StringBuffer information) {
		boolean isCreate = false;
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		user.setSex(sex);
		user.setAge(age);
		user.setSchool(school);
		user.setInformation(information);

		if (user.getUsername() != null && user.getPassword() != null && user.getSex() != null) {
			writeToFile(user);
			isCreate = true;
		}

		return isCreate;

	}

	// 2.use db..

	public static void main(String args[]) {
		int id = 1;
		String username = "marsyoung";
		String password = "123456";
		String sex = "male";
		int age = 21;
		String school = "TJCU";
		StringBuffer information = new StringBuffer("I love WebQQ");

		ServerDB serverdb = new ServerDB();
		serverdb.createNewUser(id, username, password, sex, age, school, information);
		serverdb.getFromFile(username);
		// 又一次我改了user类，结果报错，原因是找不到了原来的那个串行化的user，只要重写一遍就好
	}

}
