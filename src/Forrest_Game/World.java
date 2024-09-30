package Forrest_Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class World implements ActionListener {

    private JFrame window;
    private JPanel commentator;
    private JPanel board;
    private JPanel legend;
    private JPanel buttons;
    private JButton Turn, Save, Load;

    private int TuraLicznik = 0;
    private static int N = 20;
    private Vector<Entity> organizmy = new Vector<>();
    private int ileZwierzat;
    private boolean wczytano = false;
    private int wczytanaTura = 0;



    public World() {
        window = new JFrame();
        window.setTitle("Michal Pawlowski");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(800, 500);
        window.setLocationRelativeTo(null);

        buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttons.setBackground(Color.BLUE);

        Turn = new JButton("New Turn");
        Turn.addActionListener(this);
        buttons.add(Turn);

        Save = new JButton("Save Game");
        Save.addActionListener(this);
        buttons.add(Save);

        Load = new JButton("Load Game");
        Load.addActionListener(this);
        buttons.add(Load);

        legend = new JPanel();
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));
        legend.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        legend.add(new JLabel("LEGEND:"));
        legend.add(new JLabel("S - Sheep"));
        legend.add(new JLabel("W - Wolf"));
        legend.add(new JLabel("V - Viper"));
        legend.add(new JLabel("M - Mosquito"));
        legend.add(new JLabel("B - Beaver"));
        legend.add(new JLabel("G - Grass"));
        legend.add(new JLabel("R - Wolfberry"));
        legend.add(new JLabel("D - Dendalion"));


        board = new JPanel(new GridLayout(N, N));
        board.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        for(int i = 0; i < N * N; i++) {
            JLabel label = new JLabel(" ");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            board.add(label);
        }

        commentator = new JPanel();
        commentator.setLayout(new BoxLayout(commentator, BoxLayout.Y_AXIS));
        commentator.setPreferredSize(new Dimension(200, 500));
        commentator.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 10));
        JLabel licznikTur = new JLabel("Turn: " + TuraLicznik);
        commentator.add(licznikTur);

        window.add(board, BorderLayout.CENTER);
        window.add(buttons, BorderLayout.NORTH);
        window.add(commentator, BorderLayout.EAST);
        window.add(legend, BorderLayout.WEST);
    }

    public void show() {
        window.setVisible(true);
    }

    public void DrawWorld() {
        window.remove(commentator);
        commentator = new JPanel();
        commentator.setLayout(new BoxLayout(commentator, BoxLayout.Y_AXIS));
        commentator.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 8));
        commentator.setPreferredSize(new Dimension(220, 500));
        if(!wczytano) TuraLicznik++;
        JLabel licznikTur = new JLabel("Tura: " + TuraLicznik);
        commentator.add(licznikTur);

        sort();
        ileZwierzat = organizmy.size();
        if(!wczytano && TuraLicznik != 1) {
            for(int i = 0; i < ileZwierzat; i++) {
                organizmy.get(i).Action();
            }
        }
        wczytano = false;

        window.add(commentator, BorderLayout.EAST);

        window.remove(board);
        board = new JPanel(new GridLayout(N, N));
        board.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        for(int i = 0; i < N * N; i++) {
            JLabel label = new JLabel(" ");
            for(Entity entity : organizmy) {
                if(entity.getLocationY() * N + entity.getLocationX() == i ) {
                   label = new JLabel(String.valueOf(entity.getSymbol()));
                   }
            }
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            int finalI = i;
            if(label.getText() == " ") {
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int x = (finalI % N);
                        int y = (finalI / N);
                        addEntityWindow(x, y);

                    }
                });
            }

            board.add(label);
        }
        window.add(board, BorderLayout.CENTER);

        window.setVisible(true);
    }

    private void addEntityWindow(int x, int y) {
        String[] entitiesNames = {"Sheep", "Wolf", "Fox", "Viper", "Beaver", "Grass", "Wolfberry", "Dendalion"};

        String chosenEntity = (String) JOptionPane.showInputDialog(null, "What entity to add?", "List of entities", JOptionPane.QUESTION_MESSAGE, null, entitiesNames, entitiesNames[0]);


        switch (chosenEntity) {
            case "Sheep":
                addEntity(new Sheep(x, y, 'S', this));
                break;
            case "Wolf":
                addEntity(new Wolf(x, y, 'W', this));
                break;
            case "Fox":
                addEntity(new Fox(x, y, 'F', this));
                break;
            case "Viper":
                addEntity(new Viper(x, y, 'V', this));
                break;
            case "Beaver":
                addEntity(new Beaver(x, y, 'B', this));
                break;
            case "Grass":
                addEntity(new Grass(x, y, 'G', this));
                break;
            case "Wolfberry":
                addEntity(new Wolfberries(x, y, 'R', this));
                break;
            case "Dendalion":
                addEntity(new Dandelion(x, y, 'D', this));
                break;
            default:
                break;
        }
        wczytano = true;
        DrawWorld();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Turn) {
            DrawWorld();
        } else if (e.getSource() == Save) {
            Save();
        } else if (e.getSource() == Load) {
            Load();
        }
    }

    public boolean noWall(int x, int y) {
        if (x < N && y < N && x >= 0 && y >= 0) return true;
        else return false;
    }

    public boolean isFree(int x, int y) {
        for(int i = 0; i < organizmy.size(); i++) {
            if(x == organizmy.get(i).getLocationX() && y == organizmy.get(i).getLocationY()) return false;
        }
        return true;
    }

    public boolean isFrendly(char zwierze1, int x, int y) {
        for(int i = 0; i < organizmy.size(); i++) {
            char symbol = organizmy.get(i).getSymbol();
            if (x == organizmy.get(i).getLocationX() && y == organizmy.get(i).getLocationY() && symbol == zwierze1) return true;
        }
        return false;
    }

    public int getEntityStrength(int index) {
        return organizmy.get(index).getStrenght();
    }

    public void kill(int index) {
        organizmy.remove(index);
        ileZwierzat--;
    }

    public int getIndex(int polozenieX, int polozenieY) {
        int index = 0;
        for(int i = 0; i < organizmy.size(); i++) {
            if(organizmy.get(i).getLocationX() == polozenieX && organizmy.get(i).getLocationY() == polozenieY) return index;
            index++;
        }
        return 0;
    }

    public char getSymbolByIndex(int index) {
        return organizmy.get(index).getSymbol();
    }

    public void addEntity(Entity noweZwierze) {
        organizmy.add(noweZwierze);
    }

    public void addComment(String komentarz) {
        JLabel wiadomosc = new JLabel(komentarz);
        commentator.add(wiadomosc);
    }

    public void sort() {
        Entity tmp;
        for(int i = 0; i < organizmy.size() - 1; i++) {
            for(int j = 0; j < organizmy.size() - i - 1; j++) {
                if(organizmy.get(j).getInitiative() < organizmy.get(j + 1).getInitiative()) {
                    tmp = organizmy.get(j);
                    organizmy.set(j, organizmy.get(j+1));
                    organizmy.set(j+1, tmp);
                } else if (organizmy.get(j).getInitiative() == organizmy.get(j+1).getInitiative()) {
                    if(organizmy.get(j).getAge() < organizmy.get(j+1).getAge()) {
                        tmp = organizmy.get(j);
                        organizmy.set(j, organizmy.get(j+1));
                        organizmy.set(j+1, tmp);
                    }
                }
            }
        }
    }

    public void Save() {
        try {
            wczytanaTura = TuraLicznik;
            FileWriter plik = new FileWriter("Entities.txt");
            int i = 0;
            for(Entity entity : organizmy) {
                plik.write(entity.getSymbol() + " " + entity.getLocationX() + " " + entity.getLocationY() + "\n");
            }
            plik.close();
            System.out.println("World saved succesfully");
        } catch (IOException e) {
            System.out.println("Error saving the world");
        }
    }

    public void Load() {
        try {
            TuraLicznik = wczytanaTura;
            wczytano = true;
            organizmy.clear();
            File plik = new File("Entities.txt");
            Scanner czytanie = new Scanner(plik);
            while (czytanie.hasNext()) {
                String nazwa = czytanie.next();
                char symbol = nazwa.charAt(0);
                int polozenieX = czytanie.nextInt();
                int polozenieY = czytanie.nextInt();

                switch (symbol) {
                    case 'S':
                        addEntity(new Sheep(polozenieX, polozenieY, 'S', this));
                        break;
                    case 'W':
                        addEntity(new Wolf(polozenieX, polozenieY, 'W', this));
                        break;
                    case 'F':
                        addEntity(new Fox(polozenieX, polozenieY, 'F', this));
                        break;
                    case 'V':
                        addEntity(new Viper(polozenieX, polozenieY, 'V', this));
                        break;
                    case 'B':
                        addEntity(new Beaver(polozenieX, polozenieY, 'B', this));
                        break;
                    case 'M':
                        addEntity(new Mosquito(polozenieX, polozenieY, 'M', this));
                        break;
                    case 'G':
                        addEntity(new Grass(polozenieX, polozenieY, 'G', this));
                        break;
                    case 'R':
                        addEntity(new Wolfberries(polozenieX, polozenieY, 'R', this));
                        break;
                    case 'D':
                        addEntity(new Dandelion(polozenieX, polozenieY, 'D', this));
                        break;
                    default:
                        throw new FileNotFoundException("No file found");
                }
            }
            czytanie.close();
            System.out.println("World loaded succesfully");
        } catch (FileNotFoundException e) {
            System.out.println("Error loading the world");
            e.printStackTrace();
        }
        DrawWorld();
    }

    public int getWielkoscSwiata() {
        return N;
    }
}
