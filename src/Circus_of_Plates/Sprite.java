package Circus_of_Plates;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Sprite implements GameObject{
	private BufferedImage spriteImages ;
	private int x;
	private int y;
	private boolean visible;
	private int type;

	
	public Sprite(int posX, int posY, String path, int type){
		this.x = posX;
		this.y = posY;
		this.type = type;
		this.visible = true;
		try {
			spriteImages = GameEngine.s.createCompatibleImage(500,500, BufferedImage.TYPE_INT_ARGB);
			spriteImages = ImageIO.read(new File(path));
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public BufferedImage getSpriteImages() {
		return spriteImages;
	}

	@Override
	public int getWidth(){
		return spriteImages.getWidth();
	}

	@Override
	public int getHeight() {
		return spriteImages.getHeight();
	}

	@Override
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
