package com.mcelrea.gameTemplate;

public class BulletFixtureData 
{
	String name;
	Enemy enemy;
	
	public BulletFixtureData(String name, Enemy enemy) {
		super();
		this.name = name;
		this.enemy = enemy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}
	
	
}
