package Circus_of_Plates;

public class Hard implements Strategy{
	
	private static Hard HardMode;
	private Hard(){}
	public static Hard getInstance(){
		if(HardMode == null)
			HardMode = new Hard();
		return HardMode;
	}
	@Override
	public String getLevel() {
		// TODO Auto-generated method stub
		return "level3";
	}

}
