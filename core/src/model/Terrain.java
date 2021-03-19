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
            System.out.println("L'élément n'existe pas.");
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
        default:
            return new Empty(col, row);
        }
    }
}
