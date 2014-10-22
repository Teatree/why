package com.me.swampmonster.utils;

import com.badlogic.gdx.Gdx;

public class Constants {
	public static final float DEFAULT_PLAYER_DAMAGE = 1f;
	public static final float VIEWPORT_WIDTH = Gdx.graphics.getWidth();
	public static final float VIEWPORT_HEIGHT = Gdx.graphics.getHeight();
	public static final float VIEWPORT_GUI_WIDTH = Gdx.graphics.getWidth();
	public static final float VIEWPORT_GUI_HEIGHT = Gdx.graphics.getHeight();
	public static final int SLOT_DESC_WINDOW_HEIGHT = 300;
	public static final int SLOT_DESC_WINDOW_WIDTH = 400;
	
	public static final String sufficateMessage1 = "You forgot your oxygen mask, you big dummy!";
	public static final String sufficateMessage2 = "I gues the absence of oxygen has left you... breathless.";
	public static final String sufficateMessage3 = "Don't forget to breath next time!";
	public static final String enemyMessage1 = "I think that guy likes you";
	public static final String enemyMessage2 = "You went up agaist a zombie, and lost";
	public static final String enemyMessage3 = "Bet you thought that guy was friendly";
	public static final String poisionedMessage1 = "You shouldn't eat Pizza with it's box!"; 
	public static final String poisionedMessage2 = "That went right into your ear!";
	public static final String poisionedMessage3 = "Tyrion did it!";
	
	public static final int pendingPeriodBetweedWaves = 500;
	public static final int NodeSize = 16;
	
	public static final int Max_slot_level = 4;
	
	//:TODO THE AWESOME SLOT DATA!
//  Arrows3:
	public static final String Arrows3_Name = "Triple Arrows!";
	
	public static final int Arrows3_CoolDown_L1 = 1200;
	public static final int Arrows3_CoolDown_L2 = 900;
	public static final int Arrows3_CoolDown_L3 = 900;
	public static final int Arrows3_CoolDown_L4 = 600;
	public static final int Arrows3_CoolDown_L5 = 600;
	
	public static final String Arrows3_Description_L1 = "I shoot 3 arrows at thee l1 ";
	public static final String Arrows3_Description_L2 = "I shoot 3 arrows at thee l2 ";
	public static final String Arrows3_Description_L3 = "I shoot 3 arrows at thee l3 ";
	public static final String Arrows3_Description_L4 = "I shoot 3 arrows at thee l4 ";
	public static final String Arrows3_Description_L5 = "I shoot 3 arrows at thee l4 "; 
	
	
//  DamageTrap:
	public static final String DamageTrap_Name = "Damage Trap";
	
	public static final int DamageTrap_CoolDown_L1 = 1800;
	public static final int DamageTrap_CoolDown_L2 = 1800;
	public static final int DamageTrap_CoolDown_L3 = 1200;
	public static final int DamageTrap_CoolDown_L4 = 1200;
	public static final int DamageTrap_CoolDown_L5 = 900;
	
	public static final float DamageTrap_Damage_L1 = 2f;
	public static final float DamageTrap_Damage_L2 = 2f;
	public static final float DamageTrap_Damage_L3 = 3f;
	public static final float DamageTrap_Damage_L4 = 4f;
	public static final float DamageTrap_Damage_L5 = 5f;
	
	public static final int DamageTrap_CircleRadius_L1 = 10;
	public static final int DamageTrap_CircleRadius_L2 = 20;
	public static final int DamageTrap_CircleRadius_L3 = 30;
	public static final int DamageTrap_CircleRadius_L4 = 40;
	public static final int DamageTrap_CircleRadius_L5 = 50;
	
	public static final int DamageTrap_LifeTimeMax_L1 = 300;
	public static final int DamageTrap_LifeTimeMax_L2 = 400;
	public static final int DamageTrap_LifeTimeMax_L3 = 500;
	public static final int DamageTrap_LifeTimeMax_L4 = 600;
	public static final int DamageTrap_LifeTimeMax_L5 = 700;
	
