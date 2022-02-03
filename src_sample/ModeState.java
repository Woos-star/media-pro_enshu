import java.awt.*;
import java.awt.event.*;


public interface ModeState{
	
	public void Show(Graphics2D g2);
	public void run(GameManager gm);
	public void init();
	
	public void KeyPressed(KeyEvent arg0);
	public void KeyReleased(KeyEvent arg0);
	public void KeyTyped(KeyEvent arg0);
}