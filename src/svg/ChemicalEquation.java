package svg;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class ChemicalEquation implements Printable {

    IconPanel panel;
    
    ChemicalEquation(IconPanel p) {
        panel = p;
    }

    void draw(Graphics2D g2d, int mode) {

        double h1, h2, w1, w2, wmid, wq1, wq2, we1, we2, we3, we4, ratio, newwidth;

        ratio = ((double) panel.getHeight()) / 297;
        newwidth = ratio * 210;

        h2 = panel.getHeight() - 10;
        w1 = ((panel.getWidth() - newwidth) / 2) + 10;
        w2 = ((panel.getWidth() + newwidth) / 2) - 10;
        h1 = 10;

        if (mode == 1) {
            h2 = 860;
            w1 = 0;
            w2 = 585;
            h1 = 0;
        }

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

                if (t <= 27) {
                    txt1 = txt1 + txtbuffer + ' ';
                    txtbuffer = "";
                }
                if ((t > 28) && (t <= 55)) {
                    txt2 = txt2 + txtbuffer + ' ';
                    txtbuffer = "";
                }

            } else if ((t == panel.txt.length() - 1) || (t == 27)) {
                if (t <= 27) {
                    txt1 = txt1 + txtbuffer + panel.txt.charAt(t);
                    txtbuffer = "";
                }
                if ((t > 27) && (t <= 55)) {
                    txt2 = txt2 + txtbuffer + panel.txt.charAt(t);
                }

            } else {
                txtbuffer = txtbuffer + panel.txt.charAt(t);
            }

        }

         if (mode == 0) {
            //Boundary
            g2d.draw(new Rectangle2D.Double(w1, h1, w2 - w1, h2 - h1));
        }

        //Top Right Corner Circle
        g2d.fill(new Ellipse2D.Double(w1 + 20, h1 + 20, 25, 25));

        // Text box
        g2d.setFont(new Font("Braille", Font.PLAIN, panel.fontsize));
        g2d.drawString(txt1, (float) w1 + 60, (float) h1 + 40);
        g2d.drawString(txt2, (float) w1 + 60, (float) h1 + 75);

        //Reactants
        if ((panel.nor == 2) || (panel.nor == 4)) {
            g2d.draw(new Line2D.Double(wmid, h1 + 369.5 / 1.1674, wmid, h1 + 385.5 / 1.1674));
            g2d.draw(new Line2D.Double(wmid - 8 / 1.1674, h1 + 377.5 / 1.1674, wmid + 8 / 1.1674, h1 + 377.5 / 1.1674));
        }

        if (panel.nor == 4) {
            g2d.draw(new Line2D.Double(wq1, h1 + 369.5 / 1.1674, wq1, h1 + 385.5 / 1.1674));
            g2d.draw(new Line2D.Double(wq1 - 8 / 1.1674, h1 + 377.5 / 1.1674, wq1 + 8 / 1.1674, h1 + 377.5 / 1.1674));

            g2d.draw(new Line2D.Double(wq2, h1 + 369.5 / 1.1674, wq2, h1 + 385.5 / 1.1674));
            g2d.draw(new Line2D.Double(wq2 - 8 / 1.1674, h1 + 377.5 / 1.1674, wq2 + 8 / 1.1674, h1 + 377.5 / 1.1674));
        }

        if (panel.nor == 3) {
            g2d.draw(new Line2D.Double(we2, h1 + 369.5 / 1.1674, we2, h1 + 385.5 / 1.1674));
            g2d.draw(new Line2D.Double(we2 - 8 / 1.1674, h1 + 377.5 / 1.1674, we2 + 8 / 1.1674, h1 + 377.5 / 1.1674));

            g2d.draw(new Line2D.Double(we3, h1 + 369.5 / 1.1674, we3, h1 + 385.5 / 1.1674));
            g2d.draw(new Line2D.Double(we3 - 8 / 1.1674, h1 + 377.5 / 1.1674, we3 + 8 / 1.1674, h1 + 377.5 / 1.1674));
        }

        if (panel.nor == 2) {
            rmaker(g2d, we2, h1 + 145.6227, panel.r1nam);
            rmaker(g2d, we3, h1 + 145.6227, panel.r2nam);
        }

        if (panel.nor == 4) {
            rmaker(g2d, we1, h1 + 145.6227, panel.r1nam);
            rmaker(g2d, we2, h1 + 145.6227, panel.r2nam);
            rmaker(g2d, we3, h1 + 145.6227, panel.r3nam);
            rmaker(g2d, we4, h1 + 145.6227, panel.r4nam);
        }

        if (panel.nor == 1) {
            rmaker(g2d, wmid, h1 + 145.6227, panel.r1nam);
        }

        if (panel.nor == 3) {
            rmaker(g2d, wq1, h1 + 145.6227, panel.r1nam);
            rmaker(g2d, wmid, h1 + 145.6227, panel.r2nam);
            rmaker(g2d, wq2, h1 + 145.6227, panel.r3nam);
        }

        //Arrow
        if ((panel.nop != 0) && (panel.nor != 0)) {

            double x2Points[] = {wmid, wmid, wmid + 24 / 1.1674};
            double y2Points[] = {h1 + 450 / 1.1674, h1 + 700 / 1.1674, h1 + 680 / 1.1674};
            GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x2Points.length);

            polyline.moveTo(x2Points[0], y2Points[0]);

            for (int index = 1; index < x2Points.length; index++) {
                polyline.lineTo(x2Points[index], y2Points[index]);
            };

            g2d.draw(polyline);

            g2d.draw(new Line2D.Double(wmid, h1 + 700 / 1.1674, wmid - 24 / 1.1674, h1 + 680 / 1.1674));
        }

        //Conditions
        g2d.drawString(panel.pressure, (float) (wmid - (panel.pressure.length() * 20)), (float) (h1 + 550 / 1.1674));
        g2d.drawString(panel.temp, (float) (wmid - (panel.temp.length() * 20)), (float) (h1 + 600 / 1.1674));

        //Products
        if ((panel.nop == 2) || (panel.nop == 4)) {
            g2d.draw(new Line2D.Double(wmid, h1 + 807.5 / 1.1674, wmid, h1 + 823.5 / 1.1674));
            g2d.draw(new Line2D.Double(wmid - 8 / 1.1674, h1 + 815.5 / 1.1674, wmid + 8 / 1.1674, h1 + 815.5 / 1.1674));
        }

        if (panel.nop == 4) {
            g2d.draw(new Line2D.Double(wq1, h1 + 807.5 / 1.1674, wq1, h1 + 823.5 / 1.1674));
            g2d.draw(new Line2D.Double(wq1 - 8 / 1.1674, h1 + 815.5 / 1.1674, wq1 + 8 / 1.1674, h1 + 815.5 / 1.1674));

            g2d.draw(new Line2D.Double(wq2, h1 + 807.5 / 1.1674, wq2, h1 + 823.5 / 1.1674));
            g2d.draw(new Line2D.Double(wq2 - 8 / 1.1674, h1 + 815.5 / 1.1674, wq2 + 8 / 1.1674, h1 + 815.5 / 1.1674));
        }

        if (panel.nop == 3) {
            g2d.draw(new Line2D.Double(we2, h1 + 807.5 / 1.1674, we2, h1 + 823.5 / 1.1674));
            g2d.draw(new Line2D.Double(we2 - 8 / 1.1674, h1 + 815.5 / 1.1674, we2 + 8 / 1.1674, h1 + 815.5 / 1.1674));

            g2d.draw(new Line2D.Double(we3, h1 + 807.5 / 1.1674, we3, h1 + 823.5 / 1.1674));
            g2d.draw(new Line2D.Double(we3 - 8 / 1.1674, h1 + 815.5 / 1.1674, we3 + 8 / 1.1674, h1 + 815.5 / 1.1674));
        }

        if (panel.nop == 2) {
            rmaker(g2d, we2, h1 + 608 / 1.1674, panel.p1nam);
            rmaker(g2d, we3, h1 + 608 / 1.1674, panel.p2nam);
        }

        if (panel.nop == 4) {
            rmaker(g2d, we1, h1 + 608 / 1.1674, panel.p1nam);
            rmaker(g2d, we2, h1 + 608 / 1.1674, panel.p2nam);
            rmaker(g2d, we3, h1 + 608 / 1.1674, panel.p3nam);
            rmaker(g2d, we4, h1 + 608 / 1.1674, panel.p4nam);
        }

        if (panel.nop == 1) {
            rmaker(g2d, wmid, h1 + 608 / 1.1674, panel.p1nam);
        }

        if (panel.nop == 3) {
            rmaker(g2d, wq1, h1 + 608 / 1.1674, panel.p1nam);
            rmaker(g2d, wmid, h1 + 608 / 1.1674, panel.p2nam);
            rmaker(g2d, wq2, h1 + 608 / 1.1674, panel.p3nam);
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
            GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x2Points.length);

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

            g2d.drawString("NH", (float) (cord - 20.63 / 1.1674), (float) (h1 + 145 / 1.1674));
            g2d.setFont(new Font("Braille", Font.PLAIN, 23));
            g2d.drawString("2", (float) (cord + 20.63 / 1.1674), (float) (h1 + 155 / 1.1674));

        }

        if (chemical.equals("Bromine")) {

            g2d.drawString("Br", (float) (cord - 20.63 / 1.1674), (float) (h1 + 222.5 / 1.1674));
            g2d.setFont(new Font("Braille", Font.PLAIN, 23));
            g2d.drawString("2", (float) (cord + 20.63 / 1.1674), (float) (h1 + 232.5 / 1.1674));

        }

    }

    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

        if (page > 0) {

            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        draw(g2d,1);

        return PAGE_EXISTS;
    }

}
