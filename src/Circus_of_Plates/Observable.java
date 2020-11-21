package Circus_of_Plates;

public interface Observable {
	 void addObserver(Observer o);
	 void removeObserver();
	 void notifyObserver();
}
