/* file:        801-GestoreSegnale.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-26  TF  Prima stesura
 */

/** Classe che gestisce l'evento di tipo segnale. */
PT.CONTROLLER.G.GestoreSegnale = new Class({
/** ricerca il Canale aperto con il mittente dell'evento, e richiama su di esso
il metodo ricevi(evento) */
    esegui: function(evento) {
        var rcan = PT.CONTROLLER.S.getInstanceRegistroCanali();
        var ch;
        if (evento.porta === 0) {
            assert(evento.sottotipo === 'RichiestaAggiunta'
                    && evento.segnale.tipo);
            ch = this.nuovoCanale(evento);
        } else {
            ch = rcan.cerca(evento.porta);
        }
        if (ch)
            ch.ricevi(evento);
    },
    //
    nuovoCanale: function(evento) {
        if (evento.segnale.tipo === 'data') {
            return new PT.CONTROLLER.S.CanaleT();
        } else {
            return new PT.CONTROLLER.S.CanaleAV();
        }
    }
});

PT.CONTROLLER.getInstanceDispatcher().
        aggiungiGestore("Segnale", new PT.CONTROLLER.G.GestoreSegnale());
