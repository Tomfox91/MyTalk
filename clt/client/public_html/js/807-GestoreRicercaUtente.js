/* file:        807-GestoreRicercaUtente.js
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

/** Classe che si occupa di comunicare al Server il pattern con cui effettuare la
ricerca. */
PT.CONTROLLER.G.GestoreRicercaUtente = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("RicercaUtente", this.esegui);
    },
/** Viene creato un evento per comunicare al Server il pattern con cui effettuare
la ricerca */
    esegui: function() {
        var e = {
            tipo: "RicercaUtenti",
            evento: {
                pattern: document.getElementById("cercaAmiciInput").value
            }
        };

        PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
    }
});


new PT.CONTROLLER.G.GestoreRicercaUtente();

