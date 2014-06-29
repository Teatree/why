package com.me.swampmonster.GUI;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class HealthBar extends AbstractGameObject{
	
	private static final int HealthBarYPosition = 454;
	private Rectangle[] healthBarRect;
	private Sprite spriteTail;
	private Sprite spriteMiddle;
	private Sprite spriteHead;
	private Sprite spriteIcon;
	public List<Sprite> sprites;
	
	public HealthBar(Player player){
		spriteIcon = new Sprite(Assets.manager.get(Assets.HealthBarIcon));
		spriteTail = new Sprite(Assets.manager.get(Assets.HealthBarTail));
		spriteMiddle = new Sprite(Assets.manager.get(Assets.HealthBarMiddle));
		spriteHead = new Sprite(Assets.manager.get(Assets.HealthBarHead));
		sprites = new ArrayList<Sprite>();
		position = new Vector2();
		position.x = 0;
		position.y = 448;
		setCasingPos(Player.playerMaxHealth);
	}
	public void update(Player player){
		healthBarRect = new Rectangle[Player.playerMaxHealth];
		fillBar(healthBarRect, (int)player.health);
	}
	private void fillBar(Rectangle[] r, int q) {
		int cunter = 0;
		while(cunter < q){
			r[cunter] = new Rectangle();
			r[cunter].setHeight(22);
			r[cunter].setWidth(16);
			r[cunter].x = 16+cunter*r[cunter].getWidth();
			r[cunter].y = HealthBarYPosition;
			cunter++;
		}
	}
	
	private void setCasingPos(int maxHP){
		int cunter = 0;
		spriteIcon.setPosition(0, HealthBarYPosition);
		sprites.add(spriteIcon);
		spriteTail.setPosition(16, HealthBarYPosition);
		sprites.add(spriteTail);
		int position = 16;
		while(cunter < maxHP-2){
			Sprite temp = new Sprite(spriteMiddle);
			position += spriteMiddle.getWidth();
			temp.setPosition(position, HealthBarYPosition);
			sprites.add(temp);
			cunter++;
		}
		spriteHead.setPosition(position+16, HealthBarYPosition);
		sprites.add(spriteHead);
	}
	
	public Rectangle[] getHealthBarRect() {
		return healthBarRect;
	}
	public void setHealthBarRect(Rectangle[] healthBarRect) {
		this.healthBarRect = healthBarRect;
	}
	
}
