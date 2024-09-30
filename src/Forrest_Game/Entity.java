package Forrest_Game;

abstract class Entity {
    protected int _strength, _initiative;
    protected int _locationX, _locationY;
    protected static World _world;
    protected char _symbol;
    protected int _age = 0;

    public Entity(int strength, int initiative, int locationX, int locationY, char symbol, World world) {
        this._strength = strength;
        this._initiative = initiative;
        this._locationX = locationX;
        this._locationY = locationY;
        this._symbol = symbol;
        this._world = world;
    }

    public void Action() {
        return;
    }


    public int getStrenght() {
        return _strength;
    }

    public int getInitiative() {
        return _initiative;
    }

    public int getLocationX() {
        return _locationX;
    }

    public int getLocationY() {
        return _locationY;
    }

    public char getSymbol() {
        return _symbol;
    }

    public int getAge() {
        return _age;
    }

    public static void newChild(Entity nowy) {
        _world.addEntity(nowy);
    }

    public void newChild(int x, int y) {
        return;
    }
}
