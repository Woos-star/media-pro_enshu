//Enemyの基本設定 + EnemyのBullet 実装

import java.awt.*;
import java.awt.geom.*;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Enemy extends BaseObject{
	private int	m_HP;
	private int m_Def;			// 防御力
	private int m_AppearTime;	// 出現時間
	private int m_bulletType;	// 弾タイプ
	private int m_bulletIntvl;	// 発射間隔
	private int m_bulletSpeed;	// 弾速度

	private Image EnemyImage = new ImageIcon(Fighter.class.getResource("../src_sample/ImageFile/ufo.png")).getImage(); 
	int imageWidth = EnemyImage.getWidth(null);	 
    int imageHeight = EnemyImage.getHeight(null);
	float w = (float)(imageWidth/6 );
	float h = (float)(imageHeight/6 );
	Image resizeImage = EnemyImage.getScaledInstance((int)w, (int)h, Image.SCALE_SMOOTH);

	public final static int BL_1WAY_MON	=	0;			// bullet 種類 1way  
	public final static int BL_8WAY_ALL	= 	1;			// bullet 種類 8way  
	public final static int BL_3WAY_FAN	= 	2;			// bullet 種類 3way  
	public final static int KB_2WAY_DI	=	3;			// bullet 種類 2way  
	
	private EnemyManager _manager = null; // EnemyManager 使用
	
	public Enemy(EnemyManager em)
	{
		super(); 							// BaseObject.BaseObject()
		m_HP = 0;							//初期化
		m_Def = 0;
		m_AppearTime = 0;
		m_bulletType = 0;
		m_bulletIntvl = 0;
		m_bulletSpeed = 0;
	
		_manager = em;					//Enemmy.Enemy(EnemyManager == em)
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

	public void DecreaseHP(int damage)
	{
		m_HP -= (damage - m_Def);

		if(m_HP < 0)
			Enable(false);
	}
	public int GetHP()           ////////////////
    {
        return m_HP;
    }

	//弾生成
	public void Fire()
	{
		if(!isEnable) return;		//if 存在しない

		if(_manager.GetTime() % m_bulletIntvl == 0)		//if あるintervalすぎた-> またfire (短い)

			CreateEimsBullet();
	}

	public void Show(Graphics2D g2)
	{
		if(!isEnable) return;

		//Random random = new Random();
		//int a = random.nextInt(10);
		//if(a > 4)
		g2.drawImage(resizeImage, (int)fX-20, (int)fY-20, null);		
//else g2.drawImage(resizeImage2, (int)fX-20, (int)fY-20, null);		
	}

	//狙い弾を生成
	public void CreateEimsBullet() 				///ここ修正->いろいろな角度のbullet 現在　狙い弾
	{
		Fighter fighter = _manager.GetFighter();		//Fighter 情報
		Bullet[] bullet = _manager.GetBullet();			//Bullet は　配列（多数）
		
		if(!fighter.IsEnable()) return;					//if　Fighter 存在しない
		if(!isEnable) return;							//if　Fighter 存在しない

		if(fY > 1000) return;							//座標　1000すぎた（見えなくなった）-> Bullet生成しない

		for(int i=0; i<EnemyManager.BULLET_MAX; i++)		//bullet 数は　Maxまで -> boss max数　いじる必要 -> enemy managerで
		{
			if(bullet[i].isEnable) continue;		//if bullet[i] が存在している　→ 次のやつ生成

			float dx, dy, d, speed;

			dx = fighter.GetX() - fX;				//比率で投げるため
			dy = fighter.GetY() + 23 - fY;

			speed = this.m_bulletSpeed;

			d = (float)Math.sqrt(dx*dx + dy*dy);   	//距離

			bullet[i].SetPos(fX, fY);
			bullet[i].SetVX((dx/d)*speed);			//比率（三角形）投げる	
			bullet[i].SetVY((dy/d)*speed);

			bullet[i].Enable(true);					//bullet[i] -> とうとう生成　1個

			break;
		}
	}
}
