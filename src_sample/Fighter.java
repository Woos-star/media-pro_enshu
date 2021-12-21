import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Fighter extends BaseObject{
	protected int nLeft;	

       
	protected boolean bKeyLeft;
	protected boolean bKeyRight;
	protected boolean bKeyUp;
	protected boolean bKeyDown;
	protected int bKeyZ;

	public final static int KB_NONE	=	0;
	public final static int KB_TRIG	= 	1;
	public final static int KB_PUSH	= 	2;
	public final static int KB_PULL	=	3;

	private int numShot;	
	private int numValidShot;
	private int shotTimer = 0;

	private Shot shot[];

       	class Shot extends BaseObject{


		public Shot()
		{
			super();
		}


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



	public void Show(Graphics2D g2)
	{

		for(int i=0; i<numShot; i++)
		{
			shot[i].Show(g2);
		}


		if(!isEnable) return;

		g2.setPaint(Color.blue);
		g2.fill(new Arc2D.Double( (int)fX - 55, (int)fY - 55, 110, 110, 250, 40, Arc2D.PIE));
		g2.setPaint(Color.yellow);
                g2.fill(new Arc2D.Double( (int)fX - 40, (int)fY + 30, 10, 15, 0
, 360, Arc2D.PIE));
		g2.fill(new Arc2D.Double( (int)fX + 30, (int)fY + 30, 10, 15, 0, 360, Arc2D.PIE));
	}


	public void Move()
	{

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
			if(fX <= 480)
				fX += fVX;
		}

		if(bKeyUp)
		{
			if(fY >= - 30)
				fY -= fVY;
		}
		else if(bKeyDown)
		{
			if(fY <= 610)
				fY += fVY;
		}
	}



	public void Shoot()
	{
		if(!isEnable) return;


		if(bKeyZ == KB_TRIG)
		{
			shotTimer = 0;
		}


		if(bKeyZ == KB_PUSH)
		{

			if(shotTimer % 2 == 0)
			{
				if(numValidShot>=2)
				{

					for(int i=0; i<2; i++)
					{

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
