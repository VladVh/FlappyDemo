package com.game.voitsekh.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * This is the game manager, it changes the screen according to the game state.
 */
public class GameStateManager {
    private Stack<State> states;
    private int score = 0;
    private Preferences prefs;

    public GameStateManager() {
        states = new Stack<State>();
        prefs = Gdx.app.getPreferences("FlappyBird");
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop().dispose();
    }

    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    //update only needed window
    public void update(float dt) {
        states.peek().update(dt);
    }

    public void addScore() {
        score++;
    }

    public int getScore() {
        return score;
    }
    public void resetScore() {
        score = 0;
    }

    public void updateHighScore() {
        int old = getHighScore();
        if (old < score) {
            prefs.putInteger("highScore", score);
            prefs.flush();
        }
    }

    public  int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
