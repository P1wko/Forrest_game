package Forrest_Game;

public class Wolfberries extends Plant {
    public Wolfberries(int locationX, int locationY, char symbol, World world) {
        super(0, 0, locationX, locationY, symbol, world);
    }

    public void newChild(int x, int y) {
        Entity.newChild(new Wolfberries(x, y, 'J', _world));
    }
}
