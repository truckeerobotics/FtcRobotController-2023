
package org.firstinspires.ftc.teamcode.main.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.main.AutonomousOpMode;
import org.firstinspires.ftc.teamcode.utils.Master;

@Autonomous(name="Auto Red Other Side", group="Auto")
public class OpModeRedOther extends LinearOpMode{
    @Override
    public void runOpMode() {
        Master.setCurrentOpMode(this);
        AutonomousOpMode.run(AutonomousOpMode.RED, AutonomousOpMode.RIGHT);
    }
}
