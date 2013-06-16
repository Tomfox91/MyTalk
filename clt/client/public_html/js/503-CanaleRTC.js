/* file:        503-CanaleRTC.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.2
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-18  TF  Prima stesura
 * 1.1  2013-05-26  MD  Adatto a RTCMulti v1.2
 * 1.2  2013-06-06  SB  Rimuovo il concetto di tipo
 */

/** fornisce dei metodi comuni alle sottoclassi per la gestione della comunicazione
WebRTC */
PT.CONTROLLER.S.CanaleRTC = new Class({
    Extends: PT.CONTROLLER.S.Canale,
    connection: null,
    onmessage: {},
    contBeacon: 0,
    //
    // Override
/**  reperisce informazioni quando un destinatario è stato aggiunto */
    _destinatarioAggiunto: function() {
        this.contBeacon = 3;
    },
    //
    // Override
    _gestisciPayload: function(evento) {
        var func = this.onmessage[evento.segnale.subcanale];
        if (func)
            func(evento.segnale.messaggio);
    },
    //astratto
/**  aggiunge dinamicamente i metodi alla connessione  */
    _preparaConnessione: function(connection) {
    },
    //astratto
/**  chiamato quando è disponibile una funzione per le statistiche */
    _newStats: function(getStats) {
    },
    //
/**  restituisce connessione  */
    _getConnection: function() {
        return this.connection;
    },
    //
    // Override
/**  gestisce le informazioni sul fatto che il canale sia attivo o meno  */
    _canaleAperto: function(attivo) {
        var that = this;
        that.connection = new RTCMultiConnection();
        that.connection.openSignalingChannel = function(config) {
            var subcanale = config.channel || this.channel || 'Default-Socket';
            var socket = {};
            socket.channel = subcanale;

            if (config.onopen)
                setTimeout(config.onopen, 1);

            socket.send = function(messaggio) {
                that.send({
                    subcanale: subcanale,
                    messaggio: messaggio
                });
            };

            that.onmessage[subcanale] = config.onmessage;

            if (config.getStats) {
                that._newStats(config.getStats);
            }

            return socket;
        };

        this._preparaConnessione(that.connection);

        that.connection.session = {
            video: 'video audio data',
            audio: 'audio data',
            data: 'data'
        }[this._getTipo()];

        if (attivo) {
            that.connection.open('42');
        } else {
            that.connection.connect('42');
        }
    },
    //
/**  chiama il metodo invia sull'oggetto passato dopo aver controllato che non sia
un Beacon  */
    send: function(data) {
        if (data.messaggio.sessionid && data.messaggio.userid) { //beacon
            if (this.contBeacon > 0) {
                this.contBeacon--;
                this.invia(data);
            }
        } else {
            this.invia(data);
        }
    },
    //
/**  chiama il metodo send passandogli l'oggetto inviato  */
    connectionSend: function(data) {
        this.connection.send(data);
    }
});
