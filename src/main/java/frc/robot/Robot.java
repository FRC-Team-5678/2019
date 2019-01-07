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
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;



public class Robot extends TimedRobot {


  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
NetworkTableEntry tx = table.getEntry("tx");
NetworkTableEntry ty = table.getEntry("ty");
NetworkTableEntry ta = table.getEntry("ta");




//read values periodically
double x = tx.getDouble(0.0);
double y = ty.getDouble(0.0);
double area = ta.getDouble(0.0);

//post to smart dashboard periodically
//SmartDashboard.putNumber("LimelightX", x);
//SmartDashboard.putNumber("LimelightY", y);
//SmartDashboard.putNumber("LimelightArea", area);
  
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
    table.getEntry("camMode").setNumber(1);
   
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
