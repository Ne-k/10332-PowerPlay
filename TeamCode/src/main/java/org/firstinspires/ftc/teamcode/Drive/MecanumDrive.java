package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@TeleOp(name = "MechDrive", group = "Linear opmode")
public class MecanumDrive extends LinearOpMode {

    //get motors
    DcMotor leftFront, leftRear, rightFront, rightRear, extendMotor, liftMotor, grabber180, slideM1, slideM2;
    Servo rotServo, grabServo;
    private int mid = -311;
    private int startPos = 0;
    private int fullDown = -1197;

    private double servoRotPos = 0.95;

    /*
    DcMotor LFMotor = hardwareMap.dcMotor.get("leftFront");
    DcMotor LBMotor = hardwareMap.dcMotor.get("leftRear");
    DcMotor RFMotor = hardwareMap.dcMotor.get("rightFront");
    DcMotor RBMotor = hardwareMap.dcMotor.get("rightRear");
     */


    public void Drive(Gamepad gp) {
        double y = gp.left_stick_y;
        double x = -gp.left_stick_x * 1.1;
        double rx = -gp.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        leftFront.setPower(frontLeftPower);
        leftRear.setPower(backLeftPower );
        rightFront.setPower(frontRightPower );
        rightRear.setPower(backRightPower);
    }

    private static final ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(10);

    private static void setTimeout(int ms) {
        try {
            scheduledThreadPoolExecutor.schedule(() -> {
            }, ms, TimeUnit.MILLISECONDS).get();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {


        leftFront = hardwareMap.dcMotor.get(Constants.Motors.lf);
        leftRear = hardwareMap.dcMotor.get(Constants.Motors.lr);
        rightFront = hardwareMap.dcMotor.get(Constants.Motors.rf);
        rightRear = hardwareMap.dcMotor.get(Constants.Motors.rr);
        extendMotor = hardwareMap.dcMotor.get(Constants.Motors.extendMotor);
        grabber180 = hardwareMap.dcMotor.get(Constants.Motors.grabber180);

        slideM1 = hardwareMap.dcMotor.get(Constants.Motors.sM1);
        slideM2 = hardwareMap.dcMotor.get(Constants.Motors.sM2);


        slideM1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slideM2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slideM1.setDirection(DcMotorSimple.Direction.FORWARD);
        slideM2.setDirection(DcMotorSimple.Direction.FORWARD);

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
            extendMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//            grabServo.setPosition(1.5);
            rotServo.setPosition(.73);


            Drive(gamepad1);



            telemetry.update();

            if(gamepad1.x || gamepad2.y) {
                extendMotor.setTargetPosition(0);
                extendMotor.setPower(0.3);
            } else if(gamepad1.a || gamepad2.b){
                extendMotor.setTargetPosition(-150);
                extendMotor.setPower(.15);

            }

            if(gamepad2.a || gamepad1.y) {
                grabServo.setPosition(0);
            }
            if(gamepad2.b || gamepad1.b) {
                grabServo.setPosition(2);
            }

            if(gamepad1.left_bumper || gamepad2.left_bumper) {
                grabber180.setTargetPosition(fullDown);
                grabber180.setPower(.3);
            } else if(gamepad1.right_bumper || gamepad2.right_bumper) {
                grabber180.setTargetPosition(mid);
                grabber180.setPower(0.25);
                setTimeout(300);
                extendMotor.setTargetPosition(-20);
                extendMotor.setPower(0.11);
                setTimeout(350);

                if(grabber180.getCurrentPosition() >= mid) {
                    rotServo.setPosition(0.1);
                    grabber180.setTargetPosition(startPos);
                    grabber180.setPower(0.2);
                    setTimeout(1000);
                    grabServo.setPosition(.93);
                    setTimeout(1000);
                    grabber180.setTargetPosition(mid);
                    setTimeout(1000);
                    grabServo.setPosition(2);
                    setTimeout(1000);
                    rotServo.setPosition(.7);

                    extendMotor.setTargetPosition(0);
                    extendMotor.setPower(0.1);
                }

//                if(grabber180.getCurrentPosition() >= 0) {
//                    rotServo.setPosition(0.05);
//                    setTimeout(1000);
//                    grabber180.setTargetPosition(47);
//                    grabber180.setPower(0.1);
//                    setTimeout(500);
//                    grabServo.setPosition(0.1);
//                    setTimeout(2000);
//                   grabber180.setTargetPosition(0);
//                }
            }
            if(gamepad1.start || gamepad2.start) {
                slideM1.setPower(-.5);
                slideM2.setPower(.5);
            } else if(gamepad1.back || gamepad2.back) {
                slideM1.setPower(.2);
                slideM2.setPower(-.2);
            } else {
                slideM1.setPower(0);
                slideM2.setPower(0);
            }



        }

    }
}