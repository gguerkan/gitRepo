package com.crazybirds.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.crazybirds.interfacesImpl.IUserMoveImpl;
import com.crazybirds.util.CollisionInformation;
import com.crazybirds.util.Const;
import com.crazybirds.util.EntityState;

public class PinkBird extends Hero {

	public PinkBird(World world, float x, float y) {
		super(world, x, y);

		mover = new IUserMoveImpl(this);
		ratio = 315 / 268;
		posX = x;
		posY = y;
		flyAnimation = RessourceManager.instance.pinkBirdsAssets.fly;
		shootAnimation = RessourceManager.instance.pinkBirdsAssets.shoot;
		deadAnimation = RessourceManager.instance.pinkBirdsAssets.dead;

	}

	@Override
	protected void createBody() {

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.55f, 0.5f);

		BodyDef bodydef = new BodyDef();
		bodydef.position.x = posX;
		bodydef.position.y = posY;
		bodydef.type = BodyType.DynamicBody;
		bodydef.fixedRotation = true;

		body = world.createBody(bodydef);

		fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 5;
		fixDef.friction = 0;
		fixDef.filter.categoryBits = Const.FilterHero;
		fixDef.filter.maskBits = Const.FilterGround | Const.FilterEnemy
				| Const.FilterObstacle | Const.FilterCollectable;

		body.createFixture(fixDef).setUserData(
				new CollisionInformation(this, Const.HeroID));

	}

	@Override
	protected void correctAxes() {

		if (currentState == EntityState.FLY) {
			yCorrection = 70f;
			xCorrection = 90;
		} else {
			yCorrection = 62;
			xCorrection = 88f;
		}
	}

}
