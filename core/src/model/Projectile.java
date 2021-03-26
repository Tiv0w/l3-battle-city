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

    public void move(float delta, Terrain terrain) {
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
        handleCollision(terrain);
    }

    private void handleCollision(Terrain terrain) {
        int col = (int)Math.floor(_x);
        int row = (int)Math.floor(_y);
        GameElement[] e = new GameElement[4];
        e[0] = terrain.getElement(col, row);
        e[1] = terrain.getElement(col + (int)_size, row);
        e[2] = terrain.getElement(col, row + (int)_size);
        e[3] = terrain.getElement(col + (int)_size, row + (int)_size);

        for (GameElement elem : e) {
            if (elem instanceof ConcreteWall) {
                _x = -1;
                _y = -1;
            } else if (elem instanceof BrickWall) {
                terrain.emptyTile((int)elem.getX(), (int)elem.getY());
                _x = -1;
                _y = -1;
            }
        }
    }

    public boolean isOut() {
        float maxX = (Gdx.graphics.getWidth() / Controller.ELEMENT_SIZE) - _size;
        float maxY = (Gdx.graphics.getHeight() / Controller.ELEMENT_SIZE) - _size;

        return (_x < 0 || _x > maxX) || (_y < 0 || _y > maxY);
    }
}
