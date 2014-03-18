package com.mcelrea.gameTemplate;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mcelrea.screens.GamePlay;

public class MyContactFilter implements ContactFilter{

	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {

		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////player and player collision///////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
		if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("player"))
		{
			return false;
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////end player and player collision/////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////player and sword collision//////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
		if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("sword"))
		{
			return false;
		}
		if(fixtureA.getUserData().equals("sword") && fixtureB.getUserData().equals("player"))
		{
			return false;
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////end player and sword collision//////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////player and ladder collision///////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
		if(fixtureA.getUserData().equals("ladder") && fixtureB.getUserData().equals("player"))
		{
			return false;
		}
		if(fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("ladder"))
		{
			return false;
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////end player and ladder collision/////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////



		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////player and wonder box collision///////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
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
		/////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////end player and wonder box collision////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////




		/////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////player and ground collision//////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
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
		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////end player and ground collision///////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////


		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////enemy and ground collision////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
		if(fixtureB.getUserData() instanceof EnemyFixtureData)
		{
			EnemyFixtureData ef = (EnemyFixtureData) fixtureB.getUserData();
			if(fixtureA.getUserData().equals("ground"))
			{
				ef.getEnemy().setGrounded(true);
			}
		}
		if(fixtureA.getUserData() instanceof EnemyFixtureData)
		{
			EnemyFixtureData ef = (EnemyFixtureData) fixtureA.getUserData();
			if(fixtureB.getUserData().equals("ground"))
			{
				ef.getEnemy().setGrounded(true);
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////end enemy and ground collision///////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////





		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////enemy and ladder collision////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
		if(fixtureB.getUserData() instanceof EnemyFixtureData)
		{
			if(fixtureA.getUserData().equals("ladder"))
			{
				return false;
			}
		}
		if(fixtureA.getUserData() instanceof EnemyFixtureData)
		{
			if(fixtureB.getUserData().equals("ladder"))
			{
				return false;
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////end enemy and ladder collision///////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////


		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////enemy and enemy bullet collision//////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
		if(fixtureA.getUserData().equals("enemy") && fixtureB.getUserData().equals("enemy_bullet"))
		{
			return false;
		}
		if(fixtureA.getUserData().equals("enemy_bullet") && fixtureB.getUserData().equals("enemy"))
		{
			return false;
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////end enemy and enemy bullet collision///////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////ladder and enemy bullet collision//////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
		if(fixtureA.getUserData().equals("ladder") && fixtureB.getUserData().equals("enemy_bullet"))
		{
			return false;
		}
		if(fixtureA.getUserData().equals("enemy_bullet") && fixtureB.getUserData().equals("ladder"))
		{
			return false;
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////end ladder and enemy bullet collision///////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////enemy bullet and enemy bullet collision//////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
		if(fixtureA.getUserData().equals("enemy_bullet") && fixtureB.getUserData().equals("enemy_bullet"))
		{
			return false;
		}
		if(fixtureA.getUserData().equals("enemy_bullet") && fixtureB.getUserData().equals("enemy_bullet"))
		{
			return false;
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////end enemy bullet and enemy bullet collision///////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////

		return true;
	}

}









