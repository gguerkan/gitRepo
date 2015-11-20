package com.crazybirds.interfacesImpl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.crazybirds.entities.SimulationEntity;
import com.crazybirds.interfaces.IMoveEntity;

public class IDefaultMove implements IMoveEntity {

	public IDefaultMove(SimulationEntity entity) {

		Body b = entity.getBody();
		b.setLinearVelocity(new Vector2());
		b.setGravityScale(0);

		Filter emptyFilter = new Filter();

		for (Fixture f : b.getFixtureList()) {

			f.setFilterData(emptyFilter);
		}
	}

	@Override
	public void move() {

	}

	@Override
	public void setSpeed(float i) {
	}

}
