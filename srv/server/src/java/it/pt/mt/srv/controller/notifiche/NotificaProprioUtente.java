/* file:        NotificaProprioUtente.java
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
 * Indica al Client la presenza di modifiche al utente con cui è connesso.
 */
public class NotificaProprioUtente extends Notifica {

    private String email;
    private String username;
    private String stato;

    /**
     * Restituisce il tipo della Notifica.
     * @return Restituisce una stringa contenente il tipo della notifica
     */
    @Override
    public String getTipo() {
        return "ProprioUtente";
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
     * @return la stringa contenente lo username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Imposta lo username.
     * @param username lo username da impostare
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Restituisce lo stato.
     * @return la stringa che rappresenta lo stato
     */
    public String getStato() {
        return stato;
    }

    /**
     * Imposta lo stato.
     * @param stato lo stato da impostare
     */
    public void setStato(String stato) {
        this.stato = stato;
    }
}
