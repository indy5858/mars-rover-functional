package kata;

import static fj.data.List.list;

import fj.data.List;

public class Planet {

    public final int x;
    public final int y;
    public List<Obstacle> obstacles;

    public Planet(int x, int y) {
        this.x = x;
        this.y = y;
        this.obstacles = list();
    }

    public Planet(int x, int y, Obstacle... obstacle) {
        this(x, y);
        this.obstacles = list(obstacle);
    }

}
