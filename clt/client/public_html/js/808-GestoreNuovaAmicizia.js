/* file:        808-GestoreNuovaAmicizia.js
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

/** Classe che informa il Server dell'accettazione o rifiuto di un'amicizia pendente.
*/
PT.CONTROLLER.G.GestoreNuovaAmicizia = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("NuovaAmicizia", this.esegui);

    },
/** Viene creato un evento per comunicare al Server che è stata accetta o rifiutata
un'amicizia pendente e viene aggiunto l'utente in questione nella tab degli amici
oppure eliminato dalla tab delle richieste di amicizie pendenti */
    esegui: function(Email) {
        var e = {
            tipo: "NuovaAmicizia",
            evento: {
                email: Email
            }
        };

        PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
    }
});


new PT.CONTROLLER.G.GestoreNuovaAmicizia();
