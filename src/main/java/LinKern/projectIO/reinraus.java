package main.java.LinKern.projectIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import main.java.LinKern.comaList.DoubleList;
import main.java.LinKern.comaTree.AVLTree;
import main.java.LinKern.projectGraph.Vertex;

/**
 * @author Libby
 *
 */
public class reinraus {
	// Schalter der nod etwas schneller macht
	private static int klick = 0;
	// Schalter fuer sol
	private static int klickklack = 0;

	/**
	 * @param file
	 *            is name of txt-date
	 * @return format f.e.: nod, pnt,....
	 */
	public static String datenformat(String file) {
		return file.substring(file.length() - 3, file.length());
	}

	/**
	 * @param file
	 *            is txt-file with points
	 * @return list: if pnt, vertex; if gra, vertex(1) and edge(2); if nod,
	 *         vertex; if sol, waylength(1), goodness(2) and vertex(3)
	 */
	public static DoubleList einlesen(String file) throws NumberFormatException {
		// Ergebnisliste
		DoubleList result = new DoubleList();

		// Art der Datei feststellen
		String daten = datenformat(file);

		// einlesen f�r .pnt
		if (daten.equals("pnt"))
			result = lesepnt(file);

		// jetzt kommt .sol
		else if (daten.equals("sol"))
			result = lesesol(file);
		// Fehlerfang
		else
			throw new NumberFormatException("Datenart ist nicht bekannt!");

		return result;
	}

	/**
	 * @param file
	 *            to read in
	 * @return list with vertex
	 * @throws NumberFormatException
	 *             for wrong file
	 */
	private static DoubleList lesepnt(String file) throws NumberFormatException {
		// Ergebnisliste
		DoubleList result = new DoubleList();

		try {
			// Einlesemethoden
			FileInputStream fs = new FileInputStream(file);
			BufferedReader bs = new BufferedReader(new InputStreamReader(fs));

			// #Knoten
			String line = bs.readLine();

			int knoten = Integer.parseInt(line);

			// Kontrolle der Knotenzahl
			int i = 0;

			// durschlaufen und Liste fuellen
			while ((line = bs.readLine()) != null) {
				i++;

				// letzte Zeile kann leer sein, i um eins zur�ck setzten
				if (line.equals("")) {
					i--;
					break;
				}

				// zerhacken
				StringTokenizer toky = new StringTokenizer(line);

				// ein Vertex(Knoten) wird erstellt und gefuellt
				Vertex vert = new Vertex(Integer.parseInt(toky.nextToken()), Double.parseDouble(toky.nextToken()),
						Double.parseDouble(toky.nextToken()));

				// die Liste hat gleiche reihenfolge wie die Datei
				result.insertAtTail((vert));
			}

			if (knoten != i)
				throw new NumberFormatException("Fehler bei der Knotenzahl!");
		} catch (FileNotFoundException e) {
			System.out.println("Datei gibt es nicht!");
		} catch (IOException e) {
			System.out.println("Datensatz leer bzw. nichts zum einlesen!");
		}
		return result;
	}

	/**
	 * @param file
	 *            to read in
	 * @return list with lenght(1), goodness(2) and vertex(3)
	 * @throws NumberFormatException
	 *             for wrong file
	 */
	private static DoubleList lesesol(String file) throws NumberFormatException {
		// Ergebnisliste
		DoubleList result = new DoubleList();

		try {
			// Einlesemethoden
			FileInputStream fs = new FileInputStream(file);
			BufferedReader bs = new BufferedReader(new InputStreamReader(fs));

			// erst,entscheidente Zeile
			String line = bs.readLine();

			line = file.substring(0, file.length() - line.length()) + line;

			// lese pnt
			if (datenformat(line).equals("pnt")) {
				// Punkteliste mit Indizes
				DoubleList pnts = lesepnt(line);

				// fuer Indizes wird aus Liste ein Baum gemacht
				AVLTree see = new AVLTree();

				pnts.resetToHead();

				// erster wird sonst vergessen, bei Doublelist raus
				// see.insert((Vertex)pnts.currentData());

				while (!pnts.isAtTail()) {
					// Vertex aus der Liste in den Baum machen
					see.insert((Vertex) pnts.currentData());
					// weiterlaufen
					pnts.increment();
				}

				// Laenge das Weges kommt zuerst
				result.insertAfter((new Double(Double.parseDouble(bs.readLine()))));

				// Guete der Loesung als zweites
				result.insertAtTail(new Double(Double.parseDouble(bs.readLine())));

				// Liste leeren
				DoubleList point = new DoubleList();

				// durchlauf den Rest und speicher Knoten in Liste
				while ((line = bs.readLine()) != null) {
					// Vertexs im Baum suchen&speichern welche gebraucht werden
					if (!see._freeRun(new Vertex(Integer.parseInt(line), 0, 0)))
						throw new NumberFormatException("Punkt " + line + " gibt es nicht!");
					Vertex x1 = (Vertex) see._freedata();

					// die Liste hat gleiche reihenfolge wie die Datei
					point.insertAtTail(x1);
				}
				// Liste mit Knoten kommt zuletzt
				result.insertAtTail(point);

			} // pnt
			else
				throw new NumberFormatException("Datenfehler, Datei fehlerhaft!");
		} catch (FileNotFoundException e) {
			System.out.println("Datei gibt es nicht!");
		} catch (IOException e) {
			System.out.println("Datensatz leer bzw. nichts zum einlesen!");
		}
		return result;
	}

