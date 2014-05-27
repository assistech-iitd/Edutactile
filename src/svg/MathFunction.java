package svg;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.FuncMap;
import com.graphbuilder.math.VarMap;
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
    String txt;
    int flinethickness, flinetype;
    Expression fun;
    VarMap vm;
    FuncMap fm;
    int fontsize;

    MathFunction(IconPanel p, String t, int fthick, int ftype, Expression func, VarMap varm, FuncMap funcm, int size) {
        panel = p;
        txt = t;
        flinethickness = fthick;
        flinetype = ftype;
        fun = func;
        vm = varm;
        fm = funcm;
        fontsize = size;
    }

    void draw(Graphics2D g2d) {

        double h1, h2, w1, w2, wmid, ratio, newwidth;

        ratio = ((double) panel.getHeight()) / 297;
        newwidth = ratio * 210;

        h2 = panel.getHeight() - 10;
        w1 = ((panel.getWidth() - newwidth) / 2) + 10;
        w2 = ((panel.getWidth() + newwidth) / 2) - 10;
        h1 = 10;
        wmid = (w1 + w2) / 2;

        String txt1 = "", txt2 = "", txtbuffer = "";

        for (int t = 0; t < txt.length(); t++) {

            if ((txt.charAt(t) == (' '))) {

                if (t <= 27) {
                    txt1 = txt1 + txtbuffer + ' ';
                    txtbuffer = "";
                }
                if ((t > 28) && (t <= 55)) {
                    txt2 = txt2 + txtbuffer + ' ';
                    txtbuffer = "";
                }

            } else if ((t == txt.length() - 1) || (t == 27)) {
                if (t <= 27) {
                    txt1 = txt1 + txtbuffer + txt.charAt(t);
                    txtbuffer = "";
                }
                if ((t > 27) && (t <= 55)) {
                    txt2 = txt2 + txtbuffer + txt.charAt(t);
                }

            } else {
                txtbuffer = txtbuffer + txt.charAt(t);
            }

        }

        //Boundary + Top Right Corner Circle
        g2d.draw(new Rectangle2D.Double(w1, h1, w2 - w1, h2 - h1));
        g2d.fill(new Ellipse2D.Double(w1 + 20, h1 + 20, 25, 25));

        // Text box
        g2d.setFont(new Font("Braille", Font.PLAIN, fontsize));
        g2d.drawString(txt1, (float) w1 + 60, (float) h1 + 40);
        g2d.drawString(txt2, (float) w1 + 60, (float) h1 + 75);

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

    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

        if (page > 0) {

            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        print2(g2d);
        //g2d.draw(new Line2D.Double(10, 10, 605, 10));
        //g2d.draw(new Line2D.Double(10, 10, 10, 874));

        return PAGE_EXISTS;
    }

    private void print2(Graphics2D g2d) {
        double h1, h2, w1, w2, wmid;

        h2 = 860;
        w1 = 0;
        w2 = 585;
        h1 = 0;
        wmid = (w1 + w2) / 2;

        String txt1 = "", txt2 = "", txtbuffer = "";

        for (int t = 0; t < txt.length(); t++) {

            if ((txt.charAt(t) == (' '))) {

                if (t <= 27) {
                    txt1 = txt1 + txtbuffer + ' ';
                    txtbuffer = "";
                }
                if ((t > 28) && (t <= 55)) {
                    txt2 = txt2 + txtbuffer + ' ';
                    txtbuffer = "";
                }

            } else if ((t == txt.length() - 1) || (t == 27)) {
                if (t <= 27) {
                    txt1 = txt1 + txtbuffer + txt.charAt(t);
                    txtbuffer = "";
                }
                if ((t > 27) && (t <= 55)) {
                    txt2 = txt2 + txtbuffer + txt.charAt(t);
                }

            } else {
                txtbuffer = txtbuffer + txt.charAt(t);
            }

        }

        //Top Right Corner Circle
        g2d.fill(new Ellipse2D.Double(w1 + 20, h1 + 20, 25, 25));

        // Text box
        g2d.setFont(new Font("Braille", Font.PLAIN, fontsize));
        g2d.drawString(txt1, (float) w1 + 60, (float) h1 + 40);
        g2d.drawString(txt2, (float) w1 + 60, (float) h1 + 75);

        //Coordinate axes
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(new Line2D.Double(wmid, h1 + 300-100, wmid, h1 + 800-100));
        g2d.draw(new Line2D.Double(wmid - 250, h1 + 550-100, wmid + 250, h1 + 550-100));
        g2d.drawString("X", (float) wmid + 260, (float) h1 + 560-100);
        g2d.drawString("X'", (float) wmid - 290, (float) h1 + 560-100);
        g2d.drawString("Y", (float) wmid - 10, (float) h1 + 290-100);
        g2d.drawString("Y'", (float) wmid - 10, (float) h1 + 830-100);

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
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 -100 - a, wmid + m + 1, h1 + 550 -100 - b));
                    } else if ((a <= 250) && (a >= -250) && (b >= 250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 -100 - a, wmid + m + 1, h1 + 300 -100));
                    } else if ((a <= -250) && (b <= 250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 800 -100, wmid + m + 1, h1 + 550 -100 - b));
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
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 -100 - a, wmid + m + 1, h1 + 550 -100 - b));
                    } else if ((a <= 250) && (a >= -250) && (b >= 250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 550 -100 - a, wmid + m + 1, h1 + 300 -100));
                    } else if ((a <= -250) && (b <= 250) && (b >= -250)) {
                        g2d.draw(new Line2D.Double(wmid + m, h1 + 800 -100, wmid + m + 1, h1 + 550 -100 - b));
                    }

                }
            }

        }
    }

}
