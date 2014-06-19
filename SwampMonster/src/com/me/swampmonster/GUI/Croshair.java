package com.me.swampmonster.GUI;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.utils.AssetsMainManager;

public class Croshair extends AbstractGameObject{
	
	private boolean aiming = false;
	
	public Croshair(Vector2 position){
		this.position = position;
		
		sprite = new Sprite(AssetsMainManager.manager.get(AssetsMainManager.PointerHead));
		aiming = false;
	}
	public void update(AbstractGameObject player, Vector2 point, Vector3 V3point, float rot){
		position.x = player.getPosition().x;
		position.y = player.getPosition().y;
		
		sprite.setRotation(rot*57.29f);
		
//		// System.out.println("aiming " + aiming);
		if(doesIntersect(new Vector2(player.circle.x, player.circle.y), player.circle.radius*2, new Vector2(V3point.x, V3point.y))
				&& !aiming && Gdx.input.isTouched()){
			player.state = State.GUNMOVEMENT;
			aiming = true;
		}else if(!doesIntersect(new Vector2(player.circle.x, player.circle.y), player.circle.radius*2, new Vector2(V3point.x, V3point.y))
				&& aiming && !Gdx.input.isTouched()){
			aiming = false;
		}
	}
	
	public boolean doesIntersect(Vector2 v2, float radius, Vector2 point){
		boolean questionMark;
		if(Intersector.intersectSegmentCircle(point, point, v2, radius*radius)){
			questionMark = true;
		}else{
			questionMark = false;
		}
		return questionMark;
	}
	
	public boolean isAiming() {
		return aiming;
	}
	public void setAiming(boolean aiming) {
		this.aiming = aiming;
	}
	
}
