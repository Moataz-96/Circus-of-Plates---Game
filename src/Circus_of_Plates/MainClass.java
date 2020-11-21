package Circus_of_Plates;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//import Test.NewShape;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Strategy Mode = Hard.getInstance();
		State s = State.getInstance();
		s.checkLevel(Mode);
		FlyWeight fly = FlyWeight.getInstance();
		fly.addnewShape("Images/logo1.png");
		GameEngine engine = GameEngine.getInstance(Mode);
		engine.runProg();
	
	}		
}
