package com.crazybirds.util;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.crazybirds.entities.Explosion;
import com.crazybirds.entities.Hero;
import com.crazybirds.entities.RessourceManager;
import com.crazybirds.interfacesImpl.IDefaultMove;
import com.crazybirds.screens.AbstractScreen;

public class MyContactListener implements ContactListener {

	AbstractScreen screen;

	public MyContactListener(AbstractScreen screen) {

		this.screen = screen;
	}

	@Override
	public void beginContact(Contact contact) {

		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();

		if (a.getUserData() != null && b.getUserData() != null) {

			if (checkHeroEnemyCollision(a, b))
				return;
			if (checkHeroEnemyCollision(b, a))
				return;
			if (checkBulletEnemyCollision(a, b))
				return;
			if (checkBulletEnemyCollision(b, a))
				return;
			if (checkBulletObstacleCollision(a, b))
				return;
			if (checkBulletObstacleCollision(b, a))
				return;
			if (checkHeroObstacleCollision(b, a))
				return;
			if (checkHeroObstacleCollision(a, b))
				return;
			if (checkHeroCollectableCollision(a, b))
				return;
			if (checkHeroCollectableCollision(b, a))
				return;

		}

	}

	private boolean checkHeroCollectableCollision(Fixture a, Fixture b) {
		CollisionInformation helperA = (CollisionInformation) a.getUserData();
		CollisionInformation helperB = (CollisionInformation) b.getUserData();

		if (helperA.id == Const.GoldCoinID && helperB.id == Const.HeroID) {
			KillThisBodyList.getInstance().addBody(helperA.entity.getBody());
			RessourceManager.instance.globalAssets.coinSound.play();
			Hero hero = (Hero) helperB.entity;
			hero.addPoint(1);

			return true;
		}
		return false;
	}

	private boolean checkHeroObstacleCollision(Fixture b, Fixture a) {

		CollisionInformation helperA = (CollisionInformation) a.getUserData();
		CollisionInformation helperB = (CollisionInformation) b.getUserData();

		if (helperA.id == Const.HeroID && helperB.id == Const.ObstacleID) {
			helperA.entity.setMover(new IDefaultMove(helperA.entity));
			helperA.entity.setCurrentState(EntityState.HURT);
			return true;
		}
		return false;
	}

	private boolean checkBulletObstacleCollision(Fixture b, Fixture a) {

		CollisionInformation helperA = (CollisionInformation) a.getUserData();
		CollisionInformation helperB = (CollisionInformation) b.getUserData();

		if (helperA.id == Const.BulletID && helperB.id == Const.ObstacleID) {
			KillThisBodyList.getInstance().addBody(a.getBody());

			SimulationEntityList.getInstance().addEntity(
					new Explosion(null, a.getBody().getPosition().x, a
							.getBody().getPosition().y));

			RessourceManager.instance.globalAssets.explosionSound.play();

			return true;
		}
		return false;
	}

	private boolean checkBulletEnemyCollision(Fixture a, Fixture b) {

		CollisionInformation helperA = (CollisionInformation) a.getUserData();
		CollisionInformation helperB = (CollisionInformation) b.getUserData();

		if (helperA.id == Const.BulletID && helperB.id == Const.EnemyID) {
			KillThisBodyList.getInstance().addBody(a.getBody());
			KillThisBodyList.getInstance().addBody(b.getBody());

			SimulationEntityList.getInstance().addEntity(
					new Explosion(null, a.getBody().getPosition().x, a
							.getBody().getPosition().y));

			RessourceManager.instance.globalAssets.explosionSound.play();

			return true;
		}

		return false;
	}

	private boolean checkHeroEnemyCollision(Fixture a, Fixture b) {

		CollisionInformation helperA = (CollisionInformation) a.getUserData();
		CollisionInformation helperB = (CollisionInformation) b.getUserData();

		if (helperA.id == Const.EnemyID && helperB.id == Const.HeroID) {

			helperB.entity.setMover(new IDefaultMove(helperB.entity));
			helperB.entity.setCurrentState(EntityState.HURT);
			return true;
		}

		return false;
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
