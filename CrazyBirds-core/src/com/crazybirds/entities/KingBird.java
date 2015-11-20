package com.crazybirds.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.crazybirds.interfacesImpl.IUserMoveImpl;
import com.crazybirds.util.EntityState;

public class KingBird extends Hero {

	public KingBird(World world, float x, float y) {
		super(world, x, y);
		mover = new IUserMoveImpl(this);
		ratio = 315 / 268;
		posX = x;
		posY = y;
		flyAnimation = RessourceManager.instance.kingBirdAssets.fly;
		shootAnimation = RessourceManager.instance.kingBirdAssets.shoot;
		deadAnimation = RessourceManager.instance.kingBirdAssets.dead;


	}

	@Override
	protected void createBody() {

		super.createBody();

	}

	@Override
	protected void correctAxes() {

		if (currentState == EntityState.FLY) {
			yCorrection = 70f;
			xCorrection = 90f;
		} else {

			yCorrection = 56;
			xCorrection = 88f;
		}

	}


}
