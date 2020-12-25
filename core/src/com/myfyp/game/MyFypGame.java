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
	PreferenceManager preferenceManager;


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
		DataClass.setStepCount(preferenceManager.getFloatValue("STEP_COUNT"));
		DataClass.setMONEY(preferenceManager.getFloatValue("MONEY"));

		/*
		DataClass.setUpgrade1Multiplier(preferenceManager.getFloatValue("UPGRADE1_MULTIPLIER"));
		DataClass.setUpgrade2Multiplier(preferenceManager.getFloatValue("UPGRADE2_MULTIPLIER"));
		DataClass.setPRICE1(preferenceManager.getFloatValue("UPGRADE1_PRICE"));
		DataClass.setPRICE2(preferenceManager.getFloatValue("UPGRADE2_PRICE"));

		 */
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
		//preferenceManager.setFloatValue("UPGRADE1_MULTIPLIER", DataClass.getUpgrade1Multiplier());
		//preferenceManager.setFloatValue("UPGRADE2_MULTIPLIER", DataClass.getUpgrade2Multiplier());
		//preferenceManager.setFloatValue("UPGRADE1_PRICE", DataClass.getPRICE1());
		//preferenceManager.setFloatValue("UPGRADE2_PRICE", DataClass.getPRICE2());
		super.dispose();
	}

}
