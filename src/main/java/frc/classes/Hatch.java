/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.classes;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.classes.Robot_Map;
import frc.classes.SmartDash;
import frc.robot.Robot;

public class Hatch {
    SmartDash dash;
    Robot bot;
   public boolean hatchState = false;
    public DoubleSolenoid hatch = new DoubleSolenoid(Robot_Map.hatch_Solenoid_1, Robot_Map.hatch_Solenoid_2);
    public int ArmTrans = 0;
    public int armState = 1;
    public int Auto_start = 0;


    public Hatch(Robot robot, SmartDash dash) {
        this.bot = robot;
        this.dash = dash;
    }
    public void move() {
        ArmTrans = 1;
        System.out.println("Move1");
      
    }

    public void arm(){
        if (ArmTrans == 1) {
            System.out.println("Move2");
            if (armState == 0) {
                System.out.println("Move3");
                bot.MArm.set(Robot_Map.armSpeed);
                if (!bot.IsArmOpen.get()) {
                    System.out.println("Move4");
                    ArmTrans = 0;
                    armState = 1;
                    bot.MArm.stopMotor();
                }
            } else {
                System.out.println("Move6");
                bot.MArm.set(-Robot_Map.armSpeed);
                if (!bot.IsArmClose.get()) {
                    System.out.println("Move5");
                    ArmTrans = 0;
                    armState = 0;
                    bot.MArm.stopMotor();
                }
            }
        }

    }

    public void hatch() {
        if (hatchState == false) {// is the solinoid alredy extended if not extend
            hatch.set(DoubleSolenoid.Value.kReverse);
            hatchState = true;
        } else {
            hatch.set(DoubleSolenoid.Value.kForward);
            hatchState = false;
        }
        SmartDashboard.putBoolean(dash.Hatch_Key, hatchState);
    }
    public void Arm_Open() {
        if (Auto_start <= 2) {
            if (hatchState == false) {// is the solinoid alredy extended if not extend
                hatch.set(DoubleSolenoid.Value.kReverse);
                hatchState = true;
            } else {
                hatch.set(DoubleSolenoid.Value.kForward);
                hatchState = false;
            }
            Auto_start++;
        }

    }
}
