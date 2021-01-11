
package com.myfyp.game.GameWorld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myfyp.game.helper.AssetLoader;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.myfyp.game.helper.DataClass;
import com.myfyp.game.helper.StepCounterInterface;
import com.myfyp.game.screen.ExerciseScreen;
import com.myfyp.game.screen.UpgradeScreen;

import java.util.ArrayList;

import javax.xml.crypto.Data;

import GameObjects.ArrowLeft;
import GameObjects.ArrowRight;
import GameObjects.Coin;
import GameObjects.FallingObject;
import GameObjects.Food;
import GameObjects.Fun;
import GameObjects.Happiness;
import GameObjects.Pet;
import GameObjects.RunButton;
import GameObjects.Toy;
import static com.myfyp.game.helper.AssetLoader.dispose;

public class GameRenderer {

    private int gameWidth;
    private int gameHeight;
    private SpriteBatch batcher;
    private BitmapFont screenNumber, screenNumber2, screenNumber3, countdown;
    private GameWorld world;
    private ShapeRenderer shapeRenderer;
    private Game game;
    private OrthographicCamera camera;
    private Stage stage;
    private Viewport viewport;

    private StepCounterInterface stepCounter;

    //Game Object
    private Pet pet;
    private Toy toy;
    private Food food;
    private ArrowLeft arrowLeft;
    private ArrowRight arrowRight;
    private RunButton runButton;
    private Coin coin;
    private Fun fun;
    private Happiness happiness;
    public Image imagePet, imageBall, a_left, a_right, imageRunButton, imageFood, imageFood2, imageFood3, imageCoin, imageFun, imageHappiness;

    private ArrayList<FallingObject> fallongObjectQueue = new ArrayList<>();
    private int amountFallingObject;
    private static float FALLINGOBJECT_THRESHOLD = -1;
    private static float FALLINGOBJECT_SPEED = 0.8f;
    private static float FALLINGOBJECT_ROTATION_SPEED = 0.25f;

    //Set main screen as FIRST_SCREEN
    ScreenNo myVar = ScreenNo.FIRST_SCREEN;

    //Determine the current screen
    enum ScreenNo{
        //Default screen
        FIRST_SCREEN,
        //Food screen
        SECOND_SCREEN,
        //Play screen
        THIRD_SCREEN
    }

