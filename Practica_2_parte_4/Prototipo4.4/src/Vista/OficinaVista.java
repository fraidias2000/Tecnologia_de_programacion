/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.4
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import modelo.Oficina;
import modelo.Viaje;
import Control.OyenteVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.Tupla;
import modelo.Viajero;

public class OficinaVista implements ActionListener, PropertyChangeListener {
    /** Constantes para redimensionamiento */
    public static final int NUMERO_FILAS = 5;
    public static final int NUMERO_COLUMNAS = 5;
    private static final int ANIO_MAXIMO = 2050;
    
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
    public static final String OCUPAR = "Ocupar";
    public static final String DESOCUPAR = "Desocupar";
    private static final String MENSAJE_ERROR ="No se puede dejar el campo"
                                            + " de nombre o dni en blanco";
    
    
    
    /*CONSTANTES VISTA WRAPPER*/
    private final String VACIO = "" ;
    private final String CONECTAR = "CONECTAR";
    private final String USERID = "USERID";
    private final String PASSWORD = "PASSWORD";
    
    private static final Object[] meses = {"Enero", "Febrero", "Marzo", 
        "Abril","Mayo", "Junio", "Julio", "Agosto", "Septiembre", 
        "Octubre", "Noviembre", "Diciembre"};
 
    private JComboBox seleccionarAnio ;
    private JComboBox seleccionarMes;
    private JComboBox seleccionarDia;
    private JComboBox viajeSeleccionado;
  
    private JPanel panelNorteVentanaPrincipal;
    private JPanel panelCentralVentanaPrincipal;
    private JPanel panelSurVentanaPrincipal;
    private JPanel panelAsignar ;
  
    private JFrame ventanaPrincipal;
    private JFrame ventanaAsignar;
    private JFrame informacionViajero;
  
   
    private JButton desasignar;
    private JButton asignar;
    private JButton generar;
    private JButton aceptarVentanaAsignar;  
  
    private JTextArea textoAsignar;
    private JTextArea nombreViajero;
    private JTextArea dniViajero;
    private JTextArea texto;
    private JTextArea datosViajero;
    
    
    private JTextArea userId;        
    private JTextArea password;       
    private JTextField introducirUserId;          
    private JTextField introducirPassword;      
    private JButton conectar;
  
    private JTextField insertarViajero;
    private JTextField insertarDNI;
  
    private static OficinaVista instancia = null;
    private OyenteVista oyenteVista;
    private Oficina oficina;
    private AutobusVista autobusVista;
    private AsientoVista asientoSeleccionado;
    private Viaje viajeElegido;

    public OficinaVista(OyenteVista oyenteVista, Oficina oficina)
            throws IOException{       
        this.oyenteVista = oyenteVista;
        this.oficina = oficina;
        crearVentana();   
    }
  
   /*
    * Devuelve la instancia de la vista del tablero
    * 
    */        
    public static synchronized OficinaVista devolverInstancia(
                             OyenteVista oyenteVista, Oficina oficina)
                             throws IOException {
        if (instancia == null){
            instancia = new OficinaVista(oyenteVista, oficina);
        }
            return instancia;
    } 
    
