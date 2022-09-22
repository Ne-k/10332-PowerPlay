package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="Mecanum Drive", group="Iterative Opmode")

public class MechDrive extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor front_left = hardwareMap.dcMotor.get(Constants.Motors.front_left);
        DcMotor front_right = hardwareMap.dcMotor.get(Constants.Motors.front_right);
        DcMotor back_left = hardwareMap.dcMotor.get(Constants.Motors.back_left);
        DcMotor back_right = hardwareMap.dcMotor.get(Constants.Motors.back_right);

        waitForStart();

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);

            double front_left_power = (y + x + rx) / denominator;
            double back_left_power = (y - x + rx) / denominator;
            double front_right_power = (y - x - rx) / denominator;
            double back_right_power = (y + x - rx) / denominator;

            front_left.setPower(front_left_power);
            back_left.setPower(back_left_power);
            front_right.setPower(front_right_power);
            back_right.setPower(back_right_power);
        }
    }
}
