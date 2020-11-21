package Circus_of_Plates;


// Enum

public enum Objects {
	background1(0,"background1.png"),
	background2(1,"background2.png"),
	clown1left(2,"clown1left.png"),
	clown1right(3,"clown1right.png"),
	clown2left(4,"clown2left.png"),
	clown2right(5,"clown2right.png"),
	blueBall(6,"blueBall.png"),
	redBall(7,"redBall.png"),
	bomb(8,"bomb.png"),
	bluePlate(9,"bluePlate.png"),
	redPlate(10,"redPlate.png"),
	greenBall(11,"greenBall.png"),
	greenPlate(12,"greenPlate.png");

	
	private  final int index;
	private  final String path;
	Objects(int indexNum,String Path){
		index = indexNum;
		path = Path;
	}
	public int getIndex(){
		return index;
	}
	public String gePath(){
		return path;
	}
}
