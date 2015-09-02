package freeTalk4.control;

import freeTalk4.model.Model;
import freeTalk4.view.LoginView;
import freeTalk4.view.TalkView;

public class Controller {
	public Model model;
	public TalkView talkView;
	public LoginView loginView;

	// 思想：分而治之，让不同的view 持有controller的不同部分，从而调用model的不同数据与方法
	public Controller() {
		super();
	}

	// 该方法可用在LoginView中，进行MVC模式的建立
	public Controller(Model model, LoginView loginView) {
		this.model = model;
		this.loginView = loginView;
	}

	// 该方法可用在TalkView中，进行MVC模式的建立
	public Controller(Model model, TalkView talkView) {
		this.model = model;
		this.talkView = talkView;
	}

	// 下面的俩个run都是让M V C三个对象互相持有。而且都new除了新对象
	// 在内存中只有一个对象
	public void initMVC() {
		model = new Model(this, loginView, talkView);//这句话初始化的时候，其实loginView和talkView都是空的，后两句才给这两个对象赋值。
		loginView = new LoginView(this, model);
		talkView = new TalkView(this, model);
	}

	public static void main(String args[]) {
		// 初始化m v c模式
		Controller controller = new Controller();
		controller.initMVC();
		// 建立客户端与服务器的连接，并且把该部分操作写在model中，让所有的view 和controller持有
		controller.model.connect();
		// 启动登录页面
		controller.loginView.init();

	}

}
