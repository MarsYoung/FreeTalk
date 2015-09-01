package freeTalk4.view;

import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import freeTalk4.control.Controller;
import freeTalk4.model.Model;

public class LoginView extends JFrame {
	private static final long serialVersionUID = 4226122652846177673L;

	Controller controller;
	Model model;

	TalkView talkView;

	private JPanel loginPanel;
	private JLabel pictureLabel;
	private JLabel usernameLabel;
	JTextField usernameText;
	private JLabel userpasswordLabel;
	JTextField userpasswordText;// JTextField不知道如何设置默认字符
	JButton loginButton;
	JButton registerButton;

	JPanel registerPanel;
	private JLabel picture1Label;
	private JLabel usrLabel;
	JTextField usrText;
	private JLabel pwdLabel;
	JTextField pwdText;
	private JLabel sexLabel;
	CheckboxGroup sexGroup;
	Checkbox maleBox;
	Checkbox femaleBox;
	private JLabel ageLabel;
	Choice ageChoice;
	private JLabel schoolLabel;
	JTextField schoolText;
	private JLabel informationLabel;
	JTextArea informationText;
	JButton registerButtonOK;

	JPanel registerResultOkPanel;
	JPanel registerResultNotOkPanel;

	public LoginView(Controller controller, Model model) {
		this.controller = controller;
		this.model = model;
	}

	public void init() {
		this.setTitle("TalkMachine#204");// 名字
		this.setDefaultCloseOperation(LoginView.EXIT_ON_CLOSE);// 设置开关
		this.setSize(355, 320);// 大小
		this.setResizable(false);// 大小不可改变
		this.setLocation(200, 200);// 在桌面上的位置
		this.setLayout(new CardLayout());// 布局方式

		loginPanel = new JPanel();
		registerPanel = new JPanel();
		registerResultOkPanel = new JPanel();
		registerResultNotOkPanel = new JPanel();
		this.add("loginPanelCard", loginPanel);// 前面那个是卡片名
		this.add("registerPanelCard", registerPanel);
		this.add("registerResultOkCard", registerResultOkPanel);
		this.add("registerResultNotOkCard", registerResultNotOkPanel);
		initLoginPanel();
		initRegisterPanel();
		initRegisterResultPanelOk();
		initRegisterResultPanelNotOk();
		talkView = controller.talkView;

		this.setVisible(true);
		this.addAction();
	}

	private void addAction() {
		loginButton.addActionListener(new LoginViewListener(this));
		registerButton.addActionListener(new LoginViewListener(this));
		registerButtonOK.addActionListener(new LoginViewListener(this));
	}

	public void initLoginPanel() {
		pictureLabel = new JLabel();
		System.out.println(getClass());
		pictureLabel.setIcon(new ImageIcon(getClass().getResource("./login.png")));
		usernameLabel = new JLabel(" 用户 ");
		usernameLabel.setFont(new Font("宋体", 1, 16));// "楷体_GB2312","宋体","楷体","华文行楷"
		usernameText = new JTextField(30);
		userpasswordLabel = new JLabel(" 密码 ");
		userpasswordLabel.setFont(new Font("宋体", 1, 16));
		userpasswordText = new JTextField(30);
		// userpasswordText.setEchoChar('*');
		loginButton = new JButton(" 登录 ");
		registerButton = new JButton(" 注册 ");

		loginPanel.setLayout(null);// JFrame.setDefaultLookAndFeelDecorated(true);
		loginPanel.add(usernameLabel);
		usernameLabel.setLocation(143, 130);
		usernameLabel.setSize(100, 50);// 必须setsize不然不显示
		loginPanel.add(userpasswordLabel);
		userpasswordLabel.setLocation(143, 170);
		userpasswordLabel.setSize(100, 50); // 或者userpasswordLabel.setBounds(20, 30, 20, 30);
		loginPanel.add(usernameText);
		usernameText.setLocation(200, 140);
		usernameText.setSize(140, 28);
		usernameText.setOpaque(false);
		loginPanel.add(userpasswordText);
		userpasswordText.setLocation(200, 180);
		userpasswordText.setSize(140, 28);
		userpasswordText.setOpaque(false);
		loginPanel.add(loginButton);
		loginButton.setLocation(70, 250);
		loginButton.setSize(68, 30);
		loginPanel.add(registerButton);
		registerButton.setLocation(210, 250);
		registerButton.setSize(68, 30);

		loginPanel.add(pictureLabel);// 后加的显示在后面，所以这个必须在最后
		pictureLabel.setLocation(0, 0);
		pictureLabel.setSize(355, 310);
		loginPanel.validate();
	}

