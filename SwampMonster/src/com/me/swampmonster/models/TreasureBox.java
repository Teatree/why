package com.me.swampmonster.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.animations.AnimationControl;
import com.me.swampmonster.models.AbstractGameObject.State;
import com.me.swampmonster.utils.Assets;
import com.me.swampmonster.utils.MisterItemSpawner;

public class TreasureBox extends Prop{
	public TreasureBox(Vector2 position){
		this.position = position;
		state = State.STANDARD;
		
		despawningCounter = 45;
		
		sizeW = 16;
		sizeH = 32;
		
		animationsStandard.put(State.STANDARD, new AnimationControl(Assets.manager.get(Assets.propTreasure), 4, 2, 3.6f));
		animationsStandard.put(State.DESPAWNING, new AnimationControl(Assets.manager.get(Assets.propTreasure), 4, 2, 3.6f));
		
		sprite = new Sprite(animationsStandard.get(state).getCurrentFrame());
	}
	
	public void toDoSomething(AbstractGameObject abs){
		L1.items.add(MisterItemSpawner.spawnPropsItem(L1.player, this));
	}

	public void toDoSomething() {
		L1.items.add(MisterItemSpawner.spawnPropsItem(L1.player, this));
		
	}

}
