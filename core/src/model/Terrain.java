package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Terrain {
    public GameElement[][] _grid;
    int _width, _height;

    public Terrain(int levelNumber) {
        loadTerrainFromFile(levelNumber);
    }

    public int getHeight() {
        return _height;
    }

    public int getWidth() {
        return _width;
    }

    public GameElement getElement(int col, int row) {
        if ((col < 0 || col > _width - 1) || (row < 0 || row > _height - 1)) {
            return null;
        }
        return _grid[row][col];
    }

    public void changeTerrain(int levelNumber) {
        loadTerrainFromFile(levelNumber);
    }

    private void loadTerrainFromFile(int levelNumber) {
        String levelFilename = "level" + levelNumber + ".txt";
        File file = new File("../assets/Levels/" + levelFilename);
        try {
            Scanner reader = new Scanner(file);
            String[] levelParameters = reader.nextLine().split(":");
            _width = Integer.parseInt(levelParameters[0]);
            _height = Integer.parseInt(levelParameters[1]);
            _grid = new GameElement[_height][_width];

            for (int row = 0; row < _height; row++) {
                String inputLine = reader.nextLine();
                if (inputLine != null) {
                    for (int col = 0; col < _width; col++) {
                        _grid[row][col] = parseElementCharacter(inputLine.charAt(col), col, row);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Le niveau n'a pas pu être chargé.");
            e.printStackTrace();
        }
    }

    private GameElement parseElementCharacter(char elementCharacter, int col, int row) {
        switch (elementCharacter) {
        case 'V':
            return new Vegetation(col, row);
        case 'B':
            return new BrickWall(col, row);
        case 'C':
            return new ConcreteWall(col, row);
        case 'P':
            return new PlayerTank(col, row);
            // TODO: Refacto tout ça parce que pas vraiment propre
        case 'H':
            return new HunterTank(col, row, Direction.UP, 0);
        case 'h':
            return new HunterTank(col, row, Direction.DOWN, 0);
        case 'J':
            return new HunterTank(col, row, Direction.LEFT, 0);
        case 'j':
            return new HunterTank(col, row, Direction.RIGHT, 0);
        case 'K':
            return new HunterTank(col, row, Direction.UP, 1);
        case 'k':
            return new HunterTank(col, row, Direction.DOWN, 1);
        case 'L':
            return new HunterTank(col, row, Direction.LEFT, 1);
        case 'l':
            return new HunterTank(col, row, Direction.RIGHT, 1);
        default:
            return new Empty(col, row);
        }
    }

    public PlayerTank getPlayerTank() {
        for (int row = 0; row < _height; row++) {
            for (int col = 0; col < _width; col++) {
                GameElement elem = getElement(col, row);
                if (elem instanceof PlayerTank) {
                    PlayerTank tank = (PlayerTank)elem;
                    return tank;
                }
            }
        }
        return null;
    }

    public HunterTank[] getHunterTanks() {
        HunterTank[] tanks = new HunterTank[2];
        int index = 0;
        for (int row = 0; row < _height; row++) {
            for (int col = 0; col < _width; col++) {
                GameElement elem = getElement(col, row);
                if (elem instanceof HunterTank) {
                    HunterTank tank = (HunterTank)elem;
                    tanks[index] = tank;
                    index++;
                }
            }
        }
        return tanks;
    }

    public void emptyTile(int col, int row) {
        if ((col > 0 || col < _width - 1) || (row > 0 || row < _height - 1)) {
            _grid[row][col] = new Empty(col, row);
        }
    }
}
