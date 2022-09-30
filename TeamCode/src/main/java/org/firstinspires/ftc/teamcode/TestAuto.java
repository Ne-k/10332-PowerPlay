package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.lang.annotation.Annotation;

@Autonomous(name="TestAuto", group="Linear Opmode")
public class TestAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        while(opModeIsActive()) {
            telemetry.addData("Status", "Initialized");
            telemetry.update();

            waitForStart();

        }
    }
}
