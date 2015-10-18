package clashRobot;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.*;



public class ScreenScrape 
{
	 private int 	gold=0,
			 		elixer=0,
			 		trophys=0,
			 		score=0,
			 		lootGold=0,
			 		lootElixer=0,
			 		stars=0;
	

	private boolean atHomeScreen = false,
					atBattleScreen = false,
					atTroopScreen = false,
					atDisconnectScreen = false,
					atBattleOverScreen = false;
	
	private Point   gameWindowLocation = null;
	private Mat		gameMat				=null;
	
						
	private Mat		any = null,
					close_chat = null,
					donate = null,
					elixer_collect = null,
					gold_collect = null,
					land_mark = null,
					open_army = null,
					open_chat = null,
					reload_game = null,
					reload_game2 = null,
					reload_game3 = null,
					blue_stacks =null,
					donate_barbarian = null,
					new_message 	=null,
					stock_donate_message = null,
					troops_full	=null,
					close_troop_window= null,
					create_troops_landmark =null,
					barracks_full	= null,
					attack_return_home_button = null,
					empty_space = null,
					shield_break_ok_button = null,
					find_match_button= null,
					attack_button = null,
					troops_full_landmark =null,
					attacked_ok_button = null;
					
					
	
	public ScreenScrape()
	{
		
		
		//load templates
		any 			= Imgcodecs.imread("template/any.bmp");
		close_chat 		= Imgcodecs.imread("template/close_chat.bmp");
		donate 			= Imgcodecs.imread("template/donate.bmp");
		elixer_collect 	= Imgcodecs.imread("template/elixer_collect.bmp");
		gold_collect	= Imgcodecs.imread("template/gold_collect.bmp");
		land_mark		= Imgcodecs.imread("template/land_mark.bmp");
		open_army 		= Imgcodecs.imread("template/open_army.bmp");
		open_chat 		= Imgcodecs.imread("template/open_chat.bmp");
		reload_game 	= Imgcodecs.imread("template/reload_game.bmp");
		blue_stacks		= Imgcodecs.imread("template/blue_stacks.bmp");
		donate_barbarian= Imgcodecs.imread("template/donate_barbarian.bmp");
		new_message		= Imgcodecs.imread("template/new_message.bmp");
		stock_donate_message = Imgcodecs.imread("template/stock_donate_message.bmp");
		troops_full			= Imgcodecs.imread("template/troops_full.bmp");
		close_troop_window =  Imgcodecs.imread("template/close_troop_window.bmp");
		create_troops_landmark = Imgcodecs.imread("template/create_troops_landmark.bmp");
		barracks_full			= Imgcodecs.imread("template/barracks_full.bmp");
		attack_return_home_button = Imgcodecs.imread("template/attack_return_home_button.bmp");
		empty_space = Imgcodecs.imread("template/empty_space.bmp");
		shield_break_ok_button = Imgcodecs.imread("template/shield_break_ok_button.bmp");
		find_match_button= Imgcodecs.imread("template/find_match_button.bmp");
		attack_button = Imgcodecs.imread("template/attack_button.bmp");
		troops_full_landmark = Imgcodecs.imread("template/troops_full_landmark.bmp");
		reload_game2 	= Imgcodecs.imread("template/reload_game2.bmp");
		reload_game3 	= Imgcodecs.imread("template/reload_game3.bmp");
		attacked_ok_button= Imgcodecs.imread("template/attacked_ok_button.bmp");
		

	}
	
	public void grabData(BufferedImage image) 
	{
		
		gameMat = bufferedImage2Mat(image);
			//crop area
			// collect data
			gold	=	Ocr.readInt(gameMat);
	 		elixer	=	Ocr.readInt(gameMat);
	 		trophys	=	Ocr.readInt(gameMat);
	 		score	=	Ocr.readInt(gameMat);
			
		
		
		
	}

	private void idGameScreen(Mat gameMat2) 
	{
		//template match for icons unique to each screen
		
		
	}

