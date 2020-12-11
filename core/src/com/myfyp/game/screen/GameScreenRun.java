package com.myfyp.game.screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.myfyp.game.GameWorld.GameRenderer;
import com.myfyp.game.GameWorld.GameRendererRun;
import com.myfyp.game.GameWorld.GameWorld;
import com.myfyp.game.helper.StepCounterInterface;

public class GameScreenRun implements Screen {
    GameWorld world;
    GameRendererRun rendererRun;
    int runTime;

    public GameScreenRun(Game game, StepCounterInterface stepCounter){

        //Info about the screen size
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        //World unit
        float gameWidthF = 20;
        float ppu = screenWidth/gameWidthF; // pixel per inch
        float gameHeightF = screenHeight /ppu;

        int gameWidth = (int)gameWidthF;
        int gameHeight = (int)gameHeightF;
        int midPointY = (int) (gameHeightF / 2);

        world = new GameWorld(gameWidth, gameHeight, midPointY);
        rendererRun = new GameRendererRun(world, gameWidth, gameHeight, midPointY, game, stepCounter);

    }

    @Override
    public void render(float delta) {

        runTime += delta;
        world.update(delta);
        rendererRun.render(runTime);

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
