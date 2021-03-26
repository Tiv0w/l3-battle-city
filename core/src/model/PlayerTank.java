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

    public void move(float x, float y, Terrain terrain, float delta) {
        float previous_x = _x;
        float previous_y = _y;
        if (x < 0) _direction = Direction.LEFT;
        else if (x > 0) _direction = Direction.RIGHT;
        else if (y < 0) _direction = Direction.UP;
        else if (y > 0) _direction = Direction.DOWN;

        // float maxX = (Gdx.graphics.getWidth() / Controller.ELEMENT_SIZE) - _size;
        // float maxY = (Gdx.graphics.getHeight() / Controller.ELEMENT_SIZE) - _size;

        handleCollision(x, y, delta, terrain);
    }

    private void handleCollision(float moveX, float moveY, float delta, Terrain terrain) {
        float newX = _x + moveX * _speed;
        float newY = _y + moveY * _speed;
        System.out.println("newX Y " + newX + " " + newY);
        System.out.println("DELTA: " + delta);

        if (!isColliding(newX, newY, terrain)) {
            _x = newX;
            _y = newY;
        } else {
            System.out.println("COLLISION DETECTEE");
            _x = roundX(newX);
            _y = roundY(newY);
            System.out.println(_x);
            System.out.println(_y);
        }
    }

    private boolean isColliding(float newX, float newY, Terrain terrain) {
        int col = (int)Math.floor(newX);
        int row = (int)Math.floor(newY);
        int col2 = (int)Math.floor(newX + 1.8);
        int row2 = (int)Math.floor(newY + 1.8);

        GameElement e1 = terrain.getElement(col, row);
        GameElement e2 = terrain.getElement(col, row);
        GameElement e3 = terrain.getElement(col, row);

        if (_direction == Direction.UP) {
            e1 = terrain.getElement(col, row);
            e2 = terrain.getElement(col + 1, row);
            e3 = terrain.getElement(col2, row);
        } else if (_direction == Direction.DOWN) {
            e1 = terrain.getElement(col, row + (int)_size);
            e2 = terrain.getElement(col + 1, row + (int)_size);
            e3 = terrain.getElement(col2, row + (int)_size);
        } else if (_direction == Direction.RIGHT) {
            e1 = terrain.getElement(col + (int)_size, row);
            e2 = terrain.getElement(col + (int)_size, row + 1);
            e3 = terrain.getElement(col + (int)_size, row2);
        } else if (_direction == Direction.LEFT) {
            e1 = terrain.getElement(col, row);
            e2 = terrain.getElement(col, row + 1);
            e3 = terrain.getElement(col, row2);
        }

        if (e1 instanceof ConcreteWall || e1 instanceof BrickWall ||
            e2 instanceof ConcreteWall || e2 instanceof BrickWall ||
            e3 instanceof ConcreteWall || e3 instanceof BrickWall) {
            return true;
        }
        System.out.println("yes");
        return
            (col < 0 || col > terrain.getWidth() - 1 - _size) ||
            (row < 0 || row > terrain.getHeight() - 1 - _size);
    }

    private float roundX(float x) {
        if (_direction == Direction.RIGHT) return (float)Math.round(x);
        if (_direction == Direction.LEFT) return (float)Math.round(x);
        return x;
    }

    private float roundY(float y) {
        if (_direction == Direction.UP) return (float)Math.round(y);
        if (_direction == Direction.DOWN) return (float)Math.round(y);
        return y;
    }

    private boolean isXCollisionSmall(float x, float delta) {
        float xDifference = (float)Math.abs(Math.floor(x) - x);
        return xDifference < delta;
    }

    private boolean isYCollisionSmall(float y, float delta) {
        float yDifference = (float)Math.abs(Math.floor(y) - y);
        return yDifference < delta;
    }

    public Projectile shoot() {
        System.out.println("Shooting!");
        return new Projectile(_x + (_size / 2), _y + (_size / 2), _direction);
    }
}
