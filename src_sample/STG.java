import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

<<<<<<< HEAD
public class STG extends JPanel implements Runnable, KeyListener{

	public static Thread mainThread = null;

	public static void main(String args[])
	{

		JFrame frame = new JFrame();


		STG app = new STG();

		frame.getContentPane().add(app);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setBounds(10, 10, 480, 640); 
		frame.setTitle("Templete Shooting");
		frame.setVisible(true);

		
		mainThread = new Thread(app);
		
		
		app.init();
	}
	

	private GameManager _gmanager;


=======
// ここはMVCで言えば、VとC

// MVCの観点で組むとすると
// Mはゲーム全体の状態とそれに対する処理を管理するゲームmanager
// VはJPanelでの表示。GameManagerの状態に応じて処理をする
// どうせキー入力なんでcもJPanelにKeyListenerを入れて頑張ってもらう
// GameManagerには状態を持たせ、それを切り替えさせる
// stateパターンにまとめれば入力系もスッキリ

public class STG extends JPanel implements Runnable, KeyListener{

	public static Thread mainThread = null;
	// エイン関数
	public static void main(String args[])
	{
		//適当なJFrame用意
		JFrame frame = new JFrame();

		//メインパネル（シューティングを実行するパネル）を新規作成
		STG app = new STG();
		//フレームに登録
		frame.getContentPane().add(app);
		//各種フレームの設定
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setBounds(10, 10, 1000, 800); 
		frame.setTitle("Templete Shooting"); // 
		frame.setVisible(true); // 

		//メインスレッド化
		mainThread = new Thread(app);
		
		//設定終わったのでメインパネル初期化して開始
		app.init();
	}
	
	//ゲームマネージャの保持
	private GameManager _gmanager;

	// 描画対象バッファ
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	private Image buffer;
	private Graphics bufferGraphics;
	
	public void init(){
		setBackground(Color.black);
		setForeground(Color.white);

		if (buffer == null){
			buffer = createImage(1000, 800);
			bufferGraphics = buffer.getGraphics();
		}

		addKeyListener(this);
		requestFocus();
		
		_gmanager = new GameManager(this);
		
	    mainThread.start();
	}


<<<<<<< HEAD

	public void run(){
		while (true){
			try{
				Thread.sleep(20);	
=======
	//スレッドで動く関数かもしれない
	public void run(){
		while (true){
			try{
				Thread.sleep(20);	//FPS調整・・・でも処理落ちしてるからあんま関数ねえっぽ
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
			}catch (InterruptedException e){
				break;
			}

<<<<<<< HEAD
			Graphics2D g2 = (Graphics2D) bufferGraphics;

			g2.setBackground(Color.red);
			g2.clearRect(0, 0, 480, 640);

			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(4.0f));

			
			_gmanager.GameMainUpdate();	
			
			
			ShowObjects(g2);
			
			
=======
			Graphics2D g2 = (Graphics2D) bufferGraphics;	//2D使うため

			g2.setBackground(Color.black);
			g2.clearRect(0, 0, 1000, 800);

			//アンチエイリアシング
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(4.0f));

			//ゲーム内処理
			_gmanager.GameMainUpdate();	
			
			//この辺で描画
			ShowObjects(g2);
			
			//リペイント
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
			repaint();
		}
	}

<<<<<<< HEAD

=======
	// 描画命令
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	public void ShowObjects(Graphics2D g2)
	{
		_gmanager.State().Show(g2);
	}
	
<<<<<<< HEAD

	public void paintComponent(Graphics g){
           		g.setColor(Color.black);
			g.clearRect(0, 0, 480, 640);
			g.drawImage(buffer, 0, 0, this);
	}

	
=======
	//催秒が命令の際にはこれを頑張りなおす
	public void paintComponent(Graphics g){
			g.setColor(Color.black);
			g.clearRect(0, 0, 1000, 800);
			g.drawImage(buffer, 0, 0, this);
	}

	//入力系。状態により切り替える
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
	public void keyPressed(KeyEvent e){
		_gmanager.State().KeyPressed(e);
	}

	public void keyReleased(KeyEvent e){
		_gmanager.State().KeyReleased(e);
	}
	public void keyTyped(KeyEvent e)
	{
		_gmanager.State().KeyTyped(e);
	}

}
