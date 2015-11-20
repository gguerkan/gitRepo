package com.crazybirds.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crazybirds.entities.SimulationEntity;
import com.crazybirds.screens.AbstractScreen;

public class WorldRenderer {

	private float accumulator = 0f;

	private OrthographicCamera camWorld;
	private OrthographicCamera camBox2D;

	private Viewport viewPortWorld;
	private Viewport viewPortBox2d;

	private SpriteBatch batch;
	private Box2DDebugRenderer debugrenderer;
	private GUIRenderer guiRenderer;

	private AbstractScreen screen;
	protected float scrollTimer = 0;

	public WorldRenderer(AbstractScreen screen) {
		this.screen = screen;
		batch = new SpriteBatch();
		debugrenderer = new Box2DDebugRenderer();
		guiRenderer = new GUIRenderer();
		// Cams
		camWorld = new OrthographicCamera();
		viewPortWorld = new FitViewport(Const.worldWidth, Const.worldHeight, camWorld);
		camWorld.translate(Const.worldWidth * 0.5f, Const.worldHeight * 0.5f);

		camBox2D = new OrthographicCamera();
		viewPortBox2d = new FitViewport(Const.worldWidth / 100, Const.worldHeight / 100, camBox2D);
		camBox2D.translate((Const.worldWidth * 0.5f) / 100, (Const.worldHeight * 0.5f) / 100);

	}

	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		drawSimulationWorld();
		SimulationEntityList.getInstance().updateEntityList();
		drawWorldEntities(delta);
		debugrenderer.render(screen.getWorld(), viewPortBox2d.getCamera().combined);

		updateCamera();

	}

	private void updateCamera() {

		camBox2D.update();
		camWorld.update();

	}

	private void drawSimulationWorld() {

		screen.getWorld().step(1 / 60f, 8, 3);
		KillThisBodyList.getInstance().destroyBodys(screen.getWorld());

	}

	private void drawWorldEntities(float delta) {

		batch.setProjectionMatrix(camWorld.combined);

		batch.begin();
		screen.getBackground().draw(batch);

		renderMovingBackground(delta);
		renderGame(delta);

		batch.end();

	}

	private void renderMovingBackground(float delta) {
		
		scrollTimer += delta;
		screen.getBackground().setU(scrollTimer / 8);
		screen.getBackground().setU2((scrollTimer / 8) + 1);
		
	}

	private void renderGame(float delta) {

		SimulationEntityList.getInstance().updateEntityList();
		for (SimulationEntity entity : SimulationEntityList.getInstance().getEntityList()) {

			entity.update();
		}

		for (SimulationEntity entity : SimulationEntityList.getInstance().getEntityList()) {

			entity.render(batch);
		}

		guiRenderer.render(batch);

	}

	public void resize(int width, int height) {
		viewPortWorld.update(width, height);
		viewPortBox2d.update(width, height);

	}

}
