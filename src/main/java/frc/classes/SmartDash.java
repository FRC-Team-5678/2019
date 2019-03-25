/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* Codded By:Andrew Levin                                                     */
/* Team#5678                                                                  */
/* name# Solaris                                                              */
/*----------------------------------------------------------------------------*/

package frc.classes;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * Add your docs here.
 */
public class SmartDash {
    Robot robot;
    public String Hatch_Key = "Hatch State";
    public String Arm_State = "Arm State";
    private static final int Open = 1;
    private static final int Close = 2;
    private static final int blue = 20;
    private static final int red = 10;
    public final SendableChooser<Integer> m_chooser = new SendableChooser<>();
    public final SendableChooser<Integer> A_Chooser = new SendableChooser<>();
    public final SendableChooser<Integer> Baby_mode = new SendableChooser<>();

    public SmartDash(Robot robot) {
        this.robot = robot;
    }

    public void init() {
        SmartDashboard.putBoolean(Hatch_Key, false);
        m_chooser.setDefaultOption("Blue", blue);
        m_chooser.addOption("Red", red);
        SmartDashboard.putNumber("Arm Open", robot.hatch.armState);
        SmartDashboard.putData("Side Color", m_chooser);
        SmartDashboard.putData(Arm_State, A_Chooser);
        A_Chooser.setDefaultOption("Open", Open);
        A_Chooser.addOption("Close", Close);
        SmartDashboard.putNumber("Distince", robot.lidar);
        Baby_mode.setDefaultOption("off", 0);
        Baby_mode.addOption("on", 1);

        // SmartDashboard.putBoolean("Open", robot.IsArmOpen.get());
        // SmartDashboard.putBoolean("close", robot.IsArmClose.get());

    }

    public void Always() {
        System.out.println(robot.lidar);
        SmartDashboard.putNumber("Arm Open", robot.hatch.armState);
        SmartDashboard.putNumber("Distince", robot.lidar);
    }
}
