package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Constants.Camera.webcamName;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.pipelines.colorDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@TeleOp(name = "MechDrive", group = "Linear opmode")
public class Mecanum extends LinearOpMode {

    colorDetectionPipeline pipeline;

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



        pipeline = new colorDetectionPipeline();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        camera.setPipeline(pipeline);


        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                                         @Override
                                         public void onOpened() {
                                                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                                         }

                                         @Override
                                         public void onError(int errorCode) {

                                         }
                                     });



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


            telemetry.addData("Motor position", elevatorMotor.getCurrentPosition());
            telemetry.addData("ROTATION: ", pipeline.getPosition());
            telemetry.update();


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
        }

    }
}