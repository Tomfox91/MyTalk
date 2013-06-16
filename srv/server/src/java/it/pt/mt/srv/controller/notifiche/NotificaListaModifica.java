/* file:        NotificaListaModifica.java
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
 * Indica al Client di modificare un utente del suo registro.
 */
public class NotificaListaModifica extends Notifica {

    private String email;
    private String username;
    private String stato;

    /**
     * Restituisce il tipo della Notifica.
     * @return Restituisce una stringa contenente il tipo della notifica
     */
    @Override
    public String getTipo() {
        return "ListaModifica";
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

    /**
     * Restituisce lo username.
     * @return lo username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Imposta lo username.
     * @param username lo username da inserire nella notifica
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Restituisce lo stato.
     * @return lo stato
     */
    public String getStato() {
        return stato;
    }

    /**
     * Imposta lo stato.
     * @param stato lo stato da inserire nella notifica
     */
    public void setStato(String stato) {
        this.stato = stato;
    }
}
