package org.firstinspires.ftc.teamcode.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.main.AutonomousOpMode;
import org.firstinspires.ftc.vision.VisionPortal;

import java.io.File;

/**
 * Camera class for 2023/2024 season. Using VisionPortal which can only save to drive so it's slow. Also mostly undocumented.
 * I'm pretty sure I'm just abusing a camera debugging library for this class. Might switch to something better someday but this is easy.
 * TODO: Switch to camera2 or opencv or something. Will probably never do this, but maybe one day.
 *
 * @author github.com/jakeslye
 */
public class Camera {

    public static final int ERROR = -1;
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;

    private static LinearOpMode opMode;
    private static Telemetry telemetry;
    private static VisionPortal portal;

    public static void init(){
        opMode = Master.getCurrentOpMode();
        telemetry = opMode.telemetry;

        final int RESOLUTION_WIDTH = 800;
        final int RESOLUTION_HEIGHT = 448;


        portal = new VisionPortal.Builder()
                .setCamera(opMode.hardwareMap.get(WebcamName.class, "cam"))
                .setCameraResolution(new Size(RESOLUTION_WIDTH, RESOLUTION_HEIGHT))
                .build();
    }

    public static int run() {
        opMode = Master.getCurrentOpMode();
        telemetry = opMode.telemetry;


        final String FILE_NAME = "frame";

        portal.saveNextFrameRaw(FILE_NAME);

        Tools.doForTime(1, () -> {
            telemetry.addData("Status", "Waiting for image to save..");
            telemetry.update();
        });

        //Get the file from the weird file saving format
        File imgFile = new File(Environment.getExternalStorageDirectory().toString() + "/VisionPortal-" + FILE_NAME + ".png");

        if(imgFile.exists()){
            //Convert to bitmap
            Bitmap bitMap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            //TODO:Crop image instead of searching everything

            int height = bitMap.getHeight();
            int width = bitMap.getWidth();

            int leftCount = 0;
            int centerCount = 0;
            int rightCount = 0;

            //Search each third of the image. Probably needs fixing.
            int sides = width/3;

            //Get the target color from master autonomous class
            int color = AutonomousOpMode.getColor();

            telemetry.addData("STATUS", "Calculating Side for " + color + " o_o *this takes way too long*");
            telemetry.update();

            final int THRESHOLD = 100;

            //Search by row. This is so slow. I know why Alex wanted to use c++ now.
            for(int h=0+(height/2); h<height; h++){
                for(int w=0; w<width; w++){
                    //Gets a pixels content as a int
                    int pixel = bitMap.getPixel(w, h);

                    //Convert to rgb. Could use Vector3 but would likely lead to more lag
                    int redValue = Color.red(pixel);
                    int greenValue = Color.green(pixel);
                    int blueValue = Color.blue(pixel);

                    /*
                        I used green because its too inconsistent not too. The big if statement is for performance.
                        Wish I could use a function for some of this too clean it up but it will worsen the already
                        terrible performance
                    */
                    if(color == AutonomousOpMode.RED) {
                        if (redValue > blueValue && redValue > greenValue) {
                            if (blueValue < THRESHOLD && greenValue < THRESHOLD) {
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
                    }else if(color == AutonomousOpMode.BLUE){
                        if (blueValue > redValue && blueValue > greenValue) {
                            if(redValue < THRESHOLD && greenValue < THRESHOLD) {
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
            }

            //If this doesn't work it will jump to the return ERROR at the end
            if(leftCount > rightCount && leftCount > centerCount){
                return LEFT;
            }else if(centerCount > leftCount && centerCount > rightCount){
                return CENTER;
            }else if(rightCount > leftCount && rightCount > centerCount){
                return RIGHT;
            }

            if(leftCount == 0 && rightCount == 0 && centerCount == 0) {
                telemetry.addData(":0 Warning", "Image is black :(");
            }


        }else{
            telemetry.addData("Error", ">~< No file at " + imgFile.getAbsolutePath());
            telemetry.update();
        }

        return ERROR;
    }
}
