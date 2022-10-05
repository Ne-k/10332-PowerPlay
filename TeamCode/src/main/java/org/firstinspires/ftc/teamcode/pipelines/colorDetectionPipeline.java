package org.firstinspires.ftc.teamcode.pipelines;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.imgproc.Imgproc;

public class colorDetectionPipeline extends OpenCvPipeline{

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2HSV);
        return input;
    }
}
