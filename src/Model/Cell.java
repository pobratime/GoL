package Model;

public class Cell {

    private boolean state;

    public Cell() {
        this.state = false;
    }

    public boolean isAlive() {
        return state;
    }

    public void changeState(boolean state) {
        this.state = state;
    }

    public void kill() {
        this.state = false;
    }

    public void revive() {
        this.state = true;
    }
}