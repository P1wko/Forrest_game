package Forrest_Game;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        World world = new World();
        world.addEntity(new Sheep(7, 9, 'S', world));
        world.addEntity(new Sheep(18, 9, 'S', world));
        world.addEntity(new Sheep(2, 8, 'S', world));

        world.addEntity(new Wolf(12, 9, 'W', world));
        world.addEntity(new Wolf(13, 13, 'W', world));
        world.addEntity(new Wolf(3, 17, 'W', world));

        world.addEntity(new Fox(12, 12, 'F', world));
        world.addEntity(new Fox(15, 16, 'F', world));
        world.addEntity(new Fox(10, 10, 'F', world));

        world.addEntity(new Viper(5, 1, 'V', world));
        world.addEntity(new Viper(19, 19, 'V', world));
        world.addEntity(new Viper(11, 12, 'V', world));

        world.addEntity(new Beaver(12, 16, 'B', world));
        world.addEntity(new Beaver(17, 12, 'B', world));
        world.addEntity(new Beaver(2, 18, 'B', world));

        world.addEntity(new Mosquito(8, 18, 'M', world));
        world.addEntity(new Mosquito(1, 2, 'M', world));
        world.addEntity(new Mosquito(7, 7, 'M', world));

        world.addEntity(new Wolfberries(5, 18, 'R', world));
        world.addEntity(new Wolfberries(18, 11, 'R', world));
        world.addEntity(new Wolfberries(4, 5, 'R', world));

        world.addEntity(new Dandelion(17, 7, 'D', world));
        world.addEntity(new Dandelion(12, 12, 'D', world));
        world.addEntity(new Dandelion(11, 19, 'D', world));

        world.addEntity(new Grass(15, 16, 'G', world));
        world.addEntity(new Grass(16, 3, 'G', world));
        world.addEntity(new Grass(19, 1, 'G', world));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                world.show();
            }
        });


    }
}
