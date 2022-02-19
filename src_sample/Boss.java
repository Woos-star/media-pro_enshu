//Enemyの基本設定 + EnemyのBullet 実装

import java.awt.*;
import java.awt.geom.*;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

import java.lang.*;
import java.lang.Math;


public class Boss extends BaseObject{
	private int	m_HP;
	private int m_Def;			// 防御力
	private int m_AppearTime;	// 出現時間
	private int m_bulletType;	// 弾タイプ
	private int m_bulletIntvl;	// 発射間隔
	private int m_bulletSpeed;	// 弾速度

    private MainGameState _maingamestate;

    //boss image
    private Image Boss1Image = new ImageIcon(Fighter.class.getResource("../src_sample/ImageFile/boss1.png")).getImage();

	int imageWidth1 = Boss1Image.getWidth(null);	 //fighter の大きさ調節
    int imageHeight1 = Boss1Image.getHeight(null);
	float w1 = (float)(imageWidth1*2 );
	float h1 = (float)(imageHeight1*2 );
	Image resizeImage1 = Boss1Image.getScaledInstance((int)w1, (int)h1, Image.SCALE_SMOOTH);
    
    private Image Boss2Image = new ImageIcon(Fighter.class.getResource("../src_sample/ImageFile/boss2.png")).getImage(); 

	int imageWidth2 = Boss2Image.getWidth(null);	 //fighter の大きさ調節
    int imageHeight2 = Boss2Image.getHeight(null);
	float w2 = (float)(imageWidth1*1.5 );
	float h2 = (float)(imageHeight1*1.5 );
	Image resizeImage2 = Boss2Image.getScaledInstance((int)w2, (int)h2, Image.SCALE_SMOOTH);


	public final static int BL_1WAY_MON	=	0;			// bullet 種類 1way  
	public final static int BL_8WAY_ALL	= 	1;			// bullet 種類 8way  
	public final static int BL_3WAY_FAN	= 	2;			// bullet 種類 3way  
	public final static int KB_2WAY_DI	=	3;			// bullet 種類 2way  
	
	private BossManager _manager = null; // BossManager 使用
	
    public void bullet_sort(int x){
        switch(x){
			case 0:
            CreateEimsBullet();
			break;
			case 1:
            CreateYWayBullet();
			break;
            case 2:
            Create8WayBullet();
			break;
            // case 3:
			// break;
		}
    }

