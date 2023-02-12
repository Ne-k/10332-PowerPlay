package org.firstinspires.ftc.teamcode.Subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public class IntakeExtender extends SubsystemBase {
    private MotorEx intakeExt;
    private final PIDController pid = new PIDController(0,0,0);

    public IntakeExtender(MotorEx intakeExt) {
        this.intakeExt = intakeExt;
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

    public void resetEncoder() {
        intakeExt.resetEncoder();
    }


}
