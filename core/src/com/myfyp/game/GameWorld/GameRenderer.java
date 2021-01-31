
package com.myfyp.game.GameWorld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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

import static com.myfyp.game.helper.AssetLoader.background;
import static com.myfyp.game.helper.AssetLoader.dispose;

public class GameRenderer {

    private int gameWidth;
    private int gameHeight;
    private SpriteBatch batcher;
    private BitmapFont numberCoin, numberSteps, numberCost, countdown, labelUpgrade, labelBall, labelFood;
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
    private RunButton upgradeButton;
    private Coin coin;
    private Fun fun;
    private Happiness happiness;
    public Image imageBackGround, imagePet, imageBall, a_left, a_right, imageUpgradeButton, imageFood, imageCoin, imageFun, imageHappiness;
    private Music menuMusic;
    private Sound woofSound;

    private float fallCheck = DataClass.getStepCount();
    private float moneyCheck = DataClass.getMONEY();
    private ArrayList<FallingObject> fallongObjectQueue = new ArrayList<>();
    private int amountFallingObject;
    private static float FALLINGOBJECT_THRESHOLD = -1;
    private static float FALLINGOBJECT_SPEED = 0.8f;

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
        musicInit();

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

    public static class InteractDialog extends Dialog {

        public InteractDialog(String title, Skin skin) {
            super(title, skin);
        }

        {
            text("WOOF WOOF").setColor(Color.SALMON);
            button ("AFFINITY +1", ":D");
        }

        @Override
        protected void result(Object object){
        }

    }

    private void gameObjectsInit(){
        pet = world.getPet();
        toy = world.getToy();
        food = world.getFood();
        arrowLeft = world.getArrowLeft();
        arrowRight = world.getArrowRight();
        upgradeButton = world.getRunButton();
        coin = world.getCoin();
        fun = world.getFun();
        happiness = world.getHappiness();
    }

    private void assetsInit(){
        AssetLoader.load();
        imageBackGround = new Image(AssetLoader.background);
        imagePet = new Image(AssetLoader.shiba);
        imageBall = new Image(AssetLoader.toy);
        imageFood = new Image(AssetLoader.food);
        imageFood.setName("imageFoodDragAndDrop");

        a_left = new Image(AssetLoader.arrowLeft);
        a_right = new Image(AssetLoader.arrowRight);
        imageUpgradeButton = new Image(AssetLoader.runButton);
        imageCoin = new Image(AssetLoader.coin);
        imageFun = new Image(AssetLoader.fun);
        imageHappiness = new Image(AssetLoader.happiness);
    }

    private void musicInit(){
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Netherplace_Looping.mp3"));
        woofSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pup2.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();
    }

    private void playAnimation(){
        //RUN 3 TIMES WHEN INCREMENTED

        //if ori location, move up

        Vector2 coords = new Vector2(imagePet.getX(), imagePet.getY());

        if(coords.y >= pet.getY()+1){
            imagePet.addAction(Actions.moveTo(pet.getX(), pet.getY(), 0.1f));
        }
        if(coords.y == pet.getY()){
            imagePet.addAction(Actions.moveTo(pet.getX(), pet.getY()+1, 0.1f));
        }
        //if hit barrier, mov   e down
    }