	public static final String DamageTrap_Description_L1 = "Cause " + DamageTrap_Damage_L1 +" damage  to enemy " ;
	public static final String DamageTrap_Description_L2 = "Cause " + DamageTrap_Damage_L2 +" damage  to enemy ";
	public static final String DamageTrap_Description_L3 = "Cause " + DamageTrap_Damage_L3 +" damage  to enemy ";
	public static final String DamageTrap_Description_L4 = "Cause " + DamageTrap_Damage_L4 +" damage  to enemy ";
	public static final String DamageTrap_Description_L5 = "Cause " + DamageTrap_Damage_L5 +" damage  to enemy ";
	
	
//	ExplosiveArrow:
	public static final String ExplosiveArrow_Name = "Explosive Arrow";
	
	public static final int ExplosiveArrow_CoolDown_L1 = 600;
	public static final int ExplosiveArrow_CoolDown_L2 = 500;
	public static final int ExplosiveArrow_CoolDown_L3 = 400;
	public static final int ExplosiveArrow_CoolDown_L4 = 300;
	public static final int ExplosiveArrow_CoolDown_L5 = 200;
	
	public static final String ExplosiveArrow_Description_L1 = "I am explosive arrow l1";
	public static final String ExplosiveArrow_Description_L2 = "I am explosive arrow l2";
	public static final String ExplosiveArrow_Description_L3 = "I am explosive arrow l3";
	public static final String ExplosiveArrow_Description_L4 = "I am explosive arrow l4";
	public static final String ExplosiveArrow_Description_L5 = "I am explosive arrow l5";
	
	public static final float ExplosiveArrow_explCircleRadius_L1 = 10;
	public static final float ExplosiveArrow_explCircleRadius_L2 = 12;
	public static final float ExplosiveArrow_explCircleRadius_L3 = 14;
	public static final float ExplosiveArrow_explCircleRadius_L4 = 16;
	public static final float ExplosiveArrow_explCircleRadius_L5 = 18;
	
	public static final float ExplosiveArrow_damage_L1 = 2f;
	public static final float ExplosiveArrow_damage_L2 = 2f;
	public static final float ExplosiveArrow_damage_L3 = 3f;
	public static final float ExplosiveArrow_damage_L4 = 3f;
	public static final float ExplosiveArrow_damage_L5 = 4f;
	
	public static final float ExplosiveArrow_incrementExplosionRadius_L1 = 3f;
	public static final float ExplosiveArrow_incrementExplosionRadius_L2 = 4f;
	public static final float ExplosiveArrow_incrementExplosionRadius_L3 = 5f;
	public static final float ExplosiveArrow_incrementExplosionRadius_L4 = 6f;
	public static final float ExplosiveArrow_incrementExplosionRadius_L5 = 7f;
	
	public static final float ExplosiveArrow_incrementDamage_L1 = 0.013f;
	public static final float ExplosiveArrow_incrementDamage_L2 = 0.011f;
	public static final float ExplosiveArrow_incrementDamage_L3 = 0.010f;
	public static final float ExplosiveArrow_incrementDamage_L4 = 0.09f;
	public static final float ExplosiveArrow_incrementDamage_L5 = 0.08f;
	
	
	
//	NUKE:
	public static final int NUKE_CoolDown_L1 = 1000;
	public static final int NUKE_CoolDown_L2 = 800;
	public static final int NUKE_CoolDown_L3 = 600;
	public static final int NUKE_CoolDown_L4 = 400;
	public static final int NUKE_CoolDown_L5 = 200;
	
	public static final float NUKE_CircleRadius_L1 = 16;
	public static final float NUKE_CircleRadius_L2 = 20;
	public static final float NUKE_CircleRadius_L3 = 25;
	public static final float NUKE_CircleRadius_L4 = 30;
	public static final float NUKE_CircleRadius_L5 = 35;
	
	public static final int NUKE_LifeTime_L1 = 900;
	public static final int NUKE_LifeTime_L2 = 900;
	public static final int NUKE_LifeTime_L3 = 900;
	public static final int NUKE_LifeTime_L4 = 1200;
	public static final int NUKE_LifeTime_L5 = 1600;
	
	public static final String NUKE_Description_L1 = "ExplosionTrap Level 1";
	public static final String NUKE_Description_L2 = "ExplosionTrap Level 2";
	public static final String NUKE_Description_L3 = "ExplosionTrap Level 3";
	public static final String NUKE_Description_L4 = "ExplosionTrap Level 4";
	public static final String NUKE_Description_L5 = "ExplosionTrap Level 5";
	
