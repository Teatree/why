package com.me.swampmonster.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
public class AssetsMainManager {

	public static final AssetManager manager = new AssetManager();
	
	public static final AssetDescriptor<Texture> nastyaSpriteStandard = new AssetDescriptor<Texture>("data\\NastyaOxygenSheet.png", Texture.class);
	public static final AssetDescriptor<Texture> nastyaSpriteGun = new AssetDescriptor<Texture>("data\\NastyaGunSheet.png", Texture.class);
	public static final AssetDescriptor<Texture> enemyLeech = new AssetDescriptor<Texture>("data\\EnemyLeech.png", Texture.class);
	public static final AssetDescriptor<Texture> enemyZombie = new AssetDescriptor<Texture>("data\\EnemyZombie.png", Texture.class);
	public static final AssetDescriptor<Texture> enemyMaggot = new AssetDescriptor<Texture>("data\\EnemyMaggot.png", Texture.class);
	public static final AssetDescriptor<Texture> enemy = new AssetDescriptor<Texture>("data\\Skelenten.png", Texture.class);
	public static final AssetDescriptor<Texture> HealthBarIcon = new AssetDescriptor<Texture>("data\\HealthBarIcon.png", Texture.class);
	public static final AssetDescriptor<Texture> HealthBarTail = new AssetDescriptor<Texture>("data\\HealthBarTail.png", Texture.class);
	public static final AssetDescriptor<Texture> HealthBarMiddle = new AssetDescriptor<Texture>("data\\HealthBarMiddle.png", Texture.class);
	public static final AssetDescriptor<Texture> HealthBarHead = new AssetDescriptor<Texture>("data\\HealthBarHead.png", Texture.class);
	public static final AssetDescriptor<Texture> OxygenBarIcon = new AssetDescriptor<Texture>("data\\OxygenBarIcon.png", Texture.class);
	public static final AssetDescriptor<Texture> OxygenBarTail = new AssetDescriptor<Texture>("data\\OxygenBarTail.png", Texture.class);
	public static final AssetDescriptor<Texture> OxygenBarMiddle = new AssetDescriptor<Texture>("data\\OxygenBarMiddle.png", Texture.class);
	public static final AssetDescriptor<Texture> OxygenBarHead = new AssetDescriptor<Texture>("data\\OxygenBarHead.png", Texture.class);
	public static final AssetDescriptor<Texture> arrow = new AssetDescriptor<Texture>("data\\arrrrow.png", Texture.class);
	public static final AssetDescriptor<Texture> arrowPoisoned = new AssetDescriptor<Texture>("data\\arrrrowPoisoned.png", Texture.class);
	public static final AssetDescriptor<Texture> arrowExplosive = new AssetDescriptor<Texture>("data\\arrrrowExplosive.png", Texture.class);
	public static final AssetDescriptor<Texture> items = new AssetDescriptor<Texture>("data\\Items.png", Texture.class);
	public static final AssetDescriptor<Texture> weaponizerButton = new AssetDescriptor<Texture>("data\\Weaponizer.png", Texture.class);
	public static final AssetDescriptor<Texture> tiles = new AssetDescriptor<Texture>("data\\Tiles.png", Texture.class);
	public static final AssetDescriptor<Texture> croshair = new AssetDescriptor<Texture>("data\\Croshair.png", Texture.class);
	public static final AssetDescriptor<Texture> slotMachineCase = new AssetDescriptor<Texture>("data\\slotMachineUI\\slotMachineCase.png", Texture.class);
	public static final AssetDescriptor<Texture> slotMachineNextButton = new AssetDescriptor<Texture>("data\\slotMachineUI\\slotMachineNextButton.png", Texture.class);
	public static final AssetDescriptor<Texture> FADE_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\shield.png", Texture.class);
	public static final AssetDescriptor<Texture> RADIOACTIVE_AURA_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\RADIOACTIVE_AURA_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> SPEED_BOOST_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\speedBoost.png", Texture.class);
	public static final AssetDescriptor<Texture> THREEARROW_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\3arrows.png", Texture.class);
	public static final AssetDescriptor<Texture> EXPLOSIVE_ARROW_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\explosiveArrow.png", Texture.class);
	public static final AssetDescriptor<Texture> SHADOW_ARROW_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\SHADOW_ARROW_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> POISONED_ARROW_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\poisonedArrow.png", Texture.class);
	public static final AssetDescriptor<Texture> DAMAGE_TRAP_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\DAMAGE_TRAP_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> slotBackGround = new AssetDescriptor<Texture>("data\\slotIcons\\slotBackGround.png", Texture.class);
	public static final AssetDescriptor<Texture> EXPLOSIVE_TRAP_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\explosive_icon2.png", Texture.class);
	public static final AssetDescriptor<Texture> POISONED_TRAP_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\poison.png", Texture.class);
	public static final AssetDescriptor<Texture> FROST_TRAP_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\Frozen.png", Texture.class);
	public static final AssetDescriptor<Texture> IMPROVEMAXHEALTH_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\IMPROVEMAXHEALTH_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> IMPROVEMAXOXYGEN_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\IMPROVEMAXOXYGEN_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> IMPROVEARROWDAMAGE_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\IMPROVEARROWDAMAGE_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> IMPROVEARROWSPEED_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\IMPROVEARROWSPEED_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> IMPROVEMOVEMENTSPEED_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\IMPROVE_MOVEMENT_SPEED_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> FROZENNEGATIVEEFFECT_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\FROZEN_NEGATIVE_EFFECT_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> POISONEDNEGATIVEEFFECT_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\POISONED_NEGATIVE_EFFECT_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> SCAREDNEGATIVEEFFECT_ICON = new AssetDescriptor<Texture>("data\\slotIcons\\fear.png", Texture.class);
	public static final AssetDescriptor<Texture> POISON_TRAP = new AssetDescriptor<Texture>("data\\googleSaysItIsAPool.png", Texture.class);
	public static final AssetDescriptor<Texture> FROZEN_TRAP = new AssetDescriptor<Texture>("data\\Frozen.png", Texture.class);
	public static final AssetDescriptor<Texture> DAMAGE_TRAP = new AssetDescriptor<Texture>("data\\appleTrap.png", Texture.class);
	public static final AssetDescriptor<Texture> slotMachineWindow = new AssetDescriptor<Texture>("data\\slotMachineUI\\slotMachineWindow.png", Texture.class);
	public static final AssetDescriptor<Texture> slotMachineWindowYes = new AssetDescriptor<Texture>("data\\slotMachineUI\\slotMachineWindowYES.png", Texture.class);
	public static final AssetDescriptor<Texture> slotMachineWindowNo = new AssetDescriptor<Texture>("data\\slotMachineUI\\slotMachineWindowNO.png", Texture.class);
	public static final AssetDescriptor<Texture> leachProjectile = new AssetDescriptor<Texture>("data\\leechProjectile.png", Texture.class);
	public static final AssetDescriptor<TiledMap> map = new AssetDescriptor<TiledMap>("data\\Map.tmx", TiledMap.class);
	public static final AssetDescriptor<BitmapFont> font = new AssetDescriptor<BitmapFont>("data\\font.fnt", BitmapFont.class);
	
