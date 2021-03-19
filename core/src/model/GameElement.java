package model;

public class GameElement {
    int _x, _y, _size;

    public GameElement(int x, int y) {
        _x = x;
        _y = y;
        _size = 1;
    }

    public GameElement(int x, int y, int size) {
        _x = x;
        _y = y;
        _size = size;
    }

    public int getX() {
        return _x;
    }

    public void setX(int x) {
        _x = x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int y) {
        _y = y;
    }

    public int getSize() {
        return _size;
    }
}
