package com.crazybirds.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class KillThisBodyList {

	private static KillThisBodyList instance = new KillThisBodyList();
	private Array<Body> list;

	private KillThisBodyList() {
		list = new Array<Body>();
	}

	public static KillThisBodyList getInstance() {

		return instance;
	}

	public void addBody(Body b) {

		if (!list.contains(b, false)) {
			list.add(b);
		}
	}

	public Array<Body> getList() {

		return list;
	}

	public void clear() {

		list.clear();

	}

	public void destroyBodys(World world) {

		for (Body b : list) {
			if (!world.isLocked()) {
				b.setActive(false);
				world.destroyBody(b);
			}
		}

		clear();
	}
}
