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
    Robot_Map map = new Robot_Map();
    SmartDash dash = new SmartDash();
    Robot bot = new Robot();
    boolean hatchState = false;
    DoubleSolenoid hatch = new DoubleSolenoid(map.hatch_Solenoid_1, map.hatch_Solenoid_2);
    int ArmTrans = 1;
    public int armState = 0;

    public void move() {
        if (ArmTrans == 1) {
            if (armState == 0) {
                bot.MArm.set(map.armSpeed);
                if (bot.IsArmOpen.get()) {
                    ArmTrans = 0;
                    armState = 1;
                    bot.MArm.stopMotor();
                }
            } else {
                bot.MArm.set(-map.armSpeed);
                if (bot.IsArmOpen.get()) {
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
}
