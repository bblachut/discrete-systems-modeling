package CarsOvertaking;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private static final double p = 0.3;
    private Point[][] points;
    private int size = 25;
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
                if (y > 1 && y < 4) {
                    points[x][y].setNext(points[x == 0 ? points.length - 1 : x - 1][y]);
                    points[x][y].setPrev(points[x == points.length - 1 ? 0 : x + 1][y]);
                }else {
                    points[x][y].setType(5);
                }
            }
        }

    }

    private void slowDown(){
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

    private void accelerate(){
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                Point p = points[x][y];
                if (p.getValue() == 1) {
                    if (p.getVelocity() < p.getMaxVelocity()) p.setVelocity(p.getVelocity() + 1);
                }
            }
        }
    }

    private boolean canTakeOver(int x, int y){
        if (y != 2) return false;
        Point p = points[x][y];
        Point leftP = points[x][y+1];
        Point prev = p.getPrev();
        Point leftPrev = leftP.getPrev();
        for (int i = 0; i < p.getMaxVelocity(); i++) {
            if (prev.getValue() == 1 || leftPrev.getValue() == 1){
                return false;
            }
            prev = prev.getPrev();
            leftPrev = leftPrev.getPrev();
        }

        Point leftNext = leftP.getNext();
        for (int i = 0; i <= p.getVelocity(); i++) {
            if (leftNext.getValue() == 1){
                return false;
            }
            leftNext = leftNext.getNext();
        }
        return p.getVelocity()<p.getMaxVelocity() ;
    }

    private boolean canGoBack(int x, int y){
        if (y != 3) return false;
        Point p = points[x][y];
        Point rightP = points[x][y-1];
        Point prev = p.getPrev();
        Point rightPrev = rightP.getPrev();
        for (int i = 0; i < p.getMaxVelocity(); i++) {
            if (prev.getValue() == 1 || rightPrev.getValue() == 1){
                return false;
            }
            prev = prev.getPrev();
            rightPrev = rightPrev.getPrev();
        }
        Point rightNext = rightP.getNext();
        for (int i = 0; i <= p.getVelocity(); i++) {
            if (rightNext.getValue() == 1){
                return false;
            }
            rightNext = rightNext.getNext();
        }
        return true;
    }

    private void move(){
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if (points[x][y].getValue() == 1) {
                    if (canTakeOver(x, y)){
                        points[Math.floorMod(x - points[x][y].getVelocity()-1, points.length)][y+1].setValue(1);
                        points[Math.floorMod(x - points[x][y].getVelocity()-1, points.length)][y+1].setType(points[x][y].getType());
                        points[Math.floorMod(x - points[x][y].getVelocity()-1, points.length)][y+1].setMaxVelocity(points[x][y].getMaxVelocity());
                        points[Math.floorMod(x - points[x][y].getVelocity()-1, points.length)][y+1].setVelocity(points[x][y].getVelocity());
                    }else if (canGoBack(x, y)){
                        points[Math.floorMod(x - points[x][y].getVelocity(), points.length)][y-1].setValue(1);
                        points[Math.floorMod(x - points[x][y].getVelocity(), points.length)][y-1].setType(points[x][y].getType());
                        points[Math.floorMod(x - points[x][y].getVelocity(), points.length)][y-1].setMaxVelocity(points[x][y].getMaxVelocity());
                        points[Math.floorMod(x - points[x][y].getVelocity(), points.length)][y-1].setVelocity(points[x][y].getVelocity());
                    } else {
                        points[Math.floorMod(x - points[x][y].getVelocity(), points.length)][y].setValue(1);
                        points[Math.floorMod(x - points[x][y].getVelocity(), points.length)][y].setType(points[x][y].getType());
                        points[Math.floorMod(x - points[x][y].getVelocity(), points.length)][y].setMaxVelocity(points[x][y].getMaxVelocity());
                        points[Math.floorMod(x - points[x][y].getVelocity(), points.length)][y].setVelocity(points[x][y].getVelocity());
                    }
                    points[x][y].setValue(0);
                    points[x][y].setType(0);
                }
            }
        }
    }

    public void iteration() {
        accelerate();
        slowDown();
        move();

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
                switch (points[x][y].getType()){
                    case 0 -> g.setColor(new Color(255, 255, 255));
                    case 1 -> g.setColor(new Color(255, 205, 46));
                    case 2 -> g.setColor(new Color(10, 52, 222));
                    case 3 -> g.setColor(new Color(253, 1, 1));
                    case 5 -> g.setColor(new Color(18, 225, 29));
                }

                    g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            points[x][y].setType(editType);
            switch (editType){
                case 0, 5 ->{
                    points[x][y].setMaxVelocity(0);
                    points[x][y].setValue(0);
                }
                case 1 ->{
                    points[x][y].setMaxVelocity(3);
                    points[x][y].setValue(1);
                }
                case 2 ->{
                    points[x][y].setMaxVelocity(5);
                    points[x][y].setValue(1);
                }
                case 3 ->{
                    points[x][y].setMaxVelocity(7);
                    points[x][y].setValue(1);
                }
            }
            points[x][y].setVelocity(points[x][y].getMaxVelocity());
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
            points[x][y].setType(editType);
            switch (editType){
                case 0, 5 ->{
                    points[x][y].setMaxVelocity(0);
                    points[x][y].setValue(0);
                }
                case 1 ->{
                    points[x][y].setMaxVelocity(3);
                    points[x][y].setValue(1);
                }
                case 2 ->{
                    points[x][y].setMaxVelocity(5);
                    points[x][y].setValue(1);
                }
                case 3 ->{
                    points[x][y].setMaxVelocity(7);
                    points[x][y].setValue(1);
                }
            }
            points[x][y].setVelocity(points[x][y].getMaxVelocity());;
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
