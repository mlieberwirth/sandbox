package main.java.timetracker;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class StundenCalc extends Applet implements ActionListener {

	private String _directory = "/Users/Libby/Documents/DENISE/StundenArbeit/";
		
	private JLabel _vonText;
	private JTextField _vonStunde;
	private JTextField _vonMinute;

	private JLabel _bisText;
	private JTextField _bisStunde;
	private JTextField _bisMinute;

	private JLabel _arbZeitText;
	private JLabel _arbStunden;

	private JLabel _tagNummerText;
	private JComboBox _tagNummer;

	private String[][] _data;

	private JLabel _summeText;
	private JLabel _summeStunden;

	private JButton _save;
	private JTextField _saveFile;
	private JFrame _saveFrame;
	private boolean _saving;
	private String _saveString;

	private JButton _load;
	private JComboBox _loadFile;

	private String _loadString = "";
	private JFrame _loadFrame;

	private JButton _neu;
	private JFrame _neuFrame;
	
	private JLabel _tourText;
	private JTextField _tour;
	
	private JLabel _einkaufText;
	private JTextField _einkauf;
	
	public void init() {

		setLayout(new GridBagLayout());
		setSize(new Dimension(800, 560));
		 		
		GridBagConstraints c = new GridBagConstraints();

		JPanel empty = new JPanel();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1; 
		c.weighty = 1;

		c.gridx = 0;
		c.gridy = 0;
		c.gridheight=3;
		add(empty, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight=1;
		add(saveLoadNewPanel(), c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridheight=2;
		add(calcPanel(), c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight=3;
		add(tablePanel(), c);
	}

	private JPanel calcPanel() {

		JPanel main = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.blue);

				g2.draw(new Line2D.Double(0, 175, getWidth(), 175));

			}
		};

		main.setLayout(new GridBagLayout());
		main.setPreferredSize(new Dimension(200, 230));
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		
		GridBagConstraints c1 = new GridBagConstraints();
		c1.weightx = 1;
		c1.weighty = 1;
		c1.ipadx=50;
		c1.ipady=1;
		
		_tagNummerText = new JLabel("Tag Nr.: ");
		c.gridx = 0;
		c.gridy = 0;
		main.add(_tagNummerText, c);
		
		_tagNummer = new JComboBox();

		for (int i = 0; i < 31; i++) {
			_tagNummer.addItem((i + 1));
		}

		c.gridx = 1;
		c.gridy = 0;
		_tagNummer.addActionListener(this);
		main.add(_tagNummer, c);

		_tourText = new JLabel("Tour:");
		c.gridx = 0;
		c.gridy = 1;
		main.add(_tourText, c);

		_tour = new JTextField("");
		c1.gridx = 1;
		c1.gridy = 1;
		_tour.addActionListener(this);
		_tour.setBorder(BorderFactory.createLoweredBevelBorder());
		main.add(_tour, c1);

		_einkaufText = new JLabel("Einkauf:");
		c.gridx = 0;
		c.gridy = 2;
		main.add(_einkaufText, c);

		_einkauf = new JTextField("");
		c1.gridx = 1;
		c1.gridy = 2;
		_einkauf.addActionListener(this);
		_einkauf.setBorder(BorderFactory.createLoweredBevelBorder());
		main.add(_einkauf, c1);
		
		JLabel empty = new JLabel(" ");
		c.gridx = 0;
		c.gridy = 3;
		main.add(empty, c);
		
		JLabel stunde = new JLabel("Stunde");
		c.gridx = 1;
		c.gridy = 4;
		main.add(stunde, c);

		JLabel minute = new JLabel("Minute");
		c.gridx = 2;
		c.gridy = 4;
		main.add(minute, c);

		_vonText = new JLabel("von: ");
		c.gridx = 0;
		c.gridy = 5;
		main.add(_vonText, c);

		_vonStunde = new JTextField();
		c1.gridx = 1;
		c1.gridy = 5;
		_vonStunde.addActionListener(this);
		_vonStunde.setBorder(BorderFactory.createLoweredBevelBorder());
		main.add(_vonStunde, c1);

		_vonMinute = new JTextField();
		c1.gridx = 2;
		c1.gridy = 5;
		_vonMinute.addActionListener(this);
		_vonMinute.setBorder(BorderFactory.createLoweredBevelBorder());
		main.add(_vonMinute, c1);

		_bisText = new JLabel("bis: ");
		c.gridx = 0;
		c.gridy = 6;
		main.add(_bisText, c);

		_bisStunde = new JTextField();
		c1.gridx = 1;
		c1.gridy = 6;
		_bisStunde.addActionListener(this);
		_bisStunde.setBorder(BorderFactory.createLoweredBevelBorder());
		main.add(_bisStunde, c1);

		_bisMinute = new JTextField();
		c1.gridx = 2;
		c1.gridy = 6;
		_bisMinute.addActionListener(this);
		_bisMinute.setBorder(BorderFactory.createLoweredBevelBorder());
		main.add(_bisMinute, c1);

		JLabel empty1 = new JLabel(" ");
		c.gridx = 0;
		c.gridy = 7;
		main.add(empty1, c);

		_arbZeitText = new JLabel("Arbeitszeit: ");
		c.gridx = 0;
		c.gridy = 8;
		main.add(_arbZeitText, c);

		_arbStunden = new JLabel(" ");
		c1.gridx = 1;
		c1.gridy = 8;
		main.add(_arbStunden, c1);

		JLabel stunde2 = new JLabel("Stunden");
		c.gridx = 2;
		c.gridy = 8;
		main.add(stunde2, c);

		JLabel empty2 = new JLabel(" ");
		c.gridx = 0;
		c.gridy = 9;
		main.add(empty2, c);

		_summeText = new JLabel("Gesamtstunden: ");
		_summeText.setForeground(Color.blue);
		c.gridx = 0;
		c.gridy = 10;
		main.add(_summeText, c);

		_summeStunden = new JLabel(" ");
		_summeStunden.setForeground(Color.blue);
		c1.gridx = 1;
		c1.gridy = 10;
		main.add(_summeStunden, c1);

		JLabel pause = new JLabel("Immer 0.5h Pause !");
		c.gridx = 1;
		c.gridy = 11;
		main.add(pause, c);

		return main;
	}

	private JPanel saveLoadNewPanel() {

		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;

		_neu = new JButton("Neu");
		c.gridx = 0;
		c.gridy = 0;
		_neu.addActionListener(this);
		main.add(_neu, c);
		
		
		_saveFile = new JTextField();
		c.gridx = 0;
		c.gridy = 1;
		main.add(_saveFile, c);

		_save = new JButton("Speichern");
		_save.addActionListener(this);
		c.gridx = 0;
		c.gridy = 2;
		main.add(_save, c);

		_loadFile = new JComboBox();
		InOutFile iof = new InOutFile(_directory);
		iof.readFiles();
				
		for (int i = 0; i < iof.getFiles().length; i++) {
			_loadFile.addItem(iof.getFiles()[i]);
		}
		c.gridx = 1;
		c.gridy = 1;
		main.add(_loadFile, c);
		
		_load = new JButton("Laden");
		_load.addActionListener(this);
		c.gridx = 1;
		c.gridy = 2;
		main.add(_load, c);

		return main;
	}

	private JPanel tablePanel() {

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new GridBagLayout());

		_data = new String[31][6];

		for (int i = 0; i < _data.length; i++) {
			_data[i][0] = "" + (i + 1);
			_data[i][1] = "-";
			_data[i][2] = "-";
			_data[i][3] = "0";
			_data[i][4] = "-";
			_data[i][5] = "-";


		}

		String[] columnNames = { "Tag", "von", "bis", "Stunden","Tour","Einkauf" };

		JTable table = new JTable(_data, columnNames);

		table.setEnabled(false);

		table.setGridColor(Color.black);

		JScrollPane scroll = new JScrollPane(table);

		scroll.setPreferredSize(new Dimension(350, 320));

		tablePanel.add(scroll);

		return tablePanel;

	}

	private boolean calcTime() {

		boolean result = true;
		double erg = -1;

		int number = _tagNummer.getSelectedIndex();

		Integer vonStunde = 0;
		Integer vonMinute = 0;

		Integer bisStunde = 0;
		Integer bisMinute = 0;

		try {
			vonStunde = Integer.parseInt(_vonStunde.getText());
			if(vonStunde < 0 || vonStunde > 23){
				result = false;
			}
			vonMinute = Integer.parseInt(_vonMinute.getText());
			if(vonMinute < 0 || vonMinute > 59){
				result = false;
			}
			bisStunde = Integer.parseInt(_bisStunde.getText());
			if(bisStunde < 0 || bisStunde > 23){
				result = false;
			}
			bisMinute = Integer.parseInt(_bisMinute.getText());
			if(bisMinute < 0 || bisMinute > 59){
				result = false;
			}
			
			double pause = .5;

			erg = bisStunde + bisMinute / 60.
					- vonStunde - vonMinute / 60.
					- pause;
			
			if(erg < 0){
				result = false;
			}

		} catch (Exception e) {
			result = false;
		}

		if (result) {

			double wert = Math.round(erg * 100.) / 100.;
			_arbStunden.setText("" + wert);

			String vMinute = "";
			if (vonMinute.intValue() == 0) {
				vMinute = "00";
			}
			else if (vonMinute.intValue() < 10) {
				vMinute = "0"+vonMinute.intValue();
			}
			else{
				vMinute = vonMinute.intValue()+"";
			}
			
			String vStunde="";
			if(vonStunde.intValue() == 0){
				vStunde = "00";
			}
			else if(vonStunde.intValue() < 10){
				vStunde = "0"+vonStunde.intValue();
			}	
			else{
				vStunde = vonStunde.intValue()+"";
			}
			
			String bMinute="";
			if (bisMinute.intValue() == 0) {
				bMinute = "00";
			}
			else if (bisMinute.intValue() < 10) {
				bMinute = "0"+bisMinute.intValue();
			}
			else{
				bMinute = bisMinute.intValue()+"";
			}
			
			String bStunde="";
			if(bisStunde.intValue() == 0){
				bStunde = "00";
			}
			else if(bisStunde.intValue() < 10){
				bStunde = "0"+bisStunde.intValue();
			}	
			else{
				bStunde = bisStunde.intValue()+"";
			}
				
			_data[number][1] = vStunde+":"+vMinute;
			_data[number][2] = bStunde+":"+bMinute;
			
			_data[number][3] = "" + wert;
			
			if(_tour.getText().equals("")){
				_data[number][4] = "-";
			}
			else{
				_data[number][4] = _tour.getText();
			}
			if(_einkauf.getText().equals("")){
				_data[number][5] = "-";
			}
			else{
				_data[number][5] = _einkauf.getText();
			}
		}

		return result;

	}

	public void actionPerformed(ActionEvent event) {

//***********TAG********//
		
		if (event.getSource() == _tagNummer) {

			// index!!!
			int number = _tagNummer.getSelectedIndex();

			if (!_data[number][1].equals("-")) {
				// _data[number][1].equals("");

				String von = _data[number][1];
				String bis = _data[number][2];
				String stunden = _data[number][3];
				String tour = _data[number][4];
				String einkauf = _data[number][5];
				
				_vonStunde.setText(von.substring(0, von.length() - 3));
				_vonMinute.setText(von
						.substring(von.length() - 2, von.length()));

				_bisStunde.setText(bis.substring(0, bis.length() - 3));
				_bisMinute.setText(bis
						.substring(bis.length() - 2, bis.length()));

				_arbStunden.setText(stunden);
				_arbStunden.setForeground(Color.black);

				_tour.setText(tour);
				_einkauf.setText(einkauf);
				
				
			} else {

				_vonStunde.setText("");
				_vonMinute.setText("");
				_bisStunde.setText("");
				_bisMinute.setText("");
				_arbStunden.setText("");
				_tour.setText("");
				_einkauf.setText("");				
			}
			
//*******SPEICHERN***********//
			
		} else if (event.getSource() == _save) {

			_saveString = _saveFile.getText();
			_saveFile.setText("");
			
			if (!_saveString.equals("")) {
				save(_saveString);
			} else {

				JFrame saver = new JFrame("Speichern");
				saver.setVisible(true);
				saver.setLocation(150, 150);
				saver.add(new JLabel("   Keinen Name eingegeben"));
				saver.setSize(new Dimension(200, 80));
			}

//**********LADEN*************//
			
		} else if (event.getSource() == _load) {

			_loadString = (String)_loadFile.getSelectedItem();
			
			if (!_loadString.equals("")) {

				_loadFrame = new JFrame("Achtung!");
				_loadFrame.setLayout(new FlowLayout());

				_loadFrame.setVisible(true);
				_loadFrame.setLocation(150, 150);

				JLabel text = new JLabel("Ungespeicherte Daten werden gel�scht!");
				_loadFrame.add(text);
				_loadFrame.setSize(new Dimension(300, 100));

				JButton ok = new JButton("OK");
				ok.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						_loadFrame.setVisible(false);

						load(_loadString);
						
						_vonStunde.setText("");
						_vonMinute.setText("");
						_bisStunde.setText("");
						_bisMinute.setText("");
						_arbStunden.setText("");
						_tour.setText("");
						_einkauf.setText("");
						
						_tagNummer.setSelectedIndex(0);
						repaint();
						
					}
				});

				_loadFrame.add(ok);

			} else {

				JFrame saver = new JFrame("Laden");
				saver.setVisible(true);
				saver.setLocation(150, 150);
				saver.add(new JLabel("   Keinen Name eingegeben"));
				saver.setSize(new Dimension(200, 80));
			}
			
