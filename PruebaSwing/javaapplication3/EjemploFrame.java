package javaapplication3;

import java.awt.Color;
 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
public class EjemploFrame extends JFrame {
 
	public EjemploFrame()
	{
//		setUndecorated(true);
		setBounds(100,100,400,400);
		JPanel pnl = new JPanel();
		JLabel lbl = new JLabel("THIS IS A LABEL IN A JPANEL IN AN UNDECORATED JFRAME");
		pnl.setBounds(0,0,400,300);
//		pnl.setBackground(Color.CYAN);
		lbl.setBounds(0,0,200,100);
		pnl.add(lbl);
		getContentPane().add(pnl);
 
		setVisible(true);
		
	}
	public static void main(String[] args) {
		EjemploFrame f = new EjemploFrame();
		
	}
}