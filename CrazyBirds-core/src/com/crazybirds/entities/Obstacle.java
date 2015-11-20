package com.crazybirds.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.crazybirds.util.CollisionInformation;
import com.crazybirds.util.Const;

public class Obstacle extends Enemy {

	private int number;
	private boolean isOnTop;

	public Obstacle(World world, float x, float y) {
		super(world, x, y);
		mover.setSpeed(Const.backGroundSpeed);

	}

	private void generateRandomPosition() {
		int i = MathUtils.random.nextInt(2);
		if (i == 0) {
			isOnTop = true;
			xCorrection = -18;
			yCorrection = -114;
		} else {
			isOnTop = false;
			xCorrection = 40;
			yCorrection = 120;
		}

	}

	@Override
	protected void createBody() {
		generateRandomPosition();

		BodyDef bodydef = new BodyDef();

		if (!isOnTop) {
			bodydef.position.x = 12;
			bodydef.position.y = 1.75f;
		} else {
			bodydef.position.x = 12;

			bodydef.position.y = 4f;

		}

		bodydef.gravityScale = 0;
		bodydef.type = BodyType.KinematicBody;
		bodydef.fixedRotation = true;

		body = world.createBody(bodydef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.27f, 1.1f);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 5;
		fixDef.friction = 0;
		fixDef.isSensor = true;
		fixDef.filter.categoryBits = Const.FilterObstacle;
		fixDef.filter.maskBits = Const.FilterHero | Const.FilterBullet;

		body.createFixture(fixDef).setUserData(
				new CollisionInformation(this, Const.ObstacleID));

	}

	@Override
	public void render(SpriteBatch batch) {

		switch (number) {

		case 0:
			region = RessourceManager.instance.obstacleAssets.obstacle1;
			break;
		case 1:
			region = RessourceManager.instance.obstacleAssets.obstacle2;
			break;
		case 2:
			region = RessourceManager.instance.obstacleAssets.obstacle3;
			break;
		case 3:
			region = RessourceManager.instance.obstacleAssets.obstacle4;
			break;
		case 4:
			region = RessourceManager.instance.obstacleAssets.obstacle5;
			break;
		case 5:
			region = RessourceManager.instance.obstacleAssets.obstacle6;
			break;
		case 6:
			region = RessourceManager.instance.obstacleAssets.obstacle7;
			break;
		case 7:
			region = RessourceManager.instance.obstacleAssets.obstacle8;
			break;
		}

		if (!isOnTop) {

			if (region.isFlipX()) {
				region.flip(true, false);
			}

			batch.draw(region, (posX - originXOffset) * 100 - xCorrection,
					(posY - originYOffset) * 100 - yCorrection,
					originXOffset * 100, originYOffset * 100,
					region.getRegionWidth(), region.getRegionHeight(), 0.35f,
					0.35f, 0);
		} else {
			if (!region.isFlipX()) {
				region.flip(true, false);
			}
			batch.draw(region, (posX - originXOffset) * 100 - xCorrection,
					(posY - originYOffset) * 100 - yCorrection,
					originXOffset * 100, originYOffset * 100,
					region.getRegionWidth(), region.getRegionHeight(), 0.35f,
					0.35f, 180);

		}

	}

	public void setNumber(int number) {
		this.number = number;
	}

}
