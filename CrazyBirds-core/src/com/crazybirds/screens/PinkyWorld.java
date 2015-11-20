package com.crazybirds.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.crazybirds.entities.PinkyWorldFactory;
import com.crazybirds.entities.RessourceManager;
import com.crazybirds.util.Const;

public class PinkyWorld extends AbstractScreen {

	protected Music music;

	public PinkyWorld(Game game) {
		super(game);
		factory = new PinkyWorldFactory(world);
	}

	private void init() {

		background = new Sprite(
				RessourceManager.instance.pinkyWorldAssets.background);
		background.setSize(Const.worldWidth, Const.worldHeight);

		createEntities();
		music = RessourceManager.instance.pinkyWorldAssets.music;
		music.setLooping(true);
		music.play();

	}

	@Override
	public void render(float delta) {

		renderer.render(delta);
	}

	@Override
	public void show() {
		RessourceManager.instance.pinkyWorldAssets.loadAssets();

		init();

	}

	@Override
	public void hide() {
		RessourceManager.instance.pinkyWorldAssets.unloadAssets();
	}

	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
	}

}