    /*
    * Crea una ventana donde aparece toda la informacion de los viajes y la 
    * distribución de los asientos del autobús.
    */
    private void crearVentana(){
        ventanaPrincipal = new JFrame();
        panelNorteVentanaPrincipal = new JPanel();
        panelCentralVentanaPrincipal = new JPanel();
        panelSurVentanaPrincipal = new JPanel();
    
        ventanaPrincipal.getContentPane().setLayout(new BorderLayout()); 
        panelNorteVentanaPrincipal.setLayout(new GridLayout());
        panelCentralVentanaPrincipal.setLayout(new FlowLayout());
        panelSurVentanaPrincipal.setLayout(new FlowLayout());
  
        ventanaPrincipal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                oyenteVista.eventoProducido(OyenteVista.Evento.SALIR, null );
            }   
        });    
    
        // creamos elementos
        creaMenus(panelNorteVentanaPrincipal);
        ventanaPrincipal.getContentPane().add(panelNorteVentanaPrincipal,
                                              BorderLayout.NORTH);
        //Creamos el plano del autobus
        creaPlano(panelCentralVentanaPrincipal);
        ventanaPrincipal.getContentPane().
                         add(panelCentralVentanaPrincipal, 
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
        /*seleccionarAnio = new JComboBox();
        seleccionarMes = new JComboBox();
        seleccionarDia = new JComboBox();
        viajeSeleccionado = new JComboBox();*/
        
        userId = new JTextArea(USERID);
        userId.setEditable(false);
        
        password = new JTextArea(PASSWORD);
        password.setEditable(false);
        
        introducirUserId = new JTextField(VACIO);       
        
        introducirPassword = new JTextField(VACIO);
        
        conectar = crearBoton(CONECTAR);
      
        panelNorte.add(userId);
        panelNorte.add(introducirUserId);
        panelNorte.add(password);
        panelNorte.add(introducirPassword); 
        panelNorte.add(conectar);
        
    } 
    
   /*
    * Crea el panel sur
    */
    private void crearBotonesSur(JPanel panelSur){      
        asignar = crearBoton(ASIGNAR);
        generar = crearBoton(GENERAR);
        desasignar = crearBoton(DESASIGNAR);    
  
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
   * Rellena el JComboBox dias
   */
    private void rellenarAnyios(JComboBox lista){
        for (int i = 2020; i <= ANIO_MAXIMO; i++) {
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
   * Sobreescribe actionPerformed de ActionListener
   */
    public void actionPerformed(ActionEvent e)  {
        try {      
            notificacionAControl(e.getActionCommand());
        }catch (IOException ex) {
            Logger.getLogger(OficinaVista.class.getName()).
                             log(Level.SEVERE,null, ex);
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
            case VER_ASIENTOS :
                verAsientos();
                break;                  
            case ASIGNAR:    
                crearVentanaAsignar();                  
                break;                   
            case ACEPTAR_ASIGNAR:
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
    
    /*
     * Asigna un viajero a un asiento
     */
    private void asignarViajero(){     
        String nombre;
        String DNI;
        nombre = insertarViajero.getText();
        DNI = insertarDNI.getText();
        if ( nombre.equals(VACIO) || DNI.equals(VACIO)){
            JOptionPane.showMessageDialog(null, MENSAJE_ERROR);
        }else{
            Viajero viajero = new Viajero (nombre,DNI);
            ponerDatosViajero(nombre,DNI);
            
            oyenteVista.eventoProducido(OyenteVista.Evento.OCUPAR_ASIENTO, 
                                        new Tupla<Integer ,Viajero,Integer>(
                                        viajeElegido.getCodigo(), viajero,
                                        asientoSeleccionado.getNumero()));     
        }       
  }
  
   /*
    * Desasigna un viajero de un asiento
    */
    private void desasignarViajero(){  
        oyenteVista.eventoProducido(OyenteVista.Evento.DESOCUPAR_ASIENTO,
                                    new Tupla<Integer ,Viajero,Integer>(
                                    viajeElegido.getCodigo(), null, 
                                    asientoSeleccionado.getNumero()));
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
       datosViajero.setText(viajero.toString());
       datosViajero.setEditable(false);
                                
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
        dia = (int)seleccionarDia.getSelectedItem();
        mes = cambiarMesANumero((String)seleccionarMes.getSelectedItem());
        anio = (int)seleccionarAnio.getSelectedItem();
        fecha = new GregorianCalendar(anio, mes, dia);
        
        viajeElegido = oficina.obtenerViaje(fecha);
        
        
        if(viajeElegido == null){
            JOptionPane.showMessageDialog(null, MENSAJE_NO_ENCONTRADO, 
            null, JOptionPane.INFORMATION_MESSAGE);
        }
        
        if(viajeElegido != null){
            generar.setVisible(true);
            ponerAutobusVista(viajeElegido);
        }
    }
    
    /*
    * Cambia el formato al mes, de String a int
    */
    private int cambiarMesANumero(String mes){
        int numeroMes = 0;
        for (int i = 0; i < meses.length; i++) {
            if(meses[i].equals(mes)){
                numeroMes=++i;
            }
        }
        return numeroMes;
    }

   /*
    * Crea una ventana para introducir los datos del viajero
    */
    private void crearVentanaAsignar(){
        ventanaAsignar = new JFrame();
        panelAsignar = new JPanel();
        insertarViajero = new JTextField();
        insertarDNI = new JTextField();
      
        textoAsignar = new JTextArea(TEXTO_ASIGNAR);
        nombreViajero = new JTextArea(TEXTO_NOMBRE);
        dniViajero = new JTextArea(TEXTO_DNI);
        aceptarVentanaAsignar = crearBoton(ACEPTAR_ASIGNAR);
        
        textoAsignar.setEditable(false);
        nombreViajero.setEditable(false);
        dniViajero.setEditable(false);
        
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
    * Sobreescribe propertyChange para recibir cambios en modelo
    */  
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        String evento = pce.getPropertyName();
        switch(evento){
            case OCUPAR:
                if(asientoSeleccionado.isEsAsiento()){
                    asientoSeleccionado.ocuparAsiento
                    ((Viajero) pce.getNewValue());
                }
                break;   
            case DESOCUPAR:
                asientoSeleccionado.desocuparAsiento();
                break;
        }
    }
}