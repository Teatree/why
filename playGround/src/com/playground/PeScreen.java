package com.playground;

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

	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	
	public Actor actor;
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mapRenderer.render();
		mapRenderer.setView(camera);
		
		shapeRenderer.setTransformMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(actor.getPosition().x, actor.getPosition().y, 16, 16);
		shapeRenderer.end();
	}

	public void resize(int width, int height) {
		camera.viewportHeight = height;
		camera.viewportWidth = width;
		camera.update();
	}
	public void show() {
		camera = new OrthographicCamera();
		camera.position.x = 80;
		camera.position.y = 80;
		camera.zoom = 0.5f;
		
		shapeRenderer = new ShapeRenderer();
		
		actor = new Actor(new Vector2(30, 30));
		actor.position.x = 20;
		actor.position.y = 20;
		
		tiledMap = new TmxMapLoader().load("data/playMap.tmx");
		
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		
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
	}

}
