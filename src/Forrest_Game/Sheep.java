package Forrest_Game;

public class Sheep extends Animal {
    public Sheep(int locationX, int locationY, char symbol, World world) {
        super(4, 4, locationX, locationY, symbol, world);
    }

    public void newChild(int x, int y) {
        Entity.newChild(new Sheep(x, y, 'S', _world));
    }
}
