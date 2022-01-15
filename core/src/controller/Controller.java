package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Controller {
    //private STATE state = STATE.PLAYING;  //A
    private int score;
    private int level;
    private int life = 2;
    //private STATE state;
    //GlyphLayout layout;


    public DIRECTION queryInput() {
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        if (rightPressed)
            return  DIRECTION.RIGHT;
        if (leftPressed)
            return  DIRECTION.LEFT;
        if (upPressed)
            return  DIRECTION.UP;
        if (downPressed)
            return  DIRECTION.DOWN;
        return DIRECTION.NONE;
    }



    public void increaseScore(){
        this.score++;
    }

    public void resetScore() {
        this.score = 0;  //vvv
    }

    public int getScore() {
        return this.score;
    }

    /////
    public void increaseLevel(){
        this.level++;
    }

    public void resetLevel() {
        this.level = 0;  //vvv
    }

    public int getLevel() {
        return this.level;
    }
    /////
    public void decreaseLife(){
        this.life--;
    }

    public void resetLife() {
        this.life = 3;  //vvv
    }

    public int getLife() {
        return this.life;
    }


}
