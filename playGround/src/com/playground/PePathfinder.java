package com.playground;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
 
public class PePathfinder {
 
	private PriorityQueue<Node> openList;
	private Set<Node> closedList;
	
	private TiledMap tiledMap;
	private TiledMapTileLayer nodeLayer;
	
	private int mapWidth;
	private int mapHeight;
	
	private Node[][] nodes;
	private Node startingNode;
	private Node targetNode;
	
	public PePathfinder(TiledMap tiledMap) {
		this.tiledMap = tiledMap;
		
		openList = new PriorityQueue<Node>();
		closedList = new HashSet<Node>();
		
		nodeLayer = (TiledMapTileLayer) tiledMap.getLayers().get("olive");
		
		mapWidth = nodeLayer.getWidth();
		mapHeight = nodeLayer.getHeight();
		
		nodes = new Node[mapWidth][mapHeight];
		loadNodes();
	}
	
	public void setMap(TiledMap newMap) {
		tiledMap = newMap;
		loadNodes();
	}
	
	private void loadNodes() {
		for (int x = 0; x < mapWidth; ++x) {
			for (int y = 0; y < mapHeight; ++y) {
				nodes[x][y] = new Node(x, y, null);
			}
		}
	}
	
	public boolean findPath(Vector2 startingPosition, Vector2 targetPosition) {
		loadNodes();
		
		openList = new PriorityQueue<Node>();
		closedList = new HashSet<Node>();
		
		startingNode = nodes[(int) startingPosition.x][(int) startingPosition.y];
		targetNode = nodes[(int) targetPosition.x][(int) targetPosition.y];
		
		Node[][] navigatedNodes = new Node[mapWidth][mapHeight];
		
		startingNode.g = 0;
		startingNode.h = distanceToNode(startingNode, targetNode);
		startingNode.f = startingNode.g + startingNode.h;
		startingNode.parentNode = null;
		System.out.println(startingNode.h);
		
		openList.add(startingNode);
		
		while (openList.size() > 0) {
			Node currentNode = openList.poll();
			
			if (currentNode.equals(targetNode)) {
				reconstructPath(navigatedNodes, currentNode);
				System.out.println("done!");
				return true;
			}
			
			openList.remove(currentNode);
			closedList.add(currentNode);
			
			for (Node neighborNode : getNeighborNodes(currentNode)) {
				float tentativeGScore = currentNode.g + distanceToNode(currentNode, neighborNode);
				
				if (closedList.contains(neighborNode) && tentativeGScore >= neighborNode.g) {
					continue;
				}
				
				if (neighborNode.g == 0 || tentativeGScore < neighborNode.g) {
					navigatedNodes[neighborNode.x][neighborNode.y] = currentNode;
					neighborNode.g = tentativeGScore;
					neighborNode.f = neighborNode.g + distanceToNode(neighborNode, targetNode);
					
					if (!openList.contains(neighborNode)) {
						System.out.println("the OpenList does not contain neighborNode");
						openList.add(neighborNode);
					}
				}
			}
		}
		
		return false;
	}
	
	private void reconstructPath(Node[][] navigatedNodes, Node currentNode) {
		
	}
	
	private Node[] getNeighborNodes(Node parentNode) {
		Node[] neighborNodes = new Node[4];
		
		neighborNodes[0] = nodes[parentNode.x][parentNode.y + 1]; // North
		neighborNodes[1] = nodes[parentNode.x][parentNode.y - 1]; // South
		neighborNodes[2] = nodes[parentNode.x + 1][parentNode.y]; // East
		neighborNodes[3] = nodes[parentNode.x - 1][parentNode.y]; // West
		
		return neighborNodes; 
	}
	
	private int distanceToNode(Node nodeA, Node nodeB) {
		return Math.abs(nodeA.x - nodeB.x) + Math.abs(nodeA.y - nodeB.y);
	}
	
	private boolean nodeIsWalkable(int x, int y) {
		if (nodeLayer.getCell(x, y) == null) {
			return true;
		}
		
		return false;
	}
	
}