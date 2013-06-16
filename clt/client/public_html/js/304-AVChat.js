/* file:        304-VChat.js
 * package:     PT.VIEW.VC
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-10  GF  Prima stesura
 * 1.1  2013-04-16  SC  Aggiunta Stream
 * 2.0  2013-04-19  LDV  Correzione ai metodi privati
 */

/** Finestra contenente la chat audio e video. */
PT.VIEW.AVChat = new Class({
    Extends: PT.VIEW.Finestra,
    Implements: Events,
    destinatari: null,
/** metodo che crea una chat audio o audio-video. Mostra una barra di navigazione
contenente tre tasti, uno per l'aggiunta di un nuovo interlocutore solo nel caso
in cui l'utente abbia effettuato il login, uno per la visualizzazione delle
statistiche e uno per la chiusura della conversazione, un elemento che contiene
il proprio video e quello degli interlocutori. Nel caso in cui vi siano anche delle
chat attive esse si ridimensionano posizionandosi al di sotto dei video.  */
    initialize: function(o) {
        this.channel = o.canale;
        this.tipo = o.tipo;
        var that = this;
        o.elem.id = o.id;
        if (o.tipo === "video") {
            o.elem.controls = false;
        }
        if (o.locale) {
            var aggiungi = new Element('button', {
                'html': "Aggiungi",
                'id': "btnAggiungi",
                'class': "btn btn-primary",
                'data-dismiss': "modal",
                events: {
                    click: function() {
                        var destinatario = $("[name='aggiungi']:checked");
                        $('[name="aggiungi"]').attr('checked', false);
                        for (var j = 0; j < destinatario.length; j++) {
                            that.fireEvent('aggiungi', destinatario[j].value);
                        }
                    }}
            });
            var but = this.cbutton("AVCHAT", this);
            but.addClass("pull-right");
            o.elem.muted = true;
            $('#myv').append(o.elem);
            $("#add").find(".modal-footer").append(aggiungi);
            $('#tools').append(but);
            $('#tools').show();

        }
        else {
            $('#vchat').append(o.elem);
            this.getSizeOf();
            $('#myv').show();
        }

        $('#logo').hide();
        $('#tabchat').height($('#tabchat').height() * 0.5);
        $('#vchat').show();
        $(".multy").hide();
        $('#stat').popover({
            placement: 'right',
            html: 'true',
            title: 'Statistiche',
            content: '<ul><li>Dati inviati: 0000 kB</li><li>Velocità attuale: 0000 kBps</li><li>Latenza: 0000 ms</li><li>Durata: 0000 s</li>'

        });
        if (PT.MODEL.getInstanceRegistro().getProprioUtente()) {
            $("#aggiungi").on("click", function() {

                $('#list').empty();
                that.fireEvent("richiestaDestinatari");
                var lista = PT.MODEL.getInstanceRegistro().getListaUtenti();
                var key = $(Object.keys(lista)).not(that.destinatari).get();
                for (var j = 0; j < key.length; j++) {
                    if (lista[key[j]].getStato() !== "Offline") {
                        var utente = '<li id="' + lista[key[j]].getNome().replace(/ /g, "_") + '_lista"><span class="checkbox"><input type="checkbox" name="aggiungi" value="' + key[j] + '"></span>' + lista[key[j]].getNome() + '</li>';
                        $('#list').append(utente);
                    }
                }
            });
            $("#aggiungi").removeAttr("disabled");
        }
        else {
            $("#aggiungi").attr("disabled", "disabled");
        }
    },
/** metodo utilizzato per ridimensionare la video-chat a seconda di un solo
interlocutore o più. */
    getSizeOf: function() {
        if ($('#vchat>' + this.tipo).length > 1) {
            $('#vchat>' + this.tipo).width("47%");
        }
        else {
            $('#vchat>' + this.tipo).width("80%");
        }

    }.protect(),
/** meotodo che rimuove lo stream quando un interlocutore si disconnette. */
    rimuoviStream: function(id) {
        var e = document.getElementById(id);
        this.getSizeOf();
        if (e) {
            e.parentNode.removeChild(e);
            return true;
        } else {
            return false;
        }
    },
    //
/** metodo che imposta le statiste della chiamata. */
    setStatistiche: function(s) {
        $('#tools .popover').find('.popover-content').empty();
        $('#tools .popover').find('.popover-content').append(
                '<ul><li>Dati inviati: ' + s.sent +
                ' kB</li><li>Velocità attuale: ' + s.rate +
                ' kBps</li><li>Latenza: ' + s.roundTrip +
                ' ms</li><li>Durata: ' + s.time + ' s</li>');
    },
    //
/** metodo che imposta i destinatari della conversazione. */
    setDestinatari: function(s) {
        this.destinatari = s;
    },
/** metodo che chiude la finestra della video-chat. Nasconde la barra di navigazione,
il contenitore di cui erano inseriti gli audio e mostra il logo nel caso in cui
non ci siano altre chat aperte. */
    rimuovi: function() {
        $("#vchat " + this.tipo).hide();
        $("#tools button[href='#AVCHAT']").remove()
        $("#vchat").hide();
        $("#myv").hide();
        $(".multy").show();
        $('#tools').hide();
        $("#tabchat").height($('.row-fluid').height());
        $("#add").find("#btnAggiungi").remove();
        if ($("#tabchat").is(":hidden")) {
            $("#logo").show();
        }
        var l = $('#_media_local');
        if (l)
            l.remove();
    }
});
