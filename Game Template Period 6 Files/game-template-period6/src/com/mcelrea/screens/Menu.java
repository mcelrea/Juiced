package com.mcelrea.screens;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Menu implements Screen
{
	private Stage stage; //a 2d scene that fills the whole screen with a viewport
	private TextureAtlas atlas; //loads files creating by TexturePacker
	private Skin skin; //stores UI resources (texture regions, ninepatches, fonts, colors, etc.)
	private Table table;
	private TextButton buttonExit, buttonPlay;
	private Label heading;
	private TweenManager tweenManager;
	
	private SpriteBatch batch;
	private Sprite background;
	private Sprite title;

	@Override
	public void render(float delta) {
		//set the color to clear the screen to
		Gdx.gl.glClearColor(0, 0, 0, 1);
		//clear the screen to the glClearColor
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//table.drawDebug(stage);
		
		batch.begin();
		background.draw(batch);
		title.draw(batch);
		batch.end();

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		
		table.invalidateHierarchy(); //recalculate the tables layout
		table.setSize(width, height);

	}

	@Override
	public void show() {
		stage = new Stage();
		
		batch = new SpriteBatch();
		Texture texture = new Texture("img/samara_background_by_lunar47-d4rrbny.png");
		background = new Sprite(texture);
		texture = new Texture("img/gameTitle.png");
		title = new Sprite(texture);
		title.setPosition(100, 300);

		//listen to the stage, button presses
		Gdx.input.setInputProcessor(stage);

		atlas = new TextureAtlas("ui/texture packer/atlas.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);

		table = new Table(skin);
		table.setFillParent(true);//table fills the whole screen
		table.debug();

		
		buttonPlay = new TextButton("Play", skin);
		buttonPlay.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new GamePlay());
			}
		});
		
		buttonExit = new TextButton("Exit", skin);
		buttonExit.addListener(new ClickListener () {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		
		table.add(buttonPlay).pad(10).padTop(200);
		table.row();
		table.add(buttonExit);

		stage.addActor(table);

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		atlas.dispose();
		skin.dispose();
		stage.dispose();

	}

}
