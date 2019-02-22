/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* Codded By:Andrew Levin                                                     */
/* Cleaned Up By:Walter Hicks                                                 */
/* Team#5678                                                                  */
/* name# Solaris                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.classes.*;

public class Robot extends TimedRobot {
  // DECLARATION
  public Compressor cmain;
  public DifferentialDrive myRobot;
  public Spark Mleft, Mright, MIntake, MArm;
  public Joystick main;
  public DigitalInput IsArmClose, IsArmOpen;
  int armState, ArmTrans;
  public double regular, reversed, drivedirection;
  SmartDash dash = new SmartDash(this);
  public Hatch hatch = new Hatch(this, dash);
  Intake intake = new Intake(this, dash);
  Vision vision = new Vision(this, hatch);
  public SerialPort aNano = new SerialPort(115200, Port.kUSB1);
  LED lights = new LED(dash, aNano);
  Auto auto = new Auto(this);

  public void robotInit() {
    aNano.enableTermination();
    aNano.setReadBufferSize(4);
    dash.init();
    motors();
    vision.network_table_init();
    vision.limelight_init();
    pnumatics_init();
    hatch_init();
    controls_init();
    drive_init();
    vision.sData();

    vision.table.getEntry("ledMode").setNumber(1);
  }

  @Override
  public void autonomousInit() {
    vision.sData();
  }

  @Override
  public void autonomousPeriodic() {
    vision.table.getEntry("stream").setNumber(3);
    vision.sData();
    if (dash.A_Chooser.getSelected() == 1) {
      hatch.Arm_Open();
      cmain.start();
      // cmain.stop();
      drive();
      drive_select();
      speed_select();
      center();
      intake();
      arm_trans();
      hatch();
      hatch.arm();
      vision.sData();
      dash.Always();
    } else {
      cmain.start();
      // cmain.stop();
      drive();
      drive_select();
      speed_select();
      center();
      // intake();
      arm_trans();
      hatch();
      hatch.arm();
      vision.sData();
      dash.Always();
    }
  }

  @Override
  public void teleopInit() {
    vision.sData();
   
        vision.table.getEntry("stream").setNumber(1);
  }

  @Override
  public void teleopPeriodic() {

    cmain.start();
    vision.table.getEntry("ledMode").setNumber(3);
    // cmain.stop();
    drive();
    drive_select();
    speed_select();
    center();
    intake();
    arm_trans();
    hatch();
    hatch.arm();
    vision.sData();
    dash.Always();

  }

  @Override
  public void testInit() {

  }

  @Override
  public void testPeriodic() {
    System.out.println("Close: " + IsArmClose.get());

    System.out.println("Open: " + IsArmOpen.get());

  }

  void motors() {
    Mleft = new Spark(Robot_Map.left);
    Mright = new Spark(Robot_Map.right);
    MIntake = new Spark(Robot_Map.intake);
    MArm = new Spark(Robot_Map.arm);
  }

  public void pnumatics_init() {
    cmain = new Compressor(Robot_Map.compressor);
  }

  public void hatch_init() {
    armState = 0;
    ArmTrans = 0;
    IsArmClose = new DigitalInput(Robot_Map.LimitArmClosep);
    IsArmOpen = new DigitalInput(Robot_Map.LimitArmOpenp);
  }

  public void controls_init() {
    main = new Joystick(Robot_Map.joysticPort);
  }

  public void drive_init() {
    // drive setup
    myRobot = new DifferentialDrive(Mleft, Mright);
    regular = 1;
    reversed = -1;
    drivedirection = reversed;
  }

  public void drive() {
    /*
     * if (drivedirection == regular) { myRobot.arcadeDrive(-main.getY(),
     * main.getX()); } // sets drive to regular else if (drivedirection == reversed)
     * { myRobot.arcadeDrive(main.getY(), main.getX()); } // sets drive to reversed
     * else { myRobot.arcadeDrive(-main.getY(), main.getX()); } // defaults to
     * regular drive
     */
    myRobot.arcadeDrive(drivedirection * main.getY(), main.getX());
  }

  public void drive_select() {
    if (main.getTriggerPressed()) {
      if (drivedirection == reversed) {
        drivedirection = regular;
        lights.up();
        vision.table.getEntry("stream").setNumber(2);
      } else if (drivedirection == regular) {
        drivedirection = reversed;
        lights.down();
        vision.table.getEntry("stream").setNumber(1);

      }
    }
  }

  public void speed_select() {
    if (main.getRawButtonPressed(Robot_Map.speedfull)) { // sets speed
      myRobot.setMaxOutput(1);
    } else if (main.getRawButtonPressed(Robot_Map.speed3quarter)) {
      myRobot.setMaxOutput(.75);
    } else if (main.getRawButtonPressed(Robot_Map.speedhalf)) {
      myRobot.setMaxOutput(.625);
    }
  }

  public void center() {

    if (main.getRawButton(Robot_Map.vision)) {// if trigger is pressed vision.visionAim();
      vision.visionAim();
      vision.visionMove();
      //System.out.println("1");
    } else if (main.getRawButtonReleased(Robot_Map.vision)) {
      vision.lidarActive = 0;
      //System.out.println("0");
    }
  }

  public void intake() {
    intake.intake();
  }

  public void arm_trans() {
    if (main.getRawButtonPressed(Robot_Map.armtrigger)) {
      hatch.move();

    }
  }

  public void hatch() {
    if (main.getRawButtonPressed(Robot_Map.hatchTrigger)) {
      hatch.hatch();
    }
  }
}
