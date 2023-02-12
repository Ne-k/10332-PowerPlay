package org.firstinspires.ftc.teamcode.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.Subsystem.IntakeExtender;


public class IntakeExtenderCommand extends CommandBase {
    private IntakeExtender extender;
    private extenderPosition pos;

    public IntakeExtenderCommand(IntakeExtender extender, extenderPosition position) {
        this.extender = extender;
        this.pos = position;
        addRequirements(extender);
    }

    public enum extenderPosition {
        OPEN,
        CLOSE
    }

    @Override
    public void execute() {

        switch(pos) {
            case OPEN:
                extender.pidExtend(0); // TODO TUNE THESE VALUES
                break;
            case CLOSE:
                extender.pidExtend(0); // TODO TUNE THESE VALUES

        }

    }

    @Override
    public boolean isFinished() {
        return extender.isAtPosition();
    }
}
