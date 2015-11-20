package com.crazybirds.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;

public class HeartMonster extends Enemy {
	private Animation flyAnimation;

	public HeartMonster(World world, float x, float y) {
		super(world, x, y);
		flyAnimation = RessourceManager.instance.heartMonsterAssets.fly;
	}


	@Override
	public void render(SpriteBatch batch) {

		region = (AtlasRegion) flyAnimation.getKeyFrame(stateTime);

		batch.draw(region, (posX - originXOffset) * 100f - 70f,
				(posY - originYOffset) * 100 - 70, originXOffset * 100f,
				originYOffset * 100f, region.getRegionWidth(),
				region.getRegionHeight(), 0.5f, 0.5f, body.getAngle()
						* MathUtils.radiansToDegrees);

	}

}
