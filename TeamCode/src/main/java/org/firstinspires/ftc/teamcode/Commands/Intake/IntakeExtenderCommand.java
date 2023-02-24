package org.firstinspires.ftc.teamcode.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystem.IntakeExtender;


public class IntakeExtenderCommand extends CommandBase {
    private final IntakeExtender extender;
    private final extenderPosition pos;
    private final Telemetry telemetry;
    private final double speed;

    public IntakeExtenderCommand(IntakeExtender extender, extenderPosition position, Telemetry telemetry, double speed) {
        this.extender = extender;
        this.pos = position;
        this.telemetry = telemetry;
        this.speed = speed;
        addRequirements(extender);
    }

    public enum extenderPosition {
        OUT,
        IN,
        DEBUG,
        OVERRIDEOUT,
        OVERRIDEIN
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        switch(pos) {
            case OVERRIDEOUT:
                extender.extendOverride(speed);
                break;
            case OVERRIDEIN:
                extender.extendOverride(-speed);
                break;
            case IN:
                extender.pidExtend(0); // TODO TUNE THESE VALUES
                break;
            case OUT:
                extender.pidExtend(0); // TODO TUNE THESE VALUES
                break;
            case DEBUG:
                telemetry.addData("Extender Position", extender.getPosition());
                telemetry.update();
                break;


        }

    }

    @Override
    public boolean isFinished() {
//        return extender.isAtPosition();
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        extender.extendOverride(0);
    }
}
