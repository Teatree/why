package com.me.swampmonster.utils;

import com.badlogic.gdx.Gdx;

public class Constants {
	public static final float DEFAULT_PLAYER_min_DAMAGE = 1f;
	public static final float DEFAULT_PLAYER_max_DAMAGE = 3f;
	public static final float VIEWPORT_WIDTH = Gdx.graphics.getWidth();
	public static final float VIEWPORT_HEIGHT = Gdx.graphics.getHeight();
	public static final float VIEWPORT_GUI_WIDTH = Gdx.graphics.getWidth();
	public static final float VIEWPORT_GUI_HEIGHT = Gdx.graphics.getHeight();
	public static final int SLOT_DESC_WINDOW_HEIGHT = 300;
	public static final int SLOT_DESC_WINDOW_WIDTH = 400;
	public static final float OXYGEN_DECREASE = 0.02f;

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

	// :TODO THE AWESOME SLOT DATA!
	// Arrows3:
	public static final String Arrows3_Name = "Triple Arrows!";
	public static final int Arrow3_unlockScore = 0;
	
	public static final int Arrows3_CoolDown_L1 = 1800;
	public static final int Arrows3_CoolDown_L2 = 1600;
	public static final int Arrows3_CoolDown_L3 = 1600;
	public static final int Arrows3_CoolDown_L4 = 1200;
	public static final int Arrows3_CoolDown_L5 = 1200;

	public static final String Arrows3_Description_L1 = "Loads bow with 3 arrows, next time you"
			+ " shoot, instead of 1 arrow, 3 come out L1";
	public static final String Arrows3_Description_L2 = "Loads bow with 3 arrows, next time you"
			+ " shoot, instead of 1 arrow, 3 come out L2";
	public static final String Arrows3_Description_L3 = "Loads bow with 3 arrows, next time you"
			+ " shoot, instead of 1 arrow, 3 come out L3 ";
	public static final String Arrows3_Description_L4 = "Loads bow with 3 arrows, next time you"
			+ " shoot, instead of 1 arrow, 3 come out L4";
	public static final String Arrows3_Description_L5 = "Loads bow with 3 arrows, next time you"
			+ " shoot, instead of 1 arrow, 3 come out L5";

	// DamageTrap:
	public static final String DamageTrap_Name = "Damage Trap";
	public static final int DamageTrap_unlockScore = 0;
	
	public static final int DamageTrap_CoolDown_L1 = 900;
	public static final int DamageTrap_CoolDown_L2 = 900;
	public static final int DamageTrap_CoolDown_L3 = 720;
	public static final int DamageTrap_CoolDown_L4 = 600;
	public static final int DamageTrap_CoolDown_L5 = 600;

	public static final float DamageTrap_Damage_L1 = 2f;
	public static final float DamageTrap_Damage_L2 = 2f;
	public static final float DamageTrap_Damage_L3 = 3f;
	public static final float DamageTrap_Damage_L4 = 4f;
	public static final float DamageTrap_Damage_L5 = 4f;

	public static final int DamageTrap_LifeTimeMax_L1 = 1500;
	public static final int DamageTrap_LifeTimeMax_L2 = 1800;
	public static final int DamageTrap_LifeTimeMax_L3 = 1800;
	public static final int DamageTrap_LifeTimeMax_L4 = 1800;
	public static final int DamageTrap_LifeTimeMax_L5 = 2400;

	public static final String DamageTrap_Description_L1 = "Spawns a trap, which deals "
			+ DamageTrap_Damage_L1 + "damage to the enemy if stepped on L1";
	public static final String DamageTrap_Description_L2 = "Spawns a trap, which deals "
			+ DamageTrap_Damage_L2 + "damage to the enemy if stepped on L2";
	public static final String DamageTrap_Description_L3 = "Spawns a trap, which deals "
			+ DamageTrap_Damage_L3 + "damage to the enemy if stepped on L3";
	public static final String DamageTrap_Description_L4 = "Spawns a trap, which deals "
			+ DamageTrap_Damage_L4 + "damage to the enemy if stepped on L4";
	public static final String DamageTrap_Description_L5 = "Spawns a trap, which deals "
			+ DamageTrap_Damage_L5 + "damage to the enemy if stepped on";

	// ExplosiveArrow:
	public static final String ExplosiveArrow_Name = "Explosive Arrow";
	public static final int ExplosiveArrow_unlockScore = 1000;

