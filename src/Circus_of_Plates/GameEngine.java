package Circus_of_Plates;

import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GameEngine extends JFrame implements MouseListener,KeyListener{
	private FlyWeight fly ;
	private Strategy Mode;
	private LinkedList<HashMap<Integer,String>> Shapes;
	public static Screen s;  // Screen mangager it's static to use it In View Class
	private Sprite sprite[];	//an array of sprite ( w homa 12 bs ) 7sb 3dd el ashkal el mwgoda (Enum) el Objects
	private View view;  // Object mn Class el View 3shsan a3ml el JFrame add this object 3shan View extends JCombonent w a3rf arsm
	private Point point ; // Point for Mouse Pressed el mkan el hydos feh el mouse 
	private JButton newGame,exitGame,Restart,Tryagain; //3shan a7ot feha setbounds bs..mlhash d3wa b ay 7aga b7otlha setbounds bs 3shan lma a5od point el mouse ashofha nfs mkan el buttons wla l2
	private BufferedImage newGamaImg,exitGameImg; //dol el mohmen el swr bt3t el newGame wel exit game 3rft dol bs hena 3shan dol awl 7aga hyzhro lw das newgame hyro7 le View w ykml b2et el zrayr lw 3mlt exit msh hyro7 fe 7ta w hy3ml exit
	private int  WIDTH,HEIGHT;  //dol el width wel height bto3 el screen el mwgoda fe shasha el hyft7 el le3ba
	private static GameEngine Engine;
	private static final DisplayMode modes[] = { // dy mo3zm el possible modes el mwgoda fel labtobs hwa hya5od a7sn displaymode w ysh3'l el le3ba 3la asas el resolution dh
		
	//	new DisplayMode(1920,1080,32,0),
	//	new DisplayMode(1920,1080,24,0),
	//	new DisplayMode(1920,1080,16,0),
	//	new DisplayMode(1600,900,32,0),
	//		new DisplayMode(1600,900,24,0),
	//		new DisplayMode(1600,900,16,0),

	//		new DisplayMode(1330,960,32,0),
	//		new DisplayMode(1330,960,24,0),
	//		new DisplayMode(1330,960,16,0),
			new DisplayMode(800,600,32,0),
			new DisplayMode(800,600,24,0),
			new DisplayMode(800,600,16,0),
			new DisplayMode(600,480,32,0),
			new DisplayMode(600,480,24,0),
			new DisplayMode(600,480,16,0),
			new DisplayMode(640,360,32,0),
			new DisplayMode(640,360,24,0),
			new DisplayMode(640,360,16,0),	
	};
	
	
	//Constructor
	private GameEngine(Strategy Mode){
		s = Screen.getInstance();   //initalize object s from screen
	//	sprite = new Sprite[20];  // heya mwgod feha 12 bs lw 3ayz tzwd shapes ht7otha 3ltol fe Enum el Objects el shkl el gded w feh l7d 20 shape godad
		Shapes = new LinkedList<HashMap<Integer,String>>();
		this.Mode = Mode;
	}
	
	public static synchronized GameEngine getInstance(Strategy Mode){
		if(Engine == null)
			Engine = new GameEngine(Mode);
		return Engine;
	}
	//Main Method
/*	public static void main(String args[]){
		// m3mltsh call lel method 3ltol 3shan el main static method lazm lw nadet 3la method tnya tkon static fa 3mlt 
		//object mn el class el ana feh 3shan anady 3la el methods 
		
	//	GameEngine engine = new GameEngine();
	//	engine.runProg();
		new GameEngine().runProg(); 
	} */
	public void runProg(){

		try{
			
		this.addMouseListener(this); // 3shan lw dost 3al mouse yp2a belnsba lel JFrame == ObjectFromJFrame.addmouseListener(ObjectFromJFrame)
		LoadPic();		 //Loading pictures(newGameBtn,exitGameBtn and initialize sprite of BufferedImages 		
		loadShapes();
		view = View.getInstance(sprite,Mode);
		this.add(view);  //Line dh object mn View(extends JComponent) k2ne b2ol ObjectFromJFrame.add(Line) 3shan yrsm gwaha	
		DisplayMode dm = s.findFirstCompatibleMode(modes); // Screen method to get best mode for your machine w 3la asaso ht3ml el full screen el resolution bt3tha tkon wd7a belnsba lel machine el m3ak
		s.setFullScreen(dm,this);  // parameter (Best DisplayMode , Object From JFrame);
		WIDTH = s.getWidth();   // width = screen.getWidth() belnsba lel resolution bt3tk
		HEIGHT=s.getHeight();	// same for width
		view.setScreenWidth(this.WIDTH);
		view.setScreenHeigth(this.HEIGHT);
		this.addKeyListener(this);	// ObjectFromJframe.addkeyListener(ObjectFromJFrame) for only clowns 3shan byege shmal w ymen
		init(); //init bounds for Buttons
		
		// feh method fel screen esmaha getGraphics btreturn VideoCard.getGraphics bs msh dy el hnst5dmha
		//hst5dm el graphics bta3t el screen nfsaha el lsa 3mltha so .. Screen.getfullscreen.getgraphics
		//w Graphics g dy el btrsm ay 7aga fel shasha fana hrsm hena background , w newGame Button w exitGame Button
		Graphics g = s.getFullScreenWindow().getGraphics();
		//3shan msh h3rf ageb el index bta3 kol w7da fa 3mlt Enum ageb mno rkm el index bta3 el klma el hdehalo
		//w kda kda mt3rfen bnfs el index fel array sprite
		//g.drawIamge(BufferedImage,x,y,width,height,null);
		g.drawImage(sprite[Objects.background1.getIndex()].getSpriteImages(),0,0,s.getWidth(),s.getHeight(),null);
		g.drawImage(newGamaImg,WIDTH*5/12, HEIGHT*370/1080, WIDTH*5/32, HEIGHT/9,null); // 800 , 370 , 300 , 120
		g.drawImage(exitGameImg,WIDTH*5/12, HEIGHT*13/27, WIDTH*5/32, HEIGHT/9,null); // 800,520,300,120
		g.dispose();
		//g.dispose lazm ttktb lw 3ayz tsbt 3la 7aga le ftra kda wna hsbt 3leha l7d mshof hydos new Game wla exit Game
		}catch(Exception ex){}
	}
	public void LoadPic(){
		
		try {


		//	for(Objects objects: Objects.values()) // for each loop to all String values in Enum Objects
		//	sprite[objects.getIndex()] =new Sprite(0,0,objects.gePath(),objects.getIndex()); 
			//for first loop it will be like that
			// sprite[0] = new sprite(0,0,"Background1.png",0);
			// initialized parameter position to 0 , 0 , 0
			
			
			// create buffered image as normal but it with the best resolution with the method in screen for your device
			newGamaImg = s.createCompatibleImage(s.getWidth(),s.getHeight(), BufferedImage.TYPE_INT_ARGB);
			newGamaImg = ImageIO.read(new File("Images/newGameButton.png"));
			exitGameImg = s.createCompatibleImage(s.getWidth(),s.getHeight(), BufferedImage.TYPE_INT_ARGB);
			exitGameImg = ImageIO.read(new File("Images/exitGameButton.png"));
		} catch (IOException e) {}

	}
	
	public void loadShapes(){
		/*
		 * lma get 3rft Enumerator el esmo Objects 3rfto 3shan lma tege t3ml el sprite t3ml add le kol
		 * el mwgod fel Objects fa mhma kan shkl el objects el htzwda fe Enum el Objects htzed lw7dha fel array
		 * w bkda class el View hya5od kol el mwgod fe sprite array w hyrsmo hyla2e ashkal gdeda hyrsma 3ady
		 * y3ne lw 3ayz tzwd shkl gded mn el ashkal el bto23 ht7ot sora fel folder w tktb esmha fel Enum el Objects
		 */
		// Loading pictures to Sprite Class
		fly = FlyWeight.getInstance();
		sprite = fly.getSprite().clone();
	}

	
	
	public void start(){
		//if program reached here that mean he pressed in ( New Game ) then i don't need mouse listener anymore because it will start a game to play
		//so i removed it 
		this.removeMouseListener(this);	
		
		/*
		 * View implements Runnable w dh m3nah eno Thread Class y3ne feh method Run el shbh el main
		 * ay class byt3mlo implements le Runnable dh threading class hysht3'l fe nfs el w2t el el class dh sh3'al
		 * el class kda sh3'alen m3 b3d
		 * bytktb kda Thread = new Thread(Object_Name_From_Class_Implements_Runnable);
		 * class name dh bt7ot feh esm el object mn el class el m3molo implements Runnable
		 * w hena hykon 
		 * Thread t =  new Thread(line);  // line dh esm el object mn class View
		 * bs henamsh m7tag aktb Thread t kda ana 5let m3aya object mno 3al fady
		 * fa hst5dmo 3ltol b (new Thread(line).start();)
		 * lma 2oltlo .start() hwwa kda hyro7 ynfz el method el esmaha run fel class el esmo View(el hwa by implement Runnable)
		 * el run dy shbh el method el main belzbt fel classat el 3dya
		 */


		new Thread(view).start();
	}
	
   
	public void init(){
		//initialize Bounds of buttonds to use it in Mouse Pressed
		newGame = new JButton();
	   	exitGame = new JButton();
		Restart = new JButton();
	   	Tryagain = new JButton();
     	newGame.setBounds(WIDTH*5/12, HEIGHT*370/1080, WIDTH*5/32, HEIGHT/9); //800 , 370 , 300 , 120
		exitGame.setBounds(WIDTH*5/12, HEIGHT*13/27, WIDTH*5/32, HEIGHT/9);	//800,520,300,120	
     	Restart.setBounds(WIDTH*5/12, HEIGHT*17/108, WIDTH*5/32, HEIGHT/9); //800,170,300,120
		Tryagain.setBounds(WIDTH*5/12, HEIGHT*2/3, WIDTH*5/32, HEIGHT/9);	//800,720,300,120	
	}
	
	
	private void EndGame(){ 
		// screen . restoreScreen dy method fe class el screen bt2fl el shasha w btsweha b null fa bt2fl
		// lw mknsh feh Threading kant el shasha 2flt 3ady
		s.restoreScreen();  
		//enma feh Threading class y3ne el shasha htfdl m2fola bs el brnamg sh3'al 
		// so i must use this method to exit of a program 
		System.exit(0);
	}
	
	
	
	/*
	 * this method for Scaling Image to width and height that you want to use bta5od icon imaage w  trg3lk icon image
	 * bs ana msh hst5dmha 3shan method el draw lw7dha momken t3ml scaling image lw d5lt el new width w new height
	 * bta3t el sora fa ana msh hst5dm method el Scaling Image dy fel program bs hseba 3shan lw est5dmtha b3d kda
	 */
	
	// for resizing image
	public static ImageIcon ScalingImage(Icon icon,int width,int height){
		 Image img = ((ImageIcon) icon).getImage() ;  
		   Image newimg = img.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH ) ;  
		   icon = new ImageIcon( newimg );
		   return (ImageIcon) icon;
	}
	
	
	
	// lazm t3ml implement le MouseListener w tkon 3aml lel JFrameObject.addMouseListener(JFrameObject) 3shan ts5dm el method dy
	@Override
	public void mousePressed(MouseEvent e) {
		// point dy bta5od mkan el 7ta el htdos feha
	    point = e.getPoint();
	   //lw heya nfs el 7ta el mwgoda fe mkan el zorar el etrsm ht5osh hena
	    //el gwa dh msh hytfhm hena hyfthm mn View Class
		if(newGame.getBounds().contains(point)){
			if(!checkEscape)
			start();
			else{
				if(view.lost){
					checkRestart=true;
					checkNewGame = true;}
				checkEscape=false;
				}
		}
		if(exitGame.getBounds().contains(point)){
			EndGame();
		}
		if(Restart.getBounds().contains(point)){
			checkEscape = false;
			checkRestart = true;

		}

	}
	
	@Override
	public void mouseClicked(MouseEvent e){}
	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	
	
	//all static to use it in View Class without declare new Object from GameEngine
	public static Boolean CheckLeft1 = false;
	public static Boolean CheckRight1 = false;
	public static Boolean CheckLeft2 = false;
	public static Boolean CheckRight2 = false;
	public static Boolean checkEscape = false;
	public static Boolean checkRestart = false;
	public static Boolean checkNewGame = false;

	
	
	//if i pressed in a button first press
    @Override
    public synchronized void keyPressed(KeyEvent e) {
    	int keyChar = e.getKeyCode();
    	
     		if(keyChar == KeyEvent.VK_LEFT ){ //if keyChar == left
     			CheckLeft1 = true;
     			
     		}
     		else if(keyChar == KeyEvent.VK_RIGHT ){  //if keyChar == Right
     			CheckRight1 = true;
     		}
     		if(keyChar == KeyEvent.VK_A ){  //if keyChar == A
     			CheckLeft2 = true;
     		}
     		else if(keyChar == KeyEvent.VK_D ){  //if keyChar == D
     			CheckRight2 = true;
     		}
     		if(keyChar == KeyEvent.VK_ESCAPE){  //if keyChar == Escape
     			checkEscape = true;
     		}
     		if(keyChar == KeyEvent.VK_F1){
     			BufferedImage image;
     			SnapShot ScreenShot = new SnapShot();
     			image = ScreenShot.screenShot(0, 0, WIDTH, HEIGHT);
     			ScreenShot.save(image);
     			
     			
     		}
     		
     		
        }
        	
    
    // if i release finger from a button 
    @Override
    public  void keyReleased(KeyEvent e) {
    	int keyChar = e.getKeyCode();
    	
 		if(keyChar == KeyEvent.VK_LEFT){
 			CheckLeft1 = false;
 			
 		}
 		else if(keyChar == KeyEvent.VK_RIGHT){
 			CheckRight1 = false;
 		}
 		if(keyChar == KeyEvent.VK_A){
 			CheckLeft2 = false;
 		}
 		else if(keyChar == KeyEvent.VK_D){
 			CheckRight2 = false;
 		}
    }
	
    
    
    /// NOT USEFULL METHOD
	
	@Override
	public void keyTyped(KeyEvent e) {
	
	
	}
	
	
	
}	
