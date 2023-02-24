package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.Grabber.Grabber180Motor;
import org.firstinspires.ftc.teamcode.Commands.Grabber.GrabberServoCommand;
import org.firstinspires.ftc.teamcode.Commands.Intake.IntakeExtenderCommand;
import org.firstinspires.ftc.teamcode.Commands.MecanumCommand;
import org.firstinspires.ftc.teamcode.Commands.Outtake.OuttakeCommand;
import org.firstinspires.ftc.teamcode.Subsystem.Grabber180Subsystem;
import org.firstinspires.ftc.teamcode.Subsystem.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.Subsystem.IntakeExtender;
import org.firstinspires.ftc.teamcode.Subsystem.Mecanum;
import org.firstinspires.ftc.teamcode.Subsystem.OuttakeSubsystem;

@TeleOp(name = "TeleOp", group = "TeleOp")
public class RobotContainer extends CommandOpMode {
    private Button m_grabButton, m_releaseButton;
    private GamepadEx driver, operator;

    @Override
    public void initialize() {
        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);


        Grabber180Subsystem grabberMotorSubsystem = new Grabber180Subsystem(hardwareMap);
        GrabberSubsystem grabberSubsystem = new GrabberSubsystem(hardwareMap);
        Mecanum mecanum = new Mecanum(hardwareMap);
        OuttakeSubsystem outtakeSubsustem = new OuttakeSubsystem(hardwareMap);
        Grabber180Subsystem grabber180Subsystem = new Grabber180Subsystem(hardwareMap);
        IntakeExtender intakeExtender = new IntakeExtender(hardwareMap);

        Grabber180Motor start = new Grabber180Motor(grabberMotorSubsystem, Grabber180Motor.GrabberMotorState.UP, telemetry, 0);
        Grabber180Motor mid = new Grabber180Motor(grabberMotorSubsystem, Grabber180Motor.GrabberMotorState.MID, telemetry, 0);
        Grabber180Motor down = new Grabber180Motor(grabberMotorSubsystem, Grabber180Motor.GrabberMotorState.DOWN, telemetry, 0);

        Grabber180Motor debug = new Grabber180Motor(grabber180Subsystem, Grabber180Motor.GrabberMotorState.DEBUG, telemetry, 0);
        MecanumCommand mecanumCommand = new MecanumCommand(mecanum, driver);

        register(mecanum);
        register(grabberMotorSubsystem);
        register(grabberSubsystem);
        register(outtakeSubsustem);

//        driver.getGamepadButton(GamepadKeys.Button.A).whenPressed(new GrabberServoCommand(grabberSubsystem, GrabberServoCommand.ServoMotorType.GRABBER, GrabberServoCommand.ServoMotorState.OPEN));
//        driver.getGamepadButton(GamepadKeys.Button.B).whenPressed(new GrabberServoCommand(grabberSubsystem, GrabberServoCommand.ServoMotorType.GRABBER, GrabberServoCommand.ServoMotorState.CLOSE));
//
//        driver.getGamepadButton(GamepadKeys.Button.X).whenPressed(new GrabberServoCommand(grabberSubsystem, GrabberServoCommand.ServoMotorType.ROTATION, GrabberServoCommand.ServoMotorState.OPEN));
//        driver.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new GrabberServoCommand(grabberSubsystem, GrabberServoCommand.ServoMotorType.ROTATION, GrabberServoCommand.ServoMotorState.CLOSE));
//
        mecanum.setDefaultCommand(mecanumCommand);
        grabber180Subsystem.setDefaultCommand(debug);
        intakeExtender.setDefaultCommand(new IntakeExtenderCommand(intakeExtender, IntakeExtenderCommand.extenderPosition.DEBUG, telemetry, 0));

        driver.getGamepadButton(GamepadKeys.Button.Y).whileHeld(new Grabber180Motor(grabber180Subsystem, Grabber180Motor.GrabberMotorState.OVERRIDEUP, telemetry, .3));
        driver.getGamepadButton(GamepadKeys.Button.A).whileHeld(new Grabber180Motor(grabber180Subsystem, Grabber180Motor.GrabberMotorState.OVERRIDEDOWN, telemetry, -.3));
        driver.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new GrabberServoCommand(grabberSubsystem, GrabberServoCommand.ServoMotorType.GRABBER, GrabberServoCommand.ServoMotorState.OPEN));
        driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new GrabberServoCommand(grabberSubsystem, GrabberServoCommand.ServoMotorType.GRABBER, GrabberServoCommand.ServoMotorState.CLOSE));
        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenHeld(new OuttakeCommand(outtakeSubsustem, OuttakeCommand.OuttakeDirection.OVERRIDEUP, 0.4));
        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenHeld(new OuttakeCommand(outtakeSubsustem, OuttakeCommand.OuttakeDirection.OVERRIDEDOWN, 0.5));

        driver.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whileHeld(new IntakeExtenderCommand(new IntakeExtender(hardwareMap), IntakeExtenderCommand.extenderPosition.OVERRIDEIN, telemetry, 0.8));
        driver.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whileHeld(new IntakeExtenderCommand(new IntakeExtender(hardwareMap), IntakeExtenderCommand.extenderPosition.OVERRIDEOUT, telemetry, 0.8));


        operator.getGamepadButton(GamepadKeys.Button.A).whenPressed(new GrabberServoCommand(grabberSubsystem, GrabberServoCommand.ServoMotorType.ROTATION, GrabberServoCommand.ServoMotorState.OPEN));
        operator.getGamepadButton(GamepadKeys.Button.B).whenPressed(new GrabberServoCommand(grabberSubsystem, GrabberServoCommand.ServoMotorType.ROTATION, GrabberServoCommand.ServoMotorState.CLOSE));
    }

}

