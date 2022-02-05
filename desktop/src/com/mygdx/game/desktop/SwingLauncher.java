package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ComplexModelViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SwingLauncher extends JFrame
   {
   private LwjglAWTCanvas canvas;
   private ComplexModelViewer game;
   private Container container;
   private SwingDisplay swingDisplay;

    public SwingLauncher()
       {
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       container = getContentPane();
       container.setLayout(new BorderLayout());

       LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

       this.swingDisplay = new SwingDisplay(container);
       this.canvas = new LwjglAWTCanvas(game = new ComplexModelViewer(), config);
       container.add(canvas.getCanvas(), BorderLayout.CENTER);

        JButton button = new JButton("Bottom");
        container.add(button, BorderLayout.SOUTH);

        button.addActionListener(this::onButtonPress);
        pack();
        setVisible(true);
        setSize(640, 400);

       }
   public void onButtonPress(ActionEvent e)
       {
       System.out.println(container.getWidth());
       System.out.println(container.getHeight());
       System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
       }
    public static void launch()
       {
       SwingLauncher swingLauncher = new SwingLauncher();
       }
    public static void main(String[] args)
       {
       SwingUtilities.invokeLater(SwingLauncher::launch);
       }
   }
