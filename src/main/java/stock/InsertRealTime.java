package main.java.stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

public class InsertRealTime {

	private double _kurs;
	private String _adress;
		
	public InsertRealTime() {
		_adress = "";
		_kurs = -1;
		
	}
	
	public InsertRealTime(String adress) {
		_adress = adress;
		_kurs = fetch(_adress);
		
	}
	
	public double getKurs() {
		return _kurs;
	}

	public void setAdress(String adress) {
		_adress = adress;
	}

	public void update(){
		_kurs = fetch(_adress);
	}


	private double fetch(String adress) {
		String urlName = "";
		String zeile;
		double kurs = -1;

		try {
			urlName = adress;
			URL urlAddress = new URL(urlName);
			URLConnection link = urlAddress.openConnection();
			BufferedReader inStream = new BufferedReader(new InputStreamReader(
					link.getInputStream()));

			boolean found = false;
			while (!found && (zeile = inStream.readLine()) != null) {

				StringTokenizer tokey = new StringTokenizer(zeile);

				while (!found && tokey.hasMoreTokens()) {
					if (tokey.nextToken().equals("alt=\"Realtime\"/>")) {
						kurs = Double.parseDouble(tokey.nextToken());
						found = true;
					}
				}
			}
		}
		catch (MalformedURLException e) {
			System.out.println(urlName + e.toString());
		}

		catch (IOException e) {
			System.out.println("Fehler bei Zugriff auf URL: " + e.toString());
		}

		return kurs;

	} // fetch
}