package com.me.swampmonster.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.utils.Assets;

public class CrossBow extends Weapon{
	
	public float playerMovementSpeed;
	
	public CrossBow() {
		super();
		weaponDescSprite = new Sprite(Assets.manager.get(Assets.crossBowDesc));
		
		coolDown = 240;
		setDamage(L1.player.absoluteScore);
		playerMovementSpeed = L1.player.movementSpeed;
	}
	

	@Override
	public void update(TiledMapTileLayer collisionLayer) {
		super.update(collisionLayer);
		
		if(coolDownCounter > 0){
			L1.player.movementSpeed/=2f;
		}else{
			L1.player.movementSpeed=playerMovementSpeed;
			System.out.println();
		}
	}

	@Override
	public void shoot(Vector3 V3point) {
		super.shoot(V3point);
		
		playerMovementSpeed = L1.player.movementSpeed;
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
