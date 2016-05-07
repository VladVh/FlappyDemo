package com.game.voitsekh.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.voitsekh.FlappyDemo;

/**
 * This screen is displayed when the game is lost. Displays the highest score.
 */
public class GameOver extends State {
    private Texture background;
    private Texture gameover;
    private int highScore;
    public GameOver(GameStateManager gm) {
        super(gm);
        camera.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        gameover = new Texture("gameover.png");
        highScore = gm.getHighScore();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(gameover, camera.position.x - gameover.getWidth() / 2, camera.position.y);
        BitmapFont font = new BitmapFont();
        font.draw(sb, "High score : " + highScore, camera.position.x, 100);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameover.dispose();
    }
}
