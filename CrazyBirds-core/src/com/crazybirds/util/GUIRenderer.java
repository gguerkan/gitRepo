package com.crazybirds.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.crazybirds.entities.Hero;
import com.crazybirds.entities.RessourceManager;
import com.crazybirds.entities.SimulationEntity;

public class GUIRenderer {

	private Sprite goldContainer;
	private Sprite timeContainer;

	private BitmapFont fnt;
	private long startTime;

	private Hero hero;

	public GUIRenderer() {

		goldContainer = new Sprite(
				RessourceManager.instance.globalAssets.goldContainer);

		timeContainer = new Sprite(
				RessourceManager.instance.globalAssets.timeContainer);

		fnt = RessourceManager.instance.fontsAssets.font12;
		fnt.setColor(Color.YELLOW);

		startTime = System.currentTimeMillis();
		goldContainer.setScale(0.4f);
		timeContainer.setScale(0.4f);

		timeContainer.setPosition(720, -46);
		goldContainer.setPosition(-100, -55);

	}

	public void render(SpriteBatch batch) {

		timeContainer.draw(batch);
		goldContainer.draw(batch);

		renderGameTime(batch);
		renderPoints(batch);
	}

	private void renderPoints(SpriteBatch batch) {

		if (hero == null) {
			findHero();
			if (hero == null) {
				return;
			}
		}

		fnt.draw(batch, Integer.toString(hero.getGoldPoints()),
				goldContainer.getX() + 185, 50);

	}

	private void findHero() {

		for (SimulationEntity ent : SimulationEntityList.getInstance()
				.getEntityList()) {

			if (ent instanceof Hero) {
				this.hero = (Hero) ent;
			}
		}
	}

	private void renderGameTime(SpriteBatch batch) {

		long gameTime = System.currentTimeMillis() - startTime;

		int seconds = (int) (gameTime / 1000) % 60;
		int minutes = (int) ((gameTime / (1000 * 60)) % 60);

		String sec = Integer.toString(seconds);
		String min = Integer.toString(minutes);

		String finalSec;
		String finalMin;

		if (sec.length() == 1) {
			finalSec = "0" + sec;
		} else {
			finalSec = sec;
		}

		if (min.length() == 1) {
			finalMin = "0" + min;
		} else {
			finalMin = min;
		}

		fnt.draw(batch, finalMin + ":" + finalSec, timeContainer.getX() + 195,
				47);

	}
}
