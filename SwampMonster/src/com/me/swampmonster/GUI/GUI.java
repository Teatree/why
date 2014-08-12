package com.me.swampmonster.GUI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.models.Player;

public class GUI {
	
	private OxygenBar oxygenBar;
	private HealthBar healthBar;
	private Weaponizer weaponizer;
	private Croshair croshair;
	private GameOverGUI gameoverGUI;
	
	public GUI(Player player){
		gameoverGUI = new GameOverGUI();
		oxygenBar = new OxygenBar(player);
		healthBar = new HealthBar(player);
		weaponizer = new Weaponizer();
		croshair = new Croshair(new Vector2());
	}
	
	public void update(Player player, Vector2 point, Vector3 V3point){
		healthBar.update(player);
		oxygenBar = new OxygenBar(player);
		weaponizer.update(player, point);
		gameoverGUI.update(player);
//		if(croshair.isAiming())
		croshair.update(player, point, V3point);
	
	}

	public OxygenBar getOxygenBar() {
		return oxygenBar;
	}

	public void setOxygenBar(OxygenBar oxygenBar) {
		this.oxygenBar = oxygenBar;
	}

	public HealthBar getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(HealthBar healthBar) {
		this.healthBar = healthBar;
	}

	public Weaponizer getWeaponizer() {
		return weaponizer;
	}

	public void setWeaponizer(Weaponizer weaponizer) {
		this.weaponizer = weaponizer;
	}
	public Croshair getCroshair() {
		return croshair;
	}

	public void setCroshair(Croshair croshair) {
		this.croshair = croshair;
	}

	public GameOverGUI getGameoverGUI() {
		return gameoverGUI;
	}

	public void setGameoverGUI(GameOverGUI gameoverGUI) {
		this.gameoverGUI = gameoverGUI;
	}
}

