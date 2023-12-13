package org.firstinspires.ftc.teamcode.main.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.main.Drive;
import org.firstinspires.ftc.teamcode.utils.Master;

@TeleOp(name="Drive", group="OpMode")
public class OpModeDrive extends LinearOpMode {
    @Override
    public void runOpMode(){
        Master.setCurrentOpMode(this);
        Drive.run();
    }
}

