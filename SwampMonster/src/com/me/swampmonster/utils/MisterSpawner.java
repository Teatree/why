package com.me.swampmonster.utils;

import java.util.Stack;

import com.me.swampmonster.models.Enemy;
import com.me.swampmonster.models.EnemyGenerator;
import com.me.swampmonster.models.Wave;

public class MisterSpawner {
	EnemyGenerator enemyGenerator = new EnemyGenerator();
	
	public Wave generateWave(int playersScore){
		Wave wave = new Wave();
		int maxEnemy = calcMaxEnemy(playersScore);
		int minEnemy = calcMinEnemy(playersScore);
		int maxTough = calcMaxTough(playersScore);
		int minTough = calcMinTough(playersScore);
		int size = calcSize(playersScore);
		int amountOfToughGuys = calcAmountOfTough(playersScore);
		Stack<Enemy> enemies = new Stack<Enemy>(); 
		
		for (int i = 0; i < size - amountOfToughGuys; i++){
			enemies.add(enemyGenerator.getPlainEnemy(minEnemy, maxEnemy));
		}
		
		for (int i = 0; i < amountOfToughGuys; i++){
			//change enemy generator
			enemies.add(enemyGenerator.getToughEnemy(minEnemy, maxEnemy, minTough, maxTough));
		}
		wave.enemies = enemies;
		wave.enemiesOnBattleField = calcEnemiesOnBattleField(playersScore);
		return wave;
	}
	
	//add formula
	private int calcMaxEnemy(int playersScore){
		return 5;
	}
	
	private int calcMinEnemy(int playersScore){
		return 1;
	}
	
	private int calcMaxTough(int playersScore){
		return 5;
	}
	
	private int calcMinTough(int playersScore){
		return 1;
	}
	
	private int calcAmountOfTough(int playersScore){
		return 3;
	}
	
	private int calcSize(int playersScore){
		return 10;
	}
	
	private int calcEnemiesOnBattleField(int playersScore){
		return 5;
	}

}
