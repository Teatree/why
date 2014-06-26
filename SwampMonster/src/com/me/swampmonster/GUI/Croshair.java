package com.me.swampmonster.GUI;


import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class Croshair extends AbstractGameObject{
	
	private boolean aiming = false;
	public Sprite pointerMiddle;
	public List<Sprite> pointers;
	private float getRot;
	
	public Croshair(Vector2 position){
		this.position = position;
		
		sprite = new Sprite(Assets.manager.get(Assets.PointerHead));
		pointerMiddle = new Sprite(Assets.manager.get(Assets.PointerMiddle));
		pointers = new ArrayList<Sprite>();
		
		aiming = false;
	}
	public void update(Player player, Vector2 point, Vector3 V3point){
		getRot = player.getRotation()*57.29f;
		
		position.x = player.aimLineHead.x;
		position.y = player.aimLineHead.y;
		
		player.shotDir.x = (player.getPosition().x + sprite.getWidth() / 2) * 2 - V3point.x;
		player.shotDir.y = (player.getPosition().y + sprite.getHeight() / 2) * 2 - V3point.y;
		
		sprite.setRotation(getRot);
		
		double pointerLength = Math.sqrt(Math.pow(this.position.x - player.position.x, 2)+Math.pow(this.position.y - player.position.y, 2));
		int pointsCount = (int) (pointerLength/(pointerMiddle.getHeight()+4));
		int cunter = 0;
		pointers.clear();
		if (aiming){
		while(cunter < pointsCount){
			double c = (pointerMiddle.getHeight()+4)*cunter;
			double a = c*Math.cos(Math.toRadians(getRot));
			Sprite temp = new Sprite(pointerMiddle);
			temp.setRotation(getRot);
			double x = player.position.x - a;
			double lineRotation=  getRot;
			if (Math.abs(getRot) <= 71 || Math.abs(getRot) >= 113){
				lineRotation = getRot;
			System.out.println("Roatation = " + getRot + ", y =  " + (Math.tan(Math.toRadians(lineRotation))*(x-this.position.x/*TheController.touchPos.x*/)+this.position.y/*TheController.touchPos.y*/));
			temp.setPosition((float)x, (float) (Math.tan(Math.toRadians(lineRotation))*(x-this.position.x)+this.position.y));
			} else {
//				if (getRot >= 86 && getRot <= 90){
//					lineRotation = 85;
//				} else if (getRot > 90 && getRot <= 93){
//					lineRotation = 94;
//				} else if (getRot > -93 && getRot <= -90){
//					lineRotation = -94;
//				} else if (getRot <= -86 && getRot > -90){
//					lineRotation = -85;
//				}
				temp.setPosition((float)x, (float)(this.position.y - c));
			}
			pointers.add(temp);
			cunter++;
		}
		}
		
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
