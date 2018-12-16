package main.java.stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

public class InsertRealTime2 {

		private double _kurs;
		private String _adress;
			
		public InsertRealTime2() {
			_adress = "";
			_kurs = -1;
			
		}
		
		public InsertRealTime2(String adress) {
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
			
			String search = "href=\"http://aktien.wallstreet-online.de/2101.html\">COMMERZBANK</a></td><td>Xetra</td><td>803200</td><td>803200</td><td>EUR</td><td";
			
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
						if (tokey.nextToken().equals(search)) {
							String erg = tokey.nextToken();
							kurs = Double.parseDouble(erg.substring(14, 14));
							System.out.println(erg.substring(14, 14));
							kurs +=Double.parseDouble(erg.substring(15, 18));
							System.out.println(erg.substring(15, 18));
							
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
