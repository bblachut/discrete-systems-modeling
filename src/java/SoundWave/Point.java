package SoundWave;

public class Point {

	public Point nNeighbor;
	public Point wNeighbor;
	public Point eNeighbor;
	public Point sNeighbor;
	public float nVel;
	public float eVel;
	public float wVel;
	public float sVel;
	private float pressure;
	public static Integer[] types ={0,1,2};
	public int type;
	public int sinInput = 0;


	public Point() {
		clear();
		type = 0;
	}

	public void clicked() {
		pressure = 1;
	}
	
	public void clear() {
		pressure = 0;
		nVel = 0;
		eVel = 0;
		wVel = 0;
		sVel = 0;
	}

	public void updateVelocity() {
		if (type == 0) {
			nVel = nVel - (nNeighbor.getPressure() - pressure);
			eVel = eVel - (eNeighbor.getPressure() - pressure);
			wVel = wVel - (wNeighbor.getPressure() - pressure);
			sVel = sVel - (sNeighbor.getPressure() - pressure);
		}
	}

	public void updatePresure() {
		if (type == 0) {
			pressure = (float) (pressure - 0.5 * (nVel + sVel + wVel + eVel));
		}else if (type == 2){
			double radians = Math.toRadians(sinInput);
			pressure = (float) (Math.sin(radians));
			sinInput = (sinInput+30)%360;
		}
	}

	public float getPressure() {
		return pressure;
	}
}