package com.kine.sim.desktop;

import com.kine.sim.IDisplay;

import java.awt.*;

public class SwingDisplay implements IDisplay
   {
   public static final int WIDTH_SCALE;
   public static final int HEIGHT_SCALE;

   private Component container;

   public SwingDisplay(Component container)
      {
      this.container = container;
      }
   static
      {
      double awtWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
      double awtHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

      int trueWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
      int trueHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();

      WIDTH_SCALE = (int)Math.round(trueWidth / awtWidth);
      HEIGHT_SCALE = (int)Math.round(trueHeight / awtHeight);
      }
   
   @Override
   public int getWidthScalar()
      {
      return WIDTH_SCALE;
      }

   @Override
   public int getHeightScalar()
      {
      return HEIGHT_SCALE;
      }
   }
