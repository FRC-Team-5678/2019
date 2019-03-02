/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.classes;

/**
 * Add your docs here.
 */
public class Robot_Map {
    // Ports
    public static int compressor = 0;
    public static int hatch_Solenoid_1 = 6;
    public static int hatch_Solenoid_2 = 7;
    public static int left = 0;
    public static int right = 1;
    public static int intake = 2;
    public static int arm = 4;
    public static int LimitArmOpenp = 8;
    public static int LimitArmClosep = 0;

    // Leds
    public static int Down = 0;
    public static int Up = 1;

    // Joystic Buttons
    public static int joysticPort = 0;
    public static int speedchange = 2;
    public static int hatchTrigger = 3;
    public static int speed3quarter = 5;
    public static int intakeButton = 0; // the button number for intake
    public static int intakerev = 180; // the button number for intake rev
    public static int armretract = 8;
    public static int driveselector = 9;
    public static int armtrigger = 4;
    public static int armextend = 11;
    public static int vision = 6;

    // Hatch
    public static double armSpeed = .6;
}
