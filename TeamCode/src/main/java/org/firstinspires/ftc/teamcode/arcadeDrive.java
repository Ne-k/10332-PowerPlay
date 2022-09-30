package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="arcodeDrive", group="Linear Opmode")
public class arcadeDrive extends LinearOpMode
{
    DcMotor leftMotor, rightMotor;
    float   leftPower, rightPower, turnLeft, turnRight;

    // called when init button is  pressed.
    @Override
    public void runOpMode() throws InterruptedException
    {
        leftMotor = hardwareMap.dcMotor.get(Constants.Motors.left);
        rightMotor = hardwareMap.dcMotor.get(Constants.Motors.right);

        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        // wait for start button.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive())
        {
            leftPower =  gamepad1.left_stick_y;
            rightPower = gamepad1.left_stick_y;

            turnLeft = gamepad1.right_stick_x * -1;
            turnRight = gamepad1.right_stick_x * 1;


           // leftMotor.setPower(Range.clip(leftPower, -1.0, 1.0));
           // rightMotor.setPower(Range.clip(rightPower, -1.0, 1.0));


            leftMotor.setPower(leftPower);
            rightMotor.setPower(rightPower);

            leftMotor.setPower(turnLeft);
            rightMotor.setPower(turnRight);

            /*
             telemetry.addData("Mode", "running");
            telemetry.addData("stick", "  y=" + yValue + "  x=" + xValue);
            telemetry.addData("power", "  left=" + leftPower + "  right=" + rightPower);
            telemetry.update();
             */

            idle();
        }
    }

}
