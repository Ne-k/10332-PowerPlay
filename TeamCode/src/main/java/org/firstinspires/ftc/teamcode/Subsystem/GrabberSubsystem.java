package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class GrabberSubsystem implements Subsystem {
    Servo rotServo, grabServo;

    public GrabberSubsystem(final HardwareMap hmap) {
        this.rotServo = hmap.servo.get(Constants.Servos.Grabber.rotServo);
        this.grabServo = hmap.servo.get(Constants.Servos.Grabber.grabServo);
    }

    public void closeGrabServo() {
        grabServo.setPosition(.29);
    }

    public void openGrabServo() {
        grabServo.setPosition(0);
    }

    public void rotateGrabServoOuttake() {
        rotServo.setPosition(.25);
    }

    public void rotateGrabServoIntake() {
        rotServo.setPosition(.88);
    }



    @Override
    public void periodic() {
    }
}
