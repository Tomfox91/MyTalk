/* file:        822-GestoreAmiciziaPendente.js
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

/** Classe che si occupa di dire al client se una richiesta di amicizia è stata
accettata o rifiutata. */
PT.CONTROLLER.G.RispostaAmiciziaPendente = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("rispostaRichiesta", this.esegui);
    },
/** Viene creato un evento che indica se una richiesta di amicizia è stata
accettata o rifiutata. */
    esegui: function(o) {

        var e = {
            tipo: "AccettazioneAmicizia",
            evento: {
                email: o.email,
                accettata: o.esito
            }
        };

        new PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
    }
});

new PT.CONTROLLER.G.RispostaAmiciziaPendente();
