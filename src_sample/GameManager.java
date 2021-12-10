
public class GameManager{
	// ゲーム全体で必要な変数とかはおっこに保持するといいでしょう
	// 例；起動してからの時間など
	
	// s
	private ModeState _modeState = null;
	public ModeState State(){return _modeState;}

	public GameManager(STG stg)
	{
		init();
	}
	
	// s
	public void init()
	{
		// s
		_modeState = new TitleState();
	}
	
	// s
	public void ChangeMode(ModeState mode)
	{
		_modeState = mode;
	}

	// ���C��������
	public void GameMainUpdate()
	{
		_modeState.run(this);
	}
}
