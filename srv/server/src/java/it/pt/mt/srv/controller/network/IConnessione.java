/* file:        IConnessione.java
 * package:     it.pt.mt.srv.controller.network
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-14  TF  Prima stesura
 */
package it.pt.mt.srv.controller.network;

/**
 * Interfaccia per una generica connessione
 */
public interface IConnessione {

    /**
     * Invia il messaggio passato come parametro.
     *
     * @param messaggio la stringsa contenente il messaggio
     */
    void send(String messaggio);

    /**
     * Imposta la mail di colui che ha fatto la richiesta legata alla
     * connessione.
     *
     * @param Email la mail da impostare
     */
    void setEmail(String Email);

    /**
     * Restituisce la mail legata alla connessione.
     *
     * @return la stringa contenente la mail legata alla connessione
     */
    String getEmail();

    /**
     * Restituisce l'ip di colui che ha fatto la richiesta legata alla
     * connessione.
     *
     * @return l'ip legato alla connessione
     */
    String getIP();

    /**
     * L'interfaccia del Subject.
     */
    @SuppressWarnings("PublicInnerClass")
    public interface ISubject {

        /**
         * Restituisce la connessione legata al Subject.
         *
         * @return la connessione
         */
        IConnessione getConnessione();
    }
}
