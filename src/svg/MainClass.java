package svg;

import com.graphbuilder.math.*;
import com.kitfox.svg.SVGCache;
import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGElementException;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.Tspan;
import com.kitfox.svg.app.beans.SVGIcon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

class MyCustomFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getAbsolutePath().endsWith(".svg");
    }

    @Override
    public String getDescription() {
        return "Vector Graphics (*.svg)";
    }
}

class IconPanel extends JPanel {

    File f = null;
    int value = 0;
    int nor = 0, nop = 0, noc = 0;
    ArrayList<Shape> converted = new ArrayList<Shape>();
    String txt = "", r1nam = "", r2nam = "", r3nam = "", r4nam = "", p1nam = "", p2nam = "", p3nam = "", p4nam = "", fnam = "", c1nam = "", c2nam = "", c3nam = "", c4nam = "", pressure = "", temp = "", chemical = "";
    Expression fun = null;
    VarMap vm = new VarMap(false);
    FuncMap fm = new FuncMap();
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
        converted = new ArrayList<Shape>();

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
        g2d.setStroke(new BasicStroke(2));

        if (value == 4) {

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

                int x = (this.getWidth() - image.getWidth(null)) / 2;
                int y = (this.getHeight() - image.getHeight(null)) / 2;

                g2d.drawImage(image, x, y, null);

            }
        }

        switch (value) {
            case 1:
                ImageConverter(g2d, f);
                break;
            case 2:
                ChemicalEquation(g2d);
                break;
            case 3:
                MathFunction(g2d);
                break;
        }
    }

    private void MathFunction(Graphics2D g2d) {

        /* try {
         translator = new LanguageUnicode("britishtobr");
         translator.setState(1);
         } catch (IOException | ClassNotFoundException ex) {
         Logger.getLogger(IconPanel.class.getName()).log(Level.SEVERE, null, ex);
         }*/

        double h2 = 0;
        double w1 = 0, w2 = 0, wmid = 0, wq1 = 0, wq2 = 0, we1 = 0, we2 = 0, we3 = 0, we4 = 0;
        double ratio = 0;
        double newwidth = 0;
        double w3 = 0;
        double h1 = 0;

        ratio = ((double) this.getHeight()) / 297;
        newwidth = ratio * 210;

        h2 = this.getHeight() - 10;
        w1 = ((this.getWidth() - newwidth) / 2) + 10;
        w2 = ((this.getWidth() + newwidth) / 2) - 10;
        w3 = w2 - 30;
        h1 = 10;
        wmid = (w1 + w2) / 2;
        wq1 = (w1 + wmid) / 2;
        wq2 = (wmid + w2) / 2;
        we1 = (w1 + wq1) / 2;
        we2 = (wq1 + wmid) / 2;
        we3 = (wmid + wq2) / 2;
        we4 = (w2 + wq2) / 2;

        String txt1 = "", txt2 = "", txtbuffer = "";

        for (int t = 0; t < txt.length(); t++) {

            if ((txt.charAt(t) == (' '))) {

                if (t <= 29) {
                    txt1 = txt1 + txtbuffer + ' ';
                    txtbuffer = "";
                }
                if ((t > 29) && (t <= 59)) {
                    txt2 = txt2 + txtbuffer + ' ';
                    txtbuffer = "";
                }

            } else {
                txtbuffer = txtbuffer + txt.charAt(t);
            }

        }

        //Boundary + Top Right Corner Circle

        g2d.draw(new Rectangle2D.Double(w1, h1, w2 - w1, h2 - h1));
        g2d.draw(new Ellipse2D.Double(w1 + 10, h1 + 10, 40, 40));

        // Text box

        g2d.setFont(new Font("Braille", Font.PLAIN, 25));
        g2d.drawString(txt1, (float) w1 + 20, (float) h1 + 100);
        g2d.drawString(txt2, (float) w1 + 20, (float) h1 + 135);

        //Coordinate axes
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(new Line2D.Double(wmid, h1 + 300, wmid, h1 + 800));
        g2d.draw(new Line2D.Double(wmid - 250, h1 + 550, wmid + 250, h1 + 550));
        g2d.drawString("X", (float) wmid + 260, (float) h1 + 560);
        g2d.drawString("X'", (float) wmid - 290, (float) h1 + 560);
        g2d.drawString("Y", (float) wmid - 10, (float) h1 + 290);
        g2d.drawString("Y'", (float) wmid - 10, (float) h1 + 830);

        if (fun != null) {

            switch (flinethickness) {
                case 0:
                    g2d.setStroke(new BasicStroke(1));
                    break;
                case 1:
                    g2d.setStroke(new BasicStroke(2));
                    break;
                case 2:
                    g2d.setStroke(new BasicStroke(3));
                    break;
            }

            if (flinetype == 0) {
                for (double m = -250; m < 250; m = m + 1) {
                    vm.setValue("x", 0.04 * m);
                    double a = 25 * fun.eval(vm, fm);
                    vm.setValue("x", 0.04 * (m + 1));
                    double b = 25 * fun.eval(vm, fm);

                    if ((a <= 250) && (b <= 250) && (a >= -250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 - a, wmid + m + 1, h1 + 550 - b));
                    } else if ((a <= 250) && (a >= -250) && (b >= 250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 - a, wmid + m + 1, h1 + 300));
                    } else if ((a <= -250) && (b <= 250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 800, wmid + m + 1, h1 + 550 - b));
                    }
                }
            }

            if (flinetype == 1) {
                for (double m = -250; m < 250; m = m + 4) {
                    vm.setValue("x", 0.04 * m);
                    double a = 25 * fun.eval(vm, fm);
                    vm.setValue("x", 0.04 * (m + 1));
                    double b = 25 * fun.eval(vm, fm);

                    if ((a <= 250) && (b <= 250) && (a >= -250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 - a, wmid + m + 1, h1 + 550 - b));
                    } else if ((a <= 250) && (a >= -250) && (b >= 250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 - a, wmid + m + 1, h1 + 300));
                    } else if ((a <= -250) && (b <= 250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 800, wmid + m + 1, h1 + 550 - b));
                    }

                }
            }

        }

    }

    private void ChemicalEquation(Graphics2D g2d) {

        /* try {
         translator = new LanguageUnicode("britishtobr");
         translator.setState(1);
         } catch (IOException | ClassNotFoundException ex) {
         Logger.getLogger(IconPanel.class.getName()).log(Level.SEVERE, null, ex);
         }*/

        double h2 = 0;
        double w1 = 0, w2 = 0, wmid = 0, wq1 = 0, wq2 = 0, we1 = 0, we2 = 0, we3 = 0, we4 = 0;
        double ratio = 0;
        double newwidth = 0;
        double w3 = 0;
        double h1 = 0;

        ratio = ((double) this.getHeight()) / 297;
        newwidth = ratio * 210;

        h2 = this.getHeight() - 10;
        w1 = ((this.getWidth() - newwidth) / 2) + 10;
        w2 = ((this.getWidth() + newwidth) / 2) - 10;
        w3 = w2 - 30;
        h1 = 10;
        wmid = (w1 + w2) / 2;
        wq1 = (w1 + wmid) / 2;
        wq2 = (wmid + w2) / 2;
        we1 = (w1 + wq1) / 2;
        we2 = (wq1 + wmid) / 2;
        we3 = (wmid + wq2) / 2;
        we4 = (w2 + wq2) / 2;

        String txt1 = "", txt2 = "", txtbuffer = "";

        for (int t = 0; t < txt.length(); t++) {

            if ((txt.charAt(t) == (' '))) {

                if (t <= 29) {
                    txt1 = txt1 + txtbuffer + ' ';
                    txtbuffer = "";
                }
                if ((t > 29) && (t <= 59)) {
                    txt2 = txt2 + txtbuffer + ' ';
                    txtbuffer = "";
                }

            } else {
                txtbuffer = txtbuffer + txt.charAt(t);
            }

        }

        //Boundary + Top Right Corner Circle

        g2d.draw(new Rectangle2D.Double(w1, h1, w2 - w1, h2 - h1));
        g2d.draw(new Ellipse2D.Double(w1 + 10, h1 + 10, 40, 40));

        // Text box

        g2d.setFont(new Font("Braille", Font.PLAIN, 25));
        g2d.drawString(txt1, (float) w1 + 20, (float) h1 + 100);
        g2d.drawString(txt2, (float) w1 + 20, (float) h1 + 135);


        //Reactants

        if ((nor == 2) || (nor == 4)) {
            g2d.draw(new Line2D.Double(wmid, h1 + 369.5 / 1.1674, wmid, h1 + 385.5 / 1.1674));
            g2d.draw(new Line2D.Double(wmid - 8 / 1.1674, h1 + 377.5 / 1.1674, wmid + 8 / 1.1674, h1 + 377.5 / 1.1674));
        }

        if (nor == 4) {
            g2d.draw(new Line2D.Double(wq1, h1 + 369.5 / 1.1674, wq1, h1 + 385.5 / 1.1674));
            g2d.draw(new Line2D.Double(wq1 - 8 / 1.1674, h1 + 377.5 / 1.1674, wq1 + 8 / 1.1674, h1 + 377.5 / 1.1674));

            g2d.draw(new Line2D.Double(wq2, h1 + 369.5 / 1.1674, wq2, h1 + 385.5 / 1.1674));
            g2d.draw(new Line2D.Double(wq2 - 8 / 1.1674, h1 + 377.5 / 1.1674, wq2 + 8 / 1.1674, h1 + 377.5 / 1.1674));
        }

        if (nor == 3) {
            g2d.draw(new Line2D.Double(we2, h1 + 369.5 / 1.1674, we2, h1 + 385.5 / 1.1674));
            g2d.draw(new Line2D.Double(we2 - 8 / 1.1674, h1 + 377.5 / 1.1674, we2 + 8 / 1.1674, h1 + 377.5 / 1.1674));

            g2d.draw(new Line2D.Double(we3, h1 + 369.5 / 1.1674, we3, h1 + 385.5 / 1.1674));
            g2d.draw(new Line2D.Double(we3 - 8 / 1.1674, h1 + 377.5 / 1.1674, we3 + 8 / 1.1674, h1 + 377.5 / 1.1674));
        }

        
         if (nor == 2) {
         rmaker(g2d, we2, h1 + 145.6227, r1nam);
         rmaker(g2d, we3, h1 + 145.6227, r2nam);
         }

         if (nor == 4) {
         rmaker(g2d, we1, h1 + 145.6227, r1nam);
         rmaker(g2d, we2, h1 + 145.6227, r2nam);
         rmaker(g2d, we3, h1 + 145.6227, r3nam);
         rmaker(g2d, we4, h1 + 145.6227, r4nam);
         }

         if (nor == 1) {
         rmaker(g2d, wmid, h1 + 145.6227, r1nam);
         }

         if (nor == 3) {
         rmaker(g2d, wq1, h1 + 145.6227, r1nam);
         rmaker(g2d, wmid, h1 + 145.6227, r2nam);
         rmaker(g2d, wq2, h1 + 145.6227, r3nam);
         }
         

        //Arrow
        if ((nop != 0) && (nor != 0)) {

            double x2Points[] = {wmid, wmid, wmid + 24 / 1.1674};
            double y2Points[] = {h1 + 450 / 1.1674, h1 + 700 / 1.1674, h1 + 680 / 1.1674};
            GeneralPath polyline =
                    new GeneralPath(GeneralPath.WIND_EVEN_ODD, x2Points.length);

            polyline.moveTo(x2Points[0], y2Points[0]);

            for (int index = 1; index < x2Points.length; index++) {
                polyline.lineTo(x2Points[index], y2Points[index]);
            };

            g2d.draw(polyline);

            g2d.draw(new Line2D.Double(wmid, h1 + 700 / 1.1674, wmid - 24 / 1.1674, h1 + 680 / 1.1674));
        }

        //Conditions
        g2d.drawString(pressure, (float) (wmid - (pressure.length() * 20)), (float) (h1 + 550 / 1.1674));
        g2d.drawString(temp, (float) (wmid - (temp.length() * 20)), (float) (h1 + 600 / 1.1674));

        //Products

        if ((nop == 2) || (nop == 4)) {
            g2d.draw(new Line2D.Double(wmid, h1 + 807.5 / 1.1674, wmid, h1 + 823.5 / 1.1674));
            g2d.draw(new Line2D.Double(wmid - 8 / 1.1674, h1 + 815.5 / 1.1674, wmid + 8 / 1.1674, h1 + 815.5 / 1.1674));
        }

        if (nop == 4) {
            g2d.draw(new Line2D.Double(wq1, h1 + 807.5 / 1.1674, wq1, h1 + 823.5 / 1.1674));
            g2d.draw(new Line2D.Double(wq1 - 8 / 1.1674, h1 + 815.5 / 1.1674, wq1 + 8 / 1.1674, h1 + 815.5 / 1.1674));

            g2d.draw(new Line2D.Double(wq2, h1 + 807.5 / 1.1674, wq2, h1 + 823.5 / 1.1674));
            g2d.draw(new Line2D.Double(wq2 - 8 / 1.1674, h1 + 815.5 / 1.1674, wq2 + 8 / 1.1674, h1 + 815.5 / 1.1674));
        }

        if (nop == 3) {
            g2d.draw(new Line2D.Double(we2, h1 + 807.5 / 1.1674, we2, h1 + 823.5 / 1.1674));
            g2d.draw(new Line2D.Double(we2 - 8 / 1.1674, h1 + 815.5 / 1.1674, we2 + 8 / 1.1674, h1 + 815.5 / 1.1674));

            g2d.draw(new Line2D.Double(we3, h1 + 807.5 / 1.1674, we3, h1 + 823.5 / 1.1674));
            g2d.draw(new Line2D.Double(we3 - 8 / 1.1674, h1 + 815.5 / 1.1674, we3 + 8 / 1.1674, h1 + 815.5 / 1.1674));
        }
        
         if (nop == 2) {
         rmaker(g2d, we2, h1 + 608 / 1.1674, p1nam);
         rmaker(g2d, we3, h1 + 608 / 1.1674, p2nam);
         }

         if (nop == 4) {
         rmaker(g2d, we1, h1 + 608 / 1.1674, p1nam);
         rmaker(g2d, we2, h1 + 608 / 1.1674, p2nam);
         rmaker(g2d, we3, h1 + 608 / 1.1674, p3nam);
         rmaker(g2d, we4, h1 + 608 / 1.1674, p4nam);
         }

         if (nop == 1) {
         rmaker(g2d, wmid, h1 + 608 / 1.1674, p1nam);
         }

         if (nop == 3) {
         rmaker(g2d, wq1, h1 + 608 / 1.1674, p1nam);
         rmaker(g2d, wmid, h1 + 608 / 1.1674, p2nam);
         rmaker(g2d, wq2, h1 + 608 / 1.1674, p3nam);
         }
         
    }

    private void ImageConverter(Graphics2D g2d, File f) {

        double h2 = 0;
        double w1 = 0, w2 = 0, wmid = 0, wq1 = 0, wq2 = 0, we1 = 0, we2 = 0, we3 = 0, we4 = 0;
        double ratio = 0;
        double newwidth = 0;
        double w3 = 0;
        double h1 = 0;

        ratio = ((double) this.getHeight()) / 297;
        newwidth = ratio * 210;

        h2 = this.getHeight() - 10;
        w1 = ((this.getWidth() - newwidth) / 2) + 10;
        w2 = ((this.getWidth() + newwidth) / 2) - 10;
        h1 = 10;

        wmid = (w1 + w2) / 2;
        wq1 = (w1 + wmid) / 2;
        wq2 = (wmid + w2) / 2;
        we1 = (w1 + wq1) / 2;
        we2 = (wq1 + wmid) / 2;
        we3 = (wmid + wq2) / 2;
        we4 = (w2 + wq2) / 2;

        String txt1 = "", txt2 = "", txtbuffer = "";

        for (int t = 0; t < txt.length(); t++) {

            if ((txt.charAt(t) == (' '))) {

                if (t <= 29) {
                    txt1 = txt1 + txtbuffer + ' ';
                    txtbuffer = "";
                }
                if ((t > 29) && (t <= 59)) {
                    txt2 = txt2 + txtbuffer + ' ';
                    txtbuffer = "";
                }

            } else {
                txtbuffer = txtbuffer + txt.charAt(t);
            }

        }

        //Boundary + Top Right Corner Circle

        g2d.draw(new Rectangle2D.Double(w1, h1, w2 - w1, h2 - h1));
        g2d.draw(new Ellipse2D.Double(w1 + 10, h1 + 10, 40, 40));

        // Text box

        g2d.setFont(new Font("Braille", Font.PLAIN, 25));
        g2d.drawString(txt1, (float) w1 + 20, (float) h1 + 100);
        g2d.drawString(txt2, (float) w1 + 20, (float) h1 + 135);

        if (f != null) {

            try {
                SVGUniverse svgUniverse = new SVGUniverse();
                SVGDiagram diagram = svgUniverse.getDiagram(svgUniverse.loadSVG(f.toURI().toURL()));
                SVGElement root = diagram.getRoot();


                converter(root);
                scaling();
                aslipaint(g2d);


            } catch (MalformedURLException ex) {
                Logger.getLogger(IconPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SVGElementException ex) {
                Logger.getLogger(IconPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SVGException ex) {
                Logger.getLogger(IconPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void rmaker(Graphics2D g2d, double cord, double h1, String chemical) {

        g2d.setFont(new Font("Braille", Font.PLAIN, 27));

        if ((chemical.equals("Phenol")) || (chemical.equals("Benzene")) || (chemical.equals("Benzoic Acid")) || (chemical.equals("Aniline")) || (chemical.equals("O-Bromoaniline")) || (chemical.equals("M-Bromoaniline")) || (chemical.equals("P-Bromoaniline")) || (chemical.equals("Bromobenzene"))) {

            if (chemical.equals("Phenol")) {
                g2d.drawString("OH", (float) (cord - 20.63 / 1.1674), (float) (h1 + 145 / 1.1674));
            }

            if (chemical.equals("Bromobenzene")) {
                g2d.drawString("Br", (float) (cord - 20.63 / 1.1674), (float) (h1 + 145 / 1.1674));
            }

            if (chemical.equals("Benzoic Acid")) {
                g2d.drawString("COOH", (float) (cord - 42.63 / 1.1674), (float) (h1 + 145 / 1.1674));
            }

            double x2Points[] = {cord - 43.30 / 1.1674, cord, cord + 43.30 / 1.1674, cord + 43.30 / 1.1674, cord, cord - 43.30 / 1.1674, cord - 43.30 / 1.1674};
            double y2Points[] = {h1 + 182.5 / 1.1674, h1 + 157.5 / 1.1674, h1 + 182.5 / 1.1674, h1 + 232.5 / 1.1674, h1 + 257.5 / 1.1674, h1 + 232.5 / 1.1674, h1 + 182.5 / 1.1674};
            GeneralPath polyline =
                    new GeneralPath(GeneralPath.WIND_EVEN_ODD, x2Points.length);

            polyline.moveTo(x2Points[0], y2Points[0]);

            for (int index = 1; index < x2Points.length; index++) {
                polyline.lineTo(x2Points[index], y2Points[index]);
            };

            g2d.draw(polyline);
            g2d.draw(new Ellipse2D.Double(cord - 30, (h1 + 207.5 / 1.1674) - 30, 60, 60));
        }

        if ((chemical.equals("Aniline")) || (chemical.equals("O-Bromoaniline")) || (chemical.equals("M-Bromoaniline")) || (chemical.equals("P-Bromoaniline"))) {

            if (chemical.equals("O-Bromoaniline")) {
                g2d.drawString("Br", (float) (cord + 43.63 / 1.1674), (float) (h1 + 185 / 1.1674));
            }

            if (chemical.equals("M-Bromoaniline")) {
                g2d.drawString("Br", (float) (cord + 43.63 / 1.1674), (float) (h1 + 250 / 1.1674));
            }

            if (chemical.equals("P-Bromoaniline")) {
                g2d.drawString("Br", (float) (cord - 20.63 / 1.1674), (float) (h1 + 290 / 1.1674));
            }

            
            g2d.drawString("NH", (float) (cord - 20.63 / 1.1674), (float) (h1 + 145/ 1.1674));
            g2d.setFont(new Font("Braille", Font.PLAIN, 23));
            g2d.drawString("2", (float) (cord + 20.63 / 1.1674), (float) (h1 + 155 / 1.1674));

        }

        if (chemical.equals("Bromine")) {
            
            g2d.drawString("Br", (float) (cord - 20.63 / 1.1674), (float) (h1 + 222.5/ 1.1674));
            g2d.setFont(new Font("Braille", Font.PLAIN, 23));
            g2d.drawString("2", (float) (cord + 20.63 / 1.1674), (float) (h1 + 232.5/ 1.1674));

        }

    }

    private void converter(SVGElement root) throws SVGElementException, SVGException {

        if (root.getTagName().equals("rect")) {
            double height = root.getPresAbsolute("height").getDoubleValue();
            double width = root.getPresAbsolute("width").getDoubleValue();
            double x = root.getPresAbsolute("x").getDoubleValue();
            double y = root.getPresAbsolute("y").getDoubleValue();

            Shape aalo = new Shape();

            aalo.type = "Rect";
            aalo.recheight = height;
            aalo.recwidth = width;
            aalo.recx = x;
            aalo.recy = y;

            aalo.recheight2 = height;
            aalo.recwidth2 = width;
            aalo.recx2 = x;
            aalo.recy2 = y;

            aalo.minx = x;
            aalo.miny = y;
            aalo.maxx = x + width;
            aalo.maxy = y + height;

            converted.add(aalo);

        } else if (root.getTagName().equals("circle")) {
            double radius = root.getPresAbsolute("r").getDoubleValue();
            double x = root.getPresAbsolute("cx").getDoubleValue();
            double y = root.getPresAbsolute("cy").getDoubleValue();

            Shape aalo = new Shape();

            aalo.type = "Circ";
            aalo.circr = radius;
            aalo.circx = x;
            aalo.circy = y;

            aalo.circr2 = radius;
            aalo.circx2 = x;
            aalo.circy2 = y;

            aalo.minx = x - radius;
            aalo.miny = y - radius;
            aalo.maxx = x + radius;
            aalo.maxy = y + radius;

            converted.add(aalo);

        } else if (root.getTagName().equals("line")) {

            double x1 = root.getPresAbsolute("x1").getDoubleValue();
            double y1 = root.getPresAbsolute("y1").getDoubleValue();
            double x2 = root.getPresAbsolute("x2").getDoubleValue();
            double y2 = root.getPresAbsolute("y2").getDoubleValue();

            Shape aalo = new Shape();

            aalo.type = "Line";
            aalo.linx = x1;
            aalo.liny = y1;
            aalo.linxa = x2;
            aalo.linya = y2;

            aalo.linx2 = x1;
            aalo.liny2 = y1;
            aalo.linxa2 = x2;
            aalo.linya2 = y2;

            aalo.minx = Math.min(x1, x2);
            aalo.miny = Math.min(y1, y2);
            aalo.maxx = Math.max(x1, x2);
            aalo.maxy = Math.max(y1, y2);

            converted.add(aalo);

        } else if (root.getTagName().equals("tspan")) {

            String s = ((Tspan) root).getText();
            double x = root.getPresAbsolute("x").getDoubleValue();
            double y = root.getPresAbsolute("y").getDoubleValue();

            double valuea = 25.0;

            Shape aalo = new Shape();
            aalo.type = "Text";
            aalo.textx = x;
            aalo.texty = y;
            aalo.text = s;

            aalo.font = valuea;
            aalo.font2 = valuea;

            aalo.textx2 = x;
            aalo.texty2 = y;

            aalo.minx = x;
            aalo.miny = y;
            aalo.maxx = x + ((s.length()) * valuea);
            aalo.maxy = y + (valuea);

            converted.add(aalo);

        } else if (root.getTagName().equals("path")) {

            String love = root.getPresAbsolute("d").getStringValue();
            System.out.println(love);

            int state = 0;
            Shape aalo = new Shape();
            aalo.type = "Path";
            aalo.path = new ArrayList<>();

            PathType reserve = null;
            Coordinate r1 = null;
            String first = null, second = null;

            //<editor-fold defaultstate="collapsed" desc="State Machine">

            while (love.length() != 0) {

                System.out.println("State: " + state);
                System.out.println("Character: " + love.charAt(0));
                System.out.println(" ");

                switch (state) {
                    case 0:
                        r1 = new Coordinate();
                        if (love.charAt(0) == (' ')) {
                            love = love.substring(1, love.length());
                        } else if (love.charAt(0) == ('z') || love.charAt(0) == ('Z')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            reserve = new PathType();
                            reserve.Type = chor;
                            aalo.path.add(reserve);
                        } else if (love.charAt(0) == ('m') || love.charAt(0) == ('M') || love.charAt(0) == ('L') || love.charAt(0) == ('l')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            state = 1;
                            reserve = new PathType();
                            reserve.Type = chor;
                        } else if (love.charAt(0) == ('c') || love.charAt(0) == ('C')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            state = 5;
                            reserve = new PathType();
                            reserve.Type = chor;
                        }
                        break;

                    case 1:
                        if (love.charAt(0) == (' ')) {
                            love = love.substring(1, love.length());
                        } else if (love.charAt(0) == ('0') || love.charAt(0) == ('1') || love.charAt(0) == ('-') || love.charAt(0) == ('2') || love.charAt(0) == ('3') || love.charAt(0) == ('4') || love.charAt(0) == ('5') || love.charAt(0) == ('6') || love.charAt(0) == ('7') || love.charAt(0) == ('8') || love.charAt(0) == ('9')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            state = 2;
                            first = "";
                            first = first + chor;
                        }
                        break;

                    case 2:
                        if (love.charAt(0) == (' ') || love.charAt(0) == (',')) {
                            love = love.substring(1, love.length());
                            state = 3;
                            r1.x = (Double.parseDouble(first));
                        } else if (love.charAt(0) == ('0') || love.charAt(0) == ('1') || love.charAt(0) == ('2') || love.charAt(0) == ('3') || love.charAt(0) == ('.') || love.charAt(0) == ('4') || love.charAt(0) == ('5') || love.charAt(0) == ('6') || love.charAt(0) == ('7') || love.charAt(0) == ('8') || love.charAt(0) == ('9')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            first = first + chor;
                        }
                        break;

                    case 3:
                        if (love.charAt(0) == (' ')) {
                            love = love.substring(1, love.length());
                        } else if (love.charAt(0) == ('0') || love.charAt(0) == ('1') || love.charAt(0) == ('-') || love.charAt(0) == ('2') || love.charAt(0) == ('3') || love.charAt(0) == ('4') || love.charAt(0) == ('5') || love.charAt(0) == ('6') || love.charAt(0) == ('7') || love.charAt(0) == ('8') || love.charAt(0) == ('9')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            state = 4;
                            second = "";
                            second = second + chor;
                        }
                        break;

                    case 4:
                        if (love.charAt(0) == (' ')) {
                            love = love.substring(1, love.length());
                            state = 0;
                            r1.y = Double.parseDouble(second);
                            reserve.single = r1;
                            aalo.path.add(reserve);
                        } else if (love.charAt(0) == ('z') || love.charAt(0) == ('Z')) {

                            char chor = love.charAt(0);

                            love = love.substring(1, love.length());
                            state = 0;
                            r1.y = Double.parseDouble(second);
                            reserve.single = r1;
                            aalo.path.add(reserve);

                            reserve = new PathType();
                            reserve.Type = chor;
                            aalo.path.add(reserve);

                        } else if (love.charAt(0) == ('c') || love.charAt(0) == ('C')) {

                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            state = 5;

                            r1.y = Double.parseDouble(second);
                            reserve.single = r1;
                            aalo.path.add(reserve);

                            reserve = new PathType();
                            reserve.Type = chor;

                        } else if (love.charAt(0) == ('0') || love.charAt(0) == ('1') || love.charAt(0) == ('2') || love.charAt(0) == ('.') || love.charAt(0) == ('3') || love.charAt(0) == ('4') || love.charAt(0) == ('5') || love.charAt(0) == ('6') || love.charAt(0) == ('7') || love.charAt(0) == ('8') || love.charAt(0) == ('9')) {
                            char chor = love.charAt(0);
                            second = second + chor;
                            if (love.length() == 1) {
                                r1.y = Double.parseDouble(second);
                                reserve.single = r1;
                                aalo.path.add(reserve);
                            }
                            love = love.substring(1, love.length());
                        }
                        break;

                    case 5:
                        if (love.charAt(0) == (' ')) {
                            love = love.substring(1, love.length());
                        } else if (love.charAt(0) == ('0') || love.charAt(0) == ('1') || love.charAt(0) == ('-') || love.charAt(0) == ('2') || love.charAt(0) == ('3') || love.charAt(0) == ('4') || love.charAt(0) == ('5') || love.charAt(0) == ('6') || love.charAt(0) == ('7') || love.charAt(0) == ('8') || love.charAt(0) == ('9')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            state = 6;
                            first = "";
                            first = first + chor;
                        }
                        break;

                    case 6:
                        if (love.charAt(0) == (' ') || love.charAt(0) == (',')) {
                            love = love.substring(1, love.length());
                            state = 7;
                            r1.x = (Double.parseDouble(first));
                        } else if (love.charAt(0) == ('0') || love.charAt(0) == ('1') || love.charAt(0) == ('2') || love.charAt(0) == ('3') || love.charAt(0) == ('.') || love.charAt(0) == ('4') || love.charAt(0) == ('5') || love.charAt(0) == ('6') || love.charAt(0) == ('7') || love.charAt(0) == ('8') || love.charAt(0) == ('9')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            first = first + chor;
                        }
                        break;

                    case 7:
                        if (love.charAt(0) == (' ')) {
                            love = love.substring(1, love.length());
                        } else if (love.charAt(0) == ('0') || love.charAt(0) == ('1') || love.charAt(0) == ('-') || love.charAt(0) == ('2') || love.charAt(0) == ('3') || love.charAt(0) == ('4') || love.charAt(0) == ('5') || love.charAt(0) == ('6') || love.charAt(0) == ('7') || love.charAt(0) == ('8') || love.charAt(0) == ('9')) {
                            char chor = love.charAt(0);
                            state = 8;
                            second = "";
                            second = second + chor;

                            if (love.length() == 1) {
                                r1.y = Double.parseDouble(second);
                                reserve.multi.add(r1);
                                aalo.path.add(reserve);
                            }

                            love = love.substring(1, love.length());
                        }
                        break;

                    case 8:
                        if (love.charAt(0) == (' ')) {
                            love = love.substring(1, love.length());
                            state = 9;
                            r1.y = Double.parseDouble(second);
                            reserve.multi.add(r1);
                        } else if (love.charAt(0) == ('z') || love.charAt(0) == ('Z')) {

                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            state = 0;

                            r1.y = Double.parseDouble(second);
                            reserve.multi.add(r1);
                            aalo.path.add(reserve);

                            reserve = new PathType();
                            reserve.Type = chor;
                            aalo.path.add(reserve);

                        } else if (love.charAt(0) == ('0') || love.charAt(0) == ('1') || love.charAt(0) == ('2') || love.charAt(0) == ('.') || love.charAt(0) == ('3') || love.charAt(0) == ('4') || love.charAt(0) == ('5') || love.charAt(0) == ('6') || love.charAt(0) == ('7') || love.charAt(0) == ('8') || love.charAt(0) == ('9')) {
                            char chor = love.charAt(0);
                            second = second + chor;
                            if (love.length() == 1) {
                                r1.y = Double.parseDouble(second);
                                reserve.multi.add(r1);
                                aalo.path.add(reserve);
                            }
                            love = love.substring(1, love.length());
                        }
                        break;

                    case 9:
                        r1 = new Coordinate();
                        if (love.charAt(0) == (' ')) {
                            if (love.length() == 1) {
                                aalo.path.add(reserve);
                            }
                            love = love.substring(1, love.length());
                        } else if (love.charAt(0) == ('z') || love.charAt(0) == ('Z')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            state = 0;
                            aalo.path.add(reserve);
                            reserve = new PathType();
                            reserve.Type = chor;
                            aalo.path.add(reserve);
                        } else if (love.charAt(0) == ('m') || love.charAt(0) == ('M') || love.charAt(0) == ('L') || love.charAt(0) == ('l')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            state = 1;
                            aalo.path.add(reserve);
                            reserve = new PathType();
                            reserve.Type = chor;
                        } else if (love.charAt(0) == ('c') || love.charAt(0) == ('C')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            state = 5;
                            aalo.path.add(reserve);
                            reserve = new PathType();
                            reserve.Type = chor;
                        } else if (love.charAt(0) == ('0') || love.charAt(0) == ('1') || love.charAt(0) == ('-') || love.charAt(0) == ('2') || love.charAt(0) == ('3') || love.charAt(0) == ('4') || love.charAt(0) == ('5') || love.charAt(0) == ('6') || love.charAt(0) == ('7') || love.charAt(0) == ('8') || love.charAt(0) == ('9')) {
                            char chor = love.charAt(0);
                            love = love.substring(1, love.length());
                            state = 6;
                            first = "";
                            first = first + chor;
                        }
                        break;

                }
            }
            //</editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Bounding the path">
            Coordinate current = new Coordinate();
            current.x = 0;
            current.y = 0;
            aalo.minx = Double.POSITIVE_INFINITY;
            aalo.miny = Double.POSITIVE_INFINITY;
            aalo.maxx = 0;
            aalo.maxy = 0;

            for (int i = 0; i < aalo.path.size(); i++) {

                if (i == 0) {

                    if (aalo.minx > aalo.path.get(i).single.x) {
                        aalo.minx = aalo.path.get(i).single.x;
                    }
                    if (aalo.miny > aalo.path.get(i).single.y) {
                        aalo.miny = aalo.path.get(i).single.y;
                    }
                    if (aalo.maxx < aalo.path.get(i).single.x) {
                        aalo.maxx = aalo.path.get(i).single.x;
                    }
                    if (aalo.maxy < aalo.path.get(i).single.y) {
                        aalo.maxy = aalo.path.get(i).single.y;
                    }

                    current.x = aalo.path.get(i).single.x;
                    current.y = aalo.path.get(i).single.y;

                } else {

                    if (aalo.path.get(i).Type == 'm') {

                        if (aalo.minx > current.x + aalo.path.get(i).single.x) {
                            aalo.minx = current.x + aalo.path.get(i).single.x;
                        }
                        if (aalo.miny > current.y + aalo.path.get(i).single.y) {
                            aalo.miny = current.y + aalo.path.get(i).single.y;
                        }
                        if (aalo.maxx < current.x + aalo.path.get(i).single.x) {
                            aalo.maxx = current.x + aalo.path.get(i).single.x;
                        }
                        if (aalo.maxy < current.y + aalo.path.get(i).single.y) {
                            aalo.maxy = current.y + aalo.path.get(i).single.y;
                        }

                        current.x = aalo.path.get(i).single.x;
                        current.y = aalo.path.get(i).single.y;

                    } else if (aalo.path.get(i).Type == 'M') {

                        if (aalo.minx > aalo.path.get(i).single.x) {
                            aalo.minx = aalo.path.get(i).single.x;
                        }
                        if (aalo.miny > aalo.path.get(i).single.y) {
                            aalo.miny = aalo.path.get(i).single.y;
                        }
                        if (aalo.maxx < aalo.path.get(i).single.x) {
                            aalo.maxx = aalo.path.get(i).single.x;
                        }
                        if (aalo.maxy < aalo.path.get(i).single.y) {
                            aalo.maxy = aalo.path.get(i).single.y;
                        }

                        current.x = aalo.path.get(i).single.x;
                        current.y = aalo.path.get(i).single.y;

                    } else if (aalo.path.get(i).Type == 'l') {

                        if (aalo.minx > current.x + aalo.path.get(i).single.x) {
                            aalo.minx = current.x + aalo.path.get(i).single.x;
                        }
                        if (aalo.miny > current.y + aalo.path.get(i).single.y) {
                            aalo.miny = current.y + aalo.path.get(i).single.y;
                        }
                        if (aalo.maxx < current.x + aalo.path.get(i).single.x) {
                            aalo.maxx = current.x + aalo.path.get(i).single.x;
                        }
                        if (aalo.maxy < current.y + aalo.path.get(i).single.y) {
                            aalo.maxy = current.y + aalo.path.get(i).single.y;
                        }

                        current.x = aalo.path.get(i).single.x;
                        current.y = aalo.path.get(i).single.y;

                    } else if (aalo.path.get(i).Type == 'L') {

                        if (aalo.minx > aalo.path.get(i).single.x) {
                            aalo.minx = aalo.path.get(i).single.x;
                        }
                        if (aalo.miny > aalo.path.get(i).single.y) {
                            aalo.miny = aalo.path.get(i).single.y;
                        }
                        if (aalo.maxx < aalo.path.get(i).single.x) {
                            aalo.maxx = aalo.path.get(i).single.x;
                        }
                        if (aalo.maxy < aalo.path.get(i).single.y) {
                            aalo.maxy = aalo.path.get(i).single.y;
                        }

                        current.x = aalo.path.get(i).single.x;
                        current.y = aalo.path.get(i).single.y;

                    } else if (aalo.path.get(i).Type == 'c') {

                        for (int a = 0; a < aalo.path.get(i).multi.size(); a++) {

                            if (aalo.minx > current.x + aalo.path.get(i).multi.get(a).x) {
                                aalo.minx = current.x + aalo.path.get(i).multi.get(a).x;
                            }
                            if (aalo.miny > current.y + aalo.path.get(i).multi.get(a).y) {
                                aalo.miny = current.y + aalo.path.get(i).multi.get(a).y;
                            }
                            if (aalo.maxx < current.x + aalo.path.get(i).multi.get(a).x) {
                                aalo.maxx = current.x + aalo.path.get(i).multi.get(a).x;
                            }
                            if (aalo.maxy < current.y + aalo.path.get(i).multi.get(a).y) {
                                aalo.maxy = current.y + aalo.path.get(i).multi.get(a).y;
                            }

                            if (a % 3 == 2) {
                                current.x = current.x + aalo.path.get(i).multi.get(a).x;
                                current.y = current.y + aalo.path.get(i).multi.get(a).y;
                            }

                        }

                    } else if (aalo.path.get(i).Type == 'C') {

                        for (int a = 0; a < aalo.path.get(i).multi.size(); a++) {

                            if (aalo.minx > aalo.path.get(i).multi.get(a).x) {
                                aalo.minx = aalo.path.get(i).multi.get(a).x;
                            }
                            if (aalo.miny > aalo.path.get(i).multi.get(a).y) {
                                aalo.miny = aalo.path.get(i).multi.get(a).y;
                            }
                            if (aalo.maxx < aalo.path.get(i).multi.get(a).x) {
                                aalo.maxx = aalo.path.get(i).multi.get(a).x;
                            }
                            if (aalo.maxy < aalo.path.get(i).multi.get(a).y) {
                                aalo.maxy = aalo.path.get(i).multi.get(a).y;
                            }

                            if (a % 3 == 2) {
                                current.x = aalo.path.get(i).multi.get(a).x;
                                current.y = aalo.path.get(i).multi.get(a).y;
                            }

                        }

                    }

                }

            }
            // </editor-fold>

            converted.add(aalo);

        }

        int rambo = root.getNumChildren();

        for (int i = 0; i < rambo; i++) {
            converter(root.getChild(i));
        }

    }

    private void scaling() {

        double minx = Double.POSITIVE_INFINITY;
        double miny = Double.POSITIVE_INFINITY;
        double maxx = 0;
        double maxy = 0;

        int is = converted.size();
        for (int iki = 0; iki < is; iki++) {

            Shape l = converted.get(iki);

            if (l.minx < minx) {
                minx = l.minx;
            }

            if (l.miny < miny) {
                miny = l.miny;
            }

            if (l.maxx > maxx) {
                maxx = l.maxx;
            }

            if (l.maxy > maxy) {
                maxy = l.maxy;
            }

        }

        // <editor-fold defaultstate="collapsed" desc="Scaling">

        double inputheight = (maxy - miny) + 20;
        double inputwidth = (maxx - minx) + 20;

        double ratio = ((double) this.getHeight()) / 297;
        double newwidth = ratio * 210;
        double h2 = this.getHeight() - 10;
        double w1 = ((this.getWidth() - newwidth) / 2) + 10;
        double w2 = ((this.getWidth() + newwidth) / 2) - 10;

        double asliheight = (h2 - 5) - (h2 / 4);
        double asliwidth = (w2 - 5) - (w1 + 5);

        double xshift = 0.0;
        double yshift = 0.0;
        double scaling = 0.0;

        double xshift2 = 0.0;
        double yshift2 = 0.0;
        double scaling2 = 0.0;

        double asliwidth2 = 746.21;
        double asliheight2 = (1034.6468) - (1039.6468 / 4);

        if (inputheight >= inputwidth) {

            scaling = (asliheight / inputheight);
            scaling2 = (asliheight2 / inputheight);

            double inputwidth2 = inputwidth * scaling2;
            inputwidth = inputwidth * scaling;

            xshift = (w1 + 5) + ((asliwidth - inputwidth) / 2);
            yshift = h2 / 4;

            xshift2 = (40) + ((asliwidth2 - inputwidth2) / 2);
            yshift2 = 1039.6468 / 4;

        } else {

            scaling = (asliwidth / inputwidth);
            scaling2 = (asliwidth2 / inputwidth);

            double inputheight2 = inputheight * scaling2;
            inputheight = inputheight * scaling;

            xshift = (w1 + 5);
            yshift = h2 / 4 + ((asliheight - inputheight) / 2);

            xshift2 = 40;
            yshift2 = (1039.6468 / 4) + ((asliheight2 - inputheight2) / 2);

        }


        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Applying the Scaling">

        for (int iki = 0; iki < is; iki++) {

            Shape l = converted.get(iki);

            if (l.type.equals("Rect")) {

                //Parameters for displaying a rectangle
                l.recheight = l.recheight * scaling;
                l.recwidth = l.recwidth * scaling;
                l.recx = (l.recx - (minx - 10)) * scaling + xshift;
                l.recy = (l.recy - (miny - 10)) * scaling + yshift;

                //Parameters for printing a rectangle
                l.recheight2 = l.recheight2 * scaling2;
                l.recwidth2 = l.recwidth2 * scaling2;
                l.recx2 = (l.recx2 - (minx - 10)) * scaling2 + xshift2;
                l.recy2 = (l.recy2 - (miny - 10)) * scaling2 + yshift2;

            } else if (l.type.equals("Circ")) {

                //Parameters for displaying a circle
                l.circr = l.circr * scaling;
                l.circx = (l.circx - (minx - 10)) * scaling + xshift;
                l.circy = (l.circy - (miny - 10)) * scaling + yshift;

                //Parameters for printing a circle
                l.circr2 = l.circr2 * scaling2;
                l.circx2 = (l.circx2 - (minx - 10)) * scaling2 + xshift2;
                l.circy2 = (l.circy2 - (miny - 10)) * scaling2 + yshift2;

            } else if (l.type.equals("Text")) {

                //Parameters for displaying text
                l.textx = (l.textx - (minx - 10)) * scaling + xshift;
                l.texty = (l.texty - (miny - 10)) * scaling + yshift;
                l.font = l.font;

                //Parameters for printing text
                l.textx2 = (l.textx2 - (minx - 10)) * scaling2 + xshift2;
                l.texty2 = (l.texty2 - (miny - 10)) * scaling2 + yshift2;
                l.font2 = l.font2;

            } else if (l.type.equals("Line")) {

                //Parameters for displaying a line
                l.linx = (l.linx - (minx - 10)) * scaling + xshift;
                l.liny = (l.liny - (miny - 10)) * scaling + yshift;
                l.linxa = (l.linxa - (minx - 10)) * scaling + xshift;
                l.linya = (l.linya - (miny - 10)) * scaling + yshift;

                //Parameters for printing a line
                l.linx2 = (l.linx2 - (minx - 10)) * scaling2 + xshift2;
                l.liny2 = (l.liny2 - (miny - 10)) * scaling2 + yshift2;
                l.linxa2 = (l.linxa2 - (minx - 10)) * scaling2 + xshift2;
                l.linya2 = (l.linya2 - (miny - 10)) * scaling2 + yshift2;

            } else if (l.type.equals("Path")) {

                int okok = l.path.size();

                for (int i = 0; i < okok; i++) {

                    /* System.out.println(l.path.get(i).Type + " " + l.path.get(i).single.x + " " + l.path.get(i).single.y);
                    
                     for(int a = 0; a<l.path.get(i).multi.size();a++){
                     System.out.println("X "+l.path.get(i).multi.get(a).x);
                     System.out.println("Y "+l.path.get(i).multi.get(a).y);
                     }*/

                    if ((l.path.get(i).Type) == ('m') || (l.path.get(i).Type) == ('l')) {


                        if (i == 0) {

                            //For printing a path
                            l.path.get(i).single2.x = (l.path.get(i).single2.x - (minx - 10)) * scaling2 + xshift2;
                            l.path.get(i).single2.y = (l.path.get(i).single2.y - (miny - 10)) * scaling2 + yshift2;

                            //For displaying a path
                            l.path.get(i).single.x = (l.path.get(i).single.x - (minx - 10)) * scaling + xshift;
                            l.path.get(i).single.y = (l.path.get(i).single.y - (miny - 10)) * scaling + yshift;
                        } else {

                            //For printing a path
                            l.path.get(i).single2.x = l.path.get(i).single2.x * scaling2;
                            l.path.get(i).single2.y = l.path.get(i).single2.y * scaling2;

                            //For displaying a path
                            l.path.get(i).single.x = l.path.get(i).single.x * scaling;
                            l.path.get(i).single.y = l.path.get(i).single.y * scaling;
                        }


                    };

                    if ((l.path.get(i).Type) == ('M') || (l.path.get(i).Type) == ('L')) {

                        //For printing a path
                        l.path.get(i).single2.x = (l.path.get(i).single2.x - (minx - 10)) * scaling2 + xshift2;
                        l.path.get(i).single2.y = (l.path.get(i).single2.y - (miny - 10)) * scaling2 + yshift2;

                        //For displaying a path
                        l.path.get(i).single.x = (l.path.get(i).single.x - (minx - 10)) * scaling + xshift;
                        l.path.get(i).single.y = (l.path.get(i).single.y - (miny - 10)) * scaling + yshift;

                    };

                    if ((l.path.get(i).Type) == 'C') {

                        for (int a = 0; a < l.path.get(i).multi.size(); a++) {

                            //For displaying a path
                            l.path.get(i).multi.get(a).x = (l.path.get(i).multi.get(a).x - (minx - 10)) * scaling + xshift;
                            l.path.get(i).multi.get(a).y = (l.path.get(i).multi.get(a).y - (miny - 10)) * scaling + yshift;

                        }

                    };

                    if ((l.path.get(i).Type) == 'c') {

                        for (int a = 0; a < l.path.get(i).multi.size(); a++) {

                            //For displaying a path
                            l.path.get(i).multi.get(a).x = (l.path.get(i).multi.get(a).x) * scaling;
                            l.path.get(i).multi.get(a).y = (l.path.get(i).multi.get(a).y) * scaling;

                        }

                    };
                }

            }

        }

        // </editor-fold>


    }

    private void aslipaint(Graphics2D g2d) {

        int is = converted.size();
        for (int iki = 0; iki < is; iki++) {

            Shape l = converted.get(iki);

            if (l.type.equals("Rect")) {

                g2d.draw(new Rectangle2D.Double(l.recx, l.recy, l.recwidth, l.recheight));

            }

            if (l.type.equals("Circ")) {

                g2d.draw(new Ellipse2D.Double(l.circx - l.circr, l.circy - l.circr, 2 * l.circr, 2 * l.circr));

            }

            if (l.type.equals("Text")) {

                g2d.setFont(new Font("Braille", Font.PLAIN, (int) l.font));
                g2d.drawString(l.text, (float) l.textx, (float) l.texty);
            }

            if (l.type.equals("Line")) {

                g2d.draw(new Line2D.Double(l.linx, l.liny, l.linxa, l.linya));
            }

            if (l.type.equals("Path")) {


                GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, l.path.size());
                Coordinate current = new Coordinate();
                current.x = 0;
                current.y = 0;

                for (int i = 0; i < l.path.size(); i++) {

                    if (i == 0) {
                        polyline.moveTo(l.path.get(i).single.x, l.path.get(i).single.y);
                        current.x = l.path.get(i).single.x;
                        current.y = l.path.get(i).single.y;
                    } else {

                        if (l.path.get(i).Type == 'm') {

                            polyline.moveTo(current.x + l.path.get(i).single.x, current.y + l.path.get(i).single.y);
                            current.x = l.path.get(i).single.x;
                            current.y = l.path.get(i).single.y;

                        } else if (l.path.get(i).Type == 'M') {

                            polyline.moveTo(l.path.get(i).single.x, l.path.get(i).single.y);
                            current.x = l.path.get(i).single.x;
                            current.y = l.path.get(i).single.y;

                        } else if (l.path.get(i).Type == 'l') {

                            polyline.lineTo(current.x + l.path.get(i).single.x, current.y + l.path.get(i).single.y);
                            current.x = l.path.get(i).single.x;
                            current.y = l.path.get(i).single.y;

                        } else if (l.path.get(i).Type == 'L') {
                            polyline.lineTo(l.path.get(i).single.x, l.path.get(i).single.y);
                            current.x = l.path.get(i).single.x;
                            current.y = l.path.get(i).single.y;

                        } else if (l.path.get(i).Type == 'c') {

                            for (int a = 0; a < l.path.get(i).multi.size(); a = a + 3) {

                                double x1 = current.x + l.path.get(i).multi.get(a).x;
                                double y1 = current.y + l.path.get(i).multi.get(a).y;
                                double x2 = current.x + l.path.get(i).multi.get(a + 1).x;
                                double y2 = current.y + l.path.get(i).multi.get(a + 1).y;
                                double x3 = current.x + l.path.get(i).multi.get(a + 2).x;
                                double y3 = current.y + l.path.get(i).multi.get(a + 2).y;

                                polyline.curveTo(x1, y1, x2, y2, x3, y3);
                                current.x = x3;
                                current.y = y3;
                            }

                        } else if (l.path.get(i).Type == 'C') {

                            for (int a = 0; a < l.path.get(i).multi.size(); a = a + 3) {

                                double x1 = l.path.get(i).multi.get(a).x;
                                double y1 = l.path.get(i).multi.get(a).y;
                                double x2 = l.path.get(i).multi.get(a + 1).x;
                                double y2 = l.path.get(i).multi.get(a + 1).y;
                                double x3 = l.path.get(i).multi.get(a + 2).x;
                                double y3 = l.path.get(i).multi.get(a + 2).y;

                                polyline.curveTo(x1, y1, x2, y2, x3, y3);
                                current.x = x3;
                                current.y = y3;

                            }

                        } else if (l.path.get(i).Type == 'z') {

                            polyline.closePath();

                        } else if (l.path.get(i).Type == 'Z') {

                            polyline.closePath();
                        }



                    }

                }

                g2d.draw(polyline);



            }
        }

    }
}

public class MainClass extends javax.swing.JFrame {

    public MainClass() {
        initComponents();
        myInitComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        Grp = new javax.swing.ButtonGroup();
        Grp2 = new javax.swing.ButtonGroup();
        fileChooser2 = new javax.swing.JFileChooser();
        toolbar = new javax.swing.JToolBar();
        diag = new javax.swing.JToggleButton();
        chem = new javax.swing.JToggleButton();
        function = new javax.swing.JToggleButton();
        menu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Save = new javax.swing.JMenuItem();
        SaveAs = new javax.swing.JMenuItem();

        fileChooser.setControlButtonsAreShown(false);
        fileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\Mrinal\\Documents\\Academics\\MTP"));
        fileChooser.setFileFilter(abc);

        fileChooser2.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fileChooser2.setCurrentDirectory(new java.io.File("C:\\Users\\Mrinal\\Documents\\Academics\\MTP"));
        fileChooser2.setFileFilter(abc);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MrinalMTP");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        toolbar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toolbar.setFloatable(false);
        toolbar.setRollover(true);

        Grp.add(diag);
        diag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/svg/resources/rsz_square-dashed-512.png"))); // NOI18N
        diag.setText("Diagram Conversion");
        diag.setBorder(null);
        diag.setFocusable(false);
        diag.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        diag.setMaximumSize(new java.awt.Dimension(140, 80));
        diag.setMinimumSize(new java.awt.Dimension(140, 80));
        diag.setPreferredSize(new java.awt.Dimension(140, 80));
        diag.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        diag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diagActionPerformed(evt);
            }
        });
        toolbar.add(diag);

        Grp.add(chem);
        chem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/svg/resources/rsz_1tumblr_m4c9wrrklq1rw1p5qo1_1280.png"))); // NOI18N
        chem.setText("Chemical Equation");
        chem.setBorder(null);
        chem.setFocusable(false);
        chem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chem.setMaximumSize(new java.awt.Dimension(140, 80));
        chem.setMinimumSize(new java.awt.Dimension(140, 80));
        chem.setPreferredSize(new java.awt.Dimension(140, 80));
        chem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        chem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chemActionPerformed(evt);
            }
        });
        toolbar.add(chem);

        Grp.add(function);
        function.setIcon(new javax.swing.ImageIcon(getClass().getResource("/svg/resources/rsz_1picture1.png"))); // NOI18N
        function.setText("Function");
        function.setBorder(null);
        function.setFocusable(false);
        function.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        function.setMaximumSize(new java.awt.Dimension(140, 80));
        function.setMinimumSize(new java.awt.Dimension(140, 80));
        function.setPreferredSize(new java.awt.Dimension(140, 80));
        function.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        function.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                functionActionPerformed(evt);
            }
        });
        toolbar.add(function);

        jMenu1.setText("File");

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });
        jMenu1.add(Save);

        SaveAs.setText("Save As...");
        SaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveAsActionPerformed(evt);
            }
        });
        jMenu1.add(SaveAs);

        menu.add(jMenu1);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 359, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Actions Performed">
    // <editor-fold defaultstate="collapsed" desc="Menu Actions Performed">
    private void diagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diagActionPerformed

        panel.removeAll();
        panel.updateUI();
        panel.zero();
        r1.setSelectedIndex(0);
        r2.setSelectedIndex(0);
        r3.setSelectedIndex(0);
        r4.setSelectedIndex(0);
        p1.setSelectedIndex(0);
        p2.setSelectedIndex(0);
        p3.setSelectedIndex(0);
        p4.setSelectedIndex(0);
        txtbx.setText("");
        funbx.setText("");
        panel.value = 1;
        this.setTitle("MrinalMTP");
        panel.repaint();
        diagchoice();
    }//GEN-LAST:event_diagActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed

        if (panel.f == null) {
            SaveAsActionPerformed(evt);
        } else {
            FileOutputStream outStream = null;
            Writer outf = null;
            try {

                File g = panel.f;
                outStream = new FileOutputStream(g + ".svg");
                outf = new OutputStreamWriter(outStream, "UTF-8");

                double h2 = 0;
                double w1 = 0, w2 = 0, wmid = 0, wq1 = 0, wq2 = 0, we1 = 0, we2 = 0, we3 = 0, we4 = 0;
                double w3 = 0;
                double h1 = 0;

                h2 = 1049.6468 - 10;
                w1 = 35;
                w2 = 791.21;
                w3 = w2 - 35.022;
                h1 = 10;

                wmid = (w1 + w2) / 2;
                wq1 = (w1 + wmid) / 2;
                wq2 = (wmid + w2) / 2;
                we1 = (w1 + wq1) / 2;
                we2 = (wq1 + wmid) / 2;
                we3 = (wmid + wq2) / 2;
                we4 = (w2 + wq2) / 2;


                String txt1 = "", txt2 = "", txtbuffer = "";

                for (int t = 0; t < panel.txt.length(); t++) {

                    if ((panel.txt.charAt(t) == (' '))) {

                        if (t <= 29) {
                            txt1 = txt1 + txtbuffer + ' ';
                            txtbuffer = "";
                        }
                        if ((t > 29) && (t <= 59)) {
                            txt2 = txt2 + txtbuffer + ' ';
                            txtbuffer = "";
                        }

                    } else {
                        txtbuffer = txtbuffer + panel.txt.charAt(t);
                    }

                }

                outf.write("<svg width=\"810\" height=\"1055\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" style=\"fill:none;stroke-width:2\">\n");
                outf.write("    <polyline points=\"" + w1 + " " + h1 + " " + w2 + " " + h1 + " " + w2 + " " + h2 + " " + w1 + " " + h2 + " " + w1 + " " + h1 + "\" style=\"stroke:black\"/>\n");
                outf.write("    <circle cx=\"" + w3 + "\" cy=\"" + (h1 + 35.022) + "\" r=\"23.348\" style=\"stroke:black\"/>\n");

                // Text box

                outf.write(" <text x=\"" + (w1 + 23.348) + "\" y=\"" + (h1 + 116.74) + "\" font-family=\"Braille\" font-size=\"29\" fill=\"black\">\n");
                outf.write(txt1 + "\n");
                outf.write("</text>\n");
                outf.write(" <text x=\"" + (w1 + 23.348) + "\" y=\"" + (h1 + 157.599) + "\" font-family=\"Braille\" font-size=\"29\" fill=\"black\">\n");
                outf.write(txt2 + "\n");
                outf.write("</text>\n");


                if (panel.value == 2) {

                    //Reactants

                    if ((panel.nor == 2) || (panel.nor == 4)) {
                        outf.write("    <polyline points=\"" + (wmid) + " " + (h1 + 369.5) + " " + (wmid) + " " + (h1 + 385.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wmid - 8) + " " + (h1 + 377.5) + " " + (wmid + 8) + " " + (h1 + 377.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nor == 4) {
                        outf.write("    <polyline points=\"" + (wq1) + " " + (h1 + 369.5) + " " + (wq1) + " " + (h1 + 385.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wq1 - 8) + " " + (h1 + 377.5) + " " + (wq1 + 8) + " " + (h1 + 377.5) + "\" style=\"stroke:black\"/>");

                        outf.write("    <polyline points=\"" + (wq2) + " " + (h1 + 369.5) + " " + (wq2) + " " + (h1 + 385.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wq2 - 8) + " " + (h1 + 377.5) + " " + (wq2 + 8) + " " + (h1 + 377.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nor == 3) {
                        outf.write("    <polyline points=\"" + (we2) + " " + (h1 + 369.5) + " " + (we2) + " " + (h1 + 385.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (we2 - 8) + " " + (h1 + 377.5) + " " + (we2 + 8) + " " + (h1 + 377.5) + "\" style=\"stroke:black\"/>");

                        outf.write("    <polyline points=\"" + (we3) + " " + (h1 + 369.5) + " " + (we3) + " " + (h1 + 385.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (we3 - 8) + " " + (h1 + 377.5) + " " + (we3 + 8) + " " + (h1 + 377.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nor == 2) {
                        rmaker2(outf, we2, h1 + 170, panel.r1nam);
                        rmaker2(outf, we3, h1 + 170, panel.r2nam);
                    }

                    if (panel.nor == 4) {
                        rmaker2(outf, we1, h1 + 170, panel.r1nam);
                        rmaker2(outf, we2, h1 + 170, panel.r2nam);
                        rmaker2(outf, we3, h1 + 170, panel.r3nam);
                        rmaker2(outf, we4, h1 + 170, panel.r4nam);
                    }

                    if (panel.nor == 1) {
                        rmaker2(outf, wmid, h1 + 170, panel.r1nam);
                    }

                    if (panel.nor == 3) {
                        rmaker2(outf, wq1, h1 + 170, panel.r1nam);
                        rmaker2(outf, wmid, h1 + 170, panel.r2nam);
                        rmaker2(outf, wq2, h1 + 170, panel.r3nam);
                    }



                    //Arrow
                    if ((panel.nop != 0) && (panel.nor != 0)) {
                        outf.write("    <polyline points=\"" + (wmid) + " " + (h1 + 450) + " " + (wmid) + " " + (h1 + 700) + " " + (wmid + 24) + " " + (h1 + 680) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wmid) + " " + (h1 + 700) + " " + (wmid - 24) + " " + (h1 + 680) + "\" style=\"stroke:black\"/>");
                    }

                    //Products

                    if ((panel.nop == 2) || (panel.nop == 4)) {
                        outf.write("    <polyline points=\"" + (wmid) + " " + (h1 + 807.5) + " " + (wmid) + " " + (h1 + 823.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wmid - 8) + " " + (h1 + 815.5) + " " + (wmid + 8) + " " + (h1 + 815.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nop == 4) {
                        outf.write("    <polyline points=\"" + (wq1) + " " + (h1 + 807.5) + " " + (wq1) + " " + (h1 + 823.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wq1 - 8) + " " + (h1 + 815.5) + " " + (wq1 + 8) + " " + (h1 + 815.5) + "\" style=\"stroke:black\"/>");

                        outf.write("    <polyline points=\"" + (wq2) + " " + (h1 + 807.5) + " " + (wq2) + " " + (h1 + 823.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wq2 - 8) + " " + (h1 + 815.5) + " " + (wq2 + 8) + " " + (h1 + 815.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nop == 3) {
                        outf.write("    <polyline points=\"" + (we2) + " " + (h1 + 807.5) + " " + (we2) + " " + (h1 + 823.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (we2 - 8) + " " + (h1 + 815.5) + " " + (we2 + 8) + " " + (h1 + 815.5) + "\" style=\"stroke:black\"/>");

                        outf.write("    <polyline points=\"" + (we3) + " " + (h1 + 807.5) + " " + (we3) + " " + (h1 + 823.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (we3 - 8) + " " + (h1 + 815.5) + " " + (we3 + 8) + " " + (h1 + 815.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nop == 2) {
                        rmaker2(outf, we2, h1 + 608, panel.p1nam);
                        rmaker2(outf, we3, h1 + 608, panel.p2nam);
                    }

                    if (panel.nop == 4) {
                        rmaker2(outf, we1, h1 + 608, panel.p1nam);
                        rmaker2(outf, we2, h1 + 608, panel.p2nam);
                        rmaker2(outf, we3, h1 + 608, panel.p3nam);
                        rmaker2(outf, we4, h1 + 608, panel.p4nam);
                    }

                    if (panel.nop == 1) {
                        rmaker2(outf, wmid, h1 + 608, panel.p1nam);
                    }

                    if (panel.nop == 3) {
                        rmaker2(outf, wq1, h1 + 608, panel.p1nam);
                        rmaker2(outf, wmid, h1 + 608, panel.p2nam);
                        rmaker2(outf, wq2, h1 + 608, panel.p3nam);
                    }
                } else if (panel.value == 1) {

                    int is = panel.converted.size();
                    for (int iki = 0; iki < is; iki++) {

                        Shape l = panel.converted.get(iki);

                        if (l.type.equals("Rect")) {

                            outf.write("    <polyline points=\"" + l.recx2 + " " + l.recy2 + " " + (l.recx2 + l.recwidth2) + " " + l.recy2 + " " + (l.recx2 + l.recwidth2) + " " + (l.recy2 + l.recheight2) + " " + l.recx2 + " " + (l.recy2 + l.recheight2) + " " + l.recx2 + " " + l.recy2 + "\" style=\"stroke:black\"/>\n");

                        }

                        if (l.type.equals("Text")) {

                            outf.write(" <text x=\"" + l.textx2 + "\" y=\"" + l.texty2 + "\" font-family=\"Braille\" font-size=\"" + (int) l.font2 + "\" fill=\"black\">\n");
                            outf.write(l.text + "\n");
                            outf.write("</text>\n");


                        }
                    }

                } else if (panel.value == 3) {

                    //Coordinate axes

                    outf.write("    <polyline points=\"" + (wmid) + " " + (h1 + 300 * 1.1674) + " " + (wmid) + " " + (h1 + 800 * 1.1674) + "\" style=\"stroke:black;stroke-width:1\"/>");
                    outf.write("    <polyline points=\"" + (wmid - 250 * 1.1674) + " " + (h1 + 550 * 1.1674) + " " + (wmid + 250 * 1.1674) + " " + (h1 + 550 * 1.1674) + "\" style=\"stroke:black;stroke-width:1\"/>");

                    outf.write(" <text x=\"" + (wmid + 260 * 1.1674) + "\" y=\"" + (h1 + 560 * 1.1674) + "\" font-family=\"Braille\" font-size=\"29.185\" fill=\"black\">\n");
                    outf.write("X" + "\n");
                    outf.write("</text>\n");

                    outf.write(" <text x=\"" + (wmid - 290 * 1.1674) + "\" y=\"" + (h1 + 560 * 1.1674) + "\" font-family=\"Braille\" font-size=\"29.185\" fill=\"black\">\n");
                    outf.write("X'" + "\n");
                    outf.write("</text>\n");

                    outf.write(" <text x=\"" + (wmid - 10 * 1.1674) + "\" y=\"" + (h1 + 290 * 1.1674) + "\" font-family=\"Braille\" font-size=\"29.185\" fill=\"black\">\n");
                    outf.write("Y" + "\n");
                    outf.write("</text>\n");

                    outf.write(" <text x=\"" + (wmid - 10 * 1.1674) + "\" y=\"" + (h1 + 830 * 1.1674) + "\" font-family=\"Braille\" font-size=\"29.185\" fill=\"black\">\n");
                    outf.write("Y'" + "\n");
                    outf.write("</text>\n");




                    if (panel.fun != null) {

                        for (double m = -250 * 1.1674; m < 250 * 1.1674; m = m + 1) {
                            panel.vm.setValue("x", 0.04 * m);
                            double a = 25 * panel.fun.eval(panel.vm, panel.fm);
                            panel.vm.setValue("x", 0.04 * (m + 1));
                            double b = 25 * panel.fun.eval(panel.vm, panel.fm);
                            if ((a <= 250 * 1.1674) && (b <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= -250 * 1.1674)) {
                                outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black\"/>");
                            } else if ((a <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= 250 * 1.1674)) {
                                outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 300 * 1.1674) + "\" style=\"stroke:black\"/>");
                            } else if ((a <= -250 * 1.1674) && (b <= 250 * 1.1674) && (b >= -250 * 1.1674)) {
                                outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 800 * 1.1674) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black\"/>");
                            }

                        }
                    }
                }


                outf.write("</svg>\n");

                outf.flush();
                outf.close();

            } catch (IOException ex) {
                Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_SaveActionPerformed

    private void SaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveAsActionPerformed
        int returnVal = fileChooser2.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser2.getSelectedFile();
            String sav = "" + file;
            FileOutputStream outStream = null;
            Writer outf = null;
            try {

                outStream = new FileOutputStream(sav + ".svg");
                this.setTitle(file.getName() + ".svg - MrinalMTP");
                outf = new OutputStreamWriter(outStream, "UTF-8");

                // <editor-fold defaultstate="collapsed" desc="General">

                double h2 = 0;
                double w1 = 0, w2 = 0, wmid = 0, wq1 = 0, wq2 = 0, we1 = 0, we2 = 0, we3 = 0, we4 = 0;
                double w3 = 0;
                double h1 = 0;

                h2 = 1049.6468 - 10;
                w1 = 35;
                w2 = 791.21;
                w3 = w2 - 35.022;
                h1 = 10;

                wmid = (w1 + w2) / 2;
                wq1 = (w1 + wmid) / 2;
                wq2 = (wmid + w2) / 2;
                we1 = (w1 + wq1) / 2;
                we2 = (wq1 + wmid) / 2;
                we3 = (wmid + wq2) / 2;
                we4 = (w2 + wq2) / 2;


                String txt1 = "", txt2 = "", txtbuffer = "";

                for (int t = 0; t < panel.txt.length(); t++) {

                    if ((panel.txt.charAt(t) == (' '))) {

                        if (t <= 29) {
                            txt1 = txt1 + txtbuffer + ' ';
                            txtbuffer = "";
                        }
                        if ((t > 29) && (t <= 59)) {
                            txt2 = txt2 + txtbuffer + ' ';
                            txtbuffer = "";
                        }

                    } else {
                        txtbuffer = txtbuffer + panel.txt.charAt(t);
                    }

                }

                outf.write("<svg width=\"810\" height=\"1055\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" style=\"fill:none;stroke-width:2\">\n");
                outf.write("    <polyline points=\"" + w1 + " " + h1 + " " + w2 + " " + h1 + " " + w2 + " " + h2 + " " + w1 + " " + h2 + " " + w1 + " " + h1 + "\" style=\"stroke:black\"/>\n");
                outf.write("    <circle cx=\"" + w3 + "\" cy=\"" + (h1 + 35.022) + "\" r=\"23.348\" style=\"stroke:black\"/>\n");

                // Text box

                outf.write(" <text x=\"" + (w1 + 23.348) + "\" y=\"" + (h1 + 116.74) + "\" font-family=\"Braille\" font-size=\"29\" fill=\"black\">\n");
                outf.write(txt1 + "\n");
                outf.write("</text>\n");
                outf.write(" <text x=\"" + (w1 + 23.348) + "\" y=\"" + (h1 + 157.599) + "\" font-family=\"Braille\" font-size=\"29\" fill=\"black\">\n");
                outf.write(txt2 + "\n");
                outf.write("</text>\n");

                // </editor-fold>

                // <editor-fold defaultstate="collapsed" desc="Chemical">

                if (panel.value == 2) {

                    //Reactants

                    if ((panel.nor == 2) || (panel.nor == 4)) {
                        outf.write("    <polyline points=\"" + (wmid) + " " + (h1 + 369.5) + " " + (wmid) + " " + (h1 + 385.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wmid - 8) + " " + (h1 + 377.5) + " " + (wmid + 8) + " " + (h1 + 377.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nor == 4) {
                        outf.write("    <polyline points=\"" + (wq1) + " " + (h1 + 369.5) + " " + (wq1) + " " + (h1 + 385.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wq1 - 8) + " " + (h1 + 377.5) + " " + (wq1 + 8) + " " + (h1 + 377.5) + "\" style=\"stroke:black\"/>");

                        outf.write("    <polyline points=\"" + (wq2) + " " + (h1 + 369.5) + " " + (wq2) + " " + (h1 + 385.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wq2 - 8) + " " + (h1 + 377.5) + " " + (wq2 + 8) + " " + (h1 + 377.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nor == 3) {
                        outf.write("    <polyline points=\"" + (we2) + " " + (h1 + 369.5) + " " + (we2) + " " + (h1 + 385.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (we2 - 8) + " " + (h1 + 377.5) + " " + (we2 + 8) + " " + (h1 + 377.5) + "\" style=\"stroke:black\"/>");

                        outf.write("    <polyline points=\"" + (we3) + " " + (h1 + 369.5) + " " + (we3) + " " + (h1 + 385.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (we3 - 8) + " " + (h1 + 377.5) + " " + (we3 + 8) + " " + (h1 + 377.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nor == 2) {
                        rmaker2(outf, we2, h1 + 170, panel.r1nam);
                        rmaker2(outf, we3, h1 + 170, panel.r2nam);
                    }

                    if (panel.nor == 4) {
                        rmaker2(outf, we1, h1 + 170, panel.r1nam);
                        rmaker2(outf, we2, h1 + 170, panel.r2nam);
                        rmaker2(outf, we3, h1 + 170, panel.r3nam);
                        rmaker2(outf, we4, h1 + 170, panel.r4nam);
                    }

                    if (panel.nor == 1) {
                        rmaker2(outf, wmid, h1 + 170, panel.r1nam);
                    }

                    if (panel.nor == 3) {
                        rmaker2(outf, wq1, h1 + 170, panel.r1nam);
                        rmaker2(outf, wmid, h1 + 170, panel.r2nam);
                        rmaker2(outf, wq2, h1 + 170, panel.r3nam);
                    }



                    //Arrow
                    if ((panel.nop != 0) && (panel.nor != 0)) {
                        outf.write("    <polyline points=\"" + (wmid) + " " + (h1 + 450) + " " + (wmid) + " " + (h1 + 700) + " " + (wmid + 24) + " " + (h1 + 680) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wmid) + " " + (h1 + 700) + " " + (wmid - 24) + " " + (h1 + 680) + "\" style=\"stroke:black\"/>");
                    }

                    //Conditions
                    outf.write(" <text x=\"" + (wmid - (panel.pressure.length() * 20 * 1.1674)) + "\" y=\"" + (h1 + 550) + "\" font-family=\"Braille\" font-size=\"25\" fill=\"black\">\n");
                    outf.write(panel.pressure + "\n");
                    outf.write("</text>\n");
                    outf.write(" <text x=\"" + (wmid - (panel.temp.length() * 20 * 1.1674)) + "\" y=\"" + (h1 + 600) + "\" font-family=\"Braille\" font-size=\"25\" fill=\"black\">\n");
                    outf.write(panel.temp + "\n");
                    outf.write("</text>\n");

                    //Products

                    if ((panel.nop == 2) || (panel.nop == 4)) {
                        outf.write("    <polyline points=\"" + (wmid) + " " + (h1 + 807.5) + " " + (wmid) + " " + (h1 + 823.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wmid - 8) + " " + (h1 + 815.5) + " " + (wmid + 8) + " " + (h1 + 815.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nop == 4) {
                        outf.write("    <polyline points=\"" + (wq1) + " " + (h1 + 807.5) + " " + (wq1) + " " + (h1 + 823.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wq1 - 8) + " " + (h1 + 815.5) + " " + (wq1 + 8) + " " + (h1 + 815.5) + "\" style=\"stroke:black\"/>");

                        outf.write("    <polyline points=\"" + (wq2) + " " + (h1 + 807.5) + " " + (wq2) + " " + (h1 + 823.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (wq2 - 8) + " " + (h1 + 815.5) + " " + (wq2 + 8) + " " + (h1 + 815.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nop == 3) {
                        outf.write("    <polyline points=\"" + (we2) + " " + (h1 + 807.5) + " " + (we2) + " " + (h1 + 823.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (we2 - 8) + " " + (h1 + 815.5) + " " + (we2 + 8) + " " + (h1 + 815.5) + "\" style=\"stroke:black\"/>");

                        outf.write("    <polyline points=\"" + (we3) + " " + (h1 + 807.5) + " " + (we3) + " " + (h1 + 823.5) + "\" style=\"stroke:black\"/>");
                        outf.write("    <polyline points=\"" + (we3 - 8) + " " + (h1 + 815.5) + " " + (we3 + 8) + " " + (h1 + 815.5) + "\" style=\"stroke:black\"/>");
                    }

                    if (panel.nop == 2) {
                        rmaker2(outf, we2, h1 + 608, panel.p1nam);
                        rmaker2(outf, we3, h1 + 608, panel.p2nam);
                    }

                    if (panel.nop == 4) {
                        rmaker2(outf, we1, h1 + 608, panel.p1nam);
                        rmaker2(outf, we2, h1 + 608, panel.p2nam);
                        rmaker2(outf, we3, h1 + 608, panel.p3nam);
                        rmaker2(outf, we4, h1 + 608, panel.p4nam);
                    }

                    if (panel.nop == 1) {
                        rmaker2(outf, wmid, h1 + 608, panel.p1nam);
                    }

                    if (panel.nop == 3) {
                        rmaker2(outf, wq1, h1 + 608, panel.p1nam);
                        rmaker2(outf, wmid, h1 + 608, panel.p2nam);
                        rmaker2(outf, wq2, h1 + 608, panel.p3nam);
                    }
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="Converter">
                else if (panel.value == 1) {

                    int is = panel.converted.size();
                    for (int iki = 0; iki < is; iki++) {

                        Shape l = panel.converted.get(iki);

                        if (l.type.equals("Rect")) {
                            outf.write("    <polyline points=\"" + l.recx2 + " " + l.recy2 + " " + (l.recx2 + l.recwidth2) + " " + l.recy2 + " " + (l.recx2 + l.recwidth2) + " " + (l.recy2 + l.recheight2) + " " + l.recx2 + " " + (l.recy2 + l.recheight2) + " " + l.recx2 + " " + l.recy2 + "\" style=\"stroke:black\"/>\n");

                        } else if (l.type.equals("Line")) {
                            outf.write("    <polyline points=\"" + l.linx2 + " " + l.liny2 + " " + l.linxa2 + " " + l.linya2 + "\" style=\"stroke:black\"/>\n");

                        } else if (l.type.equals("Circ")) {

                            outf.write("    <circle cx=\"" + l.circx2 + "\" cy=\"" + l.circy2 + "\" r=\"" + l.circr2 + "\" style=\"stroke:black\"/>\n");

                        } else if (l.type.equals("Text")) {

                            outf.write(" <text x=\"" + l.textx2 + "\" y=\"" + l.texty2 + "\" font-family=\"Braille\" font-size=\"" + (int) l.font2 + "\" fill=\"black\">\n");
                            outf.write(l.text + "\n");
                            outf.write("</text>\n");

                        }
                    }

                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="Functions">
                else if (panel.value == 3) {

                    //Coordinate axes

                    outf.write("    <polyline points=\"" + (wmid) + " " + (h1 + 300 * 1.1674) + " " + (wmid) + " " + (h1 + 800 * 1.1674) + "\" style=\"stroke:black;stroke-width:1\"/>");
                    outf.write("    <polyline points=\"" + (wmid - 250 * 1.1674) + " " + (h1 + 550 * 1.1674) + " " + (wmid + 250 * 1.1674) + " " + (h1 + 550 * 1.1674) + "\" style=\"stroke:black;stroke-width:1\"/>");

                    outf.write(" <text x=\"" + (wmid + 260 * 1.1674) + "\" y=\"" + (h1 + 560 * 1.1674) + "\" font-family=\"Braille\" font-size=\"29.185\" fill=\"black\">\n");
                    outf.write("X" + "\n");
                    outf.write("</text>\n");

                    outf.write(" <text x=\"" + (wmid - 290 * 1.1674) + "\" y=\"" + (h1 + 560 * 1.1674) + "\" font-family=\"Braille\" font-size=\"29.185\" fill=\"black\">\n");
                    outf.write("X'" + "\n");
                    outf.write("</text>\n");

                    outf.write(" <text x=\"" + (wmid - 10 * 1.1674) + "\" y=\"" + (h1 + 290 * 1.1674) + "\" font-family=\"Braille\" font-size=\"29.185\" fill=\"black\">\n");
                    outf.write("Y" + "\n");
                    outf.write("</text>\n");

                    outf.write(" <text x=\"" + (wmid - 10 * 1.1674) + "\" y=\"" + (h1 + 830 * 1.1674) + "\" font-family=\"Braille\" font-size=\"29.185\" fill=\"black\">\n");
                    outf.write("Y'" + "\n");
                    outf.write("</text>\n");




                    if (panel.fun != null) {

                        if (panel.flinetype == 0) {

                            for (double m = -250 * 1.1674; m < 250 * 1.1674; m = m + 1) {
                                panel.vm.setValue("x", 0.04 * m);
                                double a = 25 * panel.fun.eval(panel.vm, panel.fm);
                                panel.vm.setValue("x", 0.04 * (m + 1));
                                double b = 25 * panel.fun.eval(panel.vm, panel.fm);

                                if (panel.flinethickness == 0) {

                                    if ((a <= 250 * 1.1674) && (b <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        //outf.write("    <circle cx=\"" + (wmid + m) + "\" cy=\"" + (h1 + 550 * 1.1674 - a) + "\" r=\"" + 0.001 + "\" style=\"stroke:black;stroke-width:2\"/>\n");
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:1\"/>");
                                    } else if ((a <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= 250 * 1.1674)) {
                                        //outf.write("    <circle cx=\"" + (wmid + m) + "\" cy=\"" + (h1 + 550 * 1.1674 - a) + "\" r=\"" + 0.001 + "\" style=\"stroke:black;stroke-width:2\"/>\n");
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 300 * 1.1674) + "\" style=\"stroke:black;stroke-width:1\"/>");
                                    } else if ((a <= -250 * 1.1674) && (b <= 250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        //outf.write("    <circle cx=\"" + (wmid + m) + "\" cy=\"" + (h1 + 800 * 1.1674) + "\" r=\"" + 0.001 + "\" style=\"stroke:black;stroke-width:2\"/>\n");
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 800 * 1.1674) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:1\"/>");
                                    }

                                }

                                if (panel.flinethickness == 1) {

                                    if ((a <= 250 * 1.1674) && (b <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:2\"/>");
                                    } else if ((a <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= 250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 300 * 1.1674) + "\" style=\"stroke:black;stroke-width:2\"/>");
                                    } else if ((a <= -250 * 1.1674) && (b <= 250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 800 * 1.1674) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:2\"/>");
                                    }

                                }

                                if (panel.flinethickness == 2) {

                                    if ((a <= 250 * 1.1674) && (b <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:3\"/>");
                                    } else if ((a <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= 250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 300 * 1.1674) + "\" style=\"stroke:black;stroke-width:3\"/>");
                                    } else if ((a <= -250 * 1.1674) && (b <= 250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 800 * 1.1674) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:3\"/>");
                                    }

                                }
                            }
                        }

                        if (panel.flinetype == 1) {

                            for (double m = -250 * 1.1674; m < 250 * 1.1674; m = m + 4) {
                                panel.vm.setValue("x", 0.04 * m);
                                double a = 25 * panel.fun.eval(panel.vm, panel.fm);
                                panel.vm.setValue("x", 0.04 * (m + 1));
                                double b = 25 * panel.fun.eval(panel.vm, panel.fm);

                                if (panel.flinethickness == 0) {

                                    if ((a <= 250 * 1.1674) && (b <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:3\"/>");
                                    } else if ((a <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= 250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 300 * 1.1674) + "\" style=\"stroke:black;stroke-width:3\"/>");
                                    } else if ((a <= -250 * 1.1674) && (b <= 250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 800 * 1.1674) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:3\"/>");
                                    }

                                }

                                if (panel.flinethickness == 1) {

                                    if ((a <= 250 * 1.1674) && (b <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:4\"/>");
                                    } else if ((a <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= 250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 300 * 1.1674) + "\" style=\"stroke:black;stroke-width:4\"/>");
                                    } else if ((a <= -250 * 1.1674) && (b <= 250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 800 * 1.1674) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:4\"/>");
                                    }

                                }

                                if (panel.flinethickness == 2) {

                                    if ((a <= 250 * 1.1674) && (b <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:5\"/>");
                                    } else if ((a <= 250 * 1.1674) && (a >= -250 * 1.1674) && (b >= 250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 550 * 1.1674 - a) + " " + (wmid + m + 1) + " " + (h1 + 300 * 1.1674) + "\" style=\"stroke:black;stroke-width:5\"/>");
                                    } else if ((a <= -250 * 1.1674) && (b <= 250 * 1.1674) && (b >= -250 * 1.1674)) {
                                        outf.write("    <polyline points=\"" + (wmid + m) + " " + (h1 + 800 * 1.1674) + " " + (wmid + m + 1) + " " + (h1 + 550 * 1.1674 - b) + "\" style=\"stroke:black;stroke-width:5\"/>");
                                    }

                                }
                            }
                        }
                    }
                }

                // </editor-fold>

                outf.write("</svg>\n");

                outf.flush();
                outf.close();
                panel.f = file;
            } catch (IOException ex) {
                Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_SaveAsActionPerformed

    private void chemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chemActionPerformed
        panel.removeAll();
        panel.updateUI();
        panel.zero();
        r1.setSelectedIndex(0);
        r2.setSelectedIndex(0);
        r3.setSelectedIndex(0);
        r4.setSelectedIndex(0);
        p1.setSelectedIndex(0);
        p2.setSelectedIndex(0);
        p3.setSelectedIndex(0);
        p4.setSelectedIndex(0);
        txtbx.setText("");
        funbx.setText("");
        panel.value = 2;
        this.setTitle("MrinalMTP");
        panel.repaint();
        chemchoice();
    }//GEN-LAST:event_chemActionPerformed

    private void functionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_functionActionPerformed
        panel.removeAll();
        panel.updateUI();
        panel.zero();
        r1.setSelectedIndex(0);
        r2.setSelectedIndex(0);
        r3.setSelectedIndex(0);
        r4.setSelectedIndex(0);
        p1.setSelectedIndex(0);
        p2.setSelectedIndex(0);
        p3.setSelectedIndex(0);
        p4.setSelectedIndex(0);
        txtbx.setText("");
        funbx.setText("");
        panel.value = 3;
        panel.repaint();
        funchoice();
    }//GEN-LAST:event_functionActionPerformed

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Converter Actions Performed">
    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            panel.converted.clear();
            panel.setFile(file, file.getName());
            this.setTitle(file.getName() + " - MrinalMTP");
            panel.repaint();
        } else {
            System.out.println("File access cancelled by user.");
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Option Actions Performed">
    private void box1ActionPerformed(java.awt.event.ActionEvent evt) {
        int pop = box1.getSelectedIndex();
        int rop = (pop + 1) * 2;
        panel.repaint();

    }

    private void box2ActionPerformed(java.awt.event.ActionEvent evt) {
        int pop = box2.getSelectedIndex();
        panel.repaint();

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Chemical Actions Performed">
    private void p1ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) p1.getSelectedItem();
        if (pop.equals("")) {
            if (panel.p1nam.equals("")) {
            } else {
                panel.nop = panel.nop - 1;
            }
        } else {
            if (panel.p1nam.equals("")) {
                panel.nop = panel.nop + 1;
            }
        }
        panel.p1nam = pop;
        panel.repaint();
        ppanel1.chemical = pop;
        ppanel1.repaint();

    }

    private void p2ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) p2.getSelectedItem();
        if (pop.equals("")) {
            if (panel.p2nam.equals("")) {
            } else {
                panel.nop = panel.nop - 1;
            }
        } else {
            if (panel.p2nam.equals("")) {
                panel.nop = panel.nop + 1;
            }
        }
        panel.p2nam = pop;
        panel.repaint();
        ppanel2.chemical = pop;
        ppanel2.repaint();
    }

    private void p3ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) p3.getSelectedItem();
        if (pop.equals("")) {
            if (panel.p3nam.equals("")) {
            } else {
                panel.nop = panel.nop - 1;
            }
        } else {
            if (panel.p3nam.equals("")) {
                panel.nop = panel.nop + 1;
            }
        }
        panel.p3nam = pop;
        panel.repaint();
        ppanel3.chemical = pop;
        ppanel3.repaint();
    }

    private void p4ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) p4.getSelectedItem();
        if (pop.equals("")) {
            if (panel.p4nam.equals("")) {
            } else {
                panel.nop = panel.nop - 1;
            }
        } else {
            if (panel.p4nam.equals("")) {
                panel.nop = panel.nop + 1;
            }
        }
        panel.p4nam = pop;
        panel.repaint();
        ppanel4.chemical = pop;
        ppanel4.repaint();
    }

    private void r1ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) r1.getSelectedItem();
        if (pop.equals("")) {
            if (panel.r1nam.equals("")) {
            } else {
                panel.nor = panel.nor - 1;
            }
        } else {
            if (panel.r1nam.equals("")) {
                panel.nor = panel.nor + 1;
            }
        }
        panel.r1nam = pop;
        panel.repaint();
        rpanel1.chemical = pop;
        rpanel1.repaint();
    }

    private void r2ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) r2.getSelectedItem();
        if (pop.equals("")) {
            if (panel.r2nam.equals("")) {
            } else {
                panel.nor = panel.nor - 1;
            }
        } else {
            if (panel.r2nam.equals("")) {
                panel.nor = panel.nor + 1;
            }
        }
        panel.r2nam = pop;
        panel.repaint();
        rpanel2.chemical = pop;
        rpanel2.repaint();
    }

    private void r3ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) r3.getSelectedItem();
        if (pop.equals("")) {
            if (panel.r3nam.equals("")) {
            } else {
                panel.nor = panel.nor - 1;
            }
        } else {
            if (panel.r3nam.equals("")) {
                panel.nor = panel.nor + 1;
            }
        }
        panel.r3nam = pop;
        panel.repaint();
        rpanel3.chemical = pop;
        rpanel3.repaint();
    }

    private void r4ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) r4.getSelectedItem();
        if (pop.equals("")) {
            if (panel.r4nam.equals("")) {
            } else {
                panel.nor = panel.nor - 1;
            }
        } else {
            if (panel.r4nam.equals("")) {
                panel.nor = panel.nor + 1;
            }
        }
        panel.r4nam = pop;
        panel.repaint();
        rpanel4.chemical = pop;
        rpanel4.repaint();
    }

    private void c1ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) c1.getSelectedItem();
        if (pop.equals("")) {
            if (panel.c1nam.equals("")) {
            } else {
                panel.noc = panel.noc - 1;
            }
        } else {
            if (panel.c1nam.equals("")) {
                panel.noc = panel.noc + 1;
            }
        }
        panel.c1nam = pop;
        panel.repaint();
        cpanel1.chemical = pop;
        cpanel1.repaint();
    }

    private void c2ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) c2.getSelectedItem();
        if (pop.equals("")) {
            if (panel.c2nam.equals("")) {
            } else {
                panel.noc = panel.noc - 1;
            }
        } else {
            if (panel.c2nam.equals("")) {
                panel.noc = panel.noc + 1;
            }
        }
        panel.c2nam = pop;
        panel.repaint();
        cpanel2.chemical = pop;
        cpanel2.repaint();
    }

    private void c3ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) c3.getSelectedItem();
        if (pop.equals("")) {
            if (panel.c3nam.equals("")) {
            } else {
                panel.noc = panel.noc - 1;
            }
        } else {
            if (panel.c3nam.equals("")) {
                panel.noc = panel.noc + 1;
            }
        }
        panel.c3nam = pop;
        panel.repaint();
        cpanel3.chemical = pop;
        cpanel3.repaint();
    }

    private void c4ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) c4.getSelectedItem();
        if (pop.equals("")) {
            if (panel.c4nam.equals("")) {
            } else {
                panel.noc = panel.noc - 1;
            }
        } else {
            if (panel.c4nam.equals("")) {
                panel.noc = panel.noc + 1;
            }
        }
        panel.c4nam = pop;
        panel.repaint();
        cpanel4.chemical = pop;
        cpanel4.repaint();
    }

    private void doneActionPerformed(java.awt.event.ActionEvent evt) {
        String pres = pressurebx.getText();
        String pres2 = tempbx.getText();

        panel.pressure = pres;
        panel.temp = pres2;
        panel.repaint();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Language Actions Performed">
    private void TextActionPerformed(java.awt.event.ActionEvent evt) {
        String a = txtbx.getText();
        panel.txt = a;
        panel.repaint();

    }

    private void englishActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void brailleActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void asciiActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Calculator Actions Performed">
    private void FunActionPerformed(java.awt.event.ActionEvent evt) {

        String a = funbx.getText();
        panel.fm.loadDefaultFunctions();
        Expression fun = ExpressionTree.parse(a);
        panel.fun = fun;
        this.setTitle("MrinalMTP");
        panel.repaint();


    }

    private void funbackActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funcommaActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funsignActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funleftActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fun7ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fun8ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fundivActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funrightActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fun9ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fun4ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fun5ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fun6ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funmultActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fun1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fun0ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fun3ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fun2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funsubActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void fundotActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funaddActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funmaxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funminActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funavgActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funsumActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funintActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funflrActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funpowActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funsqrtActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funabsActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funrecpActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funceilActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funexpActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funlnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funlogActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funfactActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funasinhActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funtanhActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funatanActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funatanhActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funacoshActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funacosActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funcoshActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funsinhActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funasinActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funsinActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funcosActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funtanActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void funmediumActionPerformed(java.awt.event.ActionEvent evt) {
        panel.flinethickness = 1;
        panel.repaint();
    }

    private void funthinActionPerformed(java.awt.event.ActionEvent evt) {
        panel.flinethickness = 0;
        panel.repaint();
    }

    private void funthickActionPerformed(java.awt.event.ActionEvent evt) {
        panel.flinethickness = 2;
        panel.repaint();
    }

    private void funnormalActionPerformed(java.awt.event.ActionEvent evt) {
        panel.flinetype = 0;
        panel.repaint();
    }

    private void fundashedActionPerformed(java.awt.event.ActionEvent evt) {
        panel.flinetype = 1;
        panel.repaint();
    }

    // </editor-fold>
    // </editor-fold>
    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainClass().setVisible(true);
            }
        });
    }
    // <editor-fold defaultstate="collapsed" desc="Variable Declarations">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Grp;
    private javax.swing.ButtonGroup Grp2;
    private javax.swing.JMenuItem Save;
    private javax.swing.JMenuItem SaveAs;
    private javax.swing.JToggleButton chem;
    private javax.swing.JToggleButton diag;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JFileChooser fileChooser2;
    private javax.swing.JToggleButton function;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar menu;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
    //Custom variables declaration
    IconPanel panel = new IconPanel();
    JPanel options, chemequ, textenter, diagconv, functions;
    MyCustomFilter abc = new MyCustomFilter();
    JComboBox box1, box2, thickness;
    JLabel pagesize, orientation;
    JButton txtbutton, open, fungo;
    JTextField txtbx, funbx;
    JScrollBar bar;
    JToggleButton english, braille, ascii;
    ButtonGroup mode;
    // <editor-fold defaultstate="collapsed" desc="Calculator Variable Declarations">
    private javax.swing.JButton fun0;
    private javax.swing.JButton fun1;
    private javax.swing.JButton fun2;
    private javax.swing.JButton fun3;
    private javax.swing.JButton fun4;
    private javax.swing.JButton fun5;
    private javax.swing.JButton fun6;
    private javax.swing.JButton fun7;
    private javax.swing.JButton fun8;
    private javax.swing.JButton fun9;
    private javax.swing.JButton funabs;
    private javax.swing.JButton funacos;
    private javax.swing.JButton funacosh;
    private javax.swing.JButton funadd;
    private javax.swing.JButton funasin;
    private javax.swing.JButton funasinh;
    private javax.swing.JButton funatan;
    private javax.swing.JButton funatanh;
    private javax.swing.JButton funavg;
    private javax.swing.JButton funback;
    private javax.swing.JButton funceil;
    private javax.swing.JButton funcomma;
    private javax.swing.JButton funcos;
    private javax.swing.JButton funcosh;
    private javax.swing.JButton fundiv;
    private javax.swing.JButton fundot;
    private javax.swing.JButton funexp;
    private javax.swing.JButton funfact;
    private javax.swing.JButton funflr;
    private javax.swing.JButton funint;
    private javax.swing.JButton funleft;
    private javax.swing.JButton funln;
    private javax.swing.JButton funlog;
    private javax.swing.JButton funmax;
    private javax.swing.JButton funmin;
    private javax.swing.JButton funmult;
    private javax.swing.JButton funpow;
    private javax.swing.JButton funrecp;
    private javax.swing.JButton funright;
    private javax.swing.JButton funsign;
    private javax.swing.JButton funsin;
    private javax.swing.JButton funsinh;
    private javax.swing.JButton funsqrt;
    private javax.swing.JButton funsub;
    private javax.swing.JButton funsum;
    private javax.swing.JButton funtan;
    private javax.swing.JButton funtanh;
    private javax.swing.JButton funx;
    private javax.swing.JLabel funthickness;
    private javax.swing.JRadioButton funthin;
    private javax.swing.JRadioButton funmedium;
    private javax.swing.JRadioButton funthick;
    private javax.swing.JLabel funlinetype;
    private javax.swing.JRadioButton funnormal;
    private javax.swing.JRadioButton fundashed;
    private javax.swing.JPanel input;
    private javax.swing.JPanel ari;
    private javax.swing.JPanel trig;
    private javax.swing.JPanel funsettings;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Chemical Variable Declarations">
    JPanel reactantpanel, productpanel, catalystpanel, conditionpanel;
    IconPanel rpanel1, rpanel2, rpanel3, rpanel4, ppanel1, ppanel2, ppanel3, ppanel4, cpanel1, cpanel2, cpanel3, cpanel4;
    JComboBox r1, r2, r3, r4, p1, p2, p3, p4, c1, c2, c3, c4;
    JLabel temptext, pressuretxt;
    JTextField tempbx, pressurebx;
    JButton donebtn;
    // </editor-fold>

    // </editor-fold>
    private void myInitComponents() {

        //----------------------------------------------------------------------------------------------   
        //Screen fitting to monitor---------------------------------------------------------------------

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        int taskBarHeight = scrnSize.height - winSize.height;
        this.setSize(width, height - taskBarHeight);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //End screen fitting------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------



        // <editor-fold defaultstate="collapsed" desc="Assignments">

        english = new javax.swing.JToggleButton();
        braille = new javax.swing.JToggleButton();
        ascii = new javax.swing.JToggleButton();
        mode = new javax.swing.ButtonGroup();
        orientation = new javax.swing.JLabel();
        pagesize = new javax.swing.JLabel();
        options = new javax.swing.JPanel();
        chemequ = new javax.swing.JPanel();
        diagconv = new javax.swing.JPanel();
        textenter = new javax.swing.JPanel();
        functions = new javax.swing.JPanel();
        box1 = new javax.swing.JComboBox();
        box2 = new javax.swing.JComboBox();
        txtbutton = new javax.swing.JButton();
        fungo = new javax.swing.JButton();
        open = new javax.swing.JButton();
        txtbx = new javax.swing.JTextField();
        funbx = new javax.swing.JTextField();
        bar = new javax.swing.JScrollBar(JScrollBar.VERTICAL);
        thickness = new javax.swing.JComboBox();

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Calculator Assignments">

        input = new javax.swing.JPanel();

        funback = new javax.swing.JButton();
        funx = new javax.swing.JButton();
        funcomma = new javax.swing.JButton();
        funsign = new javax.swing.JButton();
        funleft = new javax.swing.JButton();
        fun7 = new javax.swing.JButton();
        fun8 = new javax.swing.JButton();
        fundiv = new javax.swing.JButton();
        funright = new javax.swing.JButton();
        fun9 = new javax.swing.JButton();
        fun4 = new javax.swing.JButton();
        fun5 = new javax.swing.JButton();
        fun6 = new javax.swing.JButton();
        funmult = new javax.swing.JButton();
        fun1 = new javax.swing.JButton();
        fun0 = new javax.swing.JButton();
        fun3 = new javax.swing.JButton();
        fun2 = new javax.swing.JButton();
        funsub = new javax.swing.JButton();
        fundot = new javax.swing.JButton();
        funadd = new javax.swing.JButton();

        ari = new javax.swing.JPanel();

        funmax = new javax.swing.JButton();
        funmin = new javax.swing.JButton();
        funavg = new javax.swing.JButton();
        funsum = new javax.swing.JButton();
        funint = new javax.swing.JButton();
        funflr = new javax.swing.JButton();
        funpow = new javax.swing.JButton();
        funsqrt = new javax.swing.JButton();
        funabs = new javax.swing.JButton();
        funrecp = new javax.swing.JButton();
        funceil = new javax.swing.JButton();
        funexp = new javax.swing.JButton();
        funln = new javax.swing.JButton();
        funlog = new javax.swing.JButton();
        funfact = new javax.swing.JButton();

        trig = new javax.swing.JPanel();

        funcosh = new javax.swing.JButton();
        funatan = new javax.swing.JButton();
        funsinh = new javax.swing.JButton();
        funacosh = new javax.swing.JButton();
        funtanh = new javax.swing.JButton();
        funasinh = new javax.swing.JButton();
        funacos = new javax.swing.JButton();
        funatanh = new javax.swing.JButton();
        funasin = new javax.swing.JButton();
        funsin = new javax.swing.JButton();
        funcos = new javax.swing.JButton();
        funtan = new javax.swing.JButton();

        funsettings = new javax.swing.JPanel();

        funthickness = new javax.swing.JLabel();
        funthin = new javax.swing.JRadioButton();
        funmedium = new javax.swing.JRadioButton();
        funthick = new javax.swing.JRadioButton();
        funlinetype = new javax.swing.JLabel();
        funnormal = new javax.swing.JRadioButton();
        fundashed = new javax.swing.JRadioButton();


        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Chemical Assignments">


        reactantpanel = new javax.swing.JPanel();
        rpanel1 = new IconPanel();
        r1 = new javax.swing.JComboBox();
        r2 = new javax.swing.JComboBox();
        rpanel2 = new IconPanel();
        r3 = new javax.swing.JComboBox();
        rpanel3 = new IconPanel();
        rpanel4 = new IconPanel();
        r4 = new javax.swing.JComboBox();
        conditionpanel = new javax.swing.JPanel();
        temptext = new javax.swing.JLabel();
        tempbx = new javax.swing.JTextField();
        pressuretxt = new javax.swing.JLabel();
        pressurebx = new javax.swing.JTextField();
        donebtn = new javax.swing.JButton();
        productpanel = new javax.swing.JPanel();
        ppanel1 = new IconPanel();
        p1 = new javax.swing.JComboBox();
        p2 = new javax.swing.JComboBox();
        ppanel2 = new IconPanel();
        p3 = new javax.swing.JComboBox();
        ppanel3 = new IconPanel();
        ppanel4 = new IconPanel();
        p4 = new javax.swing.JComboBox();
        catalystpanel = new javax.swing.JPanel();
        cpanel1 = new IconPanel();
        c1 = new javax.swing.JComboBox();
        c2 = new javax.swing.JComboBox();
        cpanel2 = new IconPanel();
        c3 = new javax.swing.JComboBox();
        cpanel3 = new IconPanel();
        cpanel4 = new IconPanel();
        c4 = new javax.swing.JComboBox();

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Panel backgrounds">

        panel.setBackground(new java.awt.Color(255, 255, 255));
        options.setBackground(new java.awt.Color(240, 240, 240));
        chemequ.setBackground(new java.awt.Color(240, 240, 240));
        diagconv.setBackground(new java.awt.Color(240, 240, 240));
        functions.setBackground(new java.awt.Color(240, 240, 240));
        textenter.setBackground(new java.awt.Color(240, 240, 240));
        input.setBackground(new java.awt.Color(240, 240, 240));
        trig.setBackground(new java.awt.Color(240, 240, 240));
        ari.setBackground(new java.awt.Color(240, 240, 240));
        funsettings.setBackground(new java.awt.Color(240, 240, 240));
        reactantpanel.setBackground(new java.awt.Color(240, 240, 240));
        productpanel.setBackground(new java.awt.Color(240, 240, 240));
        catalystpanel.setBackground(new java.awt.Color(240, 240, 240));
        conditionpanel.setBackground(new java.awt.Color(240, 240, 240));
        rpanel1.setBackground(new java.awt.Color(240, 240, 240));
        rpanel2.setBackground(new java.awt.Color(240, 240, 240));
        rpanel3.setBackground(new java.awt.Color(240, 240, 240));
        rpanel4.setBackground(new java.awt.Color(240, 240, 240));
        ppanel1.setBackground(new java.awt.Color(240, 240, 240));
        ppanel2.setBackground(new java.awt.Color(240, 240, 240));
        ppanel3.setBackground(new java.awt.Color(240, 240, 240));
        ppanel4.setBackground(new java.awt.Color(240, 240, 240));
        cpanel1.setBackground(new java.awt.Color(240, 240, 240));
        cpanel2.setBackground(new java.awt.Color(240, 240, 240));
        cpanel3.setBackground(new java.awt.Color(240, 240, 240));
        cpanel4.setBackground(new java.awt.Color(240, 240, 240));

        rpanel1.value = 4;
        rpanel2.value = 4;
        rpanel3.value = 4;
        rpanel4.value = 4;
        ppanel1.value = 4;
        ppanel2.value = 4;
        ppanel3.value = 4;
        ppanel4.value = 4;
        cpanel1.value = 4;
        cpanel2.value = 4;
        cpanel3.value = 4;
        cpanel4.value = 4;


        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Image panel">

        javax.swing.GroupLayout playout = new javax.swing.GroupLayout(panel);
        panel.setLayout(playout);
        playout.setHorizontalGroup(
                playout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, (3 * width / 5) - 37, Short.MAX_VALUE));
        playout.setVerticalGroup(
                playout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, (height - taskBarHeight) - 46 - 82 - 12, Short.MAX_VALUE));

        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Options panel">

        options.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        mode.add(english);
        english.setText("a");
        english.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                englishActionPerformed(evt);
            }
        });

        mode.add(braille);
        braille.setText("b");
        braille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brailleActionPerformed(evt);
            }
        });

        mode.add(ascii);
        ascii.setText("c");
        ascii.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asciiActionPerformed(evt);
            }
        });

        box1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"A2", "A4", "A6"}));
        box1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                box1ActionPerformed(evt);
            }
        });

        box1.setSelectedIndex(2);

        box2.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Portrait", "Landscape"}));
        box2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                box2ActionPerformed(evt);
            }
        });

        pagesize.setText("Page Size");
        orientation.setText("Orientation");

        javax.swing.GroupLayout olayout = new javax.swing.GroupLayout(options);
        options.setLayout(olayout);
        olayout.setHorizontalGroup(
                olayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, olayout.createSequentialGroup()
                .addContainerGap((2 * width / 5) - 148, Short.MAX_VALUE)
                .addGroup(olayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(olayout.createSequentialGroup()
                .addComponent(orientation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(box2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(olayout.createSequentialGroup()
                .addComponent(pagesize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(box1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)));
        olayout.setVerticalGroup(
                olayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(olayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(olayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(pagesize)
                .addComponent(box1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(olayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(orientation)
                .addComponent(box2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)));

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Equations panel">

        chemequ.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        String[] chemicals = new String[]{"", "Phenol", "Benzene", "Benzoic Acid", "Bromine", "Bromobenzene", "Aniline", "O-Bromoaniline", "M-Bromoaniline", "P-Bromoaniline"};
        Arrays.sort(chemicals);

        p1.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        p2.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        p3.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        p4.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        r1.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        r2.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        r3.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        r4.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        c1.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        c2.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        c3.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        c4.setModel(new javax.swing.DefaultComboBoxModel(chemicals));

        p1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p1ActionPerformed(evt);
            }
        });
        p2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p2ActionPerformed(evt);
            }
        });
        p3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p3ActionPerformed(evt);
            }
        });
        p4.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p4ActionPerformed(evt);
            }
        });
        r1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r1ActionPerformed(evt);
            }
        });
        r2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r2ActionPerformed(evt);
            }
        });
        r3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r3ActionPerformed(evt);
            }
        });
        r4.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r4ActionPerformed(evt);
            }
        });
        c1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c1ActionPerformed(evt);
            }
        });
        c2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c2ActionPerformed(evt);
            }
        });
        c3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c3ActionPerformed(evt);
            }
        });
        c4.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c4ActionPerformed(evt);
            }
        });

        // <editor-fold defaultstate="collapsed" desc="Reactants panel">

        reactantpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Reactants"));

        rpanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rpanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));


        javax.swing.GroupLayout rpanel1Layout = new javax.swing.GroupLayout(rpanel1);
        rpanel1.setLayout(rpanel1Layout);
        rpanel1Layout.setHorizontalGroup(
                rpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        rpanel1Layout.setVerticalGroup(
                rpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        javax.swing.GroupLayout rpanel2Layout = new javax.swing.GroupLayout(rpanel2);
        rpanel2.setLayout(rpanel2Layout);
        rpanel2Layout.setHorizontalGroup(
                rpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        rpanel2Layout.setVerticalGroup(
                rpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        rpanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout rpanel3Layout = new javax.swing.GroupLayout(rpanel3);
        rpanel3.setLayout(rpanel3Layout);
        rpanel3Layout.setHorizontalGroup(
                rpanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        rpanel3Layout.setVerticalGroup(
                rpanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        rpanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout rpanel4Layout = new javax.swing.GroupLayout(rpanel4);
        rpanel4.setLayout(rpanel4Layout);
        rpanel4Layout.setHorizontalGroup(
                rpanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        rpanel4Layout.setVerticalGroup(
                rpanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        javax.swing.GroupLayout reactantpanelLayout = new javax.swing.GroupLayout(reactantpanel);
        reactantpanel.setLayout(reactantpanelLayout);
        reactantpanelLayout.setHorizontalGroup(
                reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reactantpanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(rpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(r1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(rpanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(r2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(rpanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(r3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(rpanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(r4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85)));
        reactantpanelLayout.setVerticalGroup(
                reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reactantpanelLayout.createSequentialGroup()
                .addComponent(rpanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(r4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reactantpanelLayout.createSequentialGroup()
                .addComponent(rpanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(r3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reactantpanelLayout.createSequentialGroup()
                .addComponent(rpanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(r2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reactantpanelLayout.createSequentialGroup()
                .addComponent(rpanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(r1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Products panel">
        productpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Products"));

        ppanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ppanel1Layout = new javax.swing.GroupLayout(ppanel1);
        ppanel1.setLayout(ppanel1Layout);
        ppanel1Layout.setHorizontalGroup(
                ppanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        ppanel1Layout.setVerticalGroup(
                ppanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        ppanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ppanel2Layout = new javax.swing.GroupLayout(ppanel2);
        ppanel2.setLayout(ppanel2Layout);
        ppanel2Layout.setHorizontalGroup(
                ppanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        ppanel2Layout.setVerticalGroup(
                ppanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        ppanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ppanel3Layout = new javax.swing.GroupLayout(ppanel3);
        ppanel3.setLayout(ppanel3Layout);
        ppanel3Layout.setHorizontalGroup(
                ppanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        ppanel3Layout.setVerticalGroup(
                ppanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        ppanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ppanel4Layout = new javax.swing.GroupLayout(ppanel4);
        ppanel4.setLayout(ppanel4Layout);
        ppanel4Layout.setHorizontalGroup(
                ppanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        ppanel4Layout.setVerticalGroup(
                ppanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        javax.swing.GroupLayout productpanelLayout = new javax.swing.GroupLayout(productpanel);
        productpanel.setLayout(productpanelLayout);
        productpanelLayout.setHorizontalGroup(
                productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productpanelLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(ppanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(ppanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(ppanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(p3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(ppanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(p4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85)));
        productpanelLayout.setVerticalGroup(
                productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productpanelLayout.createSequentialGroup()
                .addComponent(ppanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productpanelLayout.createSequentialGroup()
                .addComponent(ppanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productpanelLayout.createSequentialGroup()
                .addComponent(ppanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productpanelLayout.createSequentialGroup()
                .addComponent(ppanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Catalyst panel">
        catalystpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Catalysts"));

        cpanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout cpanel1Layout = new javax.swing.GroupLayout(cpanel1);
        cpanel1.setLayout(cpanel1Layout);
        cpanel1Layout.setHorizontalGroup(
                cpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        cpanel1Layout.setVerticalGroup(
                cpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));
        c1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c1ActionPerformed(evt);
            }
        });

        cpanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout cpanel2Layout = new javax.swing.GroupLayout(cpanel2);
        cpanel2.setLayout(cpanel2Layout);
        cpanel2Layout.setHorizontalGroup(
                cpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        cpanel2Layout.setVerticalGroup(
                cpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        cpanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout cpanel3Layout = new javax.swing.GroupLayout(cpanel3);
        cpanel3.setLayout(cpanel3Layout);
        cpanel3Layout.setHorizontalGroup(
                cpanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        cpanel3Layout.setVerticalGroup(
                cpanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        cpanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout cpanel4Layout = new javax.swing.GroupLayout(cpanel4);
        cpanel4.setLayout(cpanel4Layout);
        cpanel4Layout.setHorizontalGroup(
                cpanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        cpanel4Layout.setVerticalGroup(
                cpanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        javax.swing.GroupLayout catalystpanelLayout = new javax.swing.GroupLayout(catalystpanel);
        catalystpanel.setLayout(catalystpanelLayout);
        catalystpanelLayout.setHorizontalGroup(
                catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catalystpanelLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(cpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(c1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(cpanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(c2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(cpanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(c3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(cpanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(c4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85)));
        catalystpanelLayout.setVerticalGroup(
                catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catalystpanelLayout.createSequentialGroup()
                .addComponent(cpanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catalystpanelLayout.createSequentialGroup()
                .addComponent(cpanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catalystpanelLayout.createSequentialGroup()
                .addComponent(cpanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catalystpanelLayout.createSequentialGroup()
                .addComponent(cpanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Conditions panel">
        conditionpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Conditions"));

        temptext.setText("Temperature");

        pressuretxt.setText("Pressure");

        donebtn.setText("Done");

        donebtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout conditionpanelLayout = new javax.swing.GroupLayout(conditionpanel);
        conditionpanel.setLayout(conditionpanelLayout);
        conditionpanelLayout.setHorizontalGroup(
                conditionpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(conditionpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(temptext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tempbx, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pressuretxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pressurebx, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(donebtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        conditionpanelLayout.setVerticalGroup(
                conditionpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(conditionpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(conditionpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(temptext)
                .addComponent(tempbx, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(pressuretxt)
                .addComponent(pressurebx, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(donebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        // </editor-fold>     

        javax.swing.GroupLayout cLayout = new javax.swing.GroupLayout(chemequ);
        chemequ.setLayout(cLayout);
        cLayout.setHorizontalGroup(
                cLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(conditionpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(reactantpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(productpanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(catalystpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap()));
        cLayout.setVerticalGroup(
                cLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(reactantpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(catalystpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conditionpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Diagram Conversion panel">

        diagconv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        thickness.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"", "Thin", "Medium", "Thick"}));

        open.setText("Open");
        open.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dLayout = new javax.swing.GroupLayout(diagconv);
        diagconv.setLayout(dLayout);
        dLayout.setHorizontalGroup(
                dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(open)
                .addComponent(thickness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        dLayout.setVerticalGroup(
                dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(open)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(thickness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE)));

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Functions panel">

        functions.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Font bigFont = funbx.getFont().deriveFont(Font.PLAIN, 20f);
        funbx.setFont(bigFont);

        input.setBorder(javax.swing.BorderFactory.createTitledBorder("Input"));

        // <editor-fold defaultstate="collapsed" desc="Input panel buttons">

        funback.setText("←");
        funback.setPreferredSize(new java.awt.Dimension(45, 23));
        funback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funbackActionPerformed(evt);
            }
        });

        funx.setText("x");
        funx.setMaximumSize(new java.awt.Dimension(45, 23));
        funx.setMinimumSize(new java.awt.Dimension(45, 23));
        funx.setPreferredSize(new java.awt.Dimension(45, 23));
        funx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funxActionPerformed(evt);
            }
        });

        funcomma.setText(",");
        funcomma.setMaximumSize(new java.awt.Dimension(45, 23));
        funcomma.setMinimumSize(new java.awt.Dimension(45, 23));
        funcomma.setPreferredSize(new java.awt.Dimension(45, 23));
        funcomma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funcommaActionPerformed(evt);
            }
        });

        funsign.setText("±");
        funsign.setMaximumSize(new java.awt.Dimension(45, 23));
        funsign.setMinimumSize(new java.awt.Dimension(45, 23));
        funsign.setPreferredSize(new java.awt.Dimension(45, 23));
        funsign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funsignActionPerformed(evt);
            }
        });

        funleft.setText("(");
        funleft.setMaximumSize(new java.awt.Dimension(45, 23));
        funleft.setMinimumSize(new java.awt.Dimension(45, 23));
        funleft.setPreferredSize(new java.awt.Dimension(45, 23));
        funleft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funleftActionPerformed(evt);
            }
        });

        fun7.setText("7");
        fun7.setMaximumSize(new java.awt.Dimension(45, 23));
        fun7.setMinimumSize(new java.awt.Dimension(45, 23));
        fun7.setPreferredSize(new java.awt.Dimension(45, 23));
        fun7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fun7ActionPerformed(evt);
            }
        });

        fun8.setText("8");
        fun8.setMaximumSize(new java.awt.Dimension(45, 23));
        fun8.setMinimumSize(new java.awt.Dimension(45, 23));
        fun8.setPreferredSize(new java.awt.Dimension(45, 23));
        fun8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fun8ActionPerformed(evt);
            }
        });

        fundiv.setText("/");
        fundiv.setMaximumSize(new java.awt.Dimension(45, 23));
        fundiv.setMinimumSize(new java.awt.Dimension(45, 23));
        fundiv.setPreferredSize(new java.awt.Dimension(45, 23));
        fundiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fundivActionPerformed(evt);
            }
        });

        funright.setText(")");
        funright.setMaximumSize(new java.awt.Dimension(45, 23));
        funright.setMinimumSize(new java.awt.Dimension(45, 23));
        funright.setPreferredSize(new java.awt.Dimension(45, 23));
        funright.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funrightActionPerformed(evt);
            }
        });

        fun9.setText("9");
        fun9.setMaximumSize(new java.awt.Dimension(45, 23));
        fun9.setMinimumSize(new java.awt.Dimension(45, 23));
        fun9.setPreferredSize(new java.awt.Dimension(45, 23));
        fun9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fun9ActionPerformed(evt);
            }
        });

        fun4.setText("4");
        fun4.setMaximumSize(new java.awt.Dimension(45, 23));
        fun4.setMinimumSize(new java.awt.Dimension(45, 23));
        fun4.setPreferredSize(new java.awt.Dimension(45, 23));
        fun4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fun4ActionPerformed(evt);
            }
        });

        fun5.setText("5");
        fun5.setMaximumSize(new java.awt.Dimension(45, 23));
        fun5.setMinimumSize(new java.awt.Dimension(45, 23));
        fun5.setPreferredSize(new java.awt.Dimension(45, 23));
        fun5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fun5ActionPerformed(evt);
            }
        });

        fun6.setText("6");
        fun6.setMaximumSize(new java.awt.Dimension(45, 23));
        fun6.setMinimumSize(new java.awt.Dimension(45, 23));
        fun6.setPreferredSize(new java.awt.Dimension(45, 23));
        fun6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fun6ActionPerformed(evt);
            }
        });

        funmult.setText("*");
        funmult.setMaximumSize(new java.awt.Dimension(45, 23));
        funmult.setMinimumSize(new java.awt.Dimension(45, 23));
        funmult.setPreferredSize(new java.awt.Dimension(45, 23));
        funmult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funmultActionPerformed(evt);
            }
        });

        fungo.setText("=");
        fungo.setMaximumSize(new java.awt.Dimension(45, 23));
        fungo.setMinimumSize(new java.awt.Dimension(45, 23));
        fungo.setPreferredSize(new java.awt.Dimension(45, 23));
        fungo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FunActionPerformed(evt);
            }
        });

        fun1.setText("1");
        fun1.setMaximumSize(new java.awt.Dimension(45, 23));
        fun1.setMinimumSize(new java.awt.Dimension(45, 23));
        fun1.setPreferredSize(new java.awt.Dimension(45, 23));
        fun1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fun1ActionPerformed(evt);
            }
        });

        fun0.setText("0");
        fun0.setMaximumSize(new java.awt.Dimension(45, 23));
        fun0.setMinimumSize(new java.awt.Dimension(45, 23));
        fun0.setPreferredSize(new java.awt.Dimension(45, 23));
        fun0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fun0ActionPerformed(evt);
            }
        });

        fun3.setText("3");
        fun3.setMaximumSize(new java.awt.Dimension(45, 23));
        fun3.setMinimumSize(new java.awt.Dimension(45, 23));
        fun3.setPreferredSize(new java.awt.Dimension(45, 23));
        fun3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fun3ActionPerformed(evt);
            }
        });

        fun2.setText("2");
        fun2.setMaximumSize(new java.awt.Dimension(45, 23));
        fun2.setMinimumSize(new java.awt.Dimension(45, 23));
        fun2.setPreferredSize(new java.awt.Dimension(45, 23));
        fun2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fun2ActionPerformed(evt);
            }
        });

        funsub.setText("-");
        funsub.setMaximumSize(new java.awt.Dimension(45, 23));
        funsub.setMinimumSize(new java.awt.Dimension(45, 23));
        funsub.setPreferredSize(new java.awt.Dimension(45, 23));
        funsub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funsubActionPerformed(evt);
            }
        });

        fundot.setText(".");
        fundot.setMaximumSize(new java.awt.Dimension(45, 23));
        fundot.setMinimumSize(new java.awt.Dimension(45, 23));
        fundot.setPreferredSize(new java.awt.Dimension(45, 23));
        fundot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fundotActionPerformed(evt);
            }
        });

        funadd.setText("+");
        funadd.setMaximumSize(new java.awt.Dimension(45, 23));
        funadd.setMinimumSize(new java.awt.Dimension(45, 23));
        funadd.setPreferredSize(new java.awt.Dimension(45, 23));
        funadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funaddActionPerformed(evt);
            }
        });

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Input panel">
        javax.swing.GroupLayout inputLayout = new javax.swing.GroupLayout(input);
        input.setLayout(inputLayout);
        inputLayout.setHorizontalGroup(
                inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(inputLayout.createSequentialGroup()
                .addComponent(funback, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funcomma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(funsign, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funleft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(inputLayout.createSequentialGroup()
                .addComponent(fun7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fun8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fun9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fundiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funright, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(inputLayout.createSequentialGroup()
                .addGroup(inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(inputLayout.createSequentialGroup()
                .addGroup(inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(inputLayout.createSequentialGroup()
                .addComponent(fun4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fun5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(inputLayout.createSequentialGroup()
                .addComponent(fun1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fun2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(inputLayout.createSequentialGroup()
                .addComponent(fun6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funmult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputLayout.createSequentialGroup()
                .addComponent(fun3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funsub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(inputLayout.createSequentialGroup()
                .addComponent(fun0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fundot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funadd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fungo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
        inputLayout.setVerticalGroup(
                inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(inputLayout.createSequentialGroup()
                .addGroup(inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(funback, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funx, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funleft, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funsign, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funcomma, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(fun7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funright, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(fundiv, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(fun8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(fun9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(inputLayout.createSequentialGroup()
                .addGroup(inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(fun4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funmult, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(fun5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(fun6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(fun1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(fun2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funsub, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(fun3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(fun0, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(fundot, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funadd, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(fungo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        //</editor-fold>

        ari.setBorder(javax.swing.BorderFactory.createTitledBorder("Arithmetic"));

        // <editor-fold defaultstate="collapsed" desc="Ari panel buttons">

        funmax.setText("max");
        funmax.setMaximumSize(new java.awt.Dimension(45, 23));
        funmax.setMinimumSize(new java.awt.Dimension(45, 23));
        funmax.setPreferredSize(new java.awt.Dimension(45, 23));
        funmax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funmaxActionPerformed(evt);
            }
        });

        funmin.setText("min");
        funmin.setMaximumSize(new java.awt.Dimension(45, 23));
        funmin.setMinimumSize(new java.awt.Dimension(45, 23));
        funmin.setPreferredSize(new java.awt.Dimension(45, 23));
        funmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funminActionPerformed(evt);
            }
        });

        funavg.setText("avg");
        funavg.setMaximumSize(new java.awt.Dimension(45, 23));
        funavg.setMinimumSize(new java.awt.Dimension(45, 23));
        funavg.setPreferredSize(new java.awt.Dimension(45, 23));
        funavg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funavgActionPerformed(evt);
            }
        });

        funsum.setText("sum");
        funsum.setMaximumSize(new java.awt.Dimension(45, 23));
        funsum.setMinimumSize(new java.awt.Dimension(45, 23));
        funsum.setPreferredSize(new java.awt.Dimension(45, 23));
        funsum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funsumActionPerformed(evt);
            }
        });

        funint.setText("int");
        funint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funintActionPerformed(evt);
            }
        });

        funflr.setText("flr");
        funflr.setPreferredSize(new java.awt.Dimension(47, 19));
        funflr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funflrActionPerformed(evt);
            }
        });

        funpow.setText("pow");
        funpow.setMaximumSize(new java.awt.Dimension(45, 23));
        funpow.setMinimumSize(new java.awt.Dimension(45, 23));
        funpow.setPreferredSize(new java.awt.Dimension(45, 23));
        funpow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funpowActionPerformed(evt);
            }
        });

        funsqrt.setText("sqrt");
        funsqrt.setMaximumSize(new java.awt.Dimension(45, 23));
        funsqrt.setMinimumSize(new java.awt.Dimension(45, 23));
        funsqrt.setPreferredSize(new java.awt.Dimension(45, 23));
        funsqrt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funsqrtActionPerformed(evt);
            }
        });

        funabs.setText("abs");
        funabs.setMaximumSize(new java.awt.Dimension(45, 23));
        funabs.setMinimumSize(new java.awt.Dimension(45, 23));
        funabs.setPreferredSize(new java.awt.Dimension(45, 23));
        funabs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funabsActionPerformed(evt);
            }
        });

        funrecp.setText("1/x");
        funrecp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funrecpActionPerformed(evt);
            }
        });

        funceil.setText("ceil");
        funceil.setPreferredSize(new java.awt.Dimension(47, 19));
        funceil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funceilActionPerformed(evt);
            }
        });

        funexp.setText("exp");
        funexp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funexpActionPerformed(evt);
            }
        });

        funln.setText("ln");
        funln.setMaximumSize(new java.awt.Dimension(45, 23));
        funln.setMinimumSize(new java.awt.Dimension(45, 23));
        funln.setPreferredSize(new java.awt.Dimension(45, 23));
        funln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funlnActionPerformed(evt);
            }
        });

        funlog.setText("log");
        funlog.setMaximumSize(new java.awt.Dimension(45, 23));
        funlog.setMinimumSize(new java.awt.Dimension(45, 23));
        funlog.setPreferredSize(new java.awt.Dimension(45, 23));
        funlog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funlogActionPerformed(evt);
            }
        });

        funfact.setText("fact");
        funfact.setMaximumSize(new java.awt.Dimension(45, 23));
        funfact.setMinimumSize(new java.awt.Dimension(45, 23));
        funfact.setPreferredSize(new java.awt.Dimension(45, 23));
        funfact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funfactActionPerformed(evt);
            }
        });

        //</editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Ari panel">
        javax.swing.GroupLayout ariLayout = new javax.swing.GroupLayout(ari);
        ari.setLayout(ariLayout);
        ariLayout.setHorizontalGroup(
                ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ariLayout.createSequentialGroup()
                .addGroup(ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(funmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(funmax, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                .addGroup(ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(funsum, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addComponent(funavg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(funint, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(funpow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(funflr, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(funabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(funsqrt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(funrecp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(funfact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(funceil, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(funln, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(funlog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(funexp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))));
        ariLayout.setVerticalGroup(
                ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ariLayout.createSequentialGroup()
                .addGroup(ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ariLayout.createSequentialGroup()
                .addComponent(funmax, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funmin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funavg, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funsum, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(funint, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funrecp, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(ariLayout.createSequentialGroup()
                .addComponent(funflr, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funpow, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funsqrt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funabs, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(ariLayout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(funexp, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(ariLayout.createSequentialGroup()
                .addComponent(funceil, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funfact, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funlog, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funln, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        // </editor-fold>

        trig.setBorder(javax.swing.BorderFactory.createTitledBorder("Trigonometric"));

        // <editor-fold defaultstate="collapsed" desc="Trig panel buttons">
        funcosh.setText("cosh");
        funcosh.setMaximumSize(new java.awt.Dimension(45, 23));
        funcosh.setMinimumSize(new java.awt.Dimension(45, 23));
        funcosh.setPreferredSize(new java.awt.Dimension(45, 23));
        funcosh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funcoshActionPerformed(evt);
            }
        });

        funatan.setText("atan");
        funatan.setMaximumSize(new java.awt.Dimension(45, 23));
        funatan.setMinimumSize(new java.awt.Dimension(45, 23));
        funatan.setPreferredSize(new java.awt.Dimension(45, 23));
        funatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funatanActionPerformed(evt);
            }
        });

        funsinh.setText("sinh");
        funsinh.setPreferredSize(new java.awt.Dimension(47, 19));
        funsinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funsinhActionPerformed(evt);
            }
        });

        funacosh.setText("acosh");
        funacosh.setMaximumSize(new java.awt.Dimension(45, 23));
        funacosh.setMinimumSize(new java.awt.Dimension(45, 23));
        funacosh.setPreferredSize(new java.awt.Dimension(45, 23));
        funacosh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funacoshActionPerformed(evt);
            }
        });

        funtanh.setText("tanh");
        funtanh.setMaximumSize(new java.awt.Dimension(45, 23));
        funtanh.setMinimumSize(new java.awt.Dimension(45, 23));
        funtanh.setPreferredSize(new java.awt.Dimension(45, 23));
        funtanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funtanhActionPerformed(evt);
            }
        });

        funasinh.setText("asinh");
        funasinh.setPreferredSize(new java.awt.Dimension(47, 19));
        funasinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funasinhActionPerformed(evt);
            }
        });

        funacos.setText("acos");
        funacos.setMaximumSize(new java.awt.Dimension(45, 23));
        funacos.setMinimumSize(new java.awt.Dimension(45, 23));
        funacos.setPreferredSize(new java.awt.Dimension(45, 23));
        funacos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funacosActionPerformed(evt);
            }
        });

        funatanh.setText("atanh");
        funatanh.setMaximumSize(new java.awt.Dimension(45, 23));
        funatanh.setMinimumSize(new java.awt.Dimension(45, 23));
        funatanh.setPreferredSize(new java.awt.Dimension(45, 23));
        funatanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funatanhActionPerformed(evt);
            }
        });

        funasin.setText("asin");
        funasin.setPreferredSize(new java.awt.Dimension(47, 19));
        funasin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funasinActionPerformed(evt);
            }
        });

        funsin.setText("sin");
        funsin.setPreferredSize(new java.awt.Dimension(47, 19));
        funsin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funsinActionPerformed(evt);
            }
        });

        funcos.setText("cos");
        funcos.setMaximumSize(new java.awt.Dimension(45, 23));
        funcos.setMinimumSize(new java.awt.Dimension(45, 23));
        funcos.setPreferredSize(new java.awt.Dimension(45, 23));
        funcos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funcosActionPerformed(evt);
            }
        });

        funtan.setText("tan");
        funtan.setMaximumSize(new java.awt.Dimension(45, 23));
        funtan.setMinimumSize(new java.awt.Dimension(45, 23));
        funtan.setPreferredSize(new java.awt.Dimension(45, 23));
        funtan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funtanActionPerformed(evt);
            }
        });

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Trig panel">
        javax.swing.GroupLayout trigLayout = new javax.swing.GroupLayout(trig);
        trig.setLayout(trigLayout);
        trigLayout.setHorizontalGroup(
                trigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(trigLayout.createSequentialGroup()
                .addGroup(trigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(funatanh, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funacosh, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funasinh, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(funacos, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funasin, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funatan, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(funcosh, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funsinh, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funtanh, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(funsin, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funcos, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funtan, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))));
        trigLayout.setVerticalGroup(
                trigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(trigLayout.createSequentialGroup()
                .addGroup(trigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(funasinh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funasin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funsinh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funsin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(funacosh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funacos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funcosh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funcos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(trigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(funatanh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funatan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funtanh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(funtan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)));

        // </editor-fold>

        funsettings.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));

        // <editor-fold defaultstate="collapsed" desc="Settings panel buttons">

        ButtonGroup group = new ButtonGroup();

        group.add(funthin);
        group.add(funmedium);
        group.add(funthick);
        funthin.setSelected(true);

        ButtonGroup group2 = new ButtonGroup();

        group2.add(funnormal);
        group2.add(fundashed);
        funnormal.setSelected(true);

        funthickness.setText("Thickness");

        funthin.setText("Thin");
        funthin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funthinActionPerformed(evt);
            }
        });

        funmedium.setText("Medium");
        funmedium.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funmediumActionPerformed(evt);
            }
        });

        funthick.setText("Thick");
        funthick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funthickActionPerformed(evt);
            }
        });

        funlinetype.setText("Line Type");

        funnormal.setText("Normal");
        funnormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funnormalActionPerformed(evt);
            }
        });

        fundashed.setText("Dashed");
        fundashed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fundashedActionPerformed(evt);
            }
        });
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Settings panel">
        javax.swing.GroupLayout funsettingsLayout = new javax.swing.GroupLayout(funsettings);
        funsettings.setLayout(funsettingsLayout);
        funsettingsLayout.setHorizontalGroup(
                funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(funsettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(funthickness)
                .addComponent(funthin)
                .addComponent(funmedium)
                .addComponent(funthick))
                .addGap(26, 26, 26)
                .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fundashed)
                .addComponent(funnormal)
                .addComponent(funlinetype))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        funsettingsLayout.setVerticalGroup(
                funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(funsettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(funthickness)
                .addComponent(funlinetype))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(funthin)
                .addComponent(funnormal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(funmedium)
                .addComponent(fundashed))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(funthick))
                .addContainerGap(277, Short.MAX_VALUE)));
        // </editor-fold>

        javax.swing.GroupLayout fLayout = new javax.swing.GroupLayout(functions);
        functions.setLayout(fLayout);
        fLayout.setHorizontalGroup(
                fLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(fLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(fLayout.createSequentialGroup()
                .addComponent(funbx)
                .addContainerGap())
                .addGroup(fLayout.createSequentialGroup()
                .addGroup(fLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(funsettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(fLayout.createSequentialGroup()
                .addComponent(trig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 5, Short.MAX_VALUE)))));
        fLayout.setVerticalGroup(
                fLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(fLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(funbx, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(ari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(trig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(funsettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap()));



        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Text enter panel">

        textenter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtbx.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        Font bigFont2 = txtbx.getFont().deriveFont(Font.PLAIN, 35f);
        txtbx.setFont(bigFont2);

        txtbutton.setText("Print");
        txtbutton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tlayout = new javax.swing.GroupLayout(textenter);
        textenter.setLayout(tlayout);

        tlayout.setHorizontalGroup(
                tlayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tlayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtbx)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtbutton)
                .addContainerGap()));
        tlayout.setVerticalGroup(
                tlayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tlayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tlayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tlayout.createSequentialGroup()
                .addComponent(txtbutton)
                .addGap(0, 80, Short.MAX_VALUE))
                .addComponent(txtbx, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, (height - taskBarHeight) - 300, Short.MAX_VALUE)));

        // </editor-fold>

    }

    public void chemchoice() {

        this.remove(diagconv);
        this.remove(functions);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chemequ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textenter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)));


        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(chemequ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(textenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)));
    }

    public void diagchoice() {

        this.remove(chemequ);
        this.remove(functions);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(diagconv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textenter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)));


        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(diagconv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(textenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)));
    }

    public void funchoice() {

        this.remove(chemequ);
        this.remove(diagconv);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(functions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textenter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)));


        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(functions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(textenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)));
    }

    public void rmaker2(Writer outf, double cord, double h1, String chemical) throws IOException {

        if ((chemical.equals("Phenol")) || (chemical.equals("Benzene")) || (chemical.equals("Benzoic Acid")) || (chemical.equals("Aniline")) || (chemical.equals("O-Bromoaniline")) || (chemical.equals("M-Bromoaniline")) || (chemical.equals("P-Bromoaniline")) || (chemical.equals("Bromobenzene"))) {

            if (chemical.equals("Phenol")) {
                outf.write(" <text x=\"" + (cord - 20.63) + "\" y=\"" + (h1 + 145) + "\" font-family=\"Braille\" font-size=\"32\" fill=\"black\">\n");
                outf.write("OH \n");
                outf.write("</text>\n");
            }

            if (chemical.equals("Bromobenzene")) {
                outf.write(" <text x=\"" + (cord - 20.63) + "\" y=\"" + (h1 + 145) + "\" font-family=\"Braille\" font-size=\"32\" fill=\"black\">\n");
                outf.write("Br \n");
                outf.write("</text>\n");
            }

            if (chemical.equals("Benzoic Acid")) {
                outf.write(" <text x=\"" + (cord - 42.63) + "\" y=\"" + (h1 + 145) + "\" font-family=\"Braille\" font-size=\"32\" fill=\"black\">\n");
                outf.write("COOH \n");
                outf.write("</text>\n");
            }

            if ((chemical.equals("Aniline")) || (chemical.equals("O-Bromoaniline")) || (chemical.equals("M-Bromoaniline")) || (chemical.equals("P-Bromoaniline"))) {

                if (chemical.equals("O-Bromoaniline")) {
                    outf.write(" <text x=\"" + (cord + 43.63) + "\" y=\"" + (h1 + 185) + "\" font-family=\"Braille\" font-size=\"32\" fill=\"black\">\n");
                    outf.write("Br \n");
                    outf.write("</text>\n");
                }

                if (chemical.equals("M-Bromoaniline")) {
                    outf.write(" <text x=\"" + (cord + 43.63) + "\" y=\"" + (h1 + 250) + "\" font-family=\"Braille\" font-size=\"32\" fill=\"black\">\n");
                    outf.write("Br \n");
                    outf.write("</text>\n");
                }

                if (chemical.equals("P-Bromoaniline")) {
                    outf.write(" <text x=\"" + (cord - 20.63) + "\" y=\"" + (h1 + 290) + "\" font-family=\"Braille\" font-size=\"32\" fill=\"black\">\n");
                    outf.write("Br \n");
                    outf.write("</text>\n");
                }


                outf.write(" <text x=\"" + (cord - 20.63) + "\" y=\"" + (h1 + 145) + "\" font-family=\"Braille\" font-size=\"32\" fill=\"black\">\n");
                outf.write("NH\n");
                outf.write("</text>\n");
                outf.write(" <text x=\"" + (cord + 20.63) + "\" y=\"" + (h1 + 155) + "\" font-family=\"Braille\" font-size=\"27\" fill=\"black\">\n");
                outf.write("2\n");
                outf.write("</text>\n");

            }

            outf.write("    <polyline points=\"" + (cord - 43.30) + " " + (h1 + 182.5) + " " + (cord) + " " + (h1 + 157.5) + " " + (cord + 43.30) + " " + (h1 + 182.5) + " " + (cord + 43.30) + " " + (h1 + 232.5) + " " + (cord) + " " + (h1 + 257.5) + " " + (cord - 43.30) + " " + (h1 + 232.5) + " " + (cord - 43.30) + " " + (h1 + 182.5) + "\" style=\"stroke:black\"/>");
            outf.write("    <circle cx=\"" + (cord) + "\" cy=\"" + (h1 + 207.5) + "\" r=\"30\" style=\"stroke:black\"/>");

        }

        if (chemical.equals("Bromine")) {


            outf.write(" <text x=\"" + (cord - 20.63) + "\" y=\"" + (h1 + 222.5) + "\" font-family=\"Braille\" font-size=\"32\" fill=\"black\">\n");
            outf.write("Br \n");
            outf.write("</text>\n");
            outf.write(" <text x=\"" + (cord + 20.63) + "\" y=\"" + (h1 + 232.5) + "\" font-family=\"Braille\" font-size=\"27\" fill=\"black\">\n");
            outf.write("2 \n");
            outf.write("</text>\n");



        }

    }
}