	public static final float NUKE_explCircleRadius_L1 = 10;
	public static final float NUKE_explCircleRadius_L2 = 12;
	public static final float NUKE_explCircleRadius_L3 = 14;
	public static final float NUKE_explCircleRadius_L4 = 16;
	public static final float NUKE_explCircleRadius_L5 = 18;
	
	public static final float NUKE_damage_L1 = 3;
	public static final float NUKE_damage_L2 = 3f;
	public static final float NUKE_damage_L3 = 4f;
	public static final float NUKE_damage_L4 = 4f;
	public static final float NUKE_damage_L5 = 5f;
	
	public static final float NUKE_incrementExplosionRadius_L1 = 3f;
	public static final float NUKE_incrementExplosionRadius_L2 = 4f;
	public static final float NUKE_incrementExplosionRadius_L3 = 5f;
	public static final float NUKE_incrementExplosionRadius_L4 = 6f;
	public static final float NUKE_incrementExplosionRadius_L5 = 7f;
	
	public static final float NUKE_incrementDamage_L1 = 0.13f;
	public static final float NUKE_incrementDamage_L2 = 0.11f;
	public static final float NUKE_incrementDamage_L3 = 0.10f;
	public static final float NUKE_incrementDamage_L4 = 0.09f;
	public static final float NUKE_incrementDamage_L5 = 0.08f;
	
	
//	FADE:
	public static final int FADE_CoolDown_L1 = 1200;
	public static final int FADE_CoolDown_L2 = 1000;
	public static final int FADE_CoolDown_L3 = 800;
	public static final int FADE_CoolDown_L4 = 600;
	public static final int FADE_CoolDown_L5 = 400;

	public static final int FADE_LifeTime_L1 = 600;
	public static final int FADE_LifeTime_L2 = 600;
	public static final int FADE_LifeTime_L3 = 900;
	public static final int FADE_LifeTime_L4 = 1200;
	public static final int FADE_LifeTime_L5 = 1200;

	public static final String FADE_Description_L1 = "FADE of da Level 1";
	public static final String FADE_Description_L2 = "FADE of da Level 2";
	public static final String FADE_Description_L3 = "FADE of da Level 3";
	public static final String FADE_Description_L4 = "FADE of da Level 4";
	public static final String FADE_Description_L5 = "FADE of da Level 5";
	
	
//	TURRET:
	public static final String TURRET_Name = "Gun Turret";
	
	public static final int TURRET_CoolDown_L1 = 2100;
	public static final int TURRET_CoolDown_L2 = 1800;
	public static final int TURRET_CoolDown_L3 = 1200;
	public static final int TURRET_CoolDown_L4 = 900;
	public static final int TURRET_CoolDown_L5 = 900;
	
	public static final int TURRET_LifeTime_L1 = 900;
	public static final int TURRET_LifeTime_L2 = 900;
	public static final int TURRET_LifeTime_L3 = 1400;
	public static final int TURRET_LifeTime_L4 = 1600;
	public static final int TURRET_LifeTime_L5 = 2000;
	
	public static final int TURRET_Health_L1 = 3;
	public static final int TURRET_Health_L2 = 3;
	public static final int TURRET_Health_L3 = 4;
	public static final int TURRET_Health_L4 = 4;
	public static final int TURRET_Health_L5 = 5;
	
	public static final float TURRET_Damage_L1 = 0.5f;
	public static final float TURRET_Damage_L2 = 0.5f;
	public static final float TURRET_Damage_L3 = 1f;
	public static final float TURRET_Damage_L4 = 2f;
	public static final float TURRET_Damage_L5 = 3f;
	
	public static final int TURRET_AttackSpeed_L1 = 100;
	public static final int TURRET_AttackSpeed_L2 = 80;
	public static final int TURRET_AttackSpeed_L3 = 80;
	public static final int TURRET_AttackSpeed_L4 = 60;
	public static final int TURRET_AttackSpeed_L5 = 45;
	
	public static final String TURRET_Description_L1 = "Turret of da Level 1";
	public static final String TURRET_Description_L2 = "Turret of da Level 2";
	public static final String TURRET_Description_L3 = "Turret of da Level 3";
	public static final String TURRET_Description_L4 = "Turret of da Level 4";
	public static final String TURRET_Description_L5 = "Turret of da Level 5";
	
	
//	FrostTrap:
	public static final String FrostTrap_Name = "Frost Trap";

