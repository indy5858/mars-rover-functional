package kata;

import static fj.data.List.list;
import static kata.Position.add;
import static kata.Position.inverted;
import static kata.Position.position;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import fj.F;
import fj.data.List;

public class MarsRoverTest {

    @Test
    public void roverHasPositionAndDirection() throws Exception {
        Rover rover = new Rover(position(0, 0), "N", new Planet(10, 10));

        assertThat(rover.position.x).isEqualTo(0);
        assertThat(rover.position.y).isEqualTo(0);
        assertThat(rover.direction).isEqualTo(Direction.N);
    }

    @Test
    public void roverDoesNotMoveForEmptyString() throws Exception {
        Rover rover = new Rover(position(0, 0), "N", new Planet(10, 10));

        Rover notMoved = move(rover, list());

        assertThat(notMoved).isEqualToComparingFieldByFieldRecursively(rover);
    }

    @Test
    public void roverMovesOneStepForward() throws Exception {
        Rover rover = new Rover(position(0, 0), "N", new Planet(10, 10));

        Rover oneStepNorth = singleMove(rover, 'f');

        assertThat(oneStepNorth)
                .isEqualToComparingFieldByFieldRecursively(new Rover(position(0, 1), "N", new Planet(10, 10)));
    }

    @Test
    public void northwardFacingRoverMovesOneStepForwardFromArbitraryPosition() throws Exception {
        Rover rover = new Rover(position(1, 2), "N", new Planet(10, 10));

        Rover movedNorth = singleMove(rover, 'f');

        assertThat(movedNorth)
                .isEqualToComparingFieldByFieldRecursively(new Rover(position(1, 3), "N", new Planet(10, 10)));
    }

    @Test
    public void northwardFacingRoverMovesSeveralStepsNorth() throws Exception {
        Rover rover = new Rover(position(0, 0), "N", new Planet(10, 10));
        Rover moved = move(rover, list('f', 'f', 'f'));
        assertThat(moved).isEqualToComparingFieldByFieldRecursively(new Rover(position(0, 3), "N", new Planet(10, 10)));
    }

    @Test
    public void eastwardFacingRoverMovesOneStepForward() throws Exception {
        Rover rover = new Rover(position(0, 0), "E", new Planet(10, 10));
        Rover moved = move(rover, list('f'));
        assertThat(moved).isEqualToComparingFieldByFieldRecursively(new Rover(position(1, 0), "E", new Planet(10, 10)));
    }

    @Test
    public void southwardFacingRoverMovesOneStepBack() throws Exception {
        Rover rover = new Rover(position(0, 0), "S", new Planet(10, 10));
        Rover moved = move(rover, list('b'));
        assertThat(moved).isEqualToComparingFieldByFieldRecursively(new Rover(position(0, 1), "S", new Planet(10, 10)));
    }

    @Test
    public void northwardFacingRoverTurnsLeft() throws Exception {
        Rover rover = new Rover(position(0, 0), "N", new Planet(10, 10));
        Rover moved = move(rover, list('l'));
        assertThat(moved).isEqualToComparingFieldByFieldRecursively(new Rover(position(0, 0), "W", new Planet(10, 10)));
    }

    @Test
    public void northwardFacingRoverTurnsRight() throws Exception {
        Rover rover = new Rover(position(0, 0), "N", new Planet(10, 10));
        Rover moved = move(rover, list('r'));
        assertThat(moved).isEqualToComparingFieldByFieldRecursively(new Rover(position(0, 0), "E", new Planet(10, 10)));
    }

    @Test
    public void roverWalksInSquare() throws Exception {
        Rover rover = new Rover(position(0, 0), "N", new Planet(10, 10));
        Rover moved = move(rover, list('f', 'l', 'f', 'l', 'f', 'l', 'f'));
        assertThat(moved).isEqualToComparingFieldByFieldRecursively(new Rover(position(0, 0), "E", new Planet(10, 10)));
    }

    @Test
    public void northwardFacingRoverWrapsAround() throws Exception {
        Rover rover = new Rover(position(0, 0), "N", new Planet(5, 5));
        Rover moved = move(rover, list('f', 'f', 'f', 'f', 'f'));
        assertThat(moved).isEqualToComparingFieldByFieldRecursively(new Rover(position(0, 0), "N", new Planet(5, 5)));
    }

    @Test
    public void northwardFacingRoverMovingBackwardWrapsAround() throws Exception {
        Rover rover = new Rover(position(0, 0), "N", new Planet(5, 5));
        Rover moved = move(rover, list('b', 'b', 'b', 'b', 'b'));
        assertThat(moved).isEqualToComparingFieldByFieldRecursively(new Rover(position(0, 0), "N", new Planet(5, 5)));
    }

    @Test
    public void roverStopsInForwardMoveIfObstacleAtNextPosition() throws Exception {
        Rover rover = new Rover(position(0, 9), "N", new Planet(10, 10, new Obstacle(position(0, 10))));
        Rover moved = move(rover, list('f'));
        assertThat(moved).isEqualToComparingFieldByFieldRecursively(rover);
    }

    @Test
    public void roverStopsInBackwardMoveIfObstacleAtNextPosition() throws Exception {
        Rover rover = new Rover(position(0, 0), "N", new Planet(10, 10, new Obstacle(position(0, 9))));
        Rover moved = move(rover, list('b'));
        assertThat(moved).isEqualToComparingFieldByFieldRecursively(rover);
    }

    @Test
    public void acceptanceTest() throws Exception {
        Rover rover = new Rover(position(2, 3), "N",
                new Planet(5, 5, new Obstacle(position(0, 4)), new Obstacle(position(2, 4))));
        Rover moved = move(rover, list('r', 'r', 'f', 'r', 'f', 'r', 'f', 'f', 'l', 'f', 'b', 'r', 'f', 'r', 'b', 'l',
                'f', 'f', 'f', 'l', 'b', 'b', 'r'));
        assertThat(moved).isEqualToComparingFieldByFieldRecursively(rover);
    }

    private Rover move(Rover rover, List<Character> command) {
        return command.foldLeft(this::singleMove, rover);
    }

    private Rover singleMove(Rover rover, char command) {
        if (command == 'f') {
            if (rover.planet.obstacles.exists(obstacleMovingForward(rover))) {
                return rover;
            } else {
                return new Rover(forwardPosition(rover), rover.direction.toString(), rover.planet);
            }
        } else if (command == 'b') {
            if (rover.planet.obstacles.exists(obstacleMovingBackward(rover))) {
                return rover;
            } else {
                return new Rover(backwardPosition(rover), rover.direction.toString(), rover.planet);
            }
        } else if (command == 'l') {
            return new Rover(rover.position, rover.direction.toLeft(), rover.planet);
        } else if (command == 'r') {
            return new Rover(rover.position, rover.direction.toRight(), rover.planet);
        } else {
            throw new IllegalArgumentException("unknown command");
        }
    }

    private F<Obstacle, Boolean> obstacleMovingBackward(Rover rover) {
        return o -> o.getPosition().sameAs(backwardPosition(rover), rover.planet);
    }

    private F<Obstacle, Boolean> obstacleMovingForward(Rover rover) {
        return o -> o.getPosition().sameAs(forwardPosition(rover), rover.planet);
    }

    private Position forwardPosition(Rover rover) {
        return add(rover.position, rover.direction.offset, rover.planet);
    }

    private Position backwardPosition(Rover rover) {
        return add(rover.position, inverted(rover.direction.offset), rover.planet);
    }

}