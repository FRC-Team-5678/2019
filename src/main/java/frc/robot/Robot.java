/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* By:Andrew Levin                                                            */
/* Team#5678                                                                  */
/*                                                                            */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.SerialPort.WriteBufferMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SerialPort;



public class Robot extends TimedRobot {
    // Aurdino testing
    int foo;
    String spread ="0";
    SerialPort sp = new SerialPort(9600, SerialPort.Port.kUSB1);
 int number = 0;
  //factor to convert to inches
  private static final double ultraToinch = 41.8175;

  //speed constant for encoder moving forword
  private static final double ultraSpeed = .05;

  //ultra sonic input
 //AnalogInput ultraSonic = new AnalogInput(0);
 //Ultrasonic us = new Ultrasonic(1,0);
 

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
  
 
  public void robotInit() {
    sp.enableTermination();
    sp.setTimeout(.02);

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
    
    myRobot.arcadeDrive(main.getY(), main.getX());
    spread = sp.readString();
   if(spread.length() > 1)
   { 
   spread=spread.substring(0, spread.length() - 2);
 // String t1 = "";
  
  foo = Integer.parseInt(spread);
 //System.out.println(spread +" "+ spread.length());
   }
System.out.println(foo);
   //myRobot.stopMotor();
 if(main.getTrigger() & foo > 28){
     myRobot.arcadeDrive(-.55, 0);
     System.out.println("active");
   }
   //myRobot.stopMotor();
   if(main.getTriggerPressed()){
    if(hatchState == false){
       hatch.set(DoubleSolenoid.Value.kReverse);
     hatchState = true;
     System.out.println("true");
     //hatch.set(DoubleSolenoid.Value.kOff);
     //System.out.println(hatch.get());
   }
   else{
     hatch.set(DoubleSolenoid.Value.kForward);
     hatchState = false;
   }
     
   } 
    
    
    
    
    
    
    
    
    
    
    
    
    //myRobot.setMaxOutput(.5);
    //myRobot.arcadeDrive(main.getRawAxis(1), main.getRawAxis(0));
    //double v = tv.getDouble(0.0);

    //if(v == 0){//if no target is in view
     //   left.set(.3);
        
     // }
      /*if(true){//if target is in camra view
        double xOffset = tx.getDouble(0.0);
        double yOffset = ty.getDouble(0.0);
        
        if(Math.abs(xOffset)>6){//if the target is far away from center
          System.out.println("0.02");
          System.out.println(0.02 * xOffset);
          left.set(0.03 * xOffset);
          right.set(0.03* xOffset);
        }
        else if(Math.abs(xOffset)>3){//if target is close to center
          System.out.println("0.05");
          System.out.println(0.15 * xOffset);
          left.set(0.07 * xOffset);
          right.set(0.07 * xOffset);
        }
        else{
          
          }
        } */
      }
   
        
   
    /*if(main.getTrigger()){
    double xOffset = tx.getDouble(0.0);
    left.set(.3 * xOffset);
    right.set(.3* xOffset);
    }*/
  

  

  @Override
  public void testInit() {
    
  }

  @Override
  public void testPeriodic() {
    //System.out.println(sp.readString(100));
    int foo;
    
   // String test = (sp.readString(10));
    //System.out.println(test);
   // sp.flush();
   spread = sp.readString();
   if(spread.length() > 1)
   { 
   spread=spread.substring(0, spread.length() - 2);
 // String t1 = "";
  
  foo = Integer.parseInt(spread);
 System.out.println(spread +" "+ spread.length());
   }
 


//try {
 // spread = spread.trim();
  //System.out.println(spread + " " + spread.length());
  //  foo = Integer.parseInt(spread);
 // } catch (NumberFormatException e) {
    
//}
  
  //int foo = Integer.valueOf(spread);
  //int foo = Integer.parseInt(spread);
  //if(){
    // System.out.println("yup its working");
    
   //}
   //else{
     //System.out.println("its still working");
   //}
    

   //System.out.println(us.getRangeInches());
//System.out.println(ultraSonic.getVoltage()*ultraToinch);
//Timer.delay(.001);

  //   double xOffset = tx.getDouble(0.0);
  //  System.out.println(xOffset);
  //  System.out.println(Math.abs(xOffset));
   
  //double yOffset = ty.getDouble(0.0);
  //System.out.println((26-21.5)/Math.tan(5+yOffset));
  }

}
