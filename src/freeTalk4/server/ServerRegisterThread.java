package freeTalk4.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerRegisterThread extends Thread {
	private Socket socket;
	InputStream is;
	OutputStream os;
	InputStreamReader inreader;
	OutputStreamWriter outwriter;
	BufferedReader reader;
	BufferedWriter writer;

	public ServerRegisterThread(Server server) {
		this.socket = server.registerSocket;
	}

	public void run() {
		try {
			System.out.println("Begin to init registerStream!");
			is = socket.getInputStream();
			os = socket.getOutputStream();
			inreader = new InputStreamReader(is);
			outwriter = new OutputStreamWriter(os);
			reader = new BufferedReader(inreader);
			writer = new BufferedWriter(outwriter);
			System.out.println("Register Stream have inited!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while (true) {
				System.out.println("wait to readRegisterUser from client ");
				User user = new User();
				String userS = reader.readLine();
				String userA[] = userS.split(",");

				user.setUsername(userA[0]);
				user.setPassword(userA[1]);
				user.setAge(Integer.valueOf(userA[2]));
				user.setSex(userA[3]);
				user.setSchool(userA[4]);
				user.setInformation(new StringBuffer(userA[5]));

				ServerDB database = new ServerDB();
				boolean isRegister = database.createNewUser((int) (Math.random() * 1000), user.getUsername(),
						user.getPassword(), user.getSex(), user.getAge(), user.getSchool(), user.getInformation());
				// new ServerDB().writeToFile(user);
				if (isRegister) {
					writer.write("OK" + "\n");
					writer.flush();
				}
			}
		} catch (IOException e) {
			System.out.println("the client has gone,register stream closed!");
		}
	}
}
