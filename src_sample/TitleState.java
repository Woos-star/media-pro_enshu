import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class TitleState implements ModeState{

	private final static int START	= 0;
	private final static int END		= 1;
	private int _cursorPos = START;

	// ���C���^�C�g���̈ʒu
	private final static int TITLEPOSX	= 50;
	private final static int TITLEPOSY	= 150;

	// ���C�����j���[�\���ʒu�B�\���Ԋu�B�J�[�\���ʒu�ix���W�̂݁j
	private final static int MENUPOSX		= 200;
	private final static int MENUPOSY		= 280;
	private final static int MENUINTVL	= 50;
	private final static int CURSOR		= 150;

	// �L�[�t���O
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
		// TODO Auto-generated method stub
	}
	
	// �L�[�ړ��B����Ƃ�
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

		// Z�������Ƃ�
		if(m_bKeyZ)
		{
			// �J�[�\���ʒu�ŕ���
			switch(_cursorPos)
			{
				case START:
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

		// �����Ƃ�
		g2.setPaint(Color.yellow);
		g2.drawString("Templete Shooting",TITLEPOSX,TITLEPOSY);

		// ��������
		if(_cursorPos == START)
			g2.setPaint(Color.green);
		else
			g2.setPaint(Color.yellow);
		
		g2.drawString("Game Start",MENUPOSX,MENUPOSY);

		// �����
		if(_cursorPos == END)
			g2.setPaint(Color.green);
		else
			g2.setPaint(Color.yellow);

		g2.drawString("Quit",MENUPOSX,MENUPOSY + MENUINTVL);

		// ���[����
		g2.setPaint(Color.green);
		switch(_cursorPos)
		{
			case START:
			g2.drawString("@",CURSOR,MENUPOSY);
				break;
			case END:
			g2.drawString("@",CURSOR,MENUPOSY + MENUINTVL);
				break;
		}

		// ����\��
		g2.setPaint(Color.yellow);
		g2.setFont(new Font("�l�r �S�V�b�N", Font.BOLD, 20));
		g2.drawString("�����L�[�ŃJ�[�\���ړ��BZ�L�[�Ō���B", 50,600);
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