/* file:        805-GestoreRichiestaDomandaSicurezza.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-24  JC  Prima stesura
 */

/** Classe che si occupa di richiedere la domanda di sicurezza per effettuare il
recupero password. */
PT.CONTROLLER.G.GestoreRichiestaDomandaSicurezza = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("RichiestaDomandaSicurezza", this.esegui);
    },
/** Viene creato un evento per ottenere la domanda di sicurezza */
    esegui: function() {
        var e = {
            tipo: "RichiestaDomandaSicurezza",
            evento: {
                email: document.getElementById("lstem").value
            }
        };

        PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
    }
});


new PT.CONTROLLER.G.GestoreRichiestaDomandaSicurezza();


