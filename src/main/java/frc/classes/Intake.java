/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* Codded By:Andrew Levin                                                     */
/* Team#5678                                                                  */
/* name# Solaris                                                              */
/*----------------------------------------------------------------------------*/

package frc.classes;

import frc.classes.Robot_Map;
import frc.classes.SmartDash;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class Intake {
  SmartDash dash;
  Robot bot;

  public Intake(Robot robot, SmartDash dash) {
    this.bot = robot;
    this.dash = dash;
  }

  public void intake() {
    if (bot.main.getPOV(0) == Robot_Map.intakeButton) {// intake and shoter mechinisem power.
      bot.MIntake.set(.6);// normal .6
    } else if (bot.main.getPOV(0) == -1) {
      bot.MIntake.stopMotor();
    }
    if (bot.main.getPOV(0) == Robot_Map.intakerev) {// intake rev
      bot.MIntake.set(-.3); // normal -.3
    } else if (bot.main.getPOV(0) == -1) {
      bot.MIntake.stopMotor();
    }
  }
}
