package com.me.swampmonster.utils;

import com.badlogic.gdx.Gdx;

public class Constants {
	public static final float VIEWPORT_WIDTH = Gdx.graphics.getWidth();
	public static final float VIEWPORT_HEIGHT = Gdx.graphics.getHeight();
	public static final float VIEWPORT_GUI_WIDTH = Gdx.graphics.getWidth();
	public static final float VIEWPORT_GUI_HEIGHT = Gdx.graphics.getHeight();
	
	public static final String sufficateMessage1 = "You forgot your oxygen mask, you big dummy!";
	public static final String sufficateMessage2 = "I gues the absence of oxygen has left you... breathless.";
	public static final String sufficateMessage3 = "Don't forget to breath next time!";
	public static final String enemyMessage1 = "I think that guy likes you";
	public static final String enemyMessage2 = "You went up agaist a zombie, and lost";
	public static final String enemyMessage3 = "Bet you thought that guy was friendly";
	public static final String poisionedMessage1 = "You shouldn't eat Pizza with it's box!"; 
	public static final String poisionedMessage2 = "That went right into your ear!";
	public static final String poisionedMessage3 = "Tyrion did it!";
	
	public static final int pendingPeriodBetweedWaves = 2800;
	public static final int NodeSize = 32;
	//
	
	//:TODO THE AWESOME SLOT DATA!
//  Arrows3:
	public static final int Arrows3_CoolDown_L1 = 1000;
	public static final int Arrows3_CoolDown_L2 = 900;
	public static final int Arrows3_CoolDown_L3 = 800;
	public static final int Arrows3_CoolDown_L4 = 700;
	public static final int Arrows3_CoolDown_L5 = 600;
	
	public static final String Arrows3_Description_L1 = "I shoot 3 arrows at thee l1 ";
	public static final String Arrows3_Description_L2 = "I shoot 3 arrows at thee l2 ";
	public static final String Arrows3_Description_L3 = "I shoot 3 arrows at thee l3 ";
	public static final String Arrows3_Description_L4 = "I shoot 3 arrows at thee l4 ";
	public static final String Arrows3_Description_L5 = "I shoot 3 arrows at thee l4 "; 
	
	
//  DamageTrap:
	public static final int DamageTrap_CoolDown_L1 = 5000;
	public static final int DamageTrap_CoolDown_L2 = 4000;
	public static final int DamageTrap_CoolDown_L3 = 3000;
	public static final int DamageTrap_CoolDown_L4 = 2000;
	public static final int DamageTrap_CoolDown_L5 = 1000;
	
	public static final float DamageTrap_Damage_L1 = 0f;
	public static final float DamageTrap_Damage_L2 = 1f;
	public static final float DamageTrap_Damage_L3 = 2f;
	public static final float DamageTrap_Damage_L4 = 3f;
	public static final float DamageTrap_Damage_L5 = 4f;
	
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
	
	
//	ExplozionTrap:
	public static final int ExplozionTrap_CoolDown_L1 = 1000;
	public static final int ExplozionTrap_CoolDown_L2 = 800;
	public static final int ExplozionTrap_CoolDown_L3 = 600;
	public static final int ExplozionTrap_CoolDown_L4 = 400;
	public static final int ExplozionTrap_CoolDown_L5 = 200;
	
	public static final float ExplozionTrap_CircleRadius_L1 = 16;
	public static final float ExplozionTrap_CircleRadius_L2 = 20;
	public static final float ExplozionTrap_CircleRadius_L3 = 25;
	public static final float ExplozionTrap_CircleRadius_L4 = 30;
	public static final float ExplozionTrap_CircleRadius_L5 = 35;
	
	public static final int ExplozionTrap_LifeTime_L1 = 300;
	public static final int ExplozionTrap_LifeTime_L2 = 600;
	public static final int ExplozionTrap_LifeTime_L3 = 900;
	public static final int ExplozionTrap_LifeTime_L4 = 1200;
	public static final int ExplozionTrap_LifeTime_L5 = 1600;
	
