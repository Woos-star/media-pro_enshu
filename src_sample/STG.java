import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

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


	
//背景挿入テスト
private Image BackgroundImage = new ImageIcon(MainGameState.class.getResource("../src_sample/ImageFile/img_1b138001c63d21f1d60d2e5af54d558d3376810.jpg")).getImage(); //image
int imageWidth = BackgroundImage.getWidth(null);
int imageHeight = BackgroundImage.getHeight(null);
float w = (float)(1.3*imageWidth);
float h = (float)(2*imageHeight);
Image resizeImage = BackgroundImage.getScaledInstance((int)w, (int)h, Image.SCALE_SMOOTH);


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
		frame.setTitle("SHOOTING GAME"); // 
		frame.setVisible(true); // 

		//メインスレッド化
		mainThread = new Thread(app);
		
		//設定終わったのでメインパネル初期化して開始
		app.init();
	}
	
	//ゲームマネージャの保持
	private GameManager _gmanager;

	// 描画対象バッファ
	private Image buffer;
	private Graphics bufferGraphics;
	
	public void init(){
		setBackground(Color.white);
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


	//スレッドで動く関数かもしれない
	public void run(){
		while (true){
			try{
				Thread.sleep(20);	//FPS調整・・・でも処理落ちしてるからあんま関数ねえっぽ
			}catch (InterruptedException e){
				break;
			}
			
			Graphics2D g2 = (Graphics2D) bufferGraphics;	//2D使うため


			g2.setBackground(Color.black);
			g2.clearRect(0, 0, 1000, 800);
			g2.drawImage(resizeImage, 0, 0, null);
			
			


			//アンチエイリアシング
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(4.0f));

			//ゲーム内処理
			_gmanager.GameMainUpdate();	
			
			//この辺で描画
			ShowObjects(g2);
			
			//リペイント
			repaint();
		}
	}

	// 描画命令
	public void ShowObjects(Graphics2D g2)
	{
		
		_gmanager.State().Show(g2);
		
	}
	
	//催秒が命令の際にはこれを頑張りなおす
	public void paintComponent(Graphics g){
		//g2.drawImage(resizeImage, 0, 0, null);
			g.setColor(Color.red);
			g.clearRect(0, 0, 1000, 800);
			g.drawImage(buffer, 0, 0, this);
	}

	//入力系。状態により切り替える
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