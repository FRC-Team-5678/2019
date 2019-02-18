/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.classes;
import edu.wpi.first.wpilibj.SerialPort;


/**
 * Add your docs here.
 */
public class LED {

    SmartDash dash;
    SerialPort arduino;
    Robot_Map Map;
    public LED(SmartDash dash, SerialPort arduino){
        this.dash = dash;
        this.arduino = arduino;
      
    }

    public void up(){
        update(true);
    }
    public void down(){
        update(false);
    }

    
    private void update(boolean up){ 
        int dir = Robot_Map.Down;
        if(up){
            dir = Robot_Map.Up;
        }
        //arduino.writeString(dash.m_chooser.toString() +  Integer.toString(dir));
        
    }
}
