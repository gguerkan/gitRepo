package com.crazybirds.util;

import com.crazybirds.entities.SimulationEntity;

public class CollisionInformation {

	public SimulationEntity entity;
	public int id;

	public CollisionInformation(SimulationEntity entity, int id) {

		this.id = id;
		this.entity = entity;
	}
}
