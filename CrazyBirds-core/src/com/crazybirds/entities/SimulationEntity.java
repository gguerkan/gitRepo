package com.crazybirds.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.crazybirds.interfaces.IMoveEntity;
import com.crazybirds.util.EntityState;

public abstract class SimulationEntity {

	// Box2d attributes
	protected World world;
	protected Body body;

	// Entity attributes
	protected float posX;
	protected float posY;
	protected float originXOffset;
	protected float originYOffset;
	protected float width;
	protected float height;
	protected float stateTime;
	protected float ratio;

	// Entity behaviour
	protected EntityState currentState;
	protected EntityState oldState;

	// Entity animations
	protected Animation flyAnimation;
	protected AtlasRegion region;

	// Entity movement
	protected IMoveEntity mover;

	// Entity helper variables
	protected float yCorrection;
	protected float xCorrection;

	public SimulationEntity(World world, float x, float y) {
		this.posX = x;
		this.posY = y;
		this.world = world;
		stateTime = 0;
		width = 0;
		height = 0;
		currentState = EntityState.FLY;
		yCorrection = 70f;
		xCorrection = 90f;
		createBody();

	}

	protected void checkResetStateTime() {
		if (oldState != currentState) {
			stateTime = 0;
		}

	}

	protected abstract void createBody();

	public void render(SpriteBatch batch) {

		batch.draw(region, (posX - originXOffset) * 100 - xCorrection,
				(posY - originYOffset) * 100 - yCorrection,
				originXOffset * 100, originYOffset * 100,
				region.getRegionWidth(), region.getRegionHeight(), 0.5f, 0.5f,
				body.getAngle() * MathUtils.radiansToDegrees);

	}

	public void update() {
		stateTime += Gdx.graphics.getDeltaTime();
		mover.move();

		posX = body.getPosition().x;
		posY = body.getPosition().y;
		originXOffset = width * 0.5f;
		originYOffset = height * 0.5f;

		correctAxes();

	}

	protected void correctAxes() {

	}

	public Body getBody() {
		return body;
	}

	public EntityState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(EntityState currentState) {
		this.currentState = currentState;
	}

	public void setMover(IMoveEntity mover) {
		this.mover = mover;
	}
	
	

}
