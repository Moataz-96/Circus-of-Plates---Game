package Circus_of_Plates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class View extends JComponent implements Runnable {
	private static View view; // Singleton
	private Strategy Mode;
	private static GameWorld world; // Singleton
	private static int WIDTH,HEIGHT;
	// All Scalling by my Moniter Width and Height
	private static final int ScaleWidth  = 1920; 
	private static final int ScaleHeight = 1080;
	private static int ObjectWidth,ObjectHeight;
	private LinkedList<String> Path;
	/*
	 * lma hkon fe level 1 hst5dm awl 4 lines bs enma fe level 2 hst5dm el 8 lines w level 3 brdo
	 * Lines dy int 3shan w heya btt7rk ymen w shaml kol mra bzwdha w w bn2sha 3la 7sb mknha 
	 * momken mkontsh est5dmthom bs est5dmthom 3shan lma el plates to23 a3rf a check heya w23t w heya fe a5r el line wla l2
	 * fe level 1:
	 * line1X hwa awl line 3al shmal mn fo2 w line2X dh el gmbo
	 * line3X dh el t7t line1X w line4X dh el t7t line2X
	 * fe level2,level3:
	 * line0X dh awl line mn fo2 3al shmal w line00X dh el line el gmbo
	 * line0X t7to line1X t7to line3X t7to line5X
	 * w line00X t7to line2X t7to line4X t7to line55X 
	 *
	 */
	private int Line1X, Line2X, Line3X, Line4X, Line0X, Line00X, Line5X, Line55X;
	private String Score = "0"; // initialize Score with 0 
	//flagBack for line1X lw hwa ray7 ymen yb2a flagBack = false lw heya b false kol el lines el n7yt el ymen htege shmal wel 3ks
	/*
	 * drop dy 3shan lw w2t mn 3al line
	 * level1 w level2 w level 3 kol wa7d fehom b shkl tany 5als bs bbdl mnhom lma tnt2l mn level le level tany
	 * lost dy lw 5srt tp2a b true w s3tha ydek GameOver
	 */
	boolean flagBack = true, drop = true, level1 = true, level2 = false, level3 = false, Running = true, lost = false;
	/*
	 * feh 4 Locations
	 * 1- lw el clown1 msk el object b lefthand 
	 * 2- lw clown1 msko b righthand
	 * 3- lw el clown2 msk el object b lefthand
	 * 4- lw clown2 msko b righthand
	 * fa el locations bt7dd el object (plate,or ball) fe anhy hand dlw2te
	 */
	private LinkedList<String> Location; // using it as a pointer
	
	//this sprite = sprite that initalized in GameEngine Class
	private Sprite[] sprite;
	//flag swap 3shan el clown w hwa byr2os msln kol 1 sec flagswap btzed b wa7d w lw zadt b wa7d hytrsm el clown shkl el tany leh w trg3 le zero w kda bbdl swr w yzhr k2no byr2os
	private int flagSwap = 0;
	//LH1 3shana 3rf 3dd el objects(ball or plates) on the hand
	/*
	 * LH1 for number of objects in LeftHand in clown1
	 * RH1 for number of objects in rightHand in clown1
	 * LH2 for number of objects in LeftHand in clown2
	 * RH2 for number of objects in rightHand in clown2
	 */
	private LinkedList<Integer> LH1, LH2, RH1, RH2;
	//Scores dy 3shan kol 3 yzed el score b 100 w b3d kda yrg3 ykon MinScoreLH1 = 6 ++3 // lw msh fhmha dh Algorithm bt3ha kda
	private int MinScoreLH1 = 3, MinScoreRH1 = 3, MinScoreLH2 = 3, MinScoreRH2 = 3;
	//nfs el LinkedList<Integer>LH1,LH2,RH1,RH2 bs kan lazm t3ml 2 kda mynf3sh a3tmd 3la w7da fehom bs 3shan fe b3d el 7gat mbyb2osh zy b3d
	private int lefthandCounter1 = 0, righthandCounter1 = 0, lefthandCounter2 = 0, righthandCounter2 = 0;
	/*
	 * 
	 * linkedList<GameObject> 3shan kol mra azwd new sprite() w yzed sora lel object
	 * HorLine1 for line1X and Line2X
	 * HorLine2 for line3X and Line4X
	 * HorLine3 for Line0X and Line00X
	 * HorLine4 for Line5X and Line55X
	 */
	private LinkedList<GameObject> HorLine1; // Horizontal Line 1
	private LinkedList<GameObject> HorLine2; // Horizontal Line 2
	private LinkedList<GameObject> HorLine3; // Horizontal Line 1
	private LinkedList<GameObject> HorLine4; // Horizontal Line 2
	
	//dy el buffered images el bst5dmha 3ltol fa 3rft variable mnhom
	private BufferedImage background2,backgroundlevel2,backgroundlevel3, clown1, clown2;

	
	//End Declaring Variables
	
	
	
	
	
	//Constructor
	//lma 3mlt View line = new View() hyege 3la el method dy(Constructor)
	private View(Sprite[] sprite,Strategy Mode) {
		this.sprite = sprite.clone();  // Copy of parameter sprites 
		Path  = new LinkedList<String>();
		this.Mode = Mode;
		//initialize all last Variables
		LH1 = new LinkedList<Integer>();
		LH2 = new LinkedList<Integer>();
		RH1 = new LinkedList<Integer>();
		RH2 = new LinkedList<Integer>();
		Location = new LinkedList<String>();
		HorLine1 = new LinkedList<GameObject>();
		HorLine2 = new LinkedList<GameObject>();
		HorLine3 = new LinkedList<GameObject>();
		HorLine4 = new LinkedList<GameObject>();
		world = GameWorld.getInstance();

		try {
			LoadConstansImages(); // initialize el bufferedImage el hst5dmha kter zy (background2,backgroundlevel2,backgroundlevel3,clown1,clown2)
		} catch (IOException e) {
		}
	}
	
	
	// Singleton Design Pattern
	public static View getInstance(Sprite[] sprite,Strategy Mode){
		if(view == null)
			view = new View(sprite,Mode);
		return view;
	}
	
	//Called in GameEngine Class
	public void setScreenWidth(int WIDTH){
		this.WIDTH = WIDTH;
		this.ObjectWidth = WIDTH * 50 / ScaleWidth;

	}
	
	public void setScreenHeigth(int HEIGHT){
		this.HEIGHT = HEIGHT;
		this.ObjectHeight = HEIGHT * 50 / ScaleHeight;
	}
	
	
	//dy el main method lma ktbt fel class GameEngine new Thread(Line).start(); hyege 3la el method dy
	public void run() {
		initialize();  // w hena b7ot el x wel y le kol 7aga bt3mlha initial position

		while (Running) {  //Running = true always // htsawy false w2t el lost or level finished
			moveObjects();	//move each object lw kan byt7rk 3la el Line lsa yb2a set x w lw clown yp2a 3la 7sb el keyListener w lw 3la Line nfso yb2a byt7r ymen w shaml bs
			checkObjects(); //check objects dy lw hwa 3da msafa mo3yna fel line bt7ot new object w lw a5r el line yo23 w lw nfs x w y clown yb2a msko
			repaint(); // repaint dy btrsm bs (btro7 le method paintComponent();)
			try {
				Thread.sleep(30); //sleep for 30 milliSecond b3d kda rsma
			} catch (InterruptedException e) {
			}
			if (GameEngine.checkEscape) { //lw check scape hyro7 y3ml pause
				PauseGame();
				if (!GameEngine.checkRestart) { 
					Running = true;

				}
			}

		}

		//lw lost w das new game yb2a hyro7 le level 1
		if (lost && GameEngine.checkNewGame) {
			level1 = true;
			level2 = false;
			level3 = false;
			lost = false;
			GameEngine.checkNewGame = false;
		}
		//lw wslt l7d el 7ta dy yb2a kda hwa 3ayz try Again aw new Game lw try again hy5osh 3la hena 3ltol lw new game yp2a el awl 5lah level 1 w 3ml false le b2et el levels
		resetGame();

	}

	public void initialize() {

		FlyWeight fly = FlyWeight.getInstance();
		Path = (LinkedList<String>) fly.getPaths().clone();
		if(this.Mode != null){
			
		
		if(this.Mode instanceof Easy){
			level1 = true;
			level2 = false;
			level3 = false;
		}else if(this.Mode instanceof Medium){
			level1 = false;
			level2 = true;
			level3 = false;
		}else if(this.Mode instanceof Hard){
			level1 = false;
			level2 = false;
			level3 = true;
		}}
		
		// init sprite

		sprite[Objects.clown1left.getIndex()].setX(WIDTH *11 / 64); //330
		sprite[Objects.clown1right.getIndex()].setX(WIDTH *11/ 64); //330
		sprite[Objects.clown2left.getIndex()].setX(WIDTH *85/128);  // 1275
		sprite[Objects.clown2right.getIndex()].setX(WIDTH *85/128); // 1275
		sprite[Objects.clown1left.getIndex()].setY(HEIGHT *5/9);	//600
		sprite[Objects.clown1right.getIndex()].setY(HEIGHT *5/9 );  //600
		sprite[Objects.clown2left.getIndex()].setY(HEIGHT *5/9 );   //600
		sprite[Objects.clown2right.getIndex()].setY(HEIGHT *5/9);	//600

		if (level1) {
			Line1X = WIDTH / 3;
			Line2X = 2 * Line1X;
			Line3X = Line1X / 2;
			Line4X = Line3X + Line2X;
		}
		if (level2 || level3) {
			Line0X = 4 * (WIDTH / 10);
			Line00X = WIDTH - Line0X;
			Line1X = 3 * (WIDTH / 9);
			Line2X = WIDTH - Line1X;
			Line3X = 2 * (WIDTH / 9);
			Line4X = WIDTH - Line3X;
			Line5X = (WIDTH / 9);
			Line55X = WIDTH - Line5X;
		}

		world.setStatue("movable");
		world.check(new Sprite(-WIDTH*5/192, HEIGHT*5/36, RandomPath(), 0)); // -50,150
		world.check(new Sprite((int) (WIDTH*0.9976),HEIGHT*5/36, RandomPath(), 0));// 1915.142,150
		world.check(new Sprite(-WIDTH*5/192, HEIGHT*25/108, RandomPath(), 0)); // -50,250
		world.check(new Sprite((int) (WIDTH*0.9976), HEIGHT*25/108, RandomPath(), 0)); //1915.142,250
		if (level2 || level3) {
			world.check(new Sprite(-WIDTH*5/192, HEIGHT*5/108, RandomPath(), 0)); //-50,50
			world.check(new Sprite((int) (WIDTH*0.9976), HEIGHT*5/108, RandomPath(), 0)); //1915.124,50
			world.check(new Sprite(-WIDTH*5/192, HEIGHT*35/108, RandomPath(), 0)); //-50,350
			world.check(new Sprite((int) (WIDTH*0.9976), HEIGHT*35/108, RandomPath(), 0)); //1915.124,350
			HorLine3.add(world.getMovableObjects().get(4));
			HorLine3.add(world.getMovableObjects().get(5));
			HorLine4.add(world.getMovableObjects().get(6));
			HorLine4.add(world.getMovableObjects().get(7));
		}
		HorLine1.add(world.getMovableObjects().get(0));
		HorLine1.add(world.getMovableObjects().get(1));
		HorLine2.add(world.getMovableObjects().get(2));
		HorLine2.add(world.getMovableObjects().get(3));

	}

	public void resetGame() {
		MinScoreLH1 = MinScoreRH1 = MinScoreLH2 = MinScoreRH2 = 3;
		lefthandCounter1 = righthandCounter1 = lefthandCounter2 = righthandCounter2 = 0;
		HorLine1.clear();
		HorLine2.clear();
		HorLine3.clear();
		HorLine4.clear();
		LH1.clear();
		LH2.clear();
		RH1.clear();
		RH2.clear();
		world.getMovableObjects().clear();
		world.getConstantObjects().clear();
		world.getControlableObjects().clear();
		Location.clear();
		Score = "0";
		flagSwap = 0;
		flagBack = true;
		GameEngine.checkRestart = false;
		lost = false;
		Running = true;
		run();

	}

	public void PauseGame() {

	
		new SnapShot().Pause(lost, WIDTH, HEIGHT);
		

		Running = false;

	}

	public void moveObjects() {

		if (flagBack) {
			Line0X -= world.getControlSpeed();
			Line00X += world.getControlSpeed();
			Line1X -= world.getControlSpeed();
			Line2X += world.getControlSpeed();
			Line3X -= world.getControlSpeed();
			Line4X += world.getControlSpeed();
			Line5X -= world.getControlSpeed();
			Line55X += world.getControlSpeed();
		} else {
			Line0X += world.getControlSpeed();
			Line00X -= world.getControlSpeed();
			Line1X += world.getControlSpeed();
			Line2X -= world.getControlSpeed();
			Line3X += world.getControlSpeed();
			Line4X -= world.getControlSpeed();
			Line5X += world.getControlSpeed();
			Line55X -= world.getControlSpeed();
		}

		if (level1) {
			if (Line1X <= (WIDTH / 3) / 2)
				flagBack = false;
			if (Line1X >= WIDTH / 3)
				flagBack = true;
		}
		if (level2 || level3) {
			if (Line5X <= 0)
				flagBack = false;
			if (Line5X >= (WIDTH / 9))
				flagBack = true;
		}

		if (GameEngine.CheckLeft1 && sprite[Objects.clown1left.getIndex()].getX() > WIDTH*10*world.getControlSpeed()/1920) {
			sprite[Objects.clown1left.getIndex()].setX(sprite[Objects.clown1left.getIndex()].getX() - WIDTH*10*world.getControlSpeed()/1920);
			sprite[Objects.clown1right.getIndex()].setX(sprite[Objects.clown1right.getIndex()].getX() - WIDTH*10*world.getControlSpeed()/1920);
		} else if (GameEngine.CheckRight1 && sprite[Objects.clown1left.getIndex()].getX()
				+WIDTH* sprite[Objects.clown1left.getIndex()].getWidth()/ScaleWidth < WIDTH - WIDTH*100/1920) {
			sprite[Objects.clown1left.getIndex()].setX(sprite[Objects.clown1left.getIndex()].getX() + WIDTH*10*world.getControlSpeed()/1920);
			sprite[Objects.clown1right.getIndex()].setX(sprite[Objects.clown1right.getIndex()].getX() + WIDTH*10*world.getControlSpeed()/1920);
		}
		if (GameEngine.CheckLeft2 && sprite[Objects.clown2left.getIndex()].getX() > WIDTH*10*world.getControlSpeed()/1920) {
			sprite[Objects.clown2left.getIndex()].setX(sprite[Objects.clown2left.getIndex()].getX() - WIDTH*10*world.getControlSpeed()/1920);
			sprite[Objects.clown2right.getIndex()].setX(sprite[Objects.clown2right.getIndex()].getX() - WIDTH*10*world.getControlSpeed()/1920);
		} else if (GameEngine.CheckRight2 && sprite[Objects.clown2left.getIndex()].getX()
				+ WIDTH*sprite[Objects.clown2left.getIndex()].getWidth()/ScaleWidth < WIDTH - WIDTH*100/1920) {
			sprite[Objects.clown2left.getIndex()].setX(sprite[Objects.clown2left.getIndex()].getX() + WIDTH*10*world.getControlSpeed()/1920);
			sprite[Objects.clown2right.getIndex()].setX(sprite[Objects.clown2right.getIndex()].getX() + WIDTH*10*world.getControlSpeed()/1920);
		}

	}

	public void checkObjects() {

		world.getMovableObjects().clear();

		if (HorLine1.get(HorLine1.size() - 2).getX() > 20) {
			world.setStatue("movable");
			world.check(new Sprite(-WIDTH*5/192, HEIGHT*5/36, RandomPath(), 0)); // -50,150
			world.check(new Sprite((int) (WIDTH*0.9976),HEIGHT*5/36, RandomPath(), 0));// 1915.142,150
			world.check(new Sprite(-WIDTH*5/192, HEIGHT*25/108, RandomPath(), 0)); // -50,250
			world.check(new Sprite((int) (WIDTH*0.9976), HEIGHT*25/108, RandomPath(), 0)); //1915.142,250

			HorLine1.add(world.getMovableObjects().get(0));
			HorLine1.add(world.getMovableObjects().get(1));
			HorLine2.add(world.getMovableObjects().get(2));
			HorLine2.add(world.getMovableObjects().get(3));

			if (level2 || level3) {
				world.check(new Sprite(-WIDTH*5/192, HEIGHT*5/108, RandomPath(), 0)); //-50,50
				world.check(new Sprite((int) (WIDTH*0.9976), HEIGHT*5/108, RandomPath(), 0)); //1915.124,50
				world.check(new Sprite(-WIDTH*5/192, HEIGHT*35/108, RandomPath(), 0)); //-50,350
				world.check(new Sprite((int) (WIDTH*0.9976), HEIGHT*35/108, RandomPath(), 0)); //1915.124,350
				
				HorLine3.add(world.getMovableObjects().get(4));
				HorLine3.add(world.getMovableObjects().get(5));
				HorLine4.add(world.getMovableObjects().get(6));
				HorLine4.add(world.getMovableObjects().get(7));
			}
		}

		try {
			if (HorLine1.get(0).getX() > Line1X && HorLine1.get(0).getY() == HEIGHT*5/36) {

				world.setStatue("controlable");

				for(int i =0 ; i < sprite.length ; i++){
					if (bufferedImagesEqual(sprite[i].getSpriteImages(),
							HorLine1.get(0).getSpriteImages())) {

						world.check(new Sprite(HorLine1.get(0).getX(), HorLine1.get(0).getY(),Path.get(i), 0));
						break;
					}
				}

				for(int i =0 ; i < sprite.length ; i++){
					if (bufferedImagesEqual(sprite[i].getSpriteImages(),
							HorLine1.get(1).getSpriteImages())) {
						world.check(new Sprite(HorLine1.get(1).getX(), HorLine1.get(1).getY(), Path.get(i), 0));
						break;
					}
				}
				HorLine1.removeFirst();
				HorLine1.removeFirst();

			}
		} catch (Exception e) {
		}
		
		try {
			if (HorLine2.get(0).getX() > Line3X && HorLine2.get(0).getY() == HEIGHT*25/108) {
				world.setStatue("controlable");

				for(int i =0 ; i < sprite.length ; i++){
					if (bufferedImagesEqual(sprite[i].getSpriteImages(),
							HorLine2.get(0).getSpriteImages())) {
						world.check(new Sprite(HorLine2.get(0).getX(), HorLine2.get(0).getY(), Path.get(i), 0));
						break;
					}
				}

				for(int i =0 ; i < sprite.length ; i++){
					if (bufferedImagesEqual(sprite[i].getSpriteImages(),
							HorLine2.get(1).getSpriteImages())) {
						world.check(new Sprite(HorLine2.get(1).getX(), HorLine2.get(1).getY(), Path.get(i), 0));
						break;
					}
				}
				HorLine2.removeFirst();
				HorLine2.removeFirst();

			}
		} catch (Exception e) {
		}

		if (level2 || level3) {

			try {
				if (HorLine3.get(0).getX() > Line0X && HorLine3.get(0).getY() == HEIGHT*5/108) {

					world.setStatue("controlable");

					for(int i =0 ; i < sprite.length ; i++){
						if (bufferedImagesEqual(sprite[i].getSpriteImages(),
								HorLine3.get(0).getSpriteImages())) {
							world.check(
									new Sprite(HorLine3.get(0).getX(), HorLine3.get(0).getY(), Path.get(i), 0));
							break;
						}
					}

					for(int i =0 ; i < sprite.length ; i++){
						if (bufferedImagesEqual(sprite[i].getSpriteImages(),
								HorLine3.get(1).getSpriteImages())) {
							world.check(
									new Sprite(HorLine3.get(1).getX(), HorLine3.get(1).getY(),Path.get(i), 0));
							break;
						}
					}
					HorLine3.removeFirst();
					HorLine3.removeFirst();

				}
			} catch (Exception e) {
			}

			try {
				if (HorLine4.get(0).getX() > Line5X && HorLine4.get(0).getY() == HEIGHT*35/108) {

					world.setStatue("controlable");

					for(int i =0 ; i < sprite.length ; i++){
						if (bufferedImagesEqual(sprite[i].getSpriteImages(),
								HorLine4.get(0).getSpriteImages())) {
							world.check(
									new Sprite(HorLine4.get(0).getX(), HorLine4.get(0).getY(), Path.get(i), 0));
							break;
						}
					}

					for(int i =0 ; i < sprite.length ; i++){
						if (bufferedImagesEqual(sprite[i].getSpriteImages(),
								HorLine4.get(1).getSpriteImages())) {
							world.check(
									new Sprite(HorLine4.get(1).getX(), HorLine4.get(1).getY(),Path.get(i), 0));
							break;
						}
					}
					HorLine4.removeFirst();
					HorLine4.removeFirst();

				}
			} catch (Exception e) {
			}

		}

		world.getMovableObjects().clear();

		for (int i = 0; i < HorLine1.size(); i++) {
			world.getMovableObjects().add(HorLine1.get(i));
		}

		for (int i = 0; i < world.getMovableObjects().size(); i += 2) {
			world.getMovableObjects().get(i).setX(world.getMovableObjects().get(i).getX() + WIDTH*world.getSpeed()/ScaleWidth);
			world.getMovableObjects().get(i + 1).setX(world.getMovableObjects().get(i + 1).getX() - WIDTH*world.getSpeed()/ScaleWidth);
		}

		for (int i = 0; i < HorLine2.size(); i++) {
			world.getMovableObjects().add(HorLine2.get(i));
		}

		for (int i = HorLine1.size(); i < world.getMovableObjects().size(); i += 2) {
			world.getMovableObjects().get(i).setX(world.getMovableObjects().get(i).getX() + WIDTH*world.getSpeed()/ScaleWidth);
			world.getMovableObjects().get(i + 1).setX(world.getMovableObjects().get(i + 1).getX() - WIDTH*world.getSpeed()/ScaleWidth);
		}

		if (level2 || level3) {
			for (int i = 0; i < HorLine3.size(); i++) {
				world.getMovableObjects().add(HorLine3.get(i));
			}

			for (int i = HorLine2.size() + HorLine1.size(); i < world.getMovableObjects().size(); i += 2) {
				world.getMovableObjects().get(i).setX(world.getMovableObjects().get(i).getX() + WIDTH*world.getSpeed()/ScaleWidth);
				world.getMovableObjects().get(i + 1).setX(world.getMovableObjects().get(i + 1).getX() - WIDTH*world.getSpeed()/ScaleWidth);
			}

			for (int i = 0; i < HorLine4.size(); i++) {
				world.getMovableObjects().add(HorLine4.get(i));
			}

			for (int i = HorLine3.size() + HorLine2.size() + HorLine1.size(); i < world.getMovableObjects()
					.size(); i += 2) {
				world.getMovableObjects().get(i).setX(world.getMovableObjects().get(i).getX() + WIDTH*world.getSpeed()/ScaleWidth);
				world.getMovableObjects().get(i + 1).setX(world.getMovableObjects().get(i + 1).getX() - WIDTH*world.getSpeed()/ScaleWidth);
			}

		}

		for (int i = 0; i < world.getControlableObjects().size(); i++) {
			world.getControlableObjects().get(i).setY(world.getControlableObjects().get(i).getY() + HEIGHT*world.getSpeed()/ScaleHeight*3/2);
			lefthandCounter1 = 0;
			righthandCounter1 = 0;
			lefthandCounter2 = 0;
			righthandCounter2 = 0;

			for (int j = 0; j < world.getConstantObjects().size(); j++) {
				if (Location.get(j).equalsIgnoreCase("leftHand1"))
					lefthandCounter1 += HEIGHT*50/ScaleHeight;
				else if (Location.get(j).equalsIgnoreCase("rightHand1"))
					righthandCounter1 += HEIGHT*50/ScaleHeight;
				else if (Location.get(j).equalsIgnoreCase("leftHand2"))
					lefthandCounter2 += HEIGHT*50/ScaleHeight;
				else if (Location.get(j).equalsIgnoreCase("rightHand2"))
					righthandCounter2 += HEIGHT*50/ScaleHeight;
			}

			if (lefthandCounter1 + HEIGHT*5/9 > HEIGHT*5/6 && righthandCounter1 + HEIGHT*5/9 > HEIGHT*5/6 && lefthandCounter2 + HEIGHT*5/9 > HEIGHT*5/6
					&& righthandCounter2 + HEIGHT*5/9 > HEIGHT*5/6 && !lost) {
				if(Integer.parseInt(Score) >= 100){
					try{
					if(level1||level2){
					BufferedImage back = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
					back = ImageIO.read(new File("Images/winner.png"));
					Graphics g = GameEngine.s.getFullScreenWindow().getGraphics();
					g.drawImage(back, 0, 0, WIDTH, HEIGHT, null);
					g.dispose();
					Thread.sleep(3000);}
					
					if(level1){
						level1=false;
						level2= true;
					}
					else if(level2){
						level2 = false;
						level3 = true;
					}
					else if(level3){

						BufferedImage back = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
						back = ImageIO.read(new File("Images/winGame.png"));
						Graphics g = GameEngine.s.getFullScreenWindow().getGraphics();
						g.drawImage(back, 0, 0, WIDTH, HEIGHT, null);
						g.dispose();
							Thread.sleep(2000);
							GameEngine.checkEscape = true;
							lost=true;
						PauseGame();
						
						return;
					}
					
					resetGame();
					}catch(Exception e){}
					
				}
				else{
					try{
					BufferedImage back = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
					back = ImageIO.read(new File("Images/GameOver.png"));
					Graphics g = GameEngine.s.getFullScreenWindow().getGraphics();
					g.drawImage(back, 0, 0, WIDTH, HEIGHT, null);
					g.dispose();
					Thread.sleep(3000);
					}catch(Exception z){}
					
				lost = true;
				GameEngine.checkEscape=true;
				}
			}

			getConstant(i, 30);
		}
		setScore();

		for (int i = 0; i < world.getConstantObjects().size(); i++) {
			if (Location.get(i).equalsIgnoreCase("rightHand1"))
				world.getConstantObjects().get(i)
						.setX(sprite[2].getX() +WIDTH* sprite[2].getWidth()/ScaleWidth + WIDTH*sprite[2].getWidth()/ScaleWidth / 8);
			else if (Location.get(i).equalsIgnoreCase("leftHand1"))
				world.getConstantObjects().get(i).setX(sprite[2].getX());
			else if (Location.get(i).equalsIgnoreCase("rightHand2"))
				world.getConstantObjects().get(i)
						.setX(sprite[4].getX() +WIDTH* sprite[4].getWidth()/ScaleWidth + WIDTH*sprite[4].getWidth()/ScaleWidth / 8);
			else if (Location.get(i).equalsIgnoreCase("leftHand2"))
				world.getConstantObjects().get(i).setX(sprite[4].getX());
		}

		for (int i = 0; i < world.getControlableObjects().size(); i++) {
			if (world.getControlableObjects().get(i).getY() > HEIGHT) {
				world.getControlableObjects().remove(i);
				return;
			}
		}

		// System.out.println("x = " + sprite[2].getX() + " Y = " +
		// sprite[2].getY() + " Height = " + sprite[2].getHeight() + " Width = "
		// + sprite[2].getWidth());
	}

	public void explosion(int clown) {
		try {
			BufferedImage exp = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			exp = ImageIO.read(new File("Images/fire.png"));
			BufferedImage clown1Fired = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			clown1Fired = ImageIO.read(new File("Images/clown1Fired.png"));
			BufferedImage clown2Fired = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			clown2Fired = ImageIO.read(new File("Images/clown2Fired.png"));

			Graphics g = GameEngine.s.getFullScreenWindow().getGraphics();
			if (clown == 1) {
				g.drawImage(clown1Fired, sprite[Objects.clown1left.getIndex()].getX(),
						sprite[Objects.clown1left.getIndex()].getY(), WIDTH*5/48,HEIGHT*10/27, null);
				g.drawImage(exp, sprite[Objects.clown1left.getIndex()].getX() + WIDTH*sprite[12].getWidth()/ScaleWidth,
						sprite[Objects.clown1left.getIndex()].getY() + sprite[12].getHeight(),WIDTH*5/48, HEIGHT*5/27, null);
			}
			if (clown == 2) {
				g.drawImage(clown2Fired, sprite[Objects.clown2left.getIndex()].getX(),
						sprite[Objects.clown2left.getIndex()].getY(), WIDTH*5/48,HEIGHT*10/27, null);
				g.drawImage(exp, sprite[Objects.clown2left.getIndex()].getX() + WIDTH*sprite[12].getWidth()/ScaleWidth,
						sprite[Objects.clown2left.getIndex()].getY() + sprite[12].getHeight(),WIDTH*5/48, HEIGHT*5/27, null);

			}
			g.dispose();
			Thread.sleep(2000);
			
			BufferedImage z = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			z = ImageIO.read(new File("Images/GameOver.png"));
			Graphics o = GameEngine.s.getFullScreenWindow().getGraphics();
			o.drawImage(z, 0, 0, WIDTH, HEIGHT, null);
			o.dispose();
			Thread.sleep(2000);
			// resetGame();
			Running = false;
			lost = true;
			GameEngine.checkEscape = true;
			
		} catch (Exception e) {
		}
	}

	public void setScore() {
		// lefthand1 check
		int x;

		if (LH1.size() >= MinScoreLH1)
			if (bufferedImagesEqual(world.getConstantObjects().get(LH1.get(LH1.size() - 1)).getSpriteImages(),
					world.getConstantObjects().get(LH1.get(LH1.size() - 2)).getSpriteImages())
					&& (bufferedImagesEqual(world.getConstantObjects().get(LH1.get(LH1.size() - 2)).getSpriteImages(),
							world.getConstantObjects().get(LH1.get(LH1.size() - 3)).getSpriteImages()))) {
				x = Integer.parseInt(Score);
				x += 100;
				Score = String.valueOf(x);
				MinScoreLH1 += LH1.size();

			}

		if (RH1.size() >= MinScoreRH1)
			if (bufferedImagesEqual(world.getConstantObjects().get(RH1.get(RH1.size() - 1)).getSpriteImages(),
					world.getConstantObjects().get(RH1.get(RH1.size() - 2)).getSpriteImages())
					&& (bufferedImagesEqual(world.getConstantObjects().get(RH1.get(RH1.size() - 2)).getSpriteImages(),
							world.getConstantObjects().get(RH1.get(RH1.size() - 3)).getSpriteImages()))) {
				x = Integer.parseInt(Score);
				x += 100;
				Score = String.valueOf(x);
				MinScoreRH1 += RH1.size();
			}
		if (LH2.size() >= MinScoreLH2)
			if (bufferedImagesEqual(world.getConstantObjects().get(LH2.get(LH2.size() - 1)).getSpriteImages(),
					world.getConstantObjects().get(LH2.get(LH2.size() - 2)).getSpriteImages())
					&& (bufferedImagesEqual(world.getConstantObjects().get(LH2.get(LH2.size() - 2)).getSpriteImages(),
							world.getConstantObjects().get(LH2.get(LH2.size() - 3)).getSpriteImages()))) {
				x = Integer.parseInt(Score);
				x += 100;
				Score = String.valueOf(x);
				MinScoreLH2 += LH2.size();
			}
		if (RH2.size() >= MinScoreRH2)
			if (bufferedImagesEqual(world.getConstantObjects().get(RH2.get(RH2.size() - 1)).getSpriteImages(),
					world.getConstantObjects().get(RH2.get(RH2.size() - 2)).getSpriteImages())
					&& (bufferedImagesEqual(world.getConstantObjects().get(RH2.get(RH2.size() - 2)).getSpriteImages(),
							world.getConstantObjects().get(RH2.get(RH2.size() - 3)).getSpriteImages()))) {
				x = Integer.parseInt(Score);
				x += 100;
				Score = String.valueOf(x);
				MinScoreRH2 += RH2.size();
			}

	}

	public void getConstant(int i, int ErrorRate) {

		if ((world.getControlableObjects().get(i).getY()
				+ HEIGHT*50/ScaleHeight <= sprite[2].getY() - righthandCounter1 + 5
				&& world.getControlableObjects().get(i).getX() <= sprite[2].getX() + WIDTH*sprite[2].getWidth()/ScaleWidth+ ErrorRate
				&& world.getControlableObjects().get(i).getY()
						+ HEIGHT*50/ScaleHeight >= sprite[2].getY() - righthandCounter1 - 5
				&& world.getControlableObjects().get(i).getX() >= sprite[2].getX() + WIDTH*sprite[2].getWidth()/ScaleWidth - ErrorRate)
				|| (world.getControlableObjects().get(i).getY()
						+ HEIGHT*50/ScaleHeight <= sprite[3].getY() - righthandCounter1 + 5
						&& world.getControlableObjects().get(i).getX() <= sprite[3].getX() + WIDTH*sprite[3].getWidth()/ScaleWidth
								+ ErrorRate
						&& world.getControlableObjects().get(i).getY()
								+ HEIGHT*50/ScaleHeight >= sprite[3].getY()
										- righthandCounter1 - 5
						&& world.getControlableObjects().get(i).getX() >= sprite[3].getX() + WIDTH*sprite[3].getWidth()/ScaleWidth
								- ErrorRate)) {
			world.setStatue("constant");
			Location.add("rightHand1");
			world.getConstantObjects().add(world.getControlableObjects().get(i));
			if(level3)
			if (bufferedImagesEqual(
					world.getConstantObjects().get(world.getConstantObjects().size() - 1).getSpriteImages(),
					sprite[Objects.bomb.getIndex()].getSpriteImages())) {
				explosion(1);
			}
			world.getControlableObjects().remove(i);
			RH1.add(world.getConstantObjects().size() - 1);
		}

		else if ((world.getControlableObjects().get(i).getY()
				+ HEIGHT*50./ScaleHeight <= sprite[2].getY() - lefthandCounter1 + 5
				&& world.getControlableObjects().get(i).getX() <= sprite[2].getX() + ErrorRate
				&& world.getControlableObjects().get(i).getY()
						+ HEIGHT*50/ScaleHeight >= sprite[2].getY() - lefthandCounter1 - 5
				&& world.getControlableObjects().get(i).getX() >= sprite[2].getX() - ErrorRate)
				|| (world.getControlableObjects().get(i).getY()
						+ HEIGHT*50/ScaleHeight <= sprite[3].getY() - lefthandCounter1 + 5
						&& world.getControlableObjects().get(i).getX() <= sprite[3].getX() + ErrorRate
						&& world.getControlableObjects().get(i).getY()
								+ HEIGHT *50/ScaleHeight >= sprite[3].getY()
										- lefthandCounter1 - 5
						&& world.getControlableObjects().get(i).getX() >= sprite[3].getX() - ErrorRate)) {
			world.setStatue("constant");
			Location.add("leftHand1");
			world.getConstantObjects().add(world.getControlableObjects().get(i));
			if(level3)
			if (bufferedImagesEqual(
					world.getConstantObjects().get(world.getConstantObjects().size() - 1).getSpriteImages(),
					sprite[Objects.bomb.getIndex()].getSpriteImages())) {
				explosion(1);
			}
			world.getControlableObjects().remove(i);
			LH1.add(world.getConstantObjects().size() - 1);

		}

		else if ((world.getControlableObjects().get(i).getY()
				+ HEIGHT*50/ScaleHeight <= sprite[4].getY() - righthandCounter2 + 5
				&& world.getControlableObjects().get(i).getX() <= sprite[4].getX() + WIDTH*sprite[4].getWidth()/ScaleWidth + ErrorRate
				&& world.getControlableObjects().get(i).getY()
						+ HEIGHT*50/ScaleHeight >= sprite[4].getY() - righthandCounter2 - 5
				&& world.getControlableObjects().get(i).getX() >= sprite[4].getX() + WIDTH*sprite[4].getWidth()/ScaleWidth - ErrorRate)
				|| (world.getControlableObjects().get(i).getY()
						+ HEIGHT*50/ScaleHeight <= sprite[5].getY() - righthandCounter2 + 5
						&& world.getControlableObjects().get(i).getX() <= sprite[5].getX() + WIDTH*sprite[5].getWidth()/ScaleWidth
								+ ErrorRate
						&& world.getControlableObjects().get(i).getY()
								+ HEIGHT*50/ScaleHeight >= sprite[5].getY()
										- righthandCounter2 - 5
						&& world.getControlableObjects().get(i).getX() >= sprite[5].getX() + WIDTH*sprite[5].getWidth()/ScaleWidth
								- ErrorRate)) {
			world.setStatue("constant");
			Location.add("rightHand2");
			world.getConstantObjects().add(world.getControlableObjects().get(i));
			if(level3)
			if (bufferedImagesEqual(
					world.getConstantObjects().get(world.getConstantObjects().size() - 1).getSpriteImages(),
					sprite[Objects.bomb.getIndex()].getSpriteImages())) {
				explosion(2);
			}
			world.getControlableObjects().remove(i);
			RH2.add(world.getConstantObjects().size() - 1);

		} else if ((world.getControlableObjects().get(i).getY()
				+ HEIGHT*50/ScaleHeight <= sprite[4].getY() - lefthandCounter2 + 5
				&& world.getControlableObjects().get(i).getX() <= sprite[4].getX() + ErrorRate
				&& world.getControlableObjects().get(i).getY()
						+ HEIGHT*50/ScaleHeight >= sprite[4].getY() - lefthandCounter2 - 5
				&& world.getControlableObjects().get(i).getX() >= sprite[4].getX() - ErrorRate)
				|| (world.getControlableObjects().get(i).getY()
						+ HEIGHT*50/ScaleHeight <= sprite[5].getY() - lefthandCounter2 + 5
						&& world.getControlableObjects().get(i).getX() <= sprite[5].getX() + ErrorRate
						&& world.getControlableObjects().get(i).getY()
								+ HEIGHT*50/ScaleHeight >= sprite[5].getY()
										- lefthandCounter2 - 5
						&& world.getControlableObjects().get(i).getX() >= sprite[5].getX() - ErrorRate)) {
			world.setStatue("constant");
			Location.add("leftHand2");
			world.getConstantObjects().add(world.getControlableObjects().get(i));
			if(level3)
			if (bufferedImagesEqual(
					world.getConstantObjects().get(world.getConstantObjects().size() - 1).getSpriteImages(),
					sprite[Objects.bomb.getIndex()].getSpriteImages())) {
				explosion(2);
			}
			world.getControlableObjects().remove(i);
			LH2.add(world.getConstantObjects().size() - 1);
		}
	}

	static boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
		if (WIDTH*img1.getWidth()/ScaleWidth == WIDTH*img2.getWidth()/ScaleWidth && HEIGHT*img1.getHeight()/ScaleHeight == HEIGHT*img2.getHeight()/ScaleHeight) {
			for (int x = 0; x < WIDTH*img1.getWidth()/ScaleWidth; x++) {
				for (int y = 0; y < HEIGHT*img1.getHeight()/ScaleHeight; y++) {
					if (img1.getRGB(x, y) != img2.getRGB(x, y))
						return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/*
	 * RandomPath to get Random of paths of allowed Icons in my Folder so you
	 * can easily add new icon to folder and it will be automatic added to new
	 * Shapes of icon math.random() returns only double from ( 0.0 to 1.0 ) so i
	 * multiplied it to 10 and validated it from 6 to 11 only because that
	 * number of allowed index of icons i got in "Objects.java" Enumerator so it
	 * will be randomly get one one of it's index of its paths by
	 */
	public String RandomPath() {
	//	System.out.println("sprite length = " + sprite.length);
	//	if(Objects.bomb.getIndex() != sprite.length-1){
	//	Sprite temp = sprite[Objects.bomb.getIndex()];
	//	sprite[Objects.bomb.getIndex()] = sprite[sprite.length-1];
	//	sprite[sprite.length-1] = temp;}
		
		int MaxPathsIcon = sprite.length - 1;

		int x;
		do {
			x = (int) (10 * Math.random());
			x += sprite.length-10;
			if(!level3 && x == Objects.bomb.getIndex())
				x = MaxPathsIcon;
			
		} while (x < 6 || x > MaxPathsIcon);
		for (int i =0 ; i < sprite.length ; i ++) {
			if (i == x) {
				return Path.get(i);
			}
		}
		return null;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(level1)
		g.drawImage(background2, 0, 0, WIDTH, HEIGHT, null);
		else if(level2)
		g.drawImage(backgroundlevel2, 0, 0, WIDTH, HEIGHT, null);
		else if(level3)
		g.drawImage(backgroundlevel3, 0, 0, WIDTH, HEIGHT, null);
		if (flagSwap < 16) { // 26
			g.drawImage(sprite[Objects.clown1left.getIndex()].getSpriteImages(),
					sprite[Objects.clown1left.getIndex()].getX(), sprite[Objects.clown1left.getIndex()].getY(),WIDTH*5/48,
					HEIGHT*10/27, null);

			g.drawImage(sprite[Objects.clown2right.getIndex()].getSpriteImages(),
					sprite[Objects.clown2right.getIndex()].getX(), sprite[Objects.clown2right.getIndex()].getY(),WIDTH*5/48,
					HEIGHT*10/27, null);

			flagSwap++;
		} else {
			g.drawImage(sprite[Objects.clown2left.getIndex()].getSpriteImages(),
					sprite[Objects.clown2left.getIndex()].getX(), sprite[Objects.clown2left.getIndex()].getY(), WIDTH*5/48,
					HEIGHT*10/27, null);

			g.drawImage(sprite[Objects.clown1right.getIndex()].getSpriteImages(),
					sprite[Objects.clown1right.getIndex()].getX(), sprite[Objects.clown1right.getIndex()].getY(), WIDTH*5/48,
					HEIGHT*10/27, null);

			flagSwap++;
			if (flagSwap > 30) // 50
				flagSwap = 0;
		}

		g.setColor(Color.gray);
		Font f = new Font("tahoma", Font.BOLD, HEIGHT*5/108);
		g.setFont(f);
		g.drawString("score", WIDTH*29/64,HEIGHT*5/108);
		f = new Font("tahoma", Font.BOLD, HEIGHT/36);
		g.setFont(f);
		g.drawString(Score, WIDTH/2 - WIDTH*30/ScaleWidth,HEIGHT*10/108);

		if (level2 || level3) {

			g.setColor(Color.red);

			g.drawLine(0,HEIGHT*5/54, Line0X,HEIGHT*5/54);
			g.drawLine(0,HEIGHT*5/54+1, Line0X, HEIGHT*5/54+1);
			g.drawLine(0,HEIGHT*5/54+2, Line0X,HEIGHT*5/54+2);
			g.drawLine(Line0X + 1, HEIGHT*5/54, Line0X + 11, 120);
			g.drawLine(Line0X + 1, HEIGHT*5/54+1, Line0X + 11, 121);

			g.drawLine(Line00X, HEIGHT*5/54, WIDTH,HEIGHT*5/54);
			g.drawLine(Line00X, HEIGHT*5/54+1, WIDTH,HEIGHT*5/54+1);
			g.drawLine(Line00X, HEIGHT*5/54+2, WIDTH,HEIGHT*5/54+2);
			g.drawLine(Line00X - 11, HEIGHT/9, Line00X - 1, HEIGHT*5/54);
			g.drawLine(Line00X - 11, HEIGHT/9+1, Line00X - 1, HEIGHT*5/54+1);

			g.setColor(Color.green);

			g.drawLine(0, HEIGHT*10/27, Line5X,HEIGHT*10/27);
			g.drawLine(0, HEIGHT*10/27+1, Line5X, HEIGHT*10/27+1);
			g.drawLine(0, HEIGHT*10/27+2, Line5X, HEIGHT*10/27+2);
			g.drawLine(Line5X + 1, HEIGHT*10/27, Line5X + 11, HEIGHT*7/18);
			g.drawLine(Line5X + 1, HEIGHT*10/27+1, Line5X + 11,HEIGHT*7/18+1);

			g.drawLine(Line55X, HEIGHT*10/27, WIDTH, HEIGHT*10/27);
			g.drawLine(Line55X, HEIGHT*10/27+1, WIDTH, HEIGHT*10/27+1);
			g.drawLine(Line55X, HEIGHT*10/27+2, WIDTH, HEIGHT*10/27+2);
			g.drawLine(Line55X - 11, HEIGHT*7/18, Line55X - 1, HEIGHT*10/27);
			g.drawLine(Line55X - 11, HEIGHT*7/18, Line55X - 1, HEIGHT*10/27+1);

		}
		g.setColor(Color.blue);
		g.drawLine(0, HEIGHT*5/27, Line1X, HEIGHT*5/27);
		g.drawLine(0, HEIGHT*5/27+1, Line1X, HEIGHT*5/27+1);
		g.drawLine(0, HEIGHT*5/27+2, Line1X, HEIGHT*5/27+2);
		g.drawLine(Line1X + 1, HEIGHT*5/27, Line1X + 11, HEIGHT*11/54);
		g.drawLine(Line1X + 1, HEIGHT*5/27+1, Line1X + 11, HEIGHT*11/54+1);

		g.drawLine(Line2X, HEIGHT*5/27, WIDTH, HEIGHT*5/27);
		g.drawLine(Line2X, HEIGHT*5/27+1, WIDTH,HEIGHT*5/27+1);
		g.drawLine(Line2X, HEIGHT*5/27+2, WIDTH, HEIGHT*5/27+2);
		g.drawLine(Line2X - 11, HEIGHT*11/54, Line2X - 1, HEIGHT*5/27);
		g.drawLine(Line2X - 11, HEIGHT*11/54+1, Line2X - 1, HEIGHT*5/27+1);
		g.setColor(Color.cyan);

		g.drawLine(0, HEIGHT*5/18, Line3X, HEIGHT*5/18);
		g.drawLine(0, HEIGHT*5/18+1, Line3X, HEIGHT*5/18+1);
		g.drawLine(0, HEIGHT*5/18+2, Line3X, HEIGHT*5/18+2);
		g.drawLine(Line3X + 1, HEIGHT*5/18, Line3X + 11, HEIGHT*8/27);
		g.drawLine(Line3X + 1, HEIGHT*5/18+1, Line3X + 11, HEIGHT*8/27);

		g.drawLine(Line4X, HEIGHT*5/18, WIDTH, HEIGHT*5/18);
		g.drawLine(Line4X, HEIGHT*5/18+1, WIDTH, HEIGHT*5/18+1);
		g.drawLine(Line4X, HEIGHT*5/18+2, WIDTH, HEIGHT*5/18+2);
		g.drawLine(Line4X - 11, HEIGHT*8/27, Line4X - 1, HEIGHT*5/18);
		g.drawLine(Line4X - 11, HEIGHT*8/27+1, Line4X - 1, HEIGHT*5/18+1);

		for (int i = 0; i < world.getMovableObjects().size(); i++) {
			
				g.drawImage(world.getMovableObjects().get(i).getSpriteImages(), world.getMovableObjects().get(i).getX(),
						world.getMovableObjects().get(i).getY(),ObjectWidth,ObjectHeight, null);
		}
		for (int i = 0; i < world.getControlableObjects().size(); i++) {
			
				g.drawImage(world.getControlableObjects().get(i).getSpriteImages(), world.getControlableObjects().get(i).getX(),
						world.getControlableObjects().get(i).getY(),ObjectWidth,ObjectHeight, null);
		}
		for (int i = 0; i < world.getConstantObjects().size(); i++) {
			
				g.drawImage(world.getConstantObjects().get(i).getSpriteImages(), world.getConstantObjects().get(i).getX(),
						world.getConstantObjects().get(i).getY(),ObjectWidth,ObjectHeight, null);
			}
	}

	private void LoadConstansImages() throws IOException {

		background2 = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		background2 = ImageIO.read(new File("Images/background2.png"));
		backgroundlevel2 = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		backgroundlevel2 = ImageIO.read(new File("Images/backgroundlevel2.png"));
		backgroundlevel3 = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		backgroundlevel3 = ImageIO.read(new File("Images/backgroundlevel3.png"));
		background2 = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		background2 = ImageIO.read(new File("Images/background2.png"));
		clown1 = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		clown1 = ImageIO.read(new File("Images/background2.png"));
		clown2 = GameEngine.s.createCompatibleImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		clown2 = ImageIO.read(new File("Images/background2.png"));

	}

}
