package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Grabber180Subsystem extends SubsystemBase {
    private final int mid = -311;
    private final int startPos = 0;
    private final int fullDown = -1197;
    MotorEx grabber180;
    PIDController pid = new PIDController(1, 0, 0);

    public Grabber180Subsystem(final HardwareMap hmap) {
        grabber180 = new MotorEx(hmap, Constants.Motors.grabber180);
        grabber180.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
    }

    public void runGrabberPos(double setPosition) {
        double output = pid.calculate(
                grabber180.getCurrentPosition(), setPosition
        );
        while (!pid.atSetPoint()) {
            grabber180.setVelocity(output);
        }
        grabber180.stopMotor();
    }

    public void runGrabberMid() {
        runGrabberPos(mid);
    }

    public void runGrabberStartPos() {
        runGrabberPos(startPos);
    }

    public void runGrabberFullDown() {
        runGrabberPos(fullDown);
    }

    public boolean isAtPosition() {
        return pid.atSetPoint();
    }

    public void resetEncoder() {
        grabber180.resetEncoder();
    }
    public void Override(double speed) {
        grabber180.set(speed);
    }

    @Override
    public void periodic() {
    }
}
