/* file:        505-CanaleT.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-06  SC  Prima stesura
 */

/** \todo */
PT.CONTROLLER.S.CanaleT = new Class({
    Extends: PT.CONTROLLER.S.CanaleRTC,
    windowObject: null,
    //
    // Override
/**  restituisce il tipo della comunicazione */
    _getTipo: function() {
        return 'data';
    },
    //
    // Override
/**  invoca accettaRichiesta del Canale */
    _gestisciRichiestaAggiunta: function(evento) {
        this._accettaRichiesta(evento);
    },
    //
    // Override
/**  apre una connessione RTCMultiConnection e se non è già stata creata una
Finestra invoca il CreaFinestra passando i destinatari interessati alla connessione,
definisce come trattare gli oggetti data passati come parametri, se essi contengono
l'attributo leaving invoca eliminaUtente dell'oggetto Finestra e rimuove l'utente
che ha abbandonato la conversazione, in caso contrario invoca il metodo ricevi
dell'oggetto Finestra */
    _preparaConnessione: function(connection) {
        var that = this;

        connection.onUserLeft = function() {
        };

        connection.onopen = function() {
            if (!that.windowObject) {
                that.windowObject = PT.VIEW.getCreatore().creaFinestra({
                    canale: that.getIdCanale(),
                    destinatari: that.getDestinatari().concat(that.getInAttesa()),
                    tipo: 'data'
                });
                that.windowObject.addEvent('chiudi', function() {
                    that.chiudi();
                });
                that.windowObject.addEvent('messaggioUscente', function(mess) {
                    that.mandaMessaggio(mess);
                });
            }
        };

        connection.onmessage = function(data) {
            if (data.leaving) {
                that.windowObject.eliminaUtente(data.leaving);
            } else {
                that.windowObject.ricevi(data);
            }
        };
    },
    //
/**  crea l'oggetto da inviare impostando come attributo mittente il proprio id e
come messaggio il testo da inviare, invoca infine il metodo send della connessione
*/
    mandaMessaggio: function(testo) {
        var data = {
            mittente: PT.MODEL.getInstanceRegistro().getProprioNome(),
            messaggio: testo
        };
        this._getConnection().send(data);
    },
    // Override
/**  invia un oggetto con l'attributo leaving con valore il proprio id invoca il
metodo leave sulla RTCMultiConnection, viene chiusa la Finestra corrispondente e
viene invocato il metodo abbandona di Canale */
    chiudi: function() {
        this.contBeacon = 0;
        this._getConnection().send({
            leaving: PT.MODEL.getInstanceRegistro().getProprioId()
        });
        this._getConnection().leave();
        this.windowObject.rimuovi();
        this.abbandona();
    }
});
