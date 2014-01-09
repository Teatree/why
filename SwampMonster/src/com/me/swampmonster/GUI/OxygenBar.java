package com.me.swampmonster.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.me.swampmonster.models.AbstractGameObject;

public class OxygenBar extends AbstractGameObject{
	
	public OxygenBar(){
		sprite = new Sprite(new Texture("data/oxygenBar.png"));
		rectanlge = new Rectangle();
	}
	
}
