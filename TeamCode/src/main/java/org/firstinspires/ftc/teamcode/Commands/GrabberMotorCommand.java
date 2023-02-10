package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandGroupBase;

import org.firstinspires.ftc.teamcode.Subsystem.GrabberMotorSubsystem;

public class GrabberMotorCommand extends CommandBase {

    private final GrabberMotorSubsystem grabberMotor = new GrabberMotorSubsystem();

    public GrabberMotorCommand(GrabberMotorSubsystem grabberMotorSubsystem) {
        addRequirements(grabberMotorSubsystem);
    }

    @Override
    public void initialize() {
        grabberMotor.runGrabberMid();
    }

    public void execute() {

    }

}

