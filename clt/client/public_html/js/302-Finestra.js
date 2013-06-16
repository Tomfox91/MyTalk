/* file:        302-Finestra.js
 * package:     PT.VIEW
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-15  TF  Prima stesura
 */

/** Classe che rappresenta una generica finestra. */
PT.VIEW.Finestra = new Class({
    channel: null,
    tipo: null,
/** metodo che aggiunge il tasto di chiusura della finestra stessa. */
    cbutton: function(name, that) {
        return new Element('button', {
            'class': 'close',
            'html': '&nbsp; &times;',
            'href': '#' + name,
            events: {
                click: function() {
                    that.fireEvent('chiudi');
                    ;
                }
            }
        });
    }

});
