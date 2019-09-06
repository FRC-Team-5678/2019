/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* Codded By:Andrew Levin                                                     */
/* Team#5678                                                                  */
/* name# Solaris                                                              */
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
    Hatch hatch;
    NetworkTableEntry tx, ty, ta, tv;
    public double v, x, y, area, lidar;
    public NetworkTable table;
    int lidarSemiRaw;
    public int lidarActive;
    String lidarRaw;

    public Vision(Robot robot, Hatch hatch) {
        this.robot = robot;
        this.hatch = hatch;
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
        
        hatch.arm(); try{
          lidarRaw = robot.aNano.readString();
          }catch(Exception e){
            
          } 
        if (lidarRaw.length() > 1) {
            lidarRaw = lidarRaw.substring(0, lidarRaw.length() - 2);
            try {
                lidarSemiRaw = Integer.parseInt(lidarRaw);
            } catch (Exception e) {
                lidarSemiRaw = 0;
            }
        }
        lidar = lidarSemiRaw / 25.4;
       //.println(lidar);
    }

    public void visionAim() {
        double xOffset = tx.getDouble(0.0);
        // System.out.println("true");

        // System.out.println(xOffset);
        if (lidarActive == 0) {
            // System.out.println("true");
            if (Math.abs(xOffset) > 6) {// if the target is far away from center
                robot.Mleft.set(0.03 * xOffset);
                robot.Mright.set(0.03 * xOffset);
            } else if (Math.abs(xOffset) > 4) {// if target is close to center
                robot.Mleft.set(0.07 * xOffset);
                robot.Mright.set(0.07 * xOffset);
                // System.out.println("false");
            } else {
                lidarActive = 1;
                // System.out.println("true");
            }
        } else if (Math.abs(xOffset) > 4) {
            lidarActive = 0;
        }
    } 

    public void visionMove() {
        /*
         * if (lidarActive == 1 && lidar > 22) { robot.myRobot.arcadeDrive(.6, 0); }
         * else if (lidar <= 22) {
         * 
         * robot.myRobot.stopMotor(); }
         */
    }

}
