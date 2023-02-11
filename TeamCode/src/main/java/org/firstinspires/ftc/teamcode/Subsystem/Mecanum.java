package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.Constants;

public class Mecanum implements Subsystem {
    Motor leftFront, leftRear, rightFront, rightRear;

    public Mecanum() {
        leftFront = new Motor(hardwareMap, Constants.Motors.lf);
        leftRear = new Motor(hardwareMap, Constants.Motors.lr);
        rightFront = new Motor(hardwareMap, Constants.Motors.rf);
        rightRear = new Motor(hardwareMap, Constants.Motors.rr);

        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

    }

    public void Drive(double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower) {
//        double y = 0;
//        double x = 0;
//        double rx = 0;
//
//        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
//        double frontLeftPower = (y + x + rx) / denominator;
//        double backLeftPower = (y - x + rx) / denominator;
//        double frontRightPower = (y - x - rx) / denominator;
//        double backRightPower = (y + x - rx) / denominator;

        leftFront.set(frontLeftPower);
        leftRear.set(backLeftPower);
        rightFront.set(frontRightPower);
        rightRear.set(backRightPower);
    }

    public void driveForward() {
        Drive(.5, .5, -.5, -.5);
    }

    public void stopAll() {
        Drive(0, 0, 0, 0);
    }

    @Override
    public void periodic() {
    }
}
