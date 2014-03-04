package com.mcelrea.gameTemplate;

import com.badlogic.gdx.Game;
import com.mcelrea.screens.GamePlay;
import com.mcelrea.screens.Splash;

public class MyGdxGame extends Game {
	
	public static final String TITLE = "Using Box2D", VERSION = "0.0.0.0.1";
	
	@Override
	public void create() {		
		setScreen(new GamePlay());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
		
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
