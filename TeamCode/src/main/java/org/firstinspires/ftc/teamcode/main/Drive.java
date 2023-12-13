package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.teamcode.utils.Master;
import org.firstinspires.ftc.teamcode.utils.Toggle;

/** Class that runs in standard game.
 *
 * @author github.com/jakeslye
 */
public class Drive{

    //declare booleans
    private static boolean debug;
    private static boolean driverOrientationMode;

    //declare motor variables
    private static DcMotor motorFL;
    private static DcMotor motorFR;
    private static DcMotor motorBL;
    private static DcMotor motorBR;
    private static DcMotor motorA;
    private static DcMotor motorScoop;

    private static double denominator;
    private static double frontLeftPower;
    private static double backLeftPower;
    private static double frontRightPower;
    private static double backRightPower;
    private static double armPower;
    private static double scoopPower;

    //declare custom classes
    private static Toggle toggle;
    private static BNO055IMU imu;

    private static double botHeading;
    private static double rotX;
    private static double rotY;



    public static void run() {
        LinearOpMode opmode = Master.getCurrentOpMode();

        debug = false;
        driverOrientationMode = true;

        //hardware map
        motorFL = opmode.hardwareMap.get(DcMotor.class, "motorFL");
        motorFR = opmode.hardwareMap.get(DcMotor.class, "motorFR");
        motorBL = opmode.hardwareMap.get(DcMotor.class, "motorBL");
        motorBR = opmode.hardwareMap.get(DcMotor.class, "motorBR");
        motorA = opmode.hardwareMap.get(DcMotor.class, "arm");
        motorScoop = opmode.hardwareMap.get(DcMotor.class, "scoop");

        imu = opmode.hardwareMap.get(BNO055IMU.class, "imu");

        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);


        toggle = new Toggle();


        opmode.telemetry.addData("ALERT", "Waiting for start.");
        opmode.telemetry.update();

        opmode.waitForStart();

        while (opmode.opModeIsActive()) {

            //Setup toggle key handlers
            if(toggle.onPush(opmode.gamepad1.a, "controller1ButtonA")) debug = !debug;
            if(toggle.onPush(opmode.gamepad1.x, "controller1ButtonX")) driverOrientationMode = !driverOrientationMode;

            //Debug tool tip for enabling/disabling debug mode
            if(debug) {
                opmode.telemetry.addData("WARNING", "Press A to turn off debug mode.");
            }else {
                opmode.telemetry.addData("TIP", "Press A to turn on debug mode.");
            }
            if(driverOrientationMode) {
                opmode.telemetry.addData("ALERT", "Driver Orientation Mode is on. (NOT WORKING)");
            }else {
                opmode.telemetry.addData("TIP", "Press X to turn on Driver Orientation Mode. (NOT WORKING)");
            }


            //mecanum drive math
            double y = opmode.gamepad1.left_stick_y;
            double x = opmode.gamepad1.left_stick_x * 1.1;
            double rx = opmode.gamepad1.right_stick_x;

            //Orient the robot to the driver
            if(driverOrientationMode){
                botHeading = imu.getAngularOrientation().firstAngle;
                rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
                rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);
            }else{
                rotX = x;
                rotY = y;
            }

            // Denominator is the largest motor power (absolute value) or 1
            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1) * 1.1;
            frontLeftPower = (rotY + rotX + rx) / denominator;
            backLeftPower = (rotY - rotX + rx) / denominator;
            frontRightPower = (rotY - rotX - rx) / denominator;
            backRightPower = (rotY + rotX - rx) / denominator;

            motorFL.setPower(frontLeftPower);
            motorBL.setPower(-backLeftPower);
            motorFR.setPower(frontRightPower);
            motorBR.setPower(-backRightPower);



            armPower = opmode.gamepad2.left_stick_y;
            scoopPower = opmode.gamepad2.right_stick_y * 0.5;

            motorA.setPower(armPower);
            motorScoop.setPower(scoopPower);







            //print out remaining debug variables
            if(debug){
                opmode.telemetry.addData("y", y);
                opmode.telemetry.addData("x", x);
                opmode.telemetry.addData("rx", rx);
                opmode.telemetry.addData("denominator", denominator);
                opmode.telemetry.addData("backLeftPower", backLeftPower);
                opmode.telemetry.addData("backRightPower", backRightPower);
                opmode.telemetry.addData("frontLeftPower", frontLeftPower);
                opmode.telemetry.addData("frontRightPower", frontRightPower);
                opmode.telemetry.addData("armPower", armPower);
                opmode.telemetry.addData("scoopPower", scoopPower);
                opmode.telemetry.addData("botHeading", botHeading);
            }


            //update debug screen
            opmode.telemetry.update();
        }

    }
}
