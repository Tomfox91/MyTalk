/* file:        NotificaListaElimina.java
 * package:     it.pt.mt.srv.controller.notifiche
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-15  TF  Prima stesura
 */
package it.pt.mt.srv.controller.notifiche;
/**
 * Indica al Client di eliminare un utente dal suo registro.
 */
public class NotificaListaElimina extends Notifica {

    private String email;

    /**
     * Restituisce il tipo della Notifica.
     * @return Restituisce una stringa contenente il tipo della notifica
     */
    @Override
    public String getTipo() {
        return "ListaElimina";
    }
    /**
     * Restituisce l'email.
     * @return l'email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email.
     * @param email l'email da inserire nella notifica
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
