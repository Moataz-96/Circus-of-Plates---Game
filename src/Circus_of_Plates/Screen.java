package Circus_of_Plates;

import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * 
 * 
 * 
 */
public class Screen {
	
	private static Screen screen; // -->Singleton Pattern
	//GraphicsDevice dh el output bta3 kol el Properties fel device  zy (vega , ram , resolution screen . best graphics ,,)
	private GraphicsDevice vc; // so i declared Object vc(Video Card) from GrapicsDevice Class
	//el vc bt3ml output lel 7gat kolha bs msh heya el m3aha kol el 7aga dy heya btzhrhom bs
	private Screen(){
		GraphicsEnvironment  env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		//Graphics Environment hwa el m3ah kol el properties bta3t  ghazk fa hwa hydeha lel video card 3shan t3mlk output lehom
		vc = env.getDefaultScreenDevice();
		//hena el vc kda t2dr ttl3lk kol el 7gat bta3t el ghaz bta3k
	}
	
	public static synchronized Screen getInstance(){
		if(screen == null)
			screen = new Screen();
		return screen;
	}
	
	//bt return el videoCard.getdisplaymode el heya best displaymodes for your device dy btrg3hom
	public DisplayMode[] getDisplayModes(){
		return vc.getDisplayModes();
	}
	
	//htb3t el displaymodes w tshof lw fehom displaymode matching m3 el best displaymodes el fo2eya dy heya ht5ldholk dh best desplaymode
	public DisplayMode findFirstCompatibleMode(DisplayMode Modes[]){
		DisplayMode goodModes[] = getDisplayModes().clone();
		for(int i = 0 ; i < Modes.length ; i ++)
			for(int j = 0 ; j < goodModes.length ; j++){
				if(displayModesMatch(Modes[i],goodModes[j])){
					return Modes[i];
				}
			}
		return null;
	}
	
	//DIsplayMode check matching lazm ttcheck 3la 3 7gat --->   resolution , bitDipth , refreshRate 
	public boolean displayModesMatch(DisplayMode m1 , DisplayMode m2){
		//first check for resoltuion
		if(m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight()){
			return false;
		}
		// second check for bitDipth
		if(m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m1.getBitDepth() != m2.getBitDepth()){
			return false;	
		}
		//third check for refreshRate
		if(m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m1.getRefreshRate() != m2.getRefreshRate()){
			return false;
		}
		return true;
	}
	
	
	// set Full Screen
	public void setFullScreen(DisplayMode dm,JFrame window){
		//window dh el JFrame ana ana mst5dmo hnak
		window.setUndecorated(true);	// mn3'er gwanb y3ne mfesh feha bars
		window.setIgnoreRepaint(true);	// msh hyro7 le method el repaint awl myt3ml y3ne awl repaint dy msh h3mlha
		window.setResizable(false);	//msh hyt3mlo resizable y3ne msh hytkbr w yts3'r
	
		
		vc.setFullScreenWindow(window);	//el video card h7ot feh el full screen el JFrame
		//lw feh displaymode best msh btsawy null w el vc et3'yrt w hena hena et3'yrt b3d setFullSCreen
		if(dm != null && vc.isDisplayChangeSupported()){
			try{
			vc.setDisplayMode(dm);	//hy7ot bestdisplaymode fel video card
			}catch(Exception e){}
		}	
		window.createBufferStrategy(2); // 2 7ta lw homa kter
		// bufferStrategey dy 3shan t5le el swr bttbdl asr3
		//msln lw 3ndy background el awlnya bta3t new game w hwa das 3la new game httbdl el background fa hena hytbdlo bsor3a
		//3d el bufferstrateget w dymn btb2a 2 3shan enta btbdl sorten wra b3d fe kol mra btbdl feha 7aga
	}
	
	
	//return best graphics
	public Graphics2D getGraphics(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			BufferStrategy bs = w.getBufferStrategy();
			return (Graphics2D)bs.getDrawGraphics();
		}
		return null;
	}
	
	
	
	public void Update(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			BufferStrategy bs = w.getBufferStrategy();
			if(!bs.contentsLost()){
				bs.show();
				
			}			
		}
	}
	
	//bt return el fullScreenWiindow el enta 7tet 3leha kol el 7gat el gdeda
	public Window getFullScreenWindow(){
		return vc.getFullScreenWindow();
	}
	
	//btrg3 el width bta3 resolution el screen bt3tk
	public int getWidth(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			return w.getWidth();
		}
		return 0;
	}
	
	//btrg3 el height bta3 resolution el screen bt3tk
	public int getHeight(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			return w.getHeight();
		}
		return 0;
	}
	

	// get out of screen
	public void restoreScreen(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			w.dispose();  // bt2fl el screen
		} 
		vc.setFullScreenWindow(null); //w bt7ot el full screen bta3t el vc b null
	}
	
	// get image compatible with your monitor
	public BufferedImage createCompatibleImage(int width,int height,int trans){ // trans for transperancy
		Window w = vc.getFullScreenWindow();
		if(w != null){
			//graphicsConfiguration btgeb Scaling el shasha w bt7dd best resolution for scaling images
			GraphicsConfiguration gc = w.getGraphicsConfiguration();
			return gc.createCompatibleImage(width, height,trans);
		}
		return null;
	}
	
	
	
	

}
