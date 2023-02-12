package org.firstinspires.ftc.teamcode.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystem.IntakeExtender;

public class IntakeExtenderOverride extends CommandBase {
    private IntakeExtender extSub;
    private double speed;
    public IntakeExtenderOverride(IntakeExtender extSub, double speed) {
        this.extSub = extSub;
        this.speed = speed;
        addRequirements(extSub);
    }

    @Override
    public void execute() {

        extSub.extendOverride(speed);

    }

    @Override
    public boolean isFinished() {
        return extSub.isAtPosition();
    }
}
