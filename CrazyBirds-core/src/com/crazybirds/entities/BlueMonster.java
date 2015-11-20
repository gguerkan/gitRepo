package com.crazybirds.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.crazybirds.util.CollisionInformation;
import com.crazybirds.util.Const;

public class BlueMonster extends Enemy {

	private Animation flyAnimation;

	public BlueMonster(World world, float x, float y) {
		super(world, x, y);
		flyAnimation = RessourceManager.instance.blueMonsterAssets.fly;
		xCorrection = 40;
	}


	@Override
	public void render(SpriteBatch batch) {

		region = (AtlasRegion) flyAnimation.getKeyFrame(stateTime);
		super.render(batch);

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
		shape.setAsBox(0.4f, 0.5f);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 5;
		fixDef.friction = 0;
		fixDef.isSensor = true;
		fixDef.filter.categoryBits = Const.FilterEnemy;
		fixDef.filter.maskBits = Const.FilterHero | Const.FilterBullet;

		body.createFixture(fixDef).setUserData(
				new CollisionInformation(this, Const.EnemyID));
	}

}
