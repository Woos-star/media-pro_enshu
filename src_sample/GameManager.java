public class GameManager{

	private ModeState _modeState = null;
	public ModeState State(){return _modeState;}

	public GameManager(STG stg)
	{
		init();
	}
	
	public void init()
	{

		_modeState = new TitleState();
	}
	

	public void ChangeMode(ModeState mode)
	{
		_modeState = mode;
	}


	public void GameMainUpdate()
	{
		_modeState.run(this);
	}
}