    public GameRenderer(GameWorld world, int gameWidth, int gameHeight, Game game, StepCounterInterface stepCounter,
                        OrthographicCamera camera, Viewport viewport, Stage stage) {
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

        //Place elements on screen
        placeArrow();
        placePet();
        placeUpgradeButton();
        placeResources();
        addStepCount();
        Gdx.input.setInputProcessor(stage);
        batcher = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        batcher.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    private void gameObjectsInit(){
        pet = world.getPet();
        toy = world.getToy();
        food = world.getFood();
        arrowLeft = world.getArrowLeft();
        arrowRight = world.getArrowRight();
        runButton = world.getRunButton();
        coin = world.getCoin();
        fun = world.getFun();
        happiness = world.getHappiness();
    }

    private void assetsInit(){
        AssetLoader.load();
        imagePet = new Image(AssetLoader.shiba);
        imageBall = new Image(AssetLoader.toy);
        imageFood = new Image(AssetLoader.food);
        imageFood.setName("imageFoodDragAndDrop");

        imageFood2 = new Image(AssetLoader.food);
        imageFood3 = new Image(AssetLoader.food);

        a_left = new Image(AssetLoader.arrowLeft);
        a_right = new Image(AssetLoader.arrowRight);
        imageRunButton = new Image(AssetLoader.runButton);
        imageCoin = new Image(AssetLoader.coin);
        imageFun = new Image(AssetLoader.fun);
        imageHappiness = new Image(AssetLoader.happiness);
    }

    private void playAnimation(){
        //TO DO
        //RUN 3 TIMES WHEN INCREMENTED

        //if ori location, move up
        Vector2 coords = new Vector2(imagePet.getX(), imagePet.getY());

        if(coords.y >= pet.getY()+1){
            imagePet.addAction(Actions.moveTo(pet.getX(), pet.getY(), 0.1f));
        }
        else if(coords.y == pet.getY()){
            imagePet.addAction(Actions.moveTo(pet.getX(), pet.getY()+1, 0.1f));
        }
        //if hit barrier, move down
    }

    private void addStepCount(){
        screenNumber = new BitmapFont(Gdx.files.internal("text.fnt"));
        screenNumber2 = new BitmapFont(Gdx.files.internal("text.fnt"));
        screenNumber3 = new BitmapFont(Gdx.files.internal("text.fnt"));
        countdown = new BitmapFont(Gdx.files.internal("text.fnt"));

    }

    private void renderFall() {
        for (FallingObject f_object : fallongObjectQueue) {
            batcher.draw(AssetLoader.fallingObject, f_object.getX(), f_object.getY(), coin.getWidth(), coin.getHeight());
            f_object.setY(f_object.getY() - FALLINGOBJECT_SPEED);
            //f_object.setRotation((f_object.getRotation() + MINICOOKIE_ROTATION_SPEED) % 360.0f);
        }
    }

    private void addFallingObject() {
        //CREATE NEW OBJECT TO FALL
        if (FALLINGOBJECT_THRESHOLD == -1 || amountFallingObject <= FALLINGOBJECT_THRESHOLD) {
            fallongObjectQueue.add(new FallingObject(MathUtils.random(0, gameWidth), gameHeight, MathUtils.random(0.0f, 360.0f)));
            amountFallingObject++;
        }
    }

    private void placeUpgradeButton(){
        imageRunButton.setPosition(runButton.getX(), runButton.getY());
        imageRunButton.setWidth(runButton.getWidth());
        imageRunButton.setHeight(runButton.getHeight());
        stage.addActor(imageRunButton);

        imageRunButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(myVar == ScreenNo.FIRST_SCREEN){
                    game.setScreen(new UpgradeScreen(game, stepCounter));
                    dispose();
                }
            }
        });
    }

    private void placePet(){
        imagePet.setPosition( pet.getX(), pet.getY());
        imagePet.setWidth(pet.getWidth());
        imagePet.setHeight(pet.getHeight());
        stage.addActor(imagePet);
    }

    private void placeArrow(){
        a_left.setPosition( arrowLeft.getX(), arrowLeft.getY());
        a_left.setWidth(arrowLeft.getWidth());
        a_left.setHeight(arrowLeft.getHeight());
        stage.addActor(a_left);

        a_right.setPosition( arrowRight.getX(),arrowRight.getY());
        a_right.setWidth(arrowRight.getWidth());
        a_right.setHeight(arrowRight.getHeight());
        stage.addActor(a_right);

        //LEFT ARROW FOR FOOD SCREEN
        a_left.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(myVar == ScreenNo.FIRST_SCREEN){
                    myVar = ScreenNo.SECOND_SCREEN;
                    foodScreen();
                }
                else if(myVar == ScreenNo.SECOND_SCREEN){
                    //Do nothing
                }
                else if(myVar == ScreenNo.THIRD_SCREEN){
                    //Change BG
                    myVar = ScreenNo.FIRST_SCREEN;
                    imageBall.remove();
                }
                System.out.println("Left button clicked");

            }
        });

        //RIGHT ARROW FOR TOY SCREEN
        a_right.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(myVar == ScreenNo.FIRST_SCREEN){
                    myVar = ScreenNo.THIRD_SCREEN;
                    playScreen();
                }
                else if(myVar == ScreenNo.SECOND_SCREEN){
                    myVar = ScreenNo.FIRST_SCREEN;
                    imageFood.remove();
                }
                else if(myVar == ScreenNo.THIRD_SCREEN){
                    //Do nothing
                }
                System.out.println("Right button clicked");
            }
        });
    }

    private void placeResources() {
        imageCoin.setPosition(coin.getX(), coin.getY());
        imageCoin.setWidth(coin.getWidth());
        imageCoin.setHeight(coin.getHeight());
        stage.addActor(imageCoin);

        imageFun.setPosition(fun.getX(), fun.getY());
        imageFun.setWidth(fun.getWidth());
        imageFun.setHeight(fun.getHeight());
        stage.addActor(imageFun);

        imageHappiness.setPosition(happiness.getX(), happiness.getY());
        imageHappiness.setWidth(happiness.getWidth());
        imageHappiness.setHeight(happiness.getHeight());
        stage.addActor(imageHappiness);
    }

    private void playScreen(){
        imageBall.setPosition( this.toy.getX(), this.toy.getY());
        imageBall.setSize(3, 3);
        stage.addActor(imageBall);
        actorDragAndDrop();
    }

    private void foodScreen(){
        imageFood.setPosition( this.toy.getX(), this.toy.getY());
        imageFood.setSize(3, 3);

        stage.addActor(imageFood);

        actorDragAndDrop();
    }

    private void actorDragAndDrop(){
        DragAndDrop dragAndDrop = new DragAndDrop();
        if(myVar == ScreenNo.SECOND_SCREEN){
            //IF = O then GREY
            //IF > 0 THEN CAN DRAG
            //ON SUCCESS, AFFINITY + 1, REWARDS -1

            if(DataClass.getREWARDS() == 0) {
                stage.getRoot().findActor("imageFoodDragAndDrop").setColor(Color.GRAY);
            }
            else{
                dragAndDrop.addSource(new Source(imageFood) {

                    public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                        System.out.println("Drag Start");
                        Payload payload = new Payload();
                        payload.setObject(imageFood);
                        payload.setDragActor(imageFood);
                        return payload;
                    }

                    public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target){
                        if(target == null){
                            imageFood.setPosition(toy.getX(), toy.getY());
                        }
                    }
                });
            }

            dragAndDrop.addTarget(new DragAndDrop.Target(imagePet) {
                @Override
                public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    getActor().setColor(Color.GREEN);
                    return true;
                }
                @Override
                public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    getActor().setColor(Color.WHITE);
                    DataClass.setAFFINITY(DataClass.getAFFINITY()+1);
                    DataClass.setREWARDS(DataClass.getREWARDS()-1);
                    System.out.println("CURRENT AFFINITY " + DataClass.getAFFINITY());
                    System.out.println("CURRENT REWARDS " + DataClass.getREWARDS());
                    imageFood.setPosition(toy.getX(), toy.getY());
                }
            });
        }

        if(myVar == ScreenNo.THIRD_SCREEN){
            dragAndDrop.addSource(new Source(imageBall) {
                public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                    System.out.println("Drag Start");
                    Payload payload = new Payload();
                    payload.setObject(imageBall);
                    payload.setDragActor(imageBall);
                    return payload;
                }
                public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target){
                    if(target == null){
                        imageBall.setPosition(toy.getX(), toy.getY());
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

                    game.setScreen(new ExerciseScreen(game, stepCounter));
                    imageBall.remove();
                    dispose();
                }
            });
        }


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
        screenNumber.getData().setScale(0.03f,0.03f);

        screenNumber2.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        screenNumber2.getData().setScale(0.03f,0.03f);

        screenNumber3.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        screenNumber3.getData().setScale(0.03f,0.03f);

        //PRINT STEP COUNT,  MONEY
        screenNumber.draw(batcher, Float.toString(DataClass.getStepCount()), coin.getX() +3, coin.getY());
        screenNumber2.draw(batcher, Float.toString(DataClass.getMONEY()), fun.getX() +3, fun.getY());
        screenNumber3.draw(batcher, Float.toString(DataClass.getCOST()), happiness.getX() +3, happiness.getY());

        addFallingObject();
        renderFall();

        batcher.end();

        playAnimation();

        stage.act();
        stage.draw();
    }

}