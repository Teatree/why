package com.me.swampmonster.AI;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
 
public class Pathfinder {
 
	private PriorityQueue<Node> openList;
	private Set<Node> closedList;
	
	private TiledMap tiledMap;
	private TiledMapTileLayer nodeLayer;
	
	private int mapWidth;
	private int mapHeight;
	
	private Node[][] nodes;
	private Iterator<TiledMapTile> it;
	private Node startingNode;
	private Node targetNode;
	public Node[] path;
	
	public Pathfinder(TiledMap tiledMap) {
		this.tiledMap = tiledMap;
		
		openList = new PriorityQueue<Node>();
		closedList = new HashSet<Node>();
		
		nodeLayer = (TiledMapTileLayer) tiledMap.getLayers().get("background");
		
		mapWidth = nodeLayer.getWidth();
		mapHeight = nodeLayer.getHeight();
		
		path = new Node[99];
		nodes = new Node[mapWidth][mapHeight];
		loadNodes();
	}
	
	public void setMap(TiledMap newMap) {
		tiledMap = newMap;
		loadNodes();
	}
	
	private void loadNodes() {
		for (int x = 0; x < mapWidth; x++) {        // consider x++
			for (int y = 0; y < mapHeight; y++) {
				nodes[x][y] = new Node(x, y, null);
			}
		}
	}
	
	public boolean findPath(Vector2 startingPosition, Vector2 targetPosition) {
		loadNodes();
//		System.out.println("(findPath): working!");
		
		
		openList = new PriorityQueue<Node>();
		closedList = new HashSet<Node>();
		
		startingNode = getNodeAt(startingPosition.x, startingPosition.y);
		targetNode = getNodeAt(targetPosition.x, targetPosition.y);
		
		Node[][] navigatedNodes = new Node[mapWidth][mapHeight];
		
		startingNode.g = 0;
		startingNode.h = distanceToNode(startingNode, targetNode);
		startingNode.f = startingNode.g + startingNode.h;
		startingNode.parentNode = null;
		
		openList.add(startingNode);
		
		while (openList.size() > 0) {
			Node currentNode = openList.poll();
//			System.out.println("iteration");
			
//			System.out.println("CurrentNode! ==== " + currentNode);
//			System.out.println("TargetNode! ==== " + targetNode);
			
			if (currentNode.equals(targetNode) && currentNode != null) {
				Node node = currentNode.getParentNode();
				reconstructPath(navigatedNodes, currentNode);
				int cuntar = 0;
				while(node.getParentNode() != null && !node.getParentNode().equals(startingPosition)){
					path[cuntar] = node;
					node = node.getParentNode();
					cuntar++;
				}
//				System.out.println("(findPath): Found the path!");
				return true;
			}
			
			openList.remove(currentNode);
//			System.out.println("Removing " + currentNode + " From openList, now openList contains: " + openList.size());
			closedList.add(currentNode);
//			System.out.println("Adding " + currentNode + " To closedList, now closedList contains: " + closedList.size());
			
			for (Node neighborNode : getNeighborNodes(currentNode)) {
				float tentativeGScore = currentNode.g + distanceToNode(currentNode, targetNode);
				
				if (closedList.contains(neighborNode) && tentativeGScore >= neighborNode.g) {
					continue;
				}
				// wall check goes here !
				
				
				if (neighborNode.g == 0 || tentativeGScore < neighborNode.g) {
						navigatedNodes[neighborNode.x][neighborNode.y] = currentNode;
//						System.out.println("Assigning currentNode, now it's: " + currentNode);
						neighborNode.g = tentativeGScore;
						neighborNode.f = neighborNode.g + distanceToNode(neighborNode, targetNode);
						
						
						if (!openList.contains(neighborNode) && nodeIsWalkable(neighborNode.x, neighborNode.y)) {
							openList.add(neighborNode);
							neighborNode.setParentNode(currentNode);
//							System.out.println("Adding neighborNode to the OPEN_LIST");
						}
				}
			}
		}
		
		return false;
	}
	
	private void reconstructPath(Node[][] navigatedNodes, Node currentNode) {
		
	}
	
	private List<Node> getNeighborNodes(Node parentNode) {
		List<Node> neighborNodes = new ArrayList<Node>();
		
		if (parentNode.y + 1 < nodes[parentNode.x].length && getTileAt(nodes[parentNode.x][parentNode.y + 1].x , nodes[parentNode.x][parentNode.y + 1].y).getProperties().containsKey("walkable")){
			neighborNodes.add(nodes[parentNode.x][parentNode.y + 1]); // North
		}
		if (parentNode.y - 1 >= 0 && getTileAt(nodes[parentNode.x][parentNode.y - 1].x , nodes[parentNode.x][parentNode.y - 1].y ).getProperties().containsKey("walkable")){
			neighborNodes.add(nodes[parentNode.x][parentNode.y - 1]); // South
		}
		if (parentNode.x + 1 < nodes.length && getTileAt(nodes[parentNode.x + 1][parentNode.y].x, nodes[parentNode.x + 1][parentNode.y].y).getProperties().containsKey("walkable")){
			neighborNodes.add(nodes[parentNode.x + 1][parentNode.y]);// East
		}
		if (parentNode.x - 1 >= 0 && getTileAt(nodes[parentNode.x - 1][parentNode.y].x, nodes[parentNode.x - 1][parentNode.y].y).getProperties().containsKey("walkable")){
			neighborNodes.add(nodes[parentNode.x - 1][parentNode.y]); // West
		}
		return neighborNodes; 
	}
	
	private int distanceToNode(Node nodeA, Node nodeB) {
		return Math.abs(nodeA.x - nodeB.x) + Math.abs(nodeA.y - nodeB.y);
	}
	
	private boolean nodeIsWalkable(int x, int y) {
		if (nodeLayer.getCell(x, y) == null) {
			return false;
		}
		
		return true;
	}
	private Node getNodeAt(float x, float y) {
		int cellx = (int)x / (int)nodeLayer.getTileWidth();
		int celly = (int)y / (int)nodeLayer.getTileHeight();
//		System.out.println(cellx + " and " + celly);
		return getNodeAt(cellx, celly);
	}
	
	private Node getNodeAt(int x, int y) {
		if (x < 0 || x >= mapWidth) {
			return null;
		}
		if (y < 0 || y >= mapHeight) {
			return null;
		}
		return nodes[x][y];
	}
	
	private Cell getCellAt(float x, float y) {
		int cellX = (int)x / (int)nodeLayer.getTileWidth();
		int cellY = (int)y / (int)nodeLayer.getTileHeight();
		
		return nodeLayer.getCell(cellX, cellY);
	}
	
	private TiledMapTile getTileAt(float x, float y) {
		x = x * nodeLayer.getTileWidth();
		y = y * nodeLayer.getTileHeight();
		
		Cell cell = getCellAt(x, y);
		return cell != null? cell.getTile() : null;
	}
	
	public int findLastNotNullInArray(){
		int i = 0;
		while(path[i] != null){
			i++;
		}
		return i - 1;
	}
	public Node[] getPath() {
		return path;
	}

	public void setPath(Node[] path) {
		this.path = path;
	}
	
}