package com.me.swampmonster.utils;

import java.util.Random;
import java.util.Stack;

import com.me.swampmonster.models.Wave;
import com.me.swampmonster.models.enemies.Enemy;

public class WaveGenerator {
	EnemyGenerator enemyGenerator = new EnemyGenerator();
	WaveParams waveParams;
	private Random random = new Random();
	
	private enum WaveParams{
		
		p0_500(1, 2, 1, 5, 10, 12, 5, 7, 5, 6, 3.0f, 150, 3, 4),
		p0_500_Elite(1, 2, 1, 5, 10, 12, 5, 7, 5, 6, 3.0f, 150, 3, 4),
		p0_500_A(1, 2, 1, 5, 10, 12, 5, 7, 5, 6, 3.0f, 150, 3, 4),
		p500_1000(1, 3, 0, 7, 5, 14, 6, 8, 9, 11, 2.5f, 100, 6, 8),
		p1000_2000(0, 3, 0, 5, 14, 16, 6, 8, 10, 12, 2.0f, 150, 5, 6),
		p2000_4000(0, 4, 0, 5, 10, 12, 6, 9, 8, 10, 1.0f, 100, 5, 7);
			
		public final int minEnemyType;
		public final int maxEnemyType;
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
		
		
	private WaveParams(int minEnemyType, int maxEnemyType, int minTough,
				int maxTough, int waveSizeMin, int waveSizeMax,
				int amountOfToughGuysInAWaveMin,
				int amountOfToughGuysInAWaveMax, int enemiesOnBattleFieldMin,
				int enemiesOnBattleFieldMax, float rate, int pendingPeriod, int waveLimitMin, int waveLimitMax) {
			this.minEnemyType = minEnemyType;
			this.maxEnemyType = maxEnemyType;
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

	public int getWavesAmount(int playersScore, boolean hasAtmosphere, boolean isElite){
		setWaveParams(playersScore, hasAtmosphere, isElite);	
		return random.nextInt(waveParams.waveLimitMax - waveParams.waveLimitMin) + waveParams.waveLimitMin;
	}
	
	public Wave generateWave(int playersScore, boolean hasAtmosphere, boolean isElite){
		Wave wave = new Wave();
		
		setWaveParams(playersScore, hasAtmosphere, isElite);	
		
		wave.enemies = new Stack<Enemy>();
		wave.pendingPeriod = waveParams.pendingPeriod;
		wave.rate = waveParams.rate;
		int maxEnemy = waveParams.maxEnemyType;
		int minEnemy = waveParams.minEnemyType;
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
		GeneralUtils.shuffle(tempEnemies);
		for (Enemy e : tempEnemies){
			wave.enemies.push(e);
		}
		wave.enemiesOnBattleField = calcEnemiesOnBattleField(playersScore);
		return wave;
	}
	
	

	private void setWaveParams(int playersScore, boolean hasAtmosphere, boolean isElite) {
		if(playersScore>=0 && playersScore<100){
			if (hasAtmosphere){
				waveParams = WaveParams.p0_500_A;
			} else if (isElite){
				waveParams = WaveParams.p0_500_Elite;
			} else {
				waveParams = WaveParams.p0_500;
			}
		}
		if(playersScore>100 && playersScore<1000 && !hasAtmosphere && !isElite){
			if (hasAtmosphere){
				waveParams = WaveParams.p0_500_A;
			} else if (isElite){
				waveParams = WaveParams.p0_500_Elite;
			} else {
				waveParams = WaveParams.p500_1000;
			}
		}
		if(playersScore>1000 && playersScore<2000 && !hasAtmosphere && !isElite){
			waveParams = WaveParams.p1000_2000;
		}
		if(playersScore>2000 && !hasAtmosphere && !isElite){
			if (hasAtmosphere){
				waveParams = WaveParams.p0_500_A;
			} else if (isElite){
				waveParams = WaveParams.p0_500_Elite;
			} else {
				waveParams = WaveParams.p2000_4000;
			}
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
}
