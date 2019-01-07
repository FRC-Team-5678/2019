/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* By:Andrew Levin                                                            */
/* Team#5678                                                                  */
/*                                                                            */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;



public class Robot extends TimedRobot {
  
  //motor setup
  Spark left = new Spark(1);
  Spark right = new Spark(2);
  
  //drive setup
  DifferentialDrive myRobot = new DifferentialDrive(left,right);
 
 // Joystick setup
  Joystick main = new Joystick(1);
  double speedX = main.getRawAxis(2);  //change to whats needed
  double rotatZ = main.getRawAxis(1);  //change to whats needed
  
 
  @Override
  public void robotInit() {
   
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    myRobot.arcadeDrive(speedX, rotatZ);
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
