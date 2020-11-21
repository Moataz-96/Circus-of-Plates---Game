package Circus_of_Plates;

public class Easy implements Strategy {
	
	private static Easy EasyMode;
	private Easy(){}
	public static Easy getInstance(){
		if(EasyMode == null)
			EasyMode = new Easy();
		return EasyMode;
	}
	@Override
	public String getLevel() {
		// TODO Auto-generated method stub
		return "level1";
	}

}
