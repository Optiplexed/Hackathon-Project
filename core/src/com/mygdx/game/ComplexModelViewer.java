package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class ComplexModelViewer extends ApplicationAdapter    
   {
	private PerspectiveCamera cam;
	private CameraInputController camController;
	private ModelBatch modelBatch;
	private AssetManager assets;
	private Array<ModelInstance> instances = new Array<ModelInstance>();
	private Environment environment;
	private boolean loading;

	private Model model;
	private ModelInstance instance;
	
	@Override
	public void create ()
	   {
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(1f, 1f, 1f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		ModelLoader loader = new ObjLoader();
		model = loader.loadModel(Gdx.files.internal("ship.obj"));
		instance = new ModelInstance(model);
		instance.transform.scale(0.1f, 0.1f, 0.1f);

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);
      
		assets = new AssetManager();
		assets.load("ship2.obj", Model.class);
		loading = true;
		}
	private void doneLoading() 
	   {
	   Model ship = assets.get("ship2.obj", Model.class);
	   ModelInstance shipInstance = new ModelInstance(ship);
	   instances.add(shipInstance);
	   loading = false;
	   }

	@Override
	public void render () 
	   {
	   if (loading && assets.update())
		   doneLoading();
	   camController.update();

    	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	   Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

   	modelBatch.begin(cam);
   	modelBatch.render(instances, environment);
   	modelBatch.end();
	   }

	@Override
	public void dispose () 
	   {
   	modelBatch.dispose();
	   instances.clear();
   	assets.dispose();
   	}

	public void resume () 
	   {
	   
	   }

	public void resize (int width, int height) 
	   {
	   
	   }

	public void pause () 
	   {
	   
	   }
	}