	public static final int FrostTrap_CoolDown_L1 = 2000;
	public static final int FrostTrap_CoolDown_L2 = 1700;
	public static final int FrostTrap_CoolDown_L3 = 1400;
	public static final int FrostTrap_CoolDown_L4 = 1100;
	public static final int FrostTrap_CoolDown_L5 = 800;

	public static final int FrostTrap_LifeTime_L1 = 900;
	public static final int FrostTrap_LifeTime_L2 = 900;
	public static final int FrostTrap_LifeTime_L3 = 900;
	public static final int FrostTrap_LifeTime_L4 = 1200;
	public static final int FrostTrap_LifeTime_L5 = 1500;

	public static final int FrostTrap_CircleRadius_L1 = 12;
	public static final int FrostTrap_CircleRadius_L2 = 16;
	public static final int FrostTrap_CircleRadius_L3 = 20;
	public static final int FrostTrap_CircleRadius_L4 = 24;
	public static final int FrostTrap_CircleRadius_L5 = 28;
	
	public static final String FrostTrap_Description_L1 = "FrostTrap of Level 1";
	public static final String FrostTrap_Description_L2 = "FrostTrap of Level 2";
	public static final String FrostTrap_Description_L3 = "FrostTrap of Level 3";
	public static final String FrostTrap_Description_L4 = "FrostTrap of Level 4";
	public static final String FrostTrap_Description_L5 = "FrostTrap of Level 5";
	
	
//	ImproveArrowDamage:
	public static final String ImproveArrowDamage_Name = "Improve Arrow Damage";
	
	public static final float ImproveArrowDamage_value_L1 = 12f;
	public static final float ImproveArrowDamage_value_L2 = 12f;
	public static final float ImproveArrowDamage_value_L3 = 12f;
	public static final float ImproveArrowDamage_value_L4 = 12f;
	public static final float ImproveArrowDamage_value_L5 = 12f;
	
	public static final String ImproveArrowDamage_Description_l1 = "Increase Arrow Damage by " + ImproveArrowDamage_value_L1;
	public static final String ImproveArrowDamage_Description_l2 = "Increase Arrow Damage by " + ImproveArrowDamage_value_L2;
	public static final String ImproveArrowDamage_Description_l3 = "Increase Arrow Damage by " + ImproveArrowDamage_value_L3;
	public static final String ImproveArrowDamage_Description_l4 = "Increase Arrow Damage by " + ImproveArrowDamage_value_L4;
	public static final String ImproveArrowDamage_Description_l5 = "Increase Arrow Damage by " + ImproveArrowDamage_value_L5;

//	ImproveArrowSpeed:
	public static final String ImproveArrowSpeed_Name = "Improve Arrow Speed";
	
	public static final float ImproveArrowSpeed_value_L1 = 1;
	public static final float ImproveArrowSpeed_value_L2 = 2;
	public static final float ImproveArrowSpeed_value_L3 = 3;
	public static final float ImproveArrowSpeed_value_L4 = 4;
	public static final float ImproveArrowSpeed_value_L5 = 5;
	
	public static final String ImproveArrowSpeed_Description_l1 = "Increase Arrow Speed by " + ImproveArrowSpeed_value_L1;
	public static final String ImproveArrowSpeed_Description_l2 = "Increase Arrow Speed by " + ImproveArrowSpeed_value_L2;
	public static final String ImproveArrowSpeed_Description_l3 = "Increase Arrow Speed by " + ImproveArrowSpeed_value_L3;
	public static final String ImproveArrowSpeed_Description_l4 = "Increase Arrow Speed by " + ImproveArrowSpeed_value_L4;
	public static final String ImproveArrowSpeed_Description_l5 = "Increase Arrow Speed by " + ImproveArrowSpeed_value_L5;

	
//	ImproveMaxHealth:
	public static final String ImproveMaxHealth_Name = "Improve Health";
	
	public static final int ImproveMaxHealth_HealthValue_L1 = 1;
	public static final int ImproveMaxHealth_HealthValue_L2 = 2;
	public static final int ImproveMaxHealth_HealthValue_L3 = 3;
	public static final int ImproveMaxHealth_HealthValue_L4 = 4;
	public static final int ImproveMaxHealth_HealthValue_L5 = 5;
	
