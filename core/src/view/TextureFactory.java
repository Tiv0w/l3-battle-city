package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.io.File;
import java.util.HashMap;

public class TextureFactory {
    private static String _imagesPath;
    private static enum TextureEnum {
        BACKGROUND,
        BRICK_WALL,
        CONCRETE_WALL,
        VEGETATION,
    }
    private static HashMap<TextureEnum, Texture> _texturesMap;
    private static TextureFactory _instance;

    private TextureFactory() {
        _imagesPath = "../assets/Images/";
        _texturesMap = new HashMap<TextureEnum, Texture>();
        _texturesMap.put(TextureEnum.CONCRETE_WALL,
                         new Texture(Gdx.files.internal(_imagesPath + "MurRenforce.png")));
        _texturesMap.put(TextureEnum.BRICK_WALL,
                         new Texture(Gdx.files.internal(_imagesPath + "MurBrique.png")));
        _texturesMap.put(TextureEnum.BACKGROUND,
                         new Texture(Gdx.files.internal(_imagesPath + "Fond.png")));
        _texturesMap.put(TextureEnum.VEGETATION,
                         new Texture(Gdx.files.internal(_imagesPath + "Arbre.png")));
    }

    private TextureFactory(String imagesPath) {
        _imagesPath = imagesPath;
    }

    public static TextureFactory getInstance() {
        if (_instance == null) {
            _instance = new TextureFactory();
        }
        return _instance;
    }

    public String getImagesPath() {
        return _imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        File imagesDirectory = new File(imagesPath);
        if (imagesDirectory.exists() && imagesDirectory.isDirectory()) {
            _imagesPath = imagesPath;
        }
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

    public Texture getVegetation() {
        return _texturesMap.get(TextureEnum.VEGETATION);
    }
}
