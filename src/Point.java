import java.util.ArrayList;
import java.util.Random;

public class Point {
	private ArrayList<Point> neighbors;
	private int currentState;
	private int nextState;
	private int numStates = 6;
	
	public Point() {
		currentState = 0;
		nextState = 0;
		neighbors = new ArrayList<Point>();
	}

	public void clicked() {
		currentState=(++currentState)%numStates;	
	}
	
	public int getState() {
		return currentState;
	}

	public void setState(int s) {
		currentState = s;
	}

	public void calculateNewState(boolean raining) {
		if (!raining) {
			int activeNeighbors = getActiveNeighbors();
			if (currentState == 0) {
				if (activeNeighbors == 3) {
					nextState = 1;
				} else nextState = 0;
			}
			if (currentState == 1) {
				if (activeNeighbors == 2 || activeNeighbors == 3) {
					nextState = 1;
				} else nextState = 0;
			}
		}else {
			if (currentState == 0 && neighbors.size() > 0 && neighbors.get(0).getState() > 0){
				nextState = 6;
			}else if (currentState <= 0){//bugfix
				nextState = 0;
			}else {
				--nextState;
			}
		}
	}

	public void changeState() {
		currentState = nextState;
	}
	
	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
	
	private int getActiveNeighbors(){
		int activeNeighbors = 0;
		for(Point neighbor : neighbors){
			if(neighbor.getState() != 0)
				activeNeighbors++;
		}
		return activeNeighbors;
	}

	public void drop(){
		Random rng = new Random();
		if(rng.nextInt(100)<5){
			nextState=5;
		}
	}
}
