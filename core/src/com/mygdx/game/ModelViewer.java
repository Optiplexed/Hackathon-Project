package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;

public class ModelViewer extends ApplicationAdapter    
   {
   private ScreenViewport viewport;
	private PerspectiveCamera cam;
	private CameraInputController camController;
	private ModelBatch modelBatch;
	private AssetManager assets;
	private Array<ModelInstance> instances = new Array<ModelInstance>();
	private Environment environment;
	private boolean loading;
   
   private ModelBuilder modelBuilder;
	private Model model;
	private ModelInstance instance;
	private IDisplay display;

	protected ModelInstance baseInstance;
	protected ModelInstance linkage1Instance;
	protected ModelInstance linkage2Instance;
	public PerspectiveCamera getCam()
		{
      return this.cam;
		}
	public ModelViewer(IDisplay display)
		{
      this.display = display;
		}
	@Override
	public void create()
	   {
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		viewport = new ScreenViewport(cam);
		this.viewport.apply();
		
		cam.position.set(1f, 1f, 1f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 50_000f;
		cam.update();
		
		modelBuilder = new ModelBuilder();
		ModelLoader loader = new ObjLoader();
		model = loader.loadModel(Gdx.files.internal("base.obj"));
		instance = new ModelInstance(model);

		instance.transform.scale(0.1f, 0.1f, 0.1f);

		camController = new InputProcessor(cam, this);
		Gdx.input.setInputProcessor(camController);

		assets = new AssetManager();
		assets.load("base.obj", Model.class);
		assets.load("linkage1.obj", Model.class);
		assets.load("linkage2.obj", Model.class);
		loading = true;
		}
	private void doneLoading() 
	   {
		Model base = assets.get("base.obj", Model.class);
	   Model linkage1 = assets.get("linkage1.obj", Model.class);
	   Model linkage2 = assets.get("linkage2.obj", Model.class);
	   
		baseInstance = new ModelInstance(base);
	   linkage1Instance = new ModelInstance(linkage1);
	   linkage2Instance = new ModelInstance(linkage2);
	   
	   linkage2Instance.transform.setToTranslation(-7, 60, 0);
	   linkage1Instance.transform.rotate(new Vector3(0,1,0), 180);
	   linkage1Instance.transform.setTranslation(7, 20, 0);
	   
	   instances.add(baseInstance);
	   instances.add(linkage1Instance);
	   instances.add(linkage2Instance);
	   
	   loading = false;
	   }
	/*private Model createPlaneModel()
	   {
	   modelBuilder.begin();
	   modelBuilder.createRect(0, 0, 0, 10, 10, 10, 10, 10, 10,
      modelBuilder.begin());
	   modelBuilder.begin();
	MeshPartBuilder bPartBuilder = modelBuilder.part("rect",
			GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates,
			material);
//NOTE ON TEXTURE REGION, MAY FILL OTHER REGIONS, USE GET region.getU() and so on
	bPartBuilder.setUVRange(u1, v1, u2, v2);
	bPartBuilder.rect(
			-(width*0.5f), -(height*0.5f), 0,
			(width*0.5f), -(height*0.5f), 0,
			(width*0.5f), (height*0.5f), 0,
			-(width*0.5f), (height*0.5f), 0,
			0, 0, -1);


	return (modelBuilder.end());
	}*/
	@Override
	public void render () 
	   {
	   if (loading && assets.update())
		   doneLoading();

	   camController.update();

    	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() * display.getWidthScalar(), 
    	   Gdx.graphics.getHeight() * display.getHeightScalar());
    	
	   Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

       modelBatch.begin(viewport.getCamera());
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
		viewport.update(width * display.getWidthScalar(), height * display.getHeightScalar());
	   }

	public void pause () 
	   {
	   
	   }
	}
