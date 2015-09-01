package freeTalk4.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TalkViewListener implements ActionListener {

	TalkView view;
	String toWhom = "me";

	public TalkViewListener(TalkView view) {
		super();
		this.view = view;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.send) {
			String ins = new String();
			ins = view.input.getText();
			String sendS = toWhom + "#woshimazhiyu#" + ins;
			System.out.println("talk to :" + toWhom + "[" + ins + "]");

			if (!ins.trim().equals(""))// 这样才能去掉(不能用null，得用""，不是一个含义)，不然这里ins为空，也能发出去||if(ins.trim()!=null)
			{// 也不能用等于号if(ins.trim()!="")
				view.show.append("&&&-Me:\n    " + ins + "\n");
				view.model.talk(sendS);
			}
		} else if (e.getSource() == view.reset) {
			view.input.setText(null);
		} else if (e.getSource() == view.callButton) {
			toWhom = view.usernameText.getText();
			System.out.println("connect To :" + toWhom);
		}

	}

}
