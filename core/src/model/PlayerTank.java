package model;

public class PlayerTank extends GameElement {
    float _speed;

    public PlayerTank(float x, float y) {
        super(x, y, 2);
        _speed = 4;
    }

    public PlayerTank(float x, float y, float speed) {
        super(x, y, 2);
        _speed = speed;
    }

    public float getSpeed() {
        return _speed;
    }

    public void setSpeed(float speed) {
        _speed = speed;
    }

    public void move(float x, float y) {
        _x += x * _speed;
        _y += y * _speed;
    }
}
