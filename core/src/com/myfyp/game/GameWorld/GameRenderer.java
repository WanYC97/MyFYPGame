
package com.myfyp.game.GameWorld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.myfyp.game.screen.UpgradeScreen;

import GameObjects.ArrowLeft;
import GameObjects.ArrowRight;
import GameObjects.Coin;
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
    private BitmapFont screenNumber, screenNumber2, screenNumber3;
    private GameWorld world;
    private ShapeRenderer shapeRenderer;
    private Game game;
    private OrthographicCamera camera;
    private Stage stage;
    private Viewport viewport;

    private StepCounterInterface stepCounter;
    //private float STEP_COUNT;
    //private float MONEY;

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
        countStep();
        //Place elements on screen
        placeArrow();
        placePet();
        placeRunButton();
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

        imageFood2 = new Image(AssetLoader.food);
        imageFood3 = new Image(AssetLoader.food);

        a_left = new Image(AssetLoader.arrowLeft);
        a_right = new Image(AssetLoader.arrowRight);
        imageRunButton = new Image(AssetLoader.runButton);
        imageCoin = new Image(AssetLoader.coin);
        imageFun = new Image(AssetLoader.fun);
        imageHappiness = new Image(AssetLoader.happiness);
    }

    private void countStep(){
        if(stepCounter.getCURRENT_STEP()>DataClass.getStepCount()){
            DataClass.setStepCount(stepCounter.getCURRENT_STEP());
        }
        playAnimation();
        //return STEP_COUNT;
    }

    private void convertToMoney(){
        //base + multiply by number of upgrades * upgrade type
        DataClass.setMONEY(DataClass.getStepCount() * (
                (DataClass.getUpgrade1Count() * DataClass.getUpgrade1Multiplier())
                + (DataClass.getUpgrade2Count() * DataClass.getUpgrade2Multiplier())
                ));
    }

    private void playAnimation(){
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
    }

    private void placeRunButton(){
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
                    imageBall.remove();
                    myVar = ScreenNo.FIRST_SCREEN;
                }
                System.out.println("Left button clicked");

            }
        });

        //RIGHT ARROW FOR TOY SCREEN
        a_right.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(myVar == ScreenNo.FIRST_SCREEN){
                    playScreen();
                    myVar = ScreenNo.THIRD_SCREEN;
                }
                else if(myVar == ScreenNo.SECOND_SCREEN){
                    imageFood.remove();
                    myVar = ScreenNo.FIRST_SCREEN;
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
        imageFood2.setPosition( this.toy.getX()-5, this.toy.getY());
        imageFood3.setPosition( this.toy.getX()+5, this.toy.getY());

        imageFood.setSize(3, 3);
        imageFood2.setSize(3, 3);
        imageFood3.setSize(3, 3);

        stage.addActor(imageFood);
        stage.addActor(imageFood2);
        stage.addActor(imageFood3);

        actorDragAndDrop();
    }

    private void actorDragAndDrop(){
        DragAndDrop dragAndDrop = new DragAndDrop();
        if(myVar == ScreenNo.SECOND_SCREEN){
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

            dragAndDrop.addSource(new Source(imageFood2) {
                public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                    System.out.println("Drag Start");
                    Payload payload = new Payload();
                    payload.setObject(imageFood2);
                    payload.setDragActor(imageFood2);
                    return payload;
                }
                public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target){
                    if(target == null){
                        imageFood2.setPosition(toy.getX()-5, toy.getY());
                    }
                }
            });

            dragAndDrop.addSource(new Source(imageFood3) {
                public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                    System.out.println("Drag Start");
                    Payload payload = new Payload();
                    payload.setObject(imageFood3);
                    payload.setDragActor(imageFood3);
                    return payload;
                }
                public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target){
                    if(target == null){
                        imageFood3.setPosition(toy.getX()+5, toy.getY());
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
                    //Play playing animation
                    //Add fun score

                    game.setScreen(new UpgradeScreen(game, stepCounter));
                    imageFood.remove();
                    imageFood2.remove();
                    imageFood3.remove();

                    dispose();
                }
            });
        }

        else if(myVar == ScreenNo.THIRD_SCREEN){
            //Play with pet
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
                    //Play playing animation
                    //Add fun score

                    game.setScreen(new UpgradeScreen(game, stepCounter));
                    imageBall.remove();
                    dispose();
                }
            });        }


    }

    public void render(float runTime){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(pet.getBoundingRectangle().x, pet.getBoundingRectangle().y, pet.getX(),pet.getY());
        shapeRenderer.end();
        System.out.println(DataClass.getUpgrade1Count());
        System.out.println(DataClass.getUpgrade2Count());

        convertToMoney();

        batcher.begin();
        screenNumber.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        screenNumber.getData().setScale(0.03f,0.03f);

        //PRINT STEP COUNT,  MONEY
        screenNumber.draw(batcher, Float.toString(DataClass.getStepCount()), coin.getX() +3, coin.getY());
        screenNumber2.draw(batcher, Float.toString(DataClass.getMONEY()), fun.getX() +3, fun.getY());
        screenNumber3.draw(batcher, Float.toString(DataClass.getCOST()), happiness.getX() +3, happiness.getY());

        batcher.end();

        countStep();
        stage.act();
        stage.draw();
    }

}