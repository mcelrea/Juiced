package com.mcelrea.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mcelrea.gameTemplate.MyContactFilter;
import com.mcelrea.gameTemplate.Player;

public class GamePlay implements Screen{

	//The world class manages all physics entities, dynamic simulation, and asynchronous queries.
	private World world;
	
	public static Player player1, player2;
	Body ladder1, ladder2, ladder3, ladder4;

	//draws boxes, joints, velocities, contacts, lets us debug our game
	private Box2DDebugRenderer debugRenderer;

	private OrthographicCamera camera; //2d camera

	private final float TIMESTEP = 1 / 60f; //1/60th of a second, 60 FPS
	private final int VELOCITYITERATIONS = 8; //pretty common, makes the world stable
	private final int POSITIONITERATIONS = 3; //pretty common, makes the world stable


	@Override
	public void render(float delta) {
		//set the color to clear the screen to
		Gdx.gl.glClearColor(0, 0, 0, 1);
		//clear the screen to the glClearColor
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//update the players and AI
		update();

		/*
		 * TIMESTEP - the amount of time to simulate
		 * VELOCITYITERATIONS - for the velocity constraint solver
		 * POSITIONITERATIONS - for the position constriaint solver
		 * 
		 * world.step performs collision detection , integration, and constraint solution
		 */
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

		//the camera will follow our box
		//camera.position.set(box.getPosition().x, box.getPosition().y, 0);

		//Recalculates the projection and view matrix of this camera and the Frustum planes. Use this after you've manipulated any of the attributes of the camera.
		camera.update();

		//show the debug objects on the screen
		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height) {
		/*
		 * viewport has nothing to do with the window size
		 * viewport determines how many pixels are shown in the window at a time
		 * It can effect zoom levels because more pixels zooms camera out
		 */
		camera.viewportWidth = width/25;
		camera.viewportHeight = height/25;
	}
	
	public void update()
	{
		updatePlayer1();
		updatePlayer2();
		
		player1.checkCollisions(world);
		player2.checkCollisions(world);
	}
	
