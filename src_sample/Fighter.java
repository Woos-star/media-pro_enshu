<<<<<<< HEAD
=======
/*

 *
 * s
 */

/**
 * @author Administrator
 *
 */
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Fighter extends BaseObject{
<<<<<<< HEAD
	protected int nLeft;	

       
=======
	protected int nLeft;	// 残機数

	// キー入力のフラグ
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	protected boolean bKeyLeft;
	protected boolean bKeyRight;
	protected boolean bKeyUp;
	protected boolean bKeyDown;
	protected int bKeyZ;

	public final static int KB_NONE	=	0;
	public final static int KB_TRIG	= 	1;
	public final static int KB_PUSH	= 	2;
	public final static int KB_PULL	=	3;

<<<<<<< HEAD
	private int numShot;	
	private int numValidShot;
=======
	private int numShot;	// 画面表示ショット
	private int numValidShot; // 現在作れるショット数
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	private int shotTimer = 0;

	private Shot shot[];

<<<<<<< HEAD
       	class Shot extends BaseObject{


=======
	// 内部クラスかされた時期ショット管理クラス
	// *戦闘機はショット生成だけを受け待ち。移動はショット自身が行う
	class Shot extends BaseObject{

		// コンストラクタ
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
		public Shot()
		{
			super();
		}

<<<<<<< HEAD

=======
		// 弾移動
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
		public void Move()
		{
			if(!this.isEnable) return;

			if(fY >= 0)
			{
				fX += fVX;
				fY += fVY;
			}
			else
				this.Enable(false);
		}

<<<<<<< HEAD

=======
		// 弾表示
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
		public void Show(Graphics2D g2)
		{
			if(!this.isEnable) return;

			g2.setPaint(Color.white);
			g2.fill(new Ellipse2D.Double(fX - 10f, fY - 10f, 10f, 20f));

		}
	}

	public Fighter()
	{
		super();
		nLeft = 0;
		bKeyLeft = bKeyRight = bKeyUp = bKeyDown =  false;

		bKeyZ = KB_NONE;

		numShot = 6;
		numValidShot = 6;

		shot = new Shot[numShot];
		for(int i=0; i<numShot; i++)
		{
			shot[i] = new Shot();
		}
	}

	public Shot[] GetShot()
	{
		return shot;
	}

	public int GetNumShot()
	{
		return numShot;
	}


<<<<<<< HEAD

	public void Show(Graphics2D g2)
	{

=======
	// この辺一緒の関数にもできるけど名前の付け方めんどくさいので
	public void Show(Graphics2D g2)
	{
		// ショット
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
		for(int i=0; i<numShot; i++)
		{
			shot[i].Show(g2);
		}

<<<<<<< HEAD

=======
		// 自機
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
		if(!isEnable) return;

		g2.setPaint(Color.white);
		g2.fill(new Arc2D.Double( (int)fX - 55/2, (int)fY - 55/2, 110/2, 110/2, 250/2, 40/2, Arc2D.PIE));
		g2.setPaint(Color.yellow);
<<<<<<< HEAD
                g2.fill(new Arc2D.Double( (int)fX - 40, (int)fY + 30, 10, 15, 0
, 360, Arc2D.PIE));
		g2.fill(new Arc2D.Double( (int)fX + 30, (int)fY + 30, 10, 15, 0, 360, Arc2D.PIE));
	}


	public void Move()
	{

=======
		g2.fill(new Arc2D.Double( (int)fX - 40/2, (int)fY + 30/2, 10/2, 15/2, 0, 360/2, Arc2D.PIE));
		g2.fill(new Arc2D.Double( (int)fX + 30/2, (int)fY + 30/2, 10/2, 15/2, 0, 360/2, Arc2D.PIE));
	}

	// 移動
	public void Move()
	{
		// ショット
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
		for(int i=0; i<numShot; i++)
		{
			shot[i].Move();
		}

		if(!isEnable) return;

		if(bKeyLeft)
		{
			if(fX >= 0)
	 			fX -= fVX;
		}
		else if(bKeyRight)
		{
			if(fX <= 1000)
				fX += fVX;
		}

		if(bKeyUp)
		{
			if(fY >= - 30)
				fY -= fVY;
		}
		else if(bKeyDown)
		{
			if(fY <= 800)
				fY += fVY;
		}
	}


<<<<<<< HEAD

=======
	// ショット生成
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	public void Shoot()
	{
		if(!isEnable) return;

<<<<<<< HEAD

=======
		// ボタン押しっぱじゃなくてボタン最初に押した時
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
		if(bKeyZ == KB_TRIG)
		{
			shotTimer = 0;
		}

<<<<<<< HEAD

		if(bKeyZ == KB_PUSH)
		{

=======
		// ボタン押されとるか？
		if(bKeyZ == KB_PUSH)
		{
			// 2回に一回作るよ
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
			if(shotTimer % 2 == 0)
			{
				if(numValidShot>=2)
				{
<<<<<<< HEAD

					for(int i=0; i<2; i++)
					{

=======
					// 2個作りたい
					for(int i=0; i<2; i++)
					{
						// まず作れるやつあるかどうかみるよ
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
						for(int j=0; j<numShot; j++)
						{
							if(shot[j].isEnable) continue;
							shot[j].SetVX(2-4*i);
							shot[j].SetVY(-40);
							shot[j].SetPos(Fighter.this.GetX() - 20 + 50*i, Fighter.this.GetY());
							shot[j].Enable(true);
							break;
						}
					}
				}
			}
			shotTimer++;
		}
	}


<<<<<<< HEAD

=======
	// ボタン押してる時
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	public void KeyPressedAnalyze(KeyEvent e)
	{
		if(!isEnable) return;

		switch(e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			bKeyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			bKeyRight = true;
			break;
		case KeyEvent.VK_UP:
			bKeyUp = true;
			break;
		case KeyEvent.VK_DOWN:
			bKeyDown = true;
			break;
		case KeyEvent.VK_Z:
			bKeyZ	= KB_PUSH;
			break;
		}
	}

<<<<<<< HEAD

=======
	// ボタン押した瞬間
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	public void KeyReleasedAnalyze(KeyEvent e)
	{
		if(!isEnable) return;

		switch(e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			bKeyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			bKeyRight = false;
			break;
		case KeyEvent.VK_UP:
			bKeyUp = false;
			break;
		case KeyEvent.VK_DOWN:
			bKeyDown = false;
			break;
		case KeyEvent.VK_Z:
			bKeyZ	= KB_PULL;
			break;
		}
	}

<<<<<<< HEAD

=======
	// s
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	public void KeyTypedAnalyze(KeyEvent e)
	{
		if(!isEnable) return;

		switch(e.getKeyCode())
		{
		case KeyEvent.VK_Z:
			bKeyZ	= KB_TRIG;
			break;
		}
	}

}
