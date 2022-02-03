/*
 *作成2004/08/29
 * この生成されたコメントの挿入されるテンプレ０とを変更するため
 * window > 設定 > Java >　コード生成 > コードとコメント
 */

/**
 * @author Administrator
 *
 */
import java.awt.*;
import java.awt.geom.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.Random;

public class Enemy extends BaseObject{
	private int	m_HP;
	private int m_Def;			// 防御力
	private int m_AppearTime;	// 出現時間
	private int m_bulletType;	// 弾タイプ
	private int m_bulletIntvl;	// 発射間隔
	private int m_bulletSpeed;	// 弾速度

	public final static int BL_1WAY_MON	=	0;
	public final static int BL_8WAY_ALL	= 	1;
	public final static int BL_3WAY_FAN	= 	2;
	public final static int KB_2WAY_DI	=	3;
	
	private EnemyManager _manager = null;

	private Image EnemyImage = new ImageIcon(Fighter.class.getResource("../src_sample/ImageFile/ufo.png")).getImage(); //image
	int imageWidth = EnemyImage.getWidth(null);
    int imageHeight = EnemyImage.getHeight(null);
	float w = (float)(imageWidth /5);
	float h = (float)(imageHeight /5);
	Image resizeImage = EnemyImage.getScaledInstance((int)w, (int)h, Image.SCALE_SMOOTH);
	
	

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
		if(!isEnable) return;

		if(_manager.GetTime() % m_bulletIntvl == 0)
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
	public void CreateEimsBullet()
	{
		Fighter fighter = _manager.GetFighter();
		Bullet[] bullet = _manager.GetBullet();
		
		if(!fighter.IsEnable()) return;
		if(!isEnable) return;

		if(fY > 1000) return;

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