/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* Codded By:Andrew Levin                                                     */
/* Team#5678                                                                  */
/* name# Solaris                                                              */
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
  int armState, ArmTrans, speedtrigger;
  public double regular, reversed, drivedirection, lidar;
  SmartDash dash = new SmartDash(this);
  public Hatch hatch = new Hatch(this, dash);
  Intake intake = new Intake(this, dash);
  Vision vision = new Vision(this, hatch);
  public SerialPort aNano = null;
  LED lights = new LED(dash, aNano);
  Auto auto = new Auto(this);

  public void robotInit() {
    try {
      aNano = new SerialPort(115200, Port.kUSB1);
      aNano.enableTermination();
      aNano.setReadBufferSize(4);
    } catch (Exception e) {

    }

    dash.init();
    motors();
    vision.network_table_init();
    vision.limelight_init();
    pnumatics_init();
    hatch_init();
    controls_init();
    drive_init();
    vision.sData();
    lights.up();
    vision.table.getEntry("ledMode").setNumber(1);

  }

  @Override
  public void autonomousInit() {
    vision.sData();
    lights.up();
    vision.table.getEntry("stream").setNumber(3);
  }

  @Override
  public void autonomousPeriodic() {
    lidar = vision.lidar;
    // vision.table.getEntry("stream").setNumber(3);
    vision.sData();
    if (dash.A_Chooser.getSelected() == 1) {
      lidar = vision.lidar;
      //cmain.start();
      vision.table.getEntry("ledMode").setNumber(1);
      // cmain.start();
      drive();
      drive_select();
      speed_select();
      intake();
      arm_trans();
      hatch();
      hatch.arm();
      try {
        vision.sData();
        center();
      } catch (Exception e) {

      }

      dash.Always();

    } else {
      lidar = vision.lidar;
      //cmain.start();
      vision.table.getEntry("ledMode").setNumber(1);
      // cmain.start();
      drive();
      drive_select();
      speed_select();
      intake();
      arm_trans();
      hatch();
      hatch.arm();
      dash.Always();
    }
    hatch.arm();
    try {
      vision.sData();
      center();
    } catch (Exception e) {

    }
  }

  @Override
  public void teleopInit() {
    if (dash.Baby_mode.getSelected() == false) {
     // cmain.start();
    }
    vision.sData();
    vision.table.getEntry("stream").setNumber(1);
    vision.table.getEntry("ledMode").setNumber(1);
    lights.up();
  }

  @Override
  public void teleopPeriodic() {
    lidar = vision.lidar;
    // vision.table.getEntry("ledMode").setNumber(1);
    // cmain.start();
    drive();
    drive_select();
    speed_select();
    intake();
    arm_trans();
    hatch();
    //hatch.arm();
    vision.sData();
    dash.Always();
    center();

  }

  @Override
  public void testInit() {

  }

  @Override
  public void testPeriodic() {
    // System.out.println("Close: " + IsArmClose.get());

    // System.out.println("Open: " + IsArmOpen.get());

  }

  void motors() {
    Mleft = new Spark(Robot_Map.left);
    Mright = new Spark(Robot_Map.right);
    MIntake = new Spark(Robot_Map.intake);
    MArm = new Spark(Robot_Map.arm);
  }

  public void pnumatics_init() {
    //cmain = new Compressor(Robot_Map.compressor);
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
    speedtrigger = 1;
    myRobot.setMaxOutput(.9);
  }

  public void drive() {
    myRobot.arcadeDrive(drivedirection * main.getY(), main.getX());
  }

  public void drive_select() {
    if (main.getTriggerPressed()) {
      if (drivedirection == reversed) {
        drivedirection = regular;
        lights.up();
        vision.table.getEntry("stream").setNumber(1);
      } else if (drivedirection == regular) {
        drivedirection = reversed;
        lights.down();
        vision.table.getEntry("stream").setNumber(2);

      }
    }
  }

  public void speed_select() {
    if (dash.Baby_mode.getSelected() == false) {
      // System.out.println(speedtrigger);
      if (main.getRawButtonPressed(Robot_Map.speedchange)) {
        if (speedtrigger <= 0) { // sets speed
          myRobot.setMaxOutput(.9);
          speedtrigger = 1;
          System.out.println("test2");
        } else {
          myRobot.setMaxOutput(.6);
          speedtrigger = 0;
          System.out.println("test");
        }

      }
      if (main.getRawButtonPressed(Robot_Map.syan)) {
        speedtrigger = 2;
      }
      if (speedtrigger == 2) {
        myRobot.setMaxOutput((main.getThrottle() * -1 + 1) / 2);
      }
    }
    if (dash.Baby_mode.getSelected() == true) {
      myRobot.setMaxOutput(.5);
    }

  }

  public void intake() {
    if (dash.Baby_mode.getSelected() == false) {
      intake.intake();
    }
  }

  public void arm_trans() {/*
    if (dash.Baby_mode.getSelected() == false) {
      if (main.getRawButtonPressed(Robot_Map.armtrigger)) {
        hatch.move();

      }
    } */
  }

  public void hatch() {/*
    if (dash.Baby_mode.getSelected() == false) {
      if (main.getRawButtonPressed(Robot_Map.hatchTrigger)) {
        hatch.hatch();
      }
    }*/
  }

  public void center() {

    if (main.getRawButton(Robot_Map.vision)) {// if trigger is pressed vision.visionAim();
      vision.visionAim();
      // vision.visionMove();
      // System.out.println("1");
      // System.out.println("1");
    } else if (main.getRawButtonReleased(Robot_Map.vision)) {
      vision.lidarActive = 0;
      // System.out.println("0");
      // System.out.println("0");
    }
  }
}
