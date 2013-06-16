/* file:        816-GestoreListaElimina.js
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

/** Classe che permette di rimuovere un amico alla lista amici dell’utente. */
PT.CONTROLLER.G.GestoreListaElimina = new Class({
/** Viene rimosso l’amico dal Registro. */
    esegui: function(evento) {
        var reg = PT.MODEL.getInstanceRegistro();
        reg.rmUtente(evento.email);
    }
});

PT.CONTROLLER.getInstanceDispatcher().
        aggiungiGestore("ListaElimina", new PT.CONTROLLER.G.GestoreListaElimina());
