package org.firstinspires.ftc.teamcode.main.Auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.utils.Camera;

import java.util.concurrent.ExecutionException;

public class AutonomousOpMode {

    public static final int RED = 0;
    public static final int BLUE = 1;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private static LinearOpMode autoOpMode;

    private static DcMotor motorFL;
    private static DcMotor motorFR;
    private static DcMotor motorBL;
    private static DcMotor motorBR;
    private static DcMotor motorAL;
    private static DcMotor motorAR;
    private static DcMotor motorScoop;

    private static int color;
    private static int fieldPos;

    private static int side;

    public static void run(LinearOpMode opmode, int color, int fieldPos){
        AutonomousOpMode.color = color;
        AutonomousOpMode.autoOpMode = opmode;
        AutonomousOpMode.fieldPos = fieldPos;

        side = Camera.run();

        if(side != -1){
            autoOpMode.telemetry.addData("Side", side);
        }else{
            autoOpMode.telemetry.addData("Side", "Oh god everything is bad!");
        }

        autoOpMode.telemetry.addData("run", "Waiting for start :3");
        autoOpMode.telemetry.update();

        autoOpMode.waitForStart();


        autoOpMode.telemetry.addData("run", "Starting Autonomous...");
        autoOpMode.telemetry.update();

/*        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");
        motorAL = hardwareMap.get(DcMotor.class, "armL");
        motorAR = hardwareMap.get(DcMotor.class, "armR");
        motorScoop = hardwareMap.get(DcMotor.class, "scoop");*/

        if(side == LEFT){
            if(color == RED){
                redLeft(side);
            }else if(color == BLUE){
                blueLeft(side);
            }
        }else if(side == RIGHT){
            if(color == RED){
                redRight(side);
            }else if(color == BLUE){
                blueRight(side);
            }
        }
    }

    private static void blueRight(int side){
        autoOpMode.telemetry.addData("starting pos", "Blue right");
        autoOpMode.telemetry.update();
    }

    private static void redRight(int side){
        autoOpMode.telemetry.addData("starting pos", "Red right");
        autoOpMode.telemetry.update();
    }

    private static void blueLeft(int side){
        autoOpMode.telemetry.addData("starting pos", "Blue left");
        autoOpMode.telemetry.update();
    }

    private static void redLeft(int side){
        autoOpMode.telemetry.addData("starting pos", "red left");
        autoOpMode.telemetry.update();
    }

    public static LinearOpMode getAutoOpMode(){
        return autoOpMode;
    }

    public static int getColor(){
        return color;
    }
}
