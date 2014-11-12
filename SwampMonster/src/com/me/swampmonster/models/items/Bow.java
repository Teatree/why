package com.me.swampmonster.models.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.items.wepMods.DamagePlayerMod;
import com.me.swampmonster.utils.Assets;

public class Bow extends Weapon{
	
	public static AssetDescriptor<Texture> poisonSprite;
	
	public Bow(){
		super();
		weaponDescSprite = new Sprite(Assets.manager.get(Assets.bowDesc));
		sprite = new Sprite(Assets.manager.get(Assets.wepBOW));
		random = new Random();
		System.out.println("minDD" + minDD);
		System.out.println("maxDD" + maxDD);
		
		// temp
		minDD = 1;
		maxDD = 2;
	}

	public void update(TiledMapTileLayer collisionLayer) {
		super.update(collisionLayer);
	}
	
	
	@Override
	public void setDamage(int playerScore) {

		if(playerScore>=0 && playerScore<500){
			randBetVal = random.nextInt(4-2)+2;
			minDD = randBetVal-1;
			maxDD = randBetVal;
		}
		else if(playerScore>=500 && playerScore<1500){
			randBetVal = random.nextInt(8-4)+4;
			minDD = randBetVal-2;
			maxDD = randBetVal;
		}
		else if(playerScore>=1500 && playerScore<3000){
			randBetVal = random.nextInt(12-6)+6;
			minDD = randBetVal-4;
			maxDD = randBetVal;
		}
		else if(playerScore>=3000){
			randBetVal = random.nextInt(20-10)+10;
			minDD = randBetVal-6;
			maxDD = randBetVal;
		}
	}

}
