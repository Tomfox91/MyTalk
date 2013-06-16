/* file:        824-GestoreProprioIP.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-05-27  TF  Prima stesura
 */

/** Classe che si occupa di impostare l’ip dell’utente. */
PT.CONTROLLER.G.GestoreProprioIP = new Class({
/** Viene impostato l’ip indicato dall’evento iniziale. */
    esegui: function(evento) {
        PT.MODEL.getInstanceRegistro().setProprioIP(evento.ip);
    }
});

PT.CONTROLLER.getInstanceDispatcher().
        aggiungiGestore("ProprioIP", new PT.CONTROLLER.G.GestoreProprioIP());
