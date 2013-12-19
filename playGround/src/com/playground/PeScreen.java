package com.playground;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class PeScreen implements Screen {

	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	
	private TiledMap tiledMap;
	
	private Vector2 s;
	private Vector2 t;
	
	public Actor actor;
	public PePathfinder pathfinder;
	
	public void show() {
		shapeRenderer = new ShapeRenderer();
		
		s = new Vector2();
		t = new Vector2();
		
		tiledMap = new TmxMapLoader().load("data/playMap.tmx");
		
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		
		camera = new OrthographicCamera();
		camera.position.x = 80;
		camera.position.y = 80;
		camera.zoom = 0.5f;
		
		actor = new Actor(new Vector2(30, 30));
		actor.position.x = 20;
		actor.position.y = 20;
		
		pathfinder = new PePathfinder(tiledMap);
		
		s.x = 4;
		s.y = 4;
		t.x = 8;
		t.x = 8;
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mapRenderer.render();
		mapRenderer.setView(camera);
		
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(s.x, s.y, 1, 1);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(t.x, t.y, 1, 1);
		shapeRenderer.end();
		
		pathfinder.findPath(s, t);
	}

	public void resize(int width, int height) {
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera.update();
	}
	public void hide() {

	}

	public void pause() {

	}

	public void resume() {

	}

	public void dispose() {
		tiledMap.dispose();
		mapRenderer.dispose();
		shapeRenderer.dispose();
	}
}
