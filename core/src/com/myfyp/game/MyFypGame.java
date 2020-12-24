package com.myfyp.game;

import com.badlogic.gdx.Game;
import com.myfyp.game.helper.DataClass;
import com.myfyp.game.helper.PreferenceManager;
import com.myfyp.game.helper.StepCounterInterface;
import com.myfyp.game.screen.GameScreen;

import java.util.GregorianCalendar;

public class MyFypGame extends Game {
	private Game game;
	private final StepCounterInterface stepCounter;
	PreferenceManager preferenceManager;


	public MyFypGame(StepCounterInterface stepCounter){
		game = this;
		this.stepCounter = stepCounter;
	}
	
	@Override
	public void create () {
		this.setScreen(new GameScreen(game, stepCounter));
		preferenceManager = new PreferenceManager();

		DataClass.setUpgrade1Count(preferenceManager.getFloatValue("UPGRADE1"));
		DataClass.setUpgrade2Count(preferenceManager.getFloatValue("UPGRADE2"));
		DataClass.setStepCount(preferenceManager.getFloatValue("STEP_COUNT"));
		DataClass.setMONEY(preferenceManager.getFloatValue("MONEY"));
	}

	@Override
	public void render () {
		super.render();
	}


	@Override
	public void dispose () {
		//SAVE DATA
		preferenceManager.setFloatValue("UPGRADE1", DataClass.getUpgrade1Count());
		preferenceManager.setFloatValue("UPGRADE2", DataClass.getUpgrade2Count());
		preferenceManager.setFloatValue("STEP_COUNT", DataClass.getStepCount());
		preferenceManager.setFloatValue("MONEY", DataClass.getMONEY());

		super.dispose();

	}


}
