package Circus_of_Plates;

public class Medium implements Strategy{

	private static Medium MediumMode;
	private Medium(){}
	public static Medium getInstance(){
		if(MediumMode == null)
			MediumMode = new Medium();
		return MediumMode;
	}
	@Override
	public String getLevel() {
		// TODO Auto-generated method stub
		return "level2";
	}

}
