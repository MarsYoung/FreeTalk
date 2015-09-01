/**
 * 
 */
package freeTalk4.control;

import freeTalk4.model.Model;
import freeTalk4.view.TalkView;

/**
 * @author mazhiyu
 * 
 */
public class Run {
	Model model;
	TalkView view;
	static Controller controller;

	public static void main(String args[]) {
		controller = new Controller();
		controller.initMVC();
		controller.loginView.init();// 先初始化界面，然后在连接服务器，如果服务器没有的提示
		controller.model.connect();

	}
}
