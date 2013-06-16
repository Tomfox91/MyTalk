/* file:        817-GestoreListaModifica.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-22  SC  Prima stesura
 */

/** Classe che permette di modificare i dettagli di un utente. */
PT.CONTROLLER.G.GestoreListaModifica = new Class({
/** Vengono modificati nel Registro i dettagli dell’utente. */
    esegui: function(evento) {
        var reg = PT.MODEL.getInstanceRegistro();
        reg.modificaNomeUtente(evento.username, evento.email);
        reg.modificaStatoUtente(evento.stato, evento.email);
    }
});

PT.CONTROLLER.getInstanceDispatcher().
        aggiungiGestore("ListaModifica", new PT.CONTROLLER.G.GestoreListaModifica());
