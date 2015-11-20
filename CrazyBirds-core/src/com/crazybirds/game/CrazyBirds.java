package com.crazybirds.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.crazybirds.entities.RessourceManager;
import com.crazybirds.screens.MenuScreen;

public class CrazyBirds extends Game {
	@Override
	public void create() {

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		RessourceManager.instance.init();

		setScreen(new MenuScreen(this));

	}
}
