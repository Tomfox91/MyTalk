/* file:        000-init.js
 * package:     
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-25  TF  Prima stesura
 */

var PT = {
    MODEL: {},
    CONTROLLER: {
        G: {},
        S: {},
        R: {}
    },
    VIEW: {}
};

function assert(cond) {
    if (!cond) {
        throw new Error("Asserzione fallita");
    }
}
