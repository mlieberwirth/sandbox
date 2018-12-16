package main.java.stock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class StandDerDinge extends JFrame{


	//private String _commerz = "http://www.ad-hoc-news.de/commerzbank-ag-o-n--/de/Aktie/Profil/DE0008032004";
	
	private String _commerz = "http://www.wallstreet-online.de/informer/?spid=ws&informer_searchkey=commerzbank";
	
	private JLabel _commerzCurKurs;
	private JLabel _commerzWert;
	
	private JLabel _diffKurs;
	private JLabel _diffWert;
		
	public StandDerDinge(){
		
		super("Commerzbank");
		setLocation(500, 200);
		setPreferredSize(new Dimension(300, 150));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 2;
		c.weighty = 2;
		c.gridx = 0;
		c.gridy = 0;
				
		add(kursPanel(),c);
		
		updateKurs();
		
		pack();
		setVisible(true);
		
	}

	private JPanel kursPanel(){

		JPanel main = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				
				Graphics2D g1 = (Graphics2D) g;
				g1.setColor(Color.blue);

				g1.draw(new Line2D.Double(0, 40, getWidth(), 40));
			}
		};

		
		
		main.setLayout(new GridLayout(3, 3, 10, 10));

		JLabel kauf = new JLabel("Kaufwert: ");
		main.add(kauf);
		
		JLabel commerzKaufkurs = new JLabel("3.57");
		main.add(commerzKaufkurs);

		JLabel commKaufWert = new JLabel("3570.0");
		main.add(commKaufWert);
		
		JLabel curr = new JLabel("Currentwert: ");
		main.add(curr);
				
		_commerzCurKurs = new JLabel();
		main.add(_commerzCurKurs);
		
		_commerzWert = new JLabel();
		main.add(_commerzWert);
		
		JLabel diff = new JLabel("Differenz: ");
		main.add(diff);
				
		_diffKurs = new JLabel();
		main.add(_diffKurs);
		
		_diffWert = new JLabel();
		main.add(_diffWert);
		
		return main;
		
	}
	
	private void updateKurs(){
		
		InsertRealTime2 in = new InsertRealTime2(_commerz);
		
		double kurs = in.getKurs();
		_commerzCurKurs.setText("" + Math.round(kurs * 100.) / 100.);
		
		double value = 1000; 
		_commerzWert.setText("" + Math.round(value * kurs * 100.) / 100.);
		
		_diffKurs.setText(Math.round((kurs-3.57)*100.)/100.+"");
		_diffWert.setText(""+Math.round(((kurs-3.57)*1000)*100.)/100.);
		
		if(kurs-3.57 < 0){
			_diffWert.setForeground(Color.red);
			_diffKurs.setForeground(Color.red);
		}
		else{
			_diffWert.setForeground(Color.green);
			_diffKurs.setForeground(Color.green);
		}
		
				
	}
	
	public static void main(String[] args) {
		StandDerDinge sdd = new StandDerDinge();
	}
	
}
