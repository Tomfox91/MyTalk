
/* file:        303-CreatoreFinestre.js
 * package:     PT.VIEW
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-15  GF  Prima stesura
 * 2.0  2013-04-16  GF  aggiunta getCreatore
 */

/** Classe che crea le finestre necessarie alla visualizzazione della chat testuale,
audio e audiovideo. */
PT.VIEW.CreatoreFinestre = new Class({
    creaFinestra: function(a) {
        var chat;
        $('body').css('cursor', 'auto');
        switch (a.tipo) {
            case 'data':
                chat = this.newChat(a);
                break;
            default:
                chat = this.newAVChat(a);
                break;
        }
        return chat;
    },

/** metodo utilizzato per la creazione di una nuova finestra html per la chiamata
audio e video. In essa saranno presenti dei pulsanti per alcune funzionalità quali,
l’aggiunta di un nuovo interlocutore o la visualizzazione delle statistiche. */
    newAVChat: function(a) {
        return new PT.VIEW.AVChat(a);
    },

/** metodo utilizzato per la creazione di una nuova finestra per la comunicazione
via chat. */
    newChat: function(chi) {
        return new PT.VIEW.Chat(chi);
    }
});

PT.VIEW.getCreatore = function() {
    var creatore;
    return function() {
        if (!creatore) {
            creatore = new PT.VIEW.CreatoreFinestre();
        }

        return creatore;
    };
}();
