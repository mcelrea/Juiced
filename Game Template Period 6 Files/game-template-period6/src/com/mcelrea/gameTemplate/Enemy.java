package com.mcelrea.gameTemplate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mcelrea.screens.GamePlay;

public class Enemy 
{
	Body myBody;
	Fixture myFixture;
	float speed;
	final float MAXSPEED = 4;
	private boolean grounded;
	private int id;
	private static int count=0;
	private Sprite leftImage, rightImage;
	private boolean facingLeft;

	boolean bulletAlive;
	float shootTime = 0;

	public Enemy(World world, float x, float y)
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();

		//x = 0 is the middle of the screen
		if (x < 0)
			facingLeft = false;
		else
			facingLeft = true;

		leftImage = new Sprite(new Texture("img/robot1.png"));
		leftImage.setSize(1.4f, 1.4f);
		rightImage = new Sprite(new Texture("img/robot1.png"));
		rightImage.flip(true, false);
		rightImage.setSize(1.4f, 1.4f);

		bodyDef.type = BodyType.DynamicBody;
		PolygonShape box = new PolygonShape();
		box.setAsBox(0.7f, 0.7f);
		bodyDef.position.x = x;
		bodyDef.position.y = y;
		fixtureDef.shape = box;
		fixtureDef.density = 100f;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0.75f;
		myBody = world.createBody(bodyDef);
		myBody.setFixedRotation(true);
		myFixture = myBody.createFixture(fixtureDef);
		id = count;
		myBody.getFixtureList().get(0).setUserData(new EnemyFixtureData("enemy", this));
		count++;

	}//end constructor

	public void draw(SpriteBatch batch)
	{
		if(facingLeft)
		{
			leftImage.setPosition(myBody.getPosition().x-0.7f, myBody.getPosition().y-0.7f);
			leftImage.draw(batch);
		}
		else
		{
			rightImage.setPosition(myBody.getPosition().x-0.7f, myBody.getPosition().y-0.7f);
			rightImage.draw(batch);
		}
	}

	public void act(World world)
	{
		shootTime += Gdx.graphics.getDeltaTime();
		if(shootTime > 1)
		{
			shootTime = 0;
			BodyDef bodyDef = new BodyDef();
			FixtureDef fixtureDef = new FixtureDef();		

			bodyDef.type = BodyType.DynamicBody;
			CircleShape circle = new CircleShape();
			circle.setRadius(0.1f);

			if(!facingLeft)
				bodyDef.position.x = myBody.getPosition().x+2;
			else
				bodyDef.position.x = myBody.getPosition().x-2;

			bodyDef.position.y = myBody.getPosition().y;
			fixtureDef.shape = circle;
			fixtureDef.density = 100f;
			fixtureDef.restitution = 0;
			fixtureDef.friction = 0.75f;
			Body bullet = world.createBody(bodyDef);
			bullet.setFixedRotation(true);
			bullet.createFixture(fixtureDef);
			bullet.getFixtureList().get(0).setUserData(new BulletFixtureData("enemy_bullet", this));
			bullet.setBullet(true);
			bullet.setGravityScale(0);
			bulletAlive = true;
			if(!facingLeft)
				bullet.applyLinearImpulse(100, 0, bullet.getPosition().x, bullet.getPosition().y, true);//bullet.applyForceToCenter(5000, 0, true);
			else
				bullet.applyLinearImpulse(-100, 0, bullet.getPosition().x, bullet.getPosition().y, true);//bullet.applyForceToCenter(-5000, 0, true);

			GamePlay.bullets.add(bullet);
			circle.dispose();
		}

	}//end act

	public boolean isGrounded() {
		return grounded;
	}//end isGrounded

	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}//end setGrounded

	public boolean isBulletAlive() {
		return bulletAlive;
	}

	public void setBulletAlive(boolean bulletAlive) {
		this.bulletAlive = bulletAlive;
	}



}//end Enemy class