	public static final String ImproveMaxHealth_Description_l1 = "Increase max health value by " + ImproveMaxHealth_HealthValue_L1;
	public static final String ImproveMaxHealth_Description_l2 = "Increase max health value by "  + ImproveMaxHealth_HealthValue_L2;
	public static final String ImproveMaxHealth_Description_l3 = "Increase max health value by " + ImproveMaxHealth_HealthValue_L3;
	public static final String ImproveMaxHealth_Description_l4 = "Increase max health value by " + ImproveMaxHealth_HealthValue_L4;
	public static final String ImproveMaxHealth_Description_l5 = "Increase max health value by " + ImproveMaxHealth_HealthValue_L5;
	
	
//	ImproveMaxOxygen:
	public static final String ImproveMaxOxygen_Name = "Improve Oxygen";
	
	public static final float ImproveMaxOxygen_OxygenValue_L1 = 10f;
	public static final float ImproveMaxOxygen_OxygenValue_L2 = 15f;
	public static final float ImproveMaxOxygen_OxygenValue_L3 = 20f;
	public static final float ImproveMaxOxygen_OxygenValue_L4 = 25f;
	public static final float ImproveMaxOxygen_OxygenValue_L5 = 30f;
	
	public static final String ImproveMaxOxygen_Description_l1 = "Increase max oxygen value by " + ImproveMaxOxygen_OxygenValue_L1;
	public static final String ImproveMaxOxygen_Description_l2 = "Increase max oxygen value by "  + ImproveMaxOxygen_OxygenValue_L2;
	public static final String ImproveMaxOxygen_Description_l3 = "Increase max oxygen value by " + ImproveMaxOxygen_OxygenValue_L3;
	public static final String ImproveMaxOxygen_Description_l4 = "Increase max oxygen value by " + ImproveMaxOxygen_OxygenValue_L4;
	public static final String ImproveMaxOxygen_Description_l5 = "Increase max oxygen value by " + ImproveMaxOxygen_OxygenValue_L5;

	
	//	ImproveMaxSpeed:
	public static final String ImproveMaxSpeed_Name = "Improve Speed";
	
	public static final float ImproveMaxSpeed_SpeedValue_L1 = 0.2f;
	public static final float ImproveMaxSpeed_SpeedValue_L2 = 0.4f;
	public static final float ImproveMaxSpeed_SpeedValue_L3 = 0.4f;
	public static final float ImproveMaxSpeed_SpeedValue_L4 = 0.4f;
	public static final float ImproveMaxSpeed_SpeedValue_L5 = 1f;
	
	public static final String ImproveMaxSpeed_Description_l1 = "Increase max Speed by " + ImproveMaxSpeed_SpeedValue_L1;
	public static final String ImproveMaxSpeed_Description_l2 = "Increase max Speed by " + ImproveMaxSpeed_SpeedValue_L2;
	public static final String ImproveMaxSpeed_Description_l3 = "Increase max Speed by " + ImproveMaxSpeed_SpeedValue_L3;
	public static final String ImproveMaxSpeed_Description_l4 = "Increase max Speed by " + ImproveMaxSpeed_SpeedValue_L4;
	public static final String ImproveMaxSpeed_Description_l5 = "Increase max Speed by " + ImproveMaxSpeed_SpeedValue_L5;

	
//	PoisonArrow:
	public static final String PoisonArrow_Name = "Position Arrow";
	
	public static final int PoisonArrow_CoolDown_L1 = 2100;
	public static final int PoisonArrow_CoolDown_L2 = 1800;
	public static final int PoisonArrow_CoolDown_L3 = 1200;
	public static final int PoisonArrow_CoolDown_L4 = 900;
	public static final int PoisonArrow_CoolDown_L5 = 900;
	
	public static final String PoisonArrow_Description_L1 = "I am poisonedArrow of Level 1!";
	public static final String PoisonArrow_Description_L2 = "I am poisonedArrow of Level 2!";
	public static final String PoisonArrow_Description_L3 = "I am poisonedArrow of Level 3!";
	public static final String PoisonArrow_Description_L4 = "I am poisonedArrow of Level 4!";
	public static final String PoisonArrow_Description_L5 = "I am poisonedArrow of Level 5!";
	
	
//	PoisonTrap:
	public static final String PoisonTrap_Name = "Position Trap";
	
	public static final int PoisonTrap_CoolDown_L1 = 1800;
	public static final int PoisonTrap_CoolDown_L2 = 1800;
	public static final int PoisonTrap_CoolDown_L3 = 1200;
	public static final int PoisonTrap_CoolDown_L4 = 1200;
	public static final int PoisonTrap_CoolDown_L5 = 900;
	
