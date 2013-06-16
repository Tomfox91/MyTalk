/* file:        NotificaNuovaRichiestaAmicizia.java
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
 * Indica che l'utente ha una nuova di amicizia pendente.
 */
public class NotificaNuovaRichiestaAmicizia extends Notifica {

    private String email;
    private String username;

    /**
     * Restituisce il tipo della Notifica.
     * @return Restituisce una stringa contenente il tipo della notifica
     */
    @Override
    public String getTipo() {
        return "NuovaRichiestaAmicizia";
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
     * @param username lo username da impostare
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
