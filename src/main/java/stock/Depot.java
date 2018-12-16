package main.java.stock;

import java.util.ArrayList;
import java.util.List;


public class Depot {

	private List<Aktie> _aktien; 
	
	public Depot() {
		_aktien = new ArrayList<Aktie>();
	}
	
	public void insert(Aktie aktie){
		boolean found = false;
		
		for (int i = 0; i < _aktien.size() && !found; i++) {
			if(_aktien.get(i).getName().equals(aktie.getName())){
				_aktien.get(i).addAnzahl(aktie.getAnzahl());
				found = true;
			}	
		}	
		
		if(!found){
			_aktien.add(aktie);
		}
	}
	
	public void remove(Aktie aktie){
		_aktien.remove(aktie);
	}	 
}