	public static final int PoisonTrap_Radius_L1 = 16;
	public static final int PoisonTrap_Radius_L2 = 20;
	public static final int PoisonTrap_Radius_L3 = 24;
	public static final int PoisonTrap_Radius_L4 = 24;
	public static final int PoisonTrap_Radius_L5 = 28;
	
	public static final int PoisonTrap_LifeTime_L1 = 1000;
	public static final int PoisonTrap_LifeTime_L2 = 1500;
	public static final int PoisonTrap_LifeTime_L3 = 2000;
	public static final int PoisonTrap_LifeTime_L4 = 2500;
	public static final int PoisonTrap_LifeTime_L5 = 3000;
	
	public static final String PoisonTrap_Description_L1 = "I am poisoned Trap of Level 1!";
	public static final String PoisonTrap_Description_L2 = "I am poisoned Trap of Level 2!";
	public static final String PoisonTrap_Description_L3 = "I am poisoned Trap of Level 3!";
	public static final String PoisonTrap_Description_L4 = "I am poisoned Trap of Level 4!";
	public static final String PoisonTrap_Description_L5 = "I am poisoned Trap of Level 5!";
	
	
//	RADIOACTIVE:
	public static final int RADIOACTIVE_CoolDown_L1 = 1800;
	public static final int RADIOACTIVE_CoolDown_L2 = 1500;
	public static final int RADIOACTIVE_CoolDown_L3 = 1200;
	public static final int RADIOACTIVE_CoolDown_L4 = 900;
	public static final int RADIOACTIVE_CoolDown_L5 = 900;
	
	public static final int RADIOACTIVE_Damage_L1 = 1;
	public static final int RADIOACTIVE_Damage_L2 = 2;
	public static final int RADIOACTIVE_Damage_L3 = 2;
	public static final int RADIOACTIVE_Damage_L4 = 3;
	public static final int RADIOACTIVE_Damage_L5 = 4;
	
	public static final int RADIOACTIVE_LifeTime_L1 = 300;
	public static final int RADIOACTIVE_LifeTime_L2 = 500;
	public static final int RADIOACTIVE_LifeTime_L3 = 700;
	public static final int RADIOACTIVE_LifeTime_L4 = 900;
	public static final int RADIOACTIVE_LifeTime_L5 = 1220;
	
	public static final float RADIOACTIVE_Radius_L1 = 16;
	public static final float RADIOACTIVE_Radius_L2 = 20;
	public static final float RADIOACTIVE_Radius_L3 = 23;
	public static final float RADIOACTIVE_Radius_L4 = 24;
	public static final float RADIOACTIVE_Radius_L5 = 30;
	
	public static final String RADIOACTIVE_Description_L1 = "Sup, name is RADIOACTIVE Aura Level 1!";
	public static final String RADIOACTIVE_Description_L2 = "Sup, name is RADIOACTIVE Aura Level 2!";
	public static final String RADIOACTIVE_Description_L3 = "Sup, name is RADIOACTIVE Aura Level 3!";
	public static final String RADIOACTIVE_Description_L4 = "Sup, name is RADIOACTIVE Aura Level 4!";
	public static final String RADIOACTIVE_Description_L5 = "Sup, name is RADIOACTIVE Aura Level 5!";
	
	
//	PLASMA_SHIELD:
	public static final String PLASMA_SHIELD_Name = "Plasma Shield";
	
	public static final int PLASMA_SHIELD_CoolDown_L1 = 1800;
	public static final int PLASMA_SHIELD_CoolDown_L2 = 1600;
	public static final int PLASMA_SHIELD_CoolDown_L3 = 1200;
	public static final int PLASMA_SHIELD_CoolDown_L4 = 900;
	public static final int PLASMA_SHIELD_CoolDown_L5 = 600;
	
	public static final int PLASMA_SHIELD_LifeTime_L1 = 600;
	public static final int PLASMA_SHIELD_LifeTime_L2 = 600;
	public static final int PLASMA_SHIELD_LifeTime_L3 = 900;
	public static final int PLASMA_SHIELD_LifeTime_L4 = 1200;
	public static final int PLASMA_SHIELD_LifeTime_L5 = 1500;