	public static final int ExplosiveArrow_CoolDown_L1 = 2400;
	public static final int ExplosiveArrow_CoolDown_L2 = 2100;
	public static final int ExplosiveArrow_CoolDown_L3 = 2100;
	public static final int ExplosiveArrow_CoolDown_L4 = 1800;
	public static final int ExplosiveArrow_CoolDown_L5 = 1800;

	public static final float ExplosiveArrow_explCircleRadius_L1 = 10;
	public static final float ExplosiveArrow_explCircleRadius_L2 = 10;
	public static final float ExplosiveArrow_explCircleRadius_L3 = 14;
	public static final float ExplosiveArrow_explCircleRadius_L4 = 14;
	public static final float ExplosiveArrow_explCircleRadius_L5 = 18;

	public static final float ExplosiveArrow_damage_L1 = 2f;
	public static final float ExplosiveArrow_damage_L2 = 2f;
	public static final float ExplosiveArrow_damage_L3 = 3f;
	public static final float ExplosiveArrow_damage_L4 = 3f;
	public static final float ExplosiveArrow_damage_L5 = 4f;

	public static final String ExplosiveArrow_Description_L1 = "Loads bow with an explosive arrow,"
			+ " that deals "
			+ ExplosiveArrow_damage_L1
			+ " damage upon impact in a "
			+ ExplosiveArrow_explCircleRadius_L1
			+ " pixel radius around it L1";
	public static final String ExplosiveArrow_Description_L2 = "Loads bow with an explosive arrow,"
			+ " that deals "
			+ ExplosiveArrow_damage_L2
			+ " damage upon impact in a "
			+ ExplosiveArrow_explCircleRadius_L2
			+ " pixel radius around it L2";
	public static final String ExplosiveArrow_Description_L3 = "Loads bow with an explosive arrow,"
			+ " that deals "
			+ ExplosiveArrow_damage_L3
			+ " damage upon impact in a "
			+ ExplosiveArrow_explCircleRadius_L3
			+ " pixel radius around it L3";
	public static final String ExplosiveArrow_Description_L4 = "Loads bow with an explosive arrow,"
			+ " that deals "
			+ ExplosiveArrow_damage_L4
			+ " damage upon impact in a "
			+ ExplosiveArrow_explCircleRadius_L4
			+ " pixel radius around it L4";
	public static final String ExplosiveArrow_Description_L5 = "Loads bow with an explosive arrow,"
			+ " that deals "
			+ ExplosiveArrow_damage_L5
			+ " damage upon impact in a "
			+ ExplosiveArrow_explCircleRadius_L5
			+ " pixel radius around it L5";

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

	// NUKE:
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

	// FADE:
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

	// TURRET:
	public static final String TURRET_Name = "Gun Turret";
	public static final int TURRET_unlockScore = 1750;

	public static final int TURRET_CoolDown_L1 = 2100;
	public static final int TURRET_CoolDown_L2 = 1800;
	public static final int TURRET_CoolDown_L3 = 1800;
	public static final int TURRET_CoolDown_L4 = 1200;
	public static final int TURRET_CoolDown_L5 = 900;

	public static final int TURRET_LifeTime_L1 = 900;
	public static final int TURRET_LifeTime_L2 = 900;
	public static final int TURRET_LifeTime_L3 = 1200;
	public static final int TURRET_LifeTime_L4 = 1200;
	public static final int TURRET_LifeTime_L5 = 2100;

	public static final int TURRET_Health_L1 = 3;
	public static final int TURRET_Health_L2 = 3;
	public static final int TURRET_Health_L3 = 4;
	public static final int TURRET_Health_L4 = 4;
	public static final int TURRET_Health_L5 = 5;

	public static final float TURRET_min_Damage_L1 = 0.5f;
	public static final float TURRET_min_Damage_L2 = 0.5f;
	public static final float TURRET_min_Damage_L3 = 1f;
	public static final float TURRET_min_Damage_L4 = 1f;
	public static final float TURRET_min_Damage_L5 = 2f;
	
	public static final float TURRET_max_Damage_L1 = 1f;
	public static final float TURRET_max_Damage_L2 = 1f;
	public static final float TURRET_max_Damage_L3 = 2f;
	public static final float TURRET_max_Damage_L4 = 2f;
	public static final float TURRET_max_Damage_L5 = 3f;

