package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.utils.Assets;

public class PlasmaShield extends AbstractGameObject{
	
	public static Integer level;
	public int lifeTime;
	public int spawnTime;
	public AnimationControl animControl;
	
	public PlasmaShield(Vector2 pos){
		state = State.SPAWNING;
		animControl = new AnimationControl(Assets.manager.get(Assets.plasmaShield), 4, 2, 6.9f);
		position = new Vector2(pos.x, pos.y);
		sprite = new Sprite(animControl.getCertainFrame(0));
		sprite.setSize(256, 256);
		sprite.setX(position.x-sprite.getWidth()/2);
		sprite.setY(position.y-sprite.getHeight()/2+18);
		circle = new Circle();
		circle.radius = 108;
		circle.x = pos.x;
		circle.y = pos.y;
	}
	
	public void update(){
		lifeTime--;
//		System.out.println("state: " + state);
		if(lifeTime>spawnTime){
			state = State.SPAWNING;
		}
		if(lifeTime<spawnTime && lifeTime>50){
			state = State.STANDARD;
		}
		if(lifeTime<50){
			state = State.DESPAWNING;
		}
		if(lifeTime==0){
			state = State.DEAD;
		}
		
		if(state == State.SPAWNING){
			animControl.doComplexAnimation(0, 1f, 0.02f, Animation.PlayMode.NORMAL);
			sprite.setRegion(animControl.getCurrentFrame());
		}
		if(state == State.STANDARD){
			animControl.doComplexAnimation(6, 1f, 0.02f, Animation.PlayMode.NORMAL);
			sprite.setRegion(animControl.getCurrentFrame());
		}
	}
}
