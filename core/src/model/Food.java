package model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import screen.STATE;

import controller.Controller;

public class Food {
    private int x;
    private int y;
    private boolean alive = false;
    model.Snake snake;
    Viewport viewport;
    //Camera camera;
    controller.Controller controller;

    private STATE state = STATE.PLAYING;

    public Food(Snake snake, Controller controller) {
        this.snake = snake;
        this.controller = controller;
    }

    public void updatePosition () {
        boolean covered;   //tapado
        if (!alive) {   //vida
            do {
                covered = false;
                x = MathUtils.random(((int) viewport.getWorldWidth() / snake.getSIZE()) - 1) * snake.getSIZE(); //calibracion de oobjeto
                y = MathUtils.random(((int) viewport.getWorldHeight() / snake.getSIZE()) - 1) * snake.getSIZE();
                if (x == snake.getX() || y == snake.getY()){
                    covered = true;
                }
                alive = true;
            } while (covered);
        }
    }

    public void checkFoodCollision() {
        if (alive && x == snake.getX() && y == snake.getY()){
            snake.createBodyPart(snake.getX(), snake.getY());
            controller.increaseScore();
            if (controller.getScore() > 20) {   /////////2  LEVEL 10
                controller.increaseLevel();
                state = STATE.WIN;
            }
            alive = false;
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x, y, snake.getSIZE(), snake.getSIZE());
        shapeRenderer.end();
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public void  reset() {
        alive = false;
    }
/*
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

 */
}