	public static final int TURRET_AttackSpeed_L1 = 100;
	public static final int TURRET_AttackSpeed_L2 = 80;
	public static final int TURRET_AttackSpeed_L3 = 80;
	public static final int TURRET_AttackSpeed_L4 = 60;
	public static final int TURRET_AttackSpeed_L5 = 45;

	public static final String TURRET_Description_L1 = "Spawns a turret that shoots nearby enemies,"
			+ " causing "
			+ TURRET_min_Damage_L1 + " - " + TURRET_max_Damage_L1
			+ " dmg per hit, lives for "
			+ TURRET_LifeTime_L1/60 + " " + "seconds. Level 1.";
	public static final String TURRET_Description_L2 = "Spawns a turret that shoots nearby enemies,"
			+ " causing "
			+ TURRET_min_Damage_L2 + " - " + TURRET_max_Damage_L2
			+ " dmg per hit, lives for "
			+ TURRET_LifeTime_L2/60 + " " + "seconds. Level 2.";
	public static final String TURRET_Description_L3 = "Spawns a turret that shoots nearby enemies,"
			+ " causing "
			+ TURRET_min_Damage_L3 + " - " + TURRET_max_Damage_L3
			+ " dmg per hit, lives for "
			+ TURRET_LifeTime_L3/60 + " " + "seconds. Level 3.";
	public static final String TURRET_Description_L4 = "Spawns a turret that shoots nearby enemies,"
			+ " causing "
			+ TURRET_min_Damage_L4 + " - " + TURRET_max_Damage_L4
			+ " dmg per hit, lives for "
			+ TURRET_LifeTime_L4/60 + " " + "seconds. Level 4.";
	public static final String TURRET_Description_L5 = "Spawns a turret that shoots nearby enemies,"
			+ " causing "
			+ TURRET_min_Damage_L5 + " - " + TURRET_max_Damage_L5
			+ " dmg per hit, lives for "
			+ TURRET_LifeTime_L5/60 + " " + "seconds. Level 5.";

	// FrostTrap:
	public static final String FrostTrap_Name = "Frost Trap";
	public static final int FrostTrap_unlockScore = 1500;

	public static final int FrostTrap_CoolDown_L1 = 900;
	public static final int FrostTrap_CoolDown_L2 = 900;
	public static final int FrostTrap_CoolDown_L3 = 720;
	public static final int FrostTrap_CoolDown_L4 = 720;
	public static final int FrostTrap_CoolDown_L5 = 600;

	public static final int FrostTrap_LifeTime_L1 = 900;
	public static final int FrostTrap_LifeTime_L2 = 900;
	public static final int FrostTrap_LifeTime_L3 = 900;
	public static final int FrostTrap_LifeTime_L4 = 1200;
	public static final int FrostTrap_LifeTime_L5 = 1500;

	public static final int FrostTrap_CircleRadius_L1 = 12;
	public static final int FrostTrap_CircleRadius_L2 = 16;
	public static final int FrostTrap_CircleRadius_L3 = 16;
	public static final int FrostTrap_CircleRadius_L4 = 20;
	public static final int FrostTrap_CircleRadius_L5 = 24;
	
	public static final int FrostTrap_FrostLifeTime_L1 = 900;
	public static final int FrostTrap_FrostLifeTime_L2 = 9000;
	public static final int FrostTrap_FrostLifeTime_L3 = 1200;
	public static final int FrostTrap_FrostLifeTime_L4 = 1200;
	public static final int FrostTrap_FrostLifeTime_L5 = 1500;

	public static final String FrostTrap_Description_L1 = "Spawns a frost trap that explodes if stepped on, causing eveyone in the "
			+ FrostTrap_CircleRadius_L1
			+ " radius to slow down at half their speed, for "
			+ FrostTrap_LifeTime_L1 / 60 + " seconds L1";
	public static final String FrostTrap_Description_L2 = "Spawns a frost trap that explodes if stepped on, causing eveyone in the "
			+ FrostTrap_CircleRadius_L2
			+ " radius to slow down at half their speed, for "
			+ FrostTrap_LifeTime_L2 / 60 + " seconds L2";
	public static final String FrostTrap_Description_L3 = "Spawns a frost trap that explodes if stepped on, causing eveyone in the "
			+ FrostTrap_CircleRadius_L3
			+ " radius to slow down at half their speed, for "
			+ FrostTrap_LifeTime_L3 / 60 + " seconds L3";
	public static final String FrostTrap_Description_L4 = "Spawns a frost trap that explodes if stepped on, causing eveyone in the "
			+ FrostTrap_CircleRadius_L4
			+ " radius to slow down at half their speed, for "
			+ FrostTrap_LifeTime_L4 / 60 + " seconds L4";
	public static final String FrostTrap_Description_L5 = "Spawns a frost trap that explodes if stepped on, causing eveyone in the "
			+ FrostTrap_CircleRadius_L5
			+ " radius to slow down at half their speed, for "
			+ FrostTrap_LifeTime_L5 / 60 + " seconds L5";

