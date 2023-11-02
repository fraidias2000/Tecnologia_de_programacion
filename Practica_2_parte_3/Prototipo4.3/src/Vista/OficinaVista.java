/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.2
 *02/05/2020 
 * 
 */

package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import modelo.Oficina;
import modelo.Viaje;
import Control.OyenteVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.Asiento;
import modelo.Viajero;

public class OficinaVista implements ActionListener, PropertyChangeListener {
    /** Constantes para redimensionamiento */
  private static final String MENSAJE_NO_ENCONTRADO = "No existe un viaje para tal fecha";
  private static final int MARGEN_HORIZONTAL = 50;
  private static final int MARGEN_VERTICAL = 100;
  public static final int NUMERO_FILAS = 5;
  public static final int NUMERO_COLUMNAS = 5;
   private final String ASIGNAR = "ASIGNAR";
  private final String DESASIGNAR = "DESASIGNAR";
  private final String GENERAR = "GENERAR HOJA VIAJE";
  private final String BUSCAR = "BUSCAR VIAJES";
  private final String TEXTOVIAJE = "VIAJE:";
  private final String VER_ASIENTOS = "VER ASIENTOS";
  private static final int FILAS_ASIGNACION_VISTA = 5;
  private static final int COLUMNAS_ASIGNACION_VISTA = 20;
  private String TEXTO_ASIGNAR = "Introduce el nombre y DNI del viajero";
  private String TEXTO_NOMBRE = "Nombre: ";
  private String TEXTO_DNI = "DNI: ";
  private final String ACEPTAR = "Aceptar";
 public static final String OCUPAR = "Ocupar";
 public static final String DESOCUPAR = "Desocupar";
 
  private static final Object [] anios = {2020, 2021, 2022, 2023, 2024, 2025};
  private static final Object[] meses = {"Enero", "Febrero", "Marzo", "Abril", 
      "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", 
      "Diciembre"};
  
  
  private static OficinaVista instancia = null;
  private OyenteVista oyenteVista;
  private JComboBox seleccionarAnio ;
  private JComboBox seleccionarMes;
  private JComboBox seleccionarDia;
  private JComboBox viajeSeleccionado;
  private JTextArea texto;
 
  private JFrame ventana;
  private JButton verAsientos ;
  private JButton buscarViaje;
  private JButton desasignar;
  private JButton asignar;
  private JButton generar;
  
  private JFrame ventanaAsignar;
  private JTextArea textoAsignar;
  private JTextArea nombreViajero;
  private JTextArea dniViajero;
  private JTextField insertarViajero;
  private JTextField insertarDNI;
  private JButton aceptar;
  
  JFrame informacionViajero;
  JTextArea datosViajero;
  
  
  private Oficina oficina;
  private AutobusVista autobusVista;
  private AsientoVista asientoSeleccionado;
  private Viaje viajeElegido;
 
  private Viaje viajes[];

  public OficinaVista(OyenteVista oyenteVista, Oficina oficina) throws FileNotFoundException {       
    this.oyenteVista = oyenteVista;
    this.oficina = oficina;
    viajes = new Viaje[oficina.ViajesTotales()]; 
    viajes = oficina.obtenerViajes(viajes);
    crearVentana();   
   
    }
  
     /**
   * Devuelve la instancia de la vista del tablero
   * 
   */        
  public static synchronized OficinaVista devolverInstancia(
                             OyenteVista oyenteVista, Oficina oficina)
                             throws FileNotFoundException {
    if (instancia == null)
      instancia = new OficinaVista(oyenteVista, oficina);
    return instancia;
  } 
  