	/**
	 * @param file
	 *            is name of new file (don't write file.pnt only file)
	 * @param list
	 *            with Vertexs to write out(sorted)
	 * @return true for okay
	 */
	public static boolean schreibePNT(DoubleList list, String file) {
		try {
			// #Knoten bestimmen
			int lange = list.length();

			/*
			 * /zum sortieren einen AVL machen AVLTree see=new AVLTree();
			 * 
			 * //zurueck setzten list.resetToHead();
			 * 
			 * for(int i=0;i<lange;i++) {
			 * see.insert((Vertex)list.currentData()); list.increment(); }
			 * 
			 * //Baum an den Anfang setzen see.reset();
			 */

			// Datei erzeugen, falls noch nicht vorhanden, sonst ueberschrieben
			File datei = new File(file + ".pnt");
			datei.createNewFile();

			// der Rausschreibende
			FileOutputStream fos = new FileOutputStream(datei);

			// Liste zurueck setzten
			list.resetToHead();

			// Knotenzahl schreiben
			fos.write((lange + "\n").getBytes());

			while (!list.isAtTail()) {
				// Vertex erzeugen
				Vertex ver = (Vertex) list.currentData();
				// Vertex ausschreiben
				fos.write((ver.getNum() + " " + ver.getX() + " " + ver.getY() + "\n").getBytes());

				list.increment();
			}

			/*
			 * /letzten Vertex erzeugen Vertex ver=(Vertex)see.currentData();
			 * //Vertex ausschreiben
			 * fos.write((ver.getNum()+" "+ver.getX()+" "+ver.getY()+"\n").
			 * getBytes());
			 */

			// Schreiber ausschalten
			fos.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Dateierstellen!");
		} catch (Exception e) {
			System.out.println("Allg. Fehler beim schreiben von .pnt!");
		}

		return true;
	}

	/**
	 * @param vert
	 *            with vertexs
	 * @param file
	 *            with name from inputfile (*.nod or *.pnt)
	 * @param fileout
	 *            is name of new file (don't write file.sol only file)
	 * @param length
	 *            is waylenght
	 * @param good
	 *            is goodness from way
	 * @return true for okay
	 * @throws NumberFormatException
	 *             for wrong input
	 */
	public static boolean schreibeSOL(Vertex[] vert, String file, String fileout, double lenght, double good)
			throws NumberFormatException {
		try {
			// Datei erzeugen, falls noch nicht vorhanden, sonst ueberschrieben
			File datei = new File(fileout + ".sol");
			datei.createNewFile();

			// der Rausschreibende
			FileOutputStream fos = new FileOutputStream(datei);

			// Fehler fangen
			File f1 = new File(fileout + ".pnt"); // das .pnt wurde rangehangen

			if (!f1.exists())
				throw new NumberFormatException("Die Inputdatei ex. nicht!");

			if (lenght < 0 || good < 0)
				throw new NumberFormatException("Bitte nur positive Strecken/Gueten!");

			// einzelne Zeugs ausschrieben
			fos.write((file + "\n").getBytes());
			fos.write((lenght + "\n").getBytes());
			fos.write((good + "\n").getBytes());

			// Eintraege unsortiert ausschrieben
			for (int i = 0; i < vert.length; i++) {
				// Vertex erzeugen
				Vertex ver = vert[i];
				// Vertex ausschreiben
				fos.write((ver.getNum() + "\n").getBytes());
			}

			// Schreiber ausschalften
			fos.close();

		} catch (IOException e) {
			System.out.println("Fehler beim Dateierstellen");
		}
		return true;

	}

	public static void main(String[] args) {

		DoubleList list = einlesen("bohrer442.pnt");
		Vertex[] vert = new Vertex[list.length()];
		list.resetToHead();
		for (int i = 0; !list.isAtTail(); i++) {
			vert[i] = (Vertex) list.currentData();
			list.increment();
		}

		System.out.println(schreibeSOL(vert, "bohrer442.pnt", "test", 12, 1));
	}
}