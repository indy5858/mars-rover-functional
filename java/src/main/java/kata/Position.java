package kata;

public class Position {
    public final int x;
    public final int y;

    public static Position position(int x, int y) {
        return new Position(x, y);
    }

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean sameAs(Position other, Planet planet) {
        return (x - other.x) % planet.x == 0 && (y - other.y) % planet.y == 0;
    }

    public static Position add(Position first, Position second, Planet planet) {
        return position((first.x + second.x) % planet.x, (first.y + second.y) % planet.y);
    }

    public static Position inverted(Position position) {
        return position(-position.x, -position.y);
    }
}