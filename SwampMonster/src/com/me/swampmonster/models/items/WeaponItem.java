package com.me.swampmonster.models.items;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.game.GShape;
import com.me.swampmonster.game.L1Renderer;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.WeaponUIWindow;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class WeaponItem extends Item{

	public Weapon weapon;
	Random random;
	
	public WeaponItem() {

		animationsStandard.put(State.SPAWNING, new AnimationControl(Assets.manager.get(Assets.wepBOW), 4, 2, 4));
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.wepBOW), 4, 2, 4));
		animationsStandard.put(State.DEAD, new AnimationControl(Assets.manager.get(Assets.wepBOW), 4, 2, 4));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(Assets.manager.get(Assets.wepBOW), 4, 2, 4));
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
		
		circle.radius = 16;
		
	}
	
	@Override
	public void pickMeUp(Player player) {
//		player.weapon = this.weapon;
		weapon.setStats(player.absoluteScore);
		TheController.showWeaponInv = true;
		GShape.weaponDialog = new WeaponUIWindow("statuk'", GShape.skin, this);
		GShape.weaponDialog.setSize(400, 350);
		GShape.weaponDialog.setX(170);
		GShape.weaponDialog.setY(100);
		GShape.weaponDialog.debug();
		TheController.paused = true;
//		System.out.println("player weapon stats: type:"
//				+ player.weapon.getClass().getSimpleName() + " attack: "
//				+ player.weapon.minDD + " - " + player.weapon.maxDD + " mods: "
//				+ player.weapon.mod1 + " " + player.weapon.mod2);
//		state = State.DEAD;
		L1Renderer.stage.addActor(GShape.weaponDialog);
	}

	@Override
	public void parametersForThrowing(Player player) {
		// TODO Auto-generated method stub
		
	}

}
