package CarsOvertaking;

public class Point {
    private int value;
    private Point next;
    private Point prev;
    private boolean moved;
    private int velocity;
    private int maxVelocity;
    private int type;
    public static Integer[] types = {0, 1, 2, 3, 5};

    public Point(){
        velocity = 1;
    }

    public void move() {
        if (next.getValue() == 0 && !next.isMoved() && value == 1 && !moved){
            value = 0;
            next.setValue(1);
            moved = true;
        }
    }

    public void clicked() {
        type = 0;
    }


    public void clear() {
        value = 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Point getNext() {
        return next;
    }

    public void setNext(Point next) {
        this.next = next;
    }
    public void setType(int type) {
        this.type = type;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public Point getPrev() {
        return prev;
    }

    public void setPrev(Point prev) {
        this.prev = prev;
    }

    public int getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(int maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public int getType() {
        return type;
    }
}

