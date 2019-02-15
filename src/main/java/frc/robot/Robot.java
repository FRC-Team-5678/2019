/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* Codded By:Andrew Levin                                                     */
/* Cleaned Up By:Walter Hicks                                                 */
/* Team#5678                                                                  */
/* name # orion                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
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
  Robot_Map map = new Robot_Map();
  Hatch hatch = new Hatch();
  SmartDash dash = new SmartDash();
  Intake intake = new Intake();

  public void robotInit() {
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

    if (spread.length() > 1) {
      spread = spread.substring(0, spread.length() - 2);
      // String t1 = "";

      foo = Integer.parseInt(spread);
      System.out.println(spread + " " + spread.length());
    }
  }

  void ports() {
  }

  void motors() {
    Mleft = new Spark(map.left);
    Mright = new Spark(map.right);
    MIntake = new Spark(map.intake);
    MArm = new Spark(map.arm);
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
    cmain = new Compressor(map.compressor);
  }


  void hatch_init() {
    armState = 0;
    ArmTrans = 0;
    IsArmClose = new DigitalInput(map.LimitArmClosep);
    IsArmOpen = new DigitalInput(map.LimitArmOpenp);
  }

  void controls_init() {
    main = new Joystick(map.joysticPort);
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
      myRobot.arcadeDrive(main.getY(), main.getX());
    } // sets drive to regular
    else if (drivedirection == reversed) {
      myRobot.arcadeDrive(-main.getY(), -main.getX());
    } // sets drive to reversed
    else {
      myRobot.arcadeDrive(main.getY(), main.getX());
    } // defaults to regular drive
  }

  void drive_select() {
    if (main.getRawButtonPressed(map.driveselector)) {
      if (drivedirection = reversed) {
        drivedirection = regular;
      } else if (drivedirection = regular) {
        drivedirection = reversed;
      }
    }
  }

  void speed_select() {
    if (main.getRawButtonPressed(map.speedfull)) { // sets speed
      myRobot.setMaxOutput(1);
    } else if (main.getRawButtonPressed(map.speed3quarter)) {
      myRobot.setMaxOutput(.75);
    } else if (main.getRawButtonPressed(map.speedhalf)) {
      myRobot.setMaxOutput(.5);
    }
  }

  void wot() {
    if (spread.length() > 1) {
      spread = spread.substring(0, spread.length() - 2);
      foo = Integer.parseInt(spread);
    }
    lidar = foo / 25.4;
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
    if (main.getRawButton(map.armtrigger)) {
      hatch.move();
    }
  }

  void hatch() {
    if (main.getRawButtonPressed(map.hatchTrigger)) {
      hatch.hatch();
    }
  }
}
