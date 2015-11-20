package com.crazybirds.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crazybirds.entities.RessourceManager;
import com.crazybirds.tween.ActorAccessor;
import com.crazybirds.util.Const;

public class MenuScreen extends ScreenAdapter {

	// music
	private Music music;

	// graphics and cam
	private OrthographicCamera camWorld;
	private Viewport viewPortWorld;
	private SpriteBatch batch;

	// scene2d widgets
	private Label title;
	private Skin menuSkin;

	// Screens and Frameworks
	private Game game;
	private Stage stage;
	private TweenManager manager;

	// Birds decoration
	private Animation pinkyBirdFly;
	private Animation kingbirdFly;
	private float stateTime;

	private float positionPinky = -260;
	private float positioKing = 1000;

	public MenuScreen(Game game) {

		this.game = game;

		stage = new Stage();
		batch = new SpriteBatch();
		camWorld = new OrthographicCamera();
		viewPortWorld = new FitViewport(Const.worldWidth, Const.worldHeight,
				camWorld);
		camWorld.translate(Const.worldWidth * 0.5f, Const.worldHeight * 0.5f);
		stage.setViewport(viewPortWorld);

		Tween.registerAccessor(Actor.class, new ActorAccessor());
		manager = new TweenManager();
		menuSkin = new Skin(Gdx.files.internal("skins/menuskin.json"),
				new TextureAtlas(Gdx.files.internal("skins/menuatlas.txt")));

		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void show() {

		RessourceManager.instance.menuAssets.loadAssets();
		pinkyBirdFly = RessourceManager.instance.menuAssets.bigPinkyFly;
		kingbirdFly = RessourceManager.instance.menuAssets.bigKingFly;
		init();
	}

	private void init() {

		music = RessourceManager.instance.menuAssets.music;
		music.setLooping(true);
		music.play();

		buildbackgroundLayer();
		buildControllLayer();

	}

	private void buildControllLayer() {

		Table table = new Table();

		Button playBtn = new Button(menuSkin, "play");
		playBtn.setPosition(400, 0);
		playBtn.setSize(100, 100);

		Button settingsBtn = new Button(menuSkin, "settings");
		settingsBtn.setPosition(520, 0);
		settingsBtn.setSize(100, 100);

		Button multiplayerBtn = new Button(menuSkin, "multiplayer");
		multiplayerBtn.setPosition(280, 0);
		multiplayerBtn.setSize(100, 100);

		Button highscoreBtn = new Button(menuSkin, "highscore");
		highscoreBtn.setPosition(640, 0);
		highscoreBtn.setSize(100, 100);

		playBtn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new PinkyWorld(game));

			}
		});

		table.addActor(playBtn);
		table.addActor(settingsBtn);
		table.addActor(multiplayerBtn);
		table.addActor(highscoreBtn);

		stage.addActor(table);

	}

	private void startTweenAnimation() {

		Timeline.createSequence()
				.beginSequence()
				.push(Tween.to(title, ActorAccessor.TINT, 0.9f)
						.target(0, 0, 255).ease(TweenEquations.easeInOutSine))
				.repeatYoyo(1000, 0).start(manager);

	}

	private void buildbackgroundLayer() {

		Table table = new Table();

		// background image
		Image imgBackground = new Image(
				RessourceManager.instance.menuAssets.background);
		imgBackground.setSize(Const.worldWidth, Const.worldHeight);

		// title
		BitmapFont font = RessourceManager.instance.fontsAssets.font64;
		LabelStyle stlye = new LabelStyle(font, Color.PINK);
		title = new Label("Birds enraged", stlye);
		title.setPosition(100, 400);

		// background of control
		Image controlBackground = new Image(menuSkin, "backmenu");
		final float scaleSize = 1.41f;
		float widthControlBack = 500;
		float heightControlBack = widthControlBack / scaleSize;
		controlBackground.setSize(widthControlBack, heightControlBack);
		controlBackground.setPosition(Const.worldWidth * 0.5f
				- controlBackground.getWidth() * 0.5f, -268);

		table.addActor(imgBackground);
		table.addActor(title);
		table.addActor(controlBackground);

		startTweenAnimation();

		stage.addActor(table);

	}

	@Override
	public void render(float delta) {

		batch.setProjectionMatrix(camWorld.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

		renderBigBirds(delta);
		manager.update(delta);

	}

	private void renderBigBirds(float delta) {
		stateTime += delta;

		AtlasRegion pinkyRegion = (AtlasRegion) pinkyBirdFly
				.getKeyFrame(stateTime);

		AtlasRegion kingRegion = (AtlasRegion) kingbirdFly
				.getKeyFrame(stateTime);

		if (positioKing > 720) {
			if (!kingRegion.isFlipX()) {
				kingRegion.flip(true, false);
			}
		}

		batch.begin();

		batch.draw(pinkyRegion, positionPinky, 100, pinkyRegion.offsetX,
				pinkyRegion.offsetY, pinkyRegion.getRegionWidth(),
				pinkyRegion.getRegionHeight(), 0.5f, 0.5f, 0);

		if (positionPinky < 20) {
			positionPinky += 0.5f;
		}

		batch.draw(kingRegion, positioKing, 80, pinkyRegion.offsetX,
				pinkyRegion.offsetY, pinkyRegion.getRegionWidth(),
				pinkyRegion.getRegionHeight(), 0.6f, 0.6f, 0);

		if (positioKing > 720) {
			positioKing -= 0.5f;
		}

		batch.end();

	}

	@Override
	public void hide() {
		music.stop();
		RessourceManager.instance.menuAssets.unloadAssets();
		stage.dispose();
		menuSkin.dispose();

	}

	@Override
	public void resize(int width, int height) {

		viewPortWorld.update(width, height);
	}

}