	// ImproveArrowDamage:
	public static final String ImproveArrowDamage_Name = "Improve Arrow Damage";
	public static final int ImproveArrowDamage_unlockScore = 1200;

	public static final float ImproveArrowDamage_value_L1 = 0.5f;
	public static final float ImproveArrowDamage_value_L2 = 0.5f;
	public static final float ImproveArrowDamage_value_L3 = 0.5f;
	public static final float ImproveArrowDamage_value_L4 = 1f;
	public static final float ImproveArrowDamage_value_L5 = 1f;

	public static final String ImproveArrowDamage_Description_l1 = "Increases Arrow Damage by "
			+ ImproveArrowDamage_value_L1 + " L1";
	public static final String ImproveArrowDamage_Description_l2 = "Increases Arrow Damage by "
			+ ImproveArrowDamage_value_L2 + " L2";
	public static final String ImproveArrowDamage_Description_l3 = "Increases Arrow Damage by "
			+ ImproveArrowDamage_value_L3 + " L3";;
	public static final String ImproveArrowDamage_Description_l4 = "Increases Arrow Damage by "
			+ ImproveArrowDamage_value_L4 + " L4";
	public static final String ImproveArrowDamage_Description_l5 = "Increases Arrow Damage by "
			+ ImproveArrowDamage_value_L5 + " L5";

	// ImproveArrowSpeed:
	public static final String ImproveArrowSpeed_Name = "Improve Arrow Speed";
	public static final int ImproveArrowSpeed_unlockScore = 0;

	public static final float ImproveArrowSpeed_value_L1 = 1;
	public static final float ImproveArrowSpeed_value_L2 = 1;
	public static final float ImproveArrowSpeed_value_L3 = 1;
	public static final float ImproveArrowSpeed_value_L4 = 1;
	public static final float ImproveArrowSpeed_value_L5 = 1;

	public static final String ImproveArrowSpeed_Description_l1 = "Increase Arrow Speed by "
			+ ImproveArrowSpeed_value_L1 + " L1";
	public static final String ImproveArrowSpeed_Description_l2 = "Increase Arrow Speed by "
			+ ImproveArrowSpeed_value_L2 + " L2";
	public static final String ImproveArrowSpeed_Description_l3 = "Increase Arrow Speed by "
			+ ImproveArrowSpeed_value_L3 + " L3";
	public static final String ImproveArrowSpeed_Description_l4 = "Increase Arrow Speed by "
			+ ImproveArrowSpeed_value_L4 + " L4";
	public static final String ImproveArrowSpeed_Description_l5 = "Increase Arrow Speed by "
			+ ImproveArrowSpeed_value_L5 + " L5";

	// ImproveMaxHealth:
	public static final String ImproveMaxHealth_Name = "Improve Health";
	public static final int ImproveMaxHealth_unlockScore = 750;

	public static final int ImproveMaxHealth_HealthValue_L1 = 1;
	public static final int ImproveMaxHealth_HealthValue_L2 = 1;
	public static final int ImproveMaxHealth_HealthValue_L3 = 1;
	public static final int ImproveMaxHealth_HealthValue_L4 = 1;
	public static final int ImproveMaxHealth_HealthValue_L5 = 2;

	public static final String ImproveMaxHealth_Description_l1 = "Increase max health value by "
			+ ImproveMaxHealth_HealthValue_L1 + " L1";
	public static final String ImproveMaxHealth_Description_l2 = "Increase max health value by "
			+ ImproveMaxHealth_HealthValue_L2 + " L2";
	public static final String ImproveMaxHealth_Description_l3 = "Increase max health value by "
			+ ImproveMaxHealth_HealthValue_L3 + " L3";
	public static final String ImproveMaxHealth_Description_l4 = "Increase max health value by "
			+ ImproveMaxHealth_HealthValue_L4 + " L4";
	public static final String ImproveMaxHealth_Description_l5 = "Increase max health value by "
			+ ImproveMaxHealth_HealthValue_L5 + " L5";

