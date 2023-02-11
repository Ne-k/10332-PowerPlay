package org.firstinspires.ftc.teamcode.Commands.Grabber;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystem.GrabberSubsystem;

public class GrabberServoCommand extends CommandBase {
    private final GrabberSubsystem grabberSubsystem;
    private final ServoMotorState state;
    private final ServoMotorType type;

    public GrabberServoCommand(GrabberSubsystem grabberSubsystem, ServoMotorType type, ServoMotorState state) {
        this.grabberSubsystem = grabberSubsystem;
        this.state = state;
        this.type = type;
        addRequirements(grabberSubsystem);
    }

    @Override
    public void execute() {
        switch (type) {
            case GRABBER:
                switch (state) {
                    case OPEN:
                        grabberSubsystem.openGrabServo();
                        break;
                    case CLOSE:
                        grabberSubsystem.closeGrabServo();
                        break;
                }
                break;
            case ROTATION:
                switch (state) {
                    case OPEN:
                        grabberSubsystem.rotateGrabServoIntake();
                        break;
                    case CLOSE:
                        grabberSubsystem.rotateGrabServoOuttake();
                        break;
                }
                break;
        }
    }

    public enum ServoMotorType {
        GRABBER,
        ROTATION
    }

    public enum ServoMotorState {
        OPEN,
        CLOSE
    }
}