	public static void load(){
		manager.load(nastyaSpriteStandard);
		manager.load(nastyaSpriteGun);
		manager.load(enemy);
		manager.load(enemyLeech);
		manager.load(enemyZombie);
		manager.load(enemyMaggot);
		manager.load(arrow);
		manager.load(HealthBarIcon);
		manager.load(HealthBarTail);
		manager.load(HealthBarMiddle);
		manager.load(HealthBarHead);
		manager.load(OxygenBarIcon);
		manager.load(OxygenBarTail);
		manager.load(OxygenBarMiddle);
		manager.load(OxygenBarHead);
		manager.load(items);
		manager.load(weaponizerButton);
		manager.load(tiles);
		manager.load(croshair);
		manager.load(slotMachineCase);
		manager.load(slotMachineNextButton);
		manager.load(FADE_ICON);
		manager.load(RADIOACTIVE_AURA_ICON);
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
		manager.load(POISON_TRAP);
		manager.load(FROZEN_TRAP);
		manager.load(DAMAGE_TRAP);
		manager.load(slotMachineWindow);
		manager.load(slotMachineWindowYes);
		manager.load(slotMachineWindowNo);
		manager.load(slotBackGround);
		manager.load(arrowPoisoned);
		manager.load(arrowExplosive);
		manager.load(leachProjectile);
		manager.setLoader(BitmapFont.class, new BitmapFontLoader(new InternalFileHandleResolver()));
		manager.load(font);
		manager.setLoader(TiledMap.class, new TmxMapLoader());
		manager.load(map);
	}

	public static void dispose(){
		manager.dispose();
	}
	
}