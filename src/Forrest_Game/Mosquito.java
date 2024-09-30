package Forrest_Game;

import java.util.Random;

public class Mosquito extends Animal {
    public Mosquito(int locationX, int locationY, char symbol, World world) {
        super(1, 1, locationX, locationY, symbol, world);
    }

    public void newChild(int x, int y) {
        Entity.newChild(new Mosquito(x, y, 'M', _world));
    }

    public void Action() {
        if (_world.isFrendly(_symbol, _locationX + 1, _locationY))
        {
            _strength++;
            _initiative++;
        }
        if (_world.isFrendly(_symbol, _locationX - 1, _locationY))
        {
            _strength++;
            _initiative++;
        }
        if (_world.isFrendly(_symbol, _locationX, _locationY + 1))
        {
            _strength++;
            _initiative++;
        }
        if (_world.isFrendly(_symbol, _locationX, _locationY - 1))
        {
            _strength++;
            _initiative++;
        }

        int movement = new Random().nextInt(4);
        int jump;

        switch (movement)
        {
            case 0:
                jump = _locationX + 1;
                if (!_world.noWall(_locationX + 1, _locationY)) jump = 0;
                if (_world.isFree(jump, _locationY))
                {
                    _locationX = jump;
                }
                else
                {
                    _world.addComment(_symbol + " moves on (" + jump + ", " + _locationY + ").");
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
        if(_world.isFrendly(_symbol, animalX, animalY)) {
            int nowy = new Random().nextInt(4);
            switch (nowy)
            {
                case 0:
                    if (_world.isFree(animalX + 1, animalY))
                    {
                        _world.addComment(_symbol + " is multiplying on (" + (animalX + 1) + ", " + animalY + ").");
                        newChild(animalX + 1, animalY);
                    }
                    break;
                case 1:
                    if (_world.isFree(animalX, animalY + 1))
                    {
                        _world.addComment(_symbol + " is multiplying on (" + animalX + ", " + (animalY + 1) + ").");
                        newChild(animalX, animalY + 1);
                    }
                    break;
                case 2:
                    if (_world.isFree(animalX, animalY - 1))
                    {
                        _world.addComment(_symbol + " is multiplying on (" + animalX + ", " + (animalY - 1) + ").");
                        newChild(animalX, animalY - 1);
                    }
                    break;
                case 3:
                    if (_world.isFree(animalX - 1, animalY))
                    {
                        _world.addComment(_symbol + " is multiplying on (" + (animalX - 1) + ", " + animalY + ").");
                        newChild(animalX - 1, animalY);
                    }
                    break;
                default:
                    break;
            }
        } else {
            int index = _world.getIndex(animalX, animalY);
            if (_world.getSymbolByIndex(index) == 'R')
            {
                if(_world.getSymbolByIndex(index) == 'R') _world.addComment(_symbol + " eats R on" + animalX + ", " + animalY + " and dies");
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
            }
            else
            {
                int przezycie = new Random().nextInt(4);

                switch (przezycie)
                {
                    case 0:
                        _world.addComment(_symbol + " attacks " + _world.getSymbolByIndex(index) + " on (" + animalX + ", " + animalY + ") but doesn't kill and run away");
                        break;
                    case 1:
                        _world.addComment(_symbol + " attacks " + _world.getSymbolByIndex(index) + " on (" + animalX + ", " + animalY + ") but dies");
                        index = _world.getIndex(_locationX, _locationY);
                        _world.kill(index);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
