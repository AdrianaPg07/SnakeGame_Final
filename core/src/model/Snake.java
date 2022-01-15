package model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import screen.STATE;

import controller.Controller;
import controller.DIRECTION;

public class Snake {
    //private int size;
    //private int speed;
    //private int step;
    private DIRECTION direction = DIRECTION.RIGHT;
    private int x;
    private int y;
    //private  int size;  //
    //private  int bodySize = 0;
    private  int xBeforeMove;
    private  int yBeforeMove;
    private float timer;
    private final float MOVE_TIME;
    private final int SIZE;
    private final int STEP;
    private STATE state = STATE.PLAYING;
    //private boolean direciionSet = false;  //a
    private Array<BodyPart> bodyParts = new Array<>();
    Viewport viewport;
    //Camera camera;
    controller.Controller controller;

    public Snake(int size, int speed) {
        MOVE_TIME = 1f / speed;
        SIZE = 16 * size;   //or 32
        STEP = SIZE;
        this.timer = MOVE_TIME;
        controller = new Controller();  ///aa
        //System.out.println("HOLA" +controller.getLife());
    }

    private void move(DIRECTION direction){
        this.xBeforeMove = this.x;
        this.yBeforeMove = this.y;
        switch (direction){
            case RIGHT: {
                this.x += STEP;
                return;
            }
            case LEFT: {
                this.x -= STEP;
                return;
            }
            case UP: {
                this.y += STEP;
                return;
            }
            case DOWN: {
                this.y -= STEP;
                return;
            }
        }
    }

    public STATE update(float delta){
        timer -= delta;
        if (timer <= 0) {
            timer = MOVE_TIME;
            move(direction);
            checkOutOfBounds();
            updateBodyParts();  //actualizar cuerpo tamaño  MIN 4:15  //a
            checkBodyCollision();
            //direciionSet = false;  //a
        }
        return state;
    }

    public void updateDirection(controller.DIRECTION newDirection){
        if (this.direction != newDirection){  //a
            //direciionSet = true;  //a
            switch (newDirection) {
                case RIGHT: {
                    //this.direction = direction;
                    updateIfNotOpposite(newDirection, DIRECTION.LEFT);
                }
                break;
                case LEFT: {
                    //this.direction = direction;
                    updateIfNotOpposite(newDirection, DIRECTION.RIGHT);
                }
                break;
                case UP: {
                    //this.direction = direction;
                    updateIfNotOpposite(newDirection, DIRECTION.DOWN);
                }
                break;
                case DOWN: {
                    //this.direction = direction;
                    updateIfNotOpposite(newDirection, DIRECTION.UP);
                }
                break;
            }
        }

    }

    private void checkOutOfBounds() { //Limites
        if (x >= viewport.getWorldWidth()) {
            x = 0; //SIZE / 2;
        }
        if (x < 0) {
            x = Math.round(viewport.getWorldWidth()) - STEP; // - SIZE / 2;
        }
        if (y >= viewport.getWorldHeight()) {
            y = 0;
        }
        if (y < 0) {
            y = Math.round(viewport.getWorldHeight()) - STEP;
        }
    }

    public void checkBodyCollision(){  //STATE

        for (BodyPart bodyPart : bodyParts) {
            if (bodyPart.x == this.x && bodyPart.y == this.y) {  // && bodyParts.size > 2
                if (controller.getLife() > 0) {
                    state = STATE.PLAYING;
                    controller.decreaseLife();
                }else{
                    state = STATE.GAME_OVER;
                }

            }
        }
    }
    public int getLife(){
        return controller.getLife();
    }

    //direccion Opuesto if
    private void updateIfNotOpposite(controller.DIRECTION newDirection, controller.DIRECTION oppositeDirection) {
        if (this.direction != oppositeDirection || bodyParts.size == 0) {  //size
            this.direction = newDirection;
        }
    }

    public void draw(ShapeRenderer shapeRenderer){
        drawHead(shapeRenderer);
    }

    private void drawHead(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.rect(x, y, SIZE, SIZE);
        shapeRenderer.end();
    }

    public void setDirection(controller.DIRECTION direction) {
        this.direction = direction;
    }

    public int getSIZE(){
        return this.SIZE;
    }

    public int getSTEP(){
        return this.STEP;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    //
    public void createBodyPart(int x, int y) {
        BodyPart bodyPart = new BodyPart();
        bodyPart.updateBodyPart(x, y);
        bodyParts.insert(0, bodyPart);
        //bodyParts.add(bodyPart);
    }

    public void drawBodyParts(ShapeRenderer shapeRenderer) {
        for (BodyPart bodyPart : bodyParts) {
            bodyPart.draw(shapeRenderer);  // dibuja objeto    ///////
        }
    }

    public void updateBodyParts() {   //------------
        if (bodyParts.size > 0) {
            BodyPart bodyPart = bodyParts.removeIndex(0);
            bodyPart.updateBodyPart(xBeforeMove, yBeforeMove);
            bodyParts.add(bodyPart);
        }
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public void reset() {
        x = 0;
        y = 0;
        xBeforeMove = 0;
        yBeforeMove = 0;
        bodyParts.clear();
        timer = MOVE_TIME;
        direction = DIRECTION.RIGHT;
        state = STATE.PLAYING;  //...
    }

    //class
    private class BodyPart {
        int x;
        int y;
        /*
        BodyPart() {

        }*/

        public void updateBodyPart(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw(ShapeRenderer shapeRenderer) {
            if (!(this.x == Snake.this.x && this.y == Snake.this.y)) {
                //shapeRenderer.setProjectionMatrix(camera.projection);  //////
                //shapeRenderer.setTransformMatrix(camera.view);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.GOLD);
                shapeRenderer.rect(this.x, this.y, Snake.this.SIZE, Snake.this.SIZE);  //MIN 4
                shapeRenderer.end();
            }
        }
    }
}
