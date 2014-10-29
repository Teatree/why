package com.me.swampmonster.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
public class Assets {

	public static final AssetManager manager = new AssetManager();
	//characters
	public static final AssetDescriptor<Texture> nastyaSpriteStandard = new AssetDescriptor<Texture>("data\\EnemyTemp.png", Texture.class);
	public static final AssetDescriptor<Texture> nastyaSpriteGun = new AssetDescriptor<Texture>("data\\EnemyTemp.png", Texture.class);
	public static final AssetDescriptor<Texture> bow = new AssetDescriptor<Texture>("data\\BowNArrow.png", Texture.class);
	public static final AssetDescriptor<Texture> enemyLeech = new AssetDescriptor<Texture>("data\\EnemyLeech_new.png", Texture.class);
	public static final AssetDescriptor<Texture> enemySofa = new AssetDescriptor<Texture>("data\\EnemySOFA.png", Texture.class);
	public static final AssetDescriptor<Texture> enemyZombie = new AssetDescriptor<Texture>("data\\EnemyZombie_new.png", Texture.class);
	public static final AssetDescriptor<Texture> aimingAuraSprite = new AssetDescriptor<Texture>("data\\aimingAura.png", Texture.class);
	public static final AssetDescriptor<Texture> enemyMaggot = new AssetDescriptor<Texture>("data\\EnemyMaggot_new.png", Texture.class);
	public static final AssetDescriptor<Texture> enemy = new AssetDescriptor<Texture>("data\\EnemyTemp.png", Texture.class);
	
	//GUI
	public static final AssetDescriptor<Texture> HealthBarIcon = new AssetDescriptor<Texture>("data\\HealthBarIcon.png", Texture.class);
	public static final AssetDescriptor<Texture> HealthBarTail = new AssetDescriptor<Texture>("data\\HealthBarTail.png", Texture.class);
	public static final AssetDescriptor<Texture> HealthBarMiddle = new AssetDescriptor<Texture>("data\\HealthBarMiddle.png", Texture.class);
	public static final AssetDescriptor<Texture> HealthBarHead = new AssetDescriptor<Texture>("data\\HealthBarHead.png", Texture.class);
	public static final AssetDescriptor<Texture> OxygenBarIcon = new AssetDescriptor<Texture>("data\\OxygenBarIcon.png", Texture.class);
	public static final AssetDescriptor<Texture> OxygenBarTail = new AssetDescriptor<Texture>("data\\OxygenBarTail.png", Texture.class);
	public static final AssetDescriptor<Texture> OxygenBarMiddle = new AssetDescriptor<Texture>("data\\OxygenBarMiddle.png", Texture.class);
	public static final AssetDescriptor<Texture> OxygenBarHead = new AssetDescriptor<Texture>("data\\OxygenBarHead.png", Texture.class);
	public static final AssetDescriptor<Texture> PointerHead = new AssetDescriptor<Texture>("data\\PointerHead.png", Texture.class);
	public static final AssetDescriptor<Texture> weaponizerButton = new AssetDescriptor<Texture>("data\\Weaponizer.png", Texture.class);
	public static final AssetDescriptor<BitmapFont> font = new AssetDescriptor<BitmapFont>("data\\font.fnt", BitmapFont.class);
	public static final AssetDescriptor<BitmapFont> font1 = new AssetDescriptor<BitmapFont>("data\\font.fnt", BitmapFont.class);
	public static final AssetDescriptor<BitmapFont> font2 = new AssetDescriptor<BitmapFont>("data\\slotMachineUI\\fontPics\\bitMapForStats.fnt", BitmapFont.class);
	public static final AssetDescriptor<Music> menuBackgroundMusic = new AssetDescriptor<Music>("data\\backgroundMusic.mp3", Music.class);
	
	//tutorial
	public static final AssetDescriptor<Texture> moveHere = new AssetDescriptor<Texture>("data\\tutorialStuff\\moveHere.png", Texture.class);
	public static final AssetDescriptor<Texture> aFinger = new AssetDescriptor<Texture>("data\\tutorialStuff\\aGoodLookingFinger.png", Texture.class);
	public static final AssetDescriptor<Texture> greenPointerArrow = new AssetDescriptor<Texture>("data\\tutorialStuff\\greenPointerArrow.png", Texture.class);
	
