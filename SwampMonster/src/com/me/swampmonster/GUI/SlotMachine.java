package com.me.swampmonster.GUI;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.Enemy;
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
	
	private Sprite spriteSlot1;
	private Sprite spriteSlot2;
	private Sprite spriteSlot3;
	private Sprite[] spriteSlots;
	
	public SlotMachine(){
		slotMachine = false;
		timer = 0;
		sprite = new Sprite(new Texture("data/slotMachineCase.png"));
		texture = new Texture(Gdx.files.internal("data/perkSheet.png"));
		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 4,
						texture.getHeight() / 4);
		frames = new TextureRegion[4 * 4];
		
		int index = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		animation = new Animation(1, frames);
		
		currentFrame = animation.getKeyFrame(0);
		
		spriteSlot1 = new Sprite(currentFrame);
		spriteSlot2 = new Sprite(currentFrame);
		spriteSlot3 = new Sprite(currentFrame);
		sprite.setX(0);
		sprite.setY(0);
		sprite.setScale(2, 2);
		spriteSlot1.setX(100);
//		spriteSlot1.setY(175);
		spriteSlot2.setX(180);
//		spriteSlot2.setY(175);
		spriteSlot3.setX(260);
//		spriteSlot3.setY(175);
		spriteSlots = new Sprite[]{spriteSlot1, spriteSlot2, spriteSlot3};
		
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
	
	public Perks getRandPerk(int valMin, int valMax){
		random = new Random();
		int key = random.nextInt(valMax - valMin + 1) + valMin;
		Perks perk = null;
		try {
			perk = perkParams.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return perk;
	}
	
	public void update(){
		if(slotMachine){
			
			if(timer<60){
				for(Sprite n:spriteSlots){
//					System.out.println("sprUte: " + sprUte);
//					int perkRand = random.nextInt((11-1)+1);
					
					Perks perks = getRandPerk(1, 11);
					
					currentFrame = animation.getKeyFrame(3);
					n = new Sprite(currentFrame);
					n.setY(70);
					
				}
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

	public Sprite[] getSpriteSlots() {
		return spriteSlots;
	}

	public void setSpriteSlots(Sprite[] spriteSlots) {
		this.spriteSlots = spriteSlots;
	}
	
	
}
