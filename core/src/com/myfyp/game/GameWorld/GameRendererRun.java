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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myfyp.game.helper.AssetLoader;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.myfyp.game.helper.StepCounterInterface;
import com.myfyp.game.screen.GameScreen;
import com.myfyp.game.screen.GameScreenRun;

import GameObjects.ArrowLeft;
import GameObjects.ArrowRight;
import GameObjects.Pet;
import GameObjects.Toy;

public class GameRendererRun {

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

    public GameRendererRun(GameWorld world, int gameWidth, int gameHeight, int midPointY, final Game game, final StepCounterInterface stepCounter) {
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

        Table table = new Table();
        table.setBounds(0,0, gameWidth/2, gameHeight/10);
        table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        TextButton back = new TextButton("Back", skin);
        back.setPosition(0,0);
        back.setSize(gameWidth/2, gameHeight/2);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, stepCounter));
            }
        });

        table.add(back);

        imageInit();

        Gdx.input.setInputProcessor(stage);
        batcher = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        assetsInit();
        batcher.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
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
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(pet.getBoundingRectangle().x, pet.getBoundingRectangle().y, pet.getX(),pet.getY());
        shapeRenderer.end();

        //batcher.begin();
        //batcher.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
