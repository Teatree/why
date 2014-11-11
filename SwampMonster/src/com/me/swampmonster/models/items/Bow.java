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
		setDamage(Player.absoluteScore);
		System.out.println("minDD" + minDD);
		System.out.println("maxDD" + maxDD);
	}

	public void update(TiledMapTileLayer collisionLayer) {
		super.update(collisionLayer);
	}
	
	private void setDamage(int playersScore) {
		float randBetVal;
		float randBetPers = (random.nextInt(30-5)+5);
		int foundMin;
		int foundMax;
		if(playersScore>=0 && playersScore<500){
			randBetVal = random.nextInt(4-2)+2;
			foundMin = (int) (randBetVal*(randBetPers/100));
			foundMax = (int) (randBetVal/(randBetPers/100));
			minDD = foundMin;
			maxDD = foundMax;
		}
		else if(playersScore>=500 && playersScore<1500){
			randBetVal = random.nextInt(8-4)+4;
			foundMin = (int) (randBetVal*(randBetPers/100));
			foundMax = (int) (randBetVal/(randBetPers/100));
			minDD = foundMin;
			maxDD = foundMax;
		}
		else if(playersScore>=1500 && playersScore<3000){
			randBetVal = random.nextInt(12-6)+6;
			foundMin = (int) (randBetVal*(randBetPers/100));
			foundMax = (int) (randBetVal/(randBetPers/100));
			minDD = foundMin;
			maxDD = foundMax;
		}
		else if(playersScore>=3000){
			randBetVal = random.nextInt(20-10)+10;
			foundMin = (int) (randBetVal*(randBetPers/100));
			foundMax = (int) (randBetVal/(randBetPers/100));
			minDD = foundMin;
			maxDD = foundMax;
		}
	}
	
}
