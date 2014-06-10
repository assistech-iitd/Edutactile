package svg;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class MathFunction implements Printable {

    IconPanel panel;

    MathFunction(IconPanel p) {
        panel = p;
    }

    public void draw(Graphics2D g2d, int mode) {

        double h1, h2, w1, w2, wmid, ratio, newwidth;

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

        //Coordinate axes
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(new Line2D.Double(wmid, h1 + 300 - 100, wmid, h1 + 800 - 100));
        g2d.draw(new Line2D.Double(wmid - 250, h1 + 550 - 100, wmid + 250, h1 + 550 - 100));
        g2d.drawString("X", (float) wmid + 260, (float) h1 + 560 - 100);
        g2d.drawString("X'", (float) wmid - 290, (float) h1 + 560 - 100);
        g2d.drawString("Y", (float) wmid - 10, (float) h1 + 290 - 100);
        g2d.drawString("Y'", (float) wmid - 10, (float) h1 + 830 - 100);

        if (panel.fun!=null) {

            switch (panel.flinethickness) {
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
            for(int j=0;j<panel.fun.size();j++){

            if (panel.flinetype == 0) {
                for (double m = -250; m < 250; m = m + 1) {
                    panel.vm.setValue("x", 0.04 * m);
                    double a = 25 * panel.fun.get(j).eval(panel.vm, panel.fm);
                    panel.vm.setValue("x", 0.04 * (m + 1));
                    double b = 25 * panel.fun.get(j).eval(panel.vm, panel.fm);

                    if ((a <= 250) && (b <= 250) && (a >= -250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 - 100 - a, wmid + m + 1, h1 + 550 - 100 - b));
                    } else if ((a <= 250) && (a >= -250) && (b >= 250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 - 100 - a, wmid + m + 1, h1 + 300 - 100));
                    } else if ((a <= -250) && (b <= 250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 800 - 100, wmid + m + 1, h1 + 550 - 100 - b));
                    }
                }
            }

            if (panel.flinetype == 1) {
                for (double m = -250; m < 250; m = m + 16) {
                    panel.vm.setValue("x", 0.04 * m);
                    double a = 25 * panel.fun.get(j).eval(panel.vm, panel.fm);
                    panel.vm.setValue("x", 0.04 * (m + 1));
                    double b = 25 * panel.fun.get(j).eval(panel.vm, panel.fm);

                    if ((a <= 250) && (b <= 250) && (a >= -250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 - 100 - a, wmid + m + 4, h1 + 550 - 100 - b));
                    } else if ((a <= 250) && (a >= -250) && (b >= 250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 - 100 - a, wmid + m + 4, h1 + 300 - 100));
                    } else if ((a <= -250) && (b <= 250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 800 - 100, wmid + m + 4, h1 + 550 - 100 - b));
                    }

                }
            }
            
            if (panel.flinetype == 2) {
                for (double m = -250; m < 250; m = m + 6) {
                    panel.vm.setValue("x", 0.04 * m);
                    double a = 25 * panel.fun.get(j).eval(panel.vm, panel.fm);
                    panel.vm.setValue("x", 0.04 * (m + 1));
                    double b = 25 * panel.fun.get(j).eval(panel.vm, panel.fm);

                    if ((a <= 250) && (b <= 250) && (a >= -250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 - 100 - a, wmid + m + 1, h1 + 550 - 100 - b));
                    } else if ((a <= 250) && (a >= -250) && (b >= 250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 - 100 - a, wmid + m + 1, h1 + 300 - 100));
                    } else if ((a <= -250) && (b <= 250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 800 - 100, wmid + m + 1, h1 + 550 - 100 - b));
                    }

                }
            }
            }
            

        }
    }

    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

        if (page > 0) {

            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        draw(g2d, 1);

        return PAGE_EXISTS;
    }
}
