/**
 * @author Administrator
 *
 */


import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.File;
 
import javax.imageio.ImageIO;
 

public class Fighter extends BaseObject{
	protected int nLeft;	// 残機数

	// キー入力のフラグ
	protected boolean bKeyLeft;
	protected boolean bKeyRight;
	protected boolean bKeyUp;
	protected boolean bKeyDown;
	protected int bKeyZ;
	protected int bKeyP;

	public final static int KB_NONE	=	0;
	public final static int KB_TRIG	= 	1;
	public final static int KB_PUSH	= 	2;
	public final static int KB_PULL	=	3;

	private int numShot;	// 画面表示ショット
	private int numValidShot; // 現在作れるショット数
	private int shotTimer = 0;

	private Shot shot[];

	//Image image[];
	private Image fighterImage = new ImageIcon(Fighter.class.getResource("../src_sample/ImageFile/plane_test.jpeg")).getImage(); //image
	int imageWidth = fighterImage.getWidth(null);
    int imageHeight = fighterImage.getHeight(null);
	float w = (float)(imageWidth /20);
	float h = (float)(imageHeight /20);
	Image resizeImage = fighterImage.getScaledInstance((int)w, (int)h, Image.SCALE_SMOOTH);
 
    //BufferedImage newImage = new BufferedImage((int)w,(int) h, BufferedImage.TYPE_INT_RGB);
    //g2.dispose();
    //ImageIO.write(newImage, imgFormat, new File(imgTargetPath));



	// 内部クラスかされた時期ショット管理クラス
	// *戦闘機はショット生成だけを受け待ち。移動はショット自身が行う
	class Shot extends BaseObject{

		// コンストラクタ
		public Shot()
		{
			super();
		}

		// 弾移動
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

		// 弾表示
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
		nLeft = 4;
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

	public void DecresenLeft(){
		nLeft -= 1;
		if(nLeft < 0) {
			Enable (false);
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


	// この辺一緒の関数にもできるけど名前の付け方めんどくさいので
	public void Show(Graphics2D g2)
	{
		// ショット
		for(int i=0; i<numShot; i++)
		{
			shot[i].Show(g2);
		}

		// 自機
		if(!isEnable) return;

		// g2.setPaint(Color.white);
		// g2.fill(new Arc2D.Double( (int)fX - 55/2, (int)fY - 55/2, 110/2, 110/2, 250/2, 40/2, Arc2D.PIE));
		// g2.setPaint(Color.yellow);
		// g2.fill(new Arc2D.Double( (int)fX - 40/2, (int)fY + 30/2, 10/2, 15/2, 0, 360/2, Arc2D.PIE));
		// g2.fill(new Arc2D.Double( (int)fX + 30/2, (int)fY + 30/2, 10/2, 15/2, 0, 360/2, Arc2D.PIE));
		//g2.drawImage(fighterImage, (int)fX, (int)fY, null);
		g2.drawImage(resizeImage, (int)fX-20, (int)fY-20, null);
	}

	// 移動
	public void Move()
	{
		// ショット
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


	// ショット生成
	public void Shoot()
	{
		if(!isEnable) return;

		// ボタン押しっぱじゃなくてボタン最初に押した時
		if(bKeyZ == KB_TRIG)
		{
			shotTimer = 0;
		}

		// ボタン押されとるか？
		if(bKeyZ == KB_PUSH)
		{
			// 2回に一回作るよ
			if(shotTimer % 2 == 0)
			{
				if(numValidShot>=2)
				{
					// 2個作りたい
					for(int i=0; i<2; i++)
					{
						// まず作れるやつあるかどうかみるよ
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


	// ボタン押してる時
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
		case KeyEvent.VK_P:
			bKeyP	= KB_PUSH;
		}
	}

	// ボタン押した瞬間
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

	// 
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
