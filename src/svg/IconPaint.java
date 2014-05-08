package svg;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class IconPaint {
    
    void draw(String chemical, Image image,IconPanel panel,Graphics2D g2d){
        if (chemical.equals("")) {
                image = null;
            } else if (chemical.equals("Benzene")) {
                image = (new ImageIcon("src/svg/resources/benzene.png").getImage());
            } else if (chemical.equals("Bromobenzene")) {
                image = (new ImageIcon("src/svg/resources/bromobenzene.png").getImage());
            } else if (chemical.equals("Aniline")) {
                image = (new ImageIcon("src/svg/resources/aniline.png").getImage());
            } else if (chemical.equals("O-Bromoaniline")) {
                image = (new ImageIcon("src/svg/resources/2-bromoaniline.png").getImage());
            } else if (chemical.equals("M-Bromoaniline")) {
                image = (new ImageIcon("src/svg/resources/3-bromoaniline.png").getImage());
            } else if (chemical.equals("P-Bromoaniline")) {
                image = (new ImageIcon("src/svg/resources/4-bromoaniline.png").getImage());
            }

            if (image != null) {

                int x = (panel.getWidth() - image.getWidth(null)) / 2;
                int y = (panel.getHeight() - image.getHeight(null)) / 2;

                g2d.drawImage(image, x, y, null);

            }
    }
    
}
