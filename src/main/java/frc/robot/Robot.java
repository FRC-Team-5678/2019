/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* By:Andrew Levin                                                            */
/* Team#5678                                                                  */
/* name # orion                                                                           */
/*----------------------------------------------------------------------------*/

package frc.robot;

//import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;//not used
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;//not used
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.Compressor;//not used
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;//not used
import org.opencv.ml.NormalBayesClassifier;//not used
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SerialPort;//not used

public class Robot extends TimedRobot {

  // pnumatics
  // int hatchTrigger = 4;
  // Compressor cmain = new Compressor(0);
  // DoubleSolenoid hatch = new DoubleSolenoid(0, 1);
  // boolean hatchState = false;

  // Aurdino testing
  int foo;
  String spread = "0";
  double lidar = 0;
  // SerialPort sp = new SerialPort(115200, SerialPort.Port.kUSB1);
  int number = 0;

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry tv = table.getEntry("tv");

  // read values periodically
  double v = tv.getDouble(0.0);
  double x = tx.getDouble(0.0);
  double y = ty.getDouble(0.0);
  double area = ta.getDouble(0.0);

  // motor setup
  Spark left = new Spark(0);
  Spark right = new Spark(1);
  Spark Intake = new Spark(2);
  Spark Arm = new Spark(3);

  // hatch veriables
  int armtrigger = 10;
  int armState = 0;
  double armSpeed = .4;
  int ArmTrans = 0;
  DigitalInput lsArmOpen, lsArmClose;
  int lsArmOpenp = 0;
  int lsArmClosep = 1;

  // drive setup
  DifferentialDrive myRobot = new DifferentialDrive(left, right);
  boolean regular = true;
  boolean reversed = false;
  boolean drivedirection = regular; 
  // Joystick setup
  /*Button Assignment
    1
    2 - myRobot.setMaxOutput(.5);
    3 - myRobot.setMaxOutput(1);
    4 - myRobot.setMaxOutput(.25); (commented out)
    5 - myRobot.setMaxOutput(.75);
    6 - intakeButton
    7 - intakerev
    8 - arm retract 
    9
    10
    11 - arm extend
    trigger - double xOffset = tx.getDouble(0.0);
    */
  Joystick main = new Joystick(0);
  double speedX = main.getRawAxis(0); // change to whats needed
  double rotatZ = main.getRawAxis(1); // change to whats needed
  
  int speedhalf = 2;
  int speedfull = 3;
  //int speed1quarter = 4;
  int speed3quarter = 5;
  int intakeButton = 6; // the button number for intake
  int intakerev = 7; // the button number for intake rev
  int armretract = 8;
  int driveregular = 9;
  int drivereversed = 10;
  int armextend = 11;
  

