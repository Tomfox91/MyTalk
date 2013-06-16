/* file:        812-GestoreMessaggioSegreteria.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-24  TF  Prima stesura
 */

/** Classe che informa il Server della presenza di un messaggio di segreteria da
inviare. */
PT.CONTROLLER.G.GestoreMessaggioSegreteria = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("MessaggioSegreteria", this.esegui);
    },
/** Viene comunicato al Server il messaggio di segreteria per l'utente selezionato
*/
    esegui: function() {



        var e = {
            tipo: "MessaggioSegreteria",
            evento: {
                destinatario: $("#msgbody p").text().split(": ")[1],
                messaggio: $("#msgbody textarea").val()
            }
        };

        PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
    }
});


new PT.CONTROLLER.G.GestoreMessaggioSegreteria();

