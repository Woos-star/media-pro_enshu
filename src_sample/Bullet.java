import java.awt.*;
import java.awt.geom.*;

public class Bullet extends BaseObject {

	public Bullet()
	{
		super();
	}

	public void Show(Graphics2D g2)
	{
		if(!isEnable) return;

<<<<<<< HEAD
		g2.setPaint(Color.green);
=======
		g2.setPaint(Color.yellow);
>>>>>>> dacef5f1118d9b38c6adef21a2948c04bd463309
		g2.fill(new Ellipse2D.Double(fX - 5f, fY - 5f, 10f, 10f));
	}
}
