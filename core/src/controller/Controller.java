package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.*;
import view.BattleScreen;
import view.TextureFactory;

public class Controller {
    public static final int ELEMENT_SIZE = 16;
    Terrain _terrain;

    public Controller(int levelNumber) {
        _terrain = new Terrain(levelNumber);
    }

    public Terrain getCurrentTerrain() {
        return _terrain;
    }

    public void render(SpriteBatch batch) {
        float delta = Gdx.graphics.getDeltaTime();
        PlayerTank playerTank = _terrain.getPlayerTank();
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerTank.move(-delta, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerTank.move(delta, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            playerTank.move(0, -delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            playerTank.move(0, delta);
        }

        draw(batch);
    }

    public void draw(SpriteBatch batch) {
        drawStaticElements(batch);
        drawPlayerTank(batch);
    }

    private void drawStaticElements(SpriteBatch batch) {
        batch.draw(TextureFactory.getInstance().getBackground(), 0, 0);
        for (int row = 0; row < _terrain.getHeight(); row++) {
            for (int col = 0; col < _terrain.getWidth(); col++) {
                GameElement e = _terrain.getElement(col, row);
                if (!(e instanceof Empty)) {
                    Texture t = null;
                    if (e instanceof BrickWall) {
                        t = TextureFactory.getInstance().getBrickWall();
                    } else if (e instanceof ConcreteWall) {
                        t = TextureFactory.getInstance().getConcreteWall();
                    } else if (e instanceof Vegetation) {
                        t = TextureFactory.getInstance().getVegetation();
                    }

                    float xValue = e.getX() * ELEMENT_SIZE;
                    // Needed otherwise it is upside-down
                    float yValue = ((_terrain.getHeight() - e.getSize()) - e.getY()) * ELEMENT_SIZE;

                    if (t != null) {
                        int elemSize = ELEMENT_SIZE * e.getSize();
                        batch.draw(t, xValue, yValue, elemSize, elemSize);
                    }
                }
            }
        }
    }

    private void drawPlayerTank(SpriteBatch batch) {
        PlayerTank tank = _terrain.getPlayerTank();
        Texture texture = TextureFactory.getInstance().getPlayerTank();

        float xValue = tank.getX() * ELEMENT_SIZE;
        float yValue = (_terrain.getHeight() - tank.getSize() - tank.getY()) * ELEMENT_SIZE;
        int elemSize = ELEMENT_SIZE * tank.getSize();
        int tankRotation;
        switch (tank.getDirection()) {
        case UP:
            tankRotation = 0; break;
        case LEFT:
            tankRotation = 90; break;
        case DOWN:
            tankRotation = 180; break;
        case RIGHT:
            tankRotation = 270; break;
        default:
            tankRotation = 0;
        }

        batch.draw(texture, xValue, yValue, elemSize/2, elemSize/2, elemSize, elemSize, 1, 1, tankRotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
}
