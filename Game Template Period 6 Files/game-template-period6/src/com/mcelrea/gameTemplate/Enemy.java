package com.mcelrea.gameTemplate;

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
	
	Body bullet;
	
	public Enemy(World world, float x, float y)
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
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
		
	}//end contructor
	
	public void draw(SpriteBatch batch)
	{
		leftImage.setPosition(myBody.getPosition().x-0.7f, myBody.getPosition().y-0.7f);
		leftImage.draw(batch);
	}
	
	public void act(World world)
	{
		/*
		if(grounded && myBody.getLinearVelocity().x < MAXSPEED)
			myBody.applyLinearImpulse(2, 0, myBody.getPosition().x, myBody.getPosition().y, true);
		*/
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.DynamicBody;
		CircleShape circle = new CircleShape();
		circle.setRadius(0.5f);
		bodyDef.position.x = myBody.getPosition().x;
		bodyDef.position.y = myBody.getPosition().y;
		fixtureDef.shape = circle;
		fixtureDef.density = 100f;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0.75f;
		bullet = world.createBody(bodyDef);
		bullet.setFixedRotation(true);
		bullet.createFixture(fixtureDef);
		bullet.getFixtureList().get(0).setUserData("enemy_bullet");
		/*
		bullet.setBullet(true);
		*/
		
	}//end act

	public boolean isGrounded() {
		return grounded;
	}//end isGrounded

	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}//end setGrounded
	
	
}//end Enemy class










