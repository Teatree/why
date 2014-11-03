package com.me.swampmonster.utils;

import java.util.Random;
import java.util.Stack;

import com.me.swampmonster.models.Wave;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.models.enemies.EnemySofa;

public class WaveGenerator {
	EnemyGenerator enemyGenerator = new EnemyGenerator();
	WaveParams waveParams;
	private Random random = new Random();
	
	private enum WaveParams{
		//                     *-et*+et*-tt*+tt*-ws**+ws*-tw*+tw**-eb*+eb**rate***perd*-w**+w*
		p0_500					(0,  4,  0,  2,  12,  18,  0,  1,  6,  7,  12.0f,  50,  2,  3),
		p0_500_Elite			(0,  1,  1,  2,  7,   13,  7,  13, 6,  7,  12.0f,  50,  2,  3),
		p0_500_A				(4,  5,  0,  2,  10,  17,  0,  1,  6,  7,  12.0f,  50,  2,  3),
		//                     *-et*+et*-tt*+tt*-ws**+ws*-tw*+tw***-eb*+eb**rate***perd*-w**+w*
		p500_1000				(0,  4,  0,  2,  15,  23,  8,  16,  6,  7,  12.0f,  50,  2,  3),
		p500_1000_Elite			(0,  1,  1,  2,  10,  17,  10, 17,  6,  7,  12.0f,  50,  2,  3),
		p500_1000_A				(4,  5,  0,  2,  15,  23,  0,  1,   6,  7,  12.0f,  50,  2,  3),
		//     				   *-et*+et*-tt*+tt*-ws**+ws**-tw*+tw**-eb**+eb***rate**perd*-w**+w*
		p1000_3000				(0,  2,  0,  3,  20,  30,  12, 25,  11,  18,  9.5f,  50,  3,  4),
		p1000_3000_Elite		(0,  1,  1,  3,  14,  22,  14, 22,  11,  18,  9.5f,  50,  3,  4),
		p1000_3000_A			(4,  5,  0,  3,  10,  20,  0,  5,  11,  18,  9.5f,  50,  3,  4),
		//     				   *-et*+et*-tt*+tt*-ws**+ws*-tw**+tw**-eb**+eb***rate**perd*-w**+w*
		p3000_5000				(0,  3,  0,  4,  18,  30,  3,  11,  15,  25,  6.0f,  50,  3,  5),
		p3000_5000_Elite		(0,  3,  1,  4,  18,  30,  18, 30,  15,  25,  6.0f,  50,  3,  5),
		p3000_5000_A			(4,  5,  0,  4,  18,  30,  3,  11,  15,  25,  6.0f,  50,  3,  5),
//     						   *-et*+et*-tt*+tt*-ws**+ws*-tw**+tw**-eb**+eb***rate**perd*-w**+w*
		p5000_7000				(0,  4,  0,  5,  25,  32,  8,  20,  15,  25,  6.0f,  50,  3,  6),
		p5000_7000_Elite		(0,  3,  1,  5,  25,  32,  25, 35,  15,  25,  6.0f,  50,  3,  6),
		p5000_7000_A			(4,  5,  0,  5,  25,  32,  8,  20,  15,  25,  6.0f,  50,  3,  6),
//		  					   *-et*+et*-tt*+tt*-ws**+ws**-tw**+tw**-eb**+eb***rate**perd*-w**+w*	
		p7000_9000				(0,  4,  0,  5,  32,  48,  14,  26,  15,  25,  3.0f,  50,  4,  7),
		p7000_9000_Elite		(0,  3,  1,  5,  32,  48,  32,  48,  15,  25,  3.0f,  50,  4,  7),
		p7000_9000_A			(4,  5,  0,  5,  40,  40,  14,  26,  15,  25,  3.0f,  50,  4,  7),
//							   *-et*+et*-tt*+tt*-ws**+ws**-tw**+tw**-eb**+eb***rate**perd*-w**+w*	
		p9000_0					(0,  4,  0,  5,  40,  52,  18,  29,  20,  35,  1.0f,  50,  5,  7),
		p9000_0_Elite			(0,  3,  1,  5,  40,  52,  40,  52,  20,  35,  1.0f,  50,  5,  7),
		p9000_0_A				(4,  5,  0,  5,  40,  52,  18,  29,  20,  35,  1.0f,  50,  5,  7);
			
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
		
