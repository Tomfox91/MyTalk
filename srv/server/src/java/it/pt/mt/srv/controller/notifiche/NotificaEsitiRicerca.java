/* file:        NotificaEsitiRicerca.java
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

import java.util.Map;

/**
 * Contiene i risultati di una ricerca tra gli utenti. È la risposta ad un
 * EventoRicercaUtente.
 */
public class NotificaEsitiRicerca extends Notifica {

    /**
     * Contiene una mappa.
     */
    private Map<String, String> mappa;

    /**
     * Restituisce il tipo della Notifica.
     *
     * @return Restituisce una stringa contenente il tipo della notifica
     */
    @Override
    public String getTipo() {
        return "EsitiRicerca";
    }

    /**
     * Restituisce la lista di utenti che la ricerca fornisce.
     *
     * @return un map della lista degli utenti
     */
    public Map<String, String> getMappa() {
        return mappa;
    }

    /**
     * Imposta la lista degli utenti.
     *
     * @param mappa la mappa che viene passata alla notifica
     */
    public void setMappa(Map<String, String> mappa) {
        this.mappa = mappa;
    }
}
