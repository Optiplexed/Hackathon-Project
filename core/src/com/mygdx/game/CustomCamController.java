package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class CustomCamController extends CameraInputController
   {
   private float startX, startY;
   private int selected = -1, selecting = -1;
   
   private ModelViewer modelViewer;
   private Vector3 position = new Vector3();
   
   private Material originalMaterial = new Material();
   private Material selectionMaterial;
   
   
   public CustomCamController(Camera camera, ModelViewer modelViewer)
      {
      super(camera);
      this.modelViewer = modelViewer;
      translateUnits = 200f;

      selectionMaterial = new Material();
      selectionMaterial.set(ColorAttribute.createDiffuse(Color.ORANGE));
      }
      
   public boolean keyDown(int keyCode)
      {
      return super.keyDown(keyCode);
      }

   public boolean keyTyped(char Char)
      {
     return super.keyTyped(Char);
      }

   public boolean keyUp(int keyCode)
      {
      return super.keyUp(keyCode);
      }

   public boolean mouseMoved(int xPos, int yPos)
      {
      return super.mouseMoved(xPos, yPos);
      }

   public boolean scrolled(float xAmnt, float yAmnt)
      {
      return super.scrolled(xAmnt, yAmnt );
      }
   @Override public boolean touchUp(int screenX, int screenY, int pointer, int button)
      {
      super.touchUp(screenX, screenY, pointer, button);
      System.out.println("NANI");
      if (selecting >= 0) {
      if (selecting == getObject(screenX, screenY))
      System.out.println("SELECTED SOMETHING!");
         setSelected(selecting);
      selecting = -1;
      return true;
      }
      return false;
      }
   public boolean touchDown(int screenX, int screenY, int pointer, int button)
      {
      boolean learned = super.touchDown(screenX, screenY, pointer, button);
      selecting = getObject(screenX, screenY);
      return selecting >= 0;
      }
   @Override
   public boolean touchDragged (int screenX, int screenY, int pointer) 
      {
      boolean result = super.touchDragged(screenX, screenY, pointer);
      if (result || this.button < 0) return result;
      final float deltaX = (screenX - startX) / Gdx.graphics.getWidth();
      final float deltaY = (startY - screenY) / Gdx.graphics.getHeight();
      startX = screenX;
      startY = screenY;
      return process(deltaX, deltaY, button);
      }
   public int getObject (int screenX, int screenY)
      {
      //screenX *= modelViewer.getDisplay().getWidthScalar();
      //screenY *= modelViewer.getDisplay().getHeightScalar();
      
      Ray ray = modelViewer.getCam().getPickRay(screenX, screenY);
      int result = -1;
      float distance = -1;
      System.out.println("ENTERING METHOD");
      for (int i = 0; i < modelViewer.getInstances().size; ++i)
         {
         final CustomModelInstance instance = (CustomModelInstance) modelViewer.getInstances().get(i);
         instance.transform.getTranslation(position);
         System.out.println("ITERATING");
         position.add(instance.center);
         float dist2 = ray.origin.dst2(position);
         System.out.println(dist2);
         if (distance >= 0f && dist2 > distance) continue;
         System.out.println("IT IS WITHIN DISTANCE");
         if (Intersector.intersectRaySphere(ray, position, instance.radius, null))
            {

            result = i;
            distance = dist2;
            }
         }
      return result;
      }
   public void setSelected (int value) {
   if (selected == value) return;
   if (selected >= 0) {
   Material mat = modelViewer.getInstances().get(selected).materials.get(0);
   mat.clear();
   mat.set(originalMaterial);
   }
   selected = value;
   if (selected >= 0) {
   Material mat = modelViewer.getInstances().get(selected).materials.get(0);
   originalMaterial.clear();
   originalMaterial.set(mat);
   mat.clear();
   mat.set(selectionMaterial);
   }
   }
   }
