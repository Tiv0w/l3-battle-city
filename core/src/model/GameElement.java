package model;

import com.badlogic.gdx.math.Rectangle;

public class GameElement {
    float _x, _y;
    float _size;
    Rectangle _hitbox;
    public static float TOLERANCE = 0.2f;

    public GameElement(float x, float y) {
        _x = x;
        _y = y;
        _size = 1;
        _hitbox = new Rectangle(x, y + _size, _size, _size);
    }

    public GameElement(float x, float y, float size) {
        _x = x;
        _y = y;
        _size = size;
        _hitbox = new Rectangle(x + TOLERANCE/2,
                                y + _size/2 + TOLERANCE/2,
                                _size - TOLERANCE,
                                _size - TOLERANCE);
    }

    public float getX() {
        return _x;
    }

    public void setX(float x) {
        _x = x;
        _hitbox.setX(x + TOLERANCE/2);
    }

    public float getY() {
        return _y;
    }

    public void setY(float y) {
        _y = y;
        _hitbox.setY(y + _size/2 + TOLERANCE/2);
    }

    public float getSize() {
        return _size;
    }

    public Rectangle getHitbox() {
        return _hitbox;
    }
}
