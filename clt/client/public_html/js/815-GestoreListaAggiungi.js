/* file:        815-GestoreListaAggiungi.js
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

/** Classe che permette di aggiungere un amico alla lista amici dell’utente. */
PT.CONTROLLER.G.GestoreListaAggiungi = new Class({
/** Viene aggiunto alRegistro del client il nuovo amico. */
    esegui: function(evento) {
        var reg = PT.MODEL.getInstanceRegistro();
        reg.rmPendente(evento.email);
        reg.setUtente(evento.email, evento.stato, evento.username);
    }
});

PT.CONTROLLER.getInstanceDispatcher().
        aggiungiGestore("ListaAggiungi", new PT.CONTROLLER.G.GestoreListaAggiungi());
