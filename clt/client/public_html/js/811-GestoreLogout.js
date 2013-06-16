/* file:        811-GestoreLogout.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-24  GF  Prima stesura
 */

/** Classe che si occupa della gestione del logout. */
PT.CONTROLLER.G.GestoreLogout = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("Logout", this.esegui);
    },
/** Viene creato l'oggetto per effettuare il logout */
    esegui: function() {
        var e = {
            tipo: "Logout",
            evento: {}
        };
        PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
        (new PT.VIEW.GestoreAccount()).logout();
    }
});


new PT.CONTROLLER.G.GestoreLogout();

