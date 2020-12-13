package com.myfyp.game.GameWorld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myfyp.game.helper.AssetLoader;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.myfyp.game.helper.StepCounterInterface;
import com.myfyp.game.screen.GameScreenRun;

import GameObjects.ArrowLeft;
import GameObjects.ArrowRight;
import GameObjects.Pet;
import GameObjects.Toy;

public class GameRenderer {

    private int gameWidth;
    private int gameHeight;
    private SpriteBatch batcher;
    private BitmapFont screenNumber;
    private GameWorld world;
    private ShapeRenderer shapeRenderer;
    private Game game;
    private OrthographicCamera camera;
    private Stage stage;
    private Viewport viewport;

    private StepCounterInterface stepCounter;
    private float STEP_COUNT;

    //Game Object
    private Pet pet;
    private Toy toy;
    private ArrowLeft arrowLeft;
    private ArrowRight arrowRight;
    public Image imagePet, ball, a_left, a_right;

    //Set main screen as FIRST_SCREEN
    ScreenNo myVar = ScreenNo.FIRST_SCREEN;

    //Determine the current screen
    enum ScreenNo{
        FIRST_SCREEN,
        SECOND_SCREEN,
        THIRD_SCREEN
    }

    public GameRenderer(GameWorld world, int gameWidth, int gameHeight, Game game, StepCounterInterface stepCounter, OrthographicCamera camera, Viewport viewport, Stage stage) {
        this.world = world;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.game = game;
        this.stepCounter = stepCounter;
        this.camera = camera;
        this.viewport = viewport;
        this.stage = stage;

        gameObjectsInit();
        assetsInit();

        STEP_COUNT = countStep();

        placeArrow(a_left, a_right, ball);
        placePet(imagePet);
        addStepCount();
        Gdx.input.setInputProcessor(stage);

        batcher = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        batcher.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    private float countStep(){
        return stepCounter.getStepCount();
    }

    private void addStepCount(){
        screenNumber = new BitmapFont(Gdx.files.internal("text.fnt"));
    }

    private void placePet(Image imagePet){
        imagePet.setPosition( pet.getX(), pet.getY());
        imagePet.setWidth(pet.getWidth());
        imagePet.setHeight(pet.getHeight());
        stage.addActor(imagePet);
    }

    private void placeArrow(Image arrowLeftImage, Image arrowRightImage, final Image toy){
        arrowLeftImage.setPosition( arrowLeft.getX(), arrowLeft.getY());
        arrowLeftImage.setWidth(arrowLeft.getWidth());
        arrowLeftImage.setHeight(arrowLeft.getHeight());
        stage.addActor(arrowLeftImage);

        arrowRightImage.setPosition( arrowRight.getX(),arrowRight.getY());
        arrowRightImage.setWidth(arrowRight.getWidth());
        arrowRightImage.setHeight(arrowRight.getHeight());
        stage.addActor(arrowRightImage);

        arrowLeftImage.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(myVar == ScreenNo.FIRST_SCREEN){
                    //Go to left
                }
                else if(myVar == ScreenNo.SECOND_SCREEN){
                    //Do nothing
                }
                else if(myVar == ScreenNo.THIRD_SCREEN){
                    //return to first screen
                }
                System.out.println("Left button clicked");

            }
        });

        arrowRightImage.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(myVar == ScreenNo.FIRST_SCREEN){
                    playScreen(toy);
                }
                else if(myVar == ScreenNo.SECOND_SCREEN){
                    //return to first screen
                }
                else if(myVar == ScreenNo.THIRD_SCREEN){
                    //Do nothing
                }
                System.out.println("Right button clicked");
            }
        });
    }

    private void playScreen(final Image toy){
        toy.setPosition( this.toy.getX(), this.toy.getY());
        toy.setSize(3, 3);
        stage.addActor(toy);
        ballDragAndDrop();
    }

    private void ballDragAndDrop(){
        DragAndDrop dragAndDrop = new DragAndDrop();
        dragAndDrop.addSource(new Source(ball) {
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                System.out.println("Drag Start");
                Payload payload = new Payload();
                payload.setObject(ball);
                payload.setDragActor(ball);
                return payload;
            }
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target){
                if(target == null){
                    ball.setPosition(toy.getX(), toy.getY());
                }
            }
        });
        dragAndDrop.addTarget(new DragAndDrop.Target(imagePet) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.GREEN);
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.WHITE);
                System.out.println("Dropped");
                game.setScreen(new GameScreenRun(game, stepCounter));
                ball.remove();
            }
        });
    }

    private void foodScreen(){ }

    private void foodDragAndDrop(){

    }

    private void gameObjectsInit(){
        pet = world.getPet();
        toy = world.getToy();
        arrowLeft = world.getArrowLeft();
        arrowRight = world.getArrowRight();
    }

    private void assetsInit(){
        AssetLoader.load();
        imagePet = new Image(AssetLoader.shiba);
        ball = new Image(AssetLoader.toy);
        a_left = new Image(AssetLoader.arrowLeft);
        a_right = new Image(AssetLoader.arrowRight);
    }

    public void render(float runTime){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(pet.getBoundingRectangle().x, pet.getBoundingRectangle().y, pet.getX(),pet.getY());
        shapeRenderer.end();

        batcher.begin();
        screenNumber.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        screenNumber.getData().setScale(0.1f,0.1f);
        screenNumber.draw(batcher, Float.toString(STEP_COUNT), gameWidth >> 1, gameHeight -2);
        STEP_COUNT = countStep();
        batcher.end();
        System.out.println(STEP_COUNT);
        stage.act();
        stage.draw();
    }

}