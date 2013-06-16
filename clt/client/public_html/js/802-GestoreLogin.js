/* file:        802-GestoreLogin.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-22  JC  Prima stesura
 */

/** Classe che ha la responsabilità di effettuare il login. */
PT.CONTROLLER.G.GestoreLogin = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("Login", this.esegui);
    },
/** Viene creato l'oggetto per effettuare il login */
    esegui: function() {
        var e = {
            tipo: "Login",
            evento: {
                email: document.getElementById("email").value,
                password: document.getElementById("pass").value
            }
        };

        PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
    }
});


new PT.CONTROLLER.G.GestoreLogin();