//************NEU*****************//
		} else if (event.getSource() == _neu) {
			
			_neuFrame = new JFrame("Achtung!");
			_neuFrame.setLayout(new FlowLayout());

			_neuFrame.setVisible(true);
			_neuFrame.setLocation(150, 150);

			JLabel text = new JLabel("Neu: Ungespeicherte Daten werden gel�scht!");
			_neuFrame.add(text);
			_neuFrame.setSize(new Dimension(300, 100));

			JButton ok = new JButton("OK");
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					_neuFrame.setVisible(false);

					_vonStunde.setText("");
					_vonMinute.setText("");
					_bisStunde.setText("");
					_bisMinute.setText("");
					_arbStunden.setText("");
					_summeStunden.setText("");
					_tour.setText("");
					_einkauf.setText("");
										
					for (int i = 0; i < _data.length; i++) {
						_data[i][0] = "" + (i + 1);
						_data[i][1] = "-";
						_data[i][2] = "-";
						_data[i][3] = "0";
						_data[i][4] = "-";
						_data[i][5] = "-";

					} 
					_tagNummer.setSelectedIndex(0);
					repaint();
				}
			});

			_neuFrame.add(ok);

//*************BERECHNEN STUNDEN**********//
			
		} else {

			if (!calcTime()) {
				_arbStunden.setText("Daten-Ceck!");
				_arbStunden.setForeground(Color.red);
			} else {
				_arbStunden.setForeground(Color.black);
				double summe = 0;
				for (int i = 0; i < _data.length; i++) {
					summe += Double.parseDouble(_data[i][3]);
				}
				_summeStunden.setText("" + Math.round(summe * 100) / 100.);
			}

		}

		repaint();
	}

	private boolean save(String file) {

		boolean result = true;

		Reader f = null;
		try {
			f = new FileReader(_directory+ file + ".txt");
		} catch (Exception e) {
			System.out.println(e);
		}

		_saving = true;
		
		if (f != null) {
			
			_saving = false;
			
			_saveFrame = new JFrame("Speichern");
			_saveFrame.setLayout(new FlowLayout());

			_saveFrame.setVisible(true);
			_saveFrame.setLocation(150, 150);
			_saveFrame.add(new JLabel("   Datei existiert schon, �berschreiben?"));
			_saveFrame.setSize(new Dimension(300, 100));
			
			JButton ok = new JButton("OK");
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					_saveFrame.setVisible(false);
					speichern(_saveString);
									}
			});

			_saveFrame.add(ok);
		}
		
		if(_saving) {
			speichern(file);
			
			InOutFile iof = new InOutFile(_directory);
			iof.addFile(file);
			
			iof.readFiles();
			
			_loadFile.addItem(file);
			repaint();
			
		}

		return result;
	}

	private boolean speichern(String file){
		
		boolean result = true;
		
		Writer fw = null;

		try {
			fw = new FileWriter(_directory + file + ".txt");

			for (int i = 0; i < 31; i++) {
				fw.write((i + 1) + " " + _data[i][1] + " " + _data[i][2]
						+ " " + _data[i][3] +" "+ _data[i][4]+" "+_data[i][5]);
				fw.append('\n');
			}

		} catch (Exception e) {
			result = false;
		}

		finally {
			if (fw != null)
				try {
					fw.close();
				} catch (Exception e) {
					result = false;
				}
		}
		
		return result;
		
	}
	
	private boolean load(String file) {

		boolean result = true;

		Reader f = null;
		try {

			f = new FileReader(_directory+ file + ".txt");
			BufferedReader fileIn = new BufferedReader(f);

			String str = "";

			double summe = 0;
						
			for (int i = 0; i < 31; i++) {
				str = fileIn.readLine();

				StringTokenizer toky = new StringTokenizer(str);
				// ist die zahl der erste
				String tok = toky.nextToken();

				for (int j = 0; j < 5; j++) {
					 tok = toky.nextToken();
					_data[i][j + 1] = tok;
				}
				
				summe+=Double.parseDouble(_data[i][3]);

			}			
			_summeStunden.setText(""+Math.round(summe*100)/100.);

		} catch (Exception e) {

			JFrame saver = new JFrame("Laden");
			saver.setVisible(true);
			saver.setLocation(150, 150);
			saver.add(new JLabel("   Kann Datei nicht finden!"));
			saver.setSize(new Dimension(200, 80));

		}

		finally {
			try {
				f.close();
			} catch (Exception e) {
			}
		}

		return result;

	}

}
