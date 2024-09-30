package Forrest_Game;

import java.util.Random;

abstract class Plant extends Entity {

    public Plant(int strenght, int initiative, int locationX, int locationY, char symbol, World world) {
        super(strenght, initiative, locationX, locationY, symbol, world);
    }

    public void Action() {
        int jump;
        int newPlant = new Random().nextInt(4);
        int newEntity = new Random().nextInt(4);

        if(newPlant == 1) {
            switch (newEntity) {
                case 0:
                    jump = _locationX + 1;
                    if(!_world.noWall(_locationX + 1, _locationY)) jump = 0;
                    if (_world.isFree(jump, _locationY))
                    {
                        _world.addComment(_symbol + " is multiplying on (" + jump + ", " + _locationY + ").");
                        newChild(jump, _locationY);
                    }
                    break;
                case 1:
                    jump = _locationY + 1;
                    if(!_world.noWall(_locationX, _locationY + 1)) jump = 0;
                    if (_world.isFree(_locationX, jump))
                    {
                        _world.addComment(_symbol + " is multiplying on (" + _locationX + ", " + jump + ").");
                        newChild(_locationX, jump);
                    }
                    break;
                case 2:
                    jump = _locationY - 1;
                    if(!_world.noWall(_locationX, _locationY - 1)) jump = _world.getWielkoscSwiata() - 1;
                    if (_world.isFree(_locationX, jump))
                    {
                        _world.addComment(_symbol + " is multiplying on (" + _locationX + ", " + jump + ").");
                        newChild(_locationX, jump);
                    }
                    break;
                case 3:
                    jump = _locationX - 1;
                    if(!_world.noWall(_locationX - 1, _locationY)) jump = _world.getWielkoscSwiata() - 1;
                    if (_world.isFree(jump, _locationY))
                    {
                        _world.addComment(_symbol + " is multiplying on (" + jump + ", " + _locationY + ").");
                        newChild(jump, _locationY);
                    }
                    break;
                default:
                    break;
            }
        }
        _age++;
    }

    public void Kolizja() {

    }
}
