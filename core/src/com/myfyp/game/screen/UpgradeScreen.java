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
import com.myfyp.game.GameWorld.GameWorld;
import com.myfyp.game.helper.DataClass;
import com.myfyp.game.helper.StepCounterInterface;
import com.myfyp.game.screen.GameScreen;
import java.util.GregorianCalendar;

import javax.xml.crypto.Data;
import javax.xml.soap.Text;

public class UpgradeScreen implements Screen {

    int runTime;
    private Skin skin;
    private OrthographicCamera camera;
    private Stage stage;
    private Viewport viewport;
    private Table table;

    private Label textUpgrade1, textUpgrade2, textCount1, textPrice1, textCount2, textPrice2;
    private String textUpgradeCount1, textUpgradePrice1, textUpgradeCount2, textUpgradePrice2;
    private TextButton upgradeButton1, upgradeButton2, backButton;
    private StepCounterInterface stepCounter;


    public UpgradeScreen(final Game game, final StepCounterInterface stepCounter){

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

        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        Label.LabelStyle label1Style = new Label.LabelStyle();
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("text.fnt"));
        label1Style.font = myFont;

        //data to string
        textUpgradeCount1 = Float.toString(DataClass.getUpgrade1Count());
         textUpgradePrice1 = Float.toString(DataClass.getPRICE1());
         textUpgradeCount2 = Float.toString(DataClass.getUpgrade2Count());
         textUpgradePrice2 = Float.toString(DataClass.getPRICE2());

         upgradeButton1 = new TextButton("BUY", skin);
         upgradeButton2 = new TextButton("BUY", skin);
         backButton = new TextButton("Back", skin);
        textUpgrade1 = new Label("UPGRADE 1",label1Style);
        textCount1 = new Label("Count: " + DataClass.getUpgrade1Count(), label1Style);
        textUpgrade2 = new Label("UPGRADE 2",label1Style);
        textCount2 = new Label("Count: " + DataClass.getUpgrade2Count(), label1Style);

        backButton.setTransform(true);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game, stepCounter));
                dispose();
            }
        });

        upgradeButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                DataClass.setUpgrade1Count(DataClass.getUpgrade1Count() + 1);
                textCount1.setText("Count: " + DataClass.getUpgrade1Count());
                DataClass.setCOST(DataClass.getCOST() + (1 * DataClass.getPRICE1()) );
                System.out.println("UPGRADE 1 COUNT IS: " + DataClass.getUpgrade1Count());
                System.out.println("CURRENT PRICE IS: " + DataClass.getPRICE1());
                System.out.println("CURRENT COST IS: " + DataClass.getCOST());
            }
        });

        upgradeButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                DataClass.setUpgrade2Count(DataClass.getUpgrade2Count() + 1);
                textCount2.setText("Count: " + DataClass.getUpgrade2Count());
                DataClass.setCOST(DataClass.getCOST() + (1 * DataClass.getPRICE2()) );
                System.out.println("UPGRADE 2 COUNT IS: " + DataClass.getUpgrade2Count());
                System.out.println("CURRENT PRICE IS: " + DataClass.getPRICE2());
                System.out.println("CURRENT COST IS: " + DataClass.getCOST());
            }
        });
        table.add(textUpgrade1).uniformX().getFillX();
        table.add(upgradeButton1).uniformX().getFillX();
        table.row();
        table.add(textCount1).uniformX().getFillX();
        table.row();
        table.add(textUpgrade2).uniformX().getFillX();
        table.add(upgradeButton2).uniformX().getFillX();
        table.row();
        table.add(textCount2).uniformX().getFillX();
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
