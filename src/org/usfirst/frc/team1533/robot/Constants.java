package org.usfirst.frc.team1533.robot;
/**
 * Contains the constants used throughout the code
 * 
 * @author eggoeke
 *
 */
public class Constants {
	/**
	 * @param FL_ENC_OFFSET - Difference in degrees of the wheel's current and desired angle for the front left module
	 * @param FR_ENC_OFFSET - Difference in degrees of the wheel's current and desired angle for the front right module
	 * @param BL_ENC_OFFSET - Difference in degrees of the wheel's current and desired angle for the back left module
	 * @param BR_ENC_OFFSET - Difference in degrees of the wheel's current and desired angle for the back right module
	 */
	public final static double FL_ENC_OFFSET = 0;
	public final static double FR_ENC_OFFSET = 0;
	public final static double BL_ENC_OFFSET = 0;
	public final static double BR_ENC_OFFSET = 0;

	/**
	 * Assumed symmetric rectangular drivetrain
	 * @param WHEEL_BASE_WIDTH - Width in inches of the robot from wheel to wheel (used for position vector)
	 * @param WHEEL_BASE_LENGTH - Length in inches of the robot from wheel to wheel (used for position vector)
	 */
	public final static double WHEEL_BASE_WIDTH = 20;
	public final static double WHEEL_BASE_LENGTH = 20;

	public static class RobotMap{
		public final static int FR_STEER = 0;
		public final static int FR_DRIVE = 1;
		public final static int BR_STEER = 2;
		public final static int BR_DRIVE = 3;
		public final static int FL_STEER = 4;
		public final static int FL_DRIVE = 5;
		public final static int BL_STEER = 6;
		public final static int BL_DRIVE = 7;


		public final static int FR_ENCODER = 0;
		public final static int BR_ENCODER = 1;
		public final static int FL_ENCODER = 2;
		public final static int BL_ENCODER = 3;
	}
	
	public static class Steering{
		public final static double SWERVE_STEER_CAP = 1; 
		public final static double SWERVE_STEER_P = 2; 
		public final static double SWERVE_STEER_I = 0; 
		public final static double SWERVE_STEER_D = 0;
	}
}
