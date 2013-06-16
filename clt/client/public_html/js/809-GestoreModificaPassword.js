/* file:        809-GestoreModificaPassword.js
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

/** Classe che si occupa di comunicare la modifica della password. */
PT.CONTROLLER.G.GestoreModificaPassword = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("ModificaPassword", this.esegui);

    },
/** Viene creato un evento per notificare le modifica della password */
    esegui: function() {
        var e = {
            tipo: "ModificaPassword",
            evento: {
                vecchia: document.getElementById("inputOldPsw").value,
                nuova: document.getElementById("inputNewPsw").value
            }
        };

        PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
    }
});


new PT.CONTROLLER.G.GestoreModificaPassword();