  /*
  * Crea una ventana donde aparece toda la informacion de los viajes y la 
  * distribución de los asientos del autobús.
  */
  public void crearVentana(){
    ventana = new JFrame();
   
      ventana.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        oyenteVista.eventoProducido(OyenteVista.Evento.SALIR, 0, null, 0);
      } });
     
    JPanel panelNorte = new JPanel();
    JPanel panelAsientos = new JPanel();
    JPanel panelSur = new JPanel();
    
    ventana.getContentPane().setLayout(new BorderLayout());
      
    panelNorte.setLayout(new GridLayout(2, 4));
    panelAsientos.setLayout(new FlowLayout());
    panelSur.setLayout(new FlowLayout());
    
    // creamos elementos
    creaMenus(panelNorte);
    ventana.getContentPane().add(panelNorte, BorderLayout.NORTH);
    
    creaPlano(panelAsientos);
    ventana.getContentPane().add(panelAsientos, BorderLayout.CENTER);
    
    crearBotonesSur(panelSur);
    ventana.getContentPane().add(panelSur, BorderLayout.SOUTH);
    
   /* JPanel panelAsignacion = new JPanel();
    panelAsignacion.setLayout(new BorderLayout());
    crearAsignacionVista(panelAsignacion);
    ventana.getContentPane().add(panelAsignacion, BorderLayout.EAST);
    */
    
    ventana.setResizable(false);    
    ventana.pack();  // ajusta ventana y sus componentes
    ventana.setVisible(true);
    ventana.setLocationRelativeTo(null);
  }
  
  /*
   * Crea un menú donde selecionar el viaje
   */
  public void creaMenus(JPanel panelNorte) {
    seleccionarAnio = new JComboBox();
    seleccionarMes = new JComboBox();
    seleccionarDia = new JComboBox();
    viajeSeleccionado = new JComboBox();
    texto = new JTextArea(TEXTOVIAJE);
    verAsientos = crearBoton(VER_ASIENTOS);
    buscarViaje = crearBoton(BUSCAR);
   
    rellenarComboBox(seleccionarAnio,anios);
    rellenarComboBox(seleccionarMes,meses);
    rellenarDias(seleccionarDia);

    panelNorte.add(seleccionarAnio);
    panelNorte.add(seleccionarMes);
    panelNorte.add(seleccionarDia);
    panelNorte.add(buscarViaje);
      
    panelNorte.add(texto);
    panelNorte.add(viajeSeleccionado);
    panelNorte.add(verAsientos);
  }
  
    
  /*
   * Crea el panel sur
   */
  public void crearBotonesSur(JPanel panelSur){
    asignar = crearBoton(ASIGNAR);
    generar = crearBoton(GENERAR);
    desasignar = crearBoton(DESASIGNAR);
    desasignar.setVisible(false);
    panelSur.add(desasignar);
    panelSur.add(asignar);
    panelSur.add(generar);
  }
   /**
   * Crea botón barra de herramientas
   */ 
  private JButton crearBoton(String etiqueta) {
    JButton boton = new JButton(etiqueta);
    boton.addActionListener(this);
    boton.setActionCommand(etiqueta);
    return boton;
  }  
  
 /*
  * Crea un panel con la distribucion de los asientos
  */
  public void creaPlano(JPanel panel) {
       autobusVista = new AutobusVista(this, AutobusVista.RECIBE_EVENTOS_RATON);
       panel.add(autobusVista);
  }
  
   /**
   * Crea vista del texto del recordatorio 
   */   
  private void crearAsignacionVista(JPanel panel) {
    nombreViajero = new JTextArea(FILAS_ASIGNACION_VISTA, 
                                       COLUMNAS_ASIGNACION_VISTA); 
     dniViajero = new JTextArea(FILAS_ASIGNACION_VISTA, 
                                       COLUMNAS_ASIGNACION_VISTA); 
    nombreViajero.setEditable(false);
    nombreViajero.setLineWrap(true);
    dniViajero.setEditable(false);
    dniViajero.setLineWrap(true);
    
    panel.add(new JLabel("hola"), BorderLayout.NORTH);
    panel.add(nombreViajero, BorderLayout.CENTER);
    panel.add(dniViajero, BorderLayout.CENTER);
  }
  
  
  
     /*
      * Selecciona un asiento
      */
  public void seleccionarAsiento(AsientoVista asiento) {
    // Quita selección anterior  
    if (asientoSeleccionado != null) {        
      asientoSeleccionado.deseleccionar();
    }     
    asiento.seleccionar();            
    this.asientoSeleccionado = asiento;
    
    if(asientoSeleccionado.isOcupado()){
            desasignar.setVisible(true);
        }else{
        desasignar.setVisible(false);
    }

  }  
  
  /**
   * Pone texto de un recordatorio
   */
  public void ponerDatosViajero(String dni, String nombre) {
    nombreViajero.setText(nombre); 
    dniViajero.setText(dni); 
  }
  
 public void ponerAutobusVista(Viaje viaje){
     autobusVista.ponerAsientos(viaje);
     //ponerDatosViajero("","");
 }
  
  /*
   * Rellena el JComboBox dias
   */
  public void rellenarDias(JComboBox lista){
      for (int i = 1; i <= 31; i++) {
          lista.addItem(i);   
      }
  }
  
  /*
   * Rellena un JcomboBox con un vector dado
   */
