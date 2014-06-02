package com.me.swampmonster.models;

import java.util.Stack;

import com.me.swampmonster.models.enemies.Enemy;

public class Wave {
	public Stack<Enemy> enemies;
	public int enemiesOnBattleField;
	public int pendingPeriod;
	public float rate;
}