    private void addStepCount(){
        numberCoin = new BitmapFont(Gdx.files.internal("text.fnt"));
        numberSteps = new BitmapFont(Gdx.files.internal("text.fnt"));
        numberCost = new BitmapFont(Gdx.files.internal("text.fnt"));
        countdown = new BitmapFont(Gdx.files.internal("text.fnt"));

        labelBall = new BitmapFont(Gdx.files.internal("text.fnt"));
        labelFood = new BitmapFont(Gdx.files.internal("text.fnt"));
        labelUpgrade = new BitmapFont(Gdx.files.internal("text.fnt"));
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

    private void removeFallingObject(){
        if(fallongObjectQueue.get(0).getY() <= -gameHeight){
            fallongObjectQueue.remove(fallongObjectQueue.get(1));
            amountFallingObject--;
        }

        //System.out.println(fallongObjectQueue.size());
    }

    private void placeUpgradeButton(){
        imageUpgradeButton.setPosition(upgradeButton.getX(), upgradeButton.getY());
        imageUpgradeButton.setWidth(upgradeButton.getWidth());
        imageUpgradeButton.setHeight(upgradeButton.getHeight());
        stage.addActor(imageUpgradeButton);

        imageUpgradeButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(myVar == ScreenNo.FIRST_SCREEN){
                    game.setScreen(new UpgradeScreen(game, stepCounter));
                    menuMusic.dispose();
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

        imagePet.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                DataClass.setMONEY(DataClass.getMONEY() + 1);
            }
        });
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
        imageBall.setSize(toy.getWidth(), toy.getHeight());
        stage.addActor(imageBall);
        actorDragAndDrop();
    }

    private void foodScreen(){

        imageFood.setPosition( this.food.getX(), this.food.getY());
        imageFood.setSize(food.getWidth(), food.getHeight());

        stage.addActor(imageFood);

        actorDragAndDrop();
    }

    private void actorDragAndDrop(){
        final DragAndDrop dragAndDrop = new DragAndDrop();
        if(myVar == ScreenNo.SECOND_SCREEN){
            //IF = O then GREY
            //IF > 0 THEN CAN DRAG
            //ON SUCCESS, AFFINITY + 1, REWARDS -1

            if(DataClass.getREWARDS() <= 0) {
                stage.getRoot().findActor("imageFoodDragAndDrop").setColor(Color.GRAY);
            }
            else{
                imageFood.setColor(Color.WHITE);
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
                            imageFood.setColor(Color.WHITE);
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
                    woofSound.play();
                    getActor().setColor(Color.WHITE);
                    DataClass.setAFFINITY(DataClass.getAFFINITY()+1);
                    DataClass.setREWARDS(DataClass.getREWARDS()-1);

                    System.out.println("CURRENT AFFINITY " + DataClass.getAFFINITY());
                    System.out.println("CURRENT REWARDS " + DataClass.getREWARDS());
                    imageFood.setPosition(toy.getX(), toy.getY());

                    Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
                    InteractDialog dia = new InteractDialog("", skin);
                    dia.show(stage);
                    dia.setOrigin(gameWidth/2 - 3, gameHeight/2 -2);
                    dia.setScale(0.02f);
                    if(DataClass.getREWARDS() ==0){
                        dragAndDrop.clear();
                        imageFood.setColor(Color.GRAY);
                    }
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
                        imageBall.setColor(Color.WHITE);
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
                    menuMusic.dispose();
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
        batcher.draw(background, 0, 0, gameWidth, gameHeight);

        float f = 0.02f;
        float f2 = 1.0f;

        numberCoin.setColor(f2, f2, f2, 1.0f);
        numberCoin.setUseIntegerPositions(false);
        numberCoin.getData().setScale(f,f);

        numberSteps.setColor(f2, f2, f2, 1.0f);
        numberSteps.setUseIntegerPositions(false);
        numberSteps.getData().setScale(f,f);

        numberCost.setColor(f2, f2, f2, 1.0f);
        numberCost.setUseIntegerPositions(false);
        numberCost.getData().setScale(f,f);

        labelUpgrade.setColor(f2, f2, f2, 1.0f);
        labelUpgrade.setUseIntegerPositions(false);
        labelUpgrade.getData().setScale(f,f);

        labelBall.setColor(f2, f2, f2, 1.0f);
        labelBall.setUseIntegerPositions(false);
        labelBall.getData().setScale(f,f);

        labelFood.setColor(f2, f2, f2, 1.0f);
        labelFood.setUseIntegerPositions(false);
        labelFood.getData().setScale(f,f);


        //PRINT STEP COUNT,  MONEY
        numberCoin.draw(batcher, Integer.toString((int)DataClass.getMONEY()), coin.getX() +4, coin.getY() +1);
        numberSteps.draw(batcher, Integer.toString((int)DataClass.getStepCount()), fun.getX() +4, fun.getY() +1);
        numberCost.draw(batcher, Integer.toString((int)DataClass.getAFFINITY()), happiness.getX() +4, happiness.getY() + 1);

        labelUpgrade.draw(batcher, "UPGRADE", upgradeButton.getX() -1, upgradeButton.getY() -1);

        if(myVar == ScreenNo.SECOND_SCREEN){
            //food screen
            labelFood.draw(batcher, "TREAT", food.getX() +1, food.getY() -1);

        }


        if(myVar == ScreenNo.THIRD_SCREEN){
            //food screen
            labelBall.draw(batcher, "PLAY", imageBall.getX(), imageBall.getY() -1);
        }



        addFallingObject();
        renderFall();
        removeFallingObject();
        playAnimation();

        batcher.end();
        stage.act();
        stage.draw();
    }

}