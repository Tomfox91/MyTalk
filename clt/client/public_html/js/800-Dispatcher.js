/* file:        800-Dispatcher.js
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

/** Classe che rappresenta un Singleton contenente tutti i gestori degli eventi.
*/
PT.CONTROLLER.getInstanceDispatcher = function() {
    var instance;
    return function() {
        if (!instance) {
            var Dispatcher = new Class({
                socket: null,
                gestori: {},
                initialize: function() {
                    var that = this;

                    var loc = window.location;
                    var sok_uri = (loc.protocol === "https:" ?
                            "wss://" : "ws://");
                    sok_uri += loc.host + "/mytalk/socket";

                    this.socket = new PT.CONTROLLER.S.
                            WebSocket(sok_uri);
                    this.socket.addEvent("messaggio", function(contenitoreEvento) {
                        that.dispatch(contenitoreEvento);
                    });
                },
/** restituisce il socket in uso */
                getSocket: function() {
                    return this.socket;
                },
/** associa l'evento al gestore che lo cattura */
                aggiungiGestore: function(nomeEvento, gestore) {
                    this.gestori[nomeEvento] = gestore;
                },
/** alla ricezione di un evento cerca tra i gestori se ce n'è uno in grado di
catturare quell'evento, e in caso lo lancia, in caso contrario stampa nella console
un messaggio di errore */
                dispatch: function(contenitoreEvento) {
                    var gestore = this.gestori[contenitoreEvento.tipo];
                    if (typeof gestore !== "object") {
                    } else {
                        gestore.esegui(contenitoreEvento.evento);
                    }
                }
            });

            instance = new Dispatcher();
        }

        return instance;
    };
}();
