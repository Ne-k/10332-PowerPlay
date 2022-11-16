package org.firstinspires.ftc.teamcode.Subsystems.TeleOp;

import static org.firstinspires.ftc.teamcode.Constants.Camera.webcamName;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.Constants;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@TeleOp(name = "MechDrive", group = "Linear opmode")
public class Mecanum extends LinearOpMode {

    DcMotor leftFront, leftRear, rightFront, rightRear, elevatorMotor, intakeLeftMotor, intakeRightMotor;
    private boolean forward = true;


    @Override
    public void runOpMode() throws InterruptedException {
        AprilTagDetectionPipeline aprilTagDetectionPipeline;

        leftFront = hardwareMap.dcMotor.get(Constants.Motors.lf);
        leftRear = hardwareMap.dcMotor.get(Constants.Motors.lr);
        rightFront = hardwareMap.dcMotor.get(Constants.Motors.rf);
        rightRear = hardwareMap.dcMotor.get(Constants.Motors.rr);
        elevatorMotor = hardwareMap.dcMotor.get(Constants.Motors.elevatorMotor);

        intakeLeftMotor = hardwareMap.dcMotor.get(Constants.Motors.intakeLeftMotor);
        intakeRightMotor = hardwareMap.dcMotor.get(Constants.Motors.intakeRightMotor);

        Servo servo = hardwareMap.servo.get(Constants.Servos.intake);

        elevatorMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setTargetPosition(0);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        servo.setDirection(Servo.Direction.REVERSE);

        servo.resetDeviceConfigurationForOpMode();

        waitForStart();
        telemetry.setMsTransmissionInterval(50);

        if (isStopRequested()) return;

        while(opModeIsActive()) {


            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x * 1.1;
            double rx = -gamepad1.right_stick_x;


            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;


            if(gamepad1.right_bumper) {


                intakeLeftMotor.setPower(0.5);
                intakeRightMotor.setPower(0.5);

                servo.setPosition(1);

            } else if(gamepad1.left_bumper) {
                intakeLeftMotor.setPower(-0.5);
                intakeRightMotor.setPower(-0.5);
                servo.setDirection(Servo.Direction.FORWARD);

                servo.setPosition(-1);
            } else if(gamepad1.a) {
                servo.setPosition(0.5);
                intakeLeftMotor.setPower(0);
                intakeRightMotor.setPower(0);
            }



           if(gamepad1.x) {
               elevatorMotor.setTargetPosition(-815);
               elevatorMotor.setPower(1);
           } else if(gamepad1.y) {
               elevatorMotor.setTargetPosition(0);
               elevatorMotor.setPower(.65);
           }



            leftFront.setPower(frontLeftPower * 0.5);
            leftRear.setPower(backLeftPower * 0.5);
            rightFront.setPower(frontRightPower * 0.5);
            rightRear.setPower(backRightPower * 0.5);

            telemetry.update();
        }

    }
}