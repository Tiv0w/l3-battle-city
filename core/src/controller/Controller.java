package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.*;
import view.BattleScreen;
import view.TextureFactory;

public class Controller {
    Terrain _terrain;

    public Controller(int levelNumber) {
        _terrain = new Terrain(levelNumber);
    }

    public Terrain getCurrentTerrain() {
        return _terrain;
    }

    public void render(SpriteBatch batch) {
        System.out.println(Gdx.graphics.getDeltaTime());
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movePlayerTank(-1, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movePlayerTank(1, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            movePlayerTank(0, -1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            movePlayerTank(0, 1);
        }
        draw(batch);
    }

    public void draw(SpriteBatch batch) {
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
                    } else if (e instanceof PlayerTank) {
                        t = TextureFactory.getInstance().getPlayerTank();
                    }

                    // TODO: mieux gérer l'affichage en fonction de la taille de la fenêtre
                    float xValue = e.getX() * BattleScreen.ELEMENT_SIZE;
                    // Needed otherwise it is upside-down
                    float yValue = ((_terrain.getHeight() - e.getSize()) - e.getY()) * BattleScreen.ELEMENT_SIZE;

                    if (t != null) {
                        int elemSize = BattleScreen.ELEMENT_SIZE * e.getSize();
                        batch.draw(t, xValue, yValue, elemSize, elemSize);
                    }
                }
            }
        }
    }

    public void movePlayerTank(int x, int y) {
        PlayerTank playerTank = _terrain.getPlayerTank();
        int currentX = playerTank.getX();
        int currentY = playerTank.getY();
        playerTank.setX(currentX + x);
        playerTank.setY(currentY + y);
        // bucket.x += 200 * Gdx.graphics.getDeltaTime();
    }
}