	//props
	public static final AssetDescriptor<Texture> propExplosiveBarrel = new AssetDescriptor<Texture>("data\\propBarrelAnimation.png", Texture.class);
	public static final AssetDescriptor<Texture> propTreasure = new AssetDescriptor<Texture>("data\\propTreasureAnimation.png", Texture.class);
	public static final AssetDescriptor<Texture> propToxicPuddle = new AssetDescriptor<Texture>("data\\googleSaysItIsAPool.png", Texture.class);
	public static final AssetDescriptor<Texture> FROZEN_TRAP = new AssetDescriptor<Texture>("data\\Frozen.png", Texture.class);
	public static final AssetDescriptor<Texture> DAMAGE_TRAP = new AssetDescriptor<Texture>("data\\appleTrap.png", Texture.class);
	public static final AssetDescriptor<Texture> POISON_TRAP = new AssetDescriptor<Texture>("data\\slotIcons\\POISON_TRAP_ICON.png", Texture.class);
	
	public static final AssetDescriptor<Texture> turretImg = new AssetDescriptor<Texture>("data\\turret.png", Texture.class);
	public static final AssetDescriptor<Texture> plasmaShield = new AssetDescriptor<Texture>("data\\plasmaShieldAnimation.png", Texture.class);
	public static final AssetDescriptor<Texture> iceCube = new AssetDescriptor<Texture>("data\\iceCube.png", Texture.class);

	//projectiles
	public static final AssetDescriptor<Texture> arrow = new AssetDescriptor<Texture>("data\\arrrrow.png", Texture.class);
	public static final AssetDescriptor<Texture> arrowPoisoned = new AssetDescriptor<Texture>("data\\arrrrowPoisoned.png", Texture.class);
	public static final AssetDescriptor<Texture> arrowExplosive = new AssetDescriptor<Texture>("data\\arrrrowExplosive.png", Texture.class);
	public static final AssetDescriptor<Texture> leachProjectile = new AssetDescriptor<Texture>("data\\leechProjectile.png", Texture.class);
	public static final AssetDescriptor<Texture> turretProjectile = new AssetDescriptor<Texture>("data\\turretProjectile.png", Texture.class);
	public static final AssetDescriptor<Texture> projectileHydra = new AssetDescriptor<Texture>("data\\ProjectileHydra.png", Texture.class);
	public static final AssetDescriptor<Texture> trailEffect = new AssetDescriptor<Texture>("data\\trailEffect.png", Texture.class);
	
	//Slotmachine
	public static final AssetDescriptor<Texture> slotMachineCase = new AssetDescriptor<Texture>("data\\slotMachineUI\\slotMachineCase.png", Texture.class);
	public static final AssetDescriptor<Texture> saveSlotBar = new AssetDescriptor<Texture>("data\\slotMachineUI\\savedSlotsBar.png", Texture.class);
	public static final AssetDescriptor<Texture> selectedSavedSlot = new AssetDescriptor<Texture>("data\\slotMachineUI\\selectedSavedSlot.png", Texture.class);
	public static final AssetDescriptor<Texture> addedSavedSlotAnimation = new AssetDescriptor<Texture>("data\\slotMachineUI\\addedSavedSlotAnimation.png", Texture.class);
	public static final AssetDescriptor<Texture> slotAnimation = new AssetDescriptor<Texture>("data\\slotMachineUI\\slotAnimation.png", Texture.class);
	
	//effects
	public static final AssetDescriptor<Texture> FADE_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\FADE_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> RADIOACTIVE_AURA_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\RADIOACTIVE_AURA_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> FROZENNEGATIVEEFFECT_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\FROZEN_NEGATIVE_EFFECT_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> POISONEDNEGATIVEEFFECT_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\POISONED_NEGATIVE_EFFECT_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> SCAREDNEGATIVEEFFECT_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\SCARED_NEGATIVE_EFFECT_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> STUNNEGATIVEEFFECT_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\STUN_NEGATIVE_EFFECT_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> WEAKENED_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\WEAKENED_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> HASTE_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\HASTE_ICON.png", Texture.class);

