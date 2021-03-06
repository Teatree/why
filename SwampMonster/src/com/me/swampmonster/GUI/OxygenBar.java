package com.me.swampmonster.GUI;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.AbstractGameObject;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.Assets;

public class OxygenBar extends AbstractGameObject{
	
	private static final int OxygenBarYPosition = 422;
	private Sprite spriteTail;
	private Sprite spriteMiddle;
	private Sprite spriteHead;
	private Sprite spriteIcon;
	public List<Sprite> sprites;
	
	public OxygenBar(Player player){
		spriteIcon = new Sprite(Assets.manager.get(Assets.OxygenBarIcon));
		spriteTail = new Sprite(Assets.manager.get(Assets.OxygenBarTail));
		spriteMiddle = new Sprite(Assets.manager.get(Assets.OxygenBarMiddle));
		spriteHead = new Sprite(Assets.manager.get(Assets.OxygenBarHead));
		sprites = new ArrayList<Sprite>();
		position = new Vector2();
		position.x = 0;
		position.y = 448;
		setCasingPos(Player.maxOxygen);
	}
	
	private void setCasingPos(float maxOxygen){
		int cunter = 0;
		spriteIcon.setPosition(0, OxygenBarYPosition);
		sprites.add(spriteIcon);
		spriteTail.setPosition(16, OxygenBarYPosition);
		sprites.add(spriteTail);
		int position = 16;
		while(cunter < (maxOxygen/16)-2){
			Sprite temp = new Sprite(spriteMiddle);
			position += spriteMiddle.getWidth();
			temp.setPosition(position, OxygenBarYPosition);
			sprites.add(temp);
			cunter++;
		}
		spriteHead.setPosition(position+16, OxygenBarYPosition);
		sprites.add(spriteHead);
	}
}
