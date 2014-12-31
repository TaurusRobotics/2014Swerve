package com.taurus;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * <pre>
 * Halo Drive: like driving a vehicle in Halo
 * Left joystick: movement l/r/f/b
 * Right joystick: rotation rate (CCW/CW)
 *          ^
 *          |
 *      <---+--->           <---+--->
 *          |
 *          v
 *       Movement            Rotation
 *       
 * Angle Drive: like a top-down shooter such
 * as Geometry Wars
 * Left joystick: movement l/r/f/b
 * Right joystick: robot heading l/r/f/b
 *          ^                   ^
 *          |                   |
 *      <---+--->           <---+--->
 *          |                   |
 *          v                   v
 *       Movement            Heading
 *    
 * Parts adapted from Team 3946 (https://github.com/frc3946)
 * </pre>
 * @author Team 4818 Taurus Robotics
 */
public class SwerveController {

    public static final int HALO_DRIVE = 0;
    public static final int ANGLE_DRIVE = 1;

    public static final double DEADBAND = 0.18;

    public boolean useXbox;
    public int driveScheme;
    
    private Joystick left;
    private Joystick right;
    
    private XboxController xbox;
    
    
    /**
     * 
     */
    public SwerveController()
    {
        left = new Joystick(1);
        right = new Joystick(2);
        xbox = new XboxController(1);
        
        useXbox = true;
        driveScheme = HALO_DRIVE;
    }

    /**
     * Get the magnitude of the direction vector formed by the
     * joystick's current position relative to its origin
     * @param hand Hand associated with the Joystick
     * @return the magnitude of the direction vector
     */
    public double getMagnitude(Hand hand)
    {
        double value = 0;

        if(useXbox)
        {
            value = xbox.getMagnitude(hand);
        }
        else
        {
            if(hand == Hand.kLeft)
            {
                value = left.getMagnitude();
            }
            else
            {
                value = right.getMagnitude();
            }
        }

        if(value < DEADBAND)
        {
            value = 0;
        }
        return value;
    }

    /**
     * Get the direction of the vector formed by the joystick and its origin in degrees
     * @param hand Hand associated with the Joystick
     * @return The direction of the vector in degrees
     */
    public double getDirectionDegrees(Hand hand)
    {
        if(useXbox)
        {
            return xbox.getDirectionDegrees(hand);
        }
        else
        {
            if(hand == Hand.kLeft)
            {
                return left.getDirectionDegrees();
            }
            else
            {
                return right.getDirectionDegrees();
            }
        }
    }
    
    /**
     * Get the direction of the vector formed by the joystick and its origin in radians
     * @param hand Hand associated with the Joystick
     * @return The direction of the vector in radians
     */
    public double getDirectionRadians(Hand hand)
    {
        if(useXbox)
        {
            return xbox.getDirectionRadians(hand);
        }
        else
        {
            if(hand == Hand.kLeft)
            {
                return left.getDirectionRadians();
            }
            else
            {
                return right.getDirectionRadians();
            }
        }
    }

    /**
     * Get the Rotation value of the joystick for Halo Drive
     * @return The Rotation value of the joystick.
     */
    public double getHaloDrive_Rotation()
    {
        double value = 0;

        if(useXbox)
        {
            value = xbox.getAxis(XboxController.AxisType.kRightX);
        }
        else
        {
            value = right.getAxis(Joystick.AxisType.kX);
        }

        if(value < DEADBAND)
        {
            value = 0;
        }
        return value;
    }

    /**
     * Get the swerve vector (mag & angle) of the velocity joystick for Halo Drive
     * @return The vector of the joystick.
     */
    public SwerveVector getHaloDrive_Velocity()
    {
        SwerveVector value;

        if(useXbox)
        {
            value = new SwerveVector(xbox.getX(Hand.kLeft),
                                     xbox.getY(Hand.kLeft));
        }
        else
        {
            value = new SwerveVector(left.getX(),
                                     left.getY());
        }

        if(value.getMag() < DEADBAND)
        {
            value.setMag(0);
        }
        
        return value;
    }

    /**
     * Get the heading/angle in degrees for Angle Drive
     * @return The angle in degrees of the joystick.
     */
    public double getAngleDrive_Heading()
    {
        if(useXbox)
        {
            return xbox.getDirectionDegrees(Hand.kRight);
        }
        else
        {
            return right.getDirectionDegrees();
        }
    }

    /**
     * Get the swerve vector (mag & angle) of the velocity joystick for Angle Drive
     * @return The vector of the joystick.
     */
    public SwerveVector getAngleDrive_Velocity()
    {
        SwerveVector value;

        if(useXbox)
        {
            value = new SwerveVector(xbox.getX(Hand.kLeft),
                                     xbox.getY(Hand.kLeft));
        }
        else
        {
            value = new SwerveVector(left.getX(),
                                     left.getY());
        }

        if(value.getMag() < DEADBAND)
        {
            value.setMag(0);
        }
        return value;
    }
    
    /**
     * Get whether the high gear should be enabled
     * @return true if high gear, else low gear
     */
    public boolean getHighGearEnable()
    {
        if(useXbox)
        {
            return !xbox.getBumper(Hand.kRight);
        }
        else
        {
            // shift to high gear if the button is not held down
            return !left.getRawButton(2);
        }
    }
}