	public void updatePlayer1()
	{
		player1.updateSword();
		
		if(Gdx.input.isKeyPressed(Keys.W))
		{
			player1.jump();
		}
		if(Gdx.input.isKeyPressed(Keys.D))
		{
			player1.moveRight();
		}
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			player1.moveLeft();
		}
		if(!Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.A))
		{
			player1.stopXMovement();
		}
		
		if(Gdx.input.isKeyPressed(Keys.W))
		{
			if(ladder1.getFixtureList().get(0).testPoint(player1.getBody().getPosition().x, player1.getBody().getPosition().y))
			{
				player1.moveUp();
			}
			else if(ladder2.getFixtureList().get(0).testPoint(player1.getBody().getPosition().x, player1.getBody().getPosition().y))
			{
				player1.moveUp();
			}
			else if(ladder3.getFixtureList().get(0).testPoint(player1.getBody().getPosition().x, player1.getBody().getPosition().y))
			{
				player1.moveUp();
			}
			else if(ladder4.getFixtureList().get(0).testPoint(player1.getBody().getPosition().x, player1.getBody().getPosition().y))
			{
				player1.moveUp();
			}
		}

	}
	
	public void updatePlayer2()
	{
		player2.updateSword();
		
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			player2.jump();
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			player2.moveRight();
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			player2.moveLeft();
		}
		if(!Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT))
		{
			player2.stopXMovement();
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			if(ladder1.getFixtureList().get(0).testPoint(player2.getBody().getPosition().x, player2.getBody().getPosition().y))
			{
				player2.moveUp();
			}
			else if(ladder2.getFixtureList().get(0).testPoint(player2.getBody().getPosition().x, player2.getBody().getPosition().y))
			{
				player2.moveUp();
			}
			else if(ladder3.getFixtureList().get(0).testPoint(player2.getBody().getPosition().x, player2.getBody().getPosition().y))
			{
				player2.moveUp();
			}
			else if(ladder4.getFixtureList().get(0).testPoint(player2.getBody().getPosition().x, player2.getBody().getPosition().y))
			{
				player2.moveUp();
			}
		}

	}

	@Override
	public void show() {
		//World(gravity, sleep)
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		player1 = new Player(world, -10, 1);
		player2 = new Player(world, 10, 1);
		Body body;
		world.setContactFilter(new MyContactFilter());

		//independent of actual use.  Re-used several times
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();

		/*
		 * Body Types
		 * --------------
		 * DynamicBody - objects which move around and are affected by forces and other dynamic, 
		 *               kinematic and static objects. Dynamic bodies are suitable for any object 
		 *               which needs to move and be affected by forces.
		 * StaticBody - objects which do not move and are not affected by forces. Dynamic bodies 
		 *              are affected by static bodies. Static bodies are perfect for ground, walls, 
		 *              and any object which does not need to move. Static bodies require less 
		 *              computing power.
		 * KinematicBody - Kinematic bodies are somewhat in between static and dynamic bodies. 
		 *                 Like static bodies, they do not react to forces, but like dynamic bodies, 
		 *                 they do have the ability to move. Kinematic bodies are great for things 
		 *                 where you, the programmer, want to be in full control of a body's motion, 
		 *                 such as a moving platform in a platform game.
		 */


		//main bottom platform
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.scl(0,0);
		ChainShape groundShape = new ChainShape();
		groundShape.createChain(new Vector2[]{new Vector2(-16,-10), new Vector2(16,-10)});
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0;
		
		//world.createBody(bodyDef).createFixture(fixtureDef);
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.getFixtureList().get(0).setUserData("ground");
		groundShape.dispose();

		//left middle platform
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.scl(0,0);
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[]{new Vector2(-16,-3), new Vector2(-5,-3)});
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.getFixtureList().get(0).setUserData("ground");
		groundShape.dispose();

		//right middle platform
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.scl(0,0);
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[]{new Vector2(5,-3), new Vector2(16,-3)});
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.getFixtureList().get(0).setUserData("ground");
		groundShape.dispose();

		//middle platform
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.scl(0,0);
		groundShape = new ChainShape();
		groundShape.createChain(new Vector2[]{new Vector2(-10,4), new Vector2(10,4)});
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.getFixtureList().get(0).setUserData("ground");
		groundShape.dispose();
		
		createLadder1();
		createLadder2();
		createLadder3();
		createLadder4();
		createWonderBox();
	}
	
	public void createWonderBox()
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		Body body;
		
		bodyDef.type = BodyType.StaticBody;
		PolygonShape rectangle = new PolygonShape();
		rectangle.set(new Vector2[]{new Vector2(-3,0),
									new Vector2(3,0),
									new Vector2(3,-5),
									new Vector2(-3,-5),
									new Vector2(-3,0)});
		fixtureDef.shape = rectangle;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.getFixtureList().get(0).setUserData("wonder box");
		rectangle.dispose();
	}
	
	public void createLadder1()
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.KinematicBody;
		PolygonShape rectangle = new PolygonShape();
		rectangle.set(new Vector2[]{new Vector2(-7,-3f),
									new Vector2(-6,-3f),
									new Vector2(-6,-10),
									new Vector2(-7,-10),
									new Vector2(-7,-3f)});
		fixtureDef.shape = rectangle;
		fixtureDef.filter.groupIndex = 1;
		ladder1 = world.createBody(bodyDef);
		ladder1.createFixture(fixtureDef);
		ladder1.getFixtureList().get(0).setUserData("ladder");
		rectangle.dispose();
	}
	
	public void createLadder2()
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.KinematicBody;
		PolygonShape rectangle = new PolygonShape();
		rectangle.set(new Vector2[]{new Vector2(7,-3f),
									new Vector2(6,-3f),
									new Vector2(6,-10),
									new Vector2(7,-10),
									new Vector2(7,-3f)});
		fixtureDef.shape = rectangle;
		fixtureDef.filter.groupIndex = 1;
		ladder2 = world.createBody(bodyDef);
		ladder2.createFixture(fixtureDef);
		ladder2.getFixtureList().get(0).setUserData("ladder");
		rectangle.dispose();
	}
	
	public void createLadder3()
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.KinematicBody;
		PolygonShape rectangle = new PolygonShape();
		//width 1
		//height is 7
		rectangle.set(new Vector2[]{new Vector2(-9,-3f),
									new Vector2(-8,-3f),
									new Vector2(-8,4),
									new Vector2(-9,4),
									new Vector2(-9,-3f)});
		fixtureDef.shape = rectangle;
		fixtureDef.filter.groupIndex = 1;
		ladder3 = world.createBody(bodyDef);
		ladder3.createFixture(fixtureDef);
		ladder3.getFixtureList().get(0).setUserData("ladder");
		rectangle.dispose();
	}
	
	public void createLadder4()
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.KinematicBody;
		PolygonShape rectangle = new PolygonShape();
		//width 1
		//height is 7
		rectangle.set(new Vector2[]{new Vector2(9,-3f),
									new Vector2(8,-3f),
									new Vector2(8,4),
									new Vector2(9,4),
									new Vector2(9,-3f)});
		fixtureDef.shape = rectangle;
		fixtureDef.filter.groupIndex = 1;
		ladder4 = world.createBody(bodyDef);
		ladder4.createFixture(fixtureDef);
		ladder4.getFixtureList().get(0).setUserData("ladder");
		rectangle.dispose();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		//dispose = get rid of resources, to free up memory
		//failure to do this will result in memory leaks and your
		//game will "eat" resources!
		world.dispose();
		debugRenderer.dispose();
	}

}