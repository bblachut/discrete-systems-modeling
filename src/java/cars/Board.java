package cars;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.lang.Math;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private static final double p = 0.3;
    private Point[][] points;
    private int size = 10;
    public int editType = 0;

    public Board(int length, int height) {
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    private void initialize(int length, int height) {
        points = new Point[length][height];

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y] = new Point();
            }
        }
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].setNext(points[x == 0 ? points.length - 1 : x - 1][y]);
            }
        }

    }

    public void slowDown(){
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                Point p = points[x][y];
                if (p.getValue() == 1) {
                    Point n = p.getNext();
                    for (int i = 0; i < p.getVelocity(); i++) {
                        if (n.getValue() == 1) {
                            p.setVelocity(i);
                            break;
                        } else n = n.getNext();
                    }
                }
            }
        }
    }

    public void accelerate(){
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                Point p = points[x][y];
                if (p.getValue() == 1) {
                    if (p.getVelocity() < 5) p.setVelocity(p.getVelocity() + 1);
                }
            }
        }
    }

    public void random(){
        Random rng = new Random();
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if (rng.nextInt(100)/100.0 < p &&points[x][y].getValue() == 1 && points[x][y].getVelocity() >0){
                    points[x][y].setVelocity(points[x][y].getVelocity()-1);
                }
            }
        }
    }

    public void move(){
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if (points[x][y].getValue() == 1) {
                    points[x][y].setValue(0);
                    points[Math.floorMod(x - points[x][y].getVelocity(), points.length)][y].setValue(1);
                }
            }
        }
    }

    public void iteration() {
        accelerate();
        slowDown();
        random();
        move();
//        for (int x = 0; x < points.length; ++x) {
//            for (int y = 0; y < points[x].length; ++y) {
//                points[x][y].setMoved(false);
//                points[x][y].move();
//            }
//        }
        this.repaint();
    }

    public void clear() {
        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].clear();
            }
        this.repaint();
    }


    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        for (x = 0; x < points.length; ++x) {
            for (y = 0; y < points[x].length; ++y) {
                float a = 1.0F;
                if (points[x][y].getValue() == 0) {
                    g.setColor(new Color(1, 1, 1, 0.7f));
                }else g.setColor(new Color(0, 0, 0, 0.7f));

                g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if (editType == 0) {
                points[x][y].clicked();
            }
            this.repaint();
        }
    }

    public void componentResized(ComponentEvent e) {
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = (this.getHeight() / size) + 1;
        initialize(dlugosc, wysokosc);
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            if (editType == 0) {
                points[x][y].clicked();
            }
            this.repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}
