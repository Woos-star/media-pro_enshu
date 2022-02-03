import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.border.*;
import java.awt.Dimension;
import javax.swing.*;


public class TitleState implements ModeState {


	private final static int START	= 0;
	private final static int CONTINUE		= 1;
	private final static int END		= 2;
	private  static int x = 0;

	private int _cursorPos = START;
	private BGM _bgm;

	//メインタイトルの位置
	private final static int TITLEPOSX	= 220;
	private final static int TITLEPOSY	= 160;

	//メインメニュー表示位置。表示間隔。カーソル位置（x座標のみ）
	private final static int MENUPOSX		= 400;
	private final static int MENUPOSY		= 280;
	private final static int MENUINTVL	= 50;
	private final static int CURSOR		= 150;

	//キーフラグ
	private boolean m_bKeyUp;
	public void KeyUp(boolean on){m_bKeyUp = on;}
	private boolean m_bKeyDown;
	public void KeyDown(boolean on){m_bKeyDown = on;}
	private boolean m_bKeyZ;
	public void KeyZ(boolean on){m_bKeyZ = on;}

	public TitleState()
	{
		init();
	}

	@Override
	public void init() {

	}
	
	//キー移動。決定とか
	public void run(GameManager gm)
	{
		if(m_bKeyUp)
		{
			if(_cursorPos != START)
				_cursorPos--;
		}
		else if(m_bKeyDown)
		{
			if(_cursorPos != END)
				_cursorPos++;
		}

		//z押した時
		if(m_bKeyZ)
		{
			//カーソル位置で分岐
			switch(_cursorPos)
			{
				case START:
					gm.ChangeMode(new MainGameState());
					if(x == 0){
					_bgm = new BGM();
		            _bgm.start1();
					x = x+1;
					}	
					break;
				case CONTINUE:
					gm.ChangeMode(new MainGameState());
					break;
				case END:
					gm.ChangeMode(new ExitState());
					break;
			}
		}
	}

	@Override
	public void Show(Graphics2D g2) {
		g2.setFont(new Font("MS　ゴシック", Font.ITALIC, 70));

		//タイトル
		g2.setPaint(Color.cyan);
		
		g2.drawString("SHOOTING GAME",TITLEPOSX,TITLEPOSY);

		//スタート
		g2.setFont(new Font("Arial", Font.BOLD, 28));
		if(_cursorPos == START)
			g2.setPaint(Color.green);
		
		else
			g2.setPaint(Color.yellow);
		
		g2.drawString("ゲームを始める",MENUPOSX,MENUPOSY);

		//Continue
		if(_cursorPos == CONTINUE)
			g2.setPaint(Color.green);
		
		else
			g2.setPaint(Color.yellow);
		
		g2.drawString("続きから行う",MENUPOSX,MENUPOSY+MENUINTVL);


		//終わり
		if(_cursorPos == END)
			g2.setPaint(Color.green);
				
					else
			g2.setPaint(Color.yellow);

		g2.drawString("やめる",MENUPOSX,MENUPOSY + 2*MENUINTVL);

		// カーソル
		g2.setPaint(Color.green);
		switch(_cursorPos)
		{
			case START:
			g2.drawString("→",CURSOR,MENUPOSY);
				break;
			case CONTINUE:
			g2.drawString("→",CURSOR,MENUPOSY + MENUINTVL);
				break;	
			case END:
			g2.drawString("→",CURSOR,MENUPOSY + 2*MENUINTVL);
				break;
		}

		//操作表示
		g2.setPaint(Color.red);
		g2.setFont(new Font("MS　ゴシック", Font.BOLD, 20));
		g2.drawString("↑↓キーでカーソル移動。zで決定", 350,600);
	}
	
	@Override
	public void KeyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode())
		{
		case KeyEvent.VK_Z:
			KeyZ(true);
			break;
		case KeyEvent.VK_UP:
			KeyUp(true);
			break;
		case KeyEvent.VK_DOWN:
			KeyDown(true);
			break;
		}
	}

	@Override
	public void KeyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode())
		{
		case KeyEvent.VK_Z:
			KeyZ(false);
			break;
		case KeyEvent.VK_UP:
			KeyUp(false);
			break;
		case KeyEvent.VK_DOWN:
			KeyDown(false);
			break;
		}
	}
		
	@Override
	public void KeyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}