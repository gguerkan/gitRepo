package com.crazybirds.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.crazybirds.util.SimulationEntityList;

public class Explosion extends SimulationEntity {

	Animation exploAnimation;

	public Explosion(World world, float x, float y) {
		super(world, x, y);
		exploAnimation = RessourceManager.instance.globalAssets.explosion;
	}

	@Override
	protected void createBody() {

	}

	@Override
	public void render(SpriteBatch batch) {

		region = (AtlasRegion) exploAnimation.getKeyFrame(stateTime);

		batch.draw(region, (posX - originXOffset) * 100 - xCorrection,
				(posY - originYOffset) * 100 - yCorrection,
				originXOffset * 100, originYOffset * 100,
				region.getRegionWidth(), region.getRegionHeight(), 0.5f, 0.5f,
				0);
	}

	@Override
	public void update() {
		stateTime += Gdx.graphics.getDeltaTime();
		if (exploAnimation.isAnimationFinished(stateTime)) {
			SimulationEntityList.getInstance().removeEntity(this);
		}

	}

}
