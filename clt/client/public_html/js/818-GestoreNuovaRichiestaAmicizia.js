/* file:        818-GestoreNuovaRichiestaAmicizia.js
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

/** Classe che permette di mostrare all’utente una nuova richiesta di amicizia. */
PT.CONTROLLER.G.GestoreNuovaRichiestaAmicizia = new Class({
/** Viene aggiunta al Registro una nuova richiesta di amicizia. */
    esegui: function(evento) {
        var nu = new PT.MODEL.Utente(evento.email, "ndf", evento.username);

        var reg = PT.MODEL.getInstanceRegistro();
        reg.addPendente(nu);
    }
});

PT.CONTROLLER.getInstanceDispatcher().
        aggiungiGestore("NuovaRichiestaAmicizia", new PT.CONTROLLER.G.GestoreNuovaRichiestaAmicizia());
