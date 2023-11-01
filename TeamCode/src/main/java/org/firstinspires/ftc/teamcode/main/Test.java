package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Test", group="OpMode")
public class Test extends LinearOpMode {
    @Override
    public void runOpMode() {
            telemetry.addData("Satus", "Wait for start...");
            telemetry.update();

            DcMotor motor = hardwareMap.get(DcMotor.class, "motor");
            DcMotor motorTwo = hardwareMap.get(DcMotor.class, "motor2");;



        waitForStart();

            while (opModeIsActive()) {
                telemetry.addData("Satus", "Started...");
                telemetry.update();

                motor.setPower(-0.5);
                motorTwo.setPower(0.5);
            }

        }
}
