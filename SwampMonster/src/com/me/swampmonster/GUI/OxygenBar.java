package com.me.swampmonster.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject;

public class OxygenBar extends AbstractGameObject{
	
	public OxygenBar(){
		sprite = new Sprite(new Texture("data/oxygenBar.png"));
		position = new Vector2();
		position.x = 0;
		position.y = 416;
		rectanlge = new Rectangle();
	}
	
}
