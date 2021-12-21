import java.awt.*;
import java.awt.geom.*;

public class Enemy extends BaseObject{
	private int	m_HP;
	private int m_Def;		
	private int m_AppearTime;	
	private int m_bulletType;	
	private int m_bulletIntvl;	
	private int m_bulletSpeed;	

	public final static int BL_1WAY_MON	=	0;
	public final static int BL_8WAY_ALL	= 	1;
	public final static int BL_3WAY_FAN	= 	2;
	public final static int KB_2WAY_DI	=	3;
	
	private EnemyManager _manager = null;
	
	public Enemy(EnemyManager em)
	{
		super();
		m_HP = 0;
		m_Def = 0;
		m_AppearTime = 0;
		m_bulletType = 0;
		m_bulletIntvl = 0;
		m_bulletSpeed = 0;
	
		_manager = em;
	}


	public int GetAppearTime()
	{
		return m_AppearTime;
	}

	public void SetHP(int hp)
	{
		m_HP = hp;
	}

	public void SetDEF(int def)
	{
		m_Def = def;
	}

	public void SetBulletType(int type)
	{
		m_bulletType = type;
	}

	public void SetAppearTime(int apptime)
	{
		m_AppearTime = apptime;
	}

	public void SetBulletIntvl(int interval)
	{
		m_bulletIntvl = interval;
	}

	public void SetBulletSpeed(int Speed)
	{
		m_bulletSpeed = Speed;
	}

	public void DecreaseHP()
	{
		m_HP -= (10 - m_Def);

		if(m_HP < 0)
			Enable(false);
	}

	
	public void Fire()
	{
		if(!isEnable) return;

		if(_manager.GetTime() % m_bulletIntvl == 0)
			CreateEimsBullet();
	}

	public void Show(Graphics2D g2)
	{
		if(!isEnable) return;

		g2.setPaint(Color.blue);
		g2.fill(new Ellipse2D.Double(fX - 20f, fY - 10f, 20f, 20f));
		g2.fill(new Ellipse2D.Double(fX - 10f, fY - 20f, 20f, 20f));
		g2.fill(new Ellipse2D.Double(fX, fY - 10f, 20f, 20f));
		g2.fill(new Ellipse2D.Double(fX - 10f, fY, 20f, 20f));
	}

	
	public void CreateEimsBullet()
	{
		Fighter fighter = _manager.GetFighter();
		Bullet[] bullet = _manager.GetBullet();
		
		if(!fighter.IsEnable()) return;
		if(!isEnable) return;

		if(fY > 240) return;

		for(int i=0; i<EnemyManager.BULLET_MAX; i++)
		{
			if(bullet[i].isEnable) continue;

			float dx, dy, d, speed;

			dx = fighter.GetX() - fX;
			dy = fighter.GetY() + 23 - fY;

			speed = this.m_bulletSpeed;

			d = (float)Math.sqrt(dx*dx + dy*dy);

			bullet[i].SetPos(fX, fY);
			bullet[i].SetVX((dx/d)*speed);
			bullet[i].SetVY((dy/d)*speed);

			bullet[i].Enable(true);

			break;
		}
	}
}
