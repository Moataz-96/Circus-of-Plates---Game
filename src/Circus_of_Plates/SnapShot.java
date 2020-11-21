package Circus_of_Plates;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;


public class SnapShot {

	private BufferedImage screenShot;
	public BufferedImage screenShot(int startX, int startY, int endX, int endY){
		try
		{
			Rectangle rect = new Rectangle(startX, startY, endX, endY);
			screenShot = new Robot().createScreenCapture(rect);
			return screenShot;
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
		return screenShot;
	}
	
	public void save(BufferedImage Image)
	{		
		int i = 1;
			do{
				try{
				File f = new File("ScreenShots/SCREENSHOT_"+i+".png");
				if(!f.exists())
					break;
				i++;
				}catch(Exception eo){}
			}while(true);
		
			try{
		
				ImageIO.write(Image, "png", new File("ScreenShots/SCREENSHOT_"+i+".png"));
				
				System.out.println("Its Saved Succefully");
				
			}catch(Exception ex){
				
				ex.printStackTrace();
				
			}
		
	}
	
	@SuppressWarnings("resource")
	public World load(String filename)
	{
		World toRet;
		try
		{
			// Read from disk using FileInputStream
			FileInputStream fIn = new FileInputStream(filename);
	
			// Read object using ObjectInputStream
			ObjectInputStream objIn = new ObjectInputStream (fIn);
	
			// Read an object
			Object obj = objIn.readObject();
	
			if (obj instanceof World)
			{
				// Cast object to a Vector
				toRet = (World) obj;
				// Do something with vector....
				return toRet;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public void Pause(Boolean lost , int WIDTH , int HEIGHT){
		int ScaleHeight = 1080;
		try {
			GameEngine.s.getFullScreenWindow().addMouseListener((MouseListener) GameEngine.s.getFullScreenWindow());
			BufferedImage back = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			back = ImageIO.read(new File("Images/background2.png"));
			BufferedImage Resume = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			Resume = ImageIO.read(new File("Images/ResumeButton.png"));
			BufferedImage exit = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			exit = ImageIO.read(new File("Images/exitGameButton.png"));
			BufferedImage Restart = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			Restart = ImageIO.read(new File("Images/Restart.png"));
			BufferedImage Tryagain = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			Tryagain = ImageIO.read(new File("Images/tryagain.png"));
			BufferedImage newGame = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			newGame = ImageIO.read(new File("Images/newGameBtn.png"));

			while (GameEngine.checkEscape) {
				Graphics g = GameEngine.s.getFullScreenWindow().getGraphics();
				g.drawImage(back, 0, 0, WIDTH, HEIGHT, null);
				g.drawImage(exit, WIDTH*5/12, HEIGHT*13/27, WIDTH*5/32, HEIGHT/9, null);   //800,520,300,120
				if (!lost) {
					g.drawImage(Resume,WIDTH*5/12, HEIGHT*370/ScaleHeight, WIDTH*5/32, HEIGHT/9, null); //800,370,300,120
					g.drawImage(Restart, WIDTH*5/12, HEIGHT*5/27, WIDTH*5/32, HEIGHT/9, null);  //800,200,300,120 
				} else {
					g.drawImage(newGame, WIDTH*5/12, HEIGHT*370/ScaleHeight, WIDTH*5/32, HEIGHT/9, null); //800,370,300,120
					g.drawImage(Tryagain, WIDTH*5/12, HEIGHT*5/27, WIDTH*5/32, HEIGHT/9, null); //800,200,300,120
				}
				Thread.sleep(200);
				g.dispose();
			}
		} catch (Exception ez) {
		}

		try {
			GameEngine.s.getFullScreenWindow().removeMouseListener((MouseListener) GameEngine.s.getFullScreenWindow());
		} catch (Exception e) {
		}
	}
	
}