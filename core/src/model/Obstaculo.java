package model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import screen.STATE;

import controller.Controller;

public class Obstaculo {
    private int x;
    private int y;
    private boolean alive = false;
    model.Snake snake;
    controller.Controller controller;
    Viewport viewport;
    private STATE state = STATE.PLAYING;

    public Obstaculo(Snake snake, Controller controller) {
        this.snake = snake;
        this.controller = controller;
        //x = 16;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        x = (((int) viewport.getWorldWidth() / snake.getSIZE())-4)* snake.getSIZE();  //snake.getSIZE()   (16 *4)
        y = (((int) viewport.getWorldHeight()/ snake.getSIZE())-30)* snake.getSIZE();
        shapeRenderer.rect(x, y, snake.getSIZE(), snake.getSIZE());
        shapeRenderer.end();
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public int getX(){
        //System.out.println("HOLA MUNDOzXxx" + x);
        return this.x;
    }

    public int getY(){
        return this.y;
    }

}
