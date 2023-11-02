package javaapplication3;

// Saludos ...
import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
public class MenuDialogSwing extends JFrame implements ActionListener {
  public static String SALIR = "Salir";
  public static String ACERCA_DE = "Acerca de...";
  private String titulo;
  MenuDialogSwing(String titulo) {
    super(titulo);
    try {
//      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//      UIManager.setLookAndFeel("com.sun.java.swing.plaf.metal.MetalLookAndFeel");
//      UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//      UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");
    } catch(Exception e){ System.out.println(e); }
    this.titulo = titulo;
    getContentPane().setLayout(new BorderLayout());
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e){System.exit(0);}
    });  
    JMenu menuFichero = new JMenu("Fichero");
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

    JPanel panelNombre = new JPanel();
    panelNombre.setLayout(new FlowLayout());
    getContentPane().add(panelNombre,BorderLayout.CENTER);

    JButton botonSalir = new JButton(SALIR);
    JPanel panelBoton = new JPanel();
    botonSalir.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
      System.exit(0);
    }});
    panelBoton.setLayout(new FlowLayout());
    panelBoton.add(botonSalir); 
    getContentPane().add(panelBoton,BorderLayout.SOUTH);
           
    JLabel etiquetaNombre = new JLabel();
    panelNombre.add(etiquetaNombre);
    etiquetaNombre.setForeground(Color.blue);
    etiquetaNombre.setFont(new Font("Arial",Font.PLAIN,40));

    setBounds(250,200,440,150); setVisible(true);  
    setResizable(false);

    String nombre = JOptionPane.showInputDialog(
      this,"Dime tu nombre:", titulo,                                                
      JOptionPane.QUESTION_MESSAGE);
    if (nombre != null) 
      etiquetaNombre.setText("Hola " + nombre);
  }
  public void actionPerformed(ActionEvent evt) {
	JMenuItem source = (JMenuItem)evt.getSource();
    if(source.getText().equals(SALIR)) 
      System.exit(0);
    else if(source.getText().equals(ACERCA_DE)) 
      JOptionPane.showMessageDialog(this,
        titulo+"\nAutor: Carlos Catal�n", ACERCA_DE, 
        JOptionPane.INFORMATION_MESSAGE,
        new ImageIcon("duke.gif"));  
  }  
  public static void main (String[] args) {
      new MenuDialogSwing("Saludos...");
  }
}
