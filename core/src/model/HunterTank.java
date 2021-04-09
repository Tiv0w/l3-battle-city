package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
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

            float previousX = _x;
            float previousY = _y;

            setX(_x + moveX * delta);
            setY(_y + moveY * delta);

            if (!isInsideTerrain(terrain) || isColliding(terrain)) {
                int newDirection = RANDOM.nextInt(4);
                _direction = Direction.class.getEnumConstants()[newDirection];
                setX(previousX);
                setY(previousY);
            }
        }
    }

    private boolean isColliding(Terrain terrain) {
        for (GameElement e : terrain.getGrid()) {
            if ((e instanceof StaticGameElement) &&
                !(e instanceof Vegetation) &&
                !(e instanceof Empty) &&
                !(e.equals(this)) &&
                _hitbox.overlaps(e.getHitbox())) {
                return true;
            }
        }
        return false;
    }

    public boolean isInsideTerrain(Terrain terrain) {
        Rectangle terrainRectangle = new Rectangle(0, 1, terrain.getWidth(), terrain.getHeight());
        return terrainRectangle.contains(_hitbox);
    }

    public Projectile shoot() {
        System.out.println("Shooting!");
        return new Projectile(_x + (_size / 2), _y + (_size / 2), _direction);
    }
}
