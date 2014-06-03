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
	public static final AssetDescriptor<Texture> projectile = new AssetDescriptor<Texture>("data\\projectile.png", Texture.class);
	public static final AssetDescriptor<Texture> items = new AssetDescriptor<Texture>("data\\Items.png", Texture.class);
	public static final AssetDescriptor<Texture> weaponizerButton = new AssetDescriptor<Texture>("data\\Weaponizer.png", Texture.class);
	public static final AssetDescriptor<Texture> tiles = new AssetDescriptor<Texture>("data\\Tiles.png", Texture.class);
	public static final AssetDescriptor<Texture> croshair = new AssetDescriptor<Texture>("data\\Croshair.png", Texture.class);
	public static final AssetDescriptor<Texture> slotMachineCase = new AssetDescriptor<Texture>("data\\slotMachineCase.png", Texture.class);
	public static final AssetDescriptor<Texture> slotMachineNextButton = new AssetDescriptor<Texture>("data\\slotMachineNextButton.png", Texture.class);
	public static final AssetDescriptor<Texture> FADE_ICON = new AssetDescriptor<Texture>("data\\FADE_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> RADIOACTIVE_AURA_ICON = new AssetDescriptor<Texture>("data\\RADIOACTIVE_AURA_ICON.png", Texture.class);
	public static final AssetDescriptor<Texture> SPEED_BOOST_ICON = new AssetDescriptor<Texture>("data\\SPEED_BOOST_ICON.png", Texture.class);
	public static final AssetDescriptor<TiledMap> map = new AssetDescriptor<TiledMap>("data\\Map.tmx", TiledMap.class);
	public static final AssetDescriptor<BitmapFont> font = new AssetDescriptor<BitmapFont>("data\\font.fnt", BitmapFont.class);
	
	public static void load(){
		manager.load(nastyaSpriteStandard);
		manager.load(nastyaSpriteGun);
		manager.load(enemy);
		manager.load(enemyLeech);
		manager.load(enemyZombie);
		manager.load(enemyMaggot);
		manager.load(projectile);
		manager.load(items);
		manager.load(weaponizerButton);
		manager.load(tiles);
		manager.load(croshair);
		manager.load(slotMachineCase);
		manager.load(slotMachineNextButton);
		manager.load(FADE_ICON);
		manager.load(RADIOACTIVE_AURA_ICON);
		manager.load(SPEED_BOOST_ICON);
		manager.setLoader(BitmapFont.class, new BitmapFontLoader(new InternalFileHandleResolver()));
		manager.load(font);
		manager.setLoader(TiledMap.class, new TmxMapLoader());
		manager.load(map);
	}

	public static void dispose(){
		manager.dispose();
	}
	
}