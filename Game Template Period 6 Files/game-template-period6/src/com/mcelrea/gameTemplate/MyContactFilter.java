package com.mcelrea.gameTemplate;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mcelrea.screens.GamePlay;

public class MyContactFilter implements ContactFilter{

	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		
		if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("player"))
		{
			return false;
		}
		if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("sword"))
		{
			return false;
		}
		if(fixtureA.getUserData().equals("sword") && fixtureB.getUserData().equals("player"))
		{
			return false;
		}
		
		//check for ladder collision
		if(fixtureA.getUserData().equals("ladder") && fixtureB.getUserData().equals("player"))
		{
			return false;
		}
		if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("ladder"))
		{
			return false;
		}
		
		if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("wonder box"))
		{
			//if player1
			if(fixtureA == GamePlay.player1.getBody().getFixtureList().get(0))
			{
				GamePlay.player1.setJumping(false);
			}
			else //it is player2
			{
				GamePlay.player2.setJumping(false);
			}
		}
		if(fixtureA.getUserData().equals("wonder box") && fixtureB.getUserData().equals("player"))
		{
			//if player1
			if(fixtureB == GamePlay.player1.getBody().getFixtureList().get(0))
			{
				GamePlay.player1.setJumping(false);
			}
			else //it is player2
			{
				GamePlay.player2.setJumping(false);
			}
		}
		
		//check for player and platform collision
		if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("ground"))
		{
			//if player1
			if(fixtureA == GamePlay.player1.getBody().getFixtureList().get(0))
			{
				GamePlay.player1.setJumping(false);
			}
			else //it is player2
			{
				GamePlay.player2.setJumping(false);
			}
			
			//if player is moving up
			if(fixtureA.getBody().getLinearVelocity().y > 0)
				return false;
		}
		if(fixtureA.getUserData().equals("ground") && fixtureB.getUserData().equals("player"))
		{
			//if player1
			if(fixtureB == GamePlay.player1.getBody().getFixtureList().get(0))
			{
				GamePlay.player1.setJumping(false);
			}
			else //it is player2
			{
				GamePlay.player2.setJumping(false);
			}
			
			//if player is moving up
			if(fixtureB.getBody().getLinearVelocity().y > 0)
				return false;
		}
		
		return true;
	}

}









