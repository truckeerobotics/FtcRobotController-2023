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

    //declare driving variables
    private double y;
    private double x;
    private double rx;
    private double denominator;
    private double frontLeftPower;
    private double backLeftPower;
    private double frontRightPower;
    private double backRightPower;

    //declare custom classes
    private Toggle toggle;
    private IMU imu;



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

        //initialize custom classes
        toggle = new Toggle(gamepad1, gamepad2);
        imu = new IMU(this, "imu");

        telemetry.addData("Status", "Waiting for start...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            //setup toggle key handlers
            if(toggle.onPush(gamepad1.a, "controller1ButtonA")) debug = !debug;
            if(toggle.onPush(gamepad1.x, "controller1ButtonX")) driverOrientationMode = !driverOrientationMode;

            //debug tool tip for enabling/disabling debug mode
            if(debug) telemetry.addData("DEBUG", "Press A to turn off debug mode.");

            //easier to use x, y, and rx instead of full names
            y = gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            rx = gamepad1.right_stick_x;

            //centralizes the robots movement to the drivers POV
            if(driverOrientationMode) {
                Vector2 rot = imu.getHeadingCorrection(x, y);
                x = rot.getX();
                y = rot.getY();
                if (debug) {
                    telemetry.addData("rot", rot.toString());
                }
            }

            //mecanum drive math
            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            backLeftPower = (y - x + rx) / denominator;
            backRightPower = (y + x - rx) / denominator;
            frontLeftPower = (y + x + rx) / denominator;
            frontRightPower = (y - x - rx) / denominator;

            //give motors power
            motorBL.setPower(-backLeftPower);
            motorBR.setPower(backRightPower);
            motorFL.setPower(-frontLeftPower);
            motorFR.setPower(frontRightPower);


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
            }


            //update debug screen
            telemetry.update();
        }

    }
}
