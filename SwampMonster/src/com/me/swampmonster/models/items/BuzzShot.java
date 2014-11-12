package com.me.swampmonster.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.models.L1;
import com.me.swampmonster.utils.Assets;

public class BuzzShot extends Weapon{

	public BuzzShot() {
		super();
		weaponDescSprite = new Sprite(Assets.manager.get(Assets.buzzDesc));
		
		setDamage(L1.player.absoluteScore);
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
