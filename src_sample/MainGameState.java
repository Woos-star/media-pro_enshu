import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
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


	private int _nLeft;
	private int nLeft;
	private String stage;
	public int stageNum = 0;
	private int _score;
	//private BGM _bgm;

	//敵キャラの管理用
	private EnemyManager _emanager;
	private BossManager _bmanager;

	private Boss[] _boss ;
	boolean bossdie = false;
	//main game 
	//private MainGameState _maingamestate;
	//public MainGameState 

	////////////////////////
	//save data
	// private MainGameState _saved;
	// private MainGameState _loaddata;
	// private int _savedtime;
////////////////////////
	//

	private boolean m_bKeyE;			//save key
	public void KeyE(boolean on){m_bKeyE = on;}
	private boolean m_bKeyR;			//load key
	public void KeyR(boolean on){m_bKeyR = on;}	

	
	// private BGM _bgm;
	
	public MainGameState(int level)
	{
		stageNum = level-1;
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
		_fighter.SetPos(480, 700);
		_fighter.SetVX(16.0f);
		_fighter.SetVY(16.0f);

		// ステージデータ
		// ステージデータ読み込みは、ステージをstateパターンで実装してその中でやってもいい
		_analyze	= new StageAnalyze();
		
		_analyze.Analyze(stage);

		// 敵情報
		_emanager = new EnemyManager(this);
		_bmanager = new BossManager(this);

		_boss = _bmanager.GetBoss();

		//プレイ中の経過時間
		_gameTimer = 0;

		_nLeft = _fighter.GetnLeft();
		_score = _fighter.GetScore();
		//BGM
		//_maingamestate = this;
		// ///BGM 追加

		// _bgm = new BGM();
		// _bgm.start();
		bossdie = false;

	}

	@Override
	public void Show(Graphics2D g2) {
		// TODO Auto-generated method stub

		// 自機と敵表示
		_fighter.Show(g2);
		_emanager.Show(g2);
		_bmanager.Show(g2);
		
		// 時間,残機数,stage
		g2.setColor(Color.white);
		g2.drawString("経過時間:" + new Integer(_gameTimer).toString(), 10, 30);
		g2.drawString("残機数:" + new Integer(_nLeft).toString(), 10, 50);
		g2.drawString("stage:" + new Integer(stageNum), 10, 70);
		g2.drawString("Score:" + new Integer(_score).toString(), 10, 90);   
		//
		////////////////////////
		// g2.setColor(Color.yellow);
		// g2.drawString("e：save",910,30);		//e key save
		// g2.drawString("r：load",910,50);		//r key load
		////////////////////////

	}

	@Override
	public void run(GameManager gm) {
	////////////////////////////////////
		// if(m_bKeyE) {
		// 	_saved = this;
		// 	_savedtime = this._gameTimer;


		// }else if (m_bKeyR){
		// 	if(_saved == null){
		// 		;
		// 	}else{
		// 		_loaddata = _saved;
		// 		_loaddata._gameTimer = _savedtime;
			
		// 		gm.ChangeMode(_loaddata);	
		// 	}			
		// }	
	//////	////////////////////////
		

		//移動処理
		_fighter.Move();
		
		// 自機ショット射出
		_fighter.Shoot();
		
		// 敵のアップデート
		_emanager.update(_gameTimer++);
		_bmanager.update(_gameTimer);

		//当たり判定
		if(_emanager.HitCheck() || _bmanager.HitCheck()){
			_nLeft = _fighter.GetnLeft();
			_score = _fighter.GetScore();
			if (_nLeft ==0) {
				
				
				if(stageNum == 1){
					ExitState exitstate = new ExitState();
					exitstate.SetScore(_score);
					gm.ChangeMode(exitstate);  
				}else {
					ExitState2 exitstate2 = new ExitState2();
					exitstate2.SetScore(_score);
					gm.ChangeMode(exitstate2);
				}
				
				//gm.ChangeMode(new TitleState());
			}
			if(_boss[stageNum-1] == null) {
				;
			}
			else if(!_boss[stageNum-1].isEnable){
				bossdie = true;
			}
			if(bossdie) {
	
					ClearState clear = new ClearState();
					clear.SetScore(_score);
					gm.ChangeMode(clear); 
			}

			// 弾が当たったら終了する

			// 弾が当たったら終了する
		}	

		// if(_bmanager.HitCheck()){
		// 	// _nLeft = _fighter.GetnLeft();         ///////////////////
		// 	// _score = _fighter.GetScore();
        //     if(_nLeft == 0){                      ///////////////////
		// 		if(stageNum == 1){
		// 			gm.ChangeMode(new ExitState());  
		// 		}else {
		// 			gm.ChangeMode(new ExitState2());
		// 		}
				
        //     }       

		// 	// 弾が当たったら終了する
		// }	
	}
	
	@Override
	public void KeyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		_fighter.KeyPressedAnalyze(arg0);
//////////////////
		// switch(arg0.getKeyCode()){
		// 	case KeyEvent.VK_E:
		// 	KeyE(true);
		// 	break;
		// 	case KeyEvent.VK_R:
		// 	KeyR(true);
		// 	break;
		// }
		////////////////////////
	}

	@Override
	public void KeyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		 _fighter.KeyReleasedAnalyze(arg0);	
		 ////////////////////////	
		// switch(arg0.getKeyCode()){
		// 	case KeyEvent.VK_E:
		// 	KeyE(false);
		// 	break;
		// 	case KeyEvent.VK_R:
		// 	KeyR(false);
		// 	break;
		// }
		////////////////////////
	}

	@Override
	public void KeyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		_fighter.KeyTypedAnalyze(arg0);
	}
	

}
