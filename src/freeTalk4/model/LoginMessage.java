package freeTalk4.model;

import java.io.Serializable;

public class LoginMessage implements Serializable {
	private static final long serialVersionUID = 8953228266797400898L;
	private String wrongMessage = null;
	private boolean loginRight = false;

	public String getWrongMessage() {
		return wrongMessage;
	}

	public void setWrongMessage(String wrongMessage) {
		this.wrongMessage = wrongMessage;
	}

	public boolean isLoginRight() {
		return loginRight;
	}

	public void setLoginRight(boolean loginRight) {
		this.loginRight = loginRight;
	}

}
