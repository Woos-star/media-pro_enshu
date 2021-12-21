import java.awt.Graphics2D;

<<<<<<< HEAD
=======
//自機や敵キャラの基礎クラス
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
public class BaseObject {
	protected float fX;
	protected float fY;
	protected float fVX;
	protected float fVY;
	protected boolean isEnable;

<<<<<<< HEAD

=======
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	public BaseObject()
	{
		fX = 0;
		fY = 0;
		fVX = fVY = 0;
		isEnable = false;
	}


	public float GetX()
	{
		return fX;
	}

	public float GetY()
	{
		return fY;
	}


	public boolean IsEnable()
	{
		return isEnable;
	}

<<<<<<< HEAD
=======
	// データ設定用
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309



	public void SetPos(float x, float y)
	{
		fX = x;
		fY = y;
	}


	public void SetVX(float x)
	{
		fVX = x;
	}


	public void SetVY(float y)
	{
		fVY = y;
	}


	public void Enable(boolean flag)
	{
		isEnable = flag;
	}

<<<<<<< HEAD


=======
	//主処理
	
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	public void Move()
	{
		if(!this.isEnable) return;

		fX += fVX;
		fY += fVY;
	}


	public void Show(Graphics2D g2)
	{
		if(!isEnable) return;
	}


}
