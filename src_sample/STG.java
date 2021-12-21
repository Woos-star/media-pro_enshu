import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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


	private Image buffer;
	private Graphics bufferGraphics;
	
	public void init(){
		setBackground(Color.black);
		setForeground(Color.white);

		if (buffer == null){
			buffer = createImage(480, 640);
			bufferGraphics = buffer.getGraphics();
		}

		addKeyListener(this);
		requestFocus();
		
		_gmanager = new GameManager(this);
		
	    mainThread.start();
	}



	public void run(){
		while (true){
			try{
				Thread.sleep(20);	
			}catch (InterruptedException e){
				break;
			}

			Graphics2D g2 = (Graphics2D) bufferGraphics;

			g2.setBackground(Color.red);
			g2.clearRect(0, 0, 480, 640);

			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(4.0f));

			
			_gmanager.GameMainUpdate();	
			
			
			ShowObjects(g2);
			
			
			repaint();
		}
	}


	public void ShowObjects(Graphics2D g2)
	{
		_gmanager.State().Show(g2);
	}
	

	public void paintComponent(Graphics g){
           		g.setColor(Color.black);
			g.clearRect(0, 0, 480, 640);
			g.drawImage(buffer, 0, 0, this);
	}

	
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
