package view;

import com.badlogic.gdx.Game;

public class BattleGame extends Game {
    @Override
    public void create() {
        setScreen(new BattleScreen(1));
    }

    @Override
    public void render() {
        super.render();
    }
}
