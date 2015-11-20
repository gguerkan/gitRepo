package com.crazybirds.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.crazybirds.util.CollisionInformation;
import com.crazybirds.util.Const;
import com.crazybirds.util.EntityState;

public abstract class Hero extends SimulationEntity {

	protected Animation shootAnimation;
	protected Animation deadAnimation;

	protected AtlasRegion bullet;
	protected Array<Body> activeBullets;
	protected Array<Body> removeBullets;

	protected FixtureDef fixDef;
	protected int goldPoints;

	public Hero(World world, float x, float y) {
		super(world, x, y);
		bullet = RessourceManager.instance.globalAssets.birdBullet;
		activeBullets = new Array<Body>();
		removeBullets = new Array<Body>();

	}

	@Override
	protected void createBody() {

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.65f, 0.5f);

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
		fixDef.isSensor = true;
		fixDef.filter.categoryBits = Const.FilterHero;
		fixDef.filter.maskBits = Const.FilterGround | Const.FilterObstacle
				| Const.FilterObstacle | Const.FilterCollectable;

		body.createFixture(fixDef).setUserData(
				new CollisionInformation(this, Const.HeroID));

	}

	protected void checkIfShootIsFinished() {

		if (currentState == EntityState.SHOOT
				&& shootAnimation.isAnimationFinished(stateTime)) {
			currentState = EntityState.FLY;
		}
	}

	@Override
	public void render(SpriteBatch batch) {

		checkResetStateTime();

		switch (currentState) {
		case FLY:

			region = (AtlasRegion) flyAnimation.getKeyFrame(stateTime);
			oldState = EntityState.FLY;

			break;

		case SHOOT:
			region = (AtlasRegion) shootAnimation.getKeyFrame(stateTime);
			oldState = EntityState.SHOOT;

			break;

		case HURT:
			region = (AtlasRegion) deadAnimation.getKeyFrame(stateTime);
			oldState = EntityState.HURT;

			break;

		default:
			region = (AtlasRegion) flyAnimation.getKeyFrame(stateTime);
			oldState = EntityState.FLY;

			break;
		}

		renderBullets(batch);
		checkIfShootIsFinished();
		super.render(batch);

	}

	public void shootFire() {

		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		def.position.x = this.body.getPosition().x + 0.5f;
		def.position.y = this.body.getPosition().y - 0.18f;
		def.gravityScale = 0;
		Body bodyBullet = world.createBody(def);
		activeBullets.add(bodyBullet);

		bodyBullet.setLinearVelocity(5, 0);

		PolygonShape bulletShape = new PolygonShape();
		bulletShape.setAsBox(0.2f, 0.1f);
		FixtureDef fixdef = new FixtureDef();
		fixdef.shape = bulletShape;
		fixdef.friction = 1;
		fixdef.density = 1;
		fixdef.isSensor = true;
		fixdef.filter.categoryBits = Const.FilterBullet;
		fixdef.filter.maskBits = Const.FilterEnemy | Const.FilterObstacle;

		bodyBullet.createFixture(fixdef).setUserData(
				new CollisionInformation(this, Const.BulletID));
		RessourceManager.instance.globalAssets.shootSound.play();

	}

	private void renderBullets(SpriteBatch batch) {

		checkifBulletsActive();

		for (Body b : activeBullets) {

			batch.draw(bullet, b.getPosition().x * 100 - 24f,
					b.getPosition().y * 100 - 16f, originXOffset * 100,
					originYOffset * 100, bullet.getRegionWidth(),
					bullet.getRegionHeight(), 0.35f, 0.35f, body.getAngle()
							* MathUtils.radiansToDegrees);
		}

	}

	private void checkifBulletsActive() {

		for (Body b : activeBullets) {

			if (b.getFixtureList().size == 0) {
				removeBullets.add(b);
			}

		}
		for (Body b : removeBullets) {
			activeBullets.removeValue(b, false);
		}

		removeBullets.clear();

	}

	public void addPoint(int i) {

		goldPoints += i;
	}

	public int getGoldPoints() {
		return goldPoints;
	}

	

}
