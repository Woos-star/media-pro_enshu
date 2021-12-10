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

	@Override
	public void init() {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void Show(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.setFont(new Font("MS　ゴシック", Font.BOLD, 16));
		g2.setPaint(Color.yellow);
		g2.drawString("終わりはこのままアプレットウィンドウを閉じてください",10, 100);
		g2.drawString("restartのキーはrです", 10,120);
	}

	@Override
	public void run(GameManager gm) {
		// TODO Auto-generated method stub
		if(m_bKeyR) {
			gm.ChangeMode(new MainGameState());
		}else if (m_bKeyX){
			gm.ChangeMode(new TitleState());
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