		int sofaCounter = 0;
		double maxSofaAmount = 2;
//		double maxSofaAmount = waveSize*0.02;
		
		for (int i = 0; i < waveSize - maxAmountOfToughGuysInAWave; i++) {
			while (tempEnemies[i] == null) {
				Enemy enemy = enemyGenerator.getPlainEnemy(minEnemy, maxEnemy);
				if (enemy.getClass().equals(EnemySofa.class)) {
					if (sofaCounter < maxSofaAmount) {
						sofaCounter++;
						tempEnemies[i] = enemy;
						break;
					}
				} else {
					tempEnemies[i] = enemy;
					break;
				}
			}
//			System.out.println("maxSofaAmount " + maxSofaAmount);
//			System.out.println("sofaCoutner " + sofaCounter);
//			System.out.println("temps " + tempEnemies);
		}

		
		for (int i = waveSize - maxAmountOfToughGuysInAWave; i < waveSize; i++) {
			while (tempEnemies[i] == null) {
				Enemy enemy = enemyGenerator.getToughEnemy(minEnemy, maxEnemy,
						minTough, maxTough);
				if (enemy.getClass().equals(EnemySofa.class)) {
					if (sofaCounter < maxSofaAmount) {
						sofaCounter++;
						tempEnemies[i] = enemy;
						break;
					} 
				} else {
					tempEnemies[i] = enemy;
					break;
				}
			}
		}
		GeneralUtils.shuffle(tempEnemies);
		for (Enemy e : tempEnemies){
			wave.enemies.push(e);
		}
		wave.enemiesOnBattleField = calcEnemiesOnBattleField(playersScore);
		return wave;
	}
	
	

	private void setWaveParams(int playersScore, boolean hasAtmosphere, boolean isElite) {
		if(playersScore>=0 && playersScore<500){
			if (hasAtmosphere){
				waveParams = WaveParams.p0_500_A;
			} else if (isElite){
				waveParams = WaveParams.p0_500_Elite;
			} else {
				waveParams = WaveParams.p0_500;
			}
		}
		if(playersScore>=500 && playersScore<1000){
			if (hasAtmosphere){
				waveParams = WaveParams.p500_1000_A;
			} else if (isElite){
				waveParams = WaveParams.p500_1000_Elite;
			} else {
				waveParams = WaveParams.p500_1000;
			}
		}
		if(playersScore>=1000 && playersScore<3000){
			if (hasAtmosphere){
				waveParams = WaveParams.p1000_3000_A;
			} else if (isElite){
				waveParams = WaveParams.p1000_3000_Elite;
			} else {
				waveParams = WaveParams.p1000_3000;
			}
		}
		if(playersScore>=3000 && playersScore<5000){
			if (hasAtmosphere) {
				waveParams = WaveParams.p3000_5000_A;
			} else if (isElite) {
				waveParams = WaveParams.p3000_5000_Elite;
			} else {
				waveParams = WaveParams.p3000_5000;
			}
		}
		if(playersScore>=5000 && playersScore<7000){
			if (hasAtmosphere){
				waveParams = WaveParams.p5000_7000_A;
			} else if (isElite){
				waveParams = WaveParams.p5000_7000_Elite;
			} else {
				waveParams = WaveParams.p5000_7000;
			}
		}
		if(playersScore>=7000 && playersScore<9000){
			if (hasAtmosphere){
				waveParams = WaveParams.p7000_9000_A;
			} else if (isElite){
				waveParams = WaveParams.p7000_9000_Elite;
			} else {
				waveParams = WaveParams.p7000_9000;
			}
		}
		if(playersScore>=9000){
			if (hasAtmosphere){
				waveParams = WaveParams.p9000_0_A;
			} else if (isElite){
				waveParams = WaveParams.p9000_0_Elite;
			} else {
				waveParams = WaveParams.p9000_0;
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
