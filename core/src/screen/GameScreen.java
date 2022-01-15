package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import controller.Controller;
import model.Food;
import model.Snake;

public class GameScreen extends ScreenAdapter {
    private GlyphLayout layout;
    private BitmapFont bitmapFont;
    private Batch batch;
    ShapeRenderer shapeRenderer;
    Camera camera;
    Viewport viewport;

    model.Snake snake;
    model.Food food;  //
    controller.Controller controller;

    private STATE state = STATE.PLAYING;  //a

    private static final int SNAKE_SIZE = 16;
    private static final int SNAKE_STEP = SNAKE_SIZE;
    private static final int SNAKE_SPEED = 6;
    private static final int WORLD_WIDTH = 640;  //A
    private static final int WORLD_HEIGHT = 480;  //A
    private static final String GAME_OVER_TEXT = "Game Over... Press space!!";
    private static final String GAME_WIN_TEXT = "Pasaste a nuevo nivel... Press Right!!";

    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //a
        camera.position.set(WORLD_WIDTH/2 , WORLD_HEIGHT / 2, 0);
        camera.update(); //A
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera); //a
        batch = new SpriteBatch();
        layout = new GlyphLayout();
        bitmapFont = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        controller = new Controller();  ///aa
        snake = new Snake(1, SNAKE_SPEED);  //4   6
        //snake.setCamera(camera);
        //controller = new Controller();
        food = new Food(snake, controller);  //
        //food.setCamera(camera);
        snake.setViewport(viewport);  //
        food.setViewport(viewport);  //a

    }

    @Override
    public void render(float delta) {
        switch (state) {
            //queryInput();
            case PLAYING: {
                snake.updateDirection(controller.queryInput());  //
                state = snake.update(delta);
                food.updatePosition(); //
                food.checkFoodCollision();  //llamar a clase food
                //state = snake.checkBodyCollision();
            }
            break;
            case GAME_OVER : {
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                    restart();
                }
            }
            break;
            case WIN : {
                if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
                    newLevel();
                }
            }
            break;

        }
        clearScreen();
        drawScreen();   ////////////

    }

    @Override
    public void resize(int width, int height){
        super.resize(width,height);
        viewport.update(width, height);
    }

    public void restart() {
        state = STATE.PLAYING;
        food.reset();
        snake.reset();
        controller.resetScore();
    }
    public void newLevel() {  ////2
        state = STATE.PLAYING;
        food.reset();
        snake.reset();
        controller.resetScore();
    }

    private void drawScreen(){
        batch.setProjectionMatrix(camera.projection);  //a
        batch.setTransformMatrix(camera.view);
        batch.begin();
        //snake.drawHead(batch, shapeRenderer);
        snake.draw(shapeRenderer);
        snake.drawBodyParts(shapeRenderer);  // llama clase...   ///////////
        food.draw(shapeRenderer);
        drawGrid(); //A  DIBUJAR CUADRICULA
        batch.end();
        batch.begin();
        if (state == STATE.GAME_OVER){
            layout.setText(bitmapFont, GAME_OVER_TEXT);
            bitmapFont.draw(batch, GAME_OVER_TEXT, viewport.getWorldWidth() / 2 - layout.width / 2, viewport.getWorldHeight() - layout.height / 2);
        }
        if (state == STATE.WIN){
            layout.setText(bitmapFont, GAME_WIN_TEXT);
            bitmapFont.draw(batch, GAME_WIN_TEXT, viewport.getWorldWidth() / 2 - layout.width / 2, viewport.getWorldHeight() - layout.height / 2);
        }
        drawScore();
        drawLevel();
        drawLife();  //
        batch.end();
    }

    public void drawGrid() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.DARK_GRAY);
        for (int i = 0; i < viewport.getWorldWidth(); i+= SNAKE_SIZE) {
            for (int j = 0; j < viewport.getWorldHeight(); j+= SNAKE_SIZE) {
                shapeRenderer.rect(i, j, SNAKE_SIZE, SNAKE_SIZE);
            }
        }
        shapeRenderer.end();
    }

    public void drawScore() {
        if (state == STATE.PLAYING) {
            String scoreToString = Integer.toString(controller.getScore());
            layout.setText(bitmapFont, scoreToString);
            bitmapFont.draw(batch, "Puntaje: " + scoreToString, layout.width / 2, viewport.getWorldHeight() - layout.height /2);
        }
    }

    public void drawLevel() {
        if (state == STATE.PLAYING) {
            String levelToString = Integer.toString(controller.getLevel());
            layout.setText(bitmapFont, levelToString);
            bitmapFont.draw(batch, "Level " + levelToString, (layout.width / 2) + 500, viewport.getWorldHeight() - layout.height /2);
        }
    }
    public void drawLife() {
        if (state == STATE.PLAYING) {
            String lifeToString = Integer.toString(snake.getLife()+1);
            layout.setText(bitmapFont, lifeToString);
            bitmapFont.draw(batch, "Vida " + lifeToString, (layout.width / 2) + 250, viewport.getWorldHeight() - layout.height /2);
        }
    }

    private  void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

}
