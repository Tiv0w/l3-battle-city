package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
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
    ShapeRenderer _shapeRenderer;

    public Controller(int levelNumber) {
        _terrain = new Terrain(levelNumber);
        _projectiles = new ArrayList<Projectile>();
        _shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        handlePlayerTankMoving();
        handleHunterTanksMoving();
        handlePlayerTankShooting();
        handleProjectilesMoving();
        draw(batch);
        cleanProjectiles();
    }

    public void draw(SpriteBatch batch) {
        _shapeRenderer.begin(ShapeType.Line);
        drawStaticElements(batch);
        for (Projectile p: _projectiles) {
            drawProjectile(batch, p);
        }
        drawHunterTanks(batch);
        drawPlayerTank(batch);
        // _shapeRenderer.identity();
        _shapeRenderer.end();
    }

    private void drawStaticElements(SpriteBatch batch) {
        _shapeRenderer.setColor(Color.GREEN);
        batch.draw(TextureFactory.getInstance().getBackground(), 0, 0);
        for (GameElement e : _terrain.getGrid()) {
            if (!(e instanceof Empty)) {
                Texture t = TextureFactory.getInstance().getTexture(e);
                float xValue = e.getX() * ELEMENT_SIZE;
                // Needed otherwise it is upside-down
                float yValue = ((_terrain.getHeight() - e.getSize()) - e.getY()) * ELEMENT_SIZE;
                float elemSize = ELEMENT_SIZE * e.getSize();
                batch.draw(t, xValue, yValue, elemSize, elemSize);
                Rectangle hitbox = e.getHitbox();
                if (e instanceof Vegetation) {
                    _shapeRenderer.setColor(Color.GREEN);
                } else {
                    _shapeRenderer.setColor(Color.BLUE);
                }
                _shapeRenderer.rect(
                    hitbox.getX() * ELEMENT_SIZE,
                    (_terrain.getHeight() - hitbox.getY()) * ELEMENT_SIZE,
                    hitbox.getWidth() * ELEMENT_SIZE,
                    hitbox.getHeight() * ELEMENT_SIZE);
            }
        }
    }

    private void drawPlayerTank(SpriteBatch batch) {
        PlayerTank tank = _terrain.getPlayerTank();
        Texture texture = TextureFactory.getInstance().getTexture(tank);

        _shapeRenderer.setColor(Color.RED);
        Rectangle hitbox = tank.getHitbox();
        _shapeRenderer.rect(
            hitbox.getX() * ELEMENT_SIZE,
            (_terrain.getHeight() - hitbox.getY() - 1) * ELEMENT_SIZE,
            hitbox.getWidth() * ELEMENT_SIZE,
            hitbox.getHeight() * ELEMENT_SIZE);

        float xValue = tank.getX() * ELEMENT_SIZE;
        float yValue = (_terrain.getHeight() - tank.getSize() - tank.getY()) * ELEMENT_SIZE;
        float elemSize = ELEMENT_SIZE * tank.getSize();
        int tankRotation = 0;
        switch (tank.getDirection()) {
        case UP:
            tankRotation = 0; break;
        case LEFT:
            tankRotation = 90; break;
        case DOWN:
            tankRotation = 180; break;
        case RIGHT:
            tankRotation = 270; break;
        }

        drawWithParameters(batch, texture, xValue, yValue, elemSize, tankRotation);
    }

    private void drawHunterTanks(SpriteBatch batch) {
        HunterTank[] tanks = _terrain.getHunterTanks();
        Texture texture = TextureFactory.getInstance().getTexture(tanks[0]);

        for (HunterTank tank : tanks) {
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
    }

    private void drawProjectile(SpriteBatch batch, Projectile p) {
        Texture texture = TextureFactory.getInstance().getTexture(p);

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
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
            Gdx.input.isKeyPressed(Input.Keys.J) ||
            Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerTank.move(-delta, 0, _terrain, delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
            Gdx.input.isKeyPressed(Input.Keys.L) ||
            Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerTank.move(delta, 0, _terrain, delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) ||
            Gdx.input.isKeyPressed(Input.Keys.I) ||
            Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerTank.move(0, -delta, _terrain, delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
            Gdx.input.isKeyPressed(Input.Keys.K) ||
            Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerTank.move(0, delta, _terrain, delta);
        }
    }

    private void handleHunterTanksMoving() {
        float delta = Gdx.graphics.getDeltaTime();
        HunterTank[] hunterTanks = _terrain.getHunterTanks();
        for (HunterTank tank : hunterTanks) {
            tank.move(delta, _terrain);
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
            p.move(Gdx.graphics.getDeltaTime(), _terrain);
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
