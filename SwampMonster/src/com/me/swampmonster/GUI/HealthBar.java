package com.me.swampmonster.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.me.swampmonster.models.AbstractGameObject;

public class HealthBar extends AbstractGameObject{
	
	private Rectangle[] healthBarRect;
	
	public HealthBar(){
		sprite = new Sprite(new Texture("data/healthbar.png"));
	}
	public void update(int qH){
		healthBarRect = new Rectangle[6];
		fillBar(healthBarRect, 454, qH);
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
	public Rectangle[] getHealthBarRect() {
		return healthBarRect;
	}
	public void setHealthBarRect(Rectangle[] healthBarRect) {
		this.healthBarRect = healthBarRect;
	}
	
}