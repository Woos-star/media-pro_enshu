//　敵、Bullet 作り・move  + 判定（ (fighter,enemy)(fighter,enemy bullet),(enemy,fighter bullet)）

import java.awt.Graphics2D;
import java.util.concurrent.ThreadLocalRandom; 
import java.util.Random;

public class EnemyManager {
	public final static int ENEMY_MAX	= 	10;			//画面に表示されるEnemyの数 MAX
	public final static int BULLET_MAX		=	30;		//Enemy 一体が生成する（画面に表示する）BulletのMAX
	public final static int ITEM_MAX = 2;

	private Enemy[]	_enemy = new Enemy[ENEMY_MAX];		//Enemy配列
	public Enemy[] GetEnemy(){return _enemy;}			//_enemy をget
	private Bullet[] _bullet = new Bullet[BULLET_MAX];	//Bullet 配列
	public Bullet[] GetBullet(){ return _bullet;}		//_bullet をget
	private Item[] _item = new Item[ITEM_MAX]; ////////////
    public Item[] GetItem(){return _item;} /////////////
	
	private Fighter _fighter = null;					//_fighter = 無 
	public Fighter GetFighter(){return _fighter;}		//_fighter をget
	
	private StageAnalyze _stage = null;					//_stage = 無
	public StageAnalyze GetStage(){return _stage;}		//_stage をget
	
	private int _time = 0;								//_time = 0 setting
	public int GetTime(){return _time;}					//_time を get
	private int t1,t2,t3,t4;
	
	public EnemyManager(MainGameState main)
	{
		_fighter = main.GetFighter();			//fighterとstage はMainGameStateから　取得
		_stage = main.GetStage();
		init();									//bullet　作り開始
	}
	
	public void init()							//Bullet を 作り
	{		
		for(int i=0;i<BULLET_MAX;i++)
		{
			_bullet[i] = new Bullet();
		}
		for(int i=0;i<ITEM_MAX;i++) /////////////
        {                           /////////////
            _item[i] = new Item();  /////////////
        }                    
		       /////////////
	}

	public void update(int timer)		//update 24 fps(多分？)		
	{
		_time = timer;					//timer         
        if(_time==t3+3){              //////////
            _fighter.SetImage1();
        }
        if(_time==t4+100 & t4 > 0){             //////////
            _fighter.DecreaseDamage();
        }
		EnemyCreate();
		BulletCreate();
		ItemCreate();

		EnemyMove();
		BulletMove();
		ItemMove();
	}
	
	public void Show(Graphics2D g2)
	{
		EnemyShow(g2);
		BulletShow(g2);
		ItemShow(g2);       /////////////
	}
	
	// 敵移動
	public void EnemyMove()
	{
		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null) return;			//i がない　->moveしない

			if(!_enemy[i].IsEnable()) continue;		//i がない　->moveしない

			_enemy[i].Move();

