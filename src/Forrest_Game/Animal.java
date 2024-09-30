package Forrest_Game;

import java.util.Random;

abstract class Animal extends Entity {

    public Animal(int strenght, int initiative, int locationX, int locationY, char symbol, World world) {
        super(strenght, initiative, locationX, locationY, symbol, world);
    }

    public void Action() {
        int jump;
        int movement = new Random().nextInt(4);

        switch (movement) {
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
                    Collision(jump, _locationY);
                }
                break;
            case 1:
                jump = _locationY + 1;
                if (!_world.noWall(_locationX, _locationY + 1)) jump = 0;
                if (_world.isFree(_locationX, jump))
                {
                    _world.addComment(_symbol + " moves on (" + _locationX + ", " + jump + ").");
                    _locationY = jump;
                }
                else
                {
                    Collision(_locationX, jump);
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
                    Collision(jump, _locationY);
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
                    Collision(_locationX, jump);
                }
                break;
            default:
                break;
        }
        _age++;
    }

    public void Collision(int animalX, int animalY) {
        int jump;
        if(_world.isFrendly(_symbol, animalX, animalY)) {
            int newEntity = new Random().nextInt(4);
            switch (newEntity)
            {
                case 0:
                    jump = animalX + 1;
                    if(!_world.noWall(animalX + 1, animalY)) jump = 0;
                    if (_world.isFree(jump, animalY))
                    {
                        newChild(jump, animalY);
                        _world.addComment(_symbol + " is multiplying on (" + jump + ", " + animalY + ").");
                    }
                    break;
                case 1:
                    jump = animalY + 1;
                    if(!_world.noWall(animalX, animalY + 1)) jump = 0;
                    if (_world.isFree(animalX, jump))
                    {
                        newChild(animalX, jump);
                        _world.addComment(_symbol + " is multiplying on (" + animalX + ", " + jump + ").");
                    }
                    break;
                case 2:
                    jump = animalY + 1;
                    if(!_world.noWall(animalX, animalY - 1)) jump = _world.getWielkoscSwiata() - 1;
                    if (_world.isFree(animalX, jump))
                    {
                        newChild(animalX, jump);
                        _world.addComment(_symbol + " is multiplying on (" + animalX + ", " + jump + ").");
                    }
                    break;
                case 3:
                    jump = animalX - 1;
                    if(!_world.noWall(animalX - 1, animalY)) jump = _world.getWielkoscSwiata() - 1;
                    if (_world.isFree(jump, animalY))
                    {
                        newChild(jump, animalY);
                        _world.addComment(_symbol + " is multiplying on (" + jump + ", " + animalY + ").");
                    }
                    break;
                default:
                    break;
            }
        } else {
            int index = _world.getIndex(animalX, animalY);
            if (_world.getSymbolByIndex(index) == 'R' || _world.getSymbolByIndex(index) == 'V')
            {
                if(_world.getSymbolByIndex(index) == 'R') _world.addComment(_symbol + " eats R on (" + animalX + ", " + animalY + " and dies");
                if(_world.getSymbolByIndex(index) == 'V') _world.addComment(_symbol + " attacks V on (" + animalX + ", " + animalY + " and both die");
                _world.kill(index);
                index = _world.getIndex(_locationX, _locationY);
                _world.kill(index);
            }
            else if (_strength >= _world.getEntityStrength(index))
            {
                _world.addComment(_symbol + " attacks " + _world.getSymbolByIndex(index) + " on (" + animalX + ", " + animalY + ") and kills it");
                _world.kill(index);
                _locationX = animalX;
                _locationY = animalY;
                ;		}
            else
            {
                _world.addComment(_symbol + " attacks " + _world.getSymbolByIndex(index) + " on (" + animalX + ", " + animalY + ") but dies");
                index = _world.getIndex(_locationX, _locationY);
                _world.kill(index);
            }
        }
    }
}
