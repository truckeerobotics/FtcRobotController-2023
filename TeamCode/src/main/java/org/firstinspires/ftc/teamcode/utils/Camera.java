package org.firstinspires.ftc.teamcode.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.main.Auto.AutonomousOpMode;
import org.firstinspires.ftc.vision.VisionPortal;

import java.io.File;

public class Camera {

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;

    private static LinearOpMode opMode;
    private static Telemetry telemetry;
    private static Context context;

    public static int run() {
        opMode = AutonomousOpMode.getAutoOpMode();
        telemetry = AutonomousOpMode.getAutoOpMode().telemetry;
        context = opMode.hardwareMap.appContext;

        final int RESOLUTION_WIDTH = 640;
        final int RESOLUTION_HEIGHT = 480;

        VisionPortal portal;

        portal = new VisionPortal.Builder()
                .setCamera(opMode.hardwareMap.get(WebcamName.class, "cam"))
                .setCameraResolution(new Size(RESOLUTION_WIDTH, RESOLUTION_HEIGHT))
                .build();


        final String FILE_NAME = "frame";

        double startTime = opMode.getRuntime();
        while(startTime+2> opMode.getRuntime()){
            telemetry.addData("STATUS", "Waiting for camera to bootup... :3");
            telemetry.update();
        }

        portal.saveNextFrameRaw(FILE_NAME);

        startTime = opMode.getRuntime();
        while(startTime+2> opMode.getRuntime()){
            telemetry.addData("STATUS", "Waiting for image to save... :3");
            telemetry.update();
        }


        File imgFile = new File(Environment.getExternalStorageDirectory().toString() + "/VisionPortal-" + FILE_NAME + ".png");

        if(imgFile.exists()){
            Bitmap bitMap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            int height = bitMap.getHeight();
            int width = bitMap.getWidth();

            int leftCount = 0;
            int centerCount = 0;
            int rightCount = 0;

            int sides = width/3;

            int color = AutonomousOpMode.getColor();

            telemetry.addData("STATUS", "Calculating Side for " + color + " o_o *this takes way too long*");
            telemetry.update();

            for(int h=0; h<height; h++){
                for(int w=0; w<width; w++){
                    int pixel = bitMap.getPixel(w, h);

                    int redValue = Color.red(pixel);
                    int greenValue = Color.green(pixel);
                    int blueValue = Color.blue(pixel);

                    if(color == AutonomousOpMode.RED) {
                        if (redValue > blueValue && redValue > greenValue){
                            if (w < sides) {
                                leftCount++;
                            }
                            if (w > sides && w < sides * 2) {
                                centerCount++;
                            }
                            if (w > sides * 2) {
                                rightCount++;
                            }
                        }
                    }else if(color == AutonomousOpMode.BLUE){
                        if (blueValue > redValue && blueValue > greenValue) {
                            if (w < sides) {
                                leftCount++;
                            }
                            if (w > sides && w < sides * 2) {
                                centerCount++;
                            }
                            if (w > sides * 2) {
                                rightCount++;
                            }
                        }
                    }
                }
            }
            telemetry.addData("leftCount", leftCount);
            telemetry.addData("centerCount", centerCount);
            telemetry.addData("rightCount", rightCount);

            if(leftCount > rightCount && leftCount > centerCount){
                return LEFT;
            }else if(centerCount > leftCount && centerCount > rightCount){
                return CENTER;
            }else if(rightCount > leftCount && rightCount > centerCount){
                return RIGHT;
            }


        }else{
            telemetry.addData("Error", ">~< No file at " + imgFile.getAbsolutePath());
            telemetry.update();
        }

        return -1;
    }
}
