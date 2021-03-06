package com.me.swampmonster.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationControl {
	private Animation animation;
	private Texture playerTexture;
	private TextureRegion[] frames;
	private TextureRegion currentFrame;
	public float stateTime;
	public float stateTimeDoComplex;
	public boolean animating;
	public boolean animating2 = true;
	private float multiplier;
	

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
//		animating2 = true;

		animation = new Animation(1, frames);
		stateTime = 0f;
		stateTimeDoComplex = 0f;
		currentFrame = animation.getKeyFrame(0);
	}
	
	public AnimationControl(Texture t, int col, int row, float multiplier) {
		this.multiplier = multiplier;
		TextureRegion[][] tmp = TextureRegion
				.split(t, t.getWidth() / col,
						t.getHeight() / row);
		frames = new TextureRegion[col * row];
		
		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		
		animation = new Animation(1, frames);
		stateTime = 0f;
		stateTimeDoComplex = 0f;
		currentFrame = animation.getKeyFrame(0);
	}
	
	public TextureRegion animate(int i){
		
		if (stateTime < 1) {
			stateTime += Gdx.graphics.getDeltaTime()/1.5f;
		} else {
			animating2 = false;
			stateTime = 0;
		}
		
		currentFrame = animation.getKeyFrame(i + stateTime*multiplier);
		return currentFrame;
		
	}
	
	//Does a complex animation...
	//Comparator is to adjust the time spent moving forward in the array, standard = 1f;
	//Adjusts the speed at which the frames are changing, standard = approximately 0.016f;
	public TextureRegion doComplexAnimation(int i, float Comparator, float speedAdjust, PlayMode playType){
		if (stateTimeDoComplex < Comparator) {
			stateTimeDoComplex += speedAdjust;
//			System.out.println(Comparator);
		} else {
			stateTimeDoComplex = 0;
		}
		Array<TextureRegion> frames2 = new Array<TextureRegion>(frames);
		animation = new Animation(1, frames2, playType);
		
		currentFrame = animation.getKeyFrame(i + stateTimeDoComplex*multiplier);
//		System.out.println("complex animation: " + i + stateTimeDoComplex*multiplier);
		
		return currentFrame;
	}
	
	public TextureRegion getCertainFrame(int i){
		currentFrame = animation.getKeyFrame(i);
		return currentFrame;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(TextureRegion currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	
}