			if(((_enemy[i].GetX() >= 1050)||(_enemy[i].GetX() <= -50))||((_enemy[i].GetY() >= 1050)||(_enemy[i].GetY() <= -50))) 
				_enemy[i].Enable(false);  //画面表示から見えない  Enemy !Enable
		}
	}

	public void ItemMove()            /////////////////
    {
        for(int i=0; i<ITEM_MAX; i++)
        {
            if(_item[i] == null) return;
 
            if(!_item[i].IsEnable()) continue;
 
            _item[i].Move();
 
            if(((_item[i].GetX() >= 1000)||(_item[i].GetX() <= -50))||((_item[i].GetY() >= 1000)||(_item[i].GetY() <= -50)))
                _item[i].Enable(false);
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
 
	

	public void EnemyCreate()
	{
		// 
		for(int i=0; i<_stage.GetStringNumber(); i++)				//number
		{
			if(Integer.parseInt(_stage.GetScenario().get(i)[0]) == _time)		//	
			{
				for(int j=0; j<ENEMY_MAX; j++)
				{
					if(_enemy[j] == null)			//enemy j がない
					{
						_enemy[j] = _stage.GetTemporaryEnemy(this,i);
						_enemy[j].SetHP(30);
						_enemy[j].Enable(true);
						break;
					}	
					else if(_enemy[j].IsEnable()) continue;

					_enemy[j] = _stage.GetTemporaryEnemy(this,i);
					_enemy[j].SetHP(30);
					_enemy[j].Enable(true);
					break;
				}
			}
		}
	}

	public void ItemCreate(){          /////////////////
        if(_time % 200 == 0)
        {
            int randomnumber1 = ThreadLocalRandom.current().nextInt(100);
            t1 = _time + randomnumber1;
			int randomnumber2 = ThreadLocalRandom.current().nextInt(100);
            t2 = _time + randomnumber2;
        }
        if(_time == t1)
        {
                _item[0].SetVX(8);
                _item[0].SetVY(8);
                _item[0].SetPos(0,0);
                _item[0].Enable(true);
		}
			if(_time == t2)
			{
				    _item[1].SetImage();
					_item[1].SetVX(-8);
					_item[1].SetVY(8);
					_item[1].SetPos(930,0);
					_item[1].Enable(true);
			}
	}



	// 敵ショット
	public void BulletCreate()
	{
		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null) return;			//enemy が存在しない
			if(!_enemy[i].IsEnable()) continue;

			_enemy[i].Fire();				//else Fire
		}
	}
	
	public void EnemyShow(Graphics2D g2)
	{
		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null) return;			//存在しない
			if(!_enemy[i].IsEnable()) continue;
			
			_enemy[i].Show(g2);						//存在 - > show
		}
	}

	public void BulletShow(Graphics2D g2)
	{
		for(int i=0; i<BULLET_MAX; i++)
		{
			if(_bullet[i] == null) return;
			if(!_bullet[i].IsEnable()) continue;
			
			_bullet[i].Show(g2);
		}
	}

	public void ItemShow(Graphics2D g2)        //////////////
    {
        for(int i=0; i<ITEM_MAX; i++)
        {
            if(_item[i] == null) return;
            if(!_item[i].IsEnable()) continue;

            _item[i].Show(g2);
        }
    }


	
	//
	public boolean HitCheck()
	{
		boolean rtn = false;
		
		rtn = HitCheckEnemyAndShot() | HitCheckItemAndFighter() | HitCheckEnemyAndFighter() | HitCheckBulletAndFighter();
		return rtn;		//hit時 1 を返す
	}

	// 敵、自機　判定
	private boolean HitCheckEnemyAndFighter()
	{
		if(!_fighter.IsEnable()) return false;			// Fighter X ->false

		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null || !_enemy[i].IsEnable()) continue;  //enemy X ->continue

			float dx, dy, width, height;
			
			dx = _enemy[i].GetX() - _fighter.GetX();
			dy = _enemy[i].GetY() - _fighter.GetY() - 23;

			width  = 30;
			height = 70;

			if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
			{
				_enemy[i].Enable(false);
				_fighter.DecresenLeft();
				_fighter.SetImage2();
				t3 = _time;

				return true;
			}
		}
			
		return false;
	}
	private boolean HitCheckItemAndFighter()          ////////////////
    {
        if(!_fighter.IsEnable()) return false;

        for(int i=0; i<ITEM_MAX; i++)
        {
            if(_item[i] == null || !_item[i].IsEnable()) continue;

            float dx, dy, width, height;

            dx = _item[i].GetX() - _fighter.GetX();
            dy = _item[i].GetY() - _fighter.GetY() - 23;

            width  = 30;
            height = 70;

            if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
            {
                _item[i].Enable(false);
                if(i==0){ _fighter.IncreasenLeft(); }      
                else{ _fighter.IncreaseDamage(); t4 = _time;}  
                return true;
            }
        }

        return false;
    }


	// 敵、自弾　判定
	private boolean HitCheckEnemyAndShot()
	{
		for(int i=0;i<_fighter.GetNumShot();i++)
		{
			// 弾有効でなかったら次へ
			if(!_fighter.GetShot()[i].IsEnable()) continue;

			// 弾有効だったら敵全部と弾に関しての判定をする
			for(int j=0;j<ENEMY_MAX;j++)
			{
				
				if(_enemy[j] == null || !_enemy[j].IsEnable()) continue; 			 // 画面内に敵いなかったら飛ばす
				
				float dx, dy, width, height;
				dx = _enemy[j].GetX() - _fighter.GetShot()[i].GetX() - 5;			
				dy = _enemy[j].GetY() - _fighter.GetShot()[i].GetY();

				width = 50;
				height = 60;

				if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
				{
					_enemy[j].DecreaseHP(_fighter.GetDamage());
					if(_enemy[j].GetHP()<0){ _fighter.IncreaseScore(1);

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

			width = 20;
			height = 28;

			// 当たりました
			if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
			{
				_bullet[i].Enable(false);
				_fighter.DecresenLeft();
				_fighter.SetImage2();        ////////////
                t3 = _time;  
				return true;
			}
		}		
		return false;
	}
}
