package com.myfyp.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.myfyp.game.helper.DataClass;
import com.myfyp.game.helper.PreferenceManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myfyp.game.GameWorld.GameRenderer;
import com.myfyp.game.GameWorld.GameWorld;
import com.myfyp.game.MyFypGame;
import com.myfyp.game.helper.StepCounterInterface;

import java.util.GregorianCalendar;

import javax.xml.crypto.Data;

import GameObjects.Pet;

public class GameScreen implements Screen {
    GameWorld world;
    GameRenderer renderer;
    int runTime;
    float lastRecorded = DataClass.getStepCount();
    private Game game;
    private OrthographicCamera camera;
    private Stage stage;
    private Viewport viewport;

    StepCounterInterface stepCounter;

    public GameScreen(Game game, StepCounterInterface stepCounter){
        this.game = game;
        //Calendar and step counter
        this.stepCounter = stepCounter;
        //Info about the screen size
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        //World unit
        float gameWidthF = 20;
        float ppu = screenWidth/gameWidthF; // pixel per inch
        float gameHeightF = screenHeight /ppu;

        //Convert to int
        int gameWidth = (int)gameWidthF;
        int gameHeight = (int)gameHeightF;
        int midPointY = (int) (gameHeightF / 2);

        camera = new OrthographicCamera(gameWidth, gameHeight);
        camera.setToOrtho(false, gameWidth, gameHeight);
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        stage = new Stage(viewport);

        world = new GameWorld(gameWidth, gameHeight, midPointY);
        renderer = new GameRenderer(world, gameWidth, gameHeight, game, stepCounter, camera, viewport, stage);
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);
        countStep();
        convertToMoney();
    }

    private void countStep(){

        if(stepCounter.getCURRENT_STEP()>DataClass.getStepCount()){
            DataClass.setStepCount(DataClass.getStepCount() +(stepCounter.getCURRENT_STEP()- lastRecorded));
        }
        lastRecorded = stepCounter.getCURRENT_STEP();
    }

    private void convertToMoney(){
        //last base +  (new base - last base) multiply by number of upgrades * upgrade type

        if(stepCounter.getCURRENT_STEP() != DataClass.getLastCount()) {
            DataClass.setMONEY(DataClass.getLastCount() +
                    ((DataClass.getStepCount() - DataClass.getLastCount())
                            * ((DataClass.getUpgrade1Count() * DataClass.getUpgrade1Multiplier())
                            + (DataClass.getUpgrade2Count() * DataClass.getUpgrade2Multiplier()))));
            DataClass.setLastCount(DataClass.getStepCount());
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
