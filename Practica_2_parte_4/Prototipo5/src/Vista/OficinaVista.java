/*
 *David Ros y Alvaro Fraidias
 *Prototipo 5
 *10/05/2020 
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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.Asiento;
import modelo.Viajero;

public class OficinaVista implements ActionListener, 
        PropertyChangeListener {
    /** Constantes para redimensionamiento */
    private static final int MARGEN_HORIZONTAL = 50;
    private static final int MARGEN_VERTICAL = 100;
    private static final int FILAS_ASIGNACION_VISTA = 5;
    private static final int COLUMNAS_ASIGNACION_VISTA = 20;
    public static final int NUMERO_FILAS = 5;
    public static final int NUMERO_COLUMNAS = 5;
    //Texto en castellano
    private final String ASIGNAR = "ASIGNAR";
    private final String DESASIGNAR = "DESASIGNAR";
    private final String GENERAR = "GENERAR HOJA VIAJE";
    private final String BUSCAR = "BUSCAR VIAJES";
    private final String TEXTOVIAJE = "VIAJE:";
    private final String VER_ASIENTOS = "VER ASIENTOS";
    private final String MENSAJE_NO_ENCONTRADO = "No existe un viaje "
                                                 + "para tal fecha";
    private final String TEXTO_ASIGNAR = "Introduce el nombre y "
                                         + "DNI del viajero";
    private final String TEXTO_NOMBRE = "Nombre: ";
    private final String TEXTO_DNI = "DNI: ";
    private final String ACEPTAR_ASIGNAR = "Aceptar";
    //Texto en ingles
    private final String ASSIGN = "ASSIGN"; 
    private final String DEALLOCATE = "DEALLOCATE";
    private final String GENERATE = "GENERATE TRIP SHEET"; 
    private final String SEARCH = "SEARCH TRIPS"; 
    private final String TRIPS = "TRIPS:"; 
    private final String SEE_SEATS = "SEE_SEATS"; 
    private final String MESSAGE_NOT_FOUND  = "There is no trip "
                                              + "for that date"; 
    private final String ASSIGN_TEXT = "Enter the traveler's name and ID"; 
    private final String TEXT_NAME = "Name: "; 
    private final String TEXT_DNI = "DNI: "; 
    private final String ACCEPT_ASSIGN = "Accept";

    private static final String SELECCIONAR_IDIOMA = "Elige un idioma";
    private static final String ACEPTAR_IDIOMA = "Aceptar idioma";
    private static final String CASTELLANO = "Castellano";
    private static final String INGLES = "Ingles";
    private static final Object[] idiomas = {CASTELLANO, INGLES};
  
    public static final String OCUPAR = "Ocupar";
    public static final String DESOCUPAR = "Desocupar";
    
    private static final String RUTA_RECURSOS = "/Recursos/";
    private static final String ICONO_APLICACION = "autobus.png";

    private static final Object [] anios = {2020, 2021, 2022, 
                                            2023, 2024, 2025};
    private static final Object[] meses = {"Enero", "Febrero", "Marzo", 
        "Abril","Mayo", "Junio", "Julio", "Agosto", "Septiembre", 
        "Octubre", "Noviembre", "Diciembre"};
    private static final Object[] meses_ingles = {"January", "February",
        "March","April", "May", "June", "July", "August", "September",
        "October","November",  "December"};
 
    private JComboBox seleccionarAnio ;
    private JComboBox seleccionarMes;
    private JComboBox seleccionarDia;
    private JComboBox viajeSeleccionado;
    private JComboBox listadoIdiomas;
  
    private JPanel panelNorteVentanaPrincipal;
    private JPanel panelAsientosVentanaPrincipal;
    private JPanel panelSurVentanaPrincipal;
    private JPanel panelAsignar ;
    private JPanel panelCentralInicio;
  
    private JFrame ventanaPrincipal;
    private JFrame ventanaAsignar;
    private JFrame informacionViajero;
    private JFrame ventanaIdioma;
  
    private JButton verAsientos ;
    private JButton buscarViaje;
    private JButton desasignar;
    private JButton asignar;
    private JButton generar;
    private JButton aceptarVentanaIdiomas;
    private JButton aceptarVentanaAsignar;  
  
    private JTextArea textoAsignar;
    private JTextArea nombreViajero;
    private JTextArea dniViajero;
    private JTextArea texto;
    private JTextArea areaTextoIdiomas;
    private JTextArea datosViajero;
  
    private JTextField insertarViajero;
    private JTextField insertarDNI;
    
     private ImageIcon icono;
     private ImagenInicial miImagen;
  
    private static OficinaVista instancia = null;
    private OyenteVista oyenteVista;
    private Oficina oficina;
    private AutobusVista autobusVista;
    private AsientoVista asientoSeleccionado;
    private Viaje viajeElegido;
    public static String idiomaElegido;
    private Viaje viajes[];

    public OficinaVista(OyenteVista oyenteVista, Oficina oficina)
            throws FileNotFoundException {       
        this.oyenteVista = oyenteVista;
        this.oficina = oficina;
        viajes = new Viaje[oficina.ViajesTotales()]; 
        viajes = oficina.obtenerViajes(viajes);
        crearVentanaIdioma();   
    }
  
   /*
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
    * Crea la ventana donde seleccionar el idioma
    */
    private void crearVentanaIdioma(){
        ventanaIdioma = new JFrame();
        areaTextoIdiomas = new JTextArea(SELECCIONAR_IDIOMA);
        listadoIdiomas = new JComboBox();
        
        panelCentralInicio = new JPanel();
        crearImagenCentral(panelCentralInicio);
        ventanaIdioma.add(panelCentralInicio, BorderLayout.CENTER);
      
        rellenarComboBox(listadoIdiomas, idiomas);
        aceptarVentanaIdiomas = crearBoton(ACEPTAR_IDIOMA);
      
        ventanaIdioma.getContentPane().setLayout(new BorderLayout());
      
        ventanaIdioma.getContentPane().add(areaTextoIdiomas, 
                                           BorderLayout.NORTH);
        ventanaIdioma.getContentPane().add(listadoIdiomas, 
                                           BorderLayout.NORTH);
        
         ventanaIdioma.getContentPane().add(aceptarVentanaIdiomas, 
                                           BorderLayout.SOUTH);
         
        ventanaIdioma.getContentPane().add(panelCentralInicio, 
                                           BorderLayout.CENTER);
        ventanaIdioma.setResizable(false);    
        ventanaIdioma.pack();  // ajusta ventana y sus componentes
        ventanaIdioma.setVisible(true);
        ventanaIdioma.setLocationRelativeTo(null);
   }
   /*
    * Crea la imagen de la pantalla inicial
    */
    public void crearImagenCentral(JPanel panel){
        icono = new ImageIcon(this.getClass()
                .getResource(RUTA_RECURSOS + ICONO_APLICACION));
        miImagen = new ImagenInicial();
        miImagen.setIcon(icono);
        panel.add(miImagen);
    }   
    
    /*
    * Crea una ventana donde aparece toda la informacion de los viajes y la 
    * distribución de los asientos del autobús.
    */
    private void crearVentana(){
        ventanaPrincipal = new JFrame();
        panelNorteVentanaPrincipal = new JPanel();
        panelAsientosVentanaPrincipal = new JPanel();
        panelSurVentanaPrincipal = new JPanel();
    
        ventanaPrincipal.getContentPane().setLayout(new BorderLayout()); 
        panelNorteVentanaPrincipal.setLayout(new GridLayout(2, 4));
        panelAsientosVentanaPrincipal.setLayout(new FlowLayout());
        panelSurVentanaPrincipal.setLayout(new FlowLayout());
  
        ventanaPrincipal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                oyenteVista.eventoProducido(OyenteVista.Evento.SALIR,
                                            0, null, 0);
            }   
        });    
    
        // creamos elementos
        creaMenus(panelNorteVentanaPrincipal);
        ventanaPrincipal.getContentPane().add(panelNorteVentanaPrincipal,
                                              BorderLayout.NORTH);
        //Creamos el plano del autobus
        creaPlano(panelAsientosVentanaPrincipal);
        ventanaPrincipal.getContentPane().add(panelAsientosVentanaPrincipal, 
                                          BorderLayout.CENTER);
        //Creamos botones sur
        crearBotonesSur(panelSurVentanaPrincipal);
        ventanaPrincipal.getContentPane().add(panelSurVentanaPrincipal, 
                                          BorderLayout.SOUTH);   
        ventanaPrincipal.setResizable(false);    
        ventanaPrincipal.pack();
        ventanaPrincipal.setVisible(true);
        ventanaPrincipal.setLocationRelativeTo(null);
    }
  
    /*
    * Crea un menú donde selecionar el viaje
    */
    public void creaMenus(JPanel panelNorte) {
        seleccionarAnio = new JComboBox();
        seleccionarMes = new JComboBox();
        seleccionarDia = new JComboBox();
        viajeSeleccionado = new JComboBox();
        idiomaElegido = (String) listadoIdiomas.getSelectedItem();
        
        switch(idiomaElegido){
            case CASTELLANO:
                texto = new JTextArea(TEXTOVIAJE);
                verAsientos = crearBoton(VER_ASIENTOS);
                buscarViaje = crearBoton(BUSCAR);
                rellenarComboBox(seleccionarMes,meses);
                break;         
            case INGLES:        
                texto = new JTextArea(TRIPS);
                verAsientos = crearBoton(SEE_SEATS);
                buscarViaje = crearBoton(SEARCH);
                rellenarComboBox(seleccionarMes,meses_ingles);
                break; 
        }
        rellenarComboBox(seleccionarAnio,anios);
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
    private void crearBotonesSur(JPanel panelSur){      
        switch(idiomaElegido){
            case CASTELLANO:
                asignar = crearBoton(ASIGNAR);
                generar = crearBoton(GENERAR);
                desasignar = crearBoton(DESASIGNAR);
                break;     
            case INGLES:        
                asignar = crearBoton(ASSIGN);
                generar = crearBoton(GENERATE);
                desasignar = crearBoton(DEALLOCATE);
                break;  
        }
        desasignar.setVisible(false);
        asignar.setVisible(false);
        generar.setVisible(false);
        panelSur.add(desasignar);
        panelSur.add(asignar);
        panelSur.add(generar);
    }
  
   /*
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
    private void creaPlano(JPanel panel) {
       autobusVista = new AutobusVista(this,
                                       AutobusVista.RECIBE_EVENTOS_RATON);
       panel.add(autobusVista);
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
        if(asientoSeleccionado.isEsAsiento())
           asignar.setVisible(true);
         
        if(asientoSeleccionado.isOcupado() || 
          !asientoSeleccionado.isEsAsiento())
            asignar.setVisible(false);
    
        if(asientoSeleccionado.isOcupado()){
            desasignar.setVisible(true);
        }else{
            desasignar.setVisible(false);
        }
    }  
  
   /*
    * Pone texto de un vijaero
    */ 
    private void ponerDatosViajero(String dni, String nombre) {
        nombreViajero.setText(nombre); 
        dniViajero.setText(dni); 
    }
  
    /*
     * Pone los asientos en la vista del autobus
     */
    private void ponerAutobusVista(Viaje viaje){
        autobusVista.ponerAsientos(viaje);
    }
  
  /*
   * Rellena el JComboBox dias
   */
    private void rellenarDias(JComboBox lista){
        for (int i = 1; i <= 31; i++) {
            lista.addItem(i);   
        }
    }
  
    /*
    * Rellena un JcomboBox con un vector dado
    */
    private void rellenarComboBox(JComboBox lista, Object vector[]){ 
        for (int i = 0; i < vector.length; i++) {
            lista.addItem(vector[i]);   
        }
    }

   /*
    * Recibe notificación de un evento de la interfaz de usuario
    */
    private void notificacionAControl(String evento) throws IOException{
        switch(evento) {
            case BUSCAR:
                buscarAsiento();
                break;  
            case SEARCH:
                buscarAsiento();
                break;   
            case VER_ASIENTOS :
                verAsientos();
                break;    
            case SEE_SEATS:
                verAsientos(); 
                break;    
            case ASIGNAR:    
                crearVentanaAsignar();                  
                break;    
            case ASSIGN:    
                crearVentanaAsignar();                  
                break;     
            case ACEPTAR_ASIGNAR:
                asignarViajero();
                ventanaAsignar.dispose();
                break;  
            case ACCEPT_ASSIGN:
                asignarViajero();
                ventanaAsignar.dispose();
                break;    
            case DESASIGNAR:     
                desasignarViajero();             
                break;    
            case DEALLOCATE:
                desasignarViajero();  
                break; 
            case GENERAR:
                oficina.generarHoja(viajeElegido.getCodigo());
                break;  
            case GENERATE:
                oficina.generarHoja(viajeElegido.getCodigo());
                break;     
            case ACEPTAR_IDIOMA:
                crearVentana();
                break;
        }
    }
    
    /*
     * Asigna un viajero a un asiento
     */
    private void asignarViajero(){     
        String nombre;
        String DNI;
        nombre = insertarViajero.getText();
        DNI = insertarDNI.getText();
        Viajero viajero = new Viajero (nombre,DNI);
        ponerDatosViajero(nombre,DNI);
      
        oyenteVista.eventoProducido(OyenteVista.Evento.OCUPAR_ASIENTO,
            viajeElegido.getCodigo(), viajero,
            asientoSeleccionado.getNumero());
  }
  
   /*
    * Desasigna un viajero de un asiento
    */
    private void desasignarViajero(){  
        oyenteVista.eventoProducido(OyenteVista.Evento.DESOCUPAR_ASIENTO, 
              viajeElegido.getCodigo(), null, 
              asientoSeleccionado.getNumero());
    }
    /*
     * Muestra los datos de un viajero
     */
    public void verViajero(AsientoVista asiento){
        Viajero viajero = asiento.getViajero();
        crearVentanaDatosViajero(viajero);    
    }
    /*
     * Crea una ventana con los datos del viajero
     */
    private void crearVentanaDatosViajero(Viajero viajero){
       datosViajero = new JTextArea();
       
        switch(idiomaElegido){
            case CASTELLANO:
               datosViajero.setText(viajero.toStringCastellano());
               break;
            case INGLES:
               datosViajero.setText(viajero.toStringIngles());
               break;     
       }
       informacionViajero = new JFrame();
       informacionViajero.getContentPane().setLayout(new BorderLayout());
       informacionViajero.getContentPane().add(datosViajero,
                                               BorderLayout.NORTH);
       informacionViajero.setResizable(false);    
       informacionViajero.pack();  // ajusta ventana y sus componentes
       informacionViajero.setVisible(true);
       informacionViajero.setLocationRelativeTo(null);
    }
  
   /*
    * Busca los asientos para un viaje dado
    */
    private void buscarAsiento(){
        viajeSeleccionado.addItem(seleccionarAnio.getSelectedItem() + "/" + 
        seleccionarMes.getSelectedItem() + "/" + 
        seleccionarDia.getSelectedItem());
    }
  
   /*
    * Muestra los asientos para un viaje dado
    */
    private void verAsientos(){
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
            switch(idiomaElegido){
                case CASTELLANO:
                    if((seleccionarAnio.getSelectedItem().equals(anio)) &&  
                       (seleccionarMes.getSelectedItem()
                        .equals(meses[mes-1])) &&
                       (seleccionarDia.getSelectedItem().equals(dia))){               
                            ponerAutobusVista(viajes[i]);
                            viajeElegido = viajes[i];
                            encontrado = true;
                    }
                    break; 
                case INGLES :
                    if((seleccionarAnio.getSelectedItem().equals(anio)) &&  
                       (seleccionarMes.getSelectedItem()
                        .equals(meses_ingles[mes-1])) &&
                       (seleccionarDia.getSelectedItem().equals(dia))){               
                            ponerAutobusVista(viajes[i]);
                            viajeElegido = viajes[i];
                            encontrado = true;     
                    }
                    break;
            }  
        }
        if(!encontrado){
            JOptionPane.showMessageDialog(null, MENSAJE_NO_ENCONTRADO, 
            null, JOptionPane.INFORMATION_MESSAGE);
        }
        if(viajeElegido != null){
            generar.setVisible(true);
        }
    }

   /*
    * Crea una ventana para introducir los datos del viajero
    */
    private void crearVentanaAsignar(){
        ventanaAsignar = new JFrame();
        panelAsignar = new JPanel();
        insertarViajero = new JTextField();
        insertarDNI = new JTextField();
      
        switch(idiomaElegido){
            case CASTELLANO:
                textoAsignar = new JTextArea(TEXTO_ASIGNAR);
                nombreViajero = new JTextArea(TEXTO_NOMBRE);
                dniViajero = new JTextArea(TEXTO_DNI);
                aceptarVentanaAsignar = crearBoton(ACEPTAR_ASIGNAR);
                break;
            case INGLES:
                textoAsignar = new JTextArea(ASSIGN_TEXT);
                nombreViajero = new JTextArea(TEXT_NAME);
                dniViajero = new JTextArea(TEXT_DNI);
                aceptarVentanaAsignar = crearBoton(ACCEPT_ASSIGN);              
                break;   
        }
        ventanaAsignar.getContentPane().setLayout(new BorderLayout());
        panelAsignar.setLayout(new GridLayout(2, 2));
        panelAsignar.add(nombreViajero);
        panelAsignar.add(insertarViajero);
        panelAsignar.add(dniViajero);
        panelAsignar.add(insertarDNI);
        ventanaAsignar.getContentPane().add(panelAsignar, 
                                            BorderLayout.CENTER);
        ventanaAsignar.getContentPane().add(textoAsignar, 
                                            BorderLayout.NORTH);
        ventanaAsignar.getContentPane().add(aceptarVentanaAsignar, 
                                            BorderLayout.SOUTH);
        ventanaAsignar.setResizable(false);    
        ventanaAsignar.pack();
        ventanaAsignar.setVisible(true);
        ventanaAsignar.setLocationRelativeTo(null);
    }
  
  /*
   * Sobreescribe actionPerformed de ActionListener
   */
    public void actionPerformed(ActionEvent e)  {
        try {      
            notificacionAControl(e.getActionCommand());
        }catch (IOException ex) {
            Logger.getLogger(OficinaVista.class.getName()).log(Level.SEVERE,
                                                               null, ex);
        }
    }  
    
   /*
    * Sobreescribe propertyChange para recibir cambios en modelo
    */  
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        String evento = pce.getPropertyName();
        switch(evento){
            case OCUPAR:
                if(asientoSeleccionado.isEsAsiento())
                asientoSeleccionado
                .ocuparAsiento((Viajero) pce.getNewValue());
                break;   
            case DESOCUPAR:
                asientoSeleccionado.desocuparAsiento();
                break;
        }
    }
}