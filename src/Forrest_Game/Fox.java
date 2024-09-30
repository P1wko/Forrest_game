package Forrest_Game;

import java.util.Random;

public class Fox extends Animal {
    public Fox(int locationX, int locationY, char symbol, World world) {
        super(3, 7, locationX, locationY, symbol, world);
    }

    public void newChild(int x, int y) {
        Entity.newChild(new Fox(x, y, 'F', _world));
    }

    public void Action() {
        int movement = new Random().nextInt(4);
        int jump;

        switch (movement)
        {
            case 0:
                jump = _locationX + 1;
                if (!_world.noWall(_locationX + 1, _locationY)) jump = 0;
                if (_world.isFree(jump, _locationY))
                {
                    _world.addComment(_symbol + " moves on (" + jump + ", " + _locationY + ").");
                    _locationX = jump;
                }
                else
                {
                    int index = _world.getIndex(jump, _locationY);
                    if (_world.getEntityStrength(index) <= _strength)
                    {
                        Collision(jump, _locationY);
                    } else {
                        _world.addComment(_symbol + " doesn't attack stronger opponent on (" + jump + ", " + _locationY + ").");
                    }
                }
                break;
            case 1:
                jump = _locationY + 1 ;
                if (!_world.noWall(_locationX, _locationY + 1)) jump = 0;
                if (_world.isFree(_locationX, jump))
                {
                    _world.addComment(_symbol + " moves on (" + _locationX + ", " + jump + ").");
                    _locationY = jump;
                }
                else
                {
                    int index = _world.getIndex(_locationX, jump);
                    if (_world.getEntityStrength(index) <= _strength)
                    {
                        Collision(_locationX, jump);
                    } else {
                        _world.addComment(_symbol + " doesn't attack stronger opponent on (" + _locationY + ", " + jump + ").");
                    }
                }
                break;
            case 2:
                jump = _locationX - 1;
                if (!_world.noWall(_locationX - 1, _locationY)) jump = _world.getWielkoscSwiata() - 1;
                if (_world.isFree(jump, _locationY))
                {
                    _world.addComment(_symbol + " moves on (" + jump + ", " + _locationY + ").");
                    _locationX = jump;
                }
                else
                {
                    int index = _world.getIndex(jump, _locationY);
                    if (_world.getEntityStrength(index) <= _strength)
                    {
                        Collision(jump, _locationY);
                    } else {
                        _world.addComment(_symbol + " doesn't attack stronger opponent on (" + jump + ", " + _locationY + ").");
                    }
                }
                break;
            case 3:
                jump = _locationY - 1;
                if (!_world.noWall(_locationX, _locationY - 1)) jump = _world.getWielkoscSwiata() - 1;
                if (_world.isFree(_locationX, jump))
                {
                    _world.addComment(_symbol + " moves on (" + _locationX + ", " + jump + ").");
                    _locationY = jump;
                }
                else
                {
                    int index = _world.getIndex(_locationX, jump);
                    if (_world.getEntityStrength(index) <= _strength)
                    {
                        Collision(_locationX, jump);
                    } else {
                        _world.addComment(_symbol + " doesn't attack stronger opponent on (" + _locationX + ", " + jump + ").");
                    }
                }
                break;
            default:
                break;
        }
        _age++;
    }
}
