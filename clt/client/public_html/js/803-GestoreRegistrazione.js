/* file:        803-GestoreRegistrazione.js
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

/** Classe che ha la responsabilità di effettuare la registrazione di un nuovo
utente. */
PT.CONTROLLER.G.GestoreRegistrazione = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("Registrazione", this.esegui);
    },
/** Viene creato l'oggetto da inviare al WebSocket per effettuare la registrazione
*/
    esegui: function() {
        var e = {
            tipo: "Registrazione",
            evento: {
                email: document.getElementById("emailR").value,
                username: document.getElementById("user").value,
                password: document.getElementById("psw").value,
                domanda: document.getElementById("domanda").value,
                risposta: document.getElementById("risposta").value

            }
        };

        PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
    }
});


new PT.CONTROLLER.G.GestoreRegistrazione();
