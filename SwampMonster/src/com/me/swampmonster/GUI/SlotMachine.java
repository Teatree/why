package com.me.swampmonster.GUI;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.pickable.Perks;

public class SlotMachine extends AbstractGameObject{
	
	boolean slotMachine;
	private int timer;
	
	private Animation animation;
	private Random random;
	private Map <Integer, Perks> perkParams;
	private Texture texture;
	private TextureRegion[] frames;
	private TextureRegion currentFrame;
	
	public SlotMachine(){
		slotMachine = false;
		timer = 0;
		texture = new Texture(Gdx.files.internal("data/perkSheet.png"));
		sprite = new Sprite(texture);
		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 8,
						texture.getHeight() / 8);
		frames = new TextureRegion[8 * 8];

		int index = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		animation = new Animation(1, frames);
		
		currentFrame = animation.getKeyFrame(0);
		
		sprite = new Sprite(currentFrame);
		sprite.setX(275);
		sprite.setY(175);
		sprite.setScale(2, 2);
		
		perkParams = new HashMap<Integer, Perks>();
		perkParams.put(0, null);
		perkParams.put(1, Perks.RELOAD_SPEED);
		perkParams.put(2, Perks.OXYGEN_COPACITY);
		perkParams.put(3, Perks.MOVEMENT_SPEED);
		perkParams.put(4, Perks.HEALTH_COPACITY);
		perkParams.put(4, Perks.MELEE_RADIUS);
		perkParams.put(6, Perks.MELEE_RELOAD_SPEED);
		perkParams.put(7, Perks.ARROW_DAMAGE);
		perkParams.put(8, Perks.HUNRETPOINTS);
		perkParams.put(9, Perks.FIVEHUNRETPOINTS);
		perkParams.put(10, Perks.NOTHING);
		perkParams.put(11, Perks.ARROW_SPEED);
		random = new Random();
		
	}
	
	public void update(){
		if(slotMachine){
			if(timer<60){
				int perkRand = random.nextInt();
				
				Perks perks = null;
				perks = perkParams.get((perkRand));
				
				currentFrame = animation.getKeyFrame(perkRand);
				sprite = new Sprite(currentFrame);
				
				timer++;
			}
		}else{
			timer=0;
		}
	}

	public boolean isSlotMachine() {
		return slotMachine;
	}

	public void setSlotMachine(boolean slotMachine) {
		this.slotMachine = slotMachine;
	}
	
}