	//slots
	public static final AssetDescriptor<Texture> TURRET_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\TURRET_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> PANIC_TELEPORT_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\PANIC_TELEPORT__ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> SPEED_BOOST_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\SPEED_BOOST_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> THREEARROW_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\3ARROW_ARROW_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> EXPLOSIVE_ARROW_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\EXPLOSIVE_ARROW_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> SHADOW_ARROW_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\SHADOW_ARROW_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> POISONED_ARROW_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\POISONED_ARROW_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> DAMAGE_TRAP_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\DAMAGE_TRAP_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> EXPLOSIVE_TRAP_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\EXPLOSIVE_TRAP_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> POISONED_TRAP_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\POISON_TRAP_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> FROST_TRAP_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\FROST_TRAP_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> IMPROVEMAXHEALTH_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\IMPROVEMAXHEALTH_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> IMPROVEMAXOXYGEN_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\IMPROVEMAXOXYGEN_ICON2.png", Texture.class);
	public static final AssetDescriptor<Texture> IMPROVEARROWDAMAGE_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\IMPROVEARROWDAMAGE_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> IMPROVEARROWSPEED_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\IMPROVEARROWSPEED_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> IMPROVEMOVEMENTSPEED_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\IMPROVE_MOVEMENT_SPEED_ICON.png", Texture.class);
	
	
	//levels
	public static final AssetDescriptor<Texture> slotLevel1 = new AssetDescriptor<Texture>("data\\slotIcons\\slotLevel1.png", Texture.class);
	public static final AssetDescriptor<Texture> slotLevel2 = new AssetDescriptor<Texture>("data\\slotIcons\\slotLevel2.png", Texture.class);
	public static final AssetDescriptor<Texture> slotLevel3 = new AssetDescriptor<Texture>("data\\slotIcons\\slotLevel3.png", Texture.class);
	public static final AssetDescriptor<Texture> slotLevel4 = new AssetDescriptor<Texture>("data\\slotIcons\\slotLevel4.png", Texture.class);
	public static final AssetDescriptor<Texture> slotLevel5 = new AssetDescriptor<Texture>("data\\slotIcons\\slotLevel5.png", Texture.class);
	
	//items
	public static final AssetDescriptor<Texture> oxygenKitItem = new AssetDescriptor<Texture>("data\\Items\\OxygenKitItem.png", Texture.class);
	public static final AssetDescriptor<Texture> healthKitItem = new AssetDescriptor<Texture>("data\\Items\\HealthKitItem.png", Texture.class);
	public static final AssetDescriptor<Texture> blueItem = new AssetDescriptor<Texture>("data\\Items\\blue.png", Texture.class);
	public static final AssetDescriptor<Texture> greenItem = new AssetDescriptor<Texture>("data\\Items\\green.png", Texture.class);
	public static final AssetDescriptor<Texture> yellowItem = new AssetDescriptor<Texture>("data\\Items\\yellow.png", Texture.class);
	public static final AssetDescriptor<Texture> purpleItem = new AssetDescriptor<Texture>("data\\Items\\purple.png", Texture.class);
	public static final AssetDescriptor<Texture> redItem = new AssetDescriptor<Texture>("data\\Items\\red.png", Texture.class);
	public static final AssetDescriptor<Texture> orangeItem = new AssetDescriptor<Texture>("data\\Items\\orange.png", Texture.class);
	public static final AssetDescriptor<Texture> greyItem = new AssetDescriptor<Texture>("data\\Items\\grey.png", Texture.class);
	public static final AssetDescriptor<Texture> lightBlueItem = new AssetDescriptor<Texture>("data\\Items\\lightBlue.png", Texture.class);
	public static final AssetDescriptor<Texture> pinkItem = new AssetDescriptor<Texture>("data\\Items\\pink.png", Texture.class);
	
//	public static final AssetDescriptor<Texture> tiles = new AssetDescriptor<Texture>("data\\Tiles.png", Texture.class);
//	public static final AssetDescriptor<Texture> tileSet_SAND_WORLD = new AssetDescriptor<Texture>("data\\tileSet_SAND_WORLD.png", Texture.class);
	
