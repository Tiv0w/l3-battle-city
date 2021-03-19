package model;

import com.badlogic.gdx.Gdx;
import controller.Controller;

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

        float maxX = (Gdx.graphics.getWidth() / Controller.ELEMENT_SIZE) - _size;
        float maxY = (Gdx.graphics.getHeight() / Controller.ELEMENT_SIZE) - _size;

        if (_x > maxX) {
            _x = maxX;
        } else if (_x < 0) {
            _x = 0;
        }

        if (_y > maxY) {
            _y = maxY;
        } else if (_y < 0) {
            _y = 0;
        }
    }
}
