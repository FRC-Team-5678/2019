/*----------------------------------------------------------------------------*/
/* Deep Space 2019                                                            */
/* Codded By:Andrew Levin                                                     */
/* Team#5678                                                                  */
/* name# Solaris                                                              */
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

    public void ledRambow(){
        arduino.writeString(Integer.toString(31));
    }
  
    public void up(){
       // System.out.println("up");
      try{
            update(false);
          }catch(Exception e){
            
          }
        
    }
    public void down(){
        try{
            update(true);
          }catch(Exception e){
            
          }
        
        //System.out.println("down");

    }

    
    private void update(boolean up){ 
        int dir = Robot_Map.Down;
        if(up){
            dir = Robot_Map.Up;
        }
        //System.out.println(dash.m_chooser.getSelected() +  dir);
        arduino.writeString(Integer.toString(dash.m_chooser.getSelected() +  dir));
        
    }
}
