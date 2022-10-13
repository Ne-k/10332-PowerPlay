package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.pipelines.colorDetectionPipeline.position;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.teamcode.pipelines.colorDetectionPipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera2;
import org.openftc.easyopencv.OpenCvPipeline;

import com.acmerobotics.dashboard.FtcDashboard;


@TeleOp(name="arcodeDrive", group="Linear Opmode")
public class arcadeDrive extends LinearOpMode
{

    DcMotor leftMotor, rightMotor;
    float   leftPower, rightPower, turnLeft, turnRight;
    OpenCvCamera webcam;

    colorDetectionPipeline pipeline = new colorDetectionPipeline();

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

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "web1"), cameraMonitorViewId);
        webcam.setPipeline(pipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                /*
                 * Tell the webcam to start streaming images to us! Note that you must make sure
                 * the resolution you specify is supported by the camera. If it is not, an exception
                 * will be thrown.
                 *
                 * Keep in mind that the SDK's UVC driver (what OpenCvWebcam uses under the hood) only
                 * supports streaming from the webcam in the uncompressed YUV image format. This means
                 * that the maximum resolution you can stream at and still get up to 30FPS is 480p (640x480).
                 * Streaming at e.g. 720p will limit you to up to 10FPS and so on and so forth.
                 *
                 * Also, we specify the rotation that the webcam is used in. This is so that the image
                 * from the camera sensor can be rotated such that it is always displayed with the image upright.
                 * For a front facing camera, rotation is defined assuming the user is looking at the screen.
                 * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
                 * away from the user.
                 */

                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

                msStuckDetectStop = 2500;

                VuforiaLocalizer.Parameters vuforiaParams = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
                vuforiaParams.vuforiaLicenseKey = Constants.Vuforia.key;
                vuforiaParams.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
                VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(vuforiaParams);

                FtcDashboard.getInstance().startCameraStream(vuforia, 10);


            }

            @Override
            public void onError(int errorCode) {
                // An error occurred. This is generally fatal and there is nothing
                // that can be done
            }
        });

        while (opModeIsActive()) {

            telemetry.addData("pos", position);
            telemetry.update();

            leftPower =  gamepad1.left_stick_y;
            rightPower = gamepad1.left_stick_y;

            turnLeft = gamepad1.right_stick_x * -1;
            turnRight = gamepad1.right_stick_x * 1;

            leftMotor.setPower(leftPower);
            rightMotor.setPower(rightPower);

            leftMotor.setPower(turnLeft);
            rightMotor.setPower(turnRight);


            idle();
        }

    }

}
