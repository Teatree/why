package com.me.swampmonster.models.items;

import java.util.Random;

import com.me.swampmonster.game.GShape;
import com.me.swampmonster.game.L1Renderer;
import com.me.swampmonster.game.TheController;
import com.me.swampmonster.game.WeaponUIWindow;
import com.me.swampmonster.models.Item;
import com.me.swampmonster.models.Player;

public class WeaponItem extends Item {

	public Weapon weapon;
	Random random;
	public String name = "unidentified";

	public WeaponItem() {

//		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());

		circle.radius = 16;
		
	}

	@Override
	public void pickMeUp(Player player) {
		// player.weapon = this.weapon;
		weapon.setStats(player.absoluteScore);
		TheController.showWeaponInv = true;
		GShape.weaponDialog = new WeaponUIWindow("statuk'", GShape.skin, this);
		GShape.weaponDialog.setSize(400, 400);
		GShape.weaponDialog.setX(170);
		GShape.weaponDialog.setY(40);
		GShape.weaponDialog.debug();
		TheController.paused = true;
		// System.out.println("player weapon stats: type:"
		// + player.weapon.getClass().getSimpleName() + " attack: "
		// + player.weapon.minDD + " - " + player.weapon.maxDD + " mods: "
		// + player.weapon.mod1 + " " + player.weapon.mod2);
		// state = State.DEAD;
		L1Renderer.stage.addActor(GShape.weaponDialog);
	}

	@Override
	public void parametersForThrowing(Player player) {
		// TODO Auto-generated method stub

	}

}
