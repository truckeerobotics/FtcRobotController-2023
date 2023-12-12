package org.firstinspires.ftc.teamcode.main.Auto;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Auto Red", group="Auto")
public class OpModeRed extends LinearOpMode{
    @Override
    public void runOpMode() {
        AutonomousOpMode.run(this, AutonomousOpMode.RED, AutonomousOpMode.RIGHT);
    }
}
