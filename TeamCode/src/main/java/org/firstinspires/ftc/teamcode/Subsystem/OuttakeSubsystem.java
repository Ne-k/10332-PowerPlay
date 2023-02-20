package org.firstinspires.ftc.teamcode.Subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class OuttakeSubsystem extends SubsystemBase {
    MotorEx sliderMotor1, sliderMotor2;
    PIDController pid = new PIDController(1, 0, 0);

    public OuttakeSubsystem(final HardwareMap hmap) {

        sliderMotor1 = new MotorEx(hmap, Constants.Motors.sM1);
        sliderMotor2 = new MotorEx(hmap, Constants.Motors.sM2);

        sliderMotor1.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        sliderMotor2.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
    }

    public void runOuttake(double speed) {
        sliderMotor1.set(speed);
        sliderMotor2.set(speed);
    }

    public void runSliderPID(double position) {
        double output = pid.calculate(
                sliderMotor1.getCurrentPosition(), position
        );
        while (!pid.atSetPoint()) {
            sliderMotor1.setVelocity(output);
            sliderMotor2.setVelocity(output);
        }
        sliderMotor1.stopMotor();
        sliderMotor2.stopMotor();
    }

    public void resetEncoder() {
        sliderMotor1.resetEncoder();
        sliderMotor2.resetEncoder();
    }

}
