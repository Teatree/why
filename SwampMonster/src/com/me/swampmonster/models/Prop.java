package com.me.swampmonster.models;


public abstract class Prop extends AbstractGameObject{

	public int despawningCounter;
	public int sizeW;
	public int sizeH;
	
	public abstract void toDoSomething(AbstractGameObject abs);
	
	public void update(){
		if(state == State.STANDARD){
			currentFrame = animationsStandard.get(state).animate(0);
			sprite.setRegion(currentFrame);
		}
		
		if(state == State.DESPAWNING){
			if(despawningCounter > 0){
				despawningCounter--;
				currentFrame = animationsStandard.get(state).animate(5);
				sprite.setRegion(currentFrame);
			}else if(despawningCounter == 0){
				state = State.DEAD;
			}
			
		}
		
		sprite.setSize(sizeW, sizeH);
	}
}
