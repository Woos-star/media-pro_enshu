import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MainGameState implements ModeState{

	private Fighter	_fighter;
	public Fighter GetFighter(){return _fighter;}
	

	private StageAnalyze _analyze;
	public StageAnalyze GetStage(){return _analyze;}


	private int _gameTimer;
	public int GetTime(){return _gameTimer;}


	private EnemyManager _emanager;
	
	public MainGameState()
	{
		init();
	}
	

	public void init()
	{		

		_fighter = new Fighter();



		_fighter.Enable(true);
		_fighter.SetPos(250, 500);
		_fighter.SetVX(16.0f);
		_fighter.SetVY(16.0f);

		// �X�e�[�W�f�[�^����[
		// �X�e�[�W�f�[�^�ǂݍ��݂́A�X�e�[�W��state�p�^�[���Ŏ������Ă��̒��ł���Ă�����
		_analyze	= new StageAnalyze();
		_analyze.Analyze("stage1.txt");

		// �G��񂾂�[
		_emanager = new EnemyManager(this);
		

		_gameTimer = 0;
	}

	@Override
	public void Show(Graphics2D g2) {
		// TODO Auto-generated method stub

		// ���@�ƓG�̕\��
		_fighter.Show(g2);
		_emanager.Show(g2);
		
		// ���Ԃ̕\��
		g2.setColor(Color.white);
		g2.drawString("経過時間:" + new Integer(_gameTimer).toString(), 10, 30);
	}

	@Override
	public void run(GameManager gm) {
		// 1000�b�߂�����I��� 
		// �{�X�Ƃ��ǉ�����Ƃ��͂����𖳂����Ă��Ȃ��Ƃ����Ȃ�����
		if(_gameTimer == 1000) gm.ChangeMode(new ExitState());

		// �ړ�����	
		_fighter.Move();
		
		// ���@�V���b�g�ˏo
		_fighter.Shoot();
		
		// �G�̃A�b�v�f�[�g
		_emanager.update(_gameTimer++);

		// �����蔻��
		if(_emanager.HitCheck()){
			// �e���G�ɓ���������I������
			gm.ChangeMode(new ExitState());
			//gm.ChangeMode(new TitleState());
		}	
	}
	
	@Override
	public void KeyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		_fighter.KeyPressedAnalyze(arg0);
	}

	@Override
	public void KeyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		_fighter.KeyReleasedAnalyze(arg0);		
	}

	@Override
	public void KeyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		_fighter.KeyTypedAnalyze(arg0);
	}
	

}
