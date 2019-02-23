package kata;

public class Rover {

    public final Direction direction;
    public final Planet planet;
    public final Position position;

    public Rover(Position position, String direction, Planet planet) {
        this.position = position;
        this.planet = planet;
        this.direction = Direction.valueOf(direction);
    }

}
