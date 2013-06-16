/* file:        502-Canale.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.2
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-18  LDV  Prima stesura
 * 1.1  2013-05-26  LDV  Adatto a RTCMulti v1.2
 * 1.2  2013-06-06  GF  Rimuovo il concetto di tipo
 */

/** Classe astrazione per la connessione con un altro Client. Essa utilizza un
destinatario e un ptsocket di tipo PT.CONTROLLER.S.WebSocket.  */
PT.CONTROLLER.S.Canale = new Class({
    ptws: null,
    destinatari: {},
    inAttesa: {},
    tipo: null, /* video / audio / data */
    porta: null,
    //
/**  inizializza gli attributi dell'oggetto */
    initialize: function() {
        this.ptws = PT.CONTROLLER.getInstanceDispatcher().getSocket();
        var rcan = PT.CONTROLLER.S.getInstanceRegistroCanali();
        this.porta = rcan.getPortaLibera();
        rcan.aggiungi(this.porta, this);
    },
    //
/**  elimina la porta corrente da RegistroCanali */
    _disattiva: function() {
        PT.CONTROLLER.S.getInstanceRegistroCanali().elimina(this.porta);
    },
    //
/**  restituisce la porta */
    getIdCanale: function() {
        return this.porta;
    },
    //
/**  restituisce l'array destinatari */
    getDestinatari: function() {
        return Object.keys(this.destinatari);
    },
    //
/**  restituisce inAttesa */
    getInAttesa: function() {
        return Object.keys(this.inAttesa);
    },
    //
    // Metodi che sarebbero astratti
/**  reperisce informazioni quando un destinatario è stato aggiunto */
    _destinatarioAggiunto: function() {
    },
    //
/**  gestisce le informazioni sul fatto che il canale sia attivo o meno  */
    _canaleAperto: function(attivo) {
    },
    //
/**  trova il tipo del segnale */
    _getTipo: function() {
    },
    //
/**  genera l'Evento Segnale e lo invia  */
    _inviaEventoSegnale: function(segnale) {
        var e = {
            tipo: "Segnale",
            evento: segnale
        };
        this.ptws.invia(e);
    },
    //
/**  richiede l'invio dell'Evento Segnale a tutti gli elementi nell'array destinatari
*/
    inviaSegnaleADestinatari: function(sottotipo, segnale) {
        var that = this;
        Object.keys(that.destinatari).forEach(function(k) {
            var s = {
                destinatario: k,
                porta: that.destinatari[k],
                sottotipo: sottotipo,
                segnale: segnale
            };
            that._inviaEventoSegnale(s);
        });
    },
    //
/**  richiede l'aggiunta di un utente alla lista dei destinatari */
    richiediAggiuntaDestinatario: function(destinatario) {
        if (/@/.test(destinatario))
            this.inAttesa[destinatario] = [];
        var s = {
            destinatario: destinatario,
            porta: 0,
            sottotipo: "RichiestaAggiunta",
            segnale: {
                partecipanti: this.destinatari,
                tipo: this._getTipo(),
                portaSrc: this.porta
            }
        };
        this._inviaEventoSegnale(s);
    },
    //
/**  accetta l'aggiunta dell'utente alla lista dei destinatari */
    _accettaRichiesta: function(eventoOrig) {
        this.destinatari = eventoOrig.segnale.partecipanti;
        this.destinatari[eventoOrig.mittente] = eventoOrig.segnale.portaSrc;
        this._destinatarioAggiunto();
        var s = {
            destinatario: eventoOrig.mittente,
            porta: eventoOrig.segnale.portaSrc,
            sottotipo: "Accettazione",
            segnale: {
                portaSrc: this.porta
            }
        };
        this._inviaEventoSegnale(s);
        this._canaleAperto(false);
    },
    //
/**  rifiuta l'aggiunta dell'utente alla lista dei destinatari  */
    _rifiutaRichiesta: function(eventoOrig) {
        var s = {
            destinatario: eventoOrig.mittente,
            porta: eventoOrig.segnale.portaSrc,
            sottotipo: "Rifiuto",
            segnale: {}
        };
        this._inviaEventoSegnale(s);
        this._disattiva();
    },
    // astratto
/**  è un metodo astratto che chiama uno dei due precedenti metodi in base alle
scelte dell'utente */
    _gestisciRichiestaAggiunta: function(evento) {
    },
    //
/**  gestisce il rifiuto di un utente alla sua immissione nella lista dei destinatari
*/
    _gestisciRifiuto: function(evento) {
        if ((Object.keys(this.destinatari).length === 0) &&
                (Object.keys(this.inAttesa).length <= 1)) {
            this._disattiva();
        }
        PT.VIEW.getInstanceUpdater().
                notificaEsito('Chiamata rifiutata',
                "L'utente " + evento.mittente + " ha rifiutato.");
    },
    //
    _gestisciAccettazione: function(evento) {
        //mando a lui quelli connessi nel frattempo
        var destArr = this.inAttesa[evento.mittente];
        if (destArr)
            for (var i = 0; i < destArr.length; i++) {
                var e = {
                    destinatario: evento.mittente,
                    porta: evento.segnale.portaSrc,
                    sottotipo: "Aggiunta",
                    segnale: {
                        partecipante: destArr[i],
                        porta: this.destinatari[destArr[i]]
                    }
                };
                this._inviaEventoSegnale(e);
            }
        delete this.inAttesa[evento.mittente];

        // salvo il nuovo partecipante per dirlo a quelli attualmente in sospeso
        destArr = Object.keys(this.inAttesa);
        for (var i = 0; i < destArr.length; i++) {
            this.inAttesa[destArr[i]].push(evento.mittente);
        }

        //informo quelli già connessi del nuovo partecipante
        this.inviaSegnaleADestinatari("Aggiunta", {
            partecipante: evento.mittente,
            porta: evento.segnale.portaSrc
        });

        //aggiorno i dati locali
        this.destinatari[evento.mittente] = evento.segnale.portaSrc;
        this._destinatarioAggiunto();
        if (Object.keys(this.destinatari).length === 1) {
            this._canaleAperto(true);
        }
    },
    //
/**  gestisce l'aggiunta del destinatario */
    _gestisciAggiunta: function(evento) {
        assert(this.destinatari[evento.mittente]);
        // altrimenti mittente che non è del canale
        this.destinatari[evento.segnale.partecipante] = evento.segnale.porta;
        this._destinatarioAggiunto();
    },
    // astratto
    _gestisciPayload: function(evento) {
    },
    //
/**  gestisce la rimozione di un destinatario */
    _gestisciAbbandono: function(evento) {
        delete this.destinatari[evento.mittente];
        if (!Object.keys(this.destinatari).length) {
            this._disattiva();
        }
    },
    //
/**  crea un oggetto di tipo evento (con destinatario, segnale e sottotipo impostati
correttamente) e lo invia tramite la ptsocket */
    invia: function(payload) {
        this.inviaSegnaleADestinatari("Payload", payload);
    },
    //
/**  riceve un evento e informa il suo Observer */
    ricevi: function(evento) {
        var that = this;
        var o = {
            RichiestaAggiunta: function(e) {
                that._gestisciRichiestaAggiunta(e);
            },
            Rifiuto: function(e) {
                that._gestisciRifiuto(e);
            },
            Accettazione: function(e) {
                that._gestisciAccettazione(e);
            },
            Aggiunta: function(e) {
                that._gestisciAggiunta(e);
            },
            Payload: function(e) {
                that._gestisciPayload(e);
            },
            Abbandono: function(e) {
                that._gestisciAbbandono(e);
            }
        };
        var x = o[evento.sottotipo];
        assert(x);
        x(evento);
    },
    //
/**  disconnette il Client dal Server eliminando la connessione dal registroCanali
*/
    abbandona: function() {
        this.inviaSegnaleADestinatari("Abbandono", {});
        this.destinatari = {};
        this._disattiva();
    }
});
