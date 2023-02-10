package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Commands.GrabberMotorCommand;
import org.firstinspires.ftc.teamcode.Commands.MecanumCommand;
import org.firstinspires.ftc.teamcode.Subsystem.GrabberMotorSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.Mecanum;
import org.firstinspires.ftc.teamcode.Subsystem.OuttakeSubsustem;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class RobotContainer extends CommandOpMode {
    private Button m_grabButton, m_releaseButton;
    private GamepadEx gpEx1, gpEx2;

    @Override
    public void initialize() {
        gpEx1 = new GamepadEx(gamepad1);


        GrabberMotorSubsystem grabberMotorSubsystem = new GrabberMotorSubsystem();
        GrabberSubsystem grabberSubsystem = new GrabberSubsystem();
        Mecanum mecanum = new Mecanum();
        OuttakeSubsustem outtakeSubsustem = new OuttakeSubsustem();

        GrabberMotorCommand grabberMotorCommand = new GrabberMotorCommand(grabberMotorSubsystem);
        MecanumCommand mecanumCommand = new MecanumCommand(mecanum, gpEx1);

        register(mecanum);
        mecanum.setDefaultCommand(mecanumCommand);

    }


}
