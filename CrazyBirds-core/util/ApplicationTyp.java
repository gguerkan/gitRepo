package com.birdsattack.util;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

/**
 * Helper class to show on which Application the game is running on.
 * 
 * @author goekhanguerkan
 *
 */
public final class ApplicationTyp {

	public static boolean APPLICATION_ANDROID = false;
	public static boolean APPLICATION_DESKTOP = false;
	public static boolean APPLICATION_IOS = false;
	public static boolean APPLICATION_HTML = false;

	static {

		

		if (Gdx.app.getType() == ApplicationType.Android) {
			APPLICATION_ANDROID = true;
		} else if (Gdx.app.getType() == ApplicationType.Desktop) {
			APPLICATION_DESKTOP = true;
		} else if (Gdx.app.getType() == ApplicationType.iOS) {
			APPLICATION_IOS = true;
		} else if (Gdx.app.getType() == ApplicationType.WebGL) {
			APPLICATION_HTML = true;

		}

	}

}
