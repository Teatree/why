package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.enemies.Enemy;
import com.me.swampmonster.utils.Assets;

public class ToxicPuddle extends Prop{

	public ToxicPuddle (Vector2 position){
		this.position = position;
		state = State.STANDARD;
		
		sizeW = 32;
		sizeH = 16;
		
		despawningCounter = 60;
		
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.propToxicPuddle), 4, 1, 3.6f));
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
	}
	
	public void toDoSomething(AbstractGameObject abstractGameObject){
		if (abstractGameObject instanceof Enemy || abstractGameObject instanceof Player){
			abstractGameObject.setNegativeEffect(NegativeEffects.POISONED);
//			try {
//				Method method = abstractGameObject.getClass().getMethod("setNegativeEffect", NegativeEffects.class);
//				method.invoke(null, NegativeEffects.POISONED);
//			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				e.printStackTrace();
//			}
		}
	}
}
