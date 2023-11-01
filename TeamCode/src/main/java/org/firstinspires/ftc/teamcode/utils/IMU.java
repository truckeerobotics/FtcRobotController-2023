package org.firstinspires.ftc.teamcode.utils;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


public class IMU {
    private BNO055IMU imu;
    private LinearOpMode opmode;

    public IMU(LinearOpMode opmode, String IMUName){
        this.opmode = opmode;
        BNO055IMU imu = opmode.hardwareMap.get(BNO055IMU.class, IMUName);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        this.imu = imu;
    }

    public Orientation getOrientation(){ return imu.getAngularOrientation(); }

    public double getHeading() {
        return -imu.getAngularOrientation().firstAngle;
    }

    public Vector2 getHeadingCorrection(double x, double y) {
        double botHeading = getHeading();
        double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
        double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);
        return new Vector2(rotX, rotY);
    }
}