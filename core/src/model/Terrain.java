package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Terrain {
    ArrayList<GameElement> _grid;
    ArrayList<HunterTank> _hunters;
    PlayerTank _playerTank;
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
        for (GameElement elem : _grid) {
            if (elem.getX() == col && elem.getY() == row) {
                return elem;
            }
        }
        return null;
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
            _grid = new ArrayList<GameElement>();
            _hunters = new ArrayList<HunterTank>();

            for (int row = 0; row < _height; row++) {
                String inputLine = reader.nextLine();
                if (inputLine != null) {
                    for (int col = 0; col < _width; col++) {
                        _grid.add(parseElementCharacter(inputLine.charAt(col), col, row));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Le niveau n'a pas pu être chargé.");
            e.printStackTrace();
        }
    }

    private GameElement parseElementCharacter(char elementCharacter, int col, int row) {
        GameElement e;
        switch (elementCharacter) {
        case 'V':
            return new Vegetation(col, row);
        case 'B':
            return new BrickWall(col, row);
        case 'C':
            return new ConcreteWall(col, row);
        case 'P':
            e = new PlayerTank(col, row);
            _playerTank = (PlayerTank) e;
            return e;
            // TODO: Refacto tout ça parce que pas vraiment propre
        case 'H':
            e = new HunterTank(col, row, Direction.UP, 0);
            _hunters.add((HunterTank) e);
            return e;
        case 'h':
            e = new HunterTank(col, row, Direction.DOWN, 0);
            _hunters.add((HunterTank) e);
            return e;
        case 'J':
            e = new HunterTank(col, row, Direction.LEFT, 0);
            _hunters.add((HunterTank) e);
            return e;
        case 'j':
            e = new HunterTank(col, row, Direction.RIGHT, 0);
            _hunters.add((HunterTank) e);
            return e;
        case 'K':
            e = new HunterTank(col, row, Direction.UP, 1);
            _hunters.add((HunterTank) e);
            return e;
        case 'k':
            e = new HunterTank(col, row, Direction.DOWN, 1);
            _hunters.add((HunterTank) e);
            return e;
        case 'L':
            e = new HunterTank(col, row, Direction.LEFT, 1);
            _hunters.add((HunterTank) e);
            return e;
        case 'l':
            e = new HunterTank(col, row, Direction.RIGHT, 1);
            _hunters.add((HunterTank) e);
            return e;
        default:
            return new Empty(col, row);
        }
    }

    public ArrayList<GameElement> getGrid() {
        return _grid;
    }

    public PlayerTank getPlayerTank() {
        return _playerTank;
    }

    public HunterTank[] getHunterTanks() {
        return _hunters.toArray(new HunterTank[2]);
    }

    public void emptyTile(int col, int row) {
        if ((col > 0 || col < _width - 1) || (row > 0 || row < _height - 1)) {
            for (int i = 0; i < _grid.size(); i++) {
                if (_grid.get(i).getX() == col && _grid.get(i).getY() == row) {
                    _grid.set(i, new Empty(col, row));
                }
            }
        }
    }
}
