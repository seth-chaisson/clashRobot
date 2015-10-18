package clashRobot;


import org.opencv.core.Core;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.sql.Time;

import javax.swing.*;

public class ClashRobot 
{
	static JFrame frame= null;
	static JLabel l = null;
	static JButton start = null;
	static ScreenScrape s =null;
	static BufferedImage image = null;
	static Robot r = null;
	static Rectangle screenSize = null;
	static org.opencv.core.Point p = null;
	
	static final int	CIRCLE_STRATAGY_BARBARIANS =1;
	
	
	public static void main(String[] args)
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("loaded\n");
		
		try {r = new Robot();} catch (AWTException e) {}
		
		screenSize =new Rectangle( Toolkit.getDefaultToolkit().getScreenSize());
		System.out.println(screenSize.toString() + "\n");
		showMainWidnow();
		
		// take screenshot
		// create new screenscrape(img)
		// 
		
			
			
			// record mouse motion https://code.google.com/p/jnativehook/
			//https://code.google.com/p/jintellitype/  --register hot key
			s = new ScreenScrape();
			
			
			
			
			
			// find the bluestacks button
			image = r.createScreenCapture(screenSize);
			p = s.findBlueStacks(image);
			r.mouseMove((int)p.x, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			
			r.delay(1000);
			
			checkReload();
			
			long troopTimer = 0,
					runTimer=0;
			
			int battleCount =0;
			runTimer = System.currentTimeMillis();
			
			while(true)
			{
				
				l.setText("Battle count= " + battleCount + "\n"+
							"   Run time = " 
						+(System.currentTimeMillis()- runTimer )/(1000*60*60) + ":" 
						+(System.currentTimeMillis()- runTimer )/(1000*60) %60 + ":"
						+(System.currentTimeMillis()- runTimer )/(1000) % 60
						
								
						
						);
				

				//System.out.println("check reload");
				checkReload();			
				

				
				//System.out.println("get resources");
				getResources();
				
				//System.out.println("check donate");
				checkDonate();	
				
				
				//System.out.println("are troops done");
				
				if((troopTimer+60000) < System.currentTimeMillis() && areTroopsDone() == true )
				{
					battleCount++;
					//System.out.println("do battle--------------battle#: " + battleCount);
					doBattle(CIRCLE_STRATAGY_BARBARIANS);
					
					
				}
				else if((troopTimer+60000) < System.currentTimeMillis())
				{ // if its been 1 min  since the last time 
					
					//System.out.println("build troops if not done and 1 min");
					buildTroops(CIRCLE_STRATAGY_BARBARIANS);
					troopTimer = System.currentTimeMillis();
				}
			
				
			}
				
			
			
		
		
		
	}
	
