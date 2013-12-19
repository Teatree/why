package com.me.swampmonster.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationControl {
	private Animation animation;
	private Texture playerTexture;
	private TextureRegion[] frames;
	private TextureRegion currentFrame;
	private float stateTime;
	private int multiplier;

	public AnimationControl(String fileName, int col, int row, int multiplier) {
		this.multiplier = multiplier;
		playerTexture = new Texture(Gdx.files.internal(fileName));
		TextureRegion[][] tmp = TextureRegion
				.split(playerTexture, playerTexture.getWidth() / col,
						playerTexture.getHeight() / row);
		frames = new TextureRegion[col * row];

		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				frames[index++] = tmp[i][j];
			}
		}

		animation = new Animation(1, frames);
		stateTime = 0f;
		currentFrame = animation.getKeyFrame(0);
	}
	
	public TextureRegion animate(int i){
		if (stateTime < 1) {
			stateTime += Gdx.graphics.getDeltaTime();
		} else {
			stateTime = 0;
		}
		
		currentFrame = animation.getKeyFrame(i + stateTime*multiplier);
		return currentFrame;
	}
	
	//Does a complex animation...
	//parameters are self explanatory: i is start frame; timeAdjust is comparison; personalMultiplier is a multiplier; and deltaTime is our time.
	//optimal set, so far are: 64, 2, 8, 0.01f for two rows, and 64, 1, 8, 0.005f for one
	public TextureRegion doComplexAnimation(int i, int timeAdjust, int personalMultiplier, float deltaTime){
		if (stateTime < timeAdjust) {
			stateTime += deltaTime;
		} else {
			stateTime = 0;
		}
		
		currentFrame = animation.getKeyFrame(i + stateTime*personalMultiplier);
		return currentFrame;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(TextureRegion currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	
}