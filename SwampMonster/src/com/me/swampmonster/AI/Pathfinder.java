package com.me.swampmonster.AI;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.me.swampmonster.models.enemies.Enemy;
 
public class Pathfinder {
	
	public static Node[] findPath(Vector2 startingPosition, Vector2 targetPosition, TiledMapTileLayer nodeLayer) {
		
//		// System.out.println("(findPath): working!");
		
		int mapWidth = nodeLayer.getWidth();
		int mapHeight = nodeLayer.getHeight();
		Node[] path = new Node[99];
		
		//load Nodes
		Node[][] nodes = new Node[mapWidth][mapHeight];
		for (int x = 0; x < nodes.length; x++) {        // consider x++
			for (int y = 0; y < nodes[0].length; y++) {
				nodes[x][y] = new Node(x, y, null);
			}
		}
		
		PriorityQueue<Node> openList = new PriorityQueue<Node>();
		Set<Node> closedList = new HashSet<Node>();
		
		Node startingNode = getNodeAt(startingPosition.x, startingPosition.y, nodeLayer, nodes);
		Node targetNode = getNodeAt(targetPosition.x, targetPosition.y, nodeLayer, nodes);
		
		Node[][] navigatedNodes = new Node[mapWidth][mapHeight];
		
		startingNode.g = 0;
		startingNode.h = distanceToNode(startingNode, targetNode);
		startingNode.f = startingNode.g + startingNode.h;
		startingNode.parentNode = null;
		
		openList.add(startingNode);
		
		while (openList.size() > 0) {
			Node currentNode = openList.poll();
//			// System.out.println("iteration");
			
//			// System.out.println("CurrentNode! ==== " + currentNode);
//			// System.out.println("TargetNode! ==== " + targetNode);
			
			if (currentNode != null && currentNode.equals(targetNode) && currentNode.getParentNode() != null) {
				Node node = currentNode.getParentNode();
				reconstructPath(navigatedNodes, currentNode);
				int cuntar = 0;
				
				if(node.getParentNode() == null){
//					// System.out.println("the GetParent is null!");
					return new Node[99];
				}else {
					while(node.getParentNode() != null /*&& !node.getParentNode().equals(startingPosition)*/){
						path[cuntar] = node;
						node = node.getParentNode();
						cuntar++;
					}
				}
				
//				if(node.getParentNode() == null){
//					// System.out.println("the GetParent is null!");
//					return null;
//				}
				return path;
			}
			
			openList.remove(currentNode);
//			// System.out.println("Removing " + currentNode + " From openList, now openList contains: " + openList.size());
			closedList.add(currentNode);
//			// System.out.println("Adding " + currentNode + " To closedList, now closedList contains: " + closedList.size());
			for (Node neighborNode : getNeighborNodes(currentNode, nodeLayer, nodes)) {
				float tentativeGScore = currentNode.g + distanceToNode(currentNode, targetNode);
				
				if (closedList.contains(neighborNode) && tentativeGScore >= neighborNode.g) {
					continue;
				}
				// wall check goes here !
				
				
				if (neighborNode.g == 0 || tentativeGScore < neighborNode.g) {
						navigatedNodes[neighborNode.x][neighborNode.y] = currentNode;
//						// System.out.println("Assigning currentNode, now it's: " + currentNode);
						neighborNode.g = tentativeGScore;
						neighborNode.f = neighborNode.g + distanceToNode(neighborNode, targetNode);
						
						
						if (!openList.contains(neighborNode) && nodeIsWalkable(neighborNode.x, neighborNode.y, nodeLayer)) {
							openList.add(neighborNode);
							neighborNode.setParentNode(currentNode);
//							// System.out.println("Adding neighborNode to the OPEN_LIST");
						}
				}
			}
		}
		System.out.println("3");
		
		return null;
	}
	
	private static void reconstructPath(Node[][] navigatedNodes, Node currentNode) {
		
	}
	
