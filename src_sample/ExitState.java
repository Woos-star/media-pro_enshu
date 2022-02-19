//    show 画面, 	R -> restart , x -> title

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

// 終了時のモード
// 終了しないでタイトル戻ってもいい
public class ExitState implements ModeState{

	private boolean m_bKeyR;
	public void KeyR(boolean on){m_bKeyR = on;}
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

		g2.setPaint(Color.red);
		g2.drawString("Game Over",200,300);
		g2.setFont(new Font("MS　ゴシック", Font.BOLD, 30));
		g2.setPaint(Color.yellow);

		g2.setPaint(Color.green);
		g2.setFont(new Font("MS　ゴシック", Font.BOLD, 50));
		g2.drawString("Score: " +  new Integer(_score).toString(), 400, 380); 

		g2.setPaint(Color.yellow);
		g2.setFont(new Font("MS　ゴシック", Font.BOLD, 30));
		g2.drawString("X : タイトルへ",400,440);
		g2.drawString("R : もう一度プレイする",400,480);
		g2.setFont(new Font("MS　ゴシック", Font.BOLD, 16));
	}

	@Override
	public void run(GameManager gm) {
		if(m_bKeyR) {

				gm.ChangeMode(new MainGameState(1));	
	
		}else if (m_bKeyX){
			gm.ChangeMode(new TitleState());			// X 押 -> Title へ
		}
	}

	@Override
	public void KeyPressed(KeyEvent arg0) {
		//TODO Auto-generated method stub
		switch(arg0.getKeyCode()){
			case KeyEvent.VK_R:
			KeyR(true);
			break;
			case KeyEvent.VK_X:
			KeyX(true);
			break;
		}
	}

	@Override
	public void KeyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()){
			case KeyEvent.VK_R:
			KeyR(false);
			break;
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
