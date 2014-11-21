package com.me.swampmonster.models.items;

import java.util.Random;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.utils.Assets;

public class Bow extends Weapon{
	
	public static AssetDescriptor<Texture> poisonSprite;
	
	public Bow(){
		super();
		weaponDescSprite = new Sprite(Assets.manager.get(Assets.bowDesc));
		sprite = new Sprite(Assets.manager.get(Assets.wepBOW));
		random = new Random();
//		System.out.println("minDD" + minDD);
//		System.out.println("maxDD" + maxDD);
		// temp
//		minDD = 1;
//		maxDD = 2;
		setStats(L1.player.absoluteScore);
	}

	public void update(TiledMapTileLayer collisionLayer) {
		super.update(collisionLayer);
		
//		System.out.println("minDD" + minDD);
//		System.out.println("maxDD" + maxDD);
	}
	
	
	@Override
	public void setStats(int playerScore) {

		if(playerScore>=0 && playerScore<500){
			randBetVal = random.nextInt(17-12)+12;
			minDD = randBetVal-random.nextInt(3);
			maxDD = minDD + random.nextInt(2)+1;
//			maxDD = randBetVal+random.nextInt(3)+1;
			coolDown = random.nextInt(100-60)+60;
		}
		else if(playerScore>=500 && playerScore<1500){
			randBetVal = random.nextInt(21-15)+15;
			minDD = randBetVal-random.nextInt(3);
//			maxDD = randBetVal+random.nextInt(3)+1;
			maxDD = minDD + random.nextInt(2)+1;
			coolDown = random.nextInt(90-50)+50;
		}
		else if(playerScore>=1500 && playerScore<3000){
			randBetVal = random.nextInt(26-19)+19;
			minDD = randBetVal-random.nextInt(3);
//			maxDD = randBetVal+random.nextInt(3)+1;
			maxDD = minDD + random.nextInt(2)+1;
			coolDown = random.nextInt(120-50)+50;
		}
		else if(playerScore>=3000){
			randBetVal = random.nextInt(32-21)+21;
			minDD = randBetVal-random.nextInt(3);
//			maxDD = randBetVal+random.nextInt(3)+1;
			maxDD = minDD + random.nextInt(2)+1;
			coolDown = random.nextInt(100-50)+50;
		}
	}

}
