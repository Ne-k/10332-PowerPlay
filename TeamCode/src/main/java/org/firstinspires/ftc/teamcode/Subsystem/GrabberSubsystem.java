package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class GrabberSubsystem implements Subsystem {
    Servo rotServo, grabServo;

    public GrabberSubsystem() {
        rotServo = hardwareMap.servo.get(Constants.Servos.Grabber.rotServo);
        grabServo = hardwareMap.servo.get(Constants.Servos.Grabber.grabServo);
    }

    public void closeGrabServo() {
        grabServo.setPosition(0);
    }

    public void openGrabServo() {
        grabServo.setPosition(1);
    }

    public void rotateGrabServoOuttake() {
        rotServo.setPosition(0.1);
    }

    public void rotateGrabServoIntake() {
        rotServo.setPosition(.7);
    }


    @Override
    public void periodic() {
    }
}