	public void initRegisterPanel() {
		picture1Label = new JLabel();
		System.out.println(getClass());
		picture1Label.setIcon(new ImageIcon(getClass().getResource("./register.png")));
		usrLabel = new JLabel("用户名");
		usrLabel.setFont(new Font("宋体", 3, 16));// "楷体_GB2312","宋体","楷体","华文行楷"
		usrText = new JTextField(30);
		pwdLabel = new JLabel("密码");
		pwdLabel.setFont(new Font("宋体", 3, 16));
		pwdText = new JTextField(30);
		sexLabel = new JLabel("性别");
		sexLabel.setFont(new Font("宋体", 3, 16));
		sexGroup = new CheckboxGroup();
		maleBox = new Checkbox("男", true, sexGroup);
		maleBox.setFont(new Font("宋体", 1, 12));
		femaleBox = new Checkbox("女", true, sexGroup);
		femaleBox.setFont(new Font("宋体", 1, 12));
		ageLabel = new JLabel("年龄");
		ageLabel.setFont(new Font("宋体", 3, 16));
		ageChoice = new Choice();

		String[] description = { "1~17", "18~23", "24~35", "36~48", "48~60", "60~100", };
		for (int i = 0; i < 6; i++)
			ageChoice.addItem(description[i]);

		schoolLabel = new JLabel("学校");
		schoolLabel.setFont(new Font("宋体", 3, 16));
		schoolText = new JTextField(30);
		informationLabel = new JLabel("个人信息");
		informationLabel.setFont(new Font("宋体", 3, 16));
		informationText = new JTextArea(80, 60);
		registerButtonOK = new JButton("注册");

		registerPanel.setLayout(null);

		registerPanel.add(usrLabel);
		usrLabel.setLocation(50, 90);
		usrLabel.setSize(60, 20);
		registerPanel.add(usrText);
		usrText.setLocation(130, 90);
		usrText.setSize(100, 25);
		usrText.setOpaque(false);

		registerPanel.add(pwdLabel);
		pwdLabel.setLocation(50, 120);
		pwdLabel.setSize(60, 20);// 必须setsize不然不显示
		registerPanel.add(pwdText);
		pwdText.setLocation(130, 120);
		pwdText.setSize(100, 25);
		pwdText.setOpaque(false);

		registerPanel.add(maleBox);
		maleBox.setLocation(85, 153);
		maleBox.setSize(30, 25);
		registerPanel.add(femaleBox);
		femaleBox.setLocation(115, 153);
		femaleBox.setSize(30, 25);
		registerPanel.add(sexLabel);
		sexLabel.setLocation(45, 140);
		sexLabel.setSize(100, 50);

		registerPanel.add(ageChoice);
		ageChoice.setLocation(200, 153);
		ageChoice.setSize(53, 25);
		registerPanel.add(ageLabel);
		ageLabel.setLocation(160, 140);
		ageLabel.setSize(100, 50);

		registerPanel.add(schoolLabel);
		schoolLabel.setLocation(85, 170);
		schoolLabel.setSize(100, 50);
		registerPanel.add(schoolText);
		schoolText.setLocation(130, 180);
		schoolText.setSize(100, 25);
		schoolText.setOpaque(false);

		registerPanel.add(informationLabel);
		informationLabel.setLocation(52, 200);
		informationLabel.setSize(100, 50);
		registerPanel.add(informationText);
		informationText.setLocation(52, 240);
		informationText.setSize(200, 100);
		informationText.setOpaque(false);

		registerPanel.add(registerButtonOK);
		registerButtonOK.setLocation(120, 360);
		registerButtonOK.setSize(68, 30);

		registerPanel.add(picture1Label);// 后加的显示在后面，所以这个必须在最后
		picture1Label.setLocation(0, 0);
		picture1Label.setSize(296, 401);
		registerPanel.validate();
	}

	private void initRegisterResultPanelOk() {
		picture1Label = new JLabel();
		picture1Label.setIcon(new ImageIcon(getClass().getResource("./register2.png")));
		JLabel returnM = new JLabel("注册成功！");
		returnM.setFont(new Font("楷体", 1, 20));
		returnM.setLocation(143, 130);
		returnM.setSize(200, 100);
		JButton returnButton = new JButton("返回");
		returnButton.setLocation(150, 300);
		returnButton.setSize(68, 30);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel("loginPanelCard");
				setSize(355, 320);
			}
		});
		registerResultOkPanel.add(returnM);
		registerResultOkPanel.add(returnButton);

		registerResultOkPanel.setLayout(null);

		registerResultOkPanel.add(picture1Label);// 后加的显示在后面，所以这个必须在最后
		picture1Label.setLocation(0, 0);
		picture1Label.setSize(296, 401);
		registerResultOkPanel.validate();
	}

	private void initRegisterResultPanelNotOk() {
		picture1Label = new JLabel();
		picture1Label.setIcon(new ImageIcon(getClass().getResource("./register2.png")));
		JLabel returnM = new JLabel("注册失败！");
		returnM.setFont(new Font("楷体", 1, 20));
		returnM.setLocation(143, 130);
		returnM.setSize(200, 100);
		JButton returnButton = new JButton("返回");
		returnButton.setLocation(150, 300);
		returnButton.setSize(68, 30);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel("registerPanelCard");
				setSize(355, 320);
			}
		});
		registerResultNotOkPanel.add(returnM);
		registerResultNotOkPanel.add(returnButton);

		registerResultNotOkPanel.setLayout(null);

		registerResultNotOkPanel.add(picture1Label);// 后加的显示在后面，所以这个必须在最后
		picture1Label.setLocation(0, 0);
		picture1Label.setSize(296, 401);
		registerResultNotOkPanel.validate();
	}

	public void switchPanel(String card) {
		CardLayout c;
		c = (CardLayout) this.getContentPane().getLayout();
		c.show(this.getContentPane(), card);
	}
}
