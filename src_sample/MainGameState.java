import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MainGameState implements ModeState{

	private Fighter	_fighter;
	public Fighter GetFighter(){return _fighter;}
	
	//ステージデータ読み込み用
	private StageAnalyze _analyze;
	public StageAnalyze GetStage(){return _analyze;}

	//ゲーム内タイマー
	private int _gameTimer;
	public int GetTime(){return _gameTimer;}

	private int nLeft;
	private String stage;
	private int stageNum = 0;

	//敵キャラの管理用
	private EnemyManager _emanager;


	
	public MainGameState()
	{
		init();
	}
	
	//初期化用
	public void init()
	{		

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

		nLeft = 5;
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
		g2.drawString("HP:" + new Integer(nLeft).toString(), 10, 50);
		g2.drawString("stage:" + new Integer(stageNum), 10, 70);

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
			nLeft -=1;
			if (nLeft ==0) {
				gm.ChangeMode(new ExitState());
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
