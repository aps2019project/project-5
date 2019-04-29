package models.items;

public class Flag extends Item {
    int gotTurn;

    public int getGotTurn() {
        return gotTurn;
    }

    public boolean isGot() {
        return gotTurn != -1;
    }

    public void realese() {
        gotTurn = -1;
    }

    public void get(int turn) {
        gotTurn = turn;
    }
}
