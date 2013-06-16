/* file:        819-GestoreNuovoMessaggio.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-22  GF  Prima stesura
 */

/** Classe che mostra all’utente il messaggio di segreteria. */
PT.CONTROLLER.G.GestoreNuovoMessaggio = new Class({
/** Viene chiamato il metodo visualizzaMessaggioSegreteria della classe Updater.
*/
    esegui: function(evento) {
        PT.VIEW.getInstanceUpdater().visualizzaMessaggioSegreteria(evento.mittente, evento.messaggio);
    }

});

PT.CONTROLLER.getInstanceDispatcher().
        aggiungiGestore("NuovoMessaggio", new PT.CONTROLLER.G.GestoreNuovoMessaggio());
