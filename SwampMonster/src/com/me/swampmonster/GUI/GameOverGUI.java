package com.me.swampmonster.GUI;

import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.utils.Constants;

public class GameOverGUI extends AbstractGameObject {

	private String gameOverString;
	private String RestartString;
	private String WittyMessage;
	private String[] WittyEnemyMessages;
	private String[] WittySuffocationMessages;
	private String[] WittyPoisonMessages;
	Random randomGenerator = new Random();
	int i = randomGenerator.nextInt(3);

	public GameOverGUI() {
		circle = new Circle();
		circle.radius = 60;
		circle.x = 400;
		circle.y = 140;

		rectanlge = new Rectangle();
		rectanlge.height = Constants.VIEWPORT_HEIGHT;
		rectanlge.width = Constants.VIEWPORT_GUI_WIDTH;
		rectanlge.x = 0;
		rectanlge.y = 0;

		WittyEnemyMessages = new String[3];
		WittySuffocationMessages = new String[3];
		WittyPoisonMessages = new String[3];
		WittyEnemyMessages[0] = Constants.enemyMessage1;
		WittyEnemyMessages[1] = Constants.enemyMessage2;
		WittyEnemyMessages[2] = Constants.enemyMessage3;
		WittySuffocationMessages[0] = Constants.sufficateMessage1;
		WittySuffocationMessages[1] = Constants.sufficateMessage2;
		WittySuffocationMessages[2] = Constants.sufficateMessage3;
		WittyPoisonMessages[0] = Constants.poisionedMessage1;
		WittyPoisonMessages[1] = Constants.poisionedMessage2;
		WittyPoisonMessages[2] = Constants.poisionedMessage3;

		gameOverString = "GAME OVER";
		RestartString = "RESTART";
	}

	public void update(AbstractGameObject player) {
		if (player.getDamageType() == "lackOfOxygen") {
			WittyMessage = WittySuffocationMessages[i];
		}
		if (player.getDamageType() == "enemy") {
			WittyMessage = WittyEnemyMessages[i];
		}
		if (player.getDamageType() == "Poisoned") {
			WittyMessage = WittyPoisonMessages[i];
		}
	}

	public void generateRandomWittyMessage() {

	}

	public String getGameOverString() {
		return gameOverString;
	}

	public void setGameOverString(String gameOverString) {
		this.gameOverString = gameOverString;
	}

	public String getRestartString() {
		return RestartString;
	}

	public void setRestartString(String restartString) {
		RestartString = restartString;
	}

	public String getWittyMessage() {
		return WittyMessage;
	}

	public void setWittyMessage(String wittyMessage) {
		WittyMessage = wittyMessage;
	}

}
