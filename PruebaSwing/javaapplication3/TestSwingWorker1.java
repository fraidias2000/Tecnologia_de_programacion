package javaapplication3;

// Swing & Threads ...
import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
public class TestSwingWorker1 extends JFrame implements ActionListener {
  private String titulo; 
  JProgressBar progressBar;
  TestSwingWorker1(String titulo) {
    super(titulo); this.titulo = titulo;
    getContentPane().setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    JMenu menuFichero = new JMenu("Fichero");
    JMenuItem menuFicheroSalir = new JMenuItem("Salir",'S');
    menuFicheroSalir.addActionListener(this);
    menuFichero.add(menuFicheroSalir);

    JMenu menuAyuda = new JMenu("Ayuda");
    JMenuItem menuAyudaAcercaDe = 
      new JMenuItem("Acerca de...",'A');
    menuAyudaAcercaDe.addActionListener(this);
    menuAyuda.add(menuAyudaAcercaDe);    

    JMenuBar barraMenu = new JMenuBar();
    barraMenu.add(menuFichero); barraMenu.add(menuAyuda);
    getContentPane().add(barraMenu,BorderLayout.NORTH);

    JPanel panelBarra = new JPanel();
    panelBarra.setLayout(new FlowLayout());
    progressBar = new JProgressBar(0, 1000);
    progressBar.setStringPainted(true);
    panelBarra.add(progressBar);    
    getContentPane().add(panelBarra,BorderLayout.CENTER); 
    
    JButton botonContar = new JButton("Contar"); 
    botonContar.addActionListener(new ActionListener() { 
    public void actionPerformed( 
      ActionEvent event) { tareaPesada(); } 
    });     
    
    JPanel panelBoton = new JPanel();
    panelBoton.setLayout(new FlowLayout());
    panelBoton.add(botonContar);    
    getContentPane().add(panelBoton,BorderLayout.SOUTH); 
       
    setBounds(250,200, 300,150); setVisible(true);  
    setResizable(false);
  } 
  void tareaPesada() {
	progressBar.setValue(0);  	  
	for (int i = 0; i < 1000; i++)
      try {
	    progressBar.setValue(i);
        Thread.sleep(10);
      } catch(Exception e){ }	  
  }
  
  public void actionPerformed(ActionEvent evt) {
    if(evt.getActionCommand().equals("Salir"))
      System.exit(0);
    else if(evt.getActionCommand().equals("Acerca de..."))
      JOptionPane.showMessageDialog(this,
       titulo+"\nAutor: Carlos Catal�n", "Acerca de...", 
       JOptionPane.INFORMATION_MESSAGE,
       new ImageIcon("duke.gif"));  
  }  
  public static void main (String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new TestSwingWorker1("Swing Worker");
      }
    });	  	  
  }
}
