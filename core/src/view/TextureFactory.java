package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.io.File;
import java.util.HashMap;

public class TextureFactory {
    private static enum TextureEnum {
        BACKGROUND,
        BRICK_WALL,
        CONCRETE_WALL,
        HUNTER_FLAG,
        HUNTER_TANK,
        PLANE,
        PLAYER_FLAG,
        PLAYER_TANK,
        PROJECTILE,
        VEGETATION,
    }
    private static HashMap<TextureEnum, Texture> _texturesMap;
    private static TextureFactory _instance;

    private TextureFactory() {
        String _imagesPath = "../assets/Images/";
        _texturesMap = new HashMap<TextureEnum, Texture>();
        _texturesMap.put(TextureEnum.BACKGROUND,
                         new Texture(Gdx.files.internal(_imagesPath + "Fond.png")));
        _texturesMap.put(TextureEnum.BRICK_WALL,
                         new Texture(Gdx.files.internal(_imagesPath + "MurBrique.png")));
        _texturesMap.put(TextureEnum.CONCRETE_WALL,
                         new Texture(Gdx.files.internal(_imagesPath + "MurRenforce.png")));
        _texturesMap.put(TextureEnum.HUNTER_FLAG,
                         new Texture(Gdx.files.internal(_imagesPath + "ChasseurDrapeau.png")));
        _texturesMap.put(TextureEnum.HUNTER_TANK,
                         new Texture(Gdx.files.internal(_imagesPath + "ChasseurChar.png")));
        _texturesMap.put(TextureEnum.PLANE,
                         new Texture(Gdx.files.internal(_imagesPath + "Avion.png")));
        _texturesMap.put(TextureEnum.PLAYER_FLAG,
                         new Texture(Gdx.files.internal(_imagesPath + "Drapeau.png")));
        _texturesMap.put(TextureEnum.PLAYER_TANK,
                         new Texture(Gdx.files.internal(_imagesPath + "CharJoueur.png")));
        _texturesMap.put(TextureEnum.PROJECTILE,
                         new Texture(Gdx.files.internal(_imagesPath + "Projectile.png")));
        _texturesMap.put(TextureEnum.VEGETATION,
                         new Texture(Gdx.files.internal(_imagesPath + "Arbre.png")));
    }

    public static TextureFactory getInstance() {
        if (_instance == null) {
            _instance = new TextureFactory();
        }
        return _instance;
    }

    public Texture getBackground() {
        return _texturesMap.get(TextureEnum.BACKGROUND);
    }

    public Texture getBrickWall() {
        return _texturesMap.get(TextureEnum.BRICK_WALL);
    }

    public Texture getConcreteWall() {
        return _texturesMap.get(TextureEnum.CONCRETE_WALL);
    }

    public Texture getHunterFlag() {
        return _texturesMap.get(TextureEnum.HUNTER_FLAG);
    }

    public Texture getHunterTank() {
        return _texturesMap.get(TextureEnum.HUNTER_TANK);
    }

    public Texture getPlane() {
        return _texturesMap.get(TextureEnum.PLANE);
    }

    public Texture getPlayerFlag() {
        return _texturesMap.get(TextureEnum.PLAYER_FLAG);
    }

    public Texture getPlayerTank() {
        return _texturesMap.get(TextureEnum.PLAYER_TANK);
    }

    public Texture getProjectile() {
        return _texturesMap.get(TextureEnum.PROJECTILE);
    }

    public Texture getVegetation() {
        return _texturesMap.get(TextureEnum.VEGETATION);
    }
}
