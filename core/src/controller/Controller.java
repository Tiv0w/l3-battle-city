package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import java.util.ArrayList;
import model.*;
import view.BattleScreen;
import view.TextureFactory;


public class Controller {
    public static final int ELEMENT_SIZE = 16;
    public static final int PROJECTILES_MAX = 3;
    Terrain _terrain;
    boolean _playerShooting = false;
    List<Projectile> _projectiles;

    public Controller(int levelNumber) {
        _terrain = new Terrain(levelNumber);
        _projectiles = new ArrayList<Projectile>();
    }

    public void render(SpriteBatch batch) {
        handlePlayerTankMoving();
        handlePlayerTankShooting();
        handleProjectilesMoving();
        draw(batch);
        cleanProjectiles();
    }

    public void draw(SpriteBatch batch) {
        drawStaticElements(batch);
        for (Projectile p: _projectiles) {
            drawProjectile(batch, p);
        }
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
                        float elemSize = ELEMENT_SIZE * e.getSize();
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
        float elemSize = ELEMENT_SIZE * tank.getSize();
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

        drawWithParameters(batch, texture, xValue, yValue, elemSize, tankRotation);
    }

    private void drawProjectile(SpriteBatch batch, Projectile p) {
        Texture texture = TextureFactory.getInstance().getProjectile();

        float xValue = p.getX() * ELEMENT_SIZE;
        float yValue = (_terrain.getHeight() - p.getSize() - p.getY()) * ELEMENT_SIZE;
        float elemSize = ELEMENT_SIZE * p.getSize();
        int projectileRotation;
        switch (p.getDirection()) {
        case DOWN:
            projectileRotation = 0; break;
        case RIGHT:
            projectileRotation = 90; break;
        case UP:
            projectileRotation = 180; break;
        case LEFT:
            projectileRotation = 270; break;
        default:
            projectileRotation = 0;
        }

        drawWithParameters(batch, texture, xValue, yValue, elemSize, projectileRotation);
    }

    private void handlePlayerTankMoving() {
        float delta = Gdx.graphics.getDeltaTime();
        PlayerTank playerTank = _terrain.getPlayerTank();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerTank.move(-delta, 0, _terrain);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerTank.move(delta, 0, _terrain);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            playerTank.move(0, -delta, _terrain);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            playerTank.move(0, delta, _terrain);
        }
    }

    private void handlePlayerTankShooting() {
        PlayerTank playerTank = _terrain.getPlayerTank();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !_playerShooting) {
            if (_projectiles.size() < PROJECTILES_MAX) {
                _projectiles.add(playerTank.shoot());
            }
            _playerShooting = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            _playerShooting = false;
        }
    }

    private void handleProjectilesMoving() {
        for (Projectile p: _projectiles) {
            p.move(Gdx.graphics.getDeltaTime());
        }
    }

    private void cleanProjectiles() {
        for (int i = 0; i < _projectiles.size(); i++) {
            if (_projectiles.get(i).isOut())
                _projectiles.remove(i);
        }
    }

    private void drawWithParameters(SpriteBatch batch, Texture texture, float xValue, float yValue, float elemSize, int rotation) {
	batch.draw(texture, xValue, yValue, elemSize/2, elemSize/2, elemSize, elemSize, 1, 1, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

}
