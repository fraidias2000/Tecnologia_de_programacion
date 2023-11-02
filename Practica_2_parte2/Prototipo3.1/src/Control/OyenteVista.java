/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 * @author Usuario
 */
public interface OyenteVista {
   public enum Evento { OCUPAR_ASIENTO, DESOCUPAR_ASIENTO, SALIR }
  
   /**
    *  Llamado para notificar un evento de la interfaz de usuario
    */ 
   public void eventoProducido(Evento evento, Object obj);
    
}
