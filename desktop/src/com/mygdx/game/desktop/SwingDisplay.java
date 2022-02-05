package com.mygdx.game.desktop;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.*;

public class SwingDisplay
   {
   public static final int WIDTH_SCALE;
   public static final int HEIGHT_SCALE;

   private Component container;

   public SwingDisplay(Component container)
      {
      this.container = container;
      }
   static
   //HELLO
      {
      double awtWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
      double awtHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

      int trueWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
      int trueHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();

      WIDTH_SCALE = (int)Math.round(trueWidth / awtWidth);
      HEIGHT_SCALE = (int)Math.round(trueHeight / awtHeight);
      }

   }
