/* file:        501-RegistroCanali.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.1
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-18  TF  Prima stesura
 * 1.1  2013-06-11  TF  Aggiunta funzione di avviso all'utente quando si chiude la finestra
 */

/** Classe che rappresenta un Singleton, esso serve per tenere traccia di tutte
le istanza di Canale a partire dai loro interlocutori. */
PT.CONTROLLER.S.getInstanceRegistroCanali = function() {
    var instance;
    return function() {
        if (!instance) {
            var RegistroCanali = new Class({
                registro: {},
                ultimaPorta: 0,
                canaleVideo: null,
                getPortaLibera: function() {
                    this.ultimaPorta++;
                    return this.ultimaPorta;
                },
                getCanaleVideo: function() {
                    return this.canaleVideo;
                },
                setCanaleVideo: function(ch) {
                    this.canaleVideo = ch;
                },
/**  aggiungi al registro l'associazione interlocutore-canale */
                aggiungi: function(porta, canale) {
                    this.registro[porta] = canale;
                },
/**  ricerca il canale per la comunicazione con quell'interlocutore */
                cerca: function(porta) {
                    return this.registro[porta];
                },
/**  rimuove l'associazione tra canale e interlocutore */
                elimina: function(porta) {
                    if (this.canaleVideo === porta)
                        this.canaleVideo = null;
                    delete this.registro[porta];
                }
            });

            instance = new RegistroCanali();

            window.onbeforeunload = function() {
                return (Object.keys(instance.registro).length ?
                        "Hai una o più connessioni attive. Chiudile prima di cambiare pagina." :
                        null);
            };

        }

        return instance;
    };
}();