	public Boss(BossManager bo)
	{
		super();							// BaseObject.BaseObject()
		m_HP = 0;							//初期化
		m_Def = 0;
		m_AppearTime = 0;
		m_bulletType = 0;
		m_bulletIntvl = 0;
		m_bulletSpeed = 0;

		_manager = bo;					//Enemmy.Enemy(EnemyManager == em)

        _maingamestate = bo.GetMain();

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

    //boss 1
	public void Fire()
	{
		if(!isEnable) return;		//if 存在しない

		if(_manager.GetTime() % m_bulletIntvl == 0)		//if あるintervalすぎた-> またfire (短い)

           // bullet_sort(BL_1WAY_MON);
            //bullet_sort(BL_8WAY_ALL);
            bullet_sort(BL_3WAY_FAN);
	}
	public void Fire2()
	{
		if(!isEnable) return;		//if 存在しない

		if(_manager.GetTime() % m_bulletIntvl == 0)		//if あるintervalすぎた-> またfire (短い)

            bullet_sort(BL_1WAY_MON);
            //bullet_sort(BL_8WAY_ALL);
            bullet_sort(BL_3WAY_FAN);
	}
	public void Fire3()
	{
		if(!isEnable) return;		//if 存在しない

		if(_manager.GetTime() % m_bulletIntvl == 0)		//if あるintervalすぎた-> またfire (短い)

        	bullet_sort(BL_1WAY_MON); 	//BL_1WAY_MON = 0
            bullet_sort(BL_8WAY_ALL);	//1
            bullet_sort(BL_3WAY_FAN);	//2
	}

	public void Show(Graphics2D g2)				//ボスの形
	{
		if(!isEnable) return;
        if(_maingamestate.stageNum == 1){
            g2.drawImage(resizeImage2, (int)fX, (int)fY, null);    
        }else if(_maingamestate.stageNum == 2){
            g2.drawImage(resizeImage1, (int)fX, (int)fY, null);  
        }else if(_maingamestate.stageNum == 3){
            g2.drawImage(resizeImage2, (int)fX, (int)fY, null);  
        }

	}


	//狙い弾を生成
	public void CreateEimsBullet() 				///ここ修正->いろいろな角度のbullet 現在　狙い弾
	{
		Fighter fighter = _manager.GetFighter();		//Fighter 情報
		Bullet[] bullet = _manager.GetBullet();			//Bullet は　配列（多数）
		
		if(!fighter.IsEnable()) return;					//if　Fighter 存在しない
		if(!isEnable) return;							//if　Fighter 存在しない

		if(fY > 1000) return;							//座標　1000すぎた（見えなくなった）-> Bullet生成しない

		for(int i=0; i<200; i++)		//bullet 数は　Maxまで -> boss max数　いじる必要 -> enemy managerで
		{
			if(bullet[i].isEnable) continue;		//if bullet[i] が存在している　→ 次のやつ生成

			float dx, dy, d, speed;
			float FX = fX+150;
			dx = fighter.GetX() - FX;				//比率で投げるため
			dy = fighter.GetY() + 23 - fY;

			speed = this.m_bulletSpeed;

			d = (float)Math.sqrt(dx*dx + dy*dy);   	//距離

			bullet[i].SetPos(FX, fY);
			bullet[i].SetVX((dx/d)*speed);			//比率（三角形）投げる	
			bullet[i].SetVY((dy/d)*speed);

			bullet[i].Enable(true);					//bullet[i] -> とうとう生成　1個

			break;
		}
	}
    public void CreateYWayBullet() 				///ここ修正->いろいろな角度のbullet 現在　狙い弾
	{
		Fighter fighter = _manager.GetFighter();		//Fighter 情報
		Bullet[] bullet = _manager.GetBullet();			//Bullet は　配列（多数）
		
		if(!fighter.IsEnable()) return;					//if　Fighter 存在しない
		if(!isEnable) return;							//if　Fighter 存在しない

		if(fY > 1000) return;							//座標　1000すぎた（見えなくなった）-> Bullet生成しない

		for(int i=0; i<200; i++)		//bullet 数は　Maxまで -> boss max数　いじる必要 -> enemy managerで
		{
			if(bullet[i].isEnable) continue;		//if bullet[i] が存在している　→ 次のやつ生成

			float speed;

			speed = this.m_bulletSpeed;
            bullet[i].SetPos(20*i, 0);
            bullet[i].SetVX(0);			//比率（三角形）投げる	
            bullet[i].SetVY(speed);
    
            bullet[i].Enable(true);					//bullet[i] -> とうとう生成　1個
			break;
		}
	}
    public void Create8WayBullet() 				///ここ修正->いろいろな角度のbullet 現在　狙い弾
	{
		Fighter fighter = _manager.GetFighter();		//Fighter 情報
		Bullet[] bullet = _manager.GetBullet();			//Bullet は　配列（多数）
		
		if(!fighter.IsEnable()) return;					//if　Fighter 存在しない
		if(!isEnable) return;							//if　Fighter 存在しない

		if(fY > 1000) return;							//座標　1000すぎた（見えなくなった）-> Bullet生成しない

		for(int i=0; i<200; i++)		//bullet 数は　Maxまで -> boss max数　いじる必要 -> enemy managerで
		{
			if(bullet[i].isEnable) continue;		//if bullet[i] が存在している　→ 次のやつ生成

			float speed;
            speed = this.m_bulletSpeed;
            
            bullet[i].SetPos(500, 300);
            bullet[i].SetVX((int)(Math.cos(_manager.GetTime())*10));			//比率（三角形）投げる	
            bullet[i].SetVY((int)(Math.sin(_manager.GetTime())*10));
    
            bullet[i].Enable(true);					//bullet[i] -> とうとう生成　1個
			break;
		}
        for(int j=0; j<200; j++)		//bullet 数は　Maxまで -> boss max数　いじる必要 -> enemy managerで
		{
			if(bullet[j].isEnable) continue;		//if bullet[j] が存在している　→ 次のやつ生成

			float speed;
            speed = this.m_bulletSpeed;
            
            bullet[j].SetPos(300, 400);
            bullet[j].SetVX((int)(Math.cos(_manager.GetTime())*10));			//比率（三角形）投げる	
            bullet[j].SetVY((int)(Math.sin(_manager.GetTime())*10));
    
            bullet[j].Enable(true);					//bullet[i] -> とうとう生成　1個
			break;
		}
        for(int j=0; j<200; j++)		//bullet 数は　Maxまで -> boss max数　いじる必要 -> enemy managerで
		{
			if(bullet[j].isEnable) continue;		//if bullet[j] が存在している　→ 次のやつ生成

			float speed;
            speed = this.m_bulletSpeed;
            
            bullet[j].SetPos(800, 600);
            bullet[j].SetVX((int)(Math.cos(_manager.GetTime())*10));			//比率（三角形）投げる	
            bullet[j].SetVY((int)(Math.sin(_manager.GetTime())*10));
    
            bullet[j].Enable(true);					//bullet[i] -> とうとう生成　1個
			break;
		}
	}

    public void CreateLaserBullet() 				///ここ修正->いろいろな角度のbullet 現在　狙い弾
	{
		Fighter fighter = _manager.GetFighter();		//Fighter 情報
		Bullet[] bullet = _manager.GetBullet();			//Bullet は　配列（多数）
		
		if(!fighter.IsEnable()) return;					//if　Fighter 存在しない
		if(!isEnable) return;							//if　Fighter 存在しない

		if(fY > 1000) return;							//座標　1000すぎた（見えなくなった）-> Bullet生成しない

		for(int i=0; i<200; i++)		//bullet 数は　Maxまで -> boss max数　いじる必要 -> enemy managerで
		{
			if(bullet[i].isEnable) continue;		//if bullet[i] が存在している　→ 次のやつ生成

			float speed;

			speed = this.m_bulletSpeed;
            bullet[i].SetPos(20*i, 0);
            bullet[i].SetVX(0);			//比率（三角形）投げる	
            bullet[i].SetVY(speed);
    
            bullet[i].Enable(true);					//bullet[i] -> とうとう生成　1個
			break;
		}
	}
}
