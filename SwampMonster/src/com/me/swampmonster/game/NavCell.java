package com.me.swampmonster.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.BinaryHeap;

public class NavCell extends BinaryHeap.Node {
	public Cell cell;
	public NavCell left, right, down, up;
	public int x;
	public int y;
	public int distance;
	public NavCell parent;
	public boolean blocked = false;
	
	public NavCell(Cell cell, int x, int y) {
		super(0.0f);
		this.cell = cell;
		this.x = x;
		this.y = y;
	}
	public void setDirection(NavCell l, NavCell r, NavCell u, NavCell d){
		left = l;
		right = r;
		up = u;
		down = d;
	}
	
	public NavCell getUp(){
		return up;
	}
	public NavCell getDown(){
		return down;
	}
	public NavCell getLeft(){
		return left;
	}
	public NavCell getRight(){
		return right;
	}
	public NavCell getParent() {
		return parent;
	}
	public void setParent(NavCell parent) {
		this.parent = parent;
	}
	public void calcDistance(NavCell target){
		distance = Math.abs(x - target.x) + Math.abs(y - target.y); 
	}
	public NavCell traceBack(){
//		onPath = true;
		return parent;
	}
	public boolean isBlocked(){
		
		return blocked;
	}
}