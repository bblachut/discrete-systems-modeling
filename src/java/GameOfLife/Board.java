package GameOfLife;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

/**
 * Board with Points that may be expanded (with automatic change of cell
 * number) with mouse event listener
 */

public class Board extends JComponent implements MouseInputListener, ComponentListener {
	private static final long serialVersionUID = 1L;
	private Point[][] points;
	private int size = 14;
	private boolean raining;

	public Board(int length, int height, boolean raining) {
		addMouseListener(this);
		addComponentListener(this);
		addMouseMotionListener(this);
		setBackground(Color.WHITE);
		setOpaque(true);
		this.raining = raining;
	}

	// single iteration
	public void iteration() {
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				points[x][y].calculateNewState(raining);
				if (raining && y == 0){
					points[x][y].drop();
				}
			}

		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y)
				points[x][y].changeState();
		this.repaint();
	}


	// clearing board
	public void clear() {
		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y) {
				points[x][y].setState(0);
			}
		this.repaint();
	}

	public void initialize(int length, int height, boolean raining) {
		this.raining = raining;
		points = new Point[length][height];

		for (int x = 0; x < points.length; ++x)
			for (int y = 0; y < points[x].length; ++y)
				points[x][y] = new Point();

		for (int x = 0; x < points.length; ++x) {
			for (int y = 0; y < points[x].length; ++y) {
				if (!raining) {
					if (x > 0 && y > 0) {
						points[x][y].addNeighbor(points[x - 1][y - 1]);
					}
					if (x > 0 && y + 1 < points[x].length) {
						points[x][y].addNeighbor(points[x - 1][y + 1]);
					}
					if (x > 0) {
						points[x][y].addNeighbor(points[x - 1][y]);
					}
					if (y > 0) {
						points[x][y].addNeighbor(points[x][y - 1]);
					}
					if (y + 1 < points[x].length) {
						points[x][y].addNeighbor(points[x][y + 1]);
					}
					if (x + 1 < points.length) {
						points[x][y].addNeighbor(points[x + 1][y]);
					}
					if (x + 1 < points.length && y > 0) {
						points[x][y].addNeighbor(points[x + 1][y - 1]);
					}
					if (x + 1 < points.length && y + 1 < points[x].length) {
						points[x][y].addNeighbor(points[x + 1][y + 1]);
					}
				}else {
					if (y > 0) {
						points[x][y].addNeighbor(points[x][y-1]);
					}
				}
			}
		}
	}


	//paint background and separators between cells
	protected void paintComponent(Graphics g) {
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		g.setColor(Color.GRAY);
		drawNetting(g, size);
	}

	// draws the background netting
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
				if (points[x][y].getState() != 0) {
					switch (points[x][y].getState()) {
						case 0 -> g.setColor(new Color(0xFFFFFFFF));
						case 1 -> g.setColor(new Color(0x58A3EC));
						case 2 -> g.setColor(new Color(0x1B78D5));
						case 3 -> g.setColor(new Color(0x1530D5));
						case 4 -> g.setColor(new Color(0x02279B));
						case 5 -> g.setColor(new Color(0x000A93));
						case 6 -> g.setColor(new Color(0xFF031E59));
					}
					g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
				}
			}
		}

	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX() / size;
		int y = e.getY() / size;
		if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
			points[x][y].clicked();
			this.repaint();
		}
	}

	public void componentResized(ComponentEvent e) {
		int dlugosc = (this.getWidth() / size) + 1;
		int wysokosc = (this.getHeight() / size) + 1;
		initialize(dlugosc, wysokosc, raining);
	}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX() / size;
		int y = e.getY() / size;
		if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
			points[x][y].setState(1);
			this.repaint();
		}
	}

	public int getBoardSize(){
		return size;
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
