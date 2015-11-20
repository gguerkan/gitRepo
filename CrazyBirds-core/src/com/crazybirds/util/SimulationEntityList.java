package com.crazybirds.util;

import com.badlogic.gdx.utils.Array;
import com.crazybirds.entities.Explosion;
import com.crazybirds.entities.SimulationEntity;

public class SimulationEntityList {

	private static SimulationEntityList instance = new SimulationEntityList();

	private Array<SimulationEntity> simulationEnties;

	private Array<SimulationEntity> removeHelper;

	private SimulationEntityList() {

		removeHelper = new Array<SimulationEntity>();
		simulationEnties = new Array<SimulationEntity>();
	}

	public static SimulationEntityList getInstance() {
		return instance;
	}

	public void addEntity(SimulationEntity entity) {

		simulationEnties.add(entity);

	}

	public void removeEntity(SimulationEntity entity) {

		simulationEnties.removeValue(entity, false);
	}

	public Array<SimulationEntity> getEntityList() {

		return simulationEnties;
	}

	public void updateEntityList() {

		for (SimulationEntity entity : simulationEnties) {
			if (entity.getBody() != null) {
				if (entity.getBody().getFixtureList().size == 0) {
					removeHelper.add(entity);
				}
			} else {
				if (!(entity instanceof Explosion)) {
					removeHelper.add(entity);
				}
			}
		}

		for (SimulationEntity entity : removeHelper) {

			simulationEnties.removeValue((SimulationEntity) entity, false);
			entity = null;
		}

		removeHelper.clear();

	}
}