	// ImproveMaxOxygen:
	public static final String ImproveMaxOxygen_Name = "Improve Oxygen";
	public static final int ImproveMaxOxygen_unlockScore = 0;

	public static final float ImproveMaxOxygen_OxygenValue_L1 = 16f;
	public static final float ImproveMaxOxygen_OxygenValue_L2 = 16f;
	public static final float ImproveMaxOxygen_OxygenValue_L3 = 16f;
	public static final float ImproveMaxOxygen_OxygenValue_L4 = 32f;
	public static final float ImproveMaxOxygen_OxygenValue_L5 = 32f;

	public static final String ImproveMaxOxygen_Description_l1 = "Increase max oxygen value by "
			+ ImproveMaxOxygen_OxygenValue_L1 + " L1";
	public static final String ImproveMaxOxygen_Description_l2 = "Increase max oxygen value by "
			+ ImproveMaxOxygen_OxygenValue_L2 + " L2";
	public static final String ImproveMaxOxygen_Description_l3 = "Increase max oxygen value by "
			+ ImproveMaxOxygen_OxygenValue_L3 + " L3";
	public static final String ImproveMaxOxygen_Description_l4 = "Increase max oxygen value by "
			+ ImproveMaxOxygen_OxygenValue_L4 + " L4";
	public static final String ImproveMaxOxygen_Description_l5 = "Increase max oxygen value by "
			+ ImproveMaxOxygen_OxygenValue_L5 + " L5";

	// ImproveMaxSpeed:
	public static final String ImproveMaxSpeed_Name = "Improve Speed";
	public static final int ImproveMaxSpeed_unlockScore = 0;

	public static final float ImproveMaxSpeed_SpeedValue_L1 = 0.2f;
	public static final float ImproveMaxSpeed_SpeedValue_L2 = 0.2f;
	public static final float ImproveMaxSpeed_SpeedValue_L3 = 0.3f;
	public static final float ImproveMaxSpeed_SpeedValue_L4 = 0.3f;
	public static final float ImproveMaxSpeed_SpeedValue_L5 = 0.4f;

	public static final String ImproveMaxSpeed_Description_l1 = "Increase max Speed by "
			+ ImproveMaxSpeed_SpeedValue_L1 + " L1";
	public static final String ImproveMaxSpeed_Description_l2 = "Increase max Speed by "
			+ ImproveMaxSpeed_SpeedValue_L2 + " L2";
	public static final String ImproveMaxSpeed_Description_l3 = "Increase max Speed by "
			+ ImproveMaxSpeed_SpeedValue_L3 + " L3";
	public static final String ImproveMaxSpeed_Description_l4 = "Increase max Speed by "
			+ ImproveMaxSpeed_SpeedValue_L4 + " L4";
	public static final String ImproveMaxSpeed_Description_l5 = "Increase max Speed by "
			+ ImproveMaxSpeed_SpeedValue_L5 + " L5";

	// PoisonArrow:
	public static final String PoisonArrow_Name = "Position Arrow";
	public static final int PoisonArrow_unlockScore = 1350;

	public static final int PoisonArrow_CoolDown_L1 = 2100;
	public static final int PoisonArrow_CoolDown_L2 = 1800;
	public static final int PoisonArrow_CoolDown_L3 = 1800;
	public static final int PoisonArrow_CoolDown_L4 = 1200;
	public static final int PoisonArrow_CoolDown_L5 = 900;

	public static final float PoisonArrow_poisonDamage_L1 = 0.4f;
	public static final float PoisonArrow_poisonDamage_L2 = 0.4f;
	public static final float PoisonArrow_poisonDamage_L3 = 0.4f;
	public static final float PoisonArrow_poisonDamage_L4 = 0.6f;
	public static final float PoisonArrow_poisonDamage_L5 = 1;

	public static final int PoisonArrow_poisonInterval_L1 = 60;
	public static final int PoisonArrow_poisonInterval_L2 = 60;
	public static final int PoisonArrow_poisonInterval_L3 = 60;
	public static final int PoisonArrow_poisonInterval_L4 = 60;
	public static final int PoisonArrow_poisonInterval_L5 = 60;

	public static final int PoisonArrow_poisonLifeTime_L1 = 300;
	public static final int PoisonArrow_poisonLifeTime_L2 = 300;
	public static final int PoisonArrow_poisonLifeTime_L3 = 600;
	public static final int PoisonArrow_poisonLifeTime_L4 = 600;
	public static final int PoisonArrow_poisonLifeTime_L5 = 600;

