package model;

public class GameElement {
    float _x, _y;
    int _size;

    public GameElement(float x, float y) {
        _x = x;
        _y = y;
        _size = 1;
    }

    public GameElement(float x, float y, int size) {
        _x = x;
        _y = y;
        _size = size;
    }

    public float getX() {
        return _x;
    }

    public void setX(float x) {
        _x = x;
    }

    public float getY() {
        return _y;
    }

    public void setY(float y) {
        _y = y;
    }

    public int getSize() {
        return _size;
    }
}
