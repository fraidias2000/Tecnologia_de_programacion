/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */

package Vista;

import Control.Agencia;
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
import Control.OyenteVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.Asiento;
import modelo.Oficina;
import modelo.Sixtupla;
import modelo.Tripla;
import modelo.Tupla;
import modelo.Viaje;
import modelo.Viajero;

public class OficinaVista implements ActionListener, 
        PropertyChangeListener {
    /** Constantes para redimensionamiento */
    public static final int NUMERO_FILAS = 5;
    public static final int NUMERO_COLUMNAS = 5;
    private static final int ANIO_MAXIMO = 2050;
    private static final String NOMBRE_VIAJERO = "Nombre: ";
    private static final String DNI_VIAJERO = "Dni: ";
    private static final String EXITO_GENERAR_HOJA = "Exito al generar la hoja de viaje";
    
    private final String ASIGNAR = "ASIGNAR";
    private final String DESASIGNAR = "DESASIGNAR";
    private final String GENERAR = "GENERAR HOJA VIAJE";
    private final String BUSCAR_VIAJES = "BUSCAR_VIAJES";
    private final String TEXTOVIAJE = "VIAJE:";
    private final String VER_ASIENTOS = "VER ASIENTOS";
    private final String TEXTO_ASIGNAR = "Introduce el nombre y "
                                         + "DNI del viajero";
    private final String TEXTO_NOMBRE = "Nombre: ";
    private final String TEXTO_DNI = "DNI: ";
    private final String ACEPTAR_ASIGNAR = "Aceptar";
    public static final String OCUPAR = "Ocupar";
    public static final String DESOCUPAR = "Desocupar";
    public static final String CONECTADO = "Conectado";
    private static final String MENSAJE_ERROR ="No se puede dejar el campo"
                                            + " de nombre o dni en blanco";
    private final String VACIO = "" ;
    public static final String ERROR_CONFIGURACION_NO_GUARDADA =
            "Error al guardar configuración";
    public static final String ERROR_CONEXION_SERVIDOR =
            "Error en la conexion con el servidor";
    private static final String ERROR_GENERAR_HOJA = 
            "Error generar hoja";
    private static final String ERROR_VER_ASIENTOS = 
            "Error al crear asientos";
    private static final String ERROR_ASIGNAR_VIAJERO = 
            "Error desasignar viajero";
    private static final String ERROR_DESASIGNAR_VIAJERO = 
            "Error desasignar viajero";
    private static final String ERROR_BUSCAR_VIAJE = 
            "Error al buscar viaje";
    private static final String ERROR_CERRAR_VENTANA = 
            "Error al cerrar la ventana";
    private static final Object[] meses = {"Enero", "Febrero", "Marzo", 
        "Abril","Mayo", "Junio", "Julio", "Agosto", "Septiembre", 
        "Octubre", "Noviembre", "Diciembre"};
    
    private int codigoViaje;
 
    private JComboBox seleccionaAnio ;
    private JComboBox seleccionaMes;
    private JComboBox seleccionaDia;
    private JComboBox viajesDisponibles;
  
    private JPanel panelNorteVentanaPrincipal;
    private JPanel panelAsientosVentanaPrincipal;
    private JPanel panelSurVentanaPrincipal;
    private JPanel panelAsignar ;
  
    private JFrame ventanaPrincipal;
    private JFrame ventanaAsignar;
    private JFrame informacionViajero;
  
    private JButton verAsientos ;
    private JButton buscarViaje;
    private JButton desasignar;
    private JButton asignar;
    private JButton generar;
    private JButton aceptarVentanaAsignar;  
  
    private JTextArea textoAsignar;
    private JTextArea nombreViajero;
    private JTextArea dniViajero;
    private JTextArea texto;
    private JTextArea datosViajero;
    private JTextArea conectado;
  
    private JTextField insertarViajero;
    private JTextField insertarDNI;
  
    private static OficinaVista instancia = null;
    private OyenteVista oyenteVista;
    private Oficina oficina;
    private AutobusVista autobusVista;
    private AsientoVista asientoSeleccionado;
    private GregorianCalendar fechaViaje;
    private ArrayList<Integer> dias = new ArrayList<>();
    

    public OficinaVista(OyenteVista oyenteVista, Oficina oficina) {       
        this.oyenteVista = oyenteVista;
        this.oficina = oficina;
        crearVentana();   
    }

    /*
    * Devuelve el codigo del viaje
    * 
    */
    public int getCodigoViaje() {
        return codigoViaje;
    }
    
    
  
   /*
    * Devuelve la instancia de la vista del tablero
    * 
    */        
    public static synchronized OficinaVista devolverInstancia(
                             OyenteVista oyenteVista, Oficina oficina)
                             {
        if (instancia == null)
            instancia = new OficinaVista(oyenteVista, oficina);
            return instancia;
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
        panelNorteVentanaPrincipal.setLayout(new FlowLayout());
        panelAsientosVentanaPrincipal.setLayout(new FlowLayout());
        panelSurVentanaPrincipal.setLayout(new FlowLayout());
  
        ventanaPrincipal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {          
                try {
                    oyenteVista.eventoProducido(OyenteVista.Evento.
                            SALIR, null);
                } catch (Exception ex) {
                    ponerMensaje(ERROR_CERRAR_VENTANA);
                }           
            }   
        });    
    
        // creamos elementos
        creaMenus(panelNorteVentanaPrincipal);
        ventanaPrincipal.getContentPane().add(panelNorteVentanaPrincipal,
                                              BorderLayout.NORTH);
        //Creamos el plano del autobus
        creaVistaAsientos(panelAsientosVentanaPrincipal);
        ventanaPrincipal.getContentPane().
                         add(panelAsientosVentanaPrincipal, 
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
        seleccionaAnio = new JComboBox();
        seleccionaMes = new JComboBox();
        seleccionaDia = new JComboBox();
        viajesDisponibles = new JComboBox();
        
        texto = new JTextArea(TEXTOVIAJE);
        conectado = new JTextArea(CONECTADO);
        texto.setEditable(false);
        verAsientos = crearBoton(VER_ASIENTOS);
        buscarViaje = crearBoton(BUSCAR_VIAJES);
        
        rellenarComboBox(seleccionaMes,meses);         
        rellenarAnyios(seleccionaAnio);
        rellenarDias(seleccionaDia);

        panelNorte.add(seleccionaAnio);
        panelNorte.add(seleccionaMes);
        panelNorte.add(seleccionaDia);
        panelNorte.add(buscarViaje); 
        panelNorte.add(texto);
        panelNorte.add(viajesDisponibles);
        panelNorte.add(verAsientos);
        panelNorte.add(conectado);
        conectado.setVisible(false);
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
    private void creaVistaAsientos(JPanel panel) {
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
    public void ponerAutobusVista( List<String>  asientos){
        autobusVista.ponerAsientos(asientos);  
    }
  
  /*
   * Rellena el JComboBox dias
   */
    private void rellenarDias(JComboBox lista){
        int anio = (int) seleccionaAnio.getSelectedItem();
        int mes = (int) seleccionaMes.getSelectedIndex();
        generarDias(mes, anio);
        for (Integer i : dias) {
            lista.addItem(i);
        }
    }
    
    private void generarDias(int mes, int anyo) {       
        dias.removeAll(dias);
        YearMonth anyoMesObj = YearMonth.of(anyo, mes+1);
        int numeroDias = anyoMesObj.lengthOfMonth();
        for (int i = 1; i <= numeroDias; i++) {
            dias.add(i);
        }  
    }
    
   /*
    * Rellena el JComboBox dias
    */
    private void rellenarAnyios(JComboBox lista){
        Calendar cal= Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        for (int i = year; i <= ANIO_MAXIMO; i++) {
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
            case BUSCAR_VIAJES:
                buscarViajes();
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
                generarHoja();
                break;  
        }
    }
    
    /*
    * Genera hoja
    * 
    */
    private void generarHoja(){
    try {
         Scanner scanner = new Scanner(String.valueOf
                         (viajesDisponibles.getSelectedItem()));
        codigoViaje = scanner.nextInt();  
        oyenteVista.eventoProducido(OyenteVista.Evento.
                GENERAR_HOJA_VIAJE, codigoViaje);
        ponerMensaje(EXITO_GENERAR_HOJA);
    } catch (Exception ex) {
        ponerMensaje(ERROR_GENERAR_HOJA);
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
            Viajero viajero = new Viajero (DNI,nombre);
            ponerDatosViajero(DNI,nombre);
            
            try {
                oyenteVista.eventoProducido(OyenteVista.Evento.
                        OCUPAR_ASIENTO, new Tripla<String ,
                                Viajero,
                                Integer>(String.valueOf(codigoViaje),
                               viajero,asientoSeleccionado.getNumero()));
            } catch (Exception ex) {
                System.out.println(ex);
                ponerMensaje(ERROR_ASIGNAR_VIAJERO);
            }
        }       
  }
    
     /*
    * Desasigna un viajero de un asiento
    */
    private void desasignarViajero(){  
        try {
          oyenteVista.eventoProducido(OyenteVista.Evento.
            DESOCUPAR_ASIENTO,  new Tupla<String,String>
            (String.valueOf(codigoViaje), 
            String.valueOf(asientoSeleccionado.getNumero())));
        } catch (Exception ex) {
            ponerMensaje(ERROR_DESASIGNAR_VIAJERO);
        }
    }
    
    /*
     * Se comunica con control para buscar un viaje 
     * en una fecha concreta
     */
    private void buscarViajes() {
        int dia = (int)seleccionaDia.getSelectedItem();
        int mes = (int)seleccionaMes.getSelectedIndex();
        int anyio = (int)seleccionaAnio.getSelectedItem();
        GregorianCalendar fecha = new GregorianCalendar(anyio,mes,dia);

        if (fecha != null) {
            try {
                oyenteVista.eventoProducido(OyenteVista.Evento.
                                            BUSCAR_VIAJES, fecha);
            } catch (Exception ex) {    
                ponerMensaje(ERROR_BUSCAR_VIAJE);
            }
        }    
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
       datosViajero.setText(NOMBRE_VIAJERO + viajero.getNombre() + 
                            " " + DNI_VIAJERO + viajero.getDni());
       datosViajero.setEditable(false);
                                
       informacionViajero = new JFrame();
       informacionViajero.getContentPane().setLayout(new BorderLayout());
       informacionViajero.getContentPane().add(datosViajero,
                                               BorderLayout.NORTH);
       informacionViajero.setResizable(false);    
       informacionViajero.pack();
       informacionViajero.setVisible(true);
       informacionViajero.setLocationRelativeTo(null);
    }
    
    /*
     * Rellena el combobox con los viajes disponibles de una fecha
     */
    public void rellenarViajes(Collection<String> viajes) {
        viajesDisponibles.removeAllItems();
        verAsientos.setEnabled(false);
    
        if (viajes.size() > 0) {  
            for (String viaje: viajes) {
               viajesDisponibles.addItem(viaje);
            }
            viajesDisponibles.setEnabled(true);
            verAsientos.setEnabled(true);
        }
    }
  
   /*
    * Muestra los asientos para un viaje dado
    */
    private void verAsientos(){            
        try {    
            List<String> lista = new ArrayList<String>();
        Scanner scanner = new Scanner(String.valueOf
                         (viajesDisponibles.getSelectedItem()));
        codigoViaje = scanner.nextInt();  
        lista = oficina.obtenerAsientos(codigoViaje);
        ponerAutobusVista(lista);
        generar.setVisible(true);
        
        } catch (Exception ex) {
            ponerMensaje(ERROR_VER_ASIENTOS);
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
    
    public void ponerMensaje(String mensaje) {
        JOptionPane.showMessageDialog(ventanaPrincipal, mensaje, 
        Agencia.VERSION, JOptionPane.INFORMATION_MESSAGE);    
    }
    
   /*
    * Sobreescribe propertyChange para recibir cambios en modelo
    */  
    @Override
    public void propertyChange(PropertyChangeEvent evento) {
        String eventoOcurrido = evento.getPropertyName(); 
        switch(eventoOcurrido){
            case OCUPAR:
                    asientoSeleccionado.ocuparAsiento((Viajero) evento.getNewValue());
                
                break;   
            case DESOCUPAR:
                asientoSeleccionado.desocuparAsiento();
                break;
                
            case CONECTADO:
                conectado.setVisible(true);  
                break;
        }
    }
}