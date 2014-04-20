package com.me.swampmonster.models;

import java.util.Random;
import java.util.Stack;

public class WaveGenerator {
	EnemyGenerator enemyGenerator = new EnemyGenerator();
	WaveParams waveParams;
	private Random random = new Random();
	
	private enum WaveParams{
		p0_500(0, 2, 0, 2, 10, 15, 2, 4, 5, 6, 3.0f, 10, 3, 4),
		p500_1000(0, 5, 0, 3, 99, 100, 4, 5, 6, 7, 2.5f, 7, 9, 10),
		p1000_2000(0, 4, 0, 4, 28, 32, 6, 8, 7, 9, 2.0f, 5, 5, 5),
		p2000_4000(0, 5, 0, 5, 37, 42, 10, 13, 9, 11, 1f, 3, 5, 7);
			
		public final int minEnemy;
		public final int maxEnemy;
		public final int minTough;
		public final int maxTough;
		public final int waveSizeMin;
		public final int waveSizeMax;
		public final int amountOfToughGuysInAWaveMin;
		public final int amountOfToughGuysInAWaveMax;
		public final int enemiesOnBattleFieldMin;
		public final int enemiesOnBattleFieldMax;
		public final float rate;
		public final int pendingPeriod;
		public final int waveLimitMin;
		public final int waveLimitMax;
		
		
	private WaveParams(int minEnemy, int maxEnemy, int minTough,
				int maxTough, int waveSizeMin, int waveSizeMax,
				int amountOfToughGuysInAWaveMin,
				int amountOfToughGuysInAWaveMax, int enemiesOnBattleFieldMin,
				int enemiesOnBattleFieldMax, float rate, int pendingPeriod, int waveLimitMin, int waveLimitMax) {
			this.minEnemy = minEnemy;
			this.maxEnemy = maxEnemy;
			this.minTough = minTough;
			this.maxTough = maxTough;
			this.waveSizeMin = waveSizeMin;
			this.waveSizeMax = waveSizeMax;
			this.amountOfToughGuysInAWaveMin = amountOfToughGuysInAWaveMin;
			this.amountOfToughGuysInAWaveMax = amountOfToughGuysInAWaveMax;
			this.enemiesOnBattleFieldMin = enemiesOnBattleFieldMin;
			this.enemiesOnBattleFieldMax = enemiesOnBattleFieldMax;
			this.rate = rate;
			this.pendingPeriod = pendingPeriod;
			this.waveLimitMin = waveLimitMin;
			this.waveLimitMax = waveLimitMax;
		}
	}

	public int getWavesAmount(int playersScore){
		setWaveParams(playersScore);	
		return random.nextInt(waveParams.waveLimitMax - waveParams.waveLimitMin) + waveParams.waveLimitMin;
	}
	
	public Wave generateWave(int playersScore){
		Wave wave = new Wave();
		
		setWaveParams(playersScore);	
		
		wave.enemies = new Stack<Enemy>();
		wave.pendingPeriod = waveParams.pendingPeriod;
		wave.rate = waveParams.rate;
		int maxEnemy = waveParams.maxEnemy;
		int minEnemy = waveParams.minEnemy;
		int maxTough = waveParams.maxTough;
		int minTough = waveParams.minTough;
		int waveSize = calcSize(playersScore);
		int maxAmountOfToughGuysInAWave = calcMaxAmountOfToughGuysInAWave(playersScore);
		Enemy [] tempEnemies = new Enemy[waveSize];
		
		for (int i = 0; i < waveSize - maxAmountOfToughGuysInAWave; i++){
			tempEnemies[i] = enemyGenerator.getPlainEnemy(minEnemy, maxEnemy);
		}
		
		for (int i = waveSize - maxAmountOfToughGuysInAWave; i < waveSize; i++){
			tempEnemies [i] = enemyGenerator.getToughEnemy(minEnemy, maxEnemy, minTough, maxTough);
		}
		shuffle(tempEnemies);
		for (Enemy e : tempEnemies){
			wave.enemies.push(e);
		}
		wave.enemiesOnBattleField = calcEnemiesOnBattleField(playersScore);
		return wave;
	}

	private void setWaveParams(int playersScore) {
		if(playersScore>=0 && playersScore<100){
			waveParams = WaveParams.p0_500;
		}
		else if(playersScore>100 && playersScore<1000){
			waveParams = WaveParams.p500_1000;
		}
		else if(playersScore>1000 && playersScore<2000){
			waveParams = WaveParams.p1000_2000;
		}
		else if(playersScore>2000 && playersScore<4000){
			waveParams = WaveParams.p2000_4000;
		}
	}
	
	private int calcMaxAmountOfToughGuysInAWave(int playersScore){
		return random.nextInt(waveParams.amountOfToughGuysInAWaveMax - waveParams.amountOfToughGuysInAWaveMin) + waveParams.amountOfToughGuysInAWaveMin;
	}
	
	private int calcSize(int playersScore){
		return random.nextInt(waveParams.waveSizeMax - waveParams.waveSizeMin) + waveParams.waveSizeMin;
	}
	
	private int calcEnemiesOnBattleField(int playersScore){
		return random.nextInt(waveParams.enemiesOnBattleFieldMax - waveParams.enemiesOnBattleFieldMin) + waveParams.enemiesOnBattleFieldMin;
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
