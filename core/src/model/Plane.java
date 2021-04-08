package model;

import com.badlogic.gdx.Gdx;
import controller.Controller;
import java.util.Random;

public class Plane extends GameElement {
    float _speed;
    boolean _isStarted;

    public Plane() {
        super(-10, -10, 5);
        _speed = 3;
        _isStarted = false;
    }

    public void move(float delta) {
        _x += delta * _speed;
        _y = cartesianLine(_x);
    }

    public float cartesianLine(float x) {
        return 3 * x + 2;
    }

    public void startPlane() {
        _x = 0;
        _y = 0;
        _isStarted = true;
    }

    public void stopPlane() {
        _x = -100;
        _y = -100;
        _isStarted = false;
    }

    public boolean isOut() {
        float maxX = (Gdx.graphics.getWidth() / Controller.ELEMENT_SIZE) - _size;
        float maxY = (Gdx.graphics.getHeight() / Controller.ELEMENT_SIZE) - _size;

        return (_x < 0 || _x > maxX) || (_y < 0 || _y > maxY);
    }
}
