package com.game.voitsekh.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.voitsekh.states.GameStateManager;

import java.util.Random;

/**
 * This is a bottom and a top tube together, because of their position on the screen.
 * The method 'collides' checks whether the bird intersects one of the tubes
 */
public class Tube {
    public static final int TUBE_WIDTH = 52;
    public static final int FLUCTUATION = 130;
    //height
    public static final int TUBE_GAP = 100;
    public static final int LOWEST_OPENING = 120;
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBottomTube;
    private Random rand;
    private Rectangle boundsTop, boundsBot;
    private GameStateManager gsm;
    private boolean isScored = false;

    public Tube(float x, GameStateManager gsm) {
        this.gsm = gsm;

        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBottomTube.x, posBottomTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    public void reposition(float x) {
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBottomTube.x, posBottomTube.y);
    }

    public boolean collides(Rectangle player) {
        if (boundsBot.x + bottomTube.getWidth() / 2 < player.x + player.getWidth() / 2 && !isScored) {
            gsm.addScore();
            isScored = true;
        }
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBottomTube() {
        return posBottomTube;
    }

    public void setScored() {
        isScored = false;
    }
    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }
}
