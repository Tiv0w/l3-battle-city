package model;

import com.badlogic.gdx.Gdx;
import controller.Controller;

public class PlayerTank extends GameElement {
    float _speed;
    Direction _direction;

    public PlayerTank(float x, float y) {
        super(x, y, 2);
        _speed = 4;
        _direction = Direction.UP;
    }

    public PlayerTank(float x, float y, float speed) {
        super(x, y, 2);
        _speed = speed;
        _direction = Direction.UP;
    }

    public float getSpeed() {
        return _speed;
    }

    public void setSpeed(float speed) {
        _speed = speed;
    }

    public Direction getDirection() {
        return _direction;
    }

    public void move(float x, float y) {
        if (x < 0) _direction = Direction.LEFT;
        else if (x > 0) _direction = Direction.RIGHT;
        else if (y < 0) _direction = Direction.UP;
        else if (y > 0) _direction = Direction.DOWN;

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

    public Projectile shoot() {
        System.out.println("Shooting!");
        return new Projectile(_x + (_size / 2), _y + (_size / 2), _direction);
    }
}
