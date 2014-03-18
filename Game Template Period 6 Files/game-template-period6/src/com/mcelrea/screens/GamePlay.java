package com.mcelrea.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mcelrea.gameTemplate.Enemy;
import com.mcelrea.gameTemplate.MyContactFilter;
import com.mcelrea.gameTemplate.Player;

public class GamePlay implements Screen{

	//The world class manages all physics entities, dynamic simulation, and asynchronous queries.
	private World world;
	
	public static Player player1, player2;
	Body ladder1, ladder2, ladder3, ladder4;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();//an empty list of enemies

	//draws boxes, joints, velocities, contacts, lets us debug our game
	private Box2DDebugRenderer debugRenderer;

	private OrthographicCamera camera; //2d camera

	private final float TIMESTEP = 1 / 60f; //1/60th of a second, 60 FPS
	private final int VELOCITYITERATIONS = 8; //pretty common, makes the world stable
	private final int POSITIONITERATIONS = 3; //pretty common, makes the world stable

	SpriteBatch batch;
	Sprite ladder1_sprite, ladder2_sprite, ladder3_sprite, ladder4_sprite;
	Sprite topPlatform, leftPlatform, rightPlatform, ground;

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
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		topPlatform.draw(batch);
		leftPlatform.draw(batch);
		rightPlatform.draw(batch);
		ground.draw(batch);
		ladder1_sprite.draw(batch);
		ladder2_sprite.draw(batch);
		ladder3_sprite.draw(batch);
		ladder4_sprite.draw(batch);
		
		for(int i=0; i < enemies.size(); i++)
		{
			enemies.get(i).draw(batch);
		}
		
		player1.draw(batch);
		player2.draw(batch);
		batch.end();

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
		
