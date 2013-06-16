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
 * 1.1  2013-04-24  SC  Aggiunte funzionalità sulle tab
 * 2.0  2013-04-24  MD  Aggiunti metodi per ripulire form e modali
 */
//bottone Login
function accedi() {
    PT.VIEW.getInstanceSubjectView().fireEvent("Login");
}

//bottone Logout
function esci() {
    PT.VIEW.getInstanceSubjectView().fireEvent("Logout");
}

//Controllo ip
function IP() {
    (new PT.VIEW.ValidatoreInput()).validaIP();
}

//bottone conferma registrazione
function registra() {
    (new PT.VIEW.ValidatoreInput()).registra();
}

//bottone cambio Usarname
function submitNewUsr() {
    (new PT.VIEW.ValidatoreInput()).newUsername();
}

//bottone cambio psw
function submitNewPsw() {
    (new PT.VIEW.ValidatoreInput()).subNewPsw();
}

//bottone elimina amico
function cancella() {
    if ($("[name='elimina']:checked").length) {
        var gestore = new PT.VIEW.GestoreAccount();
        gestore.cancella();
    }
}

//bottone conferma email recupero psw
function confermaEmail() {
    (new PT.VIEW.ValidatoreInput()).confirmEmail();
}

//bottone che reimposta la psw recupero psw
function nuovapassword() {
    (new PT.VIEW.ValidatoreInput()).newPsw();
}

//bottone ricerca
function cerca() {
    if ($("#cercaAmiciInput").val() !== "") {
        PT.VIEW.getInstanceSubjectView().fireEvent("RicercaUtente");
    }
}

//BOTTONI CHIAMATE
//bottone clickover Audio/Video/Chat
function newComunicazione(chi) {
    var tipo = $(chi).attr("value");
    var destinatario = $(chi).parents('li').find('a').attr("href").substr(1);
    $('body').css('cursor', 'wait');
    PT.VIEW.getInstanceSubjectView().fireEvent("NuovaChiamata", {destinatario: destinatario, tipo: tipo});
}

//bottone Comunicazione Multipla
function newMComunicazione(chi) {
    var tipo = $(chi).attr("value");
    var destinatario = $("[name='selettore']:checked");
    $('[name=selettore]').attr('checked', false);
    $('#amiciOn span.checkbox').hide();
    $('#multipla').hide();
    $('body').css('cursor', 'wait');
    PT.VIEW.getInstanceSubjectView().fireEvent("NuovaChiamata", {destinatario: destinatario, tipo: tipo});
    $("#um1").trigger("change");
    $('#um1').prop("checked", true);
}

//chiamata IP
function newIPComunicazione(chi) {
    var destinatario = $("#ip").val();
    var tipo = $(chi).attr("value");
    $("#ip").trigger('click');
    $('body').css('cursor', 'wait');
    PT.VIEW.getInstanceSubjectView().fireEvent("NuovaChiamata", {destinatario: destinatario, tipo: tipo});
}

//BOTTONI CLICKOVER
//bottone clickover accetta
function trattaRichiesta(chi, esito) {
    PT.VIEW.getInstanceUpdater().trattaRichiestaPendente(chi, esito);
}

//bottone clickover messaggio segreteria
function nuovoMessaggio(chi) {
    var email = $(chi).parents('li').find('a').attr("href").substr(1);
    PT.VIEW.getInstanceUpdater().scriviMessaggioSegreteria(email);
}

//bottone clickover richiesta amicizia
function richiediAmicizia(chi) {
    var email = $(chi).parents('li').find('a').attr("href").substr(1);
    $(chi).parents('.clickover').parents('li').remove();
    PT.VIEW.getInstanceSubjectView().fireEvent("NuovaAmicizia", email);
}

function aggiungiEventi() {
    $('#ip').click(function() {
        $("#ip").val('');
        $("#chiamaIP").hide();
        $('#ip').removeAttr("readonly");
    });
    //cambio stato
    $('#on').click(function() {
        (new PT.VIEW.GestoreAccount()).cambioStatoPersonale(this.text);
    });
    $('#off').click(function() {
        (new PT.VIEW.GestoreAccount()).cambioStatoPersonale(this.text);
    });

    //metodi per cambiare la modalità di selezione per la conversazione
    $('#um2').change(function() {
        $('#amiciOn span.checkbox').show();
        $('#multipla').show();
        $("#amiciOn a").clickover('disable');
        $("#amiciOn a").addClass('nonlink');
    });

    $('#um1').change(function() {
        $('#amiciOn span.checkbox').hide();
        $('#multipla').hide();
        $("#amiciOn a").clickover('enable');
        $("#amiciOn a").removeClass('nonlink');
    });

    //metodi per ripulire i modali e tab
    $('#registra').on('hide', function() {
        $("#registra div").removeClass("error");
        $("#registra input").val("");
        $("#registra span").hide();
    });
    $('#lost').on('hide', function() {
        $("#lost div").removeClass("error");
        $("#lost input").val("");
        $("#lost span").hide();
        $("#dr p").text("");
        $("#insem").show();
        $("#conferma").show();
        $("#dr").hide();
        $("#newpass").hide();
    });

    $('#opzioni').on('hide', function() {
        $("#collapseOne input").val("");
        $("#collapseTwo input").val("");
        $("#collapseThree input").prop('checked', false);
        $("#opzioni div").removeClass("error");
        $("#opzioni span").hide();
    });

    $("#tut").on("hide", function() {
        var video = $("#tut iframe").attr("src");
        $("#tut iframe").attr("src", "");
        $("#tut iframe").attr("src", video);
    });

    $("#tabamici>li>a[href='#pend']").on('click', function() {
        $('#tabamici span').hide();
    });

    //clickover tutorial
    $('#tutorial').clickover({
        placement: 'bottom',
        html: 'true',
        title: '<b>Serve aiuto?</b>',
        content: '<ul><li><a href="#tut" data-toggle="modal">Tutorial<a></li><li><a href="https://dl.dropboxusercontent.com/u/1426377/manuale-utente.pdf" target="_blank">Manuale Utente</a></li></ul>'
    });
}
