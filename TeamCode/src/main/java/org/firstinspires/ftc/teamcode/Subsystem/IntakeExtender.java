package org.firstinspires.ftc.teamcode.Subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class IntakeExtender extends SubsystemBase {
    private MotorEx intakeExt;
    private final PIDController pid = new PIDController(0,0,0);

    public IntakeExtender(final HardwareMap hmap) {
        this.intakeExt = new MotorEx(hmap, Constants.Motors.extendMotor);
        intakeExt.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    public void pidExtend(double position) {
        double output = pid.calculate(
                intakeExt.getCurrentPosition(), position
        );
        while (!pid.atSetPoint()) {
            intakeExt.setVelocity(output);
        }
        intakeExt.stopMotor();
    }


    public boolean isAtPosition() {
        return pid.atSetPoint();
    }

    public void extendOverride(double speed) {
        intakeExt.set(speed);
    }
    public int getPosition() {
        return intakeExt.getCurrentPosition();
    }

    public void resetEncoder() {
        intakeExt.resetEncoder();
    }


}