		updateEnemies();
	}
	
	public void updateEnemies()
	{
		for(int i=0; i < enemies.size(); i++)
		{
			enemies.get(i).act(world);
		}
	}
	
	public void updatePlayer1()
	{
		player1.updateSword();
		
		/*
		 * Movement Keys: W, A, D
		 * W -> Jump
		 * A -> Left
		 * D -> Right
		 */
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
		//if the 'D' and 'A' keys are NOT(!) being pressed
		if(!Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.A))
		{
			player1.stopXMovement();//stop movement. stops the "sliding" effect.
		}
		/*
		 * if 'W' is pressed we check to see if player1 is on a ladder.  There are
		 * 4 ladders, so 4 inner ifs.  If the player is on a ladder when the 'W' key
		 * is pressed, we move the player up.
		 * */
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
		
		/*
		 * Movement Keys: Up Arrow, Left Arrow, Right arrow
		 */
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
		/*
		 * if 'W' is pressed we check to see if player2 is on a ladder.  There are
		 * 4 ladders, so 4 inner ifs.  If the player is on a ladder when the 'W' key
		 * is pressed, we move the player up.
		 * */
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
	
	public void addEnemies()
	{
		Enemy e = new Enemy(world, -14, 10);
		enemies.add(e);
		
		e = new Enemy(world, 14, 10);
		enemies.add(e);
	}

	@Override
	public void show() {
		//World(gravity, sleep)
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		
		//create the players in the world at (x,y) location
		player1 = new Player(world, -10, 1, "img/player1.png");
		player2 = new Player(world, 10, 1, "img/player2.png");
		
		addEnemies();
		
		Body body;
		
		/*
		 * link the MyContactFilter class to the world.  The world will now
		 * check the MyContactFilter class anytime two box2d objects touch.
		 * We can define what happens when two objects touch inside the
		 * MyContactFilter class.  For example, the two players cannot hit
		 * eachother, when an enemy hits the player take health away, don't
		 * let the players own bullets hit them, etc.
		 */
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
		ChainShape groundShape = new ChainShape();//ChainShapes are lines that must contain at least 2 (x,y) points but can contain many (x,y) points
		groundShape.createChain(new Vector2[]{new Vector2(-16,-10), new Vector2(16,-10)});//create the actual line by passing the points in
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.getFixtureList().get(0).setUserData("ground");//naming each fixture is important.  We will use this name in the MyContactFilter to see what two things are touching
		groundShape.dispose();

		//left middle platform
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.scl(0,0);
		groundShape = new ChainShape();//ChainShapes are lines that must contain at least 2 (x,y) points but can contain many (x,y) points
		groundShape.createChain(new Vector2[]{new Vector2(-16,-3), new Vector2(-5,-3)});//create the actual line by passing the points in
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.getFixtureList().get(0).setUserData("ground");//naming each fixture is important.  We will use this name in the MyContactFilter to see what two things are touching
		groundShape.dispose();

		//right middle platform
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.scl(0,0);
		groundShape = new ChainShape();//ChainShapes are lines that must contain at least 2 (x,y) points but can contain many (x,y) points
		groundShape.createChain(new Vector2[]{new Vector2(5,-3), new Vector2(16,-3)}); //create the actual line by passing the points in
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.getFixtureList().get(0).setUserData("ground");//naming each fixture is important.  We will use this name in the MyContactFilter to see what two things are touching
		groundShape.dispose();

		//middle platform
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.scl(0,0);
		groundShape = new ChainShape();//ChainShapes are lines that must contain at least 2 (x,y) points but can contain many (x,y) points
		groundShape.createChain(new Vector2[]{new Vector2(-10,4), new Vector2(10,4)});//create the actual line by passing the points in
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.getFixtureList().get(0).setUserData("ground");//naming each fixture is important.  We will use this name in the MyContactFilter to see what two things are touching
		groundShape.dispose();
		
		//create the ladders by calling helper methods that create each one
		createLadder1();
		createLadder2();
		createLadder3();
		createLadder4();
		
		//create the ladder sprites
		ladder1_sprite = new Sprite(new Texture("img/ladder.png"));
		ladder1_sprite.setSize(1, 7);
		ladder1_sprite.setPosition(-9, -3);
		
		ladder2_sprite = new Sprite(new Texture("img/ladder.png"));
		ladder2_sprite.setSize(1, 7);
		ladder2_sprite.setPosition(8, -3);
		
		ladder3_sprite = new Sprite(new Texture("img/ladder.png"));
		ladder3_sprite.setSize(1, 7);
		ladder3_sprite.setPosition(-7, -10);
		
		ladder4_sprite = new Sprite(new Texture("img/ladder.png"));
		ladder4_sprite.setSize(1, 7);
		ladder4_sprite.setPosition(6, -10);
		
		topPlatform = new Sprite(new Texture("img/topBrickPlatform.png"));
		topPlatform.setSize(20, 0.5f);
		topPlatform.setPosition(-10, 3.5f);
		
		leftPlatform = new Sprite(new Texture("img/sideBrickPlatform.png"));
		leftPlatform.setSize(11, 0.5f);
		leftPlatform.setPosition(-16, -3.5f);
		
		rightPlatform = new Sprite(new Texture("img/sideBrickPlatform.png"));
		rightPlatform.setSize(11, 0.5f);
		rightPlatform.setPosition(5, -3.5f);
		
		ground = new Sprite(new Texture("img/stoneGround.png"));
		ground.setSize(32, 2f);
		ground.setPosition(-16, -12f);
		
		
		//create the wonder box by calling a helper method that creates it for us
		createWonderBox();
	}
	
	public void createWonderBox()
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		Body body;
		
		bodyDef.type = BodyType.StaticBody;
		/*
		 * PolygonShape makes a polygon
		 * Always create a PolygonShape with one more point than the number of sides you want.
		 * For example, a triangle will have 4 points, a square 5 points, a hexagon 7 points, etc.
		 * Always make sure the last point is the same as the first point in order to "close" the shape off
		 */
		PolygonShape rectangle = new PolygonShape();
		rectangle.set(new Vector2[]{new Vector2(-3,0),
									new Vector2(3,0),
									new Vector2(3,-5),
									new Vector2(-3,-5),
									new Vector2(-3,0)});
		fixtureDef.shape = rectangle;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.getFixtureList().get(0).setUserData("wonder box");//naming each fixture is important.  We will use this name in the MyContactFilter to see what two things are touching
		rectangle.dispose();
	}
	
	public void createLadder1()
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.KinematicBody;
		/*
		 * PolygonShape makes a polygon
		 * Always create a PolygonShape with one more point than the number of sides you want.
		 * For example, a triangle will have 4 points, a square 5 points, a hexagon 7 points, etc.
		 * Always make sure the last point is the same as the first point in order to "close" the shape off
		 */
		PolygonShape rectangle = new PolygonShape();
		//width=1, height=7 for the ladder dimensions
		rectangle.set(new Vector2[]{new Vector2(-7,-3f),
									new Vector2(-6,-3f),
									new Vector2(-6,-10),
									new Vector2(-7,-10),
									new Vector2(-7,-3f)});
		fixtureDef.shape = rectangle;
		fixtureDef.filter.groupIndex = 1;
		ladder1 = world.createBody(bodyDef);
		ladder1.createFixture(fixtureDef);
		ladder1.getFixtureList().get(0).setUserData("ladder");//naming each fixture is important.  We will use this name in the MyContactFilter to see what two things are touching
		rectangle.dispose();
	}
	
	public void createLadder2()
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.KinematicBody;
		/*
		 * PolygonShape makes a polygon
		 * Always create a PolygonShape with one more point than the number of sides you want.
		 * For example, a triangle will have 4 points, a square 5 points, a hexagon 7 points, etc.
		 * Always make sure the last point is the same as the first point in order to "close" the shape off
		 */
		PolygonShape rectangle = new PolygonShape();
		//width=1, height=7 for the ladder dimensions
		rectangle.set(new Vector2[]{new Vector2(7,-3f),
									new Vector2(6,-3f),
									new Vector2(6,-10),
									new Vector2(7,-10),
									new Vector2(7,-3f)});
		fixtureDef.shape = rectangle;
		fixtureDef.filter.groupIndex = 1;
		ladder2 = world.createBody(bodyDef);
		ladder2.createFixture(fixtureDef);
		ladder2.getFixtureList().get(0).setUserData("ladder");//naming each fixture is important.  We will use this name in the MyContactFilter to see what two things are touching
		rectangle.dispose();
	}
	
	public void createLadder3()
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.KinematicBody;
		/*
		 * PolygonShape makes a polygon
		 * Always create a PolygonShape with one more point than the number of sides you want.
		 * For example, a triangle will have 4 points, a square 5 points, a hexagon 7 points, etc.
		 * Always make sure the last point is the same as the first point in order to "close" the shape off
		 */
		PolygonShape rectangle = new PolygonShape();
		//width=1, height=7 for the ladder dimensions
		rectangle.set(new Vector2[]{new Vector2(-9,-3f),
									new Vector2(-8,-3f),
									new Vector2(-8,4),
									new Vector2(-9,4),
									new Vector2(-9,-3f)});
		fixtureDef.shape = rectangle;
		fixtureDef.filter.groupIndex = 1;
		ladder3 = world.createBody(bodyDef);
		ladder3.createFixture(fixtureDef);
		ladder3.getFixtureList().get(0).setUserData("ladder");//naming each fixture is important.  We will use this name in the MyContactFilter to see what two things are touching
		rectangle.dispose();
	}
	
	public void createLadder4()
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.KinematicBody;
		/*
		 * PolygonShape makes a polygon
		 * Always create a PolygonShape with one more point than the number of sides you want.
		 * For example, a triangle will have 4 points, a square 5 points, a hexagon 7 points, etc.
		 * Always make sure the last point is the same as the first point in order to "close" the shape off
		 */
		PolygonShape rectangle = new PolygonShape();
		//width=1, height=7 for the ladder dimensions
		rectangle.set(new Vector2[]{new Vector2(9,-3f),
									new Vector2(8,-3f),
									new Vector2(8,4),
									new Vector2(9,4),
									new Vector2(9,-3f)});
		fixtureDef.shape = rectangle;
		fixtureDef.filter.groupIndex = 1;
		ladder4 = world.createBody(bodyDef);
		ladder4.createFixture(fixtureDef);
		ladder4.getFixtureList().get(0).setUserData("ladder");//naming each fixture is important.  We will use this name in the MyContactFilter to see what two things are touching
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