	private static List<Node> getNeighborNodes(Node parentNode, TiledMapTileLayer nodeLayer, Node [][] nodes) {
		List<Node> neighborNodes = new ArrayList<Node>();
		
		if (parentNode.y + 1 < nodes[parentNode.x].length && getTileAt(nodes[parentNode.x][parentNode.y + 1].x , 
				nodes[parentNode.x][parentNode.y + 1].y, nodeLayer).getProperties().containsKey("walkable")){
			neighborNodes.add(nodes[parentNode.x][parentNode.y + 1]); // North
		}
		if (parentNode.y - 1 >= 0 && getTileAt(nodes[parentNode.x][parentNode.y - 1].x ,
				nodes[parentNode.x][parentNode.y - 1].y, nodeLayer).getProperties().containsKey("walkable")){
			neighborNodes.add(nodes[parentNode.x][parentNode.y - 1]); // South
		}
		if (parentNode.x + 1 < nodes.length && getTileAt(nodes[parentNode.x + 1][parentNode.y].x, 
				nodes[parentNode.x + 1][parentNode.y].y, nodeLayer).getProperties().containsKey("walkable")){
			neighborNodes.add(nodes[parentNode.x + 1][parentNode.y]);// East
		}
		if (parentNode.x - 1 >= 0 && getTileAt(nodes[parentNode.x - 1][parentNode.y].x, 
				nodes[parentNode.x - 1][parentNode.y].y, nodeLayer).getProperties().containsKey("walkable")){
			neighborNodes.add(nodes[parentNode.x - 1][parentNode.y]); // West
		}
		return neighborNodes; 
	}
	
	private static int distanceToNode(Node nodeA, Node nodeB) {
		return Math.abs(nodeA.x - nodeB.x) + Math.abs(nodeA.y - nodeB.y);
	}
	
	private static boolean nodeIsWalkable(int x, int y, TiledMapTileLayer nodeLayer) {
		if (nodeLayer.getCell(x, y) == null) {
			return false;
		}
		
		return true;
	}
	private static Node getNodeAt(float x, float y, TiledMapTileLayer nodeLayer, Node [][] nodes) {
		int cellx = (int)x / (int)nodeLayer.getTileWidth();
		int celly = (int)y / (int)nodeLayer.getTileHeight();
		System.out.println("tileWidth:" + (int)nodeLayer.getTileWidth());
		return getNodeAt(cellx, celly, nodes);
	}
	
	private static Node getNodeAt(int x, int y, Node [][] nodes) {
		if (x < 0 || x >= nodes.length) {
			return null;
		}
		if (y < 0 || y >= nodes[1].length) {
			return null;
		}
		return nodes[x][y];
	}
	
	private static Cell getCellAt(float x, float y, TiledMapTileLayer nodeLayer) {
		int cellX = (int)x / (int)nodeLayer.getTileWidth();
		int cellY = (int)y / (int)nodeLayer.getTileHeight();
		
		return nodeLayer.getCell(cellX, cellY);
	}
	
	private static TiledMapTile getTileAt(float x, float y, TiledMapTileLayer nodeLayer) {
		x = x * nodeLayer.getTileWidth();
		y = y * nodeLayer.getTileHeight();
		
		Cell cell = getCellAt(x, y, nodeLayer);
		return cell != null? cell.getTile() : null;
	}
	
	//threads pool
	static ExecutorService threadPool = Executors.newCachedThreadPool();
	
    public static void findPathInThreadPool(final Vector2 startingPosition, final Vector2 targetPosition, final TiledMapTileLayer nodeLayer, final Enemy enemy) {
    	threadPool.submit(new Runnable() {
            public void run() {
                enemy.setPath(findPath(startingPosition, targetPosition, nodeLayer));
            }
        });
    }

    static AtomicInteger counter = new AtomicInteger();
    public static void someTask() {
//        // System.out.println("someTask: " + counter.incrementAndGet() 
//                + " on thread: " + Thread.currentThread());
    }
}