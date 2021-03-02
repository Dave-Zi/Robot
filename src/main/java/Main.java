import Boards.GrovePiBoard;
import Boards.IBoard;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //your stuff here
        IBoard grove = new GrovePiBoard("./classes/Robot.json");

        while (true){
            double d = grove.getDoubleSensorData("D4", 0);
            if (d < 20){
                grove.setSensorData("D2", true);
            }
            else if (d >= 20){
                grove.setSensorData("D2", false);
            }
            EV3 ev3 = new EV3("rfcomm0");
        }

//        //end program forcefully
//        EV3 ev3 = new EV3("rfcomm0");
//        System.exit(0);
    }
}
