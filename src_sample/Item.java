import java.awt.*;
import java.awt.geom.*;
 
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.File;
 
import javax.imageio.ImageIO;
 
public class Item extends BaseObject {
    //item image
 
    private Image fighterImage = new ImageIcon(Fighter.class.getResource("../src_sample/ImageFile/ポーション.PNG")).getImage(); //image
    int imageWidth = fighterImage.getWidth(null);
    int imageHeight = fighterImage.getHeight(null);
    float w = (float)(imageWidth /3);
    float h = (float)(imageHeight /3);
    Image resizeImage = fighterImage.getScaledInstance((int)w, (int)h, Image.SCALE_SMOOTH);
 
    public Item()
    {
        super();        //base object
 
    }

    public void SetImage()      /////////////
    {
        fighterImage = new ImageIcon(Fighter.class.getResource("../src_sample/ImageFile/ポーション-1.PNG")).getImage();
        resizeImage = fighterImage.getScaledInstance((int)w, (int)h, Image.SCALE_SMOOTH);
    }

    public void Show(Graphics2D g2)
    {
        if(!isEnable) return;       //enable ->
 
        //g2.setPaint(Color.orange);
        //g2.fill(new Ellipse2D.Double(fX - 5f, fY - 5f, 10f, 10f));
        g2.drawImage(resizeImage, (int)fX-20, (int)fY-20, null);
    }
}
