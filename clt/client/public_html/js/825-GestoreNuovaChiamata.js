/* file:        825-GestoreNuovaChiamata.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-05-27  GF  Prima stesura
 */

/** Classe che informa il Server di una richiesta di chiamata. */
PT.CONTROLLER.G.GestoreNuovaChiamata = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("NuovaChiamata", this.esegui);
    },
/** Viene inviata la richiesta di effettuare una connessione WebRTC per la
comunicazione con l'utente richiesto */
    esegui: function(a) {
        function nuovoCanale(tipo) {
            if (tipo === 'data') {
                return new PT.CONTROLLER.S.CanaleT();
            } else {
                return new PT.CONTROLLER.S.CanaleAV(tipo);
            }
        }

        var ch = nuovoCanale(a.tipo);
        if ($('[name="unomolti"]:checked').val() === "MultiChat") {
            for (var j = 0; j < a.destinatario.length; j++) {
                ch.richiediAggiuntaDestinatario(a.destinatario[j].value);
            }
        } else {
            ch.richiediAggiuntaDestinatario(a.destinatario);
        }
    }
});

new PT.CONTROLLER.G.GestoreNuovaChiamata();
