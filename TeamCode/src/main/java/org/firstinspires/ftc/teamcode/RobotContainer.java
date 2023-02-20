package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.Grabber.Grabber180Motor;
import org.firstinspires.ftc.teamcode.Commands.MecanumCommand;
import org.firstinspires.ftc.teamcode.Subsystem.Grabber180Subsystem;
import org.firstinspires.ftc.teamcode.Subsystem.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.Mecanum;
import org.firstinspires.ftc.teamcode.Subsystem.OuttakeSubsystem;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class RobotContainer extends CommandOpMode {
    private Button m_grabButton, m_releaseButton;
    private GamepadEx driver, gpEx2;

    @Override
    public void initialize() {
        driver = new GamepadEx(gamepad1);
        gpEx2 = new GamepadEx(gamepad2);


        Grabber180Subsystem grabberMotorSubsystem = new Grabber180Subsystem(hardwareMap);
        GrabberSubsystem grabberSubsystem = new GrabberSubsystem(hardwareMap);
        Mecanum mecanum = new Mecanum(hardwareMap);
        OuttakeSubsystem outtakeSubsustem = new OuttakeSubsystem(hardwareMap);

        Grabber180Motor start = new Grabber180Motor(grabberMotorSubsystem, Grabber180Motor.GrabberMotorState.UP);
        Grabber180Motor mid = new Grabber180Motor(grabberMotorSubsystem, Grabber180Motor.GrabberMotorState.MID);
        Grabber180Motor down = new Grabber180Motor(grabberMotorSubsystem, Grabber180Motor.GrabberMotorState.DOWN);
        MecanumCommand mecanumCommand = new MecanumCommand(mecanum, driver);

        register(mecanum);
        register(grabberMotorSubsystem);
        register(grabberSubsystem);
        register(outtakeSubsustem);

        mecanum.setDefaultCommand(mecanumCommand);


    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        while (opModeIsActive()) {
            run();
        }
    }
}

