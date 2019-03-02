package kata;

import static kata.Position.position;

public enum Direction {

    N(position(0, 1)), W(position(-1, 0)), S(position(0, -1)), E(position(1, 0));

    public final Position offset;

    private Direction(Position position) {
        this.offset = position;
    }

    public String toRight() {
        return Direction.values()[(this.ordinal() + 3) % 4].toString();
    }

    public String toLeft() {
        return Direction.values()[(this.ordinal() + 1) % 4].toString();
    }
}
