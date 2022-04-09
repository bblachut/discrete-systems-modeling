package Pedestrians;

import java.util.ArrayList;

public class Point {

	public ArrayList<Point> neighbors;
	public static Integer []types ={0,1,2,3};
	public int type;
	public int staticField;
	public boolean isPedestrian;
	public boolean isBlocked;

	public Point() {
		type=0;
		staticField = 100000;
		neighbors= new ArrayList<Point>();
	}
	
	public void clear() {
		staticField = 100000;
		
	}

	public boolean calcStaticField() {
		if (type == 1){
			staticField = 100000;
			return false;
		}
		boolean was_changed = false;
		for(Point neighbor: neighbors){
			if (staticField > neighbor.staticField+1){
				staticField = neighbor.staticField+1;
				was_changed = true;
			}
		}
		return was_changed;
	}
	
	public void move() {
		if (!isPedestrian || isBlocked) return;
		Point nextStep = null;
		for (Point neighbor : neighbors) {
			if (neighbor.type != 1 && !neighbor.isPedestrian) {
				if (nextStep == null)
					nextStep = neighbor;
				else if (neighbor.staticField < nextStep.staticField)
					nextStep = neighbor;
			}
		}
		if (nextStep == null) return;
		nextStep.isBlocked = true;
		if (nextStep.type != 2)
			nextStep.isPedestrian = true;
		isPedestrian = false;
	}

	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
}