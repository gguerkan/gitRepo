package com.crazybirds.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.crazybirds.entities.AbstractEntityFactory;
import com.crazybirds.entities.GoldCoin;
import com.crazybirds.entities.Hero;
import com.crazybirds.entities.Obstacle;
import com.crazybirds.entities.SimulationEntity;
import com.crazybirds.util.CollisionInformation;
import com.crazybirds.util.Const;
import com.crazybirds.util.MyContactListener;
import com.crazybirds.util.SimulationEntityList;
import com.crazybirds.util.WorldRenderer;

public abstract class AbstractScreen extends ScreenAdapter {

	protected Game game;

	protected Sprite background;
	protected WorldRenderer renderer;
	protected World world;

	protected AbstractEntityFactory factory;

	protected Hero heroOne;
	protected Hero heroTwo;

	private Array<Obstacle> obstacleList;
	private Array<Obstacle> obstaclesDead;

	public AbstractScreen(Game game) {

		this.game = game;
		world = new World(new Vector2(0, -9.8f), true);
		world.setContactListener(new MyContactListener(this));
		renderer = new WorldRenderer(this);

		obstacleList = new Array<Obstacle>();
		obstaclesDead = new Array<Obstacle>();

		createBox2dZone();
	}

	protected void createEntities() {
		heroOne = (Hero) factory.createEntity(Const.PinkBirdID, new Vector2(
				1.5f, 5));
		SimulationEntityList.getInstance().addEntity(heroOne);

		Timer.instance().scheduleTask(new GenerateEnemyTask(), 3, 0.8f);
		Timer.instance().scheduleTask(new GenerateObstacleTask(), 1, 6f);
		Timer.instance().scheduleTask(new GenerateCoinTask(), 1, 0.7f);

	}

	protected void createBox2dZone() {

		BodyDef def = new BodyDef();
		def.type = BodyType.StaticBody;
		Body border = world.createBody(def);

		ChainShape shape = new ChainShape();
		shape.createChain(new Vector2[] { new Vector2(0f, 0.6f),
				new Vector2(13f, 0.6f) });

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.friction = 1;
		fixDef.restitution = 0;
		fixDef.filter.categoryBits = Const.FilterGround;
		fixDef.filter.maskBits = Const.FilterHero;

		border.createFixture(fixDef);

		shape.dispose();

		shape = new ChainShape();
		shape.createChain(new Vector2[] { new Vector2(0, 5.1f),
				new Vector2(13, 5.1f) });
		fixDef.shape = shape;
		border.createFixture(fixDef).setUserData(
				new CollisionInformation(heroOne, 0));

	}

	public class GenerateEnemyTask extends Timer.Task {

		@Override
		public void run() {

			checkEnvironmentBeforeCreate();

		}

		private void checkEnvironmentBeforeCreate() {

			refreshObstaclelist();

			float yPosition = findPositionToCreateEnemy();
			if (yPosition != -1) {
				factory.createRandomEnemy(yPosition);

			} else {
				float yPositionSecondTry = findPositionToCreateEnemy();
				if (yPositionSecondTry != -1) {
					factory.createRandomEnemy(yPosition);
				}
			}

		}

		private float findPositionToCreateEnemy() {
			float yPosition = MathUtils.random(1.2f, 3.9f);
			boolean canCreateAtThisPos = false;
			for (Obstacle obstacle : obstacleList) {

				float bodyPosY = obstacle.getBody().getPosition().y;
				float diff = 0;
				if (yPosition > bodyPosY) {
					diff = yPosition - bodyPosY;

				} else {
					diff = bodyPosY - yPosition;
				}

				if (diff < 1.5) {
					canCreateAtThisPos = false;
					break;
				} else {
					canCreateAtThisPos = true;
				}

			}

			if (!canCreateAtThisPos) {
				return -1;

			} else {
				return yPosition;
			}

		}

		private void refreshObstaclelist() {

			for (Obstacle obst : obstacleList) {

				if (obst.getBody() == null
						|| obst.getBody().getPosition().x < 3) {
					obstaclesDead.add(obst);
				}
			}

			obstacleList.removeAll(obstaclesDead, false);

		}

	}

	public class GenerateObstacleTask extends Timer.Task {

		@Override
		public void run() {
			obstacleList.add((Obstacle) factory.createEntity(Const.ObstacleID,
					new Vector2()));

		}

	}

	public class GenerateCoinTask extends Timer.Task {

		@Override
		public void run() {
			float yPosition = MathUtils.random(1.2f, 4f);
			float xPosition = 14;

			boolean canCreate = false;

			for (SimulationEntity entity : SimulationEntityList.getInstance()
					.getEntityList()) {

				if (entity instanceof Obstacle && entity.getBody() != null
						&& entity.getBody().getPosition().x > 9) {

					float posBodyX = entity.getBody().getPosition().x;
					float diff = 0;

					if (posBodyX > xPosition) {
						diff = posBodyX - xPosition;
					} else {

						diff = xPosition - posBodyX;
					}

					if (diff < 1) {
						canCreate = false;
						return;
					} else {
						canCreate = true;
					}
				}
			}

			if (canCreate) {
				SimulationEntityList.getInstance().addEntity(
						((GoldCoin) factory.createEntity(Const.GoldCoinID,
								new Vector2(xPosition, yPosition))));

			}
		}
	}

	@Override
	public void show() {

	}

	public Sprite getBackground() {
		return background;
	}

	public World getWorld() {
		return world;
	}

	public Hero getHeroOne() {
		return heroOne;
	}

}
