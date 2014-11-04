package com.me.swampmonster.models.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class WeaponItem extends Item{

	public Weapon weapon;
	
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
		player.weapon = this.weapon;
		System.out.println("player weapon stats: type:"
				+ player.weapon.getClass().getSimpleName() + " attack: "
				+ player.weapon.minDD + " - " + player.weapon.maxDD + " mods: "
				+ player.weapon.mod1 + " " + player.weapon.mod2);
		state = State.DEAD;
	}

	@Override
	public void parametersForThrowing(Player player) {
		// TODO Auto-generated method stub
		
	}

}
