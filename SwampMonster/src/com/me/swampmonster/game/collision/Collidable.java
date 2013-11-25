package com.me.swampmonster.game.collision;

import com.me.swampmonster.models.AbstractGameObject;

public interface Collidable {
	
	public void doCollide(AbstractGameObject abstractGameObject);
	
}
