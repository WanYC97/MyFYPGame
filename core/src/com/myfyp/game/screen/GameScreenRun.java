package com.myfyp.game.screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myfyp.game.GameWorld.GameWorld;
import com.myfyp.game.helper.StepCounterInterface;

import java.util.GregorianCalendar;

public class GameScreenRun implements Screen {
    GameWorld world;
    int runTime;

    private OrthographicCamera camera;
    private Skin skin;
    private Stage stage;
    private Viewport viewport;
    private Game game;
    private Table table;

    private StepCounterInterface stepCounter;


    public GameScreenRun(final Game game, final StepCounterInterface stepCounter){

        //Info about the screen size
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        //World unit
        float gameWidthF = 20;
        float ppu = screenWidth/gameWidthF; // pixel per inch
        float gameHeightF = screenHeight /ppu;

        camera = new OrthographicCamera(gameWidthF, gameHeightF);
        camera.setToOrtho(false);
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        stage = new Stage(viewport);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        TextButton pauseButton = new TextButton("Pause", skin);
        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton backButton = new TextButton("Back", skin);

        backButton.setTransform(true);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Confirmation button
                game.setScreen(new GameScreen(game, stepCounter));
                dispose();
            }
        });

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Pause countdown
            }
        });

        table.add(pauseButton).uniformX().getFillX();
        table.row();
        table.add(backButton).pad(1).uniformX().getFillX();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        camera.update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
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

    }
}
