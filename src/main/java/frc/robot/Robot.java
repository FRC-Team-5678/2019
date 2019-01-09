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
import edu.wpi.first.wpilibj.Timer;
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
NetworkTableEntry tv = table.getEntry("tv");




//read values periodically
double v = tv.getDouble(0.0);
double x = tx.getDouble(0.0);
double y = ty.getDouble(0.0);
double area = ta.getDouble(0.0);

//post to smart dashboard periodically
//SmartDashboard.putNumber("LimelightX", x);
//SmartDashboard.putNumber("LimelightY", y);
//SmartDashboard.putNumber("LimelightArea", area);
  
  //motor setup
  Spark left = new Spark(0);
  Spark right = new Spark(1);
  
  //drive setup
  DifferentialDrive myRobot = new DifferentialDrive(left,right);
 
 // Joystick setup
  Joystick main = new Joystick(0);
  double speedX = main.getRawAxis(0);  //change to whats needed
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
    double v = tv.getDouble(0.0);

      if(v == 0){
        left.set(.3);
        
      }
      else{
        double xOffset = tx.getDouble(0.0);
        double yOffset = ty.getDouble(0.0);
        
        if(Math.abs(xOffset)>6){
          System.out.println("0.02");
          System.out.println(0.02 * xOffset);
          left.set(0.03 * xOffset);
          right.set(0.03* xOffset);
        }
        else if(Math.abs(xOffset)>3){
          System.out.println("0.05");
          System.out.println(0.15 * xOffset);
          left.set(0.07 * xOffset);
          right.set(0.07 * xOffset);
        }
        else{
          if(main.getTriggerPressed()){
            myRobot.arcadeDrive(.2, 0);
            Timer.delay(1);
          }
        }
      }
   
   
   
    /*if(main.getTrigger()){
    double xOffset = tx.getDouble(0.0);
    left.set(.3 * xOffset);
    right.set(.3* xOffset);
    }*/
  

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  //   double xOffset = tx.getDouble(0.0);
  //  System.out.println(xOffset);
  //  System.out.println(Math.abs(xOffset));
   
  double yOffset = ty.getDouble(0.0);
  System.out.println((26-21.5)/Math.tan(5+yOffset));
  }

}