	public static final String PoisonArrow_Description_L1 = "Loads bow with a poisoned arrow,"
			+ " deals no damage upon impact, but poisons the victim causing 0.5 dmg every second,"
			+ " for 5 seconds Level 1!";
	public static final String PoisonArrow_Description_L2 = "Loads bow with a poisoned arrow,"
			+ " deals no damage upon impact, but poisons the victim causing 0.5 dmg every second,"
			+ " for 5 seconds Level 2!!";
	public static final String PoisonArrow_Description_L3 = "Loads bow with a poisoned arrow,"
			+ " deals no damage upon impact, but poisons the victim causing 0.5 dmg every second,"
			+ " for 5 seconds Level 3!";
	public static final String PoisonArrow_Description_L4 = "Loads bow with a poisoned arrow,"
			+ " deals no damage upon impact, but poisons the victim causing 0.5 dmg every second,"
			+ " for 5 seconds Level 4!";
	public static final String PoisonArrow_Description_L5 = "Loads bow with a poisoned arrow,"
			+ " deals no damage upon impact, but poisons the victim causing 0.5 dmg every second,"
			+ " for 5 seconds Level 5!";

	// PoisonTrap:
	public static final String PoisonTrap_Name = "Position Trap";
	public static final int PoisonTrap_unlockScore = 4200;

	public static final int PoisonTrap_CoolDown_L1 = 900;
	public static final int PoisonTrap_CoolDown_L2 = 900;
	public static final int PoisonTrap_CoolDown_L3 = 720;
	public static final int PoisonTrap_CoolDown_L4 = 720;
	public static final int PoisonTrap_CoolDown_L5 = 600;

	public static final int PoisonTrap_Radius_L1 = 12;
	public static final int PoisonTrap_Radius_L2 = 12;
	public static final int PoisonTrap_Radius_L3 = 16;
	public static final int PoisonTrap_Radius_L4 = 19;
	public static final int PoisonTrap_Radius_L5 = 20;

	public static final int PoisonTrap_LifeTime_L1 = 900;
	public static final int PoisonTrap_LifeTime_L2 = 1200;
	public static final int PoisonTrap_LifeTime_L3 = 1200;
	public static final int PoisonTrap_LifeTime_L4 = 1500;
	public static final int PoisonTrap_LifeTime_L5 = 2100;

	public static final float PoisonTrap_poisonDamage_L1 = 0.2f;
	public static final float PoisonTrap_poisonDamage_L2 = 0.4f;
	public static final float PoisonTrap_poisonDamage_L3 = 0.4f;
	public static final float PoisonTrap_poisonDamage_L4 = 0.5f;
	public static final float PoisonTrap_poisonDamage_L5 = 0.5f;

	public static final int PoisonTrap_poisonInterval_L1 = 60;
	public static final int PoisonTrap_poisonInterval_L2 = 60;
	public static final int PoisonTrap_poisonInterval_L3 = 60;
	public static final int PoisonTrap_poisonInterval_L4 = 60;
	public static final int PoisonTrap_poisonInterval_L5 = 60;

	public static final int PoisonTrap_poisonLifeTime_L1 = 300;
	public static final int PoisonTrap_poisonLifeTime_L2 = 300;
	public static final int PoisonTrap_poisonLifeTime_L3 = 600;
	public static final int PoisonTrap_poisonLifeTime_L4 = 600;
	public static final int PoisonTrap_poisonLifeTime_L5 = 600;

	// POISON DAMAGE ??? And EFFECT TIME

	public static final String PoisonTrap_Description_L1 = "Spawns a trap that deals no damage"
			+ " upon collide, but poisons the victim causing 0.5 dmg per second util he dies "
			+ "Level 1!";
	public static final String PoisonTrap_Description_L2 = "Spawns a trap that deals no damage"
			+ " upon collide, but poisons the victim causing 0.5 dmg per second util he dies "
			+ "Level 2!";
	public static final String PoisonTrap_Description_L3 = "Spawns a trap that deals no damage"
			+ " upon collide, but poisons the victim causing 0.5 dmg per second util he dies "
			+ "Level 3!";
	public static final String PoisonTrap_Description_L4 = "Spawns a trap that deals no damage"
			+ " upon collide, but poisons the victim causing 0.5 dmg per second util he dies "
			+ "Level 4!";
	public static final String PoisonTrap_Description_L5 = "Spawns a trap that deals no damage"
			+ " upon collide, but poisons the victim causing 0.5 dmg per second util he dies "
			+ "Level 5!";