	public static void load(){
		manager.load(menuBackgroundMusic);
		manager.load(turretImg);
		manager.load(TURRET_ICON);
		manager.load(turretProjectile);
		manager.load(bow);
		manager.load(aimingAuraSprite);
		manager.load(greenPointerArrow);
		manager.load(enemy);
		manager.load(aFinger);
		manager.load(plasmaShield);
		manager.load(enemyLeech);
		manager.load(enemySofa);
		manager.load(nastyaSpriteStandard);
		manager.load(nastyaSpriteGun);
		manager.load(enemyZombie);
		manager.load(enemyMaggot);
		manager.load(propExplosiveBarrel);
		manager.load(propTreasure);
		manager.load(propToxicPuddle);
		manager.load(iceCube);
		manager.load(arrow);
		manager.load(moveHere);
		manager.load(HealthBarIcon);
		manager.load(HealthBarTail);
		manager.load(HealthBarMiddle);
		manager.load(HealthBarHead);
		manager.load(OxygenBarIcon);
		manager.load(OxygenBarTail);
		manager.load(OxygenBarMiddle);
		manager.load(OxygenBarHead);
		manager.load(PointerHead);
		manager.load(healthKitItem);
		manager.load(oxygenKitItem);
		manager.load(blueItem);
		manager.load(greenItem);
		manager.load(yellowItem);
		manager.load(redItem);
		manager.load(purpleItem);
		manager.load(orangeItem);
		manager.load(pinkItem);
		manager.load(greyItem);
		manager.load(lightBlueItem);
		manager.load(weaponizerButton);
//		manager.load(tiles);
//		manager.load(tileSet_SAND_WORLD);
		manager.load(slotMachineCase);
		manager.load(saveSlotBar);
		manager.load(FADE_ICON);
		manager.load(WEAKENED_ICON);
		manager.load(RADIOACTIVE_AURA_ICON);
		manager.load(HASTE_ICON);
		manager.load(SPEED_BOOST_ICON);
		manager.load(THREEARROW_ICON);
		manager.load(EXPLOSIVE_ARROW_ICON);
		manager.load(SHADOW_ARROW_ICON);
		manager.load(POISONED_ARROW_ICON);
		manager.load(DAMAGE_TRAP_ICON);
		manager.load(EXPLOSIVE_TRAP_ICON);
		manager.load(POISONED_TRAP_ICON);
		manager.load(FROST_TRAP_ICON);
		manager.load(IMPROVEMAXHEALTH_ICON);
		manager.load(IMPROVEMAXOXYGEN_ICON);
		manager.load(IMPROVEARROWDAMAGE_ICON);
		manager.load(IMPROVEARROWSPEED_ICON);
		manager.load(IMPROVEMOVEMENTSPEED_ICON);
		manager.load(FROZENNEGATIVEEFFECT_ICON);
		manager.load(POISONEDNEGATIVEEFFECT_ICON);
		manager.load(SCAREDNEGATIVEEFFECT_ICON);
		manager.load(STUNNEGATIVEEFFECT_ICON);
		manager.load(PANIC_TELEPORT_ICON);
		manager.load(slotLevel1);
		manager.load(slotLevel2);
		manager.load(slotLevel3);
		manager.load(slotLevel4);
		manager.load(slotLevel5);
		manager.load(selectedSavedSlot);
		manager.load(addedSavedSlotAnimation);
		manager.load(slotAnimation);
		manager.load(POISON_TRAP);
		manager.load(FROZEN_TRAP);
		manager.load(DAMAGE_TRAP);
		manager.load(arrowPoisoned);
		manager.load(arrowExplosive);
		manager.load(leachProjectile);
		manager.load(projectileHydra);
		
		manager.setLoader(BitmapFont.class, new BitmapFontLoader(new InternalFileHandleResolver()));
		manager.load(font);
		manager.load(font2);
		manager.load(font1);
//		manager.setLoader(TiledMap.class, new TmxMapLoader());
		//Effects load
		manager.load(trailEffect);
//		manager.load(map);
	}

	public static void dispose(){
		manager.dispose();
	}
	
}