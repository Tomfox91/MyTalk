/* file:        823-GestoreRimuoviAmico.js
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

/** Classe che si occupa di comunicare che un utente deve essere rimosso dalla
lista degli amici. */
PT.CONTROLLER.G.GestoreRimuoviAmico = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("amicoRimosso", this.esegui);
    },
/** Viene creato un evento per indicare che un utente è stato rimosso e viene
compiuta l’azione stessa. */
    esegui: function(email) {

        var e = {
            tipo: "EliminaAmicizia",
            evento: {
                email: email
            }
        };

        PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
    }



});
new PT.CONTROLLER.G.GestoreRimuoviAmico();
