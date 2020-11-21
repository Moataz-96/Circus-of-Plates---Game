package Circus_of_Plates;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class FlyWeight {
	private static LinkedList<String> Path;
		/**
		 * 
		 */
		// Singleton
		private static FlyWeight instance = null;
		
		private FlyWeight() {}

		public static FlyWeight getInstance() {
			if (instance == null) {
				instance = new FlyWeight();
				
			}
			return instance;
		}
	
	private Sprite s[];	
	@SuppressWarnings("unchecked")
	public Sprite[] getSprite(){

	int i =0;
		if(Path == null){
			Path = new LinkedList<>();
		for(Objects Counter:Objects.values()){
			Path.add("Images/"+Counter.gePath());
			i++;}}
		
		s = new Sprite[Path.size()];
		/*	for(Objects objects: Objects.values()) {
			s[objects.getIndex()] =new Sprite(0,0,objects.gePath(),objects.getIndex()); }	 */
		for(i = 0;i < Path.size(); i ++){
			s[i] = new Sprite(0,0,Path.get(i),i);
		}
		
	return s;
	}
	public void addnewShape(String Path){
		File f = new File(Path);
		if(!f.exists()){
			System.out.println("\nFailed to load new Shape\n");
			return;
		}
		int i =0;
		if(this.Path == null){
			this.Path = new LinkedList<>();
		for(Objects Counter:Objects.values()){

			this.Path.add("Images/"+Counter.gePath());

			//System.out.println(this.Path.get(i));
            i++;}}
		
		this.Path.add(Path);

		//System.out.println(Path);

		
	}
	public LinkedList<String> getPaths(){
		return this.Path;
	}
	

}
