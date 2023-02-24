package org.firstinspires.ftc.teamcode.Commands.Grabber;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystem.Grabber180Subsystem;

public class Grabber180Motor extends CommandBase {

    private Grabber180Subsystem grabberMotor;
    private final GrabberMotorState state;
    private final Telemetry telemetry;
    private final double speed;

    public Grabber180Motor(Grabber180Subsystem grabberMotorSubsystem, GrabberMotorState state, Telemetry telemetry, double speed) {
        this.state = state;
        this.grabberMotor = grabberMotorSubsystem;
        this.telemetry = telemetry;
        this.speed = speed;
        addRequirements(grabberMotorSubsystem);
    }

    @Override
    public void initialize() {
        //grabberMotor.runGrabberMid();
    }

    @Override
    public void execute() {

        switch (state) {
            case MID:
                grabberMotor.runGrabberMid();
                break;
            case UP:
                grabberMotor.runGrabberStartPos();
                break;
            case DOWN:
                grabberMotor.runGrabberFullDown();
                break;
            case DEBUG:
                grabberMotor.debug(telemetry);
                break;
            case OVERRIDEUP:
                grabberMotor.runGrabberManual(speed);
            case OVERRIDEDOWN:
                grabberMotor.runGrabberManual(speed);
                break;
        }

    }


    @Override
    public boolean isFinished() {
        return false;
    }

    public enum GrabberMotorState {
        MID,
        UP,
        DOWN,
        DEBUG,
        OVERRIDEUP,
        OVERRIDEDOWN,
    }

    @Override
    public void end(boolean interrupted) {
        grabberMotor.stopMotor();
    }
}

