package Circus_of_Plates;

public class State  {
	private GameWorld world;
	private static State s;
	private State(){
		world = GameWorld.getInstance();
	}
	public static State getInstance(){
		if(s == null)
			s = new State();
		return s;
	}
	public void upSpeed(){
		world.Speed ++;
	}
	
	public void upControlableSpeed(){
		world.ControlSpeed++;
	}
	public void downSpeed(){
		world.Speed --;
	}
	public void downControlablSpeed(){
		world.ControlSpeed--;
	}
	
	public void checkLevel(Strategy mode){
		if(mode instanceof Medium){
			upSpeed();
		//	upControlableSpeed();
			}
		if(mode instanceof Hard){
			upSpeed();
			//upControlableSpeed();
			}
	
	}

}
