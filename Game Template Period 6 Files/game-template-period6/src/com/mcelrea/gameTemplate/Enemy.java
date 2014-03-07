package com.mcelrea.gameTemplate;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Enemy 
{
	Body myBody;
	Fixture myFixture;
	float speed;
	final float MAXSPEED = 4;
	private boolean grounded;
	
	public Enemy(World world, float x, float y)
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.DynamicBody;
		PolygonShape box = new PolygonShape();
		box.setAsBox(1, 0.5f);
		bodyDef.position.x = x;
		bodyDef.position.y = y;
		fixtureDef.shape = box;
		fixtureDef.density = 1f;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0.75f;
		myBody = world.createBody(bodyDef);
		myFixture = myBody.createFixture(fixtureDef);
		myBody.getFixtureList().get(0).setUserData("enemy");
		
	}//end contructor
	
	public void act()
	{
		if(grounded && myBody.getLinearVelocity().x < MAXSPEED)
			myBody.applyLinearImpulse(2, 0, myBody.getPosition().x, myBody.getPosition().y, true);
	}//end act

	public boolean isGrounded() {
		return grounded;
	}//end isGrounded

	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}//end setGrounded
	
	
}//end Enemy class










