package com.me.swampmonster.models.slots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.Explosion;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.models.enemies.Enemy;

public abstract class Trap extends Slot{
	public Circle circle;
	public Sprite trapSprite;
	public Vector2 position;
	public boolean showEffect;
	public int lifeTimeMax;
	public Explosion explosion;
	
	public ParticleEffect effect;
	
	public void catchEnemy(Enemy enemy){};

	@Override
	public void execute (Player player){
		this.lifeTime = lifeTimeMax;
//		effect = new ParticleEffect();
		player.trap = this;
		player.trapTimer = 0;
		player.trap.position = new Vector2(player.position.x, player.position.y);
		player.trap.circle.x = player.trap.position.x + trapSprite.getWidth()/2;
		player.trap.circle.y = player.trap.position.y + trapSprite.getHeight()/2; 
		System.out.println("TRAP: position is " + player.trap.position + " lifeTime: " + player.trap.lifeTime + " trapTimer " + player.trapTimer);
	}

	@Override
	public abstract String getDescription();
}
