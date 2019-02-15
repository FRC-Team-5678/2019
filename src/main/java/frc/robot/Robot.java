/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* Codded By:Andrew Levin                                                     */
/* Cleaned Up By:Walter Hicks                                                 */
/* Team#5678                                                                  */
/* name # orion                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.classes.*;

public class Robot extends TimedRobot {
  // DECLARATION
  Compressor cmain;
  NetworkTable table;
  NetworkTableEntry tx, ty, ta, tv;
  DifferentialDrive myRobot;
  public Spark Mleft, Mright, MIntake, MArm;
  public Joystick main;
  public DigitalInput IsArmClose, IsArmOpen;
  int foo, armState, ArmTrans;
  boolean  regular, reversed, drivedirection;
  double v, x, y, area, lidar;
  String spread;
  Hatch hatch = new Hatch(this);
  SmartDash dash = new SmartDash();
  Intake intake = new Intake(this);

  public void robotInit() {
    //SerialPort sp = new SerialPort(115200, SerialPort.Port.kUSB1);
    dash.init();
    motors();
    network_table_init();
    limelight_init();
    pnumatics_init();
    hatch_init();
    controls_init();
    drive_init();

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
    cmain.start();
    drive();
    drive_select();
    speed_select();
    wot();
    center();
    intake();
    arm_trans();
    hatch();

  }

  @Override
  public void testInit() {

  }

  @Override
  public void testPeriodic() {
    System.out.println("Close: "+IsArmClose.get());
      
        System.out.println("Open: "+IsArmOpen.get());
   
  }

  void motors() {
    Mleft = new Spark(Robot_Map.left);
    Mright = new Spark(Robot_Map.right);
    MIntake = new Spark(Robot_Map.intake);
    MArm = new Spark(Robot_Map.arm);
  }

  void network_table_init() {
    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
    tv = table.getEntry("tv");
  }

  void limelight_init() {
    v = tv.getDouble(0.0);
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    area = ta.getDouble(0.0);
  }

  void pnumatics_init() {
    cmain = new Compressor(Robot_Map.compressor);
  }


  void hatch_init() {
    armState = 0;
    ArmTrans = 0;
    IsArmClose = new DigitalInput(Robot_Map.LimitArmClosep);
    IsArmOpen = new DigitalInput(Robot_Map.LimitArmOpenp);
  }

  void controls_init() {
    main = new Joystick(Robot_Map.joysticPort);
  }

  void drive_init() {
    // drive setup
    myRobot = new DifferentialDrive(Mleft, Mright);
    regular = true;
    reversed = false;
    drivedirection = regular;
  }

  void andrew_WOT() {
    foo = 0;
    spread = "0";
    lidar = 0;
  }

  void drive() {
    if (drivedirection == regular) {
      myRobot.arcadeDrive(-main.getY(), main.getX());
    } // sets drive to regular
    else if (drivedirection == reversed) {
      myRobot.arcadeDrive(main.getY(), main.getX());
    } // sets drive to reversed
    else {
      myRobot.arcadeDrive(-main.getY(), main.getX());
    } // defaults to regular drive
  }

  void drive_select() {
    if (main.getRawButtonPressed(Robot_Map.driveselector)) {
      if (drivedirection = reversed) {
        drivedirection = regular;
      } else if (drivedirection = regular) {
        drivedirection = reversed;
      }
    }
  }

  void speed_select() {
    if (main.getRawButtonPressed(Robot_Map.speedfull)) { // sets speed
      myRobot.setMaxOutput(1);
    } else if (main.getRawButtonPressed(Robot_Map.speed3quarter)) {
      myRobot.setMaxOutput(.75);
    } else if (main.getRawButtonPressed(Robot_Map.speedhalf)) {
      myRobot.setMaxOutput(.5);
    }
  }

  void wot() {
    /*
    if (spread.length() > 1) {
      spread = spread.substring(0, spread.length() - 2);
      foo = Integer.parseInt(spread);
    }
    lidar = foo / 25.4;
    */
  }

  void center() {
    if (main.getTrigger()) {// if trigger is pressed
      double xOffset = tx.getDouble(0.0);

      System.out.println(xOffset);
      if (Math.abs(xOffset) > 6) {// if the target is far away from center
        Mleft.set(0.03 * xOffset);
        Mright.set(0.03 * xOffset);
      } else if (Math.abs(xOffset) > 3) {// if target is close to center
        Mleft.set(0.07 * xOffset);
        Mright.set(0.07 * xOffset);
      }
    }
  }

  void intake() {
    intake.intake();
  }

  void arm_trans() {
    if (main.getRawButtonPressed(Robot_Map.armtrigger)) {
      
        System.out.println("Close: "+IsArmClose.get());
      
        System.out.println("Open: "+IsArmOpen.get());
      
      
     hatch.move();
     System.out.println(hatch.armState);
    }
  }

  void hatch() {
    if (main.getRawButtonPressed(Robot_Map.hatchTrigger)) {
      hatch.hatch();
    }
  }
}
