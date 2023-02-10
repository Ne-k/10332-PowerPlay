package org.firstinspires.ftc.teamcode.Archive.Atuo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.pipelines.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Autonomous(name = "Straight", group = "Linear opmode")
public class Straight extends LinearOpMode {
    DcMotor leftFront, leftRear, rightFront, rightRear, extendMotor, liftMotor, grabber180, slideM1, slideM2;
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    private static final ScheduledExecutorService scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(10);

    private static void setTimeout(int ms) {
        try {

           Thread.sleep(ms);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void driveForward() {
        leftFront.setPower(0.5);
        leftRear.setPower(0.5);
        rightFront.setPower(-0.5);
        rightRear.setPower(-0.5);

        setTimeout(400);
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);
    }

    private void stopMotors() {
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);
    }

    @Override
    public void runOpMode() {
        waitForStart();
        leftFront = hardwareMap.dcMotor.get(Constants.Motors.lf);
        leftRear = hardwareMap.dcMotor.get(Constants.Motors.lr);
        rightFront = hardwareMap.dcMotor.get(Constants.Motors.rf);
        rightRear = hardwareMap.dcMotor.get(Constants.Motors.rr);
        extendMotor = hardwareMap.dcMotor.get(Constants.Motors.extendMotor);

        while(opModeIsActive()) {
            driveForward();

        }


    }
}
