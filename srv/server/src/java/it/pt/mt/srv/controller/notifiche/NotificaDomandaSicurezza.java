/* file:        NotificaDomandaSicurezza.java
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
 * Viene inviata la domanda di sicurezza dell'utente. IlClientpuò poi
inviare un EventoRecuperaPassword.
 */
public class NotificaDomandaSicurezza extends Notifica {

    private String domanda;

    /**
     * Restituisce il tipo della Notifica.
     * @return Restituisce una stringa contenente il tipo della notifica
     */
    @Override
    public String getTipo() {
        return "DomandaSicurezza";
    }

    /**
     * Restituisce la domanda segreta.
     * @return la stringa contenente la domanda segreta
     */
    public String getDomanda() {
        return domanda;
    }

    /**
     * Imposta la domanda segreta.
     * @param domanda la stringa contenente il testo della domanda segreta
     */
    public void setDomanda(String domanda) {
        this.domanda = domanda;
    }


}
