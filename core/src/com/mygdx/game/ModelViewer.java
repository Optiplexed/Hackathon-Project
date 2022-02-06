package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ModelViewer extends ApplicationAdapter    
   {
	private IDisplay display;
	private HackGUI gui;
	private Environment environment;
	
   private ScreenViewport viewport;
	public static PerspectiveCamera cam;
	private CameraInputController camController;
	
	private String endEffDisplay;
	float base_angle = 0;
	float j1_angle = 0;
	float j2_angle = 0;
	float ee_angle = 0;

	private AssetManager assets;
	private Array<ModelInstance> instances = new Array<ModelInstance>();
	private ModelBuilder modelBuilder;
	private ModelBatch modelBatch;
	
	protected ModelInstance baseInstance;
	protected ModelInstance linkage1Instance;
	protected ModelInstance linkage2Instance;
	protected ModelInstance coordsInstance;
   
	private boolean loading;
	
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
		cam.position.set(1f, 1f, 1f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 50_000f;
		cam.update();

		viewport = new ScreenViewport(cam);
		this.viewport.apply();
		
		modelBuilder = new ModelBuilder();

		camController = new CustomCamController(cam, this);
		Gdx.input.setInputProcessor(camController);

		assets = new AssetManager();
		assets.load("base.obj", Model.class);
		assets.load("linkage1.obj", Model.class);
		assets.load("linkage2.obj", Model.class);
		assets.load("cartcoords.obj", Model.class);

		loading = true;
		}
	private void doneLoading() 
	   {
		Model base = assets.get("base.obj", Model.class);
	   Model linkage1 = assets.get("linkage1.obj", Model.class);
	   Model linkage2 = assets.get("linkage2.obj", Model.class);
		Model coords = assets.get("cartcoords.obj", Model.class);

		baseInstance = new CustomModelInstance(base);
	   linkage1Instance = new CustomModelInstance(linkage1);
	   linkage2Instance = new CustomModelInstance(linkage2);
		coordsInstance = new CustomModelInstance(coords);

		linkage2Instance.transform.setToTranslation(-7, 60, 0);
	   linkage1Instance.transform.rotate(new Vector3(0,1,0), 180);
	   linkage1Instance.transform.setTranslation(7, 20, 0);
	   
	   instances.add(baseInstance);
	   instances.add(linkage1Instance);
	   instances.add(linkage2Instance);
		instances.add(coordsInstance);
	   
	   loading = false;
	   }
	public void set_angles(float base_angle, float j1_angle, float j2_angle, float ee_angle)
	   {
	   float base_height = 20f;
	   float l1_length = 40f;
	   float l2_length = 32.5f;

	   Matrix4 base = new Matrix4().rotate(0,1,0,base_angle);
	   baseInstance.transform.set(base);

	   Matrix4 linkage1 = base.translate(0,base_height,0).rotate(1,0,0,j1_angle);
	   linkage1Instance.transform.set(linkage1);

	   Matrix4 linkage2 = linkage1.translate(0,l1_length,0).rotate(1,0,0,j2_angle);
	   linkage2Instance.transform.set(linkage2);

	   Matrix4 end_effector = linkage2.translate(0,l2_length,0).rotate(0,1,0,ee_angle);
	   coordsInstance.transform.set(end_effector);
	   
	   endEffDisplay = end_effector.toString();
	   }
	public void doKeyboardInput()
		{
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
		base_angle--;

		set_angles(base_angle, j1_angle, j2_angle, ee_angle);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
		base_angle++;

		set_angles(base_angle, j1_angle, j2_angle, ee_angle);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
		j1_angle--;

		set_angles(base_angle, j1_angle, j2_angle, ee_angle);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
		j1_angle++;

		set_angles(base_angle, j1_angle, j2_angle, ee_angle);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.I)){
		j2_angle++;

		set_angles(base_angle, j1_angle, j2_angle, ee_angle);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.K)){
		j2_angle--;

		set_angles(base_angle, j1_angle, j2_angle, ee_angle);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.N)){
		ee_angle++;

		set_angles(base_angle, j1_angle, j2_angle, ee_angle);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.M)){
		ee_angle--;

		set_angles(base_angle, j1_angle, j2_angle, ee_angle);
		}
		}
	@Override
	public void render () 
	   {
	   if (loading && assets.update())
		   doneLoading();

	   camController.update();

    	Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() * display.getWidthScalar(), 
    	  Gdx.graphics.getHeight() * display.getHeightScalar());
    	
	   Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		doKeyboardInput();
		
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
	public void resize (int width, int height) 
	   {
		viewport.update(width * display.getWidthScalar(), height * display.getHeightScalar());
		viewport.update(width, height );
	   }
	public PerspectiveCamera getCam()
		{
		return this.cam;
		}
	public Array<ModelInstance> getInstances()
		{
		return instances;
		}
	public IDisplay getDisplay()
		{
		return display;
		}
	}
