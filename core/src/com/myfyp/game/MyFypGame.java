package com.myfyp.game;

import com.badlogic.gdx.Game;
import com.myfyp.game.screen.GameScreen;

public class MyFypGame extends Game {
	private Game game;

	public MyFypGame(){
		game = this;
	}
	
	@Override
	public void create () {
		this.setScreen(new GameScreen(game));
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
