package svg;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGElement;
import com.kitfox.svg.SVGElementException;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.Tspan;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageConverter implements Printable {

    IconPanel panel;

    ImageConverter(IconPanel p) {
        panel = p;
    }

    void draw(Graphics2D g2d, int mode) throws SVGException {

        double h1, h2, w1, w2, ratio, newwidth;

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

        if (panel.f != null) {

            try {
                SVGUniverse svgUniverse = new SVGUniverse();
                SVGDiagram diagram = svgUniverse.getDiagram(svgUniverse.loadSVG(panel.f.toURI().toURL()));
                SVGElement root = diagram.getRoot();

                panel.converted.clear();
                converter(root, panel.converted);
                scaling(panel.converted, panel, mode);
                aslipaint(g2d, panel.converted, panel.textures);

            } catch (MalformedURLException ex) {
                Logger.getLogger(IconPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void converter(SVGElement root, ArrayList<Shape> converted) throws SVGElementException, SVGException {

        switch (root.getTagName()) {
            case "rect": {
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
                aalo.minx = x;
                aalo.miny = y;
                aalo.maxx = x + width;
                aalo.maxy = y + height;
                converted.add(aalo);
                break;
            }
            case "circle": {
                double radius = root.getPresAbsolute("r").getDoubleValue();
                double x = root.getPresAbsolute("cx").getDoubleValue();
                double y = root.getPresAbsolute("cy").getDoubleValue();
                Shape aalo = new Shape();
                aalo.type = "Circ";
                aalo.circr = radius;
                aalo.circx = x;
                aalo.circy = y;
                aalo.minx = x - radius;
                aalo.miny = y - radius;
                aalo.maxx = x + radius;
                aalo.maxy = y + radius;
                converted.add(aalo);
                break;
            }
            case "line": {
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
                aalo.minx = Math.min(x1, x2);
                aalo.miny = Math.min(y1, y2);
                aalo.maxx = Math.max(x1, x2);
                aalo.maxy = Math.max(y1, y2);
                converted.add(aalo);
                break;
            }
            case "tspan": {
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
                aalo.minx = x;
                aalo.miny = y;
                aalo.maxx = x + ((s.length()) * 35);
                aalo.maxy = y + (35);
                converted.add(aalo);
                break;
            }
            case "path": {
                String love = root.getPresAbsolute("d").getStringValue();
                //System.out.println(love);
                int state = 0;
                Shape aalo = new Shape();
                aalo.type = "Path";
                aalo.path = new ArrayList<>();
                PathType reserve = null;
                Coordinate r1 = null;
                String first = null, second = null;

                //<editor-fold defaultstate="collapsed" desc="State Machine">
                while (love.length() != 0) {

                    //System.out.println("State: " + state);
                    //System.out.println("Character: " + love.charAt(0));
                    //System.out.println(" ");
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
                break;
            }
        }

        int rambo = root.getNumChildren();

        for (int i = 0; i < rambo; i++) {
            converter(root.getChild(i), converted);
        }

    }

    private void scaling(ArrayList<Shape> converted, IconPanel panel, int mode) {

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

        double ratio = ((double) panel.getHeight()) / 297;
        double newwidth = ratio * 210;
        double h2 = panel.getHeight() - 10;
        double w1 = ((panel.getWidth() - newwidth) / 2) + 10;
        double w2 = ((panel.getWidth() + newwidth) / 2) - 10;

        if (mode == 1) {
            h2 = 700;
            w1 = 0;
            w2 = 550;
        }

        double asliheight = (h2 - 5) - (h2 / 4);
        double asliwidth = (w2 - 5) - (w1 + 5);

        double xshift = 0.0;
        double yshift = 0.0;
        double scaling = 0.0;

        if (inputheight >= inputwidth) {

            scaling = (asliheight / inputheight);

            inputwidth = inputwidth * scaling;

            xshift = (w1 + 5) + ((asliwidth - inputwidth) / 2);
            if (mode == 1) {
                xshift = xshift + 25;
            }
            yshift = h2 / 4;

        } else {

            scaling = (asliwidth / inputwidth);

            inputheight = inputheight * scaling;

            xshift = (w1 + 5);
            yshift = h2 / 4 + ((asliheight - inputheight) / 2);
            if (mode == 1) {
                xshift = xshift + 25;
            }

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

            } else if (l.type.equals("Circ")) {

                //Parameters for displaying a circle
                l.circr = l.circr * scaling;
                l.circx = (l.circx - (minx - 10)) * scaling + xshift;
                l.circy = (l.circy - (miny - 10)) * scaling + yshift;

            } else if (l.type.equals("Text")) {

                //Parameters for displaying text
                l.textx = (l.textx - (minx - 10)) * scaling + xshift;
                l.texty = (l.texty - (miny - 10)) * scaling + yshift;
                l.font = l.font;

            } else if (l.type.equals("Line")) {

                //Parameters for displaying a line
                l.linx = (l.linx - (minx - 10)) * scaling + xshift;
                l.liny = (l.liny - (miny - 10)) * scaling + yshift;
                l.linxa = (l.linxa - (minx - 10)) * scaling + xshift;
                l.linya = (l.linya - (miny - 10)) * scaling + yshift;

            } else if (l.type.equals("Path")) {

                int okok = l.path.size();

                for (int i = 0; i < okok; i++) {

                    if ((l.path.get(i).Type) == ('m') || (l.path.get(i).Type) == ('l')) {

                        if (i == 0) {

                            //For displaying a path
                            l.path.get(i).single.x = (l.path.get(i).single.x - (minx - 10)) * scaling + xshift;
                            l.path.get(i).single.y = (l.path.get(i).single.y - (miny - 10)) * scaling + yshift;
                        } else {

                            //For displaying a path
                            l.path.get(i).single.x = l.path.get(i).single.x * scaling;
                            l.path.get(i).single.y = l.path.get(i).single.y * scaling;
                        }

                    };

                    if ((l.path.get(i).Type) == ('M') || (l.path.get(i).Type) == ('L')) {

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

    private void aslipaint(Graphics2D g2d, ArrayList<Shape> converted, ArrayList<Texture> textures) {
        int is = converted.size();
        for (int iki = 0; iki < is; iki++) {

            Shape l = converted.get(iki);

            if (l.type.equals("Rect")) {

                g2d.draw(new Rectangle2D.Double(l.recx, l.recy, l.recwidth, l.recheight));
                converted.get(iki).s = new Rectangle2D.Double(l.recx, l.recy, l.recwidth, l.recheight);

            }

            if (l.type.equals("Circ")) {

                g2d.draw(new Ellipse2D.Double(l.circx - l.circr, l.circy - l.circr, 2 * l.circr, 2 * l.circr));
                converted.get(iki).s = new Ellipse2D.Double(l.circx - l.circr, l.circy - l.circr, 2 * l.circr, 2 * l.circr);

            }

            if (l.type.equals("Text")) {

                g2d.setFont(new Font("Braille", Font.PLAIN, (int) l.font));
                g2d.drawString(l.text, (float) l.textx, (float) l.texty);
            }

            if (l.type.equals("Line")) {

                g2d.draw(new Line2D.Double(l.linx, l.liny, l.linxa, l.linya));
                converted.get(iki).s = new Line2D.Double(l.linx, l.liny, l.linxa, l.linya);
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
                converted.get(iki).s = polyline;

            }
        }

        for (int i = 0; i < textures.size(); i++) {

            Rectangle2D rect = new Rectangle2D.Double(0, 0, panel.getWidth(), panel.getHeight());
            BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
            Graphics2D big = bi.createGraphics();

            if (textures.get(i).texturetype == 1) {
                big.setColor(Color.black);
                big.fillRect(0, 0, 5, 5);
                big.setColor(Color.white);
                big.fillOval(0, 0, 5, 5);
            }

            if (textures.get(i).texturetype == 2) {
                big.setColor(Color.white);
                for (int it = 0; it < 5; it++) {
                    big.drawLine(0, it, it, 0);
                    big.drawLine(5 - it, 5, 5, 5 - it);
                }
            }

            Rectangle r = new Rectangle(0, 0, 5, 5);
            g2d.setPaint(new TexturePaint(bi, r));

            Area wholearea = new Area(rect);

            for (int j = 0; j < converted.size(); j++) {

                if (textures.get(i).code.charAt(j) == '2') {
                } else {
                    Area a = new Area(converted.get(j).s);
                    if (textures.get(i).code.charAt(j) == '1') {
                        wholearea.intersect(a);
                    } else {
                        wholearea.subtract(a);
                    }
                }
            }
            g2d.fill(wholearea);
        }

    }

    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

        if (page > 0) {

            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        try {
            draw(g2d, 1);
        } catch (SVGElementException ex) {
            Logger.getLogger(ImageConverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SVGException ex) {
            Logger.getLogger(ImageConverter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return PAGE_EXISTS;
    }
}
