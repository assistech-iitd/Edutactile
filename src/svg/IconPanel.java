package svg;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.FuncMap;
import com.graphbuilder.math.VarMap;
import com.kitfox.svg.SVGException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/*! \brief An extension of the Jpanel class.
 *
 *  An extension of the Swing Jpanel class. Used for rendering images. 
 */

public class IconPanel extends JPanel {

    public File f = null; /**< The file to be converted */
    int value = 0;
    int nor = 0, nop = 0, noc = 0;
    ArrayList<Shape> converted = new ArrayList<>();
    String txt = "", r1nam = "", r2nam = "", r3nam = "", r4nam = "", p1nam = "", p2nam = "", p3nam = "", p4nam = "", fnam = "", c1nam = "", c2nam = "", c3nam = "", c4nam = "", pressure = "", temp = "", chemical = "";
    Expression fun = null;
    VarMap vm = new VarMap(false);
    FuncMap fm = new FuncMap();
    int fontsize,linethickness;
    int flinethickness = 0;
    int flinetype = 0;
    Image image = null;

    public void zero() {
        f = null;
        fun = null;
        value = 0;
        nor = 0;
        nop = 0;
        flinethickness = 0;
        flinetype = 0;
        txt = "";
        r1nam = "";
        r2nam = "";
        r3nam = "";
        r4nam = "";
        p1nam = "";
        p2nam = "";
        p3nam = "";
        p4nam = "";
        fnam = "";
        converted = new ArrayList<>();

    }

    public void setFile(File fil, String name) {
        f = fil;
        fnam = name;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(getBackground());
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(0, 0, 0));
        g2d.setStroke(new BasicStroke(linethickness));

        switch (value) {
            case 1:
                ImageConverter imageconverter = new ImageConverter();
                try {
                    imageconverter.draw(g2d, f, this, txt, converted,fontsize);
                } catch (SVGException ex) {
                    Logger.getLogger(IconPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 2:
                ChemicalEquation chemicalequation = new ChemicalEquation();
                chemicalequation.draw(g2d,this,txt,nop,nor,pressure,temp,r1nam,r2nam,r3nam,r4nam,p1nam,p2nam,p3nam,p4nam,fontsize);
                break;
            case 3:
                MathFunction mathfunction = new MathFunction(this, txt, flinethickness, flinetype, fun, vm, fm,fontsize);
                mathfunction.draw(g2d);
                break;
            case 4:
                IconPaint iconpaint = new IconPaint();
                iconpaint.draw(chemical, image, this, g2d);
        }
    }

}
