/* file:        300-SubjectView.js
 * package:     PT.VIEW
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-22  TF  Prima stesura
 */

/** Rappresenta il subject dell'observer della view. */
PT.VIEW.getInstanceSubjectView = function() {
    var instance;
    return function() {
        if (!instance) {
            var SubjectView = new Class({
                Implements: Events

            });

            instance = new SubjectView();
        }

        return instance;
    };
}();
