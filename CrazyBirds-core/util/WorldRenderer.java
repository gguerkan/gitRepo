package com.birdsattack.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.birdsattack.entities.AbstractEntity;
import com.birdsattack.screens.AbstractScreen;

public class WorldRenderer {

	private float accumulator = 0f;
	private float delta;

	private OrthographicCamera camWorld;
	private OrthographicCamera camBox2D;

	private Viewport viewPortWorld;
	private Viewport viewPortBox2d;

	private SpriteBatch batch;
	private Box2DDebugRenderer debugrenderer;

	private AbstractScreen screen;

	public WorldRenderer(AbstractScreen screen) {
		this.screen = screen;
		batch = new SpriteBatch();
		debugrenderer = new Box2DDebugRenderer();

		// Cams
		camWorld = new OrthographicCamera();
		viewPortWorld = new FitViewport(Const.worldWidth, Const.worldHeight,
				camWorld);
		camWorld.translate(Const.worldWidth * 0.5f, Const.worldHeight * 0.5f);

		camBox2D = new OrthographicCamera();
		viewPortBox2d = new FitViewport(Const.worldWidth / 100,
				Const.worldHeight / 100, camBox2D);
		camBox2D.translate((Const.worldWidth * 0.5f) / 100,
				(Const.worldHeight * 0.5f) / 100);

	}

	public void render(Array<AbstractEntity> entityList, float delta) {
		drawSimulationWorld();

		this.delta = delta;

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		drawWorldEntities(entityList);
		debugrenderer.render(screen.getWorld(),
				viewPortBox2d.getCamera().combined);

	}

	private void drawSimulationWorld() {

		accumulator += delta;

		screen.getWorld().step(1 / 60f, 8, 3);

	

	}

	private void drawWorldEntities(Array<AbstractEntity> entityList) {

		batch.setProjectionMatrix(camWorld.combined);

		batch.begin();
		screen.getBackground().draw(batch);

		for (AbstractEntity entity : entityList) {

			entity.update();
		}

		for (AbstractEntity entity : entityList) {

			entity.render(batch);
		}

		batch.end();

	}

	public void resize(int width, int height) {
		viewPortWorld.update(width, height);
		viewPortBox2d.update(width, height);
	}

}
