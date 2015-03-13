package org.usfirst.frc.team2642.robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {
	
	Joystick driveStick; Joystick auxStick; Joystick auxCard; 
	RobotDrive drive;
	Gyro gyro;
	DigitalInput liftUpperLimit; DigitalInput liftLowerLimit;
	Talon rightPicker; Talon leftPicker; Talon lift;
	Encoder liftEncoder;
	DigitalInput toteInRobot;
	Encoder backRightEncoder; Encoder backLeftEncoder;
	final int lowerSetPoint = 80;
	final int upperSetPoint = 1150;
	
	Compressor compressor;
	Solenoid dogs;
	Solenoid pusher;
	Solenoid flipper;
	
	boolean liftUp;
	boolean liftDown;
	int autoLoopCounter;
	double Kp = 0.04;
	double angle;
    int unloadCounter;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	driveStick = new Joystick(0);
    	auxStick = new Joystick(1);
    	auxCard = new Joystick(2);
    	gyro = new Gyro(0);
    	drive = new RobotDrive(2,0,1,3); 
    	drive.setInvertedMotor(MotorType.kFrontLeft, true);
    	drive.setInvertedMotor(MotorType.kRearRight, true);
    	
    	
    	rightPicker = new Talon(6);
    	leftPicker = new Talon(5);
    	lift = new Talon(4);
    	liftEncoder = new Encoder(6,7);
    	liftUpperLimit = new DigitalInput(8);
    	liftLowerLimit = new DigitalInput(9);
    	toteInRobot = new DigitalInput(4);
    	
    	compressor = new Compressor(0);
    	dogs = new Solenoid(0);
    	pusher = new Solenoid(1);
    	flipper = new Solenoid(2);
    	
    	
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    	gyro.reset();
    	backRightEncoder.reset();
    	backLeftEncoder.reset();
    	liftEncoder.reset();
    	//compressor.start();
    	
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	//put in gyro crab straight
    	/*
    	while(backLeftEncoder.getDistance() > 50){ //drive to first box
    		drive.mecanumDrive_Cartesian(0, -0.3, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	backLeftEncoder.reset();
    	
    	while(!toteInRobot.get()){ //pick box 
    		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		rightPicker.set(0.5);
    		leftPicker.set(-0.5);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    		
    	}
    	
    	while(liftEncoder.getDistance() < 1800 && !liftUpperLimit.get()){ //lift up
    		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(1);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	
    	while(liftEncoder.getDistance() > 0 && !liftLowerLimit.get()){ //lift down
    		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(-0.9);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	
    	while(backLeftEncoder.getDistance() < 200){ //crab left
    		drive.mecanumDrive_Cartesian(0.3, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	backLeftEncoder.reset();
    	
    	while(backLeftEncoder.getDistance() < 400){ //drive 4ward 
    		drive.mecanumDrive_Cartesian(0, -0.3, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	backLeftEncoder.reset();
    	
    	while(backLeftEncoder.getDistance() < 200){ //crab right
    		drive.mecanumDrive_Cartesian(-0.3, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	backLeftEncoder.reset();
    	
    	while(backLeftEncoder.getDistance() < 100){ //drive 4ward to pick up box
    		drive.mecanumDrive_Cartesian(0, -0.3, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	backLeftEncoder.reset();
    	
    	while(!toteInRobot.get()){ //pick box 
    		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		rightPicker.set(0.5);
    		leftPicker.set(-0.5);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    		
    	}
    	
    	while(liftEncoder.getDistance() < 1800 && !liftUpperLimit.get()){ //lift up
    		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(1);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	
    	while(liftEncoder.getDistance() > 0 && !liftLowerLimit.get()){ //lift down
    		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(-0.9);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	
    	while(backLeftEncoder.getDistance() < 200){ //crab left
    		drive.mecanumDrive_Cartesian(0.3, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	backLeftEncoder.reset();
    	
    	while(backLeftEncoder.getDistance() < 400){ //drive 4ward 
    		drive.mecanumDrive_Cartesian(0, -0.3, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	backLeftEncoder.reset();
    	
    	while(backLeftEncoder.getDistance() < 200){ //crab right
    		drive.mecanumDrive_Cartesian(-0.3, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	backLeftEncoder.reset();
    	
    	while(backLeftEncoder.getDistance() < 100){ //drive 4ward to pick up box
    		drive.mecanumDrive_Cartesian(0, -0.3, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	backLeftEncoder.reset();
    	
    	while(!toteInRobot.get()){ //pick box 
    		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		rightPicker.set(0.5);
    		leftPicker.set(-0.5);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    		
    	}
    	
    	while(liftEncoder.getDistance() < 800 && !liftUpperLimit.get()){ //lift up little bit
    		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(1);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	
    	while(backLeftEncoder.getDistance() < 1200){ //crab right into zone
    		drive.mecanumDrive_Cartesian(-0.3, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	backLeftEncoder.reset();
    	
    	autoLoopCounter = 0;
    	while(autoLoopCounter < 100){ //open dogs
    		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(true);
    		pusher.set(false);
    		autoLoopCounter++;
    	}
    	
    	while(liftEncoder.getDistance() > 0 && !liftLowerLimit.get()){ //lift down
    		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(-0.9);
    		dogs.set(true);
    		pusher.set(false);
    	}
    	
    	
    	while(backLeftEncoder.getDistance() > -300){ //do unload
    		drive.mecanumDrive_Cartesian(0, 0.3, 0, 0);//drive backwards
    		rightPicker.set(-0.5);//reverse rollers
    		leftPicker.set(0.5);
    		lift.set(0);
    		dogs.set(true);
    		pusher.set(true);
    	}
    	
    	while(true){ //stop
    		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		rightPicker.set(0);
    		leftPicker.set(0);
    		lift.set(0);
    		dogs.set(false);
    		pusher.set(false);
    	}
    	*/
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    	gyro.reset();
    	//compressor.start();
    	liftEncoder.reset();
    	liftUp = false;
    	liftDown = false;
    	unloadCounter = 0;
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

    	
        //arcade drive
        if(driveStick.getRawButton(1)){
        	drive.arcadeDrive(driveStick.getY(), driveStick.getX());
        	
        }else if(driveStick.getRawButton(2)){
        	drive.arcadeDrive(0, 0);
        }else{
        	drive.arcadeDrive(driveStick.getY()/2, driveStick.getX()/2);
        }
       
        if(driveStick.getRawButton(3)){ //flipper 
        	flipper.set(true);
        }else{
        	flipper.set(false);
        }
        
 //==============================================================================================
        //lift
        /*
        //testy test mode
        if(auxStick.getRawButton(3) && !liftUpperLimit.get()){  //up
        	lift.set(1);
        }else if(auxStick.getRawButton(2) && !liftLowerLimit.get()){  //down
        	lift.set(-0.9);
        }else{
        	lift.set(0);                            
        }
        */
      
        
        //human overide
        if(auxCard.getRawButton(12) || auxStick.getRawButton(3) || auxStick.getRawButton(2) || auxStick.getRawButton(10)) {
        	
        	liftUp = false; //stop auto lift
        	liftDown = false;
        	
        	if(auxStick.getRawButton(3) && !liftUpperLimit.get() && liftEncoder.getDistance() < upperSetPoint){  //up
            	lift.set(1);
            }else if(auxStick.getRawButton(2) && !liftLowerLimit.get() && liftEncoder.getDistance() > lowerSetPoint){  //down
            	lift.set(-0.9);
            
            }else if(auxCard.getRawButton(11)){ //set point //debug me!!!!!!!!!!!!!!!!!!!
            	if(liftEncoder.getDistance() > 200){ 
    				lift.set(-0.5);
    			}else if(liftEncoder.getDistance() < 140){
    				lift.set(0.5);
    			}else{
    				lift.set(0);
    			}
            }else if(auxStick.getRawButton(10) && !liftLowerLimit.get()){ //zero lift encoder
            	lift.set(-0.5); //see if statement below for encoder reset
            }else{
            	lift.set(0);                            
            }
        }else{ //auto lift
        	if(liftUpperLimit.get() || (liftLowerLimit.get() && !toteInRobot.get())){//stop at upper limits
        		liftUp = false;
        		liftDown = false;
        	}else{
        		if(toteInRobot.get() && !liftUp && !liftDown){//auto load up to dogs
        			liftUp = true; 
        			liftDown = false;
				
        		}else if(liftUp){ //go up to dogs
        			if(liftEncoder.getDistance() > (upperSetPoint + 20)){
        				liftUp = false;
        				liftDown = true;
        			}
				
        			lift.set(0.75);
				
        		}else if(liftDown){//go down to bottom
        			if(liftEncoder.getDistance() < (lowerSetPoint + 20)){
        				liftUp = false;
        				liftDown = false;
        			}
				
        			lift.set(-0.75);
	   
        		}else{
        			lift.set(0);
        		}	
        	}
        }
        
        if(auxStick.getRawButton(10) && liftLowerLimit.get()){ //reset lift encoder
        	liftEncoder.reset();
        	//System.out.println("encoder reset");
        }
//===================================================================================================
        //picker //unloading
        if(auxStick.getRawButton(1)){ //auto unload
        	if(auxStick.getRawButton(1) && unloadCounter <= 25){ //open dogs
        		dogs.set(true);
        		pusher.set(false);
        		rightPicker.set(0);
        		leftPicker.set(0);
        		unloadCounter++;
        	}else if(auxStick.getRawButton(1) && unloadCounter >= 25){ //open dogs push and reverses pickers
        		dogs.set(true);
        		pusher.set(true);
        		rightPicker.set(-0.5);
        		leftPicker.set(0.5);
        		unloadCounter++;
        	}else{ //do nothing
        		dogs.set(false);
        		pusher.set(false);
        		rightPicker.set(0);
        		leftPicker.set(0);
        	}
        }else if(!auxStick.getRawButton(1) && unloadCounter > 100){ //reset counter
    		unloadCounter = 0;
    		dogs.set(false);
    		pusher.set(false);
    		rightPicker.set(0);
    		leftPicker.set(0);
        	
        }else{
        
        	if(auxCard.getRawButton(9)){ //in
        		rightPicker.set(0.5);
        		leftPicker.set(-0.5); //left is opposite
        	}else if(auxCard.getRawButton(7)){ //out
        		rightPicker.set(-0.5);
        		leftPicker.set(0.5);
        	}else{
        		leftPicker.set(0);
        		rightPicker.set(0);		
        	}
        	if(liftEncoder.getDistance() > (upperSetPoint + 50)){
        		dogs.set(true);
        	}else if(auxStick.getRawButton(6)){
        		dogs.set(true);
        	}else{
        		dogs.set(false);
        	}
        
        	if(auxStick.getRawButton(7)){
        		pusher.set(true);
        	}else{
        		pusher.set(false);
        	}
        }
//============================================================================================        
        Timer.delay(0.005);
        //System.out.println(unloadCounter);
		//System.out.println(toteInRobot.get());
        //System.out.println(liftUpperLimit.get());
        //System.out.println(liftEncoder.getDistance());
        //System.out.println(liftUpperLimit.get() + " upper");
        //System.out.println(liftLowerLimit.get() + " lower");
        //System.out.println(liftEncoder.getDistance());
        //System.out.println(toteInRobot.get());

    }

    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
