package Circus_of_Plates;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class GameEngineFactory implements IteratorInterface {
	
		private LinkedList<GameObject> AllCreatedSpirites ;
		public GameEngineFactory(){
			AllCreatedSpirites =new LinkedList<GameObject>();
		}
		public Object GetInstance(String ObjectName,Strategy Mode)
		{
			if (ObjectName.equalsIgnoreCase("GameEngine"))
			{GameEngine Game= GameEngine.getInstance(Mode);
			return Game;
			}
			return null;
		}

		@Override
		public Iterator<GameObject> createIterator() {
			return AllCreatedSpirites.iterator();
		}

	


}