public void rellenarComboBox(JComboBox lista, Object vector[]){ 
      for (int i = 0; i < vector.length; i++) {
        lista.addItem(vector[i]);   
      }
   }
  



  /*
   * Recibe notificación un evento de la interfaz de usuario
   */
  public void notificacionAControl(String evento) throws IOException{
    switch(evento) {
     case BUSCAR:
          buscarAsiento();
          break;  
    case VER_ASIENTOS :
          verAsientos();
          break;
        
    case ASIGNAR:    
        crearVentanaAsignar();                  
        break;
        
    case ACEPTAR:
        asignarViajero();
        ventanaAsignar.dispose();
        break;
     case DESASIGNAR:     
        desasignarViajero();             
        break;    
        
     case GENERAR:
         oficina.generarHoja(viajeElegido.getCodigo());
         break;
         
   
    }
  }
  
  
  public void desasignarViajero(){  
        oyenteVista.eventoProducido(OyenteVista.Evento.DESOCUPAR_ASIENTO, 
                viajeElegido.getCodigo(), null, asientoSeleccionado.getNumero());
  
  }
  
  public void verViajero(AsientoVista asiento){
      Viajero viajero = asiento.getViajero();
      crearVentanaDatosViajero(viajero);
      
  }
  public void crearVentanaDatosViajero(Viajero viajero){
       datosViajero = new JTextArea();
       datosViajero.setText(viajero.toString());
       informacionViajero = new JFrame();
       informacionViajero.getContentPane().setLayout(new BorderLayout());
       informacionViajero.getContentPane().add(datosViajero, BorderLayout.NORTH);
       
       
       informacionViajero.setResizable(false);    
       informacionViajero.pack();  // ajusta ventana y sus componentes
       informacionViajero.setVisible(true);
       informacionViajero.setLocationRelativeTo(null);
  }
  
  
  /*
   * Busca los asientos para un viaje dado
   */
  
  public void buscarAsiento(){
      viajeSeleccionado.addItem(seleccionarAnio.getSelectedItem() + "/" 
          + seleccionarMes.getSelectedItem() + "/" + 
          seleccionarDia.getSelectedItem());
  }
  
  /*
   * Muestra los asientos para un viaje dado
   */
  public void verAsientos(){
      GregorianCalendar fecha;
            int dia;
            int mes;
            int anio;
            boolean encontrado = false;
            for (int i = 0; i < oficina.ViajesTotales() && !encontrado; i++){
                fecha = viajes[i].getFecha();
                dia = fecha.get(Calendar.DAY_OF_MONTH);
                mes = fecha.get(Calendar.MONTH);
                anio = fecha.get(Calendar.YEAR);
                if((seleccionarAnio.getSelectedItem().equals(anio)) &&  
                   (seleccionarMes.getSelectedItem().equals(meses[mes-1])) &&
                   (seleccionarDia.getSelectedItem().equals(dia))){               
                    //autobusVista.iniciarVistaPlano(viajes[i]);
                    ponerAutobusVista(viajes[i]);
                    viajeElegido = viajes[i];
                    encontrado = true;
                }
            }
            if(!encontrado){
                JOptionPane.showMessageDialog(null, MENSAJE_NO_ENCONTRADO, 
                null, JOptionPane.INFORMATION_MESSAGE);
            }
  
  }
  
  
  
  public void asignarViajero(){     
      String nombre;
      String DNI;
      nombre = insertarViajero.getText();
      DNI = insertarDNI.getText();
      Viajero viajero = new Viajero (nombre,DNI);
      ponerDatosViajero(nombre,DNI);
      
      oyenteVista.eventoProducido(OyenteVista.Evento.OCUPAR_ASIENTO, viajeElegido.getCodigo(), viajero, asientoSeleccionado.getNumero());
  }
  
  
  private void crearVentanaAsignar(){
      ventanaAsignar = new JFrame();
      JPanel panel = new JPanel();
      textoAsignar = new JTextArea(TEXTO_ASIGNAR);
      
      nombreViajero = new JTextArea(TEXTO_NOMBRE);
      dniViajero = new JTextArea(TEXTO_DNI);
      insertarViajero = new JTextField();
      insertarDNI = new JTextField();
      aceptar = crearBoton(ACEPTAR);

      
      ventanaAsignar.getContentPane().setLayout(new BorderLayout());
      panel.setLayout(new GridLayout(2, 2));
      panel.add(nombreViajero);
      panel.add(insertarViajero);
      panel.add(dniViajero);
      panel.add(insertarDNI);
      ventanaAsignar.getContentPane().add(panel, BorderLayout.CENTER);
      ventanaAsignar.getContentPane().add(textoAsignar, BorderLayout.NORTH);
       ventanaAsignar.getContentPane().add(aceptar, BorderLayout.SOUTH);
      
      ventanaAsignar.setResizable(false);    
      ventanaAsignar.pack();  // ajusta ventana y sus componentes
      ventanaAsignar.setVisible(true);
      ventanaAsignar.setLocationRelativeTo(null);
      

  }
  
  
  /*
   * Sobreescribe actionPerformed de ActionListener
   */
   
  public void actionPerformed(ActionEvent e)  {
      try {      
          notificacionAControl(e.getActionCommand());
      } catch (IOException ex) {
          Logger.getLogger(OficinaVista.class.getName()).log(Level.SEVERE, null, ex);
      }
  }  

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        String evento = pce.getPropertyName();
        switch(evento){
            case OCUPAR:
                if(asientoSeleccionado.isEsAsiento()){
               
                }
                asientoSeleccionado.ocuparAsiento((Viajero) pce.getNewValue());
                break;
                
            case DESOCUPAR:
                asientoSeleccionado.desocuparAsiento();
                break;
        }
    }
}