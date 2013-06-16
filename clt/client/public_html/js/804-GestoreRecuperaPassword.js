/* file:        804-GestoreRecuperaPassword.js
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

/** Classe che si occupa di comunicare la domanda di sicurezza al Server. */
PT.CONTROLLER.G.GestoreRecuperaPassword = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("RecuperaPassword", this.esegui);
    },
/** Viene creato un evento per comunicare la risposta alla domanda di sicurezza e
la nuova password */
    esegui: function() {
        var e = {
            tipo: "RecuperaPassword",
            evento: {
                risposta: document.getElementById("risp").value,
                nuova: document.getElementById("pwdn").value,
                email: document.getElementById("lstem").value
            }
        };

        PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
    }
});


new PT.CONTROLLER.G.GestoreRecuperaPassword();


