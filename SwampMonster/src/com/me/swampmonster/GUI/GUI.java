package com.me.swampmonster.GUI;


public class GUI {
	
	private OxygenBar oxygenBar;
	private HealthBar healthBar;
	private Weaponizer weaponizer;
	private Maskizer maskizer;
	
	public GUI(){
		oxygenBar = new OxygenBar();
		healthBar = new HealthBar();
		weaponizer = new Weaponizer();
		maskizer = new Maskizer();
	}
	
	public void update(int qH){
		healthBar.update(qH);
		weaponizer.update();
		maskizer.update();
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
	
	
}

