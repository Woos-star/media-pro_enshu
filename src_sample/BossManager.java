//　敵、Bullet 作り・move  + 判定（ (fighter,enemy)(fighter,enemy bullet),(enemy,fighter bullet)）

import java.awt.Graphics2D;
//import java.util.concurrent.ThreadLocalRandom; 

public class BossManager {
	public final static int BOSS_MAX	= 	3;			//画面に表示されるEnemyの数 MAX
	public final static int BULLET_MAX		=	200;		//Enemy 一体が生成する（画面に表示する）BulletのMAX

	private Boss[]	_boss = new Boss[BOSS_MAX];		//boss数
	public Boss[] GetBoss(){return _boss;}			//_boss をget

	private Bullet[] _bullet = new Bullet[BULLET_MAX];	//Bullet 配列
	public Bullet[] GetBullet(){ return _bullet;}		//_bullet をget
	
	private Fighter _fighter = null;					//_fighter = 無 
	public Fighter GetFighter(){return _fighter;}		//_fighter をget
	
	private StageAnalyze _stage = null;					//_stage = 無
	public StageAnalyze GetStage(){return _stage;}		//_stage をget
	
	private int _time = 0;								//_time = 0 setting

    private MainGameState _maingamestate = null;
    public MainGameState GetMain(){return _maingamestate;}

	public int GetTime(){return _time;}					//_time を get
	
    
	public BossManager(MainGameState main)
	{
		_fighter = main.GetFighter();			//fighterとstage はMainGameStateから　取得
		_stage = main.GetStage();
        _maingamestate = main;
		init();									//bullet　作り開始
	}
	
	public void init()							//Bullet を 作り
	{		
		for(int i=0;i<BULLET_MAX;i++)
		{
			_bullet[i] = new Bullet();
		}
	}

	public void update(int timer)		//update 24 fps(多分？)		
	{
		_time = timer;					//timer
		
		BossCreate();					//敵
		BulletCreate();					//bullet
      

		
		BossMove();					//boss move
		BulletMove();
	}
	
	public void Show(Graphics2D g2)
	{
		BossShow(g2);
		BulletShow(g2);
	}
	
	// 敵移動
	public void BossMove()
	{
		for(int i=_maingamestate.stageNum-1; i<_maingamestate.stageNum; i++)
		{
			if(_boss[i] == null) return;			//i がない　->moveしない

			if(!_boss[i].IsEnable()) continue;		//i がない　->moveしない

			_boss[i].Move();

			// if(((_boss[i].GetX() >= 1100)||(_boss[i].GetX() <= -100))||((_boss[i].GetY() >= 1100)||(_boss[i].GetY() <= -100))) 
			// 	_boss[i].Enable(false);  //画面表示から見えない  
		}
	}


	// 弾移動
	public void BulletMove()
	{
		for(int i=0; i<BULLET_MAX; i++)
		{
			if(_bullet[i] == null) return;		//bullet[i] なし->動作しない

			if(!_bullet[i].IsEnable()) continue;	//bullet[i] なし->動作しない

			_bullet[i].Move();

			if(((_bullet[i].GetX() >= 1050)||(_bullet[i].GetX() <= -50))||((_bullet[i].GetY() >= 1050)||(_bullet[i].GetY() <= -50)))
				_bullet[i].Enable(false);
		}
	}
 
	

	public void BossCreate()
	{
		for(int i=0; i<_stage.GetStringNumber_b(); i++)				//number
		{
			if(Integer.parseInt(_stage.GetScenario_b().get(i)[0]) == _time)		//	
			{
				if(_boss[_maingamestate.stageNum-1] == null)		
				{
					_boss[_maingamestate.stageNum-1] = _stage.GetTemporaryBoss(this,i);
					_boss[_maingamestate.stageNum-1].Enable(true);
					break;
				}	
				else if(_boss[_maingamestate.stageNum-1].IsEnable()) continue;

				_boss[_maingamestate.stageNum-1] = _stage.GetTemporaryBoss(this,i);
				_boss[_maingamestate.stageNum-1].Enable(true);          
				break;
			}
		}
	}