	public static final String ExplozionTrap_Description_L1 = "ExplosionTrap Level 1";
	public static final String ExplozionTrap_Description_L2 = "ExplosionTrap Level 2";
	public static final String ExplozionTrap_Description_L3 = "ExplosionTrap Level 3";
	public static final String ExplozionTrap_Description_L4 = "ExplosionTrap Level 4";
	public static final String ExplozionTrap_Description_L5 = "ExplosionTrap Level 5";
	
	
//	FADE:
	public static final int FADE_CoolDown_L1 = 1200;
	public static final int FADE_CoolDown_L2 = 1000;
	public static final int FADE_CoolDown_L3 = 800;
	public static final int FADE_CoolDown_L4 = 600;
	public static final int FADE_CoolDown_L5 = 400;

	public static final int FADE_LifeTime_L1 = 100;
	public static final int FADE_LifeTime_L2 = 200;
	public static final int FADE_LifeTime_L3 = 300;
	public static final int FADE_LifeTime_L4 = 400;
	public static final int FADE_LifeTime_L5 = 500;

	public static final String FADE_Description_L1 = "FADE of da Level 1";
	public static final String FADE_Description_L2 = "FADE of da Level 2";
	public static final String FADE_Description_L3 = "FADE of da Level 3";
	public static final String FADE_Description_L4 = "FADE of da Level 4";
	public static final String FADE_Description_L5 = "FADE of da Level 5";
	
	
//	FrostTrap:
	public static final int FrostTrap_CoolDown_L1 = 2000;
	public static final int FrostTrap_CoolDown_L2 = 1700;
	public static final int FrostTrap_CoolDown_L3 = 1400;
	public static final int FrostTrap_CoolDown_L4 = 1100;
	public static final int FrostTrap_CoolDown_L5 = 800;

	public static final int FrostTrap_LifeTime_L1 = 300;
	public static final int FrostTrap_LifeTime_L2 = 600;
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
	public static final float ImproveArrowDamage_value_L1 = 1;
	public static final float ImproveArrowDamage_value_L2 = 2;
	public static final float ImproveArrowDamage_value_L3 = 3;
	public static final float ImproveArrowDamage_value_L4 = 4;
	public static final float ImproveArrowDamage_value_L5 = 5;
	
	public static final String ImproveArrowDamage_Description_l1 = "Increase Arrow Damage by " + ImproveArrowDamage_value_L1;
	public static final String ImproveArrowDamage_Description_l2 = "Increase Arrow Damage by " + ImproveArrowDamage_value_L2;
	public static final String ImproveArrowDamage_Description_l3 = "Increase Arrow Damage by " + ImproveArrowDamage_value_L3;
	public static final String ImproveArrowDamage_Description_l4 = "Increase Arrow Damage by " + ImproveArrowDamage_value_L4;
	public static final String ImproveArrowDamage_Description_l5 = "Increase Arrow Damage by " + ImproveArrowDamage_value_L5;

//	ImproveArrowSpeed:
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
	public static final float ImproveMaxSpeed_SpeedValue_L1 = 1;
	public static final float ImproveMaxSpeed_SpeedValue_L2 = 2f;
	public static final float ImproveMaxSpeed_SpeedValue_L3 = 3f;
	public static final float ImproveMaxSpeed_SpeedValue_L4 = 4f;
	public static final float ImproveMaxSpeed_SpeedValue_L5 = 5f;
	
