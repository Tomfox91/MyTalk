/* file:        NotificaNuovoMessaggio.java
 * package:     it.pt.mt.srv.controller.notifiche
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-16  TF  Prima stesura
 */
package it.pt.mt.srv.controller.notifiche;
/**
 * Indica al Client la presenza di un nuovo messaggio.
 */
public class NotificaNuovoMessaggio extends Notifica {

    private String mittente;
    private String messaggio;

    /**
     * Restituisce il tipo della Notifica.
     * @return Restituisce una stringa contenente il tipo della notifica
     */
    @Override
    public String getTipo() {
        return "NuovoMessaggio";
    }

    /**
     * Restituisce l'email del mittente.
     * @return l'email del mittente
     */
    public String getMittente() {
        return mittente;
    }

    /**
     * imposta l'email del mittente.
     * @param mittente l'email del mittente da impostare
     */
    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    /**
     * Restituisce il testo del messaggio.
     * @return la stringa contenente il testo del messaggio
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * Imposta il messaggio
     * @param messaggio la stringa contenente il testo del messaggio da impostare
     */
    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }
}
