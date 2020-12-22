package com.myfyp.game;

import com.badlogic.gdx.Game;
import com.myfyp.game.helper.StepCounterInterface;
import com.myfyp.game.screen.GameScreen;

import java.util.GregorianCalendar;

public class MyFypGame extends Game {
	private Game game;
	private final StepCounterInterface stepCounter;

	public MyFypGame(StepCounterInterface stepCounter){
		game = this;
		this.stepCounter = stepCounter;
	}
	
	@Override
	public void create () {
		this.setScreen(new GameScreen(game, stepCounter));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
	}


}
