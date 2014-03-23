package com.me.swampmonster.models;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

public class EnemyGenerator {
	private Map <Integer, Class<? extends Enemy>> enemyTypes;
	
	public EnemyGenerator () {
		enemyTypes = new HashMap<Integer, Class<? extends Enemy>>();
		enemyTypes.put(0, EnemyZombie.class);
		enemyTypes.put(1, EnemyMaggot.class);
		enemyTypes.put(2, EnemyLeech.class);
		enemyTypes.put(3, EnemySofa.class);
		enemyTypes.put(4, EnemyBreather.class);
	}
	
	public Enemy getEnemy(int enemyRang, int toughRang){
		try {
			return enemyTypes.get(enemyRang).getConstructor(Vector2.class).newInstance(new Vector2());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
