package com.crazybirds.interfacesImpl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.crazybirds.entities.Hero;
import com.crazybirds.interfaces.IMoveEntity;
import com.crazybirds.util.EntityState;

public class IUserMoveImpl implements IMoveEntity {

	public static final float MAX_VELOCITY_Y = 3;
	public static final float MAX_VELOCITY_X = 1;

	public static final float MAX_FALLING_SPEED = 2;

	public static final int KEY_UP = Keys.UP;
	public static final int KEY_SHOOT = Keys.SPACE;

	private Vector2 vectorPowerUp;

	private Body body;
	private Hero entity;

	public IUserMoveImpl(Hero entity) {

		this.entity = entity;
		this.body = entity.getBody();
		vectorPowerUp = new Vector2(0, 7);

	}

	@Override
	public void move() {

		if (Gdx.input.isKeyPressed(KEY_UP)) {

			if (body.getLinearVelocity().y < MAX_VELOCITY_Y) {

				body.applyLinearImpulse(vectorPowerUp, body.getPosition(), true);
			}
		}

		if (Gdx.input.isKeyJustPressed(KEY_SHOOT)
				&& entity.getCurrentState() == EntityState.FLY) {
			entity.setCurrentState(EntityState.SHOOT);
			entity.shootFire();
		}

		if (body.getLinearVelocity().y < -MAX_FALLING_SPEED) {

			body.setLinearVelocity(new Vector2(body.getLinearVelocity().x,
					-MAX_FALLING_SPEED));
		}

	}

	@Override
	public void setSpeed(float i) {
	}

}
