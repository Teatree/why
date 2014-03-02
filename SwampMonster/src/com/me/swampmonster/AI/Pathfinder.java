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
 
	private static PriorityQueue<Node> openList;
	private static Set<Node> closedList;
	
	private static TiledMap tiledMap;
	private static TiledMapTileLayer nodeLayer;
	
	private static int mapWidth;
	private static int mapHeight;
	
	private static Node[][] nodes;
	private Iterator<TiledMapTile> it;
	private static Node startingNode;
	private static Node targetNode;
	public static Node[] path;
	
	public static void setTiledMap(TiledMap tiledMap) {
		Pathfinder.tiledMap = tiledMap;
		
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
	
	private static void loadNodes() {
		for (int x = 0; x < mapWidth; x++) {        // consider x++
			for (int y = 0; y < mapHeight; y++) {
				nodes[x][y] = new Node(x, y, null);
			}
		}
	}
	
	public static Node[] findPath(Vector2 startingPosition, Vector2 targetPosition) {
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
			
			if (currentNode != null && currentNode.equals(targetNode) && currentNode.getParentNode() != null) {
				Node node = currentNode.getParentNode();
				reconstructPath(navigatedNodes, currentNode);
				int cuntar = 0;
				
				if(node.getParentNode() == null){
//					System.out.println("the GetParent is null!");
					return null;
				}else {
					while(node.getParentNode() != null && !node.getParentNode().equals(startingPosition)){
						path[cuntar] = node;
						node = node.getParentNode();
						cuntar++;
					}
				}
				
//				if(node.getParentNode() == null){
//					System.out.println("the GetParent is null!");
//					return null;
//				}
				return path;
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
		
		return null;
	}
	
	private static void reconstructPath(Node[][] navigatedNodes, Node currentNode) {
		
	}
	
	private static List<Node> getNeighborNodes(Node parentNode) {
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
	
	private static int distanceToNode(Node nodeA, Node nodeB) {
		return Math.abs(nodeA.x - nodeB.x) + Math.abs(nodeA.y - nodeB.y);
	}
	
	private static boolean nodeIsWalkable(int x, int y) {
		if (nodeLayer.getCell(x, y) == null) {
			return false;
		}
		
		return true;
	}
	private static Node getNodeAt(float x, float y) {
		int cellx = (int)x / (int)nodeLayer.getTileWidth();
		int celly = (int)y / (int)nodeLayer.getTileHeight();
//		System.out.println(cellx + " and " + celly);
		return getNodeAt(cellx, celly);
	}
	
	private static Node getNodeAt(int x, int y) {
		if (x < 0 || x >= mapWidth) {
			return null;
		}
		if (y < 0 || y >= mapHeight) {
			return null;
		}
		return nodes[x][y];
	}
	
	private static Cell getCellAt(float x, float y) {
		int cellX = (int)x / (int)nodeLayer.getTileWidth();
		int cellY = (int)y / (int)nodeLayer.getTileHeight();
		
		return nodeLayer.getCell(cellX, cellY);
	}
	
	private static TiledMapTile getTileAt(float x, float y) {
		x = x * nodeLayer.getTileWidth();
		y = y * nodeLayer.getTileHeight();
		
		Cell cell = getCellAt(x, y);
		return cell != null? cell.getTile() : null;
	}
	
	public static int findLastNotNullInArray(){
		int i = 0;
		while(path[i] != null){
			i++;
		}
		return i - 1;
	}
	public static Node[] getPath() {
		return path;
	}

	public void setPath(Node[] path) {
		this.path = path;
	}
	
}