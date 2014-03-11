package com.mcelrea.gameTemplate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Player
{
	Body body; //the box2D representation of the player
	Body sword;
	Fixture myFixture; //the rectangle fixture of the player
	float speed;
	boolean jumping;
	float rot = 0.0f;
	Sprite rightImage, leftImage;
	boolean facingLeft;

	public Player(World world, float x, float y, String imageFile)
	{
		FixtureDef fixDef = new FixtureDef();
		BodyDef bodyDef = new BodyDef();
		
		rightImage = new Sprite(new Texture(imageFile));
		rightImage.setSize(1f, 2f);
		leftImage = new Sprite(new Texture(imageFile));
		leftImage.flip(true, false);
		leftImage.setSize(1f, 2f);

		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;
		PolygonShape box = new PolygonShape();
		box.setAsBox(0.5f, 1);
		fixDef.shape = box;
		fixDef.restitution = 0;
		fixDef.density = 4;
		fixDef.friction = 0.75f;
		fixDef.filter.groupIndex = -1;
		body = world.createBody(bodyDef);
		myFixture = body.createFixture(fixDef);
		body.getFixtureList().get(0).setUserData("player");
		box.dispose();
		
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(x,y);
		box = new PolygonShape();
		box.setAsBox(0.2f, 0.6f);
		fixDef.shape = box;
		fixDef.restitution = 0;
		fixDef.density = 4;
		fixDef.friction = 0.75f;
		fixDef.filter.groupIndex = -1;
		sword = world.createBody(bodyDef);
		sword.createFixture(fixDef);
		sword.getFixtureList().get(0).setUserData("sword");
		box.dispose();
		
		bodyDef.type = BodyType.KinematicBody;
		PolygonShape tri = new PolygonShape();
		tri.set(new Vector2[]{new Vector2(-0.25f,0.6f),
				              new Vector2(0.20f,0.6f),
				              new Vector2(0.0f,1f), 
				              new Vector2(-0.25f,0.6f)});
		fixDef.shape = tri;
		fixDef.restitution = 0;
		fixDef.density = 4;
		fixDef.friction = 0.75f;
		fixDef.filter.groupIndex = -1;
		sword.createFixture(fixDef);
		sword.getFixtureList().get(1).setUserData("sword");
		tri.dispose();

		speed = 5;
	}
	
	public void draw(SpriteBatch batch)
	{
		rightImage.setPosition(body.getPosition().x - rightImage.getWidth()/2,
				               body.getPosition().y - rightImage.getHeight()/2);
		leftImage.setPosition(body.getPosition().x - leftImage.getWidth()/2,
		                      body.getPosition().y - leftImage.getHeight()/2);
		
		if(facingLeft == false)
			rightImage.draw(batch);
		else
		{
			leftImage.draw(batch);
		}
	}
	
	public void updateSword()
	{
		//sword follows the player
		//sword.setTransform(body.getPosition(), 0);
		sword.setTransform(body.getPosition().x, body.getPosition().y+0.5f, rot);
		rot-=0.1f;
	}
	
	public void moveUp()
	{
		body.setLinearVelocity(body.getLinearVelocity().x, speed);
	}

	public void moveRight()
	{
		body.setLinearVelocity(speed, body.getLinearVelocity().y);
		facingLeft = false;
		//teleport this bad boy!!!! :)
		if(body.getPosition().x > 16)
		{
			body.setTransform(-16, body.getPosition().y, body.getAngle());
		}
	}

	public void moveLeft()
	{
		body.setLinearVelocity(-speed, body.getLinearVelocity().y);
		facingLeft = true;
		//teleport this bad boy!!!! :)
		if(body.getPosition().x < -16)
		{
			body.setTransform(16, body.getPosition().y, body.getAngle());
		}
	}

	public void stopXMovement()
	{
		body.setLinearVelocity(0, body.getLinearVelocity().y);
	}

	public void checkCollisions(World world)
	{
		//a list of everything that can collide
		Array<Contact> contactList = world.getContactList();

		//go through the contact list
		/*
		for(int i=0; i < contactList.size; i++)
		{
			Contact contact = contactList.get(i);

			if((contact.getFixtureA() == myFixture || contact.getFixtureB() == myFixture) && contact.isTouching())
			{
				//if(!contact.getFixtureA().getUserData().equals("ladder") && !contact.getFixtureB().getUserData().equals("ladder"))
				//{
					body.setLinearVelocity(0, 4);
					//System.out.println(contact.getFixtureA().getUserData());
					//System.out.println(contact.getFixtureB().getUserData());
				//}
			}

		}
		*/
	}

	public Body getBody() {
		return body;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	
	
	public void jump()
	{
		if(!jumping)
		{
			body.setLinearVelocity(0, 4);
			jumping = true;
		}
	}
	
}











