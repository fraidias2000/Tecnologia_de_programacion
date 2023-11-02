/*
 *David Ros y Alvaro Fraidias
 *Prototipo 5
 *10/05/2020 
 * 
 */
package Control;

import modelo.Viajero;

/*
 *  Interfaz de oyente para recibir eventos de la interfaz de usuario
 */
public interface OyenteVista {
   public enum Evento { OCUPAR_ASIENTO, DESOCUPAR_ASIENTO, SALIR }
  
   /**
    *  Llamado para notificar un evento de la interfaz de usuario
    */ 
   public void eventoProducido(Evento evento, int idViaje, Viajero viajero,
                               int numeroAsiento);
    
}
