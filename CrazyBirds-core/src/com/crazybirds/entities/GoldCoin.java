package com.crazybirds.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.crazybirds.interfacesImpl.IMoveToLeft;
import com.crazybirds.util.CollisionInformation;
import com.crazybirds.util.Const;

public class GoldCoin extends SimulationEntity {

	Animation goldCoin;

	public GoldCoin(World world, float x, float y) {
		super(world, x, y);
		goldCoin = RessourceManager.instance.globalAssets.coin;
		mover = new IMoveToLeft(this);
		mover.setSpeed(Const.backGroundSpeed);

		xCorrection = 30;
		yCorrection = 23;

	}

	@Override
	protected void createBody() {

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.2f, 0.2f);

		BodyDef bodydef = new BodyDef();
		bodydef.position.x = posX;
		bodydef.position.y = posY;
		bodydef.type = BodyType.KinematicBody;
		bodydef.fixedRotation = true;
		bodydef.allowSleep = false;
		body = world.createBody(bodydef);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 5;
		fixDef.friction = 0;
		fixDef.isSensor = true;
		fixDef.filter.categoryBits = Const.FilterCollectable;
		fixDef.filter.maskBits = Const.FilterHero ;

		body.createFixture(fixDef).setUserData(
				new CollisionInformation(this, Const.GoldCoinID));

	}

	@Override
	public void render(SpriteBatch batch) {

		stateTime += Gdx.graphics.getDeltaTime();
		region = (AtlasRegion) goldCoin.getKeyFrame(stateTime);

		batch.draw(region, (posX - originXOffset) * 100 - xCorrection,
				(posY - originYOffset) * 100 - yCorrection,
				originXOffset * 100, originYOffset * 100,
				region.getRegionWidth(), region.getRegionHeight(), 0.6f, 0.6f,
				body.getAngle() * MathUtils.radiansToDegrees);
	}

}
