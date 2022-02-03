import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

// import java.awt.Graphics;
// import java.awt.Image;
// import javax.swing.ImageIcon;

public class MainGameState implements ModeState{

	private Fighter	_fighter;
	public Fighter GetFighter(){return _fighter;}

	
	
	//ステージデータ読み込み用
	private StageAnalyze _analyze;
	public StageAnalyze GetStage(){return _analyze;}

	//ゲーム内タイマー
	private int _gameTimer;
	public int GetTime(){return _gameTimer;}

	private int _nLeft;
	private int nLeft;
	private String stage;
	private int stageNum = 0;
	private int _score;
	private BGM _bgm;

	//敵キャラの管理用
	private EnemyManager _emanager;




// //背景挿入テスト
// 	private Image BackgroundImage = new ImageIcon(MainGameState.class.getResource("../src_sample/ImageFile/bg_uchu_space.jpg")).getImage(); //image
// 	int imageWidth = BackgroundImage.getWidth(null);
//     int imageHeight = BackgroundImage.getHeight(null);
// 	float w = (float)(1.3*imageWidth);
// 	float h = (float)(2*imageHeight);
// 	Image resizeImage = BackgroundImage.getScaledInstance((int)w, (int)h, Image.SCALE_SMOOTH);

	
	public MainGameState()
	{
		init();
	}
	
	//初期化用
	public void init()
	{		
		
			//while (_nLeft  !=0) {
		
			
		

		stageNum ++;
		stage = "stage" + stageNum + ".txt";
		//戦闘機を用意するよー
		_fighter = new Fighter();


		//戦闘機のパラメータだよー
		_fighter.Enable(true);
		_fighter.SetPos(250, 500);
		_fighter.SetVX(16.0f);
		_fighter.SetVY(16.0f);

		// ステージデータだよ
		// ステージデータ読み込みは、ステージをstateパターンで実装してその中でやってもいい
		_analyze	= new StageAnalyze();
		
		_analyze.Analyze(stage);

		// 敵情報だよー
		_emanager = new EnemyManager(this);
		
		//プレイ中の経過時間
		_gameTimer = 0;
		_nLeft = _fighter.GetnLeft();
		_score = _fighter.GetScore();
	

		
		//BGM
	}

	@Override
	public void Show(Graphics2D g2) {
		// TODO Auto-generated method stub

		// 自機と敵表示
		_fighter.Show(g2);
		_emanager.Show(g2);
		
		// 時間の表示

		

		g2.setColor(Color.white);
		g2.drawString("経過時間:" + new Integer(_gameTimer).toString(), 10, 30);
		g2.drawString("残機数:" + new Integer(_nLeft).toString(), 10, 50);
		g2.drawString("stage:" + new Integer(stageNum), 10, 70);
		g2.drawString("Score:" + new Integer(_score).toString(), 10, 90);        //////////////
	}

	@Override
	public void run(GameManager gm) {
		// 1000秒過ぎたら終わり
		// ボスとか追加する時はここをなくしてやらないといけないかな

		//if(_gameTimer == )
		
		if(_gameTimer == 1000) {
			if(stageNum == 2 ){
				gm.ChangeMode(new ExitState()); 
			}else {
				init();
			}	//if cleared -> next stage
		}
		//移動処理
		_fighter.Move();
		
		// 自機ショット射出
		_fighter.Shoot();
		
		// 敵のアップデート
		_emanager.update(_gameTimer++);
		
		//当たり判定
		if(_emanager.HitCheck()){
			_nLeft = _fighter.GetnLeft();
			_score = _fighter.GetScore();
			if (_nLeft ==0) {
				ExitState exitstate = new ExitState();
				exitstate.SetScore(_score);
				gm.ChangeMode(exitstate);
				//gm.ChangeMode(new TitleState());
			}
			// 弾が当たったら終了する
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