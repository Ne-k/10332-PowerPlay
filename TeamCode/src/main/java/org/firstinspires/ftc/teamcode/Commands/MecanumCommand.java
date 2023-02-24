package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Subsystem.Mecanum;

public class MecanumCommand extends CommandBase {

    private final Mecanum mecanum;
    private final GamepadEx gamepad1;

    public MecanumCommand(Mecanum mecanum, GamepadEx gamepad1) {
        this.gamepad1 = gamepad1;
        this.mecanum = mecanum;

        addRequirements(mecanum);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double y = gamepad1.getLeftY();
        double x = -gamepad1.getLeftX() * 1.1;
        double rx = -gamepad1.getRightX();

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        mecanum.Drive(frontLeftPower, backLeftPower, frontRightPower, backRightPower);

    }

    @Override
    public void end(boolean interrupted) {
        mecanum.stopAll();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
