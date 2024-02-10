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
    private static int side;

    private static boolean board;

    private static AutonomousDrive autononmousDrive;

    private static LinearOpMode opMode;

    public static void updateStatus(String emoji, String status){
        String s;
        if(AutonomousOpMode.side == Camera.LEFT){
            s = "Left";
        } else if(AutonomousOpMode.side == Camera.CENTER){
            s = "Center";
        }else if(AutonomousOpMode.side == Camera.RIGHT){
            s = "Right";
        }else{
            s = "Unknown";
        }
        opMode.telemetry.addData(">.< Side", s);
        opMode.telemetry.update();

        opMode.telemetry.addData(emoji + " Status", s);
        opMode.telemetry.update();
    }



    public static void run(int color, int fieldPos){
        //Get OpMode from master class
        opMode = Master.getCurrentOpMode();

        AutonomousOpMode.side = Camera.ERROR;
        AutonomousOpMode.color = color;
        AutonomousOpMode.fieldPos = fieldPos;
        AutonomousOpMode.autononmousDrive = new AutonomousDrive();

        opMode.telemetry.addData("STATUS", "^-^ Waiting for start");
        opMode.telemetry.update();

        Camera.init();

        opMode.waitForStart();

        //Get side from camera
        AutonomousOpMode.side = Camera.run();


        updateStatus(":)", "Done taking image... Preparing for awesome...");




        if(AutonomousOpMode.fieldPos == LEFT){
            //park side

            if(color == RED){
                board = true;
                redLeft();
            }else if(color == BLUE){
                board = true;
                blueLeft();
            }

        }else if(AutonomousOpMode.fieldPos == RIGHT){
            //do stuff side

            if(color == RED){
                board = false;
                redLeft();
            }else if(color == BLUE){
                board = false;
                blueLeft();
            }

        }

        opMode.telemetry.addData("STATUS", ":D All done. Hopefully it worked.");
        opMode.telemetry.update();
    }

    private static void doBoard() {
        updateStatus(":P", "Placing on board");

        double time = 1.4;
        if (side == Camera.CENTER) {
            time += 0.9;
        }

        autononmousDrive.driveForwardByTime(time, -0.5);

        if (side == Camera.CENTER) {
            if (color == BLUE) {
                autononmousDrive.strafeByTime(1.5, 0.5);
            }
            if (color == RED) {
                autononmousDrive.strafeByTime(1.5, -0.5);
            }
        }

        if (color == RED) {
            autononmousDrive.turnByTime(2.35, -0.5);
        } else {
            autononmousDrive.turnByTime(2.35, 0.5);
        }

        Tools.doForTime(0.2, () -> {
        });

        autononmousDrive.driveForwardByTime(1.5, 0.5);

        if (color == BLUE) {
            if(side == Camera.LEFT){
                autononmousDrive.strafeByTime(.9, -0.5);
            } else if (side == Camera.CENTER) {
                autononmousDrive.strafeByTime(1.8, -0.5);
            }
        }

        if(color == RED){
            if(side == Camera.RIGHT){
                autononmousDrive.strafeByTime(.9, 0.5);
            }else if (side == Camera.CENTER) {
                autononmousDrive.strafeByTime(1.8, 0.5);
            }
        }


        autononmousDrive.driveForwardByTime(2.3, 0.5);

        autononmousDrive.armByTime(1, 0.5);
        autononmousDrive.armByTime(1, -0.5);
    }




    private static void blueLeft(){
        //other 3
        double time = 2.8;
        if(side == Camera.CENTER){
            autononmousDrive.driveForwardByTime(time+0.9, 0.5);
            Tools.doForTime(1, () -> {
                autononmousDrive.dropper();
            });
            if(board) doBoard();
        }
        if(side == Camera.LEFT){
            autononmousDrive.driveForwardByTime(time, 0.5);
            autononmousDrive.strafeByTime(1.5, 0.5);
            Tools.doForTime(1, () -> {
                autononmousDrive.dropper();
            });
            if(board) doBoard();
        }
        if(side == Camera.RIGHT){
            autononmousDrive.driveForwardByTime(time, 0.5);
            autononmousDrive.strafeByTime(1.5, -0.5);
            Tools.doForTime(1, () -> {
                autononmousDrive.dropper();
            });
        }
    }

    private static void redLeft(){
        double time = 2.8;
        if(side == Camera.CENTER){
            autononmousDrive.driveForwardByTime(time+0.7, 0.5);
            Tools.doForTime(1, () -> {
                autononmousDrive.dropper();
            });
            if(board) doBoard();
        }
        if(side == Camera.LEFT){
            autononmousDrive.driveForwardByTime(time, 0.5);
            autononmousDrive.strafeByTime(1.5, 0.5);
            Tools.doForTime(1, () -> {
                autononmousDrive.dropper();
            });
        }
        if(side == Camera.RIGHT){
            autononmousDrive.driveForwardByTime(time, 0.5);
            autononmousDrive.strafeByTime(1.5, -0.5);
            Tools.doForTime(1, () -> {
                autononmousDrive.dropper();
            });
            if(board) doBoard();
        }
    }

    public static int getColor(){
        return color;
    }
}
