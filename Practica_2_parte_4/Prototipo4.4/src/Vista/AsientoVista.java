/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.4
 *10/05/2020 
 * 
 */

package Vista;


import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import modelo.Posicion;
import modelo.Viajero;

public class AsientoVista extends JLabel {
    private final String DATOS_VIAJERO = "Por favor rellene "
                                         + "los siguientes datos:";
    private final String NOMBRE_VIAJERO = "Nombre:";
    private final String DNI_VIAJERO = "Dni:";
    private final String ACEPTAR = "Aceptar";
    private final int ANCHURA = 400;
    private Viajero viajero;
    private final int ALTURA = 150;
    private Posicion posicion;
    private OficinaVista oficina;
    private int numero;
    private boolean ocupado = false; 
    private String nombreViajero = "";
    private String DNIViajero = "";
    private boolean esAsiento = false;
    
    private static final Color COLOR_SELECCIONADO = Color.YELLOW;
    private Color colorNoSeleccionado;
    private boolean seleccionado = false;
    private static final Color COLOR_OCUPADO = Color.CYAN;

    public AsientoVista(OficinaVista oficina, Posicion posicion, 
                        int numero, boolean recibeEventosRaton) {
        this.posicion = posicion;
        this.oficina = oficina;
        this.numero = numero;
        habilitar(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        
        if (recibeEventosRaton) {
            recibirEventosRaton();
        }  
    }

    /*
    * Devuelve el numero del asiento
    */
    public int getNumero() {
        return numero;
    }

   /*
    * Devuelve si un asiento esta ocupado
    */
    public boolean isOcupado() {
        return ocupado;
    }

   /*
    * Devuelve el viajero
    */
    public Viajero getViajero() {
        return viajero;
    }
   /*
    * Devuelve si es un asiento
    */
    public boolean isEsAsiento() {
        return esAsiento;
    }   
    
    /*
     * Habilita el asiento
     */
    public void habilitar(boolean habilitacion) {
        setEnabled(habilitacion);
    }
  
    /*
    * Devuelve la posición del asiento
    */
    public Posicion devuelvePosicion() {
        return posicion;
    } 
  
   /*
    * Pone numero de asiento
    */    
    public void ponerNumero(int numero) {
        setText(String.valueOf(numero));
        this.numero = numero;
        esAsiento = true;
    }
  
   /*
    * Recibe los eventos de ratón
    */  
    private void recibirEventosRaton() {
        addMouseListener(new MouseAdapter() { 
            @Override
            public void mousePressed(MouseEvent e) {     
                AsientoVista asiento = (AsientoVista)e.getSource();                                 
                oficina.seleccionarAsiento(asiento);     
            }
            @Override
            public void mouseEntered(MouseEvent e){     
                if(ocupado){  
                    AsientoVista asiento = (AsientoVista)e.getSource();
                    oficina.verViajero(asiento);
                }      
            }
        });    
    }
  
   /*
    * Selecciona asiento
    */
    public void seleccionar() {
        if(esAsiento){
            seleccionado = true;
            setBackground(COLOR_SELECCIONADO);
        }
    }   
  
   /*
    * Quita selección asiento
    */
    public void deseleccionar() {
        seleccionado = false;
        if (! ocupado) {
            setBackground(colorNoSeleccionado);
        }else {
            setBackground(COLOR_OCUPADO);  
        }
    }  

   /*
    * Ocupa un asiento
    */
    public void ocuparAsiento(Viajero viajero) {
        if(esAsiento){
            this.viajero = viajero;
            ocupado = true;
            setBackground(COLOR_OCUPADO);  
        }
    }  
  
  /*
   * Desocupa un asiento
   */
    public void desocuparAsiento() {
        ocupado = false;
        viajero = null;
        setBackground(colorNoSeleccionado);    
    }  
  
   /*
    * Inicia asiento vista
    */
    public void iniciar() {
        ponerTexto("");  
        desocuparAsiento();    
        deseleccionar();
    }  

   /*
    * Pone texto
    */
    public void ponerTexto(String texto) {
        setText(texto);
    }  
}
