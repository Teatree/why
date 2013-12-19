package com.playground;

public class Node implements Comparable<Node>{
	public int x;
	public int y;
	public float g;
	public float h;
	public float f;
	
	public Node parentNode;
	
	public Node(int x, int y, Node parentNode) {
		this.x = x;
		this.y = y;
		
		this.parentNode = parentNode;
	}

	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
