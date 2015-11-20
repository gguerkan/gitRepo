package com.crazybirds.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.crazybirds.util.CollisionInformation;
import com.crazybirds.util.Const;

public class PencilMonster extends Enemy {

	private Animation flyAnimation;

	public PencilMonster(World world, float x, float y) {
		super(world, x, y);
		xCorrection = 70f;
		flyAnimation = RessourceManager.instance.pencilMonster.fly;
	}

	@Override
	public void render(SpriteBatch batch) {

		region = (AtlasRegion) flyAnimation.getKeyFrame(stateTime);

		batch.draw(region, (posX - originXOffset) * 100f -100,
				(posY - originYOffset) * 100f - 70, originXOffset * 100f,
				originYOffset * 100f, region.getRegionWidth(),
				region.getRegionHeight(), 0.5f, 0.5f, body.getAngle()
						* MathUtils.radiansToDegrees);

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
		shape.setAsBox(0.8f, 0.28f);

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
