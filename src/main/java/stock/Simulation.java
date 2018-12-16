package main.java.stock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Simulation extends JFrame {

	private String _daimler = "http://www.ad-hoc-news.de/daimler-ag-na-o-n--/de/Aktie/Profil/DE0007100000";

	private String _VW = "http://www.ad-hoc-news.de/volkswagen-ag-st-o-n--/de/Aktie/Profil/DE0007664005";

	private String _siemens = "http://www.ad-hoc-news.de/siemens-ag-na--/de/Aktie/Profil/DE0007236101";

	private String _lufthansa = "http://www.ad-hoc-news.de/lufthansa-ag-vna-o-n--/de/Aktie/Profil/DE0008232125";

	private String _commerz = "http://www.ad-hoc-news.de/commerzbank-ag-o-n--/de/Aktie/Profil/DE0008032004";
	//private Depot _depot;

	private JTextField _daimlerKauf;
	private JLabel _daimlerH;
	private JLabel _daimlerWert;
	private JLabel _daimlerKurs;
	private JTextField _daimlerVerKauf;
	
	private JTextField _vwKauf;
	private JLabel _vwH;
	private JLabel _vwWert;
	private JLabel _vwKurs;
	private JTextField _vwVerKauf;
	
	private JTextField _siemensKauf;
	private JLabel _siemensH;
	private JLabel _siemensWert;
	private JLabel _siemensKurs;
	private JTextField _siemensVerKauf;

	private JTextField _lufthansaKauf;
	private JLabel _lufthansaH;
	private JLabel _lufthansaWert;
	private JLabel _lufthansaKurs;
	private JTextField _lufthansaVerKauf;
	
	private JTextField _commerzKauf;
	private JLabel _commerzH;
	private JLabel _commerzWert;
	private JLabel _commerzKurs;
	private JTextField _commerzVerKauf;
	
	private JLabel _kontoValue;
	private JLabel _aktienWertValue;
	private JLabel _gesamtGeldValue;

	private JButton _sale;
	
	private boolean _lose = false;
	
	private JCheckBox _real;
	
	public Simulation() {

		super("Fenster");
		setLocation(500, 200);
		setPreferredSize(new Dimension(600, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		_real = new JCheckBox("RealKurs");
		_real.setSelected(true);
		
		JPanel konto = createKontoPanel();
		JPanel kurs = createKursPanel();		
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		c.weighty = 2;
		c.gridx = 0;
		c.gridy = 0;
		add(kurs, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		c.gridx = 0;
		c.gridy = 1;
		add(konto, c);
		
		_sale = new JButton("SALE");
		
		_sale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double kontoV = Double.parseDouble(_kontoValue.getText());
				double aktienV = Double.parseDouble(_aktienWertValue.getText());
				
				double newKonto = Math.round((kontoV + aktienV)*100)/100.;
				
				_kontoValue.setText(""+newKonto);
				_kontoValue.setForeground(Color.black);
				_aktienWertValue.setText("0.0");
				_gesamtGeldValue.setText(""+newKonto);
				
				_daimlerH.setText("0");
				_daimlerWert.setText("0.0");
				_siemensH.setText("0");
				_siemensWert.setText("0.0");
				_vwH.setText("0");
				_vwWert.setText("0.0");
				_lufthansaH.setText("0");
				_lufthansaWert.setText("0.0");
				_commerzH.setText("0");
				_commerzWert.setText("0.0");
			}
		}
		);
		_sale.setPreferredSize(new Dimension(30,30));
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		c.gridx = 0;
		c.gridy = 2;
		add(_sale, c);

		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		c.gridx = 0;
		c.gridy = 3;
		add(_real, c);
		
		
		pack();
		setVisible(true);

	}

	private JPanel createKontoPanel() {

		JPanel main = new JPanel();
		main.setLayout(new GridLayout(3, 2, 10, 10));

		JLabel konto = new JLabel("Konto: ");
		main.add(konto);
		_kontoValue = new JLabel("10000.0");
		main.add(_kontoValue);

		JLabel aktienWert = new JLabel("Aktienwert: ");
		main.add(aktienWert);
		_aktienWertValue = new JLabel("0");
		main.add(_aktienWertValue);

		JLabel gesamtGeld = new JLabel("Gesamt: ");
		main.add(gesamtGeld);
		_gesamtGeldValue = new JLabel();
		main.add(_gesamtGeldValue);

		return main;
	}

	private void updateKonto(double kurs, double value) {
		
		double oldKonto = Double.parseDouble(_kontoValue.getText());
		_kontoValue.setText("" + Math.round((oldKonto - value * kurs)*100)/100.);

		if (oldKonto - value * kurs < 0) {
			_kontoValue.setForeground(Color.red);
			
		} else {
			_kontoValue.setForeground(Color.black);
		}
	}
	
	private void updateAktienWert(){
		
		double summe = 0;
		
		Double valueD = Double.parseDouble(_daimlerWert.getText());
		if(valueD == null){
			valueD = 0.;
		}
		Double valueV = Double.parseDouble(_vwWert.getText());
		if(valueV == null){
			valueV = 0.;
		}
		Double valueS = Double.parseDouble(_siemensWert.getText());
		if(valueS == null){
			valueS = 0.;
		}
		Double valueL = Double.parseDouble(_lufthansaWert.getText());
		if(valueL == null){
			valueL = 0.;
		}
		Double valueC = Double.parseDouble(_commerzWert.getText());
		if(valueC == null){
			valueC = 0.;
		}
		summe = valueC+valueD+valueL+valueS+valueV;
		
		_aktienWertValue.setText(""+Math.round(summe*100)/100.);
	}
	
	private void updateKontoGesamt(){
		
		Double valueAktien = Double.parseDouble(_aktienWertValue.getText());
		if(valueAktien == null){
			valueAktien = 0.;
		}
		Double valueKonto = Double.parseDouble(_kontoValue.getText());
		if(valueKonto == null){
			valueKonto = 0.;
		}
		
		_gesamtGeldValue.setText(""+Math.round((valueAktien+valueKonto)*100)/100.);
		
		if(valueAktien+valueKonto < 0){
			_lose = true;
		}
		
	}

	private JPanel createKursPanel() {

		JPanel main = new JPanel();
		main.setLayout(new GridLayout(6, 6, 10, 10));

		// Kopf
		JLabel firms = new JLabel("Firma");
		main.add(firms);

		JLabel kurs = new JLabel("Kurs �");
		main.add(kurs);

		JLabel halte = new JLabel("Halte Stk");
		main.add(halte);

		JLabel wert = new JLabel("Wert");
		main.add(wert);

		JLabel kaufe = new JLabel("Kaufe");
		main.add(kaufe);

		JLabel verkaufe = new JLabel("Verkaufe");
		main.add(verkaufe);

		// Daimler
		JLabel daimler = new JLabel("Daimler");
		main.add(daimler);
		_daimlerKurs = new JLabel();
		main.add(_daimlerKurs);
		_daimlerH = new JLabel("0");
		main.add(_daimlerH);
		_daimlerWert = new JLabel("0");
		main.add(_daimlerWert);

		_daimlerKauf = new JTextField();
		_daimlerKauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Double value = Double.parseDouble(_daimlerKauf.getText());
				_daimlerKauf.setText("");
				
				if(value !=null && value > 0){
					
					Double oldValue = Double.parseDouble(_daimlerH.getText());
	
					if (oldValue == null) {
						_daimlerH.setText("" + value);
						oldValue=value;
					} else {
						oldValue += value;
						_daimlerH.setText("" + oldValue);
					}
	
					double kurs = Double.parseDouble(_daimlerKurs.getText());
					_daimlerWert.setText("" + Math.round(oldValue * kurs * 100.)/ 100.);
					
					updateKonto(kurs, value);	
					updateAktienWert();
					updateKontoGesamt();
				}
			}
		});
		main.add(_daimlerKauf);

		_daimlerVerKauf = new JTextField();
		_daimlerVerKauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Double value = Double.parseDouble(_daimlerVerKauf.getText());
				_daimlerVerKauf.setText("");
				
				if(value !=null && value > 0){
					
					Double oldValue = Double.parseDouble(_daimlerH.getText());
	
					if (oldValue == null) {
						//nix
					} else if(value > oldValue){
						//nix
					}else{
						oldValue-=value;
						_daimlerH.setText("" + oldValue);
							
						double kurs = Double.parseDouble(_daimlerKurs.getText());
						_daimlerWert.setText("" + Math.round(oldValue * kurs * 100.)/ 100.);
						
						updateKonto(kurs, -value);	
						updateAktienWert();
						updateKontoGesamt();
					}
				}
			}
		});
		main.add(_daimlerVerKauf);
		
		//Commerz
		JLabel commerz = new JLabel("Commerzbank");
		main.add(commerz);
		_commerzKurs = new JLabel();
		main.add(_commerzKurs);
		_commerzH = new JLabel("0");
		main.add(_commerzH);
		_commerzWert = new JLabel("0");
		main.add(_commerzWert);

		_commerzKauf = new JTextField();
		_commerzKauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Double value = Double.parseDouble(_commerzKauf.getText());
				_commerzKauf.setText("");
				
				if(value !=null && value > 0){
					
					Double oldValue = Double.parseDouble(_commerzH.getText());
	
					if (oldValue == null) {
						_commerzH.setText("" + value);
						oldValue=value;
					} else {
						oldValue += value;
						_commerzH.setText("" + oldValue);
					}
	
					double kurs = Double.parseDouble(_commerzKurs.getText());
					_commerzWert.setText("" + Math.round(oldValue * kurs * 100.)/ 100.);
					
					updateKonto(kurs, value);	
					updateAktienWert();
					updateKontoGesamt();
				}
			}
		});
		main.add(_commerzKauf);

		_commerzVerKauf = new JTextField();
		_commerzVerKauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Double value = Double.parseDouble(_commerzVerKauf.getText());
				_commerzVerKauf.setText("");
				
				if(value !=null && value > 0){
					
					Double oldValue = Double.parseDouble(_commerzH.getText());
	
					if (oldValue == null) {
						//nix
					} else if(value > oldValue){
						//nix
					}else{
						oldValue-=value;
						_commerzH.setText("" + oldValue);
							
						double kurs = Double.parseDouble(_commerzKurs.getText());
						_commerzWert.setText("" + Math.round(oldValue * kurs * 100.)/ 100.);
						
						updateKonto(kurs, -value);	
						updateAktienWert();
						updateKontoGesamt();
					}
				}
			}
		});
		main.add(_commerzVerKauf);
		
		
		
		// VW
		JLabel vw = new JLabel("VW");
		main.add(vw);
		_vwKurs = new JLabel();
		main.add(_vwKurs);
		_vwH = new JLabel("0");
		main.add(_vwH);
		_vwKauf = new JTextField();
		_vwWert = new JLabel("0");
		main.add(_vwWert);

		_vwKauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Double value = Double.parseDouble(_vwKauf.getText());
				_vwKauf.setText("");
				
				if(value !=null && value > 0){
					
					Double oldValue = Double.parseDouble(_vwH.getText());
	
					if (oldValue == null) {
						_vwH.setText("" + oldValue);
						oldValue = value;
					} else {
						oldValue += value;
						_vwH.setText("" + oldValue);
					}
	
					double kurs = Double.parseDouble(_vwKurs.getText());
					_vwWert.setText("" + Math.round(oldValue * kurs * 100.) / 100.);
	
					updateKonto(kurs, value);
					updateAktienWert();
					updateKontoGesamt();
					
				}
			}
		});
		main.add(_vwKauf);

		_vwVerKauf = new JTextField();
		_vwVerKauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Double value = Double.parseDouble(_vwVerKauf.getText());
				_vwVerKauf.setText("");
				
				if(value !=null && value > 0){
					
					Double oldValue = Double.parseDouble(_vwH.getText());
	
					if (oldValue == null) {
						//nix
					} else if(value > oldValue){
						//nix
					}else{
						oldValue-=value;
						_vwH.setText("" + oldValue);
					
						double kurs = Double.parseDouble(_vwKurs.getText());
						_vwWert.setText("" + Math.round(oldValue * kurs * 100.)/ 100.);
						
						updateKonto(kurs, -value);	
						updateAktienWert();
						updateKontoGesamt();
					}
				}
			}
		});
		main.add(_vwVerKauf);
		
		
		// Siemens
		JLabel siemens = new JLabel("Siemens");
		main.add(siemens);
		_siemensKurs = new JLabel();
		main.add(_siemensKurs);
		_siemensH = new JLabel("0");
		main.add(_siemensH);
		_siemensKauf = new JTextField();
		_siemensWert = new JLabel("0");
		main.add(_siemensWert);

		_siemensKauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Double value = Double.parseDouble(_siemensKauf.getText());
				_siemensKauf.setText("");
				
				if(value !=null && value > 0){
					
					Double oldValue = Double.parseDouble(_siemensH.getText());
	
					if (oldValue == null) {
						_siemensH.setText("" + oldValue);
						oldValue=value;
					} else {
						oldValue += value;
						_siemensH.setText("" + oldValue);
					}
	
					double kurs = Double.parseDouble(_siemensKurs.getText());
					_siemensWert.setText("" + Math.round(oldValue * kurs * 100.)
							/ 100.);
					
					updateKonto(kurs, value);
					updateAktienWert();
					updateKontoGesamt();
				}
			}
		});
		main.add(_siemensKauf);

		_siemensVerKauf = new JTextField();
		_siemensVerKauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Double value = Double.parseDouble(_siemensVerKauf.getText());
				_siemensVerKauf.setText("");
				
				if(value !=null && value > 0){
					
					Double oldValue = Double.parseDouble(_siemensH.getText());
	
					if (oldValue == null) {
						//nix
					} else if(value > oldValue){
						//nix
					}else{
						oldValue-=value;
						_siemensH.setText("" + oldValue);
					
						double kurs = Double.parseDouble(_siemensKurs.getText());
						_siemensWert.setText("" + Math.round(oldValue * kurs * 100.)/ 100.);
						
						updateKonto(kurs, -value);	
						updateAktienWert();
						updateKontoGesamt();
					}
				}
			}
		});
		main.add(_siemensVerKauf);
		
		
		// Lufthansa
		JLabel lufthansa = new JLabel("Lufthansa");
		main.add(lufthansa);
		_lufthansaKurs = new JLabel();
		main.add(_lufthansaKurs);
		_lufthansaH = new JLabel("0");
		main.add(_lufthansaH);
		_lufthansaKauf = new JTextField();
		_lufthansaWert = new JLabel("0");
		main.add(_lufthansaWert);

		_lufthansaKauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Double value = Double.parseDouble(_lufthansaKauf.getText());
				_lufthansaKauf.setText("");
				
				if(value !=null && value > 0){
					
					Double oldValue = Double.parseDouble(_lufthansaH.getText());
	
					if (oldValue == null) {
						_lufthansaH.setText("" + oldValue);
						oldValue=value;
					} else {
						oldValue += value;
						_lufthansaH.setText("" + oldValue);
					}
	
					double kurs = Double.parseDouble(_lufthansaKurs.getText());
					_lufthansaWert.setText("" + Math.round(oldValue * kurs * 100.)
							/ 100.);
	
					updateKonto(kurs, value);
					updateAktienWert();
					updateKontoGesamt();
					
				}

			}
		});
		main.add(_lufthansaKauf);

		_lufthansaVerKauf = new JTextField();
		_lufthansaVerKauf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Double value = Double.parseDouble(_lufthansaVerKauf.getText());
				_lufthansaVerKauf.setText("");
				
				if(value !=null && value > 0){
					
					Double oldValue = Double.parseDouble(_lufthansaH.getText());
	
					if (oldValue == null) {
						//nix
					} else if(value > oldValue){
						//nix
					}else{
						oldValue-=value;
						_lufthansaH.setText("" + oldValue);
					
						double kurs = Double.parseDouble(_lufthansaKurs.getText());
						_lufthansaWert.setText("" + Math.round(oldValue * kurs * 100.)/ 100.);
						
						updateKonto(kurs, -value);	
						updateAktienWert();
						updateKontoGesamt();
					}
				}
			}
		});
		main.add(_lufthansaVerKauf);
		
		
		
		
		updateKurse();

		return main;

	}

	private void updateKurse() {

		if(_real.isSelected()){
			InsertRealTime in = new InsertRealTime(_daimler);
	
			double kurs = in.getKurs();
			_daimlerKurs.setText("" + kurs);
			Double value = Double.parseDouble(_daimlerH.getText());
			if (value != null) {
				_daimlerWert.setText("" + Math.round(value * kurs * 100.) / 100.);
			}
	
			in = new InsertRealTime(_VW);
			kurs = in.getKurs();
			_vwKurs.setText("" + kurs);
			value = Double.parseDouble(_vwH.getText());
			if (value != null) {
				_vwWert.setText("" + Math.round(value * kurs * 100.) / 100.);
			}
	
			in = new InsertRealTime(_siemens);
			kurs = in.getKurs();
			_siemensKurs.setText("" + kurs);
			value = Double.parseDouble(_siemensH.getText());
			if (value != null) {
				_siemensWert.setText("" + Math.round(value * kurs * 100.) / 100.);
			}
	
			in = new InsertRealTime(_lufthansa);
			kurs = in.getKurs();
			_lufthansaKurs.setText("" + kurs);
			value = Double.parseDouble(_lufthansaH.getText());
			if (value != null) {
				_lufthansaWert.setText("" + Math.round(value * kurs * 100.) / 100.);
			}
	
			in = new InsertRealTime(_commerz);
			kurs = in.getKurs();
			_commerzKurs.setText("" + kurs);
			value = Double.parseDouble(_commerzH.getText());
			if (value != null) {
				_commerzWert.setText("" + Math.round(value * kurs * 100.) / 100.);
			}
		}else{
			Random rand = new Random();
			
			double faktor = 10;
			
			double kurs = Double.parseDouble(_daimlerKurs.getText())+rand.nextGaussian()/faktor;
			if(kurs<0){
				kurs=0;
			}
			_daimlerKurs.setText("" + Math.round(kurs*100)/100.);
			Double value = Double.parseDouble(_daimlerH.getText());
			if (value != null) {
				_daimlerWert.setText("" + Math.round(value * kurs * 100.) / 100.);
			}
	
			
			kurs =Double.parseDouble(_vwKurs.getText())+rand.nextGaussian()/faktor;
			if(kurs<0){
				kurs=0;
			}
			_vwKurs.setText("" + Math.round(kurs*100)/100.);
			value = Double.parseDouble(_vwH.getText());
			if (value != null) {
				_vwWert.setText("" + Math.round(value * kurs * 100.) / 100.);
			}
	
			
			kurs =Double.parseDouble(_siemensKurs.getText())+rand.nextGaussian()/faktor;
			if(kurs<0){
				kurs=0;
			}
			_siemensKurs.setText("" + Math.round(kurs*100)/100.);
			value = Double.parseDouble(_siemensH.getText());
			if (value != null) {
				_siemensWert.setText("" + Math.round(value * kurs * 100.) / 100.);
			}
	
			
			kurs =Double.parseDouble(_lufthansaKurs.getText())+rand.nextGaussian()/faktor;
			if(kurs<0){
				kurs=0;
			}
			_lufthansaKurs.setText("" + Math.round(kurs*100)/100.);
			value = Double.parseDouble(_lufthansaH.getText());
			if (value != null) {
				_lufthansaWert.setText("" + Math.round(value * kurs * 100.) / 100.);
			}
	
			
			kurs =Double.parseDouble(_commerzKurs.getText())+rand.nextGaussian()/faktor;
			if(kurs<0){
				kurs=0;
			}			
			_commerzKurs.setText("" + Math.round(kurs*100)/100.);
			value = Double.parseDouble(_commerzH.getText());
			if (value != null) {
				_commerzWert.setText("" + Math.round(value * kurs * 100.) / 100.);
			}
						
		}
		
		
		updateAktienWert();
		updateKontoGesamt();
		
		repaint();
	}
	
	public boolean hasLose(){
		return _lose;
	}

	public static void main(String[] args) {
		Simulation simu = new Simulation();

		while (!simu.hasLose()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			simu.updateKurse();
		}
		System.out.println("STOP");
		
	}

}
