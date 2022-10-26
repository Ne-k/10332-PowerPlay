package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MechDrive", group = "Linear opmode")
public class Mecanum extends LinearOpMode {

    //get motors
    DcMotor leftFront, leftRear, rightFront, rightRear;

    /*

    DcMotor LFMotor = hardwareMap.dcMotor.get("leftFront");
    DcMotor LBMotor = hardwareMap.dcMotor.get("leftRear");
    DcMotor RFMotor = hardwareMap.dcMotor.get("rightFront");
    DcMotor RBMotor = hardwareMap.dcMotor.get("rightRear");
     */

    @Override
    public void runOpMode() throws InterruptedException {
        // define motors
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        waitForStart();

        while(opModeIsActive()) {

            final double y = (-this.gamepad1.left_stick_x)* .7;
            final double x = -this.gamepad1.left_stick_y * .7;
            final double rx = this.gamepad1.right_stick_x*.5;
            final double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
            final double frontLeftPower = (y + x + rx) / denominator;
            final double backLeftPower = (y - x + rx) / denominator;
            final double frontRightPower = (y - x - rx) / denominator;
            final double backRightPower = (y + x - rx) / denominator;
            leftFront.setPower(frontLeftPower);
            leftRear.setPower(backLeftPower);
            rightFront.setPower(frontRightPower);
            rightRear.setPower(backRightPower);
            telemetry.addData("Status", "Running");
            telemetry.update();
        }

    }
}