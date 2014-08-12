package com.me.swampmonster.GUI;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class Croshair extends AbstractGameObject{
	
	private boolean aiming = false;
	private float getRot;
	public TextureRegion[][] pointerFrames;
	
	public Croshair(Vector2 position){
		this.position = position;
		
		pointerFrames = TextureRegion.split((Assets.manager.get(Assets.PointerHead)), 256, 32);
		sprite = new Sprite(pointerFrames[0][0]);
		
		aiming = false;
	}
	public void update(Player player, Vector2 point, Vector3 V3point){
		getRot = player.getRotation(player.shotDir)*57.29f;
		
		position.x = player.position.x-122;
		position.y = player.position.y;
		
//		player.shotDir.x = (player.getPosition().x + sprite.getWidth() / 2) * 2 - V3point.x;
//		player.shotDir.y = (player.getPosition().y + sprite.getHeight() / 2) * 2 - V3point.y;
		
		
		
		double aimingLength = Math.sqrt(Math.pow(player.aimLineHead.x - player.position.x, 2)+Math.pow(player.aimLineHead.y - player.position.y, 2));
		if(aimingLength<50){
			sprite = new Sprite(pointerFrames[0][0]);
		}else if(aimingLength < 100){
			sprite = new Sprite(pointerFrames[1][0]);
		}else{
			sprite = new Sprite(pointerFrames[2][0]);
		}
		sprite.setRotation(getRot);
//		int pointsCount = (int) (pointerLength/(pointerMiddle.getHeight()+4));
//		int cunter = 0;
//		pointers.clear();
//		if (aiming) {
//			while (cunter < pointsCount) {
//				double c = (pointerMiddle.getHeight() + 4) * cunter;
//				double a = c * Math.cos(Math.toRadians(getRot));
//				Sprite temp = new Sprite(pointerMiddle);
//				temp.setRotation(getRot);
//				double x = player.position.x - a;
//				double lineRotation = getRot;
////				if (Math.abs(getRot) <= 71 || Math.abs(getRot) >= 113) {
//					lineRotation = getRot;
//					float tempY = (float) (Math.tan(Math.toRadians(lineRotation))
//							* (x - this.position.x) + this.position.y);
//					temp.setPosition((float) x,
//							tempY);
////				} else {
////					 if (getRot >= 86 && getRot <= 90){
////					 lineRotation = 85;
////					 } else if (getRot > 90 && getRot <= 93){
////					 lineRotation = 94;
////					 } else if (getRot > -93 && getRot <= -90){
////					 lineRotation = -94;
////					 } else if (getRot <= -86 && getRot > -90){
////					 lineRotation = -85;
////					 }
////					lineRotation = getRot;
////					temp.setPosition((float) x,
////							(float) (Math.tan(Math.toRadians(lineRotation))
////									* (x - this.position.x) + this.position.y)/10);
////					temp.setPosition((float) x, (float) (this.position.y - c));
////				}
//				pointers.add(temp);
//				cunter++;
//			}
//		}
		
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