  public void robotInit() {
    DigitalInput lsArmOpen = new DigitalInput(lsArmOpenp);//not used
    DigitalInput lsArmClose = new DigitalInput(lsArmClosep);//not used
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
    /*Button Assignment
      1
      2 - myRobot.setMaxOutput(.5);
      3 - myRobot.setMaxOutput(1);
      4 - myRobot.setMaxOutput(.25); (commented out)
      5 - myRobot.setMaxOutput(.75);
      6
      7
      8
      9
      10
      trigger - double xOffset = tx.getDouble(0.0);
    */
    // cmain.start();

    // NetworkTableInstance.getDefault().getTable("limelight").getEntry("<pipepine>").setNumber(1);
    // System.out.println(NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").getNumber(1));

    //Drive direction
    if(drivedirection == regular){myRobot.arcadeDrive(main.getY(), main.getX());} //sets drive to regular
    else if(drivedirection == reversed){myRobot.arcadeDrive(-main.getY(), -main.getX());} //sets drive to reversed
    else{myRobot.arcadeDrive(main.getY(), main.getX());} //defaults to regular drive
    
    //Drive Direction Selection
    if(main.getRawButtonPressed(driveregular)){drivedirection=regular;}
    else if(main.getRawButtonPressed(drivereversed)){drivedirection=reversed;}

    //main.getRawButtonPressed();

    


    if (main.getRawButtonPressed(speedfull)) { // sets speed
      myRobot.setMaxOutput(1);
    } else if (main.getRawButtonPressed(speed3quarter)) {
      myRobot.setMaxOutput(.75);
    } else if (main.getRawButtonPressed(speedhalf)) {
      myRobot.setMaxOutput(.5);
    }
    // else if(main.getRawButtonPressed(4)){
    // myRobot.setMaxOutput(.25);
    // }

    // Reading from serial
    // spread = sp.readString();

    // if serial has given a number larger then 1 parse it into a int
    if (spread.length() > 1) {
      spread = spread.substring(0, spread.length() - 2);
      foo = Integer.parseInt(spread);
    }
    lidar = foo / 25.4;
    double v = tv.getDouble(0.0);//not used
    // System.out.println(foo);

    if (main.getTrigger()) {// if trigger is pressed
      double xOffset = tx.getDouble(0.0);

      System.out.println(xOffset);
      if (Math.abs(xOffset) > 6) {// if the target is far away from center
        // System.out.println("0.02");
        // System.out.println(0.02 * xOffset);
        left.set(0.03 * xOffset);
        right.set(0.03 * xOffset);
      } else if (Math.abs(xOffset) > 3) {// if target is close to center
        // System.out.println("0.05");
        // System.out.println(0.15 * xOffset);
        left.set(0.07 * xOffset);
        right.set(0.07 * xOffset);
      }

      // else{
      // if(Math.abs(xOffset) <=3 & foo > 28){
      // myRobot.arcadeDrive(-.55, 0);
      // System.out.println("active");
      // }

      // }

    }
    // System.out.println(armState);

    if (main.getRawButton(intakeButton)) {// intake and shoter mechinisem power.
      Intake.set(.6);
    } else if (main.getRawButtonReleased(intakeButton)) {
      Intake.stopMotor();
    }
    if (main.getRawButton(intakerev)) {
      Intake.set(-.3);
    } else if (main.getRawButtonReleased(intakerev)) {
      Intake.stopMotor();
    }

    if (main.getRawButtonPressed(armtrigger) & ArmTrans == 0) {
      ArmTrans = 1;
    }

    if (ArmTrans == 1) {
      if (armState == 0) {
        Arm.set(armSpeed);
        if (lsArmOpen.get()) {
          ArmTrans = 0;
          armState = 1;
          Arm.stopMotor();
        }
      } else {
        Arm.set(-armSpeed);
        if (lsArmClose.get()) {
          ArmTrans = 0;
          armState = 0;
          Arm.stopMotor();
        }
      }

    }

    if (main.getRawButton(armextend)) {
      Arm.set(armSpeed);
    } else if (main.getRawButtonReleased(11)) {
      Arm.stopMotor();
    }
    if (main.getRawButton(armretract)) {
      Arm.set(-armSpeed);
    } else if (main.getRawButtonReleased(8)) {
      Arm.stopMotor();
    }

    /*
      if(main.getRawButtonPressed(hatchTrigger)){//is trigger been presses
      if(hatchState == false){//is the solinoid alredy extended if not extend
      hatch.set(DoubleSolenoid.Value.kReverse); hatchState = true;
      System.out.println("true"); //hatch.set(DoubleSolenoid.Value.kOff);
      //System.out.println(hatch.get()); } else{//return to normal
      hatch.set(DoubleSolenoid.Value.kForward); hatchState = false; } }
     */

    // if farther the 28 inches from target move forword
    /*
     * if(main.getTrigger() & foo > 28){ myRobot.arcadeDrive(-.55, 0);
     * System.out.println("active"); } //myRobot.stopMotor();
     * 
     * 
     * }
     */
  }

  // myRobot.setMaxOutput(.5);
  // myRobot.arcadeDrive(main.getRawAxis(1), main.getRawAxis(0));

  /*
   * if(main.getTrigger()){ double xOffset = tx.getDouble(0.0); left.set(.3 *
   * xOffset); right.set(.3* xOffset); }
   */

  @Override
  public void testInit() {

  }

  @Override
  public void testPeriodic() {
    // System.out.println(sp.readString(100));

    // String test = (sp.readString(10));
    // System.out.println(test);
    // sp.flush();
    // spread = sp.readString();
    if (spread.length() > 1) {
      spread = spread.substring(0, spread.length() - 2);
      // String t1 = "";

      foo = Integer.parseInt(spread);
      System.out.println(spread + " " + spread.length());
    }

    // try {
    // spread = spread.trim();
    // System.out.println(spread + " " + spread.length());
    // foo = Integer.parseInt(spread);
    // } catch (NumberFormatException e) {

    // }

    // int foo = Integer.valueOf(spread);
    // int foo = Integer.parseInt(spread);
    // if(){
    // System.out.println("yup its working");

    // }
    // else{
    // System.out.println("its still working");
    // }

    // System.out.println(us.getRangeInches());
    // System.out.println(ultraSonic.getVoltage()*ultraToinch);
    // Timer.delay(.001);

    // double xOffset = tx.getDouble(0.0);
    // System.out.println(xOffset);
    // System.out.println(Math.abs(xOffset));

    // double yOffset = ty.getDouble(0.0);
    // System.out.println((26-21.5)/Math.tan(5+yOffset));
  }

}
