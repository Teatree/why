package com.me.swampmonster.models;

import java.util.Random;
import java.util.Stack;

public class WaveGenerator {
EnemyGenerator enemyGenerator = new EnemyGenerator();
	
	public Wave generateWave(int playersScore){
		Wave wave = new Wave();
		wave.enemies = new Stack<Enemy>();
		wave.pendingPeriod = calcPendingPeriod(playersScore);
		wave.rate = calcRate(playersScore);
		int maxEnemy = calcMaxEnemy(playersScore);
		int minEnemy = calcMinEnemy(playersScore);
		int maxTough = calcMaxTough(playersScore);
		int minTough = calcMinTough(playersScore);
		int size = calcSize(playersScore);
		int  amountOfToughGuys = calcAmountOfTough(playersScore);
		Enemy [] tempEnemies = new Enemy[size];
		
		for (int i = 0; i < size - amountOfToughGuys; i++){
			tempEnemies[i] = enemyGenerator.getPlainEnemy(minEnemy, maxEnemy);
		}
		
		for (int i = size - amountOfToughGuys; i < size; i++){
			//change enemy generator
			tempEnemies [i] = enemyGenerator.getToughEnemy(minEnemy, maxEnemy, minTough, maxTough);
		}
		shuffle(tempEnemies);
		for (Enemy e : tempEnemies){
			wave.enemies.push(e);
		}
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
	
	private int calcPendingPeriod(int playersScore){
		return 5;
	}
	
	private int calcRate(int playersScore){
		return 5;
	}
	
	private void shuffle(Enemy[] enemies)
	{
		Random rand = new Random();
	    for (int i = 0; i < enemies.length; i++)
	    {
	        int swap = rand.nextInt(i + 1);
	        Enemy temp = enemies[swap];
	        enemies[swap] = enemies[i];
	        enemies[i] = temp;
	    }
	}
}
