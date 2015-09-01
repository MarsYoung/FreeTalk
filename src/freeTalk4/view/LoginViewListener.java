package freeTalk4.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import freeTalk4.model.User;

public class LoginViewListener implements ActionListener {
	LoginView loginView;

	public LoginViewListener(LoginView loginView) {
		this.loginView = loginView;
	}

	public void actionPerformed(ActionEvent e) {
		User user = new User();

		if (e.getSource() == loginView.loginButton) {
			String username = loginView.usernameText.getText();
			String password = loginView.userpasswordText.getText();
			user.setUsername(username);
			user.setPassword(password);
			if (loginView.model.checkUser(user).isLoginRight()) {
				loginView.talkView.init();
				loginView.dispose();
			} else {
				System.out.println(loginView.model.checkUser(user).getWrongMessage());
			}
		} else if (e.getSource() == loginView.registerButton) {
			loginView.setSize(296, 430);
			loginView.registerPanel.validate();
			loginView.switchPanel("registerPanelCard");
		} else if (e.getSource() == loginView.registerButtonOK) {

			String username = loginView.usrText.getText();
			String password = loginView.pwdText.getText();
			String sex = null;
			if (loginView.sexGroup.getSelectedCheckbox() == loginView.maleBox) {
				sex = "男";
			} else if (loginView.sexGroup.getSelectedCheckbox() == loginView.femaleBox) {
				sex = "女";
			}
			String school = loginView.schoolText.getText();
			String ageS = loginView.ageChoice.getSelectedItem();
			String agestring[] = ageS.split("~");
			int age = Integer.valueOf(agestring[0]);
			StringBuffer information = new StringBuffer(loginView.informationText.getText());

			User user2 = new User();
			user2.setUsername(username);
			user2.setPassword(password);
			user2.setSex(sex);
			user2.setAge(age);
			user2.setSchool(school);
			user2.setInformation(information);

			System.out.println(user2.getUsername() + "  " + user2.getPassword() + "   " + user2.getSchool());

			String returnMessage = loginView.model.register(user2);

			if (returnMessage.equals("OK")) {
				System.out.println("注册成功！");
				loginView.switchPanel("registerResultOkCard");
			} else {
				System.out.println("注册失败！");
				loginView.switchPanel("registerResultNotOkCard");
			}
		}

	}

}
