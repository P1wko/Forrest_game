package Forrest_Game;

import java.util.Random;

public class Beaver extends Animal {
    public Beaver(int locationX, int locationY, char symbol, World world) {
        super(5, 4, locationX, locationY, symbol, world);
    }

    public void newChild(int x, int y) {
        Entity.newChild(new Beaver(x, y, 'B', _world));
    }

    public void Collision(int animalX, int animalY) {
        if (_world.isFrendly(_symbol, animalX, animalY)) {
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
        }
        else
        {
            int index = _world.getIndex(animalX, animalY);
            if (_strength >= _world.getEntityStrength(index))
            {
                if (_world.getSymbolByIndex(index) == 'G' || _world.getSymbolByIndex(index) == 'D' || _world.getSymbolByIndex(index) == 'R')
                {
                    _strength++;
                    _initiative++;
                }
                _world.addComment(_symbol + " eats " + _world.getSymbolByIndex(index) + " on (" + animalX + ", " + animalY + ") and strengthens itself ");
                _world.kill(index);
                _locationX = animalX;
                _locationY = animalY;
            }
            else
            {
                _world.addComment(_symbol + " attacks " + _world.getSymbolByIndex(index) + " on (" + animalX + ", " + animalY + ") but dies");
                index = _world.getIndex(_locationX, _locationY);
                _world.kill(index);
            }
        }
    }
}