	// RADIOACTIVE:
	public static final int RADIOACTIVE_CoolDown_L1 = 1800;
	public static final int RADIOACTIVE_CoolDown_L2 = 1500;
	public static final int RADIOACTIVE_CoolDown_L3 = 1200;
	public static final int RADIOACTIVE_CoolDown_L4 = 900;
	public static final int RADIOACTIVE_CoolDown_L5 = 900;

	public static final int RADIOACTIVE_Damage_L1 = 1;
	public static final int RADIOACTIVE_Damage_L2 = 1;
	public static final int RADIOACTIVE_Damage_L3 = 2;
	public static final int RADIOACTIVE_Damage_L4 = 2;
	public static final int RADIOACTIVE_Damage_L5 = 2;

	public static final int RADIOACTIVE_LifeTime_L1 = 600;
	public static final int RADIOACTIVE_LifeTime_L2 = 600;
	public static final int RADIOACTIVE_LifeTime_L3 = 900;
	public static final int RADIOACTIVE_LifeTime_L4 = 900;
	public static final int RADIOACTIVE_LifeTime_L5 = 1200;

	public static final float RADIOACTIVE_Radius_L1 = 23;
	public static final float RADIOACTIVE_Radius_L2 = 25;
	public static final float RADIOACTIVE_Radius_L3 = 25;
	public static final float RADIOACTIVE_Radius_L4 = 26;
	public static final float RADIOACTIVE_Radius_L5 = 30;

	public static final String RADIOACTIVE_Description_L1 = "Sup, name is RADIOACTIVE Aura Level 1!";
	public static final String RADIOACTIVE_Description_L2 = "Sup, name is RADIOACTIVE Aura Level 2!";
	public static final String RADIOACTIVE_Description_L3 = "Sup, name is RADIOACTIVE Aura Level 3!";
	public static final String RADIOACTIVE_Description_L4 = "Sup, name is RADIOACTIVE Aura Level 4!";
	public static final String RADIOACTIVE_Description_L5 = "Sup, name is RADIOACTIVE Aura Level 5!";

	// PLASMA_SHIELD:
	public static final String PLASMA_SHIELD_Name = "Plasma Shield";
	public static final int PLASMA_SHIELD_unlockScore = 200;

	public static final int PLASMA_SHIELD_CoolDown_L1 = 1800;
	public static final int PLASMA_SHIELD_CoolDown_L2 = 1500;
	public static final int PLASMA_SHIELD_CoolDown_L3 = 1500;
	public static final int PLASMA_SHIELD_CoolDown_L4 = 1200;
	public static final int PLASMA_SHIELD_CoolDown_L5 = 900;

	public static final int PLASMA_SHIELD_LifeTime_L1 = 600;
	public static final int PLASMA_SHIELD_LifeTime_L2 = 600;
	public static final int PLASMA_SHIELD_LifeTime_L3 = 900;
	public static final int PLASMA_SHIELD_LifeTime_L4 = 900;
	public static final int PLASMA_SHIELD_LifeTime_L5 = 1200;

	public static final String PLASMA_SHIELD_Description_L1 = "Creates a plasma shield around the"
			+ " player that stops enemy projectiles from fying in, stays for "
			+ PLASMA_SHIELD_LifeTime_L1/60 + " seconds Level 1";
	public static final String PLASMA_SHIELD_Description_L2 = "Creates a plasma shield around the"
			+ " player that stops enemy projectiles from fying in, stays for "
			+ PLASMA_SHIELD_LifeTime_L2/60 + " seconds Level 2";
	public static final String PLASMA_SHIELD_Description_L3 = "Creates a plasma shield around the"
			+ " player that stops enemy projectiles from fying in, stays for "
			+ PLASMA_SHIELD_LifeTime_L3/60 + " seconds Level 3";
	public static final String PLASMA_SHIELD_Description_L4 = "Creates a plasma shield around the"
			+ " player that stops enemy projectiles from fying in, stays for "
			+ PLASMA_SHIELD_LifeTime_L4/60 + " seconds Level 4";
	public static final String PLASMA_SHIELD_Description_L5 = "Creates a plasma shield around the"
			+ " player that stops enemy projectiles from fying in, stays for "
			+ PLASMA_SHIELD_LifeTime_L5/60 + " seconds Level 5";

