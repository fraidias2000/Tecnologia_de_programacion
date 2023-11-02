package javaapplication3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Observable;

class Vista extends JFrame implements java.util.Observer {
	JLabel contador; JButton botonInc, botonDec, botonInit;
   	static final String DEC = "Dec", INC = "Inc", INICIO = "Inicio";
     
	Vista(String titulo) {
	  super(titulo);
      addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) { System.exit(0); }
      });  
      getContentPane().setLayout(new BorderLayout());
      contador = new JLabel();
      contador.setFont(new Font("Arial",Font.PLAIN,40));
      JPanel panel1 = new JPanel();
      panel1.setLayout(new FlowLayout());
      panel1.add(contador);      
      getContentPane().add(panel1,BorderLayout.CENTER);
      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout());

      botonDec = new JButton(DEC);
      panel.add(botonDec);
      botonInc = new JButton(INC);
      panel.add(botonInc);
      botonInit = new JButton(INICIO);
      panel.add(botonInit);
      
      getContentPane().add(panel,BorderLayout.SOUTH);
      setBounds(250,200,250,150); setVisible(true);  
      setResizable(false);
    }

   	public void update(Observable obs, Object obj) {
	  contador.setText("" + ((Integer)obj).intValue());
   	}
	public void setValor(int v){
      contador.setText("" + v);
	}
    	
	public void addControlador(ActionListener controlador){
      botonDec.addActionListener(controlador);
      botonInc.addActionListener(controlador);	
      botonInit.addActionListener(controlador);		
	}
}

class Modelo extends java.util.Observable {		
	private int contador = 0;

	public Modelo(){ }
	public void setValor(int v) {
	  contador = v;
	  setChanged();
	  notifyObservers(contador);
	}
	public void decValor() {
	  contador--;
	  setChanged();
      notifyObservers(contador);
	}	
	public void incValor() {
	  contador++;
	  setChanged();
      notifyObservers(contador);
	}		
}

class Controlador implements java.awt.event.ActionListener {
	Modelo modelo; Vista Vista;
	Controlador() { }

	public void actionPerformed(java.awt.event.ActionEvent e) {
	  if(e.getActionCommand().equals(Vista.DEC))
	    modelo.decValor();
	  else if(e.getActionCommand().equals(Vista.INC))
	    modelo.incValor();
	  else if(e.getActionCommand().equals(Vista.INICIO))
	    modelo.setValor(0);
	}
	public void modelo(Modelo m) { this.modelo = m; }
	public void vista(Vista v){ this.Vista = v; }
	public void inicioModelo(int v){ modelo.setValor(v); }
}

class RunMVC {
	private int valor_inicial = 10;

	public RunMVC() {
      Modelo miModelo = new Modelo();
	  Vista miVista 	= new Vista("RunMVC");
      Controlador miControlador = new Controlador();
      
      miModelo.addObserver(miVista);
     
	  miControlador.modelo(miModelo);
	  miControlador.vista(miVista);
	  miControlador.inicioModelo(valor_inicial);

      miVista.addControlador(miControlador);
	}
	public static void main(String[] args){
	  new RunMVC();
	}
}
