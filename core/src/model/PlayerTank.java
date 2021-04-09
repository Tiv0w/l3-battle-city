package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
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

        float newX = _x + x * _speed;
        float newY = _y + y * _speed;
        setX(newX);
        setY(newY);

        handleCollision(previous_x, previous_y, delta, terrain);
    }

    private void handleCollision(float previous_x, float previous_y, float delta, Terrain terrain) {
        if (!isInsideTerrain(terrain) || isColliding(terrain)) {
            System.out.println("COLLISION DETECTEE");
            setX(previous_x);
            setY(previous_y);
            // System.out.println(_x);
            // System.out.println(_y);
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

    private float roundX(float x) {
        if (_direction == Direction.RIGHT) return (float)Math.round(x - GameElement.TOLERANCE);
        if (_direction == Direction.LEFT) return (float)Math.round(x + GameElement.TOLERANCE);
        return x;
    }

    private float roundY(float y) {
        if (_direction == Direction.UP) return (float)Math.round(y + GameElement.TOLERANCE);
        if (_direction == Direction.DOWN) return (float)Math.round(y - GameElement.TOLERANCE);
        return y;
    }

    public Projectile shoot() {
        System.out.println("Shooting!");
        return new Projectile(_x + (_size / 2), _y + (_size / 2), _direction);
    }
}
