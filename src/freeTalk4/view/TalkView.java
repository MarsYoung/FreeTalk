package freeTalk4.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import freeTalk4.control.Controller;
import freeTalk4.model.Model;

public class TalkView extends JFrame {
	private static final long serialVersionUID = -6170944208236009826L;
	Controller controller;
	Model model;

	public TalkView(Controller controller, Model model) {
		this.controller = controller;
		this.model = model;
	}

	private JLabel pictureLabel;
	private JLabel usernameLabel;
	JTextField usernameText;
	JButton callButton;
	JTextArea show;
	JScrollPane shows;
	JTextArea input;
	JButton send;
	JButton reset;

	public void init() {
		this.setTitle("TalkingMachine");
		this.setSize(400, 520);
		this.setResizable(false);
		this.setLocation((int) (Math.random() * 600), (int) (Math.random() * 200));// 随机显示在桌面
		this.setDefaultCloseOperation(TalkView.DISPOSE_ON_CLOSE);

		pictureLabel = new JLabel();
		System.out.println(getClass());
		pictureLabel.setIcon(new ImageIcon(getClass().getResource("./talk.png")));
		usernameLabel = new JLabel("用户名");
		usernameLabel.setFont(new Font("宋体", 3, 15));// "楷体_GB2312","宋体","楷体","华文行楷"
		usernameLabel.setForeground(Color.white);
		usernameText = new JTextField(30);
		callButton = new JButton("呼叫");
		callButton.setFont(new Font("宋体", 0, 12));
		callButton.setForeground(Color.blue);
		// callButton.setIcon(talk.png);
		// callButton.setOpaque(false);
		show = new JTextArea();
		shows = new JScrollPane(show);
		input = new JTextArea();
		send = new JButton("发送");
		send.setFont(new Font("宋体", 0, 12));
		send.setForeground(Color.blue);
		reset = new JButton("清空");
		reset.setFont(new Font("宋体", 0, 12));
		reset.setForeground(Color.blue);

		this.setLayout(null);

		this.add(usernameLabel);
		usernameLabel.setLocation(145, 17);
		usernameLabel.setSize(60, 20);
		this.add(usernameText);
		usernameText.setLocation(205, 12);
		usernameText.setSize(100, 25);
		usernameText.setOpaque(false);

		this.add(callButton);
		callButton.setLocation(327, 12);
		callButton.setSize(60, 26);
		callButton.setOpaque(false);

		this.add(shows);
		shows.setLocation(10, 50);
		shows.setSize(375, 320);
		// show.setBackground(Color.lightGray);
		show.setOpaque(false);// 设置为透明
		show.setEditable(false);
		shows.setOpaque(false);
		shows.getViewport().setOpaque(false);

		this.add(input);
		input.setLocation(10, 380);
		input.setSize(375, 80);
		// input.setBackground(Color.lightGray);
		input.setOpaque(false);

		this.add(send);
		send.setLocation(255, 463);
		send.setSize(60, 25);

		this.add(reset);
		reset.setLocation(327, 463);
		reset.setSize(60, 25);

		this.add(pictureLabel);// 后加的显示在后面，所以这个必须在最后
		pictureLabel.setLocation(0, 0);
		pictureLabel.setSize(400, 500);
		this.validate();

		this.setVisible(true);

		TalkViewListener listener = new TalkViewListener(this);
		send.addActionListener(listener);
		reset.addActionListener(listener);
		callButton.addActionListener(listener);

		// receive thread
		new TalkViewReceiveThread(this).start();
	}
}
