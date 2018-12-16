package main.java.stock;


public class Aktie {

	private String _name;
	
	private double _anzahl;
	
	public Aktie(String name,double anzahl) {
		
		_name = name;
		_anzahl = anzahl;
	}

	public String getName() {
		return _name;
	}

	public double getAnzahl() {
		return _anzahl;
	}	
	
	public void addAnzahl(double dazu){
		_anzahl+=dazu;
	}
}
