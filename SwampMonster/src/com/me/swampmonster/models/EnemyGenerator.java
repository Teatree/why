package com.me.swampmonster.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

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
		toughtnessParams.put(4, Toughness.FREEZER_GUY);
		toughtnessParams.put(6, Toughness.EXPLOSIVE_GUY);
		toughtnessParams.put(7, Toughness.PLASMA_GUY);
		random = new Random();
	}
	
	public Enemy getToughEnemy(int enemyMin, int enemyMax, int toughMin,  int toughMax){
		random = new Random();
		int currentEnemyType = random.nextInt(enemyMax - enemyMin) + enemyMin;
		int toughGuy = random.nextInt(toughMax - toughMin) + toughMin;
		Enemy enemy = null;
		try {
			enemy = enemyTypes.get(currentEnemyType).getConstructor(Vector2.class).newInstance(new Vector2());
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
			System.out.println(currentEnemyType);			
			enemy = enemyTypes.get(currentEnemyType).getConstructor(Vector2.class).newInstance(new Vector2());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enemy;
	}
	
	public void setToughtness (Toughness toughtness, Enemy enemy) {
		if(toughtness != null){
			enemy.setToughness(toughtness);
			enemy.setPlayerMovementSpeedX((float)(enemy.getPlayerMovementSpeedX() + toughtness.speed));
			enemy.setPoints((int)(enemy.getPoints() + toughtness.points));
			enemy.setDamage((int)(enemy.getDamage() + toughtness.damage));
			enemy.setHealth((int)(enemy.getHealth() + toughtness.health));
			enemy.setAttackSpeed((int)(enemy.getAttackSpeed() + toughtness.attackSpeed));
			enemy.setColour(toughtness.red, toughtness.green, toughtness.blue, toughtness.alpha);
		}
	}
}
