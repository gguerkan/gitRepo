package com.crazybirds.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.crazybirds.util.Const;
import com.crazybirds.util.SimulationEntityList;

public class PinkyWorldFactory implements AbstractEntityFactory {

	private World world;

	public PinkyWorldFactory(World world) {

		this.world = world;
	}

	@Override
	public SimulationEntity createEntity(int id, Vector2 position) {

		if (id == Const.KingBirdID) {

			return new KingBird(world, position.x, position.y);
		}

		if (id == Const.PencilMonsterID) {

			return new PencilMonster(world, position.x, position.y);
		}

		if (id == Const.PinkBirdID) {

			return new PinkBird(world, position.x, position.y);
		}
		
		if (id == Const.GoldCoinID) {

			return new GoldCoin(world, position.x, position.y);
		}

		if (id == Const.BlueMonsterID) {

			return new BlueMonster(world, position.x, position.y);
		}

		if (id == Const.ObstacleID) {

			int number = MathUtils.random(6);
			Obstacle obstacle = new Obstacle(world, position.x, position.y);
			obstacle.setNumber(number);
			SimulationEntityList.getInstance().addEntity(obstacle);
			return obstacle;
		}

		Gdx.app.debug("Error", "Factory could not create Entity with id: " + id);

		return null;

	}

	public void createRandomEnemy(float yPosition) {

		final float xPos = 12;

		int number = MathUtils.random(4);
		switch (number) {

		case 0:
			SimulationEntityList.getInstance().addEntity(
					new HeartMonster(world, xPos, yPosition));
			break;

		case 1:
			SimulationEntityList.getInstance().addEntity(
					new BlueMonster(world, xPos, yPosition));
			break;

		case 2:
			SimulationEntityList.getInstance().addEntity(
					new PencilMonster(world, xPos, yPosition));
			break;

		case 3:
			break;

		}

	}

}
