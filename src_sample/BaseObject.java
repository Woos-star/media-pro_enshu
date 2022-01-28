//座標、速度（ある物体のmove）、show（表示）のbase

import java.awt.Graphics2D;

//自機や敵キャラの基礎クラス
public class BaseObject {
	protected float fX;
	protected float fY;
	protected float fVX;
	protected float fVY;
	protected boolean isEnable;

	public BaseObject()
	{
		fX = 0;
		fY = 0;
		fVX = fVY = 0;
		isEnable = false;
	}

	//
	public float GetX()
	{
		return fX;
	}

	public float GetY()
	{
		return fY;
	}

	//returning false
	public boolean IsEnable()
	{
		return isEnable;
	}

	// データ設定用

	//setting position
	public void SetPos(float x, float y)
	{
		fX = x;
		fY = y;
	}

	//setting velocity of x
	public void SetVX(float x)
	{
		fVX = x;
	}

	//setting velocity of y
	public void SetVY(float y)
	{
		fVY = y;
	}

	//
	public void Enable(boolean flag)
	{
		isEnable = flag;
	}

	//if true return;
	//else position に+ fVX or fVY
	public void Move()
	{
		if(!this.isEnable) return;

		fX += fVX;
		fY += fVY;
	}

	//true ->return 
	//좀더 공부
	public void Show(Graphics2D g2)
	{
		if(!isEnable) return;
	}


}
