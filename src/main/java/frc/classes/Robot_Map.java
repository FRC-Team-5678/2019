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
    //Ports
    public int compressor = 0;
    public int hatch_Solenoid_1 = 6;
    public int hatch_Solenoid_2 = 7;
    public int left = 0;
    public int right = 1;
    public int intake = 2;
    public int arm = 3;
    public int LimitArmOpenp = 1;
    public int LimitArmClosep = 0;

    //Joystic Buttons
    public int joysticPort = 0;
    public int speedhalf = 2;
    public int speedfull = 3;
    public int hatchTrigger = 4;
    public int speed3quarter = 5;
    public int intakeButton = 6; // the button number for intake
    public int intakerev = 7; // the button number for intake rev
    public int armretract = 8;
    public int driveselector = 9;
    public int armtrigger = 10;
    public int armextend = 11;

    //Hatch
    public double armSpeed = .6;
}
