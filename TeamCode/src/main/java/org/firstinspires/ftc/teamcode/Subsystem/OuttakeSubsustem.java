package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.Constants;

public class OuttakeSubsustem extends SubsystemBase {
    Motor outtakeMotor;
    public OuttakeSubsustem() {
        outtakeMotor = new Motor(hardwareMap, Constants.Motors.extendMotor);
        outtakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    public void runOuttake(double speed) {
        outtakeMotor.set(speed);
    }

    @Override
    public void periodic() {
    }

}
