package com.myfyp.game.screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myfyp.game.GameWorld.GameWorld;
import com.myfyp.game.helper.AssetLoader;
import com.myfyp.game.helper.DataClass;
import com.myfyp.game.helper.PreferenceManager;
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
    PreferenceManager preferenceManager;
    private Image imageBackGround, upgrade1, upgrade2;


    public UpgradeScreen(final Game game, final StepCounterInterface stepCounter){

        //Info about the screen size
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        //World unit
        float gameWidthF = 20;
        float ppu = screenWidth/gameWidthF; // pixel per inch
        float gameHeightF = screenHeight /ppu;

        imageBackGround = new Image(AssetLoader.background_upgrade);
        upgrade1 = new Image(AssetLoader.upgrade1);
        upgrade2 = new Image(AssetLoader.upgrade2);

        camera = new OrthographicCamera(gameWidthF, gameHeightF);
        camera.setToOrtho(false);
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        stage = new Stage(viewport);

        stage.addActor(imageBackGround);
        imageBackGround.setPosition(0, 0);
        imageBackGround.setSize(screenWidth, screenHeight);

        table = new Table();
        table.setFillParent(true);
        table.pad(10);
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

                if(DataClass.getPRICE1() > DataClass.getMONEY()){
                    System.out.println("Not enough money for upgrade 1");
                }
                else {
                    DataClass.setUpgrade1Count(DataClass.getUpgrade1Count() + 1);
                    DataClass.setCOST(DataClass.getCOST() + (1 * DataClass.getPRICE1()));
                    DataClass.setMONEY(DataClass.getMONEY() - DataClass.getPRICE1());

                    textCount1.setText("Count: " + DataClass.getUpgrade1Count());

                    System.out.println("UPGRADE 1 COUNT IS: " + DataClass.getUpgrade1Count());
                    System.out.println("CURRENT PRICE IS: " + DataClass.getPRICE1());
                    System.out.println("CURRENT CASH IS: " + DataClass.getMONEY());
                }
            }
        });

        upgradeButton2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if(DataClass.getPRICE2() > DataClass.getMONEY()){
                    System.out.println("Not enough money for upgrade 2");
                }
                else{
                    DataClass.setUpgrade2Count(DataClass.getUpgrade2Count() + 1);
                    DataClass.setCOST(DataClass.getCOST() + (1 * DataClass.getPRICE2()) );
                    DataClass.setMONEY(DataClass.getMONEY() - DataClass.getPRICE2());

                    textCount2.setText("Count: " + DataClass.getUpgrade2Count());

                    System.out.println("UPGRADE 2 COUNT IS: " + DataClass.getUpgrade2Count());
                    System.out.println("CURRENT PRICE IS: " + DataClass.getPRICE2());
                    System.out.println("CURRENT CASH IS: " + DataClass.getMONEY());

                }
            }
        });
        table.add(upgrade1).maxSize(screenWidth/10, screenWidth/10);
        table.add(textUpgrade1).getFillX();
        table.add(upgradeButton1).right().uniformX().getFillX();
        table.row();

        table.add(textCount1).right().colspan(2);
        table.row();

        table.add(upgrade2).maxSize(screenWidth/10, screenWidth/10);;
        table.add(textUpgrade2).uniformX().getFillX();
        table.add(upgradeButton2).right().uniformX().getFillX();
        table.row();

        table.add(textCount2).right().colspan(2);
        table.row();
        table.add(backButton).right().pad(1).colspan(3).uniformX().getFillX();

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


        if(DataClass.getPRICE1() > DataClass.getMONEY() && DataClass.getPRICE2() > DataClass.getMONEY()){
            upgradeButton1.setColor(Color.DARK_GRAY);
        }
        else if(DataClass.getPRICE2() > DataClass.getMONEY()){
            upgradeButton2.setColor(Color.DARK_GRAY);
        }
        else{
            upgradeButton1.setColor(Color.GOLD);
            upgradeButton2.setColor(Color.GOLD);

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
