package com.crazybirds.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.crazybirds.interfacesImpl.IMoveToLeft;
import com.crazybirds.util.CollisionInformation;
import com.crazybirds.util.Const;
import com.crazybirds.util.KillThisBodyList;

public abstract class Enemy extends SimulationEntity {

	public Enemy(World world, float x, float y) {
		super(world, x, y);
		mover = new IMoveToLeft(this);

	}

	@Override
	protected void createBody() {

		BodyDef bodydef = new BodyDef();
		bodydef.position.x = posX;
		bodydef.position.y = posY;
		bodydef.gravityScale = 0;
		bodydef.type = BodyType.DynamicBody;
		bodydef.fixedRotation = true;

		body = world.createBody(bodydef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.65f, 0.5f);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 5;
		fixDef.friction = 0;
		fixDef.isSensor = true;
		fixDef.filter.categoryBits = Const.FilterEnemy;
		fixDef.filter.maskBits = Const.FilterHero | Const.FilterBullet| Const.FilterObstacle;

		body.createFixture(fixDef).setUserData(
				new CollisionInformation(this, Const.EnemyID));

	}

	@Override
	public void update() {
		super.update();

		if (body.getPosition().x < -1) {
			KillThisBodyList.getInstance().addBody(body);

		}
	}

}
