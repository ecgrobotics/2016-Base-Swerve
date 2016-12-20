
package org.usfirst.frc.team1533.robot;

import org.usfirst.frc.team1533.subsystems.Swerve;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {
	Joystick joypad;
	Swerve swerve;

	
    public void robotInit() {
		joypad = new Joystick(0);
		swerve = new Swerve(joypad);
    }
    

    public void autonomousInit() {
    }

    public void autonomousPeriodic() {
    }

    public void teleopPeriodic() {
        swerve.move();
    }
    
    public void testPeriodic() {
    
    }
    
}
