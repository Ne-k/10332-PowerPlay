package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="arcodeDrive", group="Linear Opmode")
public class arcadeDrive extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException
    {
        DcMotor leftMotor = hardwareMap.dcMotor.get(Constants.Motors.left);
        DcMotor rightMotor = hardwareMap.dcMotor.get(Constants.Motors.right);


        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        telemetry.addData("Status", opModeIsActive() ? "Active" : "Inactive");
        telemetry.update();
        waitForStart();

        while (opModeIsActive())
        {
            float leftPower = gamepad1.left_stick_y * 0.6f;
            float rightPower = gamepad1.left_stick_y * 0.6f;

            float turnLeft = (gamepad1.right_stick_x * -1) * 0.5f;
            float turnRight = (gamepad1.right_stick_x * 1) * 0.5f;

            leftMotor.setPower(leftPower);
            rightMotor.setPower(rightPower);

            leftMotor.setPower(turnLeft);
            rightMotor.setPower(turnRight);

            telemetry.addData("Right trigger", gamepad1.right_trigger);
            telemetry.update();




            idle();
        }
    }

}
