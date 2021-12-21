public class GameManager{
<<<<<<< HEAD

=======
	// ゲーム全体で必要な変数とかはおっこに保持するといいでしょう
	// 例；起動してからの時間など
	
	// s
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	private ModeState _modeState = null;
	public ModeState State(){return _modeState;}

	public GameManager(STG stg)
	{
		init();
	}
	
<<<<<<< HEAD
	public void init()
	{

		_modeState = new TitleState();
	}
	

=======
	// s
	public void init()
	{
		// s
		_modeState = new TitleState();
	}
	
	// s
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	public void ChangeMode(ModeState mode)
	{
		_modeState = mode;
	}

<<<<<<< HEAD

=======
	// ���C��������
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	public void GameMainUpdate()
	{
		_modeState.run(this);
	}
}
