package com.mygdx.game;

import java.util.Scanner;

public class HackGUI
   {
        public static void start()
        {
            HackGUI.guiText();
            }


    private static void guiText() {
        // Creates a new Scanner object
        Scanner myObj = new Scanner(System.in);  
        System.out.println("Enter Base angle");

        // Reads user input and output
        String baseAngle = myObj.nextLine();  
        System.out.println("Base Angle is " + baseAngle);

        System.out.println("Enter Joint angle");

        String jointAngle = myObj.nextLine();
        System.out.println("Joint Angle is " + jointAngle);

        System.out.println("Would you like to enter another set of angles? [1/0]");
        Integer tryAgain = Integer.valueOf(myObj.nextLine());
        if (tryAgain == 1) {
            HackGUI.start();
        }
        else{
                System.out.println("See you next time!");
        }

    }
}