	public static void buildTroops(int stratagy)
	{
		if(stratagy == CIRCLE_STRATAGY_BARBARIANS)
		{
			//open troop window
			image = r.createScreenCapture(screenSize);
			p = s.findOpenArmyButton(image);
			if(p.x !=0 && p.y != 0)
			{	
				r.mouseMove((int)p.x, (int)p.y);
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
				r.delay(800);
			}
			else
			{
				//break
				error("build troops-couldnt find open army button");
				return;
				
			}
			// click on barbarians
			
			image = r.createScreenCapture(screenSize);
			p = s.findCreateTroopsLandmark(image);
			
			// click 1st barracks tab
			r.mouseMove((int)p.x + 93, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(400);
			
			// hold down the mouse button for barbarians
			r.mouseMove((int)p.x + 64, (int)p.y-184);
			for(int x=0;x< 20; x++)
			{
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
				r.delay(50);
			}
			
			
			// click 2nd barracks tab
			r.mouseMove((int)p.x + 147, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(400);
			
			// hold down the mouse button for barbarians
			r.mouseMove((int)p.x + 64, (int)p.y-184);
			for(int x=0;x< 20; x++)
			{
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
				r.delay(50);
			}
			
			// click 3nd barracks tab
			r.mouseMove((int)p.x + 200, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(400);
			
			// hold down the mouse button for barbarians
			r.mouseMove((int)p.x + 64, (int)p.y-184);
			for(int x=0;x< 20; x++)
			{
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
				r.delay(50);
			}
			
			
			//close troop window
			image = r.createScreenCapture(screenSize);
			p = s.findCloseTroopWindow(image);
			if(p.x !=0 && p.y != 0)
			{	
				r.mouseMove((int)p.x, (int)p.y);
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
				r.delay(200);
			}
			
		}
		
	}
	public static void doBattle(int stratagy)
	{
		
		//start a battle
		// click attack button
		image = r.createScreenCapture(screenSize);
		p = s.findAttackButton(image);
		if(p.x !=0 && p.y != 0)
		{	
			r.mouseMove((int)p.x, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(200);
		}
		else
		{
			error("cant find attack button");
			return;
		}
		
		
		// click find match
		image = r.createScreenCapture(screenSize);
		p = s.findMatchButton(image);
		if(p.x !=0 && p.y != 0)
		{	
			r.mouseMove((int)p.x, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(10000);
		}
		else
		{
			error("cant find match button");
			return;
		}
		
		
		image = r.createScreenCapture(screenSize);
		p = s.findShieldBreakOkButton(image);
		if(p.x !=0 && p.y != 0)
		{	
			r.mouseMove((int)p.x, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(10000);
		}
		
		
		
		
		// attack stratagy
		if(stratagy == CIRCLE_STRATAGY_BARBARIANS)
		{
			image = r.createScreenCapture(screenSize);
			p = s.findEmptySpace(image);
			if(p.x !=0 && p.y != 0)
			{	
				r.mouseMove((int)p.x-56, (int)p.y-114);
				/*
				// zoom out
				r.keyPress(KeyEvent.VK_CONTROL);
				r.mouseWheel(100);
				r.keyRelease(KeyEvent.VK_CONTROL);
				*/
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.delay(1000);
			}
			else
			{
				error("cant find empty space");
				return;
			}
			
			
			long time = System.currentTimeMillis();
			double angle =0,
					step= 0.00001;
			// loop for 30 sec and move in a circle
			while(time+30000 > (System.currentTimeMillis()) )
			{
				int x,y;
				x = (int) ((screenSize.width/3) * Math.cos(angle))+screenSize.width/2;
				y = (int) ((screenSize.height/3)* Math.sin(angle))+screenSize.height/2;
				
				r.mouseMove(x, y);
				
				angle+= step;
			}
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			
		}
		
		// leave battle
		do
		{// look for the return home button
			image = r.createScreenCapture(screenSize);
			p = s.findAttackReturnHomeButton(image);	
			
		}while(p.x ==0 && p.y == 0); // loop untill it pops up
		r.mouseMove((int)p.x, (int)p.y);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(4000);
		
		
	}
	
	
	public static boolean areTroopsDone()
	{
		boolean troops = false;
		
		
		// look for barracks full
		
		/*
		image = r.createScreenCapture(screenSize);
		p = s.findBarracksFull(image);
		if(p.x !=0 && p.y != 0)
		{	
			return true;
		}
		else 
			return false;
			*/
		
		//---------checks troops window
		//open troop window
		image = r.createScreenCapture(screenSize);
		p = s.findOpenArmyButton(image);
		if(p.x !=0 && p.y != 0)
		{	
			r.mouseMove((int)p.x, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(800);
		}
		else
		{
			//break
			error("are troops done-couldnt find open army button");
			return false;
		}
		// look for the green check mark
		image = r.createScreenCapture(screenSize);
		p = s.findTroopsFull(image);
		if(p.x !=0 && p.y != 0)
		{	
			
			troops = true;
			
		}
		else
		{
			troops = false;
			
		}
		
		image = r.createScreenCapture(screenSize);
		p = s.findCloseTroopWindow(image);
		if(p.x !=0 && p.y != 0)
		{	
			r.mouseMove((int)p.x, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(900);
		}
		
		
		return troops;
		
	}
	
	public static void showMainWidnow()
	{
		frame = new JFrame("clashbot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l = new JLabel();
		l.setPreferredSize(new Dimension(250,600));
		frame.getContentPane().add(l, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		
		l.setText("loaded");
	}
	public static void checkReload()
	{
		// look for reload game button
		image = r.createScreenCapture(screenSize);
		p = s.findReload(image);
		if(p.x !=0 && p.y != 0)
		{	
			r.mouseMove((int)p.x, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(10000); 
			System.out.println("reloaded");
		}
		
		// check for the attack summery window
		image = r.createScreenCapture(screenSize);
		p = s.findAttackedOkButton(image);
		if(p.x !=0 && p.y != 0)
		{	
			r.mouseMove((int)p.x, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(1000); 
			
		}
		
		
		
	}
	public static void getResources()
	{
		// find gold 
		//click gold

		do
		{
			image = r.createScreenCapture(screenSize);
			p = s.findGold(image);
			if(p.x !=0 && p.y != 0)
			{	
				r.mouseMove((int)p.x, (int)p.y);
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
			}
		}while(p.x !=0 && p.y != 0);
		
	// find elixer
		//click

		do
		{
			image = r.createScreenCapture(screenSize);
			p = s.findElixer(image);
			if(p.x !=0 && p.y != 0)
			{	
				r.mouseMove((int)p.x, (int)p.y);
				r.mousePress(InputEvent.BUTTON1_MASK);
				r.mouseRelease(InputEvent.BUTTON1_MASK);
			}
		}while(p.x !=0 && p.y != 0);
	}
	
	public static void checkDonate()
	{
		// look for new message icon
		
	
		image = r.createScreenCapture(screenSize);
		p = s.findNewMessage(image);
		
		if(p.x !=0 && p.y != 0)
		
		{
					
			// open chat
			System.out.println("open chat\n");
			image = r.createScreenCapture(screenSize);
			p = s.findOpenChat(image);
			r.mouseMove((int)p.x, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(800);
		// check donate
			do
			{
				
			
				image = r.createScreenCapture(screenSize);
				p = s.findDonate(image);
				if(p.x !=0 && p.y != 0)
				{	
					r.mouseMove((int)p.x, (int)p.y);
					r.mousePress(InputEvent.BUTTON1_MASK);
					r.mouseRelease(InputEvent.BUTTON1_MASK);
					
					r.delay(200);
					
					image = r.createScreenCapture(screenSize);
					p = s.findBarbarian(image);
					
					r.mouseMove((int)p.x, (int)p.y);
					
					for(int x=0; x<8 ;x++)
					{
						r.mousePress(InputEvent.BUTTON1_MASK);
						r.mouseRelease(InputEvent.BUTTON1_MASK);
						r.delay(100);
					}
				}
			}while(p.x !=0 && p.y != 0);
			
			
			r.delay(300);
		// close chat
			image = r.createScreenCapture(screenSize);
			p = s.findCloseChat(image);
			r.mouseMove((int)p.x, (int)p.y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.delay(1000);
			
		}
		
		
	}
	
	public static void error(String err)
	{
		System.out.println(err);

		
		// wait for a disconnect
		for(int x=0; x<10; x++)
		{
			l.setText("error-waiting:" + x+"/10 min");
			r.delay(60000);// r has a max delay of 1 min
		}
		checkReload();  
	}
	
}
