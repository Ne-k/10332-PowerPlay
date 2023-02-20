package org.firstinspires.ftc.teamcode.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystem.IntakeExtender;

public class IntakeExtenderResetEnc extends CommandBase {
    private IntakeExtender sub;

    public IntakeExtenderResetEnc(IntakeExtender sub) {
        this.sub = sub;
        addRequirements(sub);
    }

    @Override
    public void execute() {
        sub.resetEncoder();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
