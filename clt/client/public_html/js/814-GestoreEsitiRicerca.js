/* file:        814-GestoreEsitiRicerca.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-22  SB  Prima stesura
 */

/** Classe che si occupa della visualizzazione dell'esito della ricerca di un
utente richiesta precedentemente dal Client */
PT.CONTROLLER.G.GestoreEsitiRicerca = new Class({
    //chiama metdo visualizzaRicerca di Updater della view affinché mostri i
    //risultati della ricerca

/** Viene aggiornata la TabRicerca, tramite l'Updater, con i dati inviati dal
Server */
    esegui: function(evento) {
        /*
         * EVENTO NotificaEsitiRicerca
         * mappa: map<String, String>
         */

        var up = PT.VIEW.getInstanceUpdater();
        up.visualizzaRicerca(evento.mappa);
    }
});

PT.CONTROLLER.getInstanceDispatcher().
        aggiungiGestore("EsitiRicerca", new PT.CONTROLLER.G.GestoreEsitiRicerca());
