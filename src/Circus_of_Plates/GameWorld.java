package Circus_of_Plates;

import java.util.LinkedList;
import java.util.List;

public class GameWorld implements World {
	private static GameWorld world;
	private LinkedList<GameObject> ConstantObjects;
	private LinkedList<GameObject> MovableObjects;
	private LinkedList<GameObject> ControlableObjects;
	private String Statue;
	protected int Speed=4;
	protected int ControlSpeed=1;
	private int width,height;
	
	private GameWorld(){
		Speed = 4; //initiale Speed
		ControlSpeed = 1;
		ConstantObjects = new LinkedList<GameObject>();
		MovableObjects = new LinkedList<GameObject>();
		ControlableObjects = new LinkedList<GameObject>();
	}
	
	public static GameWorld getInstance(){
		if(world == null)
			world = new GameWorld();
		return world;
	}
	@Override
	public List<GameObject> getConstantObjects() {
	//	return (List<GameObject>) ConstantObjects.clone();
		return ConstantObjects;
	}

	@Override
	public List<GameObject> getMovableObjects() {
	//	return (List<GameObject>) MovableObjects.clone();
		return MovableObjects;
	}

	@Override
	public List<GameObject> getControlableObjects() {
	//	return (List<GameObject>) ControlableObjects.clone();
		return ControlableObjects;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean refresh() {
		return false;
	}

	@Override
	public String getStatus() {
		return Statue;
	}

	@Override
	public int getSpeed() {

		return Speed;
	}

	@Override
	public int getControlSpeed() {
		return ControlSpeed;
	}
	
	public void setStatue(String Statue){
		this.Statue = Statue;
	}
	
	public void check(Sprite s){
		this.width = s.getWidth();
		this.height = s.getHeight();
		if(getStatus().equalsIgnoreCase("constant")){
			ConstantObjects.add(s);
		}else if(getStatus().equalsIgnoreCase("movable")){
			MovableObjects.add(s);
		}else if(getStatus().equalsIgnoreCase("controlable")){
			ControlableObjects.add(s);
		}
	
	}


}
