package freeTalk4.model;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -2218010940837374539L;
	private int id;
	private String username;
	private String password;
	private String sex;
	private int age;
	private String school;
	private StringBuffer information;

	public String getUsername() {
		return username;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public StringBuffer getInformation() {
		return information;
	}

	public void setInformation(StringBuffer information) {
		this.information = information;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

}
