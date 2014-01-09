package com.me.swampmonster.GUI;


public class GUI {
	
	private OxygenBar oxygenBar;
	private HealthBar healthBar;
	
	public GUI(){
		oxygenBar = new OxygenBar();
		healthBar = new HealthBar();
	}
	
	public void update(int qH){
		healthBar.update(qH);
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
	
}

