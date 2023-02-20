package org.firstinspires.ftc.teamcode.Commands.Outtake;

import static org.firstinspires.ftc.teamcode.Commands.Outtake.OuttakeCommand.OuttakeDirection.FULLUP;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystem.OuttakeSubsystem;

public class OuttakeCommand extends CommandBase {
    private OuttakeSubsystem sub;
    private OuttakeDirection od;

    public OuttakeCommand(OuttakeSubsystem sub, OuttakeDirection od) {
        this.sub = sub;
        this.od = od;
        addRequirements(sub);
    }
    public enum OuttakeDirection {
        FULLUP,
        FULLDOWN,
        MEDIUM,
        LOW
    }

    @Override
    public void execute() {

        switch(od) {
            case FULLUP:
                sub.runSliderPID(900);
                break;
            case FULLDOWN:
                sub.runSliderPID(0);
                break;
            case MEDIUM:
                sub.runSliderPID(50);
                break;
            case LOW:
                sub.runSliderPID(10);
        }

    }
}
