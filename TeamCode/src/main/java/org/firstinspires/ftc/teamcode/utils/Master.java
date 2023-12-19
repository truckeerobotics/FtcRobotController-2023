package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Tools;

/**
 * Contains the master functions. Mainly for using the same LinearOpMode in every class. Maybe I will add more things later.
 * Don't mess with this class
 *
 * @author github.com/jakeslye
 */
public class Master {
    private static LinearOpMode currentOpMode;
    private static BNO055IMU imu;


    /**
     * Sets the master LinearOpMode for every class. Please only call from classes extending LinearOpMode.
     *
     * @param  opMode
     */
    public static void setCurrentOpMode(LinearOpMode opMode){
        currentOpMode = opMode;
        Tools.logCat("New OpMode set at " + currentOpMode.getRuntime());
    }

    public static LinearOpMode getCurrentOpMode(){
        return currentOpMode;
    }

    public static void setIMU(BNO055IMU _imu){
        imu = _imu;
    }

    public static BNO055IMU getImu(){
        return imu;
    }
}
