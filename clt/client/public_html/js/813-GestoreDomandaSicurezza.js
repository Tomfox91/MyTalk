/* file:        813-GestoreDomandaSicurezza.js
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

/** Classe che si occupa di notificare all'Updater la domanda di sicurezza arrivata
dal Server. */
PT.CONTROLLER.G.GestoreDomandaSicurezza = new Class({
    //chiama metodo notificaEsito di Updater affinché la view mostri la domanda
    //di sicurezza arrivata dal server

/** Viene notificata al GestoreAccount la domanda di sicurezza ricevuta dal Server
*/
    esegui: function(evento) {
        /*
         * EVENTO:
         * domanda: String
         */

        var ac = new PT.VIEW.GestoreAccount;
        ac.notificaDomanda(evento.domanda);

    }
});

PT.CONTROLLER.getInstanceDispatcher().
        aggiungiGestore("DomandaSicurezza", new PT.CONTROLLER.G.GestoreDomandaSicurezza());
