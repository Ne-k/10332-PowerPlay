package org.firstinspires.ftc.teamcode.Commands.Grabber;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystem.Grabber180Subsystem;

public class Grabber180Motor extends CommandBase {

    private Grabber180Subsystem grabberMotor = new Grabber180Subsystem();
    private final GrabberMotorState state;

    public Grabber180Motor(Grabber180Subsystem grabberMotorSubsystem, GrabberMotorState state) {
        this.state = state;
        this.grabberMotor = grabberMotorSubsystem;
        addRequirements(grabberMotorSubsystem);
    }

    @Override
    public void initialize() {
        grabberMotor.runGrabberMid();
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
        }

    }


    @Override
    public boolean isFinished() {
        return grabberMotor.isAtPosition();
    }

    public enum GrabberMotorState {
        MID,
        UP,
        DOWN
    }

}

