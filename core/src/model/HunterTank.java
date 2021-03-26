package model;

import com.badlogic.gdx.Gdx;
import controller.Controller;
import java.util.Random;

public class HunterTank extends GameElement {
    public static final int STRATEGY_HUNTING = 0;
    public static final int STRATEGY_RANDOM = 1;
    private final static Random RANDOM = new Random();
    float _speed;
    Direction _direction;
    int _strategy;

    public HunterTank(float x, float y) {
        super(x, y, 2);
        _speed = 2;
        _direction = Direction.UP;
        _strategy = STRATEGY_HUNTING;
    }

    public HunterTank(float x, float y, Direction direction, int strategy) {
        super(x, y, 2);
        _speed = 2;
        _direction = direction;
        _strategy = strategy;
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
        PlayerTank playerTank = terrain.getPlayerTank();
        float previous_x = _x;
        float previous_y = _y;

        if (_strategy == STRATEGY_HUNTING) {
            float xDifference = playerTank.getX() - _x;
            float yDifference = playerTank.getY() - _y;
            if (xDifference > delta) {
                _x += delta * _speed;
                _direction = Direction.RIGHT;
            } else if (xDifference < -delta) {
                _x -= delta * _speed;
                _direction = Direction.LEFT;
            } else if (yDifference > delta) {
                _y += delta * _speed;
                _direction = Direction.DOWN;
            } else if (yDifference < -delta) {
                _y -= delta * _speed;
                _direction = Direction.UP;
            }
        } else if (_strategy == STRATEGY_RANDOM) {
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

            _x += moveX * delta;
            _y += moveY * delta;

            if (isColliding(terrain)) {
                int newDirection = RANDOM.nextInt(4);
                _direction = Direction.class.getEnumConstants()[newDirection];
            }
        }
    }

    private boolean isColliding(Terrain terrain) {
        int col = (int)Math.floor(_x);
        int row = (int)Math.floor(_y);
        GameElement[] e = new GameElement[4];
        e[0] = terrain.getElement(col, row);
        e[1] = terrain.getElement(col + (int)_size, row);
        e[2] = terrain.getElement(col, row + (int)_size);
        e[3] = terrain.getElement(col + (int)_size, row + (int)_size);

        for (GameElement elem : e) {
            if (elem instanceof ConcreteWall || elem instanceof BrickWall) {
                return true;
            }
        }
	// if (col < 0 ||)
        return false;
    }

    public Projectile shoot() {
        System.out.println("Shooting!");
        return new Projectile(_x + (_size / 2), _y + (_size / 2), _direction);
    }
}
