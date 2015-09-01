package freeTalk4.view;

public class TalkViewReceiveThread extends Thread {
	TalkView view;

	public TalkViewReceiveThread(TalkView view) {
		super();
		this.view = view;
	}

	public void run() {
		while (true) {
			String s = view.model.receive();
			if (s.equals("serverlost"))
				break;
			if (s != null) {
				System.out.println(s);
				String sa[] = s.split("#woshimazhiyu#");
				if (sa[1] != null) {
					this.view.show.append("&&&-" + sa[0] + ":\n    " + sa[1] + "\n");
				}
			}
		}
	}
}
