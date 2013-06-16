/* file:        500-WebSocket.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-25  TF  Prima stesura
 */

/** Classe che crea una connessione webSocket allo url dato. */
PT.CONTROLLER.S.WebSocket = new Class({
    Implements: Events,
    ws: null,
    buffer: [],
    aperto: false,
    initialize: function(url) {
        this.ws = new WebSocket(url);
        var that = this;
        this.ws.addEventListener("message", function(mess) {
            that.fireEvent("messaggio", JSON.parse(mess.data));
        });
        this.ws.addEventListener("open", function() {
            that.aperto = true;
            for (var i = 0; i < that.buffer.length; i++) {
                that.ws.send(JSON.stringify(that.buffer[i]));
            }
            that.buffer = null;
            that.fireEvent("aperto");
        });
        this.ws.addEventListener("close", function() {
            that.aperto = false;
            that.fireEvent("chiuso");
        });
    },
/** controlla che l'oggetto passato sia di tipo corretto e lo invia come Json */
    invia: function(obj) {
        assert(typeof obj === "object");
        if (this.ws.readyState === 3) { //chiuso
            PT.VIEW.getInstanceUpdater().notificaEsito("Errore di rete",
                    "Controllare la propria connessione di rete e riprovare");
        }

        if (this.aperto) {
            this.ws.send(JSON.stringify(obj));
        } else {
            this.buffer.push(obj);
        }
    },
/** chiude la connessione WebSocket */
    chiudi: function() {
        this.ws.close();
    }
});
