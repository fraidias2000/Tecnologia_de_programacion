package javaapplication3;

// Swing & Threads ...
import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
public class SwingWorker extends JFrame implements ActionListener {
  public static String CONTAR = "Contar";
  public static String SALIR = "Salir";
  public static String ACERCA_DE = "Acerca de...";
  private String titulo;
  private int contador = 0;
  JProgressBar progressBar;
  SwingWorker(String titulo) {
    super(titulo);
    try {
    } catch(Exception e){ System.out.println(e); }
    this.titulo = titulo;
    getContentPane().setLayout(new BorderLayout());
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e){System.exit(0);}
    });  
    JMenu menuFichero = new JMenu("Fichero");
    JMenuItem menuFicheroContar = new JMenuItem("Contar",'C');
    menuFicheroContar.addActionListener(this);
    menuFichero.add(menuFicheroContar);
    menuFichero.addSeparator();
    JMenuItem menuFicheroSalir = new JMenuItem("Salir",'S');
    menuFicheroSalir.addActionListener(this);
    menuFichero.add(menuFicheroSalir);

    JMenu menuAyuda = new JMenu("Ayuda");
    JMenuItem menuAyudaAcercaDe = 
      new JMenuItem(ACERCA_DE,'A');
    menuAyudaAcercaDe.addActionListener(this);
    menuAyuda.add(menuAyudaAcercaDe);    

    JMenuBar barraMenu = new JMenuBar();
    barraMenu.add(menuFichero); barraMenu.add(menuAyuda);
    getContentPane().add(barraMenu,BorderLayout.NORTH);

    JPanel panelBarra = new JPanel();
    panelBarra.setLayout(new FlowLayout());
    
    progressBar = new JProgressBar(0, 1000);
    progressBar.setValue(0);
    progressBar.setStringPainted(true);
    panelBarra.add(progressBar);    
    getContentPane().add(panelBarra,BorderLayout.CENTER); 
   
    setBounds(250,200,440,150); setVisible(true);  
    setResizable(false);
  }
  
  void incrementarCnt() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {	  
	    for (int i = 0; i < 1000; i++)
          try {
	        contador++;	 
	        progressBar.setValue(contador);	
            Thread.sleep(10);
          } catch(Exception e){ }	  
	  }
     });
  }
  
  public void actionPerformed(ActionEvent evt) {
    if(evt.getActionCommand().equals(SALIR))
      System.exit(0);
    else if(evt.getActionCommand().equals(CONTAR))
      incrementarCnt();
    else if(evt.getActionCommand().equals(ACERCA_DE))
      JOptionPane.showMessageDialog(this,
       titulo+"\nAutor: Carlos Catal�n", ACERCA_DE, 
       JOptionPane.INFORMATION_MESSAGE,
       new ImageIcon("duke.gif"));  
  }  
  public static void main (String[] args) {
      new SwingWorker("Swing Worker ...");
  }
}
