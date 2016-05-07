package com.game.voitsekh.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.voitsekh.FlappyDemo;
import com.game.voitsekh.sprites.Bird;
import com.game.voitsekh.sprites.Tube;

/**
 * Active state, all the movements take place here. Displays the bird and the pipes.
 * Actually, only the bird moves forward, the pipes are recreated.
 */
public class PlayState extends State {
    //width between tubes
    public static final int TUBE_SPACING = 125;
    public static final int TUBE_COUNT = 4;
    //the ground will appear lower
    public static final int GROUND_Y_OFFSET = - 30;
    private Texture background;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Rectangle groundBounds;

    private Bird bird;
    private Array<Tube> tubes;

    public PlayState(GameStateManager gm) {
        super(gm);
        bird = new Bird(50, 300);
        background = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth(),
                GROUND_Y_OFFSET);
        groundBounds = new Rectangle(groundPos1.x, groundPos1.y, ground.getWidth() * 2, ground.getHeight());

        camera.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        tubes = new Array<Tube>();

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH), gsm));
        }

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        //camera moves with the bird
        camera.position.x = bird.getPosition().x + 80;

        for (int i = 0; i < tubes.size; i++) {
            Tube tube = tubes.get(i);
            //recreate tube
            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + (Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT);
                tube.setScored();
            }

            if (tube.collides(bird.getBounds())) {
                gsm.updateHighScore();
                gsm.resetScore();
                gsm.set(new GameOver(gsm));
            }

            if (groundBounds.overlaps(bird.getBounds())) {
                gsm.updateHighScore();
                gsm.resetScore();
                gsm.set(new GameOver(gsm));
            }
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        String score = String.valueOf(gsm.getScore());
        BitmapFont font = new BitmapFont();
        font.draw(sb, score, camera.position.x, 390);
        sb.end();
    }

    private void updateGround() {
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);

        }
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
            groundBounds.setPosition(groundPos1.x, groundPos1.y);
        }

    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        ground.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }

    }
}
