/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* Codded By:Andrew Levin                                                     */
/* Team#5678                                                                  */
/* name# Solaris                                                              */
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
    public void move() {//tells the arm to start moving
        ArmTrans = 1;
        //System.out.println("Move1");
      
    }

    public void arm(){
       if(dash.Baby_mode.getSelected() == false){
        if (ArmTrans == 1) {
           // System.out.println("Move2");
            if (armState == 0) {
               // System.out.println("Move3");
                bot.MArm.set(Robot_Map.armSpeed);
                if (!bot.IsArmOpen.get()) {
                 //   System.out.println("Move4");
                    ArmTrans = 0;
                    armState = 1;
                    bot.MArm.stopMotor();
                }
            } else {
                //System.out.println("Move6");
                bot.MArm.set(-Robot_Map.armSpeed);
                if (!bot.IsArmClose.get()) {
                   // System.out.println("Move5");
                    ArmTrans = 0;
                    armState = 0;
                    bot.MArm.stopMotor();
                }
            }
        }
    }
    if(dash.Baby_mode.getSelected() == true){
        
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
        if(dash.Baby_mode.getSelected() == false){
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
    if(dash.Baby_mode.getSelected() == true){}

    }

   
}
