package com.me.swampmonster.AI;

import com.badlogic.gdx.math.Vector2;

public class Node implements Comparable<Node>{
	public int x;
	public int y;
	public Vector2 position;
	public float g;
	public float h;
	public float f;
	
	public Node parentNode;
	
	public Node(int x, int y, Node parentNode) {
		this.x = x;
		this.y = y;
		this.g = Float.POSITIVE_INFINITY;
		this.h = Float.POSITIVE_INFINITY;
		
		this.parentNode = parentNode;
	}

	@Override
	public int compareTo(Node o) {
		if (this.f < o.f){
			return -1;
		}else if (this.f == o.f){
			return 0;
		} else {
			return 1;
		}
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}
	

}
