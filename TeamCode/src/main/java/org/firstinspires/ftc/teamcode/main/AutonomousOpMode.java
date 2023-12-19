package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.AutonomousDrive;
import org.firstinspires.ftc.teamcode.utils.Camera;
import org.firstinspires.ftc.teamcode.utils.Master;
import org.firstinspires.ftc.teamcode.utils.Tools;

/** Class that runs during autonomous
 *
 * @author github.com/jakeslye
 */
public class AutonomousOpMode {

    public static final int RED = 0;
    public static final int BLUE = 1;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private static int color;
    private static int fieldPos;

    private static AutonomousDrive autononmousDrive;

    private static LinearOpMode opMode;



    public static void run(int color, int fieldPos){
        //Get OpMode from master class
        opMode = Master.getCurrentOpMode();

        AutonomousOpMode.color = color;
        AutonomousOpMode.fieldPos = fieldPos;
        AutonomousOpMode.autononmousDrive = new AutonomousDrive();

        opMode.telemetry.addData("STATUS", "^-^ Waiting for start");
        opMode.telemetry.update();

        opMode.waitForStart();

        //Get side from camera
        int side = Camera.run();

        String s;
        if(side == Camera.LEFT){
            s = "Left";
        } else if(side == Camera.CENTER){
            s = "Center";
        }else if(side == Camera.RIGHT){
            s = "Right";
        }else{
            s = "Unknown";
        }


        Tools.doForTime(1, () -> {
            opMode.telemetry.addData("Side", s + "... Proceeding in one second...");
            opMode.telemetry.update();
        });




        if(AutonomousOpMode.fieldPos == LEFT){
            //park side

            if(color == RED){
                redLeft(side);
            }else if(color == BLUE){
                blueLeft(side);
            }

        }else if(AutonomousOpMode.fieldPos == RIGHT){
            //do stuff side

            if(color == RED){
            }else if(color == BLUE){
            }

        }

        opMode.telemetry.addData("STATUS", ":D All done. Hopefully it worked.");
        opMode.telemetry.update();
    }



    private static void doPlacement(int color, int side, double power){
        double distance = 0.0;

        if(color == BLUE) {
            if(side == Camera.LEFT){
                distance = 2.1;
            }else if(side == Camera.CENTER || side == Camera.ERROR){
                distance = 2.9;
            }else if(side == Camera.RIGHT){
                distance = 3.7;
            }
        }else{
            if(side == Camera.LEFT){
                distance = 3.7;
            }else if(side == Camera.CENTER || side == Camera.ERROR){
                distance = 2.9;
            }else if(side == Camera.RIGHT){
                distance = 2.1 ;
            }
        }

        if(color == BLUE) {
            AutonomousOpMode.autononmousDrive.strafeByTime(distance, -power);
            //AutonomousOpMode.autononmousDrive.turn(90.0, 0.25);
        }else{
            AutonomousOpMode.autononmousDrive.strafeByTime(distance, power);
            //AutonomousOpMode.autononmousDrive.turn(-90.0, 0.25);
        }


        Tools.doForTime(0.5, () -> {
            opMode.telemetry.addData("STATUS", ":P Waiting...");
            opMode.telemetry.update();
        });

        AutonomousOpMode.autononmousDrive.scoopByTime(0.2, 0.5);

        Tools.doForTime(0.3, () -> {
            opMode.telemetry.addData("STATUS", ":P Waiting for scoop...");
            opMode.telemetry.update();
        });

        AutonomousOpMode.autononmousDrive.armByTime(1.7, 0.5);

        Tools.doForTime(0.3, () -> {
            opMode.telemetry.addData("STATUS", ":P Waiting for arm...");
            opMode.telemetry.update();
        });

        AutonomousOpMode.autononmousDrive.armByTime(2, -0.4);

        Tools.doForTime(0.3, () -> {
            opMode.telemetry.addData("STATUS", ":P Waiting for arm...");
            opMode.telemetry.update();
        });

        AutonomousOpMode.autononmousDrive.scoopByTime(0.3, -0.5);

        Tools.doForTime(0.3, () -> {
            opMode.telemetry.addData("STATUS", ":P Waiting for scoop...");
            opMode.telemetry.update();
        });
    }




    private static void blueLeft(int side){
        AutonomousOpMode.autononmousDrive.driveForwardByTime(0.5, 0.5);
        AutonomousOpMode.autononmousDrive.turn(90.0, 0.25);
        AutonomousOpMode.autononmousDrive.driveForwardByTime(3.8, 0.5);


        doPlacement(BLUE, side, -0.5);
    }

    private static void redLeft(int side){
        AutonomousOpMode.autononmousDrive.driveForwardByTime(0.5, 0.5);
        AutonomousOpMode.autononmousDrive.turn(-90.0, 0.25);
        AutonomousOpMode.autononmousDrive.driveForwardByTime( 3.8, 0.5);

        doPlacement(RED, side, 0.5);
    }

    public static int getColor(){
        return color;
    }
}
