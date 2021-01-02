package com.myfyp.game.screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myfyp.game.helper.StepCounterInterface;

import java.util.Timer;
import java.util.TimerTask;


public class ExerciseScreen implements Screen {

    int runTime;
    private Skin skin;
    private OrthographicCamera camera;
    private Stage stage;
    private Viewport viewport;
    private Table table;

    private Label countDown;

    //Info about the screen size
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();
    //World unit
    float gameWidthF = 20;
    float ppu = screenWidth/gameWidthF; // pixel per inch
    float gameHeightF = screenHeight /ppu;

    private StepCounterInterface stepCounter;


    public ExerciseScreen(final Game game, final StepCounterInterface stepCounter){

        camera = new OrthographicCamera(gameWidthF, gameHeightF);
        camera.setToOrtho(false);
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        stage = new Stage(viewport);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        Label.LabelStyle label1Style = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("text.fnt"));
        label1Style.font = myFont;

        countDown = new Label("COUNTDOWN",label1Style);
        TextButton start = new TextButton("Start", skin);
        TextButton Pause = new TextButton("Pause 1", skin);
        TextButton Resume = new TextButton("Resume 2", skin);

        TextButton backButton = new TextButton("Back", skin);

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTaskApp(), 0, 1000);
            }
        });
        backButton.setTransform(true);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, stepCounter));
                dispose();
            }
        });

        table.add(countDown).uniformX().getFillX();
        table.row();
        table.add(start).uniformX().getFillX();
        table.row();
        table.add(Pause).uniformX().getFillX();
        table.row();
        table.add(Resume).uniformX().getFillX();
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

class TimerTaskApp extends TimerTask{
    int countdown = 100;

    @Override
    public void run() {
        if(countdown > 0) {
            countdown = countdown - 1;
            String time = String.format("%02d:%02d", countdown / 60, countdown % 60);
            System.out.println(time);
            countDown.setText("Time left: " + time);
        }
        else{
            //affection + 1
        }
    }
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