	private void findGameWindow(Mat gameMat2) 
	{
		// template match the gold coin
		// use that to calculate the upper right corner position
		
		
	}

	private BufferedImage markTemplate(BufferedImage image)
	{
		Mat template = Imgcodecs.imread("template/4.bmp");
		Mat img		=  Imgcodecs.imread("template/image.bmp");//bufferedImage2Mat(image);
		
		int resultCol = img.cols() - template.cols() + 1;
		int resultrow = img.rows() - template.rows() + 1;
		
		
		Mat result = new Mat(resultCol, resultrow, CvType.CV_8U);
		
		Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
		Imgproc.cvtColor(template, template, Imgproc.COLOR_RGB2GRAY);
		
		
		Imgproc.matchTemplate(img, template, result, Imgproc.TM_SQDIFF_NORMED);
		
		
		Imgproc.threshold(result, result, 0.05, 0.1, Imgproc.THRESH_BINARY_INV);
		Core.normalize(result, result, 0, 255, Core.NORM_MINMAX, -1, new Mat());
		
		
		
		
		
		MinMaxLocResult mmr = Core.minMaxLoc(result);

		Imgproc.rectangle(img, mmr.maxLoc, new Point(mmr.maxLoc.x + template.cols(),
				mmr.maxLoc.y + template.rows()), new Scalar(255, 255, 255));
		
		Imgcodecs.imwrite("test.jpg", result);
		
		showImage(mat2bufferedImage(img));
		
		showImage(mat2bufferedImage(template));
				
		
		
		return image;
	}

	public void showImage(BufferedImage i)
	{
		JFrame frame = new JFrame("output");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel l = new JLabel( new ImageIcon(i));
		l.setPreferredSize(new Dimension(i.getHeight(),i.getWidth()));
		frame.getContentPane().add(l, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		
	}


	public int getGold() {
		return gold;
	}


	public int getElixer() {
		return elixer;
	}


	public int getTrophys() {
		return trophys;
	}


	public int getScore() {
		return score;
	}


	public boolean isAtHomeScreen() {
		return atHomeScreen;
	}


	public Point getGameWindowLocation() {
		return gameWindowLocation;
	}

	public int getLootGold() {
		return lootGold;
	}


	public int getLootElixer() {
		return lootElixer;
	}


	public boolean isAtBattleScreen() {
		return atBattleScreen;
	}


	public boolean isAtTroopScreen() {
		return atTroopScreen;
	}


	public boolean isAtDisconnectScreen() {
		return atDisconnectScreen;
	}
	public static BufferedImage mat2bufferedImage(Mat m) {


        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
       
        m.get(0, 0, ((DataBufferByte)image.getRaster().getDataBuffer()).getData());
        
        return image;

    }
	public static Mat bufferedImage2Mat(BufferedImage o)
	{
		BufferedImage i = toBufferedImageOfType(o, BufferedImage.TYPE_3BYTE_BGR);
		byte[] pixels = ((DataBufferByte) i.getRaster().getDataBuffer()).getData();
		Mat m = new Mat(i.getHeight(), i.getWidth(),CvType.CV_8UC3 );
		m.put(0, 0, pixels);
		return m;
	}
	public static BufferedImage toBufferedImageOfType(BufferedImage original, int type) {
	    if (original == null) {
	        throw new IllegalArgumentException("original == null");
	    }

	    // Don't convert if it already has correct type
	    if (original.getType() == type) {
	        return original;
	    }

	    // Create a buffered image
	    BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), type);

	    // Draw the image onto the new buffer
	    Graphics2D g = image.createGraphics();
	    try {
	        g.setComposite(AlphaComposite.Src);
	        g.drawImage(original, 0, 0, null);
	    }
	    finally {
	        g.dispose();
	    }

	    return image;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public boolean isAtBattleOverScreen() {
		return atBattleOverScreen;
	}

	public void setAtBattleOverScreen(boolean atBattleOverScreen) {
		this.atBattleOverScreen = atBattleOverScreen;
	}

