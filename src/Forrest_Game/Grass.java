package Forrest_Game;

public class Grass extends Plant {
    public Grass(int locationX, int locationY, char symbol, World world) {
        super(0, 0, locationX, locationY, symbol, world);
    }

    public void newChild(int x, int y) {
        Entity.newChild(new Grass(x, y, 'G', _world));
    }
}
