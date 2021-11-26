import java.awt.Graphics2D;

public class EnemyManager {
	public final static int ENEMY_MAX	= 	10;
	public final static int BULLET_MAX		=	30;
	
	private Enemy[]	_enemy = new Enemy[ENEMY_MAX];
	public Enemy[] GetEnemy(){return _enemy;}
	private Bullet[] _bullet = new Bullet[BULLET_MAX];
	public Bullet[] GetBullet(){ return _bullet;}
	
	private Fighter _fighter = null;
	public Fighter GetFighter(){return _fighter;}
	
	private StageAnalyze _stage = null;
	public StageAnalyze GetStage(){return _stage;}
	
	private int _time = 0;
	public int GetTime(){return _time;}
	
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
	}

	public void update(int timer)
	{
		_time = timer;
		
		EnemyCreate();
		BulletCreate();
		
		EnemyMove();
		BulletMove();
	}
	
	public void Show(Graphics2D g2)
	{
		EnemyShow(g2);
		BulletShow(g2);
	}
	
	// “GˆÚ“®
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

	// ’eˆÚ“®
	public void BulletMove()
	{
		for(int i=0; i<BULLET_MAX; i++)
		{
			if(_bullet[i] == null) return;

			if(!_bullet[i].IsEnable()) continue;

			_bullet[i].Move();

			if(((_bullet[i].GetX() >= 530)||(_bullet[i].GetX() <= -50))||((_bullet[i].GetY() >= 690)||(_bullet[i].GetY() <= -50)))
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
						_enemy[j].Enable(true);
						break;
					}	
					else if(_enemy[j].IsEnable()) continue;

					_enemy[j] = _stage.GetTemporaryEnemy(this,i);
					_enemy[j].Enable(true);
					break;
				}
			}
		}
	}

	// “GƒVƒ‡ƒbƒg
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
	
	//
	public boolean HitCheck()
	{
		boolean rtn = false;
		
		HitCheckEnemyAndShot();
		rtn = HitCheckEnemyAndFighter() | HitCheckBulletAndFighter();
		
		return rtn;
	}

	// “G‚ÆŽ©‹@‚Ì”»’è
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
				_enemy[i].DecreaseHP();
				_fighter.Enable(false);
				return true;
			}
		}
			
		return false;
	}

	// “G‚ÆŽ©’e‚Ì”»’è
	private void HitCheckEnemyAndShot()
	{
		for(int i=0;i<_fighter.GetNumShot();i++)
		{
			// ’e—LŒø‚Å‚È‚©‚Á‚½‚çŽŸ‚Ö
			if(!_fighter.GetShot()[i].IsEnable()) continue;

			// ’e—LŒø‚¾‚Á‚½‚ç“G‘S•”‚Æ’e‚ÉŠÖ‚µ‚Ä‚Ì”»’è‚ð‚·‚é
			for(int j=0;j<ENEMY_MAX;j++)
			{
				// ‰æ–Ê“à‚É“G‹‚È‚©‚Á‚½‚ç”ò‚Î‚·
				if(_enemy[j] == null || !_enemy[j].IsEnable()) continue;
					float dx, dy, width, height;
				dx = _enemy[j].GetX() - _fighter.GetShot()[i].GetX() - 5;
				dy = _enemy[j].GetY() - _fighter.GetShot()[i].GetY();

				width = 50;
				height = 60;

				if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
				{
					_enemy[j].DecreaseHP();
					_fighter.GetShot()[i].Enable(false);
					break;
				}
			}
		}
	}

	// “G’e‚ÆŽ©‹@‚Ì”»’è
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

			// “–‚½‚è‚Ü‚µ‚½
			if((Math.abs(dx) <= width)&&(Math.abs(dy) <= height))
			{
				_bullet[i].Enable(false);
				_fighter.Enable(false);
				return true;
			}
		}		
		return false;
	}
}
