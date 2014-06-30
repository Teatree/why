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
	public static final int Arrows3_CoolDown_L1 = 600;
	public static final int Arrows3_CoolDown_L2 = 700;
	public static final int Arrows3_CoolDown_L3 = 800;
	public static final int Arrows3_CoolDown_L4 = 900;
	public static final int Arrows3_CoolDown_L5 = 1000;
	
	public static final String Arrows3_Description_L1 = "I shoot 3 arrows at thee l1 ";
	public static final String Arrows3_Description_L2 = "I shoot 3 arrows at thee l2 ";
	public static final String Arrows3_Description_L3 = "I shoot 3 arrows at thee l3 ";
	public static final String Arrows3_Description_L4 = "I shoot 3 arrows at thee l4 ";
	public static final String Arrows3_Description_L5 = "I shoot 3 arrows at thee l4 "; 
	
//  DamageTrap:
	public static final int DamageTrap_CoolDown_L1 = 3000;
	public static final int DamageTrap_Damage_L1 = 0;
	public static final int DamageTrap_LifeTimeMax_L1 = 500;
	public static final String DamageTrap_Description_L1 = "I shoot 3 arrows at thee";
	
//	ExplosiveArrow:
	public static final int ExplosiveArrow_CoolDown_L1 = 200;
	public static final String ExplosiveArrow_Description_L1 = "I shoot 3 arrows at thee";
	
//	ExplozionTrap:
	public static final int ExplozionTrap_CircleRadius_L1 = 16;
	public static final int ExplozionTrap_LifeTime_L1 = 112;
	public static final String ExplozionTrap_Description_L1 = "I shoot 3 arrows at thee";
	
//	FADE:
	public static final int FADE_CoolDown_L1 = 800;
	public static final int FADE_LifeTime_L1 = 233;
	public static final String FADE_Description_L1 = "I shoot 3 arrows at thee";
	
//	FrostTrap:
	public static final int FrostTrap_CoolDown_L1 = 1600;
	public static final int FrostTrap_LifeTime_L1 = 600;
	public static final int FrostTrap_CircleRadius_L1 = 16;
	
//	ImproveArrowDamage:
	
//	ImproveArrowSpeed:
	public static final float ImproveArrowSpeed_value_L1 = 1;
	public static final float ImproveArrowSpeed_value_L2 = 2;
	public static final float ImproveArrowSpeed_valueL3 = 3;
	public static final float ImproveArrowSpeed_value_L4 = 4;
	public static final float ImproveArrowSpeed_value_L5 = 5;
	
	public static final String ImproveArrowSpeed_Description_l1 = "Increase Arrow Speed by " + ImproveArrowSpeed_value_L1;
	public static final String ImproveArrowSpeed_Description_l2 = "Increase Arrow Speed by " + ImproveArrowSpeed_value_L2;
	public static final String ImproveArrowSpeed_Description_l3 = "Increase Arrow Speed by "+ ImproveArrowSpeed_valueL3;
	public static final String ImproveArrowSpeed_Description_l4 = "Increase Arrow Speed by "+ ImproveArrowSpeed_value_L4;
	public static final String ImproveArrowSpeed_Description_l5 = "Increase Arrow Speed by "+ ImproveArrowSpeed_value_L5;

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
	public static final int ImproveMaxSpeed_SpeedValue_L1 = 1;
	
//	PoisonArrow:
	public static final int PoisonArrow_CoolDown_L1 = 2200;
	
//	PoisonTrap:
	public static final int PoisonTrap_Radius_L1 = 16;
	public static final int PoisonTrap_LifeTime_L1 = 112;
	
//	RADIOACTIVE:
	public static final int RADIOACTIVE_CoolDown_L1 = 0;
	public static final int RADIOACTIVE_LifeTime_L1 = 0;
	
//	ShadowArrow:
	
//	SPEED_BOOST:
	public static final String SPEED_BOOST_Description_L1 = "Makes player move faster for a certain amount of time";
	public static final int SPEED_BOOST_LifeTime_L1 = 0;
	public static final int SPEED_BOOST_CoolDown_L1 = 0;
	
}
