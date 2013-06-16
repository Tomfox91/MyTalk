/* file:        810-GestoreModificaProfilo.js
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

/** Classe che si occupa di comunicare la modifica dei dati personali. */
PT.CONTROLLER.G.GestoreModificaProfilo = new Class({
    initialize: function() {
        PT.VIEW.getInstanceSubjectView().addEvent("ModificaProfilo", this.esegui);
    },
/** Viene creato un evento per notificare le modifiche dei dati personali (username
e stato) */
    esegui: function() {
        var nuovostato = PT.MODEL.getInstanceRegistro().getProprioUtente().getStato();
        var nuovousername = PT.MODEL.getInstanceRegistro().getProprioUtente().getNome();
        var e = {
            tipo: "ModificaProfilo",
            evento: {
                username: nuovousername,
                stato: nuovostato
            }
        };

        PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
    }
});


new PT.CONTROLLER.G.GestoreModificaProfilo();

