package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
import org.firstinspires.ftc.teamcode.utils.IMU;


@Autonomous(name = "Camera Op Mode")
public class Auto extends LinearOpMode {

    private DcMotor motorFL;
    private DcMotor motorFR;
    private DcMotor motorBL;
    private DcMotor motorBR;

    private SensorColor colorSensor;
    private IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {

        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");
        colorSensor = hardwareMap.get(SensorColor.class, "color");

        imu = new IMU(this, "imu");

        telemetry.addData("Status", "Waiting for start...");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){




//            Move to pos1
//            Check for green
//
//            Rotate
//            Check for green
//
//            Rotate
//            Check for green

            //OR

//            Find level with a threshold




            //Compare highest level

            //Move to correct position

            //Drop piece

            //Move to board

            //Align with correct level

            //Place

            //Park

            //Rotate to face away from player
        }
    }

    private void move(double speed){
        motorBL.setPower(-speed);
        motorBR.setPower(speed);
        motorFL.setPower(-speed);
        motorFR.setPower(speed);
    }

    private void turn(double speed){
        motorBL.setPower(speed);
        motorBR.setPower(speed);
        motorFL.setPower(speed);
        motorFR.setPower(speed);
    }

    private void rotate(double degree){
        double initAngle = imu.getOrientation().firstAngle;
        double target = initAngle + degree;
        double currentAngle = initAngle;

        if(target < initAngle){
            while(currentAngle < target ){
                turn(1);
                currentAngle = imu.getOrientation().firstAngle;
            }
            return;
        }

        if(target > initAngle){
            while(currentAngle > target ){
                turn(-1);
                currentAngle = imu.getOrientation().firstAngle;
            }
            return;
        }

        return;
    }
}
