/* file:        504-CanaleAV.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-06  TF  Prima stesura
 */

/** gestisce il canale audio e video e si interfaccia con la classe chatAV */
PT.CONTROLLER.S.CanaleAV = new Class({
    Extends: PT.CONTROLLER.S.CanaleRTC,
    tipo: null,
    getStats: [],
    stats: {timestamp: 0},
    contStream: 0,
    intervalID: null,
    alreadyLeft: false,
    windowObject: null,
    proprioId: null,
    //
    // Override
/**  imposta tipo della comunicazione */
    _setTipo: function(tipo) {
        assert(tipo === 'video' || tipo === 'audio');
        this.tipo = tipo;
    },
    //
    // Override
/**  restituisce il tipo della comunicazione */
    _getTipo: function() {
        assert(this.tipo);
        return this.tipo;
    },
    //
    //
/**  inizializza gli attributi dell'oggetto */
    initialize: function(tipo) {
        this.parent();
        this.proprioId = PT.MODEL.getInstanceRegistro().getProprioId();
        var rcan = PT.CONTROLLER.S.getInstanceRegistroCanali();
        if (tipo) {
            assert(!rcan.getCanaleVideo());
            rcan.setCanaleVideo(this.porta);
            this._setTipo(tipo);
        }
    },
    //
    // Override
/**  chiama o accettaRichiesta o rifiutaRichiesta in base alle scelte dell'utente
*/
    _gestisciRichiestaAggiunta: function(evento) {
        if (PT.CONTROLLER.S.getInstanceRegistroCanali().getCanaleVideo()) {
            this._rifiutaRichiesta(evento);
        } else {
            var that = this;
            PT.CONTROLLER.S.getInstanceRegistroCanali().setCanaleVideo(this.porta);
            PT.VIEW.getInstanceUpdater().richiestaChiamata({
                tipo: evento.segnale.tipo,
                partecipanti: Object.keys(evento.segnale.partecipanti).
                        concat([evento.mittente]),
                callback: function(scelta) {
                    if (scelta) {
                        that._setTipo(evento.segnale.tipo);
                        that._accettaRichiesta(evento);
                    } else {
                        that._rifiutaRichiesta(evento);
                    }
                }
            });
        }
    },
    //
    // Override
/**  chiamato quando è disponibile una funzione per le statistiche */
    _newStats: function(getStats) {
        this.getStats.push(getStats);
    },
    //
    // Override
/**  aggiunge dinamicamente i metodi alla connessione */
    _preparaConnessione: function(connection) {
        var that = this;

        connection.onopen = function() {
        };

        that.stats.initTime = new Date();
        that._avviaStatistiche();

        connection.onstream = function(stream) {
            var id;
            var loc = (stream.type === 'local');
            var audio;

            if (stream.session && stream.session.isAudio()) {
                audio = true;
            } else {
                audio = false;
            }

            if (loc) {
                id = '_media_local';
            } else {
                id = '_media_' + stream.userid;
                that.contStream++;
            }

            that.windowObject = PT.VIEW.getCreatore().creaFinestra({
                elem: stream.mediaElement,
                locale: loc,
                id: id,
                canale: that.getIdCanale(),
                tipo: (audio ? 'audio' : 'video')
            });
            that.windowObject.addEvent('chiudi', function() {
                that.chiudi();
            });
            that.windowObject.addEvent('aggiungi', function(destinatario) {
                that.richiediAggiuntaDestinatario(destinatario);
            });
            that.windowObject.addEvent('richiestaDestinatari', function() {
                that.windowObject.setDestinatari(that.getDestinatari());
            });
        };

        connection.onleave = function(userid) {
            var id = '_media_' + userid;
            if (that.windowObject.rimuoviStream(id)) {
                that.contStream--;
                if (!that.contStream) {
                    that.chiudi(true);
                }
            }
        };

        connection.onmessage = function(data) {
            if (data.ping) {
                connection.send({pong: data.ping, id: data.id});
            } else if (data.pong && data.id === that.proprioId) {
                that.stats.roundTrip = (new Date()) - new Date(data.pong);
            }
        };
    },
    //
/**  metodo chiamato per avviare il calcolo delle statistiche  */
    _avviaStatistiche: function() {
        var that = this;

        function report() {
            var s = that.stats;
            if (!s.lastTimestamp || !s.roundTrip) {
                return;
            }
            var res = ({
                sent: Math.round(s.bytes / 1000), //kB
                rate: ((s.bytes - s.lastBytes) /
                        (s.timestamp - s.lastTimestamp)).toPrecision(4), //kBps
                roundTrip: s.roundTrip, //ms
                time: Math.round((new Date() - s.initTime) / 1000) //s
            });
            that.windowObject.setStatistiche(res);
        }

        that.intervalID = setInterval(function() {
            that.connectionSend({ping: new Date(), id: that.proprioId});

            that.stats.lastTimestamp = that.stats.timestamp;
            that.stats.lastBytes = that.stats.bytes;
            that.stats.bytes = 0;

            var mancanti = that.getStats.length;
            var timeoutFuoriTempo = setTimeout(function() {
                mancanti = 9007199254740992;
                report();
            }, 200);
            for (var i = 0; i < that.getStats.length; i++) {
                that.getStats[i](function(giro) {
                    if (giro) {
                        if (giro.timestamp > that.stats.timestamp)
                            that.stats.timestamp = giro.timestamp;
                        that.stats.bytes += giro.bytesSent;
                    }
                    mancanti--;
                    if (!mancanti) {
                        clearTimeout(timeoutFuoriTempo);
                        report();
                    }
                });
            }
        }, 1000);
    },
    //
/**  metodo chiamato per chiudere il calcolo delle statistiche  */
    _arrestaStatistiche: function() {
        clearInterval(this.intervalID);
    },
    // Override
/**  interrompe la conversazione */
    chiudi: function(chiamataInterna) {
        this.contBeacon = 0;
        if (!chiamataInterna) { //chiamato dall'esterno
            this._arrestaStatistiche();
            this._getConnection().leave();
            this.windowObject.rimuovi();
            if (!this.contStream)
                this.abbandona();
        } else { //chiamato dalla onleave
            if (this.alreadyLeft) { //questa funzione è stata chiamata prima
                this.abbandona();
            } else { //prima chiamata: tutti gli altri si sono disconnessi
                this._arrestaStatistiche();
                this._getConnection().leave();
                this.windowObject.rimuovi();
                //non chiamo abbandona
            }
        }
        this.alreadyLeft = true;
    }
});
