package view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import controller.Controller;

public class BattleScreen implements Screen {
    SpriteBatch _batch;
    Controller _controller;
    public static final int ELEMENT_SIZE = 16; // FIXME: ne plus utiliser une valeur en dur

    public BattleScreen(int levelNumber) {
        _batch = new SpriteBatch();
        _controller = new Controller(levelNumber);
    }

    @Override
    public void render(float delta) {
        _batch.begin();
        _controller.draw(_batch);
        _batch.end();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
