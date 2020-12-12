package com.myfyp.game.GameWorld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.FitViewport;
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

    private ImageButton play;
    private Table table;
    private int midPointY;
    private int gameWidth;
    private int gameHeight;
    private SpriteBatch batcher;
    private BitmapFont screenNumber;
    private GameWorld world;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Skin skin;
    private Stage stage;
    private Viewport viewport;
    private Game game;

    private StepCounterInterface stepCounter;
    private float STEP_COUNT;
    //private String STEP_COUNT_STRING;

    //Game Object
    private Pet pet;
    private Toy toy;
    private ArrowLeft arrowLeft;
    private ArrowRight arrowRight;

    //Assets
    public static Texture shiba;
    public static Texture arrowLeftTexture;
    public static Texture arrowRightTexture;
    public static Texture toy_ball;

    public Image imagePet, ball, a_left, a_right;

    public GameRenderer(GameWorld world, int gameWidth, int gameHeight, int midPointY, Game game, StepCounterInterface stepCounter) {
        this.world = world;
        this.midPointY = midPointY;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.game = game;
        this.stepCounter = stepCounter;

        gameObjectsInit();
        AssetLoader.load();
        assetsInit();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameWidth, gameHeight);
        camera.update();
        viewport = new FitViewport(gameWidth, gameHeight, camera);
        stage = new Stage(viewport);
        imageInit();
        STEP_COUNT = countStep();

        placeArrow(a_left, a_right, ball, imagePet);
        drawPet(imagePet);
        addStepCount();


        Gdx.input.setInputProcessor(stage);
        batcher = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        assetsInit();
        batcher.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    private float countStep(){
        return stepCounter.getStepCount();
    }

    private void addStepCount(){
        screenNumber = new BitmapFont(Gdx.files.internal("text.fnt"));
    }


    private void placeArrow(Image arrowLeftImage, Image arrowRightImage, final Image toy1, final Image imagePet){
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
                System.out.println("Clicked 01");

            }
        });
        arrowRightImage.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                addToys(toy1, imagePet);
                System.out.println("Clicked 02");
            }
        });
    }

    private void addToys(final Image ball, Image imagePet){
        ball.setPosition( toy.getX(), toy.getY());
        ball.setSize(3, 3);
        stage.addActor(ball);

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

    private void drawPet(Image imagePet){
        imagePet.setPosition( pet.getX(), pet.getY());
        imagePet.setWidth(pet.getWidth());
        imagePet.setHeight(pet.getHeight());
        stage.addActor(imagePet);
        //batcher.draw(shiba, pet.getX(), pet.getY(), pet.getWidth(), pet.getHeight());
    }

    private void gameObjectsInit(){
        pet = world.getPet();
        toy = world.getToy();
        arrowLeft = world.getArrowLeft();
        arrowRight = world.getArrowRight();
    }

    private void assetsInit(){
        shiba = AssetLoader.shiba;
        arrowLeftTexture = AssetLoader.arrowLeft;
        arrowRightTexture = AssetLoader.arrowRight;
        toy_ball = AssetLoader.toy;
    }

    private void imageInit(){
        imagePet = new Image(shiba);
        ball = new Image(toy_ball);
        a_left = new Image(arrowLeftTexture);
        a_right = new Image(arrowRightTexture);
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
    //NEED FIX
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
    public void dispose(){
        AssetLoader.dispose();
        stage.dispose();
    }
}
