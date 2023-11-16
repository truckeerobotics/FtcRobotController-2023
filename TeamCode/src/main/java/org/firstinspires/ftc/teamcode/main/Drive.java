package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.IMU;
import org.firstinspires.ftc.teamcode.utils.Toggle;
import org.firstinspires.ftc.teamcode.utils.Vector2;

@TeleOp(name="Drive", group="OpMode")
public class Drive extends LinearOpMode{

    //declare booleans
    private boolean debug;
    private boolean driverOrientationMode;

    //declare motor variables
    private DcMotor motorFL;
    private DcMotor motorFR;
    private DcMotor motorBL;
    private DcMotor motorBR;
    private DcMotor motorAL;
    private DcMotor motorAR;
    private DcMotor motorScoop;

    //declare driving variables
    private double y;
    private double x;
    private double rx;
    private double denominator;
    private double frontLeftPower;
    private double backLeftPower;
    private double frontRightPower;
    private double backRightPower;
    private double armPower;
    private double scoopPower;

    //declare custom classes
    private Toggle toggle;
    //private IMU imu;



    @Override
    public void runOpMode() {
        //initialize variables
        debug = false;
        driverOrientationMode = true;

        //hardware map
        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");
        motorAL = hardwareMap.get(DcMotor.class, "armL");
        motorAR = hardwareMap.get(DcMotor.class, "armR");
        motorScoop = hardwareMap.get(DcMotor.class, "scoop");



        //initialize custom classes
        toggle = new Toggle(gamepad1, gamepad2);
        //imu = new IMU(this, "imu");

        telemetry.addData("ALERT", "Waiting for start.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            //setup toggle key handlers
            if(toggle.onPush(gamepad1.a, "controller1ButtonA")) debug = !debug;
            if(toggle.onPush(gamepad1.x, "controller1ButtonX")) driverOrientationMode = !driverOrientationMode;

            //debug tool tip for enabling/disabling debug mode
            if(debug) {
                telemetry.addData("WARNING", "Press A to turn off debug mode.");
            }else {
                telemetry.addData("TIP", "Press A to turn on debug mode.");
            }
            if(driverOrientationMode) {
                telemetry.addData("ALERT", "Driver Orientation Mode is on. (NOT WORKING)");
            }else {
                telemetry.addData("TIP", "Press X to turn on Driver Orientation Mode. (NOT WORKING)");
            }

            //centralizes the robots movement to the drivers POV
//            if(driverOrientationMode) {
//                Vector2 rot = imu.getHeadingCorrection(x, y);
//                x = rot.getX();
//                y = rot.getY();
//                if (debug) {
//                    telemetry.addData("rot", rot.toString());
//                }
//            }

            //mecanum drive math
            double y = gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            frontLeftPower = (y - x - rx) / denominator;
            backLeftPower = (-y + x - rx) / denominator;
            frontRightPower = (y + x + rx) / denominator;
            backRightPower = (-y - x + rx) / denominator;

            motorFL.setPower(-frontLeftPower);
            motorBL.setPower(-backLeftPower);
            motorFR.setPower(frontRightPower);
            motorBR.setPower(backRightPower);



            armPower = gamepad2.left_stick_y * 0.25;
            scoopPower = gamepad2.right_stick_y * 0.5;

            motorAL.setPower(armPower);
            motorAR.setPower(-armPower);
            motorScoop.setPower(scoopPower);







            //print out remaining debug variables
            if(debug){
                telemetry.addData("y", y);
                telemetry.addData("x", x);
                telemetry.addData("rx", rx);
                telemetry.addData("denominator", denominator);
                telemetry.addData("backLeftPower", backLeftPower);
                telemetry.addData("backRightPower", backRightPower);
                telemetry.addData("frontLeftPower", frontLeftPower);
                telemetry.addData("frontRightPower", frontRightPower);
                telemetry.addData("armPower", armPower);
                telemetry.addData("scoopPower", scoopPower);
            }


            //update debug screen
            telemetry.update();
        }

    }
}
