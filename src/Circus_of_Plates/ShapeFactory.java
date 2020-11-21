package Circus_of_Plates;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ShapeFactory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Singleton design pattern.
	 */
	
	private static final Logger logger = Logger.getLogger(ShapeFactory.class.getName());
	private static ShapeFactory instance;

	public static ShapeFactory getInstance() {
		try {
			if (instance == null) {
				instance = new ShapeFactory();
			}
			return instance;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Error in singltone!!!");
		}
	}

	public static void destoryInstance() {
		instance = null;
	}

	private ShapeFactory() {
		
	}
	public  List<Class<? extends Shape>> loadPlugn(String path){
		Reflection cpe = new Reflection();
		classes = cpe.loadPlugins(path);
		return classes;
	}
	private List<Class<? extends Shape>> classes = new LinkedList<Class<? extends Shape>>();
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Shape createShape(String className) throws ClassNotFoundException {
		try {			
			for(Class c : classes){
				if(c.getSimpleName().equals(className)){
			//		logger.debug(c.getName());
					Shape newShape = (Shape) c.newInstance();
					if(newShape instanceof Shape && Shape.class.isAssignableFrom(newShape.getClass())){
						return newShape;
					}
				}
			}
			Class<Shape> newclass = (Class<Shape>) Class.forName(className);
		//	logger.debug(newclass.getName());
			Shape newShape = newclass.newInstance();
			if(newShape instanceof Shape && Shape.class.isAssignableFrom(newShape.getClass())){
				return newShape;
			}
			return newShape;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		//	logger.error("ClassNotFoundException");
			throw new ClassNotFoundException();
		}
	}
	
	
}