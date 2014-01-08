package com.me.swampmonster.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class GUI {
	
	private Sprite healthBarSprite;
	private Sprite oxygenBarSprite;
	private Rectangle[] healthBar;
	private Rectangle[] oxygenBar;
	
	public GUI(){
		healthBarSprite = new Sprite(new Texture("data/healthbar.png"));
		oxygenBarSprite = new Sprite(new Texture("data/oxygenBar.png"));
	}
	
	public void update(int qH, int qO){
		healthBar = new Rectangle[6];
		oxygenBar = new Rectangle[6];
		fillBar(healthBar, 454, qH);
		fillBar(oxygenBar, 422, qO);
	}

	private void fillBar(Rectangle[] r, int y, int q) {
		int cunter = 0;
		while(cunter < q){
			r[cunter] = new Rectangle();
			r[cunter].setHeight(22);
			r[cunter].setWidth(16);
			r[cunter].x = 30+cunter*r[cunter].getWidth();
			r[cunter].y = y;
			cunter++;
		}
	}
	
	public void decreaseBar(Rectangle[] r, int index){
		r[index] = null;
	}
	
	public Sprite getHealthBarSprite() {
		return healthBarSprite;
	}

	public void setHealthBarSprite(Sprite healthBarSprite) {
		this.healthBarSprite = healthBarSprite;
	}

	public Sprite getOxygenBarSprite() {
		return oxygenBarSprite;
	}

	public void setOxygenBarSprite(Sprite oxygenBarSprite) {
		this.oxygenBarSprite = oxygenBarSprite;
	}

	public Rectangle[] getHealthBar() {
		return healthBar;
	}

	public void setHealthBar(Rectangle[] healthBar) {
		this.healthBar = healthBar;
	}

	public Rectangle[] getOxygenBar() {
		return oxygenBar;
	}

	public void setOxygenBar(Rectangle[] oxygenBar) {
		this.oxygenBar = oxygenBar;
	}
}

