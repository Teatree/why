package com.me.swampmonster.GUI;

import com.badlogic.gdx.math.Vector2;


public class GUI {
	
	private OxygenBar oxygenBar;
	private HealthBar healthBar;
	private Weaponizer weaponizer;
	private Maskizer maskizer;
	private Croshair croshair;
	private GameOverGUI gameoverGUI;
	
	public GUI(){
		gameoverGUI = new GameOverGUI();
		oxygenBar = new OxygenBar();
		healthBar = new HealthBar();
		weaponizer = new Weaponizer();
		maskizer = new Maskizer();
		croshair = new Croshair(new Vector2());
	}
	
	public void update(int qH){
		healthBar.update(qH);
		weaponizer.update();
		maskizer.update();
		croshair.update();
		gameoverGUI.update();
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

	public Maskizer getMaskizer() {
		return maskizer;
	}

	public void setMaskizer(Maskizer maskizer) {
		this.maskizer = maskizer;
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

