/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.classes;


import frc.robot.Robot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Add your docs here.
 */

public class Vision {
    Robot robot;
    NetworkTableEntry tx, ty, ta, tv;
    double v, x, y, area, lidar;
    NetworkTable table;
    int lidarSemiRaw;
    public int lidarActive;
    String lidarRaw;

    public Vision(Robot robot){
        this.robot = robot;
    }

    public void network_table_init() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
    }

    public void limelight_init() {
        v = tv.getDouble(0.0);
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        area = ta.getDouble(0.0);
        lidarSemiRaw = 0;
        lidarRaw = "0";
        lidar = 0;
        lidarActive = 0;
    }

    public void sData() {
        lidarRaw = robot.aNano.readString();
        if (lidarRaw.length() > 1) {
            lidarRaw = lidarRaw.substring(0, lidarRaw.length() - 2);
            lidarSemiRaw = Integer.parseInt(lidarRaw);
        }
        lidar = lidarSemiRaw / 25.4;
       // System.out.println(lidar);
    }


    public void visionAim() {
        double xOffset = tx.getDouble(0.0);

        // System.out.println(xOffset);
        if (lidarActive == 0) {
            if (Math.abs(xOffset) > 6) {// if the target is far away from center
                robot.Mleft.set(0.03 * xOffset);
                robot.Mright.set(0.03 * xOffset);
            } else if (Math.abs(xOffset) > 3) {// if target is close to center
                robot.Mleft.set(0.07 * xOffset);
                robot.Mright.set(0.07 * xOffset);
            } else {
                lidarActive = 1;
            }
        }
    }

    public void visionMove() {
        if(lidarActive ==1 && lidar >= 6){
            robot.myRobot.arcadeDrive(.3, 0);
        }
    }
}