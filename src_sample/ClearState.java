//    show 画面, 	R -> restart , x -> title

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

// 終了時のモード
// 終了しないでタイトル戻ってもいい
public class ClearState implements ModeState{


	private boolean m_bKeyX;
	public void KeyX(boolean on){m_bKeyX = on;}	

	private int _score = 0;
	public void SetScore(int score){
		_score = score;
	}

	@Override
	public void init() {
	}
	
	@Override
	public void Show(Graphics2D g2) {

		g2.setFont(new Font("MS　ゴシック", Font.BOLD, 100));

		g2.drawString("Game Clear",200,300);
		g2.setPaint(Color.green);
		g2.setFont(new Font("MS　ゴシック", Font.BOLD, 50));
		g2.drawString("Score: " +  new Integer(_score).toString(), 400, 380); 
		g2.setPaint(Color.yellow);
		g2.drawString("X : Titleへ",400,460);
	}

	@Override
	public void run(GameManager gm) {
		
		if (m_bKeyX){
			gm.ChangeMode(new TitleState());			// X 押 -> Title へ
		}
	}

	@Override
	public void KeyPressed(KeyEvent arg0) {
		//TODO Auto-generated method stub
		switch(arg0.getKeyCode()){
			case KeyEvent.VK_X:
			KeyX(true);
			break;
		}
	}

	@Override
	public void KeyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()){
			case KeyEvent.VK_X:
			KeyX(false);
			break;
		}
	}

	@Override
	public void KeyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
