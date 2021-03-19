package model;

import com.badlogic.gdx.Gdx;
import controller.Controller;

public class Projectile extends GameElement {
    Direction _direction;
    float _speed;

    public Projectile(float x, float y, Direction direction) {
        super(x, y);
        _direction = direction;
        _speed = 0.3f;
        _x -= _size / 2;
        _y -= _size / 2;
    }

    public Projectile(float x, float y, Direction direction, float speed) {
        super(x, y);
        _direction = direction;
        _speed = speed;
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

    public void move(float delta) {
        float moveX = 0;
        float moveY = 0;
        if (_direction == Direction.LEFT) {
            moveX = -_speed;
        } else if (_direction == Direction.RIGHT) {
            moveX = _speed;
        } else if (_direction == Direction.UP) {
            moveY = -_speed;
        } else if (_direction == Direction.DOWN) {
            moveY = _speed;
        }

        _x += moveX;
        _y += moveY;
    }

    public boolean isOut() {
        float maxX = (Gdx.graphics.getWidth() / Controller.ELEMENT_SIZE) - _size;
        float maxY = (Gdx.graphics.getHeight() / Controller.ELEMENT_SIZE) - _size;

        return (_x < 0 || _x > maxX) || (_y < 0 || _y > maxY);
    }
}
