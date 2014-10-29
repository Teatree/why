package com.me.swampmonster.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.enemies.EnemyBreather;
import com.me.swampmonster.models.enemies.EnemyLeech;
import com.me.swampmonster.models.enemies.EnemyMaggot;
import com.me.swampmonster.models.enemies.EnemySofa;
import com.me.swampmonster.models.enemies.EnemyZombie;

public class EnemyGenerator {
	private Map <Integer, Class<? extends Enemy>> enemyTypes;
	private Random random;
	private Map <Integer, Toughness> toughtnessParams;
	
	public EnemyGenerator () {
		
		enemyTypes = new HashMap<Integer, Class<? extends Enemy>>();
		enemyTypes.put(0, EnemyZombie.class);
		enemyTypes.put(1, EnemyMaggot.class);
		enemyTypes.put(2, EnemyLeech.class);
		enemyTypes.put(3, EnemySofa.class);
		enemyTypes.put(4, EnemyBreather.class);
		
		toughtnessParams = new HashMap<Integer, Toughness>();
		toughtnessParams.put(0, null);
		toughtnessParams.put(1, Toughness.ARMORED_GUY);
		toughtnessParams.put(2, Toughness.SPEEDY_GUY);
		toughtnessParams.put(3, Toughness.ANGRY_GUY);
		toughtnessParams.put(4, Toughness.POISONOUS_GUY);
		toughtnessParams.put(5, Toughness.FREEZER_GUY);
//		toughtnessParams.put(6, Toughness.EXPLOSIVE_GUY);
//		toughtnessParams.put(7, Toughness.PLASMA_GUY);
		random = new Random();
	}
	
	public Enemy getToughEnemy(int enemyMin, int enemyMax, int toughMin,  int toughMax){
		random = new Random();
		int currentEnemyType = random.nextInt(enemyMax - enemyMin) + enemyMin;
		int toughGuy = random.nextInt(toughMax - toughMin) + toughMin +1;
		Enemy enemy = null;
		try {
			enemy = enemyTypes.get(currentEnemyType).getConstructor(Vector2.class).newInstance(new Vector2());
			enemy.sprite.setSize(enemy.sprite.getWidth()/2, enemy.sprite.getHeight()/2);
			setToughtness(toughtnessParams.get(toughGuy), enemy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enemy;
	}
	
	public Enemy getPlainEnemy(int enemyMin, int enemyMax){
		random = new Random();
		int currentEnemyType = random.nextInt(enemyMax - enemyMin) + enemyMin;
		Enemy enemy = null;
		try {
			enemy = enemyTypes.get(currentEnemyType).getConstructor(Vector2.class).newInstance(new Vector2());
			enemy.sprite.setSize(enemy.sprite.getWidth()/2, enemy.sprite.getHeight()/2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enemy;
	}
	
	public void setToughtness (Toughness toughtness, Enemy enemy) {
		if(toughtness != null){
			enemy.toughness = toughtness;
			enemy.setPlayerMovementSpeed((float)(enemy.getPlayerMovementSpeed() + toughtness.speed));
			enemy.points +=toughtness.points;
			//: TODO enemy.damag is static
			enemy.damage += toughtness.damage;
			enemy.health += toughtness.health;
			enemy.attackSpeed += toughtness.attackSpeed;
			enemy.setColour(toughtness.red, toughtness.green, toughtness.blue, toughtness.alpha);
			
		}
	}

	public enum Toughness {
		ARMORED_GUY 	(-0.1, 0d, 1d, 25d, 0d, 192f/255, 192f/255, 192f/255, 1f), 
		SPEEDY_GUY 		(0.3, 0d, 0d, 25d, 5d, 1f, 1f, 0f, 1f), 
		ANGRY_GUY 		(0d, 1d, 0d, 50d, 0d, 220f/255, 20f/255, 60f/255, 1f ), 
		POISONOUS_GUY 	(0d, 0d, 0d, 75d, -5d, 124f/255, 252f/255, 0f, 1f), 
		FREEZER_GUY 	(0d, 0d, 0d, 75d, 0d, 0f, 191f/255, 1f, 1f),
		EXPLOSIVE_GUY 	(0d, 0d, 1d, 100d, 0d, 244f/255, 164f/255, 96f/255, 1f),
		PLASMA_GUY 		(0d, 1d, 0d, 150d, 0d, 162f/255, 19/255f, 166f/255, 1f);
		
		public final Double speed;
		public final Double damage; 
		public final Double health;
		public final Double points;
		public final Double attackSpeed;
		public final float red;
		public final float blue;
		public final float green;
		public final float alpha;
		
		Toughness (Double speed, Double damage, Double health, Double points, Double attackSpeed, float red, float green, float blue, float alpha){
			this.speed = speed;
			this.damage = damage;
			this.health = health;
			this.points = points;
			this.attackSpeed = attackSpeed;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.alpha = alpha;
		}
	}
}
