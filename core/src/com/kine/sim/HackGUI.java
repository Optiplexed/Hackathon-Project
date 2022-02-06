package com.kine.sim;

import com.badlogic.gdx.graphics.g3d.Model;

import java.util.Scanner;

public class HackGUI
   {
   private ModelViewer viewer;
   public HackGUI(ModelViewer viewer)
      {
      this.viewer = viewer;
      guiText();
      }


    private void guiText() {
        // Creates a new Scanner object
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter all angles in degrees");
        System.out.println("Enter Base angle");

        // Reads user input and displays output (degrees)
        String baseAngle = myObj.nextLine();  
        System.out.println("Base Angle is " + baseAngle);

        System.out.println("Enter Joint angle");

        String jointAngle = myObj.nextLine();
        System.out.println("Joint Angle is " + jointAngle);

        System.out.println("Enter Second Joint Angle");
        String secondJointAngle = myObj.nextLine();
        System.out.println("Second Joint Angle is " + secondJointAngle);

        System.out.println("Enter Handle Angle");
        String handAngle = myObj.nextLine();
        System.out.println("Handle  Angle is " + handAngle);

        System.out.println("Would you like to enter another set of angles? [1/0]");
        int tryAgain = Integer.parseInt(myObj.nextLine());
        
        viewer.set_angles(Float.parseFloat(baseAngle),
           Float.parseFloat(jointAngle),
           Float.parseFloat(secondJointAngle),
           Float.parseFloat(handAngle));
           
        System.out.println("Your robotic arm has been updated!");
        
        if (tryAgain == 1) {
            this.guiText();
        }
        else{ 
             
                System.out.println("See you next time!");
        }

    }
}



