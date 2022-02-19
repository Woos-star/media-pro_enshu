import java.io.*;
import java.util.*;
import java.security.AccessControlException;

public class StageAnalyze {
	private Enemy[] _enemy = new Enemy[100];
	private Boss[] _boss = new Boss[10];

	public Enemy[] GetTemporaryEnemy(){return _enemy;}
	public Boss[] GetTemporaryBoss(){return _boss;}
	private String[] _str = new String[100];		// 敵の数保存、行
	private String[] _str_b = new String[100];		// 敵の数保存、行

	private String[] _token = new String[10];		// 分割文字保存
	private String[] _token_boss = new String[10];
	private int _numStr = 0;						// 行数保存
	private int _numStr_b = 0;				//ボス行数

	public int GetStringNumber(){return _numStr;}
	public int GetStringNumber_b(){return _numStr_b;}
	

	private LinkedList<String[]> _tokenList;
	private LinkedList<String[]> _tokenList_b;
	public LinkedList<String[]> GetScenario(){return _tokenList;}
	public LinkedList<String[]> GetScenario_b(){return _tokenList_b;}
	
	// ステージデーア解析
	public void Analyze(String filePath)  throws AccessControlException
	{
		String temp = null;
		StringTokenizer st, st_boss;
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader buffererdReader = null;

		try
		{
			inputStreamReader = new InputStreamReader(new FileInputStream(filePath));
			buffererdReader = new BufferedReader(inputStreamReader);

			_tokenList = new LinkedList<String[]>();
			_tokenList_b = new LinkedList<String[]>();
			
			//	とりあえず１行ずつ読む
			while (buffererdReader.ready()){
				// コメント　飛ばす
				if((temp = buffererdReader.readLine()).startsWith("//")) continue;		// //から開始　->飛ばす

				else if(temp.startsWith("*"))		//*から始まる
				{
					//ボスの実装

					temp = buffererdReader.readLine();
					_str_b[_numStr_b] = temp;
					st_boss = new StringTokenizer(_str_b[_numStr_b],",");	// , によってdivde される
					_token_boss = new String[10];						//10 Strings
					for(int i=0; st_boss.hasMoreTokens(); i++) {			//return 할  token　あるなら　false ,else true
						_token_boss[i] = st_boss.nextToken();
					}

					if(_token_boss[0] != null)
					{
						_tokenList_b.add(_token_boss); //boss token を入れる
						_numStr_b++;			//行は増え
					}

					System.out.print("Boss:");
					for(String[] s: _tokenList_b)		
					{	
						for(int i=0; i<s.length;i++)
						{
							System.out.print(s[i]+",");
						}
						System.out.println();
					}
					System.out.println();

					continue;
				}
				
				

				// ここから通常的
				// 敵の配置だけはちょっと複雑に
				_str[_numStr] = temp;
				// 分割する
				st = new StringTokenizer(_str[_numStr],",");
			
				_token = new String[10];	
				
				// 分割したトークンを一時的に格納
				for(int i=0; st.hasMoreTokens(); i++) {
						_token[i] = st.nextToken();
				}
				
				if(_token[0] != null)
				{
					_tokenList.add(_token);
					_numStr++;
				}
				
				// デバッグ用に読み込んだステップ数を表示
				
				System.out.print("Token:");
				for(String[] s: _tokenList)
				{
					for(int i=0; i<s.length;i++)
					{
						System.out.print(s[i]+",");
					}
					System.out.println();
				}
				System.out.println();
			}
		}catch ( IOException e ){
			return;
		}finally{
			try{
				if(inputStreamReader!=null){
					inputStreamReader.close();
				}
				if(fileInputStream!=null){
					fileInputStream.close();
				}
				if(buffererdReader!=null){
					buffererdReader.close();
				}
			}catch ( IOException e ){
				return;
			}
		}
	}
	
	public Enemy GetTemporaryEnemy(EnemyManager em, int num)
	{
		// 出現時間、出現座標(x,y）、速度（vx,vy） HP 防御力　弾発射間隔　弾体ぷ　弾速度

		String[] tempString = _tokenList.get(num);
		Enemy temp = new Enemy(em);
		temp.SetAppearTime(new Integer(tempString[0]).intValue());
		temp.SetPos((new Float(tempString[1]).floatValue()),(new Float(tempString[2]).floatValue()));
		temp.SetVX(new Float(tempString[3]).floatValue());
		temp.SetVY(new Float(tempString[4]).floatValue());
		temp.SetHP(new Integer(tempString[5]).intValue());
		temp.SetDEF(new Integer(tempString[6]).intValue());
		//ここはstateパターンやstrategyパターンを使ってあらかじめ用意した状態を登録する形式にしてもいい...というかむしろその方がいい
		temp.SetBulletIntvl(new Integer(tempString[7]).intValue());
		temp.SetBulletType(new Integer(tempString[8]).intValue());
		temp.SetBulletSpeed(new Integer(tempString[9]).intValue());
	
		return temp;
	}

	//Boss実装
	
	public Boss GetTemporaryBoss(BossManager bo, int num)
	{
		String[] tempString = _tokenList_b.get(num);

		Boss temp = new Boss(bo);
		temp.SetAppearTime(new Integer(tempString[0]).intValue());
		temp.SetPos((new Float(tempString[1]).floatValue()),(new Float(tempString[2]).floatValue()));
		temp.SetVX(new Float(tempString[3]).floatValue());
		temp.SetVY(new Float(tempString[4]).floatValue());
		temp.SetHP(new Integer(tempString[5]).intValue());
		temp.SetDEF(new Integer(tempString[6]).intValue());
		//ここはstateパターンやstrategyパターンを使ってあらかじめ用意した状態を登録する形式にしてもいい...というかむしろその方がいい
		temp.SetBulletIntvl(new Integer(tempString[7]).intValue());
		temp.SetBulletType(new Integer(tempString[8]).intValue());
		temp.SetBulletSpeed(new Integer(tempString[9]).intValue());
	
		return temp;
	}
}
