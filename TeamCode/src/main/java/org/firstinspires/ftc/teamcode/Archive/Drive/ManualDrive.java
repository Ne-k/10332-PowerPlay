package org.firstinspires.ftc.teamcode.Archive.Drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ManualDrive (Blocks to Java)")
public class ManualDrive extends LinearOpMode {

    private DcMotor slideM1;
    private DcMotor leftOutGb;
    private Servo grabServo;
    private DcMotor slideM2;
    private DcMotor grabber180;
    private Servo rotServo;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        slideM1 = hardwareMap.get(DcMotor.class, "slideM1");
        leftOutGb = hardwareMap.get(DcMotor.class, "leftOutGb");
        grabServo = hardwareMap.get(Servo.class, "grabServo");
        slideM2 = hardwareMap.get(DcMotor.class, "slideM2");
        grabber180 = hardwareMap.get(DcMotor.class, "grabber180");
        rotServo = hardwareMap.get(Servo.class, "rotServo");

        // Put initialization blocks here.
        slideM1.setDirection(DcMotorSimple.Direction.REVERSE);
        leftOutGb.setPower(0.25);
        grabServo.setPosition(0.8);
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                telemetry.addData("Encoder position", slideM1.getCurrentPosition());
                telemetry.addData("Encoder position 2", slideM2.getCurrentPosition());
                if (gamepad1.dpad_up) {
                    slideM1.setPower(1);
                    slideM2.setPower(1);
                } else if (gamepad1.dpad_down) {
                    slideM1.setPower(-1);
                    slideM2.setPower(-1);
                } else {
                    slideM1.setPower(0);
                    slideM2.setPower(0);
                }
                if (gamepad1.dpad_right) {
                    leftOutGb.setPower(1);
                } else if (gamepad1.dpad_left) {
                    leftOutGb.setPower(-1);
                } else {
                    leftOutGb.setPower(0);
                }
                if (gamepad1.right_bumper) {
                    grabber180.setPower(0.5);
                } else if (gamepad1.left_bumper) {
                    grabber180.setPower(-0.5);
                } else {
                    grabber180.setPower(0);
                }
                if (gamepad1.y) {
                    grabServo.setPosition(0.8);
                } else if (gamepad1.a) {
                    grabServo.setPosition(1);
                }
                if (gamepad1.x) {
                    rotServo.setPosition(0.22);
                } else if (gamepad1.b) {
                    rotServo.setPosition(0.885);
                }
                if (gamepad1.right_stick_button) {
                    intake();
                }
                if (gamepad1.left_stick_button) {
                    slidesUp();
                }
                telemetry.update();
            }
        }
    }

    /**
     * Describe this function...
     */
    private void slidesUp() {
        slideM1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideM2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideM1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideM2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Encoder position", slideM1.getCurrentPosition());
        telemetry.addData("Encoder position 2", slideM2.getCurrentPosition());
        slideM1.setTargetPosition(-260);
        slideM2.setTargetPosition(-610);
        while (slideM2.getCurrentPosition() > slideM2.getTargetPosition()) {
            slideM2.setPower(0.85);
            slideM2.setPower(0.85);
        }
        slideM1.setTargetPosition(0);
        slideM2.setTargetPosition(0);
        while (slideM2.getCurrentPosition() < slideM2.getTargetPosition()) {
            slideM2.setPower(-1);
            slideM1.setPower(-1);
        }
        while (slideM1.getCurrentPosition() < slideM1.getTargetPosition()) {
        }
        slideM1.setPower(0);
        slideM2.setPower(0);
    }

    /**
     * Describe this function...
     */
    private void intake() {
        grabServo.setPosition(0.8);
        rotServo.setPosition(0.9);
        leftOutGb.setPower(-0.6);
        sleep(400);
        leftOutGb.setPower(0);
        grabServo.setPosition(1);
        sleep(600);
        leftOutGb.setPower(0.55);
        grabber180.setPower(1);
        rotServo.setPosition(0.22);
        sleep(700);
        leftOutGb.setPower(0);
        grabServo.setPosition(0.9);
        sleep(400);
        grabber180.setPower(-0.5);
        sleep(300);
        slidesUp();
        grabber180.setPower(-0.3);
        grabServo.setPosition(0.8);
        rotServo.setPosition(0.9);
        sleep(250);
        leftOutGb.setPower(0.55);
        sleep(500);
        grabber180.setPower(0);
    }
}