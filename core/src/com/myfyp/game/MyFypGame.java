package com.myfyp.game;

import com.badlogic.gdx.Game;
import com.myfyp.game.helper.DataClass;
import com.myfyp.game.helper.PreferenceManager;
import com.myfyp.game.helper.StepCounterInterface;
import com.myfyp.game.screen.GameScreen;

import java.util.GregorianCalendar;

import javax.xml.crypto.Data;

public class MyFypGame extends Game {
	private Game game;
	private final StepCounterInterface stepCounter;
	public static PreferenceManager preferenceManager;

	public MyFypGame(StepCounterInterface stepCounter){
		game = this;
		this.stepCounter = stepCounter;
	}
	
	@Override
	public void create () {
		this.setScreen(new GameScreen(game, stepCounter));
		preferenceManager = new PreferenceManager();

		//LOAD DATA, DEFAULT VALUE IS ZERO
		DataClass.setUpgrade1Count(preferenceManager.getFloatValue("UPGRADE1"));
		DataClass.setUpgrade2Count(preferenceManager.getFloatValue("UPGRADE2"));
		DataClass.setUpgrade2Count(preferenceManager.getFloatValue("UPGRADE3"));
		DataClass.setStepCount(preferenceManager.getFloatValue("STEP_COUNT"));
		DataClass.setLastCount(preferenceManager.getFloatValue("STEP_COUNT"));
		DataClass.setMONEY(preferenceManager.getFloatValue("MONEY"));
		DataClass.setREWARDS(preferenceManager.getIntValue("REWARDS"));
		DataClass.setAFFINITY(preferenceManager.getIntValue("AFFINITY"));
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
