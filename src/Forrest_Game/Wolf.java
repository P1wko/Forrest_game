package Forrest_Game;

public class Wolf extends Animal {
    public Wolf(int locationX, int locationY, char symbol, World world) {
        super(9, 5, locationX, locationY, symbol, world);
    }

    public void newChild(int x, int y) {
        Entity.newChild(new Wolf(x, y, 'W', _world));
    }
}