	public Point findBlueStacks(BufferedImage image)
	{
		
		
		
		return findTemplate(image, blue_stacks);
	}
	public Point findReload(BufferedImage image)
	{
		Point p = findTemplate(image, reload_game);
		
		if(p.x == 0 && p.y == 0)
			p = findTemplate(image, reload_game2);
		else if(p.x == 0 && p.y == 0)
			p = findTemplate(image, reload_game3);
		
		return p;
	}
	
	private Point findTemplate(BufferedImage image, Mat template)
	{
		
		Mat img		=  bufferedImage2Mat(image);
		
		
		int resultCol = img.cols() - template.cols() + 1;
		int resultrow = img.rows() - template.rows() + 1;
		
		
		Mat result = new Mat(resultCol, resultrow, CvType.CV_8U);
		
		//Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
		//Imgproc.cvtColor(template, template, Imgproc.COLOR_RGB2GRAY);
		
		
		Imgproc.matchTemplate(img, template, result, Imgproc.TM_SQDIFF_NORMED);
		
		
		Imgproc.threshold(result, result, 0.05, 0.1, Imgproc.THRESH_BINARY_INV);
		Core.normalize(result, result, 0, 255, Core.NORM_MINMAX, -1, new Mat());
		
		
		
		
		
		MinMaxLocResult mmr = Core.minMaxLoc(result);
		

		double[] a = result.get( (int)mmr.maxLoc.y,(int)mmr.maxLoc.x);
		
		
		
		
		if(a[0] < 200)
			return new Point(0,0);
		
	
		
		
		return new Point(mmr.maxLoc.x + (template.cols()/2),mmr.maxLoc.y+(template.rows()/2));
		
	}

	public Point findElixer(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, elixer_collect);
	}

	public Point findGold(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, gold_collect);
	}

	public Point findCloseChat(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, close_chat);
	}

	public Point findOpenChat(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, open_chat);
	}

	public Point findDonate(BufferedImage image) {
		// TODO Auto-generated method stub
		
		// find donate button
		Point donate_loc = findTemplate(image, donate);
		if(donate_loc.x < 150  && donate_loc.y < 150 )
			return new Point(0,0);
			
		// read the message if "any"  or "i need reinforcments" donate
		image = image.getSubimage((int)donate_loc.x - 150, (int)donate_loc.y -150, 150,150);
		 Point donate_msg = findTemplate(image, stock_donate_message);
		 if(donate_msg.x == 0 && donate_msg.y == 0)
			 donate_msg = findTemplate(image, any);
		 

		if(donate_msg.x != 0 && donate_msg.y !=0)
			return donate_loc ;
		else 
			return new Point(0,0);
	}

	public Point findBarbarian(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, donate_barbarian);
	}

	public Point findNewMessage(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, new_message);
	}

	public Point findOpenArmyButton(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, open_army);
	}

	public Point findTroopsFull(BufferedImage image) {
		// TODO Auto-generated method stub
		
		Point landmark = findTemplate(image, troops_full_landmark);
		
		if(landmark.x > 300)
			image = image.getSubimage((int)landmark.x-300, (int)landmark.y, 150, 50);
		else
			return new Point(0,0);
		
		
		
		
		return findTemplate(image, troops_full);
	}

	public Point findCloseTroopWindow(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, close_troop_window);
	}

	public Point findCreateTroopsLandmark(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, create_troops_landmark);
	}

	public Point findBarracksFull(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, barracks_full);
	}

	public Point findAttackButton(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, attack_button);
	}

	public Point findMatchButton(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, find_match_button);
	}

	public Point findEmptySpace(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, empty_space);
	}

	public Point findAttackReturnHomeButton(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, attack_return_home_button);
		}

	public Point findShieldBreakOkButton(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, shield_break_ok_button);
	}

	public Point findAttackedOkButton(BufferedImage image) {
		// TODO Auto-generated method stub
		return findTemplate(image, attacked_ok_button);
	}
	

}
