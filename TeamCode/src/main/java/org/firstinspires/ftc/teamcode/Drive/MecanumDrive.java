package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

@TeleOp(name = "MechDrive", group = "Linear opmode")
public class MecanumDrive extends LinearOpMode {

    //get motors
    DcMotor leftFront, leftRear, rightFront, rightRear, elevatorMotor;
    public double frontLeft;
    public double frontRight;
    public double rearLeft;
    public double rearRight;
    private boolean forward = true;

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
        elevatorMotor = hardwareMap.dcMotor.get(Constants.Motors.elevatorMotor);

        elevatorMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        elevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevatorMotor.setTargetPosition(0);
        elevatorMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

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


            telemetry.update();


            leftFront.setPower(frontLeftPower * 0.5);
            leftRear.setPower(backLeftPower * 0.5);
            rightFront.setPower(frontRightPower * 0.5);
            rightRear.setPower(backRightPower * 0.5);
        }

    }
}