	// PANIC_TELEPORT:
	public static final String PANIC_TELEPORT_Name = "Panic Teleport";
	public static final int PANIC_TELEPORT_unlockScore = 500;

	public static final int PANIC_TELEPORT_CoolDown_L1 = 1800;
	public static final int PANIC_TELEPORT_CoolDown_L2 = 1500;
	public static final int PANIC_TELEPORT_CoolDown_L3 = 1200;
	public static final int PANIC_TELEPORT_CoolDown_L4 = 900;
	public static final int PANIC_TELEPORT_CoolDown_L5 = 900;

	// Damage explosion ???

	public static final String PANIC_TELEPORT_Description_L1 = "Teleports the player to a random position on the map Level 1";
	public static final String PANIC_TELEPORT_Description_L2 = "Teleports the player to a random position on the map Level 2";
	public static final String PANIC_TELEPORT_Description_L3 = "Teleports the player to a random position on the map Level 3";
	public static final String PANIC_TELEPORT_Description_L4 = "Teleports the player to a random position on the map Level 4";
	public static final String PANIC_TELEPORT_Description_L5 = "Teleports the player to a random position on the map Level 5";

	// SPEED_BOOST:
	public static final String SPEED_BOOST_Name = "Speed Boost";
	public static final int SPEED_BOOST_unlockScore = 0;

	public static final int SPEED_BOOST_CoolDown_L1 = 1800;
	public static final int SPEED_BOOST_CoolDown_L2 = 1500;
	public static final int SPEED_BOOST_CoolDown_L3 = 1200;
	public static final int SPEED_BOOST_CoolDown_L4 = 1200;
	public static final int SPEED_BOOST_CoolDown_L5 = 900;

	public static final int SPEED_BOOST_LifeTime_L1 = 600;
	public static final int SPEED_BOOST_LifeTime_L2 = 600;
	public static final int SPEED_BOOST_LifeTime_L3 = 900;
	public static final int SPEED_BOOST_LifeTime_L4 = 1200;
	public static final int SPEED_BOOST_LifeTime_L5 = 1500;

	public static final String SPEED_BOOST_Description_L1 = "Doubles the player speed for "
			+ SPEED_BOOST_LifeTime_L1/60 + " seconds, LVL 1";
	public static final String SPEED_BOOST_Description_L2 = "Doubles the player speed for "
			+ SPEED_BOOST_LifeTime_L2/60 + " seconds, LVL 2";
	public static final String SPEED_BOOST_Description_L3 = "Doubles the player speed for "
			+ SPEED_BOOST_LifeTime_L3/60 + " seconds, LVL 3";
	public static final String SPEED_BOOST_Description_L4 = "Doubles the player speed for "
			+ SPEED_BOOST_LifeTime_L4/60 + " seconds, LVL 4";
	public static final String SPEED_BOOST_Description_L5 = "Doubles the player speed for "
			+ SPEED_BOOST_LifeTime_L5/60 + " seconds, LVL 5";

	// ----------------- ITEMS ---------------------
	// HASTE
	public static final int HASTE_LifeTime_L1 = 600;
	public static final int HASTE_LifeTime_L2 = 900;
	public static final int HASTE_LifeTime_L3 = 900;
	public static final int HASTE_LifeTime_L4 = 1200;
	public static final int HASTE_LifeTime_L5 = 1600;

	// WEAKENED
	public static final int WEAKENED_LifeTime_L1 = 300;
	public static final int WEAKENED_LifeTime_L2 = 300;
	public static final int WEAKENED_LifeTime_L3 = 600;
	public static final int WEAKENED_LifeTime_L4 = 1200;
	public static final int WEAKENED_LifeTime_L5 = 1200;

	// feedback
	public static final String SHOT_ARROWS = "Shot arrows: ";
	public static final String ENEMIES_KILLED = "Enemies killed: ";
	public static final String SCORE = "Score: ";
	public static final String ONE_MORE_WORLD_CONQUERED = "One More World conquered!";
	public static final String GET_REWARD = "Get reward!";
	public static final String TRY_AGAIN = "Next time I'll be better!";

	// Exitmessage
	public static final String EXIT_MESSAGE = "Exit to main menu";

	// UI stuff
	public static final String EXIT = "Exit";
	public static final String PLAY = "Play";
	public static final String TUTORIAL = "Tutorial";
	public static final String WORLDS_CONQUERROR = "World's Conqueror";

}
