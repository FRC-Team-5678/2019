/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.classes;
import frc.classes.Robot_Map;
import frc.classes.SmartDash;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class Intake {
    SmartDash dash = new SmartDash();
    Robot bot;
    public Intake(Robot robot){
        this.bot = robot;
    }
    public void intake(){
        if (bot.main.getRawButton(Robot_Map.intakeButton)) {// intake and shoter mechinisem power.
            bot.MIntake.set(.6);
          } else if (bot.main.getRawButtonReleased(Robot_Map.intakeButton)) {
            bot.MIntake.stopMotor();
          }
          if (bot.main.getRawButton(Robot_Map.intakerev)) {
            bot.MIntake.set(-.3);
          } else if (bot.main.getRawButtonReleased(Robot_Map.intakerev)) {
            bot.MIntake.stopMotor();
          }
    }
}
