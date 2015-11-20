package com.crazybirds.entities;

import com.badlogic.gdx.math.Vector2;

public interface AbstractEntityFactory {

	public SimulationEntity createEntity(int id, Vector2 position);

	public void createRandomEnemy(float pos);

}
