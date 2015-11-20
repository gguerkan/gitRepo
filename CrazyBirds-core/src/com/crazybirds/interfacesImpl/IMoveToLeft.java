package com.crazybirds.interfacesImpl;

import com.badlogic.gdx.physics.box2d.Body;
import com.crazybirds.entities.SimulationEntity;
import com.crazybirds.interfaces.IMoveEntity;

public class IMoveToLeft implements IMoveEntity {

	private float speed = 2;
	private Body body;

	public IMoveToLeft(SimulationEntity entity) {
		body = entity.getBody();
		body.setLinearVelocity(-speed, 0);

	}

	@Override
	public void move() {
		// TODO Variate Enemy Speed ?
	}

	public void setSpeed(float speed) {
		body.setLinearVelocity(-speed, 0);
	}
	
	

}
