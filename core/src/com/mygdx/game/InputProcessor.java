package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

public class InputProcessor extends CameraInputController
   {
   private float startX, startY;
   private ModelViewer modelViewer;
   public InputProcessor(Camera camera, ModelViewer modelViewer)
      {
      super(camera);
      this.modelViewer = modelViewer;
      translateUnits = 200f;
      }
      
   public boolean keyDown(int keyCode)
      {
      modelViewer.baseInstance.transform.rotate(new Vector3(0, 1, 0), 1f);
      
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

   public boolean touchDown(int screenX, int screenY, int pointer, int button)
      {
      return super.touchDown(screenX, screenY, pointer, button);
      }
   @Override
   public boolean touchDragged (int screenX, int screenY, int pointer) 
      {         
      System.out.println("E");
      boolean result = super.touchDragged(screenX, screenY, pointer);
      if (result || this.button < 0) return result;
      final float deltaX = (screenX - startX) / Gdx.graphics.getWidth();
      final float deltaY = (startY - screenY) / Gdx.graphics.getHeight();
      startX = screenX;
      startY = screenY;
      return process(deltaX, deltaY, button);
      }
   }
