import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class TitleState implements ModeState{

	private final static int START	= 0;
	private final static int CONTINUE		= 1;
	private final static int END		= 2;

	private int _cursorPos = START;

<<<<<<< HEAD
	// ���C���^�C�g���̈ʒu
	private final static int TITLEPOSX	= 50;
	private final static int TITLEPOSY	= 150;

	// ���C�����j���[�\���ʒu�B�\���Ԋu�B�J�[�\���ʒu�ix���W�̂݁j
	private final static int MENUPOSX		= 200;
=======
	//メインタイトルの位置
	private final static int TITLEPOSX	= 400;
	private final static int TITLEPOSY	= 150;

	//メインメニュー表示位置。表示間隔。カーソル位置（x座標のみ）
	private final static int MENUPOSX		= 400;
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	private final static int MENUPOSY		= 280;
	private final static int MENUINTVL	= 50;
	private final static int CURSOR		= 150;

<<<<<<< HEAD
	// �L�[�t���O
=======
	//キーフラグ
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
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
	
<<<<<<< HEAD
	// �L�[�ړ��B����Ƃ�
=======
	//キー移動。決定とか
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
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

<<<<<<< HEAD
		// Z�������Ƃ�
		if(m_bKeyZ)
		{
			// �J�[�\���ʒu�ŕ���
=======
		//z押した時
		if(m_bKeyZ)
		{
			//カーソル位置で分岐
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
			switch(_cursorPos)
			{
				case START:
					gm.ChangeMode(new MainGameState());
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
		g2.setFont(new Font("Arial", Font.BOLD, 28));

<<<<<<< HEAD
		// �����Ƃ�
		g2.setPaint(Color.yellow);
		g2.drawString("Templete Shooting",TITLEPOSX,TITLEPOSY);

		// ��������
=======
		//タイトル
		g2.setPaint(Color.yellow);
		g2.drawString("Templete Shooting",TITLEPOSX,TITLEPOSY);

		//スタート
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
		if(_cursorPos == START)
			g2.setPaint(Color.green);
		else
			g2.setPaint(Color.yellow);
		
		g2.drawString("Game Start",MENUPOSX,MENUPOSY);

<<<<<<< HEAD
		// �����
=======
		//Continue
		if(_cursorPos == CONTINUE)
			g2.setPaint(Color.green);
		else
			g2.setPaint(Color.yellow);
		
		g2.drawString("Continue",MENUPOSX,MENUPOSY+MENUINTVL);


		//終わり
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
		if(_cursorPos == END)
			g2.setPaint(Color.green);
		else
			g2.setPaint(Color.yellow);

		g2.drawString("Quit",MENUPOSX,MENUPOSY + 2*MENUINTVL);

<<<<<<< HEAD
		// ���[����
=======
		// カーソル
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
		g2.setPaint(Color.green);
		switch(_cursorPos)
		{
			case START:
			g2.drawString("@",CURSOR,MENUPOSY);
				break;
			case CONTINUE:
			g2.drawString("@",CURSOR,MENUPOSY + MENUINTVL);
				break;	
			case END:
			g2.drawString("@",CURSOR,MENUPOSY + 2*MENUINTVL);
				break;
		}

<<<<<<< HEAD
		// ����\��
		g2.setPaint(Color.yellow);
		g2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
		g2.drawString("", 50,600);
=======
		//操作表示
		g2.setPaint(Color.yellow);
		g2.setFont(new Font("MS　ゴシック", Font.BOLD, 20));
		g2.drawString("↑↓キーでカーソル移動。決定", 50,600);
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
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
