package com.me.swampmonster.models.slots;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.Turret;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.Constants;

public class TurretSkill extends Slot {

	public Turret turret;
	public static int level;
	private static Map<Integer, String> descriptionByLevel;
	static {
		descriptionByLevel = new HashMap<Integer, String>();
		descriptionByLevel.put(0, Constants.TURRET_Description_L1);
		descriptionByLevel.put(1, Constants.TURRET_Description_L2);
		descriptionByLevel.put(2, Constants.TURRET_Description_L3);
		descriptionByLevel.put(3, Constants.TURRET_Description_L4);
		descriptionByLevel.put(4, Constants.TURRET_Description_L5);
	}

	public TurretSkill() {
		name = Constants.TURRET_Name;
		sprite = new Sprite(Assets.manager.get(Assets.TURRET_ICON));
		turret = new Turret();
		turret.state = State.SPAWNING;

	}

	@Override
	public void execute(Player player) {
		System.out.println("execute, turret: " + player.turret);
		
		switch (level) {
		case 0:
			turret.damage = Constants.TURRET_Damage_L1;
			turret.health = Constants.TURRET_Health_L1;
			turret.attackSpeed = Constants.TURRET_AttackSpeed_L1;
			turret.lifeTime = Constants.TURRET_LifeTime_L1;
			coolDown = Constants.TURRET_CoolDown_L1;
			break;
		case 1:
			turret.damage = Constants.TURRET_Damage_L2;
			turret.health = Constants.TURRET_Health_L2;
			turret.attackSpeed = Constants.TURRET_AttackSpeed_L2;
			turret.lifeTime = Constants.TURRET_LifeTime_L2;
			coolDown = Constants.TURRET_CoolDown_L2;
			break;
		case 2:
			turret.damage = Constants.TURRET_Damage_L3;
			turret.health = Constants.TURRET_Health_L3;
			turret.attackSpeed = Constants.TURRET_AttackSpeed_L3;
			turret.lifeTime = Constants.TURRET_LifeTime_L3;
			coolDown = Constants.TURRET_CoolDown_L3;
			break;
		case 3:
			turret.damage = Constants.TURRET_Damage_L4;
			turret.health = Constants.TURRET_Health_L4;
			turret.attackSpeed = Constants.TURRET_AttackSpeed_L4;
			turret.lifeTime = Constants.TURRET_LifeTime_L4;
			coolDown = Constants.TURRET_CoolDown_L4;
			break;
		case 4:
			turret.damage = Constants.TURRET_Damage_L5;
			turret.health = Constants.TURRET_Health_L5;
			turret.attackSpeed = Constants.TURRET_AttackSpeed_L5;
			turret.lifeTime = Constants.TURRET_LifeTime_L5;
			coolDown = Constants.TURRET_CoolDown_L5;
			break;
		}
		
		turret.standardLifeTime = turret.lifeTime - 40;
		
		player.turret = this.turret;
		player.turret.state = State.SPAWNING;
		player.turret.position = new Vector2(player.position.x,
				player.position.y);
//		System.out.println("turret.pos : " + player.turret.position);
//		System.out.println("turret.STATE : " + player.turret.state);
//		System.out.println("turret.sprite : " + player.turret.sprite);
		player.turret.killingAura = new Circle();
		player.turret.killingAura.x = player.turret.position.x;
		player.turret.killingAura.y = player.turret.position.y;
		player.turret.killingAura.radius = 128;
		player.turret.turretAimerBot.x = player.turret.position.x;
		player.turret.turretAimerBot.y = player.turret.position.y;
		player.turret.turretAimerBot.width = 5;
		player.turret.turretAimerBot.height = 5;
		player.turret.circle = new Circle();
		player.turret.circle.radius = 16;
		player.turret.circle.x = player.turret.position.x
				+ player.turret.sprite.getWidth() / 2;
		player.turret.circle.y = player.turret.position.y
				+ player.turret.sprite.getHeight() / 2;
	}

	@Override
	public String getDescription() {
		return descriptionByLevel.get(level);
	}

	@Override
	public String getDescriptionForSaved() {
		return descriptionByLevel.get(level-1);
	}

	@Override
	public List<String> getStats(Player player) {
		// TODO Auto-generated method stub
		return null;
	}
}
