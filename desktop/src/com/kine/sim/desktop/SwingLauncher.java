package com.kine.sim.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kine.sim.HackGUI;
import com.kine.sim.ModelViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static com.kine.sim.ModelViewer.cam;

public class SwingLauncher extends JFrame
   {
   private LwjglAWTCanvas canvas;
   private Container container;
   private SwingDisplay swingDisplay;
   private HackGUI Hackgui;

    public SwingLauncher()
       {
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       container = getContentPane();
       container.setLayout(new BorderLayout());
       
       this.setTitle("Robotic Arm Simulation");

       LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

       this.swingDisplay = new SwingDisplay();
       this.canvas = new LwjglAWTCanvas(new ModelViewer(swingDisplay), config);
       container.add(canvas.getCanvas(), BorderLayout.CENTER);

        JButton button = new JButton("Home");
        container.add(button, BorderLayout.SOUTH);

        button.addActionListener(this::onButtonPress);
        pack();
        setVisible(true);
        setSize(640, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

       }
   public void onButtonPress(ActionEvent e)
       {
           cam.position.set(100f, 100f, 100f);
           cam.lookAt(0, 70, -20);
           cam.near = 1f;
           cam.far = 50_000f;
           cam.update();
       }
    public static void launch()
       {
       SwingLauncher swingLauncher = new SwingLauncher();
       }
    public static void main(String[] args) {
       SwingUtilities.invokeLater(SwingLauncher::launch);}
   }
