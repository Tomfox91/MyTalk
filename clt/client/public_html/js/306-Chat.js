/* file:        306-Chat.js
 * package:     PT.VIEW
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-24  SB  Prima stesura
 * 1.1  2013-04-24  SC  Modifiche al costruttore
 * 2.0  2013-04-24  MD  Aggiunta metodi di rimozione
 */

/** Finestra contenente la chat. */
PT.VIEW.Chat = new Class({
    Extends: PT.VIEW.Finestra,
    Implements: Events,
    name: '',
    nomi: [],

/** metodo che crea una nuova chat. Nel caso in cui sia la prima chat, viene
visualizzata nascondendo il logo, altrimenti viene visualizzata in primo piano
indicando uno o più interlocutori. Esso inoltre visualizza una form nella quale è
possibile scrivere il messaggio desiderato da inviare e l'apposito tasto per
l'invio. */
    initialize: function(o) {
        this.channel = o.canale;
        this.tipo = o.tipo;
        this.nomi = o.destinatari;
        var destinatario = "";
        var registro = PT.MODEL.getInstanceRegistro().getListaUtenti();
        var that = this;
        $('#tabchat').show();
        $('#logo').hide();
        $('#chat>li.active').removeClass('active');
        $('#contenuto>div.active').removeClass('active');
        $('#form>form.active').removeClass('active');
        for (var j = 0; j < this.nomi.length; j++) {
            destinatario += (j ? ' ' : '') +
                    (registro[this.nomi[j]] ?
                            registro[this.nomi[j]].getNome() :
                            this.nomi[j]);
            this.name += this.nomi[j];
        }
        this.name = this.name.replace(/[^a-z0-9]+/g, '');
        this.name += new Date().getTime();
        var but = this.cbutton(this.name, this);
        var newli = '<li class="active"><a href="#' + this.name + '" data-toggle="tab" data-target="#' + this.name + ',#' + this.name + '_form"><span class="nome">' + destinatario + '</span><span class="label label-warning notify hide">!</span></a></li>';
        var newcont = '<div class="tab-pane active" id="' + this.name + '"></div>';
        var invia = new Element('button', {
            'class': "btn",
            'html': "Invia",
            'type': "submit",
            events: {
                click: function() {

                    var messaggio = $('#' + that.name + '_form input').val();
                    if (messaggio.replace(/ /g, "") !== "") {
                        var mittente = PT.MODEL.getInstanceRegistro().getProprioNome();
                        $('#' + that.name + '_form input').val("");
                        $("#" + that.name + "").append("<p>" + mittente + ": " + messaggio.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;") + "</p>");
                        $("#contenuto").animate({scrollTop: $('#contenuto').get(0).scrollHeight}, 'slow');
                        that.fireEvent('messaggioUscente', messaggio);
                    }
                }
            }
        });

        var form = '<form id="' + this.name + '_form" class="form-search tab-pane active" onsubmit="return false;"><input class="search-query span11" type="text" maxlength="50"/></form>';
        $('#chat').append(newli);
        $('#chat').find('.active').find('a').append(but);
        $('#contenuto').append(newcont);
        $('#form').append(form);
        $('#' + this.name + '_form').append(invia);
        $("#chat a[href='#" + that.name + "']").on('click', function() {
            $("#chat a[href='#" + that.name + "'] .label").hide();
            $('#' + that.name + '_form input').focus();
        });
        $('#' + this.name + '_form input').focus();

    },
/** metodo utilizzato per la ricezione di un nuovo messaggio di testo in una chat.
Appende il messaggio appena ricevuto alla chat corretta e, nel caso in cui la chat
non sia quella al momento visualizzata, mostra un'icona di notifica. */
    ricevi: function(o) {
        $("#" + this.name + "").append("<p>" + o.mittente + ": " + o.messaggio.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;") + "</p>");
        $("#contenuto").animate({scrollTop: $('#contenuto').get(0).scrollHeight}, 1000);
        if (!$("#" + this.name).hasClass("active")) {
            $('#chat a[href="#' + this.name + '"] .label').show();
        }
    },
/** metodo utilizzato nel momento in cui un utente abbandona una chat. Nel caso
in cui la conversazione sia tra più utenti viene semplicemente visualizzato un
messaggio che informa che quell'utente ha abbandonato la conversazione; altrimenti,
oltre al messaggio di abbandono, il titolo della chat non è più il nome del
destinatario ma un messaggio che ne informa la conclusione. */
    eliminaUtente: function(nome) {
        var idx = this.nomi.indexOf(nome);
        assert(idx >= 0);
        this.nomi.splice(idx, 1);

        var registro = PT.MODEL.getInstanceRegistro().getListaUtenti();
        var destinatario = '';
        for (var j = 0; j < this.nomi.length; j++) {
            destinatario += (j ? ' ' : '') +
                    (registro[this.nomi[j]] ?
                            registro[this.nomi[j]].getNome() :
                            this.nomi[j]);
        }
        destinatario = destinatario || 'Chat conclusa';
        $('#chat a[href="#' + this.name + '"] .nome').text(destinatario);

        var abbandonante = (registro[nome] ? registro[nome].getNome() : nome);
        $("#" + this.name + "").append("<p>" + abbandonante + " ha abbandonato la chat</p>");

    },
/** metodo utilizzato per chiudere una chat. Nel caso in cui vi siano altre chat
viene visualizzata la chat successiva, o quella rimanente, altrimenti viene
visualizzato il logo. */
    rimuovi: function() {
        var pre = $('#chat a[href="#' + this.name + '"]').parent('li');
        var cont = $("#" + this.name);
        var form = $("#" + this.name + "_form");
        if ($(pre).hasClass('active')) {
            var hpre = pre.prev().find('a').attr('href');
            var hnext = pre.next().find('a').attr('href');
            if (hpre) {
                $('#chat a[href="' + hpre + '"]').tab('show');
                $('#chat a[href="#' + this.name + '"] .label').hide();

            }
            else {
                if (hnext) {
                    $('#chat a[href="' + hnext + '"]').tab('show');
                    $('#chat a[href="#' + this.name + '"] .label').hide();

                }
            }
        }
        pre.remove();
        if (($("#chat a").length === 0)) {
            $('#tabchat').hide();

            if ($("#vchat").is(":hidden")) {
                $('#logo').show();
            }
        }

        cont.remove();
        form.remove();
    }

});
