package com.game.voitsekh.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Class is responsible for the bird movement on the screen
 */
public class Bird {
    public static final int MOVEMENT = 100;
    public static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Sound flap;
    private Texture texture;
    private Animation birdAnimation;

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);

        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
    }

    public void update(float dt) {
        birdAnimation.update(dt);
        if (position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        if (position.y < 0) {
            position.y = 0;
        }
        //speed change with the time
        velocity.scl(1 / dt);

        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public void jump() {
        velocity.y = 200;
        flap.play();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
        flap.dispose();
    }
}
