package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

@TeleOp(name = "MechDrive", group = "Linear opmode")
public class MecanumDrive extends LinearOpMode {
    public static void setTimeout(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }
    //get motors
    DcMotor leftFront, leftRear, rightFront, rightRear, extendMotor, liftMotor, grabber180;
    Servo rotServo, grabServo;

    /*
    DcMotor LFMotor = hardwareMap.dcMotor.get("leftFront");
    DcMotor LBMotor = hardwareMap.dcMotor.get("leftRear");
    DcMotor RFMotor = hardwareMap.dcMotor.get("rightFront");
    DcMotor RBMotor = hardwareMap.dcMotor.get("rightRear");
     */

    @Override
    public void runOpMode() throws InterruptedException {

        leftFront = hardwareMap.dcMotor.get(Constants.Motors.lf);
        leftRear = hardwareMap.dcMotor.get(Constants.Motors.lr);
        rightFront = hardwareMap.dcMotor.get(Constants.Motors.rf);
        rightRear = hardwareMap.dcMotor.get(Constants.Motors.rr);
        extendMotor = hardwareMap.dcMotor.get(Constants.Motors.extendMotor);
        grabber180 = hardwareMap.dcMotor.get(Constants.Motors.grabber180);

        extendMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setTargetPosition(0);
        extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        grabber180.setDirection(DcMotorSimple.Direction.FORWARD);
        grabber180.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabber180.setTargetPosition(0);
        grabber180.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        grabber180.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        rotServo = hardwareMap.servo.get(Constants.Servos.Grabber.rotServo);
        grabServo = hardwareMap.servo.get(Constants.Servos.Grabber.grabServo);
        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive()) {
            grabServo.setPosition(1.5);


            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x * 1.1;
            double rx = -gamepad1.right_stick_x;


            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            telemetry.addData("extend pos", extendMotor.getCurrentPosition());
            telemetry.addData("180 pos", grabber180.getCurrentPosition());
            telemetry.update();

            if(gamepad1.x) {
                extendMotor.setTargetPosition(0);
                extendMotor.setPower(0.5);
            } else if(gamepad1.a){

            } else if(gamepad1.b) {

            } else if(gamepad1.left_bumper) {
                grabServo.setPosition(0);
                setTimeout(1000);
                grabServo.setPosition(2);
            } else if(gamepad1.right_bumper) {
                grabber180.setTargetPosition(0);
                grabber180.setPower(0.1);

                if(grabber180.getCurrentPosition() >= 0) {
                    rotServo.setPosition(0.05);
                    setTimeout(1000);
                    grabber180.setTargetPosition(47);
                    grabber180.setPower(0.1);
                    setTimeout(500);
                    grabServo.setPosition(0.1);
                    setTimeout(2000);
                    grabber180.setTargetPosition(0);
                }
            }
// 0 mid
            // 47 slide
            // -162 down
            leftFront.setPower(frontLeftPower * 0.5);
            leftRear.setPower(backLeftPower * 0.5);
            rightFront.setPower(frontRightPower * 0.5);
            rightRear.setPower(backRightPower * 0.5);
        }

    }
}