package cars;

public class Point {
    private int value;
    private Point next;
    private boolean moved;
    private int velocity;

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
        if (value == 0){
            value = 1;
        }else value = 0;
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
}