	public static final String ImproveMaxSpeed_Description_l1 = "Increase max Speed by " + ImproveMaxSpeed_SpeedValue_L1;
	public static final String ImproveMaxSpeed_Description_l2 = "Increase max Speed by " + ImproveMaxSpeed_SpeedValue_L2;
	public static final String ImproveMaxSpeed_Description_l3 = "Increase max Speed by " + ImproveMaxSpeed_SpeedValue_L3;
	public static final String ImproveMaxSpeed_Description_l4 = "Increase max Speed by " + ImproveMaxSpeed_SpeedValue_L4;
	public static final String ImproveMaxSpeed_Description_l5 = "Increase max Speed by " + ImproveMaxSpeed_SpeedValue_L5;

	
//	PoisonArrow:
	public static final int PoisonArrow_CoolDown_L1 = 3000;
	public static final int PoisonArrow_CoolDown_L2 = 2600;
	public static final int PoisonArrow_CoolDown_L3 = 2000;
	public static final int PoisonArrow_CoolDown_L4 = 1500;
	public static final int PoisonArrow_CoolDown_L5 = 1000;
	
	public static final String PoisonArrow_Description_L1 = "I am poisonedArrow of Level 1!";
	public static final String PoisonArrow_Description_L2 = "I am poisonedArrow of Level 2!";
	public static final String PoisonArrow_Description_L3 = "I am poisonedArrow of Level 3!";
	public static final String PoisonArrow_Description_L4 = "I am poisonedArrow of Level 4!";
	public static final String PoisonArrow_Description_L5 = "I am poisonedArrow of Level 5!";
	
	
//	PoisonTrap:
	public static final int PoisonTrap_CoolDown_L1 = 3000;
	public static final int PoisonTrap_CoolDown_L2 = 2600;
	public static final int PoisonTrap_CoolDown_L3 = 2000;
	public static final int PoisonTrap_CoolDown_L4 = 1500;
	public static final int PoisonTrap_CoolDown_L5 = 1000;
	
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
	public static final int RADIOACTIVE_CoolDown_L1 = 3000;
	public static final int RADIOACTIVE_CoolDown_L2 = 2500;
	public static final int RADIOACTIVE_CoolDown_L3 = 2000;
	public static final int RADIOACTIVE_CoolDown_L4 = 1500;
	public static final int RADIOACTIVE_CoolDown_L5 = 1000;
	
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
	
	
//	ShadowArrow:
	public static final int ShadowArrow_CoolDown_L1 = 3000;
	public static final int ShadowArrow_CoolDown_L2 = 2500;
	public static final int ShadowArrow_CoolDown_L3 = 2000;
	public static final int ShadowArrow_CoolDown_L4 = 1500;
	public static final int ShadowArrow_CoolDown_L5 = 1000;

	public static final String ShadowArrow_Description_L1 = "ShadowArrow SA Level 1";
	public static final String ShadowArrow_Description_L2 = "ShadowArrow SA Level 2";
	public static final String ShadowArrow_Description_L3 = "ShadowArrow SA Level 3";
	public static final String ShadowArrow_Description_L4 = "ShadowArrow SA Level 4";
	public static final String ShadowArrow_Description_L5 = "ShadowArrow SA Level 5";

	
//	SPEED_BOOST:
	public static final int SPEED_BOOST_CoolDown_L1 = 3000;
	public static final int SPEED_BOOST_CoolDown_L2 = 2500;
	public static final int SPEED_BOOST_CoolDown_L3 = 2000;
	public static final int SPEED_BOOST_CoolDown_L4 = 1500;
	public static final int SPEED_BOOST_CoolDown_L5 = 1000;

	public static final String SPEED_BOOST_Description_L1 = "Speed Boost LEVEL: 1";
	public static final String SPEED_BOOST_Description_L2 = "Speed Boost LEVEL: 2";
	public static final String SPEED_BOOST_Description_L3 = "Speed Boost LEVEL: 3";
	public static final String SPEED_BOOST_Description_L4 = "Speed Boost LEVEL: 4";
	public static final String SPEED_BOOST_Description_L5 = "Speed Boost LEVEL: 5";

	public static final int SPEED_BOOST_LifeTime_L1 = 300;
	public static final int SPEED_BOOST_LifeTime_L2 = 500;
	public static final int SPEED_BOOST_LifeTime_L3 = 800;
	public static final int SPEED_BOOST_LifeTime_L4 = 1200;
	public static final int SPEED_BOOST_LifeTime_L5 = 1600;

}