	// 敵ショット
	public void BulletCreate()
	{
		int i=_maingamestate.stageNum-1;
			if(_boss[i] == null) return;			//enemy が存在しない
			if(!_boss[i].IsEnable()) ;
			if(i==0)_boss[_maingamestate.stageNum-1].Fire();				//else Fire
			else if(i==1)_boss[_maingamestate.stageNum-1].Fire2();				//else Fire
			else{_boss[i].Fire3();}				//else Fire
	}
	
	public void BossShow(Graphics2D g2)
	{
	
		if(_boss[_maingamestate.stageNum-1] == null) return;			//存在しない
		// if(!_boss[_maingamestate.stageNum-1].IsEnable()) ;
			
		_boss[_maingamestate.stageNum-1].Show(g2);						//存在 - > show
	}

	public void BulletShow(Graphics2D g2)
	{
		for(int i=0; i<BULLET_MAX; i++)
		{
			if(_bullet[i] == null) return;
			if(!_bullet[i].IsEnable()) continue;
			if(_maingamestate.stageNum-1 == 0){
                _bullet[i].Show(g2);
            }else {
                _bullet[i].Show2(g2);
            }
			
		}
	}

	public boolean HitCheck()
	{
		boolean rtn = false;
		
		

		rtn = HitCheckBossAndShot() | HitCheckBossAndFighter() | HitCheckBulletAndFighter();  //////////		//enemy とfighter の hit　,bullet とfighter の hit　check ->1
		
		return rtn;		//hit時 1 を返す
	}

	// 敵、自機　判定
	private boolean HitCheckBossAndFighter()
	{
		if(!_fighter.IsEnable()) return false;			// Fighter X ->false

		for(int i=0; i<BOSS_MAX; i++)
		{
			if(_boss[i] == null || !_boss[i].IsEnable()) continue;  //enemy X ->continue

			float dx, dy, width, height;
			
			dx = _boss[i].GetX() - _fighter.GetX();
			dy = _boss[i].GetY() - _fighter.GetY() - 23;

			width  = 30;
			height = 70;

			if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
			{
				_boss[i].DecreaseHP(1);				//enemy HP 減少

				_fighter.DecresenLeft();			//fighter Decrease Hp
				return true;
			}
		}
		return false;
    }


	// 敵、自弾　判定
	private boolean HitCheckBossAndShot()
	{
		for(int i=0;i<_fighter.GetNumShot();i++)
		{
			// 弾有効でなかったら次へ
			if(!_fighter.GetShot()[i].IsEnable()) continue;

			// 弾有効だったら敵全部と弾に関しての判定をする
			for(int j=0;j<BOSS_MAX;j++)
			{
				
				if(_boss[j] == null || !_boss[j].IsEnable()) continue; 			 // 画面内に敵いなかったら飛ばす
				
				float dx, dy, width, height;
				dx = _boss[j].GetX() - _fighter.GetShot()[i].GetX() +150;			
				dy = _boss[j].GetY() - _fighter.GetShot()[i].GetY();

				width = 100;
				height = 100;

				if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
				{
					_boss[j].DecreaseHP(_fighter.GetDamage()); 
					if(_boss[_maingamestate.stageNum-1].GetHP()<0){ 
						_fighter.IncreaseScore(10);
					}
					_fighter.GetShot()[i].Enable(false);
					return true;
				}
			}
		}
		return false;
	}

	// 敵弾、自機　判定
	private boolean HitCheckBulletAndFighter()
	{
		if(!_fighter.IsEnable()) return false;

		for(int i=0; i<BULLET_MAX; i++)
		{
			if(_bullet[i] == null || !_bullet[i].IsEnable()) continue;
				float dx, dy, width, height;
			dx = _bullet[i].GetX() - _fighter.GetX();
			dy = _bullet[i].GetY() - _fighter.GetY() - 23;

			width = 20/2;
			height = 28/2;

			// 当たりました
			if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
			{
				_bullet[i].Enable(false);
				_fighter.DecresenLeft();
				return true;
			}
		}		
		return false;
	}
}
