/* file:        NotificaListaAggiungi.java
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
 * Indica al Client di aggiungere un utente al suo registro.
 */
public class NotificaListaAggiungi extends Notifica {

    private String email;
    private String username;
    private String stato;

    /**
     * Restituisce il tipo della Notifica.
     * @return Restituisce una stringa contenente il tipo della notifica
     */
    @Override
    public String getTipo() {
        return "ListaAggiungi";
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

    /**
     * Restituisce lo stato attuale.
     * @return lo stato attuale
     */
    public String getStato() {
        return stato;
    }

    /**
     * Imposta lo stato attuale.
     * @param stato lo stato attuale
     */
    public void setStato(String stato) {
        this.stato = stato;
    }
}
