import java.awt.Graphics2D;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

public class EnemyManager {
	public final static int ENEMY_MAX	= 	10;
	public final static int BULLET_MAX		=	30;
	public final static int ITEM_MAX = 2;

	private Enemy[]	_enemy = new Enemy[ENEMY_MAX];
	public Enemy[] GetEnemy(){return _enemy;}
	private Bullet[] _bullet = new Bullet[BULLET_MAX];
	public Bullet[] GetBullet(){ return _bullet;}
	private Item[] _item = new Item[ITEM_MAX]; ////////////
    public Item[] GetItem(){return _item;} /////////////

	private Fighter _fighter = null;
	public Fighter GetFighter(){return _fighter;}
	
	private StageAnalyze _stage = null;
	public StageAnalyze GetStage(){return _stage;}
	
	private int _time = 0;
	public int GetTime(){return _time;}
	private int t1,t2,t3,t4;

	public EnemyManager(MainGameState main)
	{
		_fighter = main.GetFighter();
		_stage = main.GetStage();
		init();
	}
	
	public void init()
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

	public void update(int timer)
	{
		_time = timer;
		
		_time = timer;            
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
		ItemShow(g2);
	}
	
	// 敵移動
	public void EnemyMove()
	{
		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null) return;

			if(!_enemy[i].IsEnable()) continue;

			_enemy[i].Move();

			if(((_enemy[i].GetX() >= 530)||(_enemy[i].GetX() <= -50))||((_enemy[i].GetY() >= 690)||(_enemy[i].GetY() <= -50)))
				_enemy[i].Enable(false);
		}
	}

	public void ItemMove()            /////////////////
    {
        for(int i=0; i<ITEM_MAX; i++)
        {
            if(_item[i] == null) return;
 
            if(!_item[i].IsEnable()) continue;
 
            _item[i].Move();
 
            if(((_item[i].GetX() >= 530)||(_item[i].GetX() <= -50))||((_item[i].GetY() >= 690)||(_item[i].GetY() <= -50)))
                _item[i].Enable(false);
        }
    }
 

	// 弾移動
	public void BulletMove()
	{
		for(int i=0; i<BULLET_MAX; i++)
		{
			if(_bullet[i] == null) return;

			if(!_bullet[i].IsEnable()) continue;

			_bullet[i].Move();

			if(((_bullet[i].GetX() >= 1000)||(_bullet[i].GetX() <= -50))||((_bullet[i].GetY() >= 1000)||(_bullet[i].GetY() <= -50)))
				_bullet[i].Enable(false);
		}
	}
 
	public void EnemyCreate()
	{
		// 
		for(int i=0; i<_stage.GetStringNumber(); i++)
		{
			if(Integer.parseInt(_stage.GetScenario().get(i)[0]) == _time)
			{
				for(int j=0; j<ENEMY_MAX; j++)
				{
					if(_enemy[j] == null)
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
					_item[1].SetPos(530,0);
					_item[1].Enable(true);
			}
	}
	

	// 敵ショット
	public void BulletCreate()
	{
		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null) return;
			if(!_enemy[i].IsEnable()) continue;

			_enemy[i].Fire();
		}
	}
	
	public void EnemyShow(Graphics2D g2)
	{
		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null) return;
			if(!_enemy[i].IsEnable()) continue;
			
			
			_enemy[i].Show(g2);
			
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
		
		
		rtn = HitCheckEnemyAndShot()|HitCheckItemAndFighter() | HitCheckEnemyAndFighter() | HitCheckBulletAndFighter();
		
		
		
		return rtn;
	}

	// 敵と自機の判定
	private boolean HitCheckEnemyAndFighter()
	{
		if(!_fighter.IsEnable()) return false;

		for(int i=0; i<ENEMY_MAX; i++)
		{
			if(_enemy[i] == null || !_enemy[i].IsEnable()) continue;

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


	// 敵と自弾の判定
	private boolean HitCheckEnemyAndShot()
	{
		for(int i=0;i<_fighter.GetNumShot();i++)
		{
			// 弾有効でなかったら次へ
			if(!_fighter.GetShot()[i].IsEnable()) continue;

			// 弾有効だったら敵全部と弾に関しての判定をする
			for(int j=0;j<ENEMY_MAX;j++)
			{
				// 外面内に敵いなかったら飛ばす
				if(_enemy[j] == null || !_enemy[j].IsEnable()) continue;
					float dx, dy, width, height;
				dx = _enemy[j].GetX() - _fighter.GetShot()[i].GetX() - 5;
				dy = _enemy[j].GetY() - _fighter.GetShot()[i].GetY();

				width = 50;
				height = 60;

				if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
				{
					_enemy[j].DecreaseHP(_fighter.GetDamage());
					if(_enemy[j].GetHP()<0){ _fighter.IncreaseScore();
						
					}
					_fighter.GetShot()[i].Enable(false);
					return true;
				}
			}
		}
		return false;
	}

	// 敵弾と自機の判定
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