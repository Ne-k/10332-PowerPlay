package org.firstinspires.ftc.teamcode.Commands.Outtake;

import static org.firstinspires.ftc.teamcode.Commands.Outtake.OuttakeCommand.OuttakeDirection.FULLUP;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystem.OuttakeSubsystem;

public class OuttakeCommand extends CommandBase {
    private OuttakeSubsystem sub;
    private OuttakeDirection od;
    private double speed;

    public OuttakeCommand(OuttakeSubsystem sub, OuttakeDirection od, double speed) {
        this.sub = sub;
        this.od = od;
        this.speed = speed;
        addRequirements(sub);
    }
    public enum OuttakeDirection {
        FULLUP,
        FULLDOWN,
        MEDIUM,
        LOW,
        OVERRIDEUP,
        OVERRIDEDOWN
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
                case OVERRIDEUP:
                sub.runOuttake(-speed);
                break;
            case OVERRIDEDOWN:
                sub.runOuttake(speed);
                break;
        }

    }
    @Override
    public void end(boolean interrupted) {
        sub.stopAll();
    }
}
