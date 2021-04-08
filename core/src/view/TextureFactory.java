package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.io.File;
import java.util.HashMap;
import model.*;

public class TextureFactory {
    private static HashMap<Class<?>, Texture> _texturesMap;
    private static Texture _background;
    private static TextureFactory _instance;

    private TextureFactory() {
        String _imagesPath = "../assets/Images/";
        _background = new Texture(Gdx.files.internal(_imagesPath + "Fond.png"));

        _texturesMap = new HashMap<Class<?>, Texture>();
        _texturesMap.put(BrickWall.class,
                         new Texture(Gdx.files.internal(_imagesPath + "MurBrique.png")));
        _texturesMap.put(ConcreteWall.class,
                         new Texture(Gdx.files.internal(_imagesPath + "MurRenforce.png")));
        // _texturesMap.put(HunterFlag.class,
        //                  new Texture(Gdx.files.internal(_imagesPath + "ChasseurDrapeau.png")));
        _texturesMap.put(HunterTank.class,
                         new Texture(Gdx.files.internal(_imagesPath + "ChasseurChar.png")));
        // _texturesMap.put(Flag.class,
        //                  new Texture(Gdx.files.internal(_imagesPath + "Drapeau.png")));
        _texturesMap.put(PlayerTank.class,
                         new Texture(Gdx.files.internal(_imagesPath + "CharJoueur.png")));
        _texturesMap.put(Projectile.class,
                         new Texture(Gdx.files.internal(_imagesPath + "Projectile.png")));
        _texturesMap.put(Vegetation.class,
                         new Texture(Gdx.files.internal(_imagesPath + "Arbre.png")));
    }

    public static TextureFactory getInstance() {
        if (_instance == null) {
            _instance = new TextureFactory();
        }
        return _instance;
    }

    public Texture getBackground() {
        return _background;
    }

    public Texture getTexture(GameElement e) {
        return _texturesMap.get(e.getClass());
    }
}
