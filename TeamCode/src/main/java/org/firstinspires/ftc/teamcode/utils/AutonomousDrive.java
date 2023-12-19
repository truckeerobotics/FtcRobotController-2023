package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


/** Has functions for controlling the robot from an autonomous mode
 *  TODO: Use encoders instead of time
 *
 *  @author github.com/jakeslye
 */
public class AutonomousDrive {

    private DcMotor motorFL;
    private DcMotor motorFR;
    private DcMotor motorBL;
    private DcMotor motorBR;
    private DcMotor motorA;
    private DcMotor motorScoop;
    private BNO055IMU imu;

    private LinearOpMode opmode;

    public AutonomousDrive(){
        this.opmode = Master.getCurrentOpMode();

        motorFL    = opmode.hardwareMap.get(DcMotor.class, "motorFL");
        motorFR    = opmode.hardwareMap.get(DcMotor.class, "motorFR");
        motorBL    = opmode.hardwareMap.get(DcMotor.class, "motorBL");
        motorBR    = opmode.hardwareMap.get(DcMotor.class, "motorBR");
        motorA     = opmode.hardwareMap.get(DcMotor.class, "arm");
        motorScoop = opmode.hardwareMap.get(DcMotor.class, "scoop");

        if(Master.getImu() == null){
            Master.setIMU(opmode.hardwareMap.get(BNO055IMU.class, "imu"));
            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
            Master.getImu().initialize(parameters);
        }
        imu = Master.getImu();

        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }


    /** Does mecannum math with fake controller values.
     *
     * @param x Left joystick X. Typically for strafing.
     * @param y Left joystick Y. Typically for forward or backward movement.
     * @param rx Right joystick x. Typically for turning.
     */
    public void emulateController(double x, double y, double rx){

        y *= -1;


        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1) * 1.1;
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        motorFL.setPower(frontLeftPower);
        motorBL.setPower(-backLeftPower);
        motorFR.setPower(frontRightPower);
        motorBR.setPower(-backRightPower);
    }


    public void driveForwardByTime(double time, double power){
        emulateController(0, power, 0);
        Tools.doForTime(time, () -> {
            opmode.telemetry.addData("AutonomousDrive", ":3 Driving forward...");
            opmode.telemetry.update();
        });
        emulateController(0, 0, 0);
    }

    public void strafeByTime(double time, double power){
        emulateController(power, 0, 0);
        Tools.doForTime(time, () -> {
            opmode.telemetry.addData("AutonomousDrive", ":O Strafing...");
            opmode.telemetry.update();
        });
        emulateController(0, 0, 0);
    }

    final double ANGLE_THRESHOLD = 0.5;
    public void turn(double target, double power){

        float a = imu.getAngularOrientation().toAngleUnit(AngleUnit.DEGREES).firstAngle;

        if(a < target + ANGLE_THRESHOLD && a > target - ANGLE_THRESHOLD){
            return;
        }


        if(target < 0){
            power *= -1;
        }

        boolean run = true;
        emulateController(0, 0, power);
        while(run){
            a = imu.getAngularOrientation().toAngleUnit(AngleUnit.DEGREES).firstAngle;
            opmode.telemetry.addData("AutonomousDrive", ":P turning... angle: " + a);
            opmode.telemetry.update();
            if(a < target + ANGLE_THRESHOLD && a > target - ANGLE_THRESHOLD){
                run = false;
            }
        }

        emulateController(0, 0, 0);
    }

    public void scoopByTime(double time, double power){
        motorScoop.setPower(power);
        Tools.doForTime(time, () -> {
            opmode.telemetry.addData("AutonomousDrive", ":D Scoop moving...");
            opmode.telemetry.update();
        });
        motorScoop.setPower(0);
    }

    public void armByTime(double time, double power){
        motorA.setPower(power);
        Tools.doForTime(time, () ->{
            opmode.telemetry.addData("AutonomousDrive", ":3 Arm moving...");
            opmode.telemetry.update();
        });
        motorA.setPower(0);
    }
}
