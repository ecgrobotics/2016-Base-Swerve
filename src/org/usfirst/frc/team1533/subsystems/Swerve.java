package org.usfirst.frc.team1533.subsystems;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team1533.robot.Constants;

/**
 * Contains implementation of modules into driving
 * @author Duncan
 *
 */
public class Swerve extends Subsystem {
	public double pivotX, pivotY, lastpressed, transAngle, pivotspeed;
	public SwerveModule[] modules;
	Joystick joypad;
	SpeedController flDrive, frDrive, blDrive, brDrive, flsteer, frsteer, blsteer, brsteer;

	/**
	 * Custom constructor for current robot.
	 */
	public Swerve(Joystick joypad) {
		this.joypad = joypad;

		//initialize array of modules
		//array can be any size, as long as the position of each module is specified in its constructor
		modules = new SwerveModule[] {
				//front left
				new SwerveModule(new Talon(Constants.RobotMap.FL_DRIVE),
						new Talon(Constants.RobotMap.FL_STEER),
						new AbsoluteEncoder(Constants.RobotMap.FL_ENCODER, Constants.FL_ENC_OFFSET),
						-Constants.WHEEL_BASE_WIDTH/2,
						Constants.WHEEL_BASE_LENGTH/2
						),
				//front right
				new SwerveModule(new Talon(Constants.RobotMap.FR_DRIVE), 
						new Talon(Constants.RobotMap.FR_STEER),
						new AbsoluteEncoder(Constants.RobotMap.FR_ENCODER, Constants.FR_ENC_OFFSET),
						Constants.WHEEL_BASE_WIDTH/2,
						Constants.WHEEL_BASE_LENGTH/2
						),
				//back left
				new SwerveModule(new Talon(Constants.RobotMap.BL_DRIVE),
						new Talon(Constants.RobotMap.BL_STEER),
						new AbsoluteEncoder(Constants.RobotMap.BL_ENCODER, Constants.BL_ENC_OFFSET),
						-Constants.WHEEL_BASE_WIDTH/2,
						-Constants.WHEEL_BASE_LENGTH/2
						),
				//back right
				new SwerveModule(new Talon(Constants.RobotMap.BR_DRIVE), 
						new Talon(Constants.RobotMap.BR_STEER),
						new AbsoluteEncoder(Constants.RobotMap.BR_ENCODER, Constants.BR_ENC_OFFSET),
						Constants.WHEEL_BASE_WIDTH/2,
						-Constants.WHEEL_BASE_LENGTH/2
						)
		};
		enable();
	}

	/**
	 * @param pivotX x coordinate in inches of pivot point relative to center of robot
	 * @param pivotY y coordinate in inches of pivot point relative to center of robot
	 */
	public void setPivot(double pivotX, double pivotY) {
		this.pivotX = pivotX;
		this.pivotY = pivotY;
	}

	/**
	 * Regular robot oriented control.
	 * @param translationX relative speed in left/right direction (-1 to 1)
	 * @param translationY relative speed in forward/reverse direction (-1 to 1)
	 * @param rotation relative rate of rotation around pivot point (-1 to 1) positive is clockwise
	 */

	private void drive(double translationX, double translationY, double rotation) {
		Vector[] vects = new Vector[modules.length];
		Vector transVect = new Vector(translationX, translationY),
				pivotVect = new Vector(pivotX, pivotY);


		//if there is only one module ignore rotation
		if (modules.length < 2)
			for (SwerveModule module : modules) 
				module.set(transVect.getAngle(), Math.min(1, transVect.getMagnitude())); //cap magnitude at 1

		double maxDist = 0;
		for (int i = 0; i < modules.length; i++) {
			vects[i] = new Vector(modules[i].positionX, modules[i].positionY);
			vects[i].subtract(pivotVect); //calculate module's position relative to pivot point
			maxDist = Math.max(maxDist, vects[i].getMagnitude()); //find farthest distance from pivot
		}

		double maxPower = 1;
		for (int i = 0; i < modules.length; i++) {
			//rotation motion created by driving each module perpendicular to
			//the vector from the pivot point
			vects[i].makePerpendicular();
			//scale by relative rate and normalize to the farthest module
			//i.e. the farthest module drives with power equal to 'rotation' variable
			vects[i].scale(rotation / maxDist);
			vects[i].add(transVect);
			//calculate largest power assigned to modules
			//if any exceed 100%, all must be scale down
			maxPower = Math.max(maxPower, vects[i].getMagnitude());
		}


		double power;
		for (int i = 0; i < modules.length; i++) {
			power = vects[i].getMagnitude() / maxPower; //scale down by the largest power that exceeds 100%
			if (power > .05) {
				modules[i].set(vects[i].getAngle()-Math.PI/2, power);
			} else {
				modules[i].rest();
			}
		}
	}
	public void enable() {
		for (SwerveModule module : modules) module.enable();
	}
	public void disable() {
		for (SwerveModule module : modules) module.disable();
	}


	public void move(){

		double x = joypad.getX();
		double y = joypad.getY();
		double z = joypad.getRawAxis(3);

		//dead zone of 10% joystick value, translational speed 60% full power
		if(Math.abs(x) > .1 || Math.abs(y)>.1 || Math.abs(z)>.1){
			drive((x*60)/100, (-y*60)/100, (z/2));
		}
		else drive(0,0,0);
	}


	/**
	 * 2D Mathematical Vector
	 */
	class Vector {
		double x = 0, y = 0;

		public Vector(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getAngle() {
			return Math.atan2(y, x);
		}

		public double getMagnitude() {
			return Math.hypot(x, y);
		}

		public void scale(double scalar) {
			x *= scalar;
			y *= scalar;
		}

		public void add(Vector v) {
			x += v.x;
			y += v.y;
		}

		public void subtract(Vector v) {
			x -= v.x;
			y -= v.y;
		}

		public void makePerpendicular() {
			double temp = x;
			x = y;
			y = -temp;
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}