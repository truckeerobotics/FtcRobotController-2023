package org.firstinspires.ftc.teamcode.utils;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/** Write all functions that are needed in multiple classes but don't need their own class here.
 *
 * @author github.com/jakeslye
 */
public class Tools {

    /** Runs a function for a certain amount of time. Useful for stalling the program or run by time movement.
     *
     * @param time Time to run for.
     * @param r Callback to run. Please use lambda.
     */
    public static void doForTime(double time, Runnable r){
        LinearOpMode opMode = Master.getCurrentOpMode();

        double initTime = opMode.getRuntime();
        while(initTime+time > opMode.getRuntime() && !opMode.isStopRequested()){
            r.run();
        }
    }

    /**
     * Logs to logCat with a unique tag.
     *
     * @param text Log Content
     */
    public static void logCat(String text){
        //Using "Tools Log" for a unique value
        Log.i("Tools Log", text);
    }
}
