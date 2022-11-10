package org.firstinspires.ftc.teamcode.Subsystems.TeleOp;

import static org.firstinspires.ftc.teamcode.Constants.Camera.webcamName;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.pipelines.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@TeleOp(name = "MechDrive", group = "Linear opmode")
public class Mecanum extends LinearOpMode {

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
        AprilTagDetectionPipeline aprilTagDetectionPipeline;

        leftFront = hardwareMap.dcMotor.get(Constants.Motors.lf);
        leftRear = hardwareMap.dcMotor.get(Constants.Motors.lr);
        rightFront = hardwareMap.dcMotor.get(Constants.Motors.rf);
        rightRear = hardwareMap.dcMotor.get(Constants.Motors.rr);
        elevatorMotor = hardwareMap.dcMotor.get(Constants.Motors.elevatorMotor);
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

        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        servo.resetDeviceConfigurationForOpMode();
        servo.setDirection(Servo.Direction.FORWARD);

        ArrayList<AprilTagDetection> detections = AprilTagDetectionPipeline.getDetectionsUpdate();

        final double FEET_PER_METER = 3.28084;

        // Lens intrinsics
        // UNITS ARE PIXELS
        // NOTE: this calibration is for the C920 webcam at 800x448.
        // You will need to do your own calibration for other configurations!
        double fx = 578.272;
        double fy = 578.272;
        double cx = 402.145;
        double cy = 221.506;

        // UNITS ARE METERS
        double tagsize = 0.166;

        int numFramesWithoutDetection = 0;

        final float DECIMATION_HIGH = 3;
        final float DECIMATION_LOW = 2;
        final float THRESHOLD_HIGH_DECIMATION_RANGE_METERS = 1.0f;
        final int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = 4;



        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);

        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);


        camera.setPipeline(aprilTagDetectionPipeline);


        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                                         @Override
                                         public void onOpened() {
                                             camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
                                         }

                                         @Override
                                         public void onError(int errorCode) {

                                         }
                                     });



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

            // if no april tag detection
            if (detections.size() == 0) {
                numFramesWithoutDetection++;
                if (numFramesWithoutDetection > THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION) {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_LOW);
                }

            } else {
                numFramesWithoutDetection = 0;
                if (detections.get(0).pose.z < THRESHOLD_HIGH_DECIMATION_RANGE_METERS) {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_HIGH);
                }

                telemetry.addData("Tag ID", detections.get(0).id);
                telemetry.addData("Tag X", detections.get(0).pose.x);
                telemetry.addData("Tag Y", detections.get(0).pose.y);
                telemetry.addData("Tag Z", detections.get(0).pose.z);
                telemetry.addData("Tag Yaw", detections.get(0).pose.yaw);
                telemetry.addData("Tag Pitch", detections.get(0).pose.pitch);
                telemetry.addData("Tag Roll", detections.get(0).pose.roll);


            }

            // If the target is within 1 meter, turn on high decimation to
            // increase the frame rate


            if(gamepad1.a) {
                if(forward) {
                    forward = false;
                    servo.setPosition(1);

                } else {
                    forward = true;
                    servo.setDirection(Servo.Direction.REVERSE);
                    servo.setPosition(-1);

                }
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