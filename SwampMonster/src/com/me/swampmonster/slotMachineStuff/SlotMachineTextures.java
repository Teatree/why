package com.me.swampmonster.slotMachineStuff;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.me.swampmonster.models.Player;
import com.me.swampmonster.utils.AssetsMainManager;

public class SlotMachineTextures extends Group {
	private BitmapFont font;
	private Player p;

	public SlotMachineTextures(Player player) {
		this.p = player;
		font = AssetsMainManager.manager.get(AssetsMainManager.font);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(AssetsMainManager.manager
				.get(AssetsMainManager.slotMachineCase), 144, 112);
		batch.draw(AssetsMainManager.manager
				.get(AssetsMainManager.slotMachineNextButton), 535, 125,
				0.10f * Gdx.graphics.getWidth(), 0.20f * Gdx.graphics
						.getHeight());
		font.setColor(Color.BLACK);
		font.setScale(0.5f, 0.5f);
		font.draw(batch, "MaxHP: " + p.getMaxHealth(), 284, 215);
		font.draw(batch, "MaxO2: " + p.maxOxygen, 284, 200);
		font.draw(batch, "Damage: " + p.damage, 284, 185);
		font.draw(batch, "AS: " + p.shotCoolDown, 284, 170);
		font.draw(batch, "Score: " + p.points, 156, 338);
	}
}
