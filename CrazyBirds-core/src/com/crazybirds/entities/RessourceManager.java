package com.crazybirds.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class RessourceManager implements AssetErrorListener {
	public static RessourceManager instance = new RessourceManager();

	public AssetManager manager;

	// Screens
	public PinkyWorldAssets pinkyWorldAssets;

	// Entities
	public KingBirdAssets kingBirdAssets;
	public PinkBirdAssets pinkBirdsAssets;
	public BlueMonsterAssets blueMonsterAssets;
	public HeartMonsterAssets heartMonsterAssets;
	public GlobalAssets globalAssets;
	public PencilMonster pencilMonster;
	public ObstacleAssets obstacleAssets;
	public FontsAssets fontsAssets;
	public MenuAssets menuAssets;

	private RessourceManager() {
		manager = new AssetManager();
	}

	public class MenuAssets {

		public Texture background;
		public Music music;

		public Animation bigPinkyFly;
		public Animation bigKingFly;

		public void loadAssets() {

			// TextureParameter param = new TextureParameter();
			// param.genMipMaps = true; // enabling mipmaps

			manager.load("birds/bigpinkyatlas.txt", TextureAtlas.class);
			manager.load("birds/bigkingatlas.txt", TextureAtlas.class);

			manager.load("backgrounds/backmenu.png", Texture.class);
			manager.load("music/monkeyspin.mp3", Music.class);

			manager.finishLoading();

			background = manager.get("backgrounds/backmenu.png");

			TextureAtlas bigPinky = manager.get("birds/bigpinkyatlas.txt");
			Array<AtlasRegion> flyRegions = bigPinky.getRegions();
			bigPinkyFly = new Animation(1 / 12f, flyRegions, PlayMode.LOOP);

			TextureAtlas bigKing = manager.get("birds/bigkingatlas.txt");
			flyRegions = bigKing.getRegions();
			bigKingFly = new Animation(1 / 12f, flyRegions, PlayMode.LOOP);

			music = manager.get("music/monkeyspin.mp3");

		}

		public void unloadAssets() {
			manager.unload("backgrounds/backmenu.png");
			manager.unload("music/monkeyspin.mp3");
			manager.unload("birds/bigpinkyatlas.txt");
			manager.unload("birds/bigkingatlas.txt");

		}

	}

	public class PinkyWorldAssets {

		public Texture background;
		public Music music;

		public void loadAssets() {
			manager.load("backgrounds/background.png", Texture.class);
			manager.load("music/theShow.mp3", Music.class);
			manager.finishLoading();

			background = manager.get("backgrounds/background.png");
			background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

			music = manager.get("music/theShow.mp3");

		}

		public void unloadAssets() {
			manager.unload("backgrounds/background.png");
			manager.unload("music/theShow.mp3");

		}

	}

	public class GlobalAssets {

		public AtlasRegion birdBullet;
		public AtlasRegion goldContainer;
		public AtlasRegion timeContainer;

		public Sound shootSound;
		public Sound coinSound;
		public Sound explosionSound;

		Array<AtlasRegion> coinRegions;
		Array<AtlasRegion> explosionRegions;
		Animation explosion;
		Animation coin;

		public GlobalAssets(TextureAtlas atlas) {
			birdBullet = atlas.findRegion("bullet");
			timeContainer = atlas.findRegion("time");
			goldContainer = atlas.findRegion("money");
			explosionRegions = atlas.findRegions("exp");
			coinRegions = atlas.findRegions("coin");

			shootSound = manager.get("sounds/shootSound.mp3");
			explosionSound = manager.get("sounds/explosion.mp3");
			coinSound = manager.get("sounds/coin_sound.mp3");

			explosion = new Animation(1 / 6f, explosionRegions, PlayMode.NORMAL);
			coin = new Animation(1 / 3f, coinRegions, PlayMode.LOOP);

		}
	}

	public class FontsAssets {

		public BitmapFont font12;
		public BitmapFont font24;
		public BitmapFont font64;

		public FontsAssets() {

			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
					Gdx.files.internal("fonts/Kraash.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 12;
			font12 = generator.generateFont(parameter);
			parameter.size = 24;
			font24 = generator.generateFont(parameter);
			parameter.size = 64;
			font64 = generator.generateFont(parameter);

			generator.dispose();

		}

		public void unloadAssets() {
			font12.dispose();

		}
	}

	public class KingBirdAssets {

		Array<AtlasRegion> flyRegions;
		Animation fly;

		Array<AtlasRegion> shootRegions;
		Animation shoot;

		Array<AtlasRegion> deadRegions;
		Animation dead;

		public void loadAssets() {

			manager.load("birds/kingbird.txt", TextureAtlas.class);
			manager.finishLoading();

			TextureAtlas kingAtlas = manager.get("birds/kingbird.txt");

			flyRegions = kingAtlas.findRegions("fly");
			fly = new Animation(1 / 12f, flyRegions, PlayMode.LOOP);

			deadRegions = kingAtlas.findRegions("dead");
			dead = new Animation(1 / 12f, deadRegions, PlayMode.LOOP);

			shootRegions = kingAtlas.findRegions("shoot");
			shoot = new Animation(1 / 6f, shootRegions, PlayMode.NORMAL);

		}

		public void unloadAssets() {

			manager.unload("birds/kingbird.txt");

		}

	}

	public class PinkBirdAssets {

		Array<AtlasRegion> flyRegions;
		Animation fly;

		Array<AtlasRegion> shootRegions;
		Animation shoot;

		Array<AtlasRegion> deadRegions;
		Animation dead;

		public void loadAssets() {

			manager.load("birds/pinkbird.txt", TextureAtlas.class);
			manager.finishLoading();

			TextureAtlas pinkAtlas = manager.get("birds/pinkbird.txt");

			flyRegions = pinkAtlas.findRegions("fly");
			fly = new Animation(1 / 12f, flyRegions, PlayMode.LOOP);

			deadRegions = pinkAtlas.findRegions("dead");
			dead = new Animation(1 / 12f, deadRegions, PlayMode.LOOP);

			shootRegions = pinkAtlas.findRegions("shoot");
			shoot = new Animation(1 / 6f, shootRegions, PlayMode.NORMAL);

		}

		public void unloadAssets() {

			manager.unload("birds/pinkbird.txt");

		}

	}

	public class BlueMonsterAssets {

		Array<AtlasRegion> flyRegions;
		Animation fly;

		public void loadAssets() {

			manager.load("enemies/bluemonster.txt", TextureAtlas.class);
			manager.finishLoading();

			TextureAtlas pinkAtlas = manager.get("enemies/bluemonster.txt");

			flyRegions = pinkAtlas.findRegions("fly");
			fly = new Animation(1 / 12f, flyRegions, PlayMode.LOOP);

		}

		public void unloadAssets() {

			manager.unload("enemies/bluemonster.txt");

		}

	}

	public class ObstacleAssets {

		AtlasRegion obstacle1;
		AtlasRegion obstacle2;
		AtlasRegion obstacle3;
		AtlasRegion obstacle4;
		AtlasRegion obstacle5;
		AtlasRegion obstacle6;
		AtlasRegion obstacle7;
		AtlasRegion obstacle8;

		public void loadAssets() {

			manager.load("enemies/obstaclesatlas.txt", TextureAtlas.class);
			manager.finishLoading();

			TextureAtlas obstAtlas = manager.get("enemies/obstaclesatlas.txt");
			obstacle1 = obstAtlas.findRegion("obstacle-1");
			obstacle2 = obstAtlas.findRegion("obstacle-2");
			obstacle3 = obstAtlas.findRegion("obstacle-3");
			obstacle4 = obstAtlas.findRegion("obstacle-4");
			obstacle5 = obstAtlas.findRegion("obstacle-5");
			obstacle6 = obstAtlas.findRegion("obstacle-6");
			obstacle7 = obstAtlas.findRegion("obstacle-7");
			obstacle6 = obstAtlas.findRegion("obstacle-8");

		}

		public void unloadAssets() {

			manager.unload("enemies/bluemonster.txt");

		}

	}

	public class PencilMonster {

		Array<AtlasRegion> flyRegions;
		Animation fly;

		public void loadAssets() {

			manager.load("enemies/pencilmonster.txt", TextureAtlas.class);
			manager.finishLoading();

			TextureAtlas pinkAtlas = manager.get("enemies/pencilmonster.txt");

			flyRegions = pinkAtlas.findRegions("fly");
			fly = new Animation(1 / 12f, flyRegions, PlayMode.LOOP);

		}

		public void unloadAssets() {

			manager.unload("enemies/pencilmonster.txt");

		}

	}

	public class HeartMonsterAssets {

		Array<AtlasRegion> flyRegions;
		Animation fly;

		public void loadAssets() {

			manager.load("enemies/enemyheart.txt", TextureAtlas.class);
			manager.finishLoading();

			TextureAtlas pinkAtlas = manager.get("enemies/enemyheart.txt");

			flyRegions = pinkAtlas.findRegions("fly");
			fly = new Animation(1 / 12f, flyRegions, PlayMode.LOOP);

		}

		public void unloadAssets() {

			manager.unload("enemies/enemyheart.txt");

		}

	}

	public void init() {

		pinkyWorldAssets = new PinkyWorldAssets();
		kingBirdAssets = new KingBirdAssets();
		pinkBirdsAssets = new PinkBirdAssets();
		heartMonsterAssets = new HeartMonsterAssets();
		blueMonsterAssets = new BlueMonsterAssets();
		pencilMonster = new PencilMonster();
		obstacleAssets = new ObstacleAssets();
		fontsAssets = new FontsAssets();
		menuAssets = new MenuAssets();

		manager.load("objects/objectatlas.txt", TextureAtlas.class);
		manager.load("sounds/shootSound.mp3", Sound.class);
		manager.load("sounds/explosion.mp3", Sound.class);
		manager.load("sounds/coin_sound.mp3", Sound.class);

		manager.finishLoading();

		TextureAtlas globalAtlas = manager.get("objects/objectatlas.txt");
		globalAssets = new GlobalAssets(globalAtlas);

		kingBirdAssets.loadAssets();
		pinkBirdsAssets.loadAssets();
		blueMonsterAssets.loadAssets();
		heartMonsterAssets.loadAssets();
		pencilMonster.loadAssets();
		obstacleAssets.loadAssets();

	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.debug(this.getClass().getName(), asset.fileName);

	}

}