	public static final String PLASMA_SHIELD_Description_L1 = "PLASMA_SHIELD SA Level 1";
	public static final String PLASMA_SHIELD_Description_L2 = "PLASMA_SHIELD SA Level 2";
	public static final String PLASMA_SHIELD_Description_L3 = "PLASMA_SHIELD SA Level 3";
	public static final String PLASMA_SHIELD_Description_L4 = "PLASMA_SHIELD SA Level 4";
	public static final String PLASMA_SHIELD_Description_L5 = "PLASMA_SHIELD SA Level 5";

//	PANIC_TELEPORT:
	public static final String PANIC_TELEPORT_Name = "Panic Teleport";
	
	public static final int PANIC_TELEPORT_CoolDown_L1 = 1800;
	public static final int PANIC_TELEPORT_CoolDown_L2 = 1400;
	public static final int PANIC_TELEPORT_CoolDown_L3 = 1200;
	public static final int PANIC_TELEPORT_CoolDown_L4 = 900;
	public static final int PANIC_TELEPORT_CoolDown_L5 = 600;
	public static final String PANIC_TELEPORT_Description_L1 = "PANIC_TELEPORT SA Level 1";
	public static final String PANIC_TELEPORT_Description_L2 = "PANIC_TELEPORT SA Level 2";
	public static final String PANIC_TELEPORT_Description_L3 = "PANIC_TELEPORT SA Level 3";
	public static final String PANIC_TELEPORT_Description_L4 = "PANIC_TELEPORT SA Level 4";
	public static final String PANIC_TELEPORT_Description_L5 = "PANIC_TELEPORT SA Level 5";
	
//	SPEED_BOOST:
	public static final String SPEED_BOOST_Name = "Speed Boost";
	
	public static final int SPEED_BOOST_CoolDown_L1 = 1800;
	public static final int SPEED_BOOST_CoolDown_L2 = 1800;
	public static final int SPEED_BOOST_CoolDown_L3 = 1200;
	public static final int SPEED_BOOST_CoolDown_L4 = 1200;
	public static final int SPEED_BOOST_CoolDown_L5 = 900;

	public static final String SPEED_BOOST_Description_L1 = "Speed Boost LEVEL: 1";
	public static final String SPEED_BOOST_Description_L2 = "Speed Boost LEVEL: 2";
	public static final String SPEED_BOOST_Description_L3 = "Speed Boost LEVEL: 3";
	public static final String SPEED_BOOST_Description_L4 = "Speed Boost LEVEL: 4";
	public static final String SPEED_BOOST_Description_L5 = "Speed Boost LEVEL: 5";

	public static final int SPEED_BOOST_LifeTime_L1 = 600;
	public static final int SPEED_BOOST_LifeTime_L2 = 600;
	public static final int SPEED_BOOST_LifeTime_L3 = 900;
	public static final int SPEED_BOOST_LifeTime_L4 = 1200;
	public static final int SPEED_BOOST_LifeTime_L5 = 1600;
	
// ----------------- ITEMS ---------------------
	//HASTE
	public static final int HASTE_LifeTime_L1 = 600;
	public static final int HASTE_LifeTime_L2 = 900;
	public static final int HASTE_LifeTime_L3 = 900;
	public static final int HASTE_LifeTime_L4 = 1200;
	public static final int HASTE_LifeTime_L5 = 1600;
	
	//WEAKENED
	public static final int WEAKENED_LifeTime_L1 = 300;
	public static final int WEAKENED_LifeTime_L2 = 300;
	public static final int WEAKENED_LifeTime_L3 = 600;
	public static final int WEAKENED_LifeTime_L4 = 1200;
	public static final int WEAKENED_LifeTime_L5 = 1200;
	
	//feedback
	public static final String SHOT_ARROWS = "Shot arrows: ";
	public static final String ENEMIES_KILLED = "Enemies killed: ";
	public static final String SCORE = "Score: ";
	public static final String ONE_MORE_WORLD_CONQUERED = "One More World conquered!";
	public static final String GET_REWARD = "Get reward!";
	public static final String TRY_AGAIN = "Next time I'll be better!";
	
	//Exitmessage
	public static final String EXIT_MESSAGE = "Exit to main menu";
	
	//UI stuff
	public static final String EXIT = "Exit";
	public static final String PLAY = "Play";
	public static final String TUTORIAL = "Tutorial";
	public static final String WORLDS_CONQUERROR = "World's Conqueror";
	
}
