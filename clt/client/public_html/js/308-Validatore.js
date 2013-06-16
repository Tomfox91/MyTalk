/* file:        307-GestoreAccount.js
 * package:     PT.VIEW
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-24  GF  Prima stesura
 * 1.1  2013-04-24  GF  Aggiunta metodi
 * 2.0  2013-04-24  GF  Correzione alla Regex
 */

/** Classe che valida gli input inseriti dall'utente. */
PT.VIEW.ValidatoreInput = new Class({
/** metodo che ritorna l'espressione regolare utilizzata per validare un email.
*/
    getEmailRegex: function() {
        var emailRegex = /^([0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9})$/;
        return emailRegex;
    }.protect(),
/** metodo che ritorna l'espressione regolare utilizzata per validare un ip. */
    getIpRegex: function() {
        var ipRegex = /^(25[0-5]|2[0-4][0-9]|1?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
        return ipRegex;
    }.protect(),
/** metodo che ritorna l'espressione regolare utilizzata per validare uno username.
*/
    getUserRegex: function() {
        return /^[A-Za-z]([-A-Za-z. _0-9ÀÁÅÃÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ])*[A-Za-z0-9ÀÁÅÃÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ]$/;
    }.protect(),
    validaPsw: function(psw, pswconfirm) {
        return (psw !== pswconfirm || psw === "");
    }.protect(),
/** metodo utilizzato per verificare che un ip inserito da un utente sia valido.
Nel caso in cui l'input sia valido vengono visualizzati i tasti per iniziare una
conversazione e non è più possibile modificare l'input della form; altrimenti viene
visualizzato un messaggio di errore. */
    validaIP: function() {
        $("#cercaIP span").hide();
        var IP = $("#ip").val();
        if (!IP.match(this.getIpRegex())) {
            $("#cercaIP span").css("display", "inline-block");
            $("#cercaIP span").show();

        }
        else {
            $("#cercaIP span").hide();
            $("#chiamaIP").show();
            $('#ip').attr("readonly", "readonly");

        }
    },
/** metodo per la registrazione di un nuovo utente. Controlla che tutti gli input
inseriti siano validi, notificando al Controller l'aggiunta di un nuovo utente,
altrimenti visualizza dei messaggi di errore a seconda dell'input sbagliato. */
    registra: function() {
        $("#errmail").hide();
        $("#errpsw").hide();
        var user = $("#user").val();
        var email = $("#emailR").val();
        var psw = $("#psw").val();
        var pswc = $("#pswc").val();
        var error = false;
        if (!user.match(this.getUserRegex())) {
            $("#user").parent("div").find("span").show();
            $("#user").parent("div").parent("div").addClass("error");
            error = true;
        }
        if (!email.match(this.getEmailRegex())) {
            $("#emailR").parent("div").find("span").show();
            $("#emailR").parent("div").parent("div").addClass("error");
            error = true;
        }
        if (this.validaPsw(psw, pswc)) {
            $("#psw").parent("div").parent("div").addClass("error");
            $("#pswc").parent("div").parent("div").addClass("error");
            $("#pswc").parent("div").find("span").show();
            error = true;
        }
        if (!error) {
            PT.VIEW.getInstanceSubjectView().fireEvent("Registrazione");
            $("#registra").modal("hide");
        }
    },
/** metodo utilizzato per la modifica dello username. Controlla che l'input inserito
sia corretto, notificando tale modifica al Controller, altrimenti visualizza un
messaggio di errore. */
    newUsername: function() {
        var username = $("#inputNewUsr").val();
        if (!username.match(this.getUserRegex())) {
            $("#inputNewUsr").parent("div").find("span").show();
            $("#inputNewUsr").parent("div").parent("div").addClass("error");
        }
        else {
            (new PT.VIEW.GestoreAccount()).setNewUsername(username);
            $("#opzioni").modal("hide");
        }
    },
/** metodo utilizzato per la modifica della password. Controlla che l'input inserito
sia corretto, notificando tale modifica al Controller, altrimenti visualizza un
messaggio di errore. */
    newPsw: function() {
        var pswn = $("#pwdn").val();
        var pswnc = $("#pwdnc").val();
        if (this.validaPsw(pswn, pswnc)) {
            $("#pwdn").parent("div").addClass("error");
            $("#pwdn").parent("div").find("span").show();
        }
        else {

            PT.VIEW.getInstanceSubjectView().fireEvent("RecuperaPassword");
            $("lost").modal('hide');
        }
    },
/** metodo utilizzato per l'inserimento dell'email durante il recupero password.
Controlla che l'email inserita sia corretta, richiedendo al Controller la domanda
di sicurezza corrispondente, altrimenti visualizza un messaggio di errore. */
    confirmEmail: function() {
        var email = $("#lstem").val();
        if (!email.match(this.getEmailRegex())) {
            $("#insem span").show();
            $("#insem").addClass("error");
        }
        else {
            PT.VIEW.getInstanceSubjectView().fireEvent("RichiestaDomandaSicurezza");
        }
    },
/** metodo utilizzato per la modifica della password in caso di smarrimento della
stessa. Controlla che la password e la sua conferma coincidano e siano valide,
notificando tale modifica al Controller, altrimenti visualizza dei messaggi di
errore. */
    subNewPsw: function() {
        var psw = $("#inputNewPsw").val();
        var cpsw = $("#inputConfPsw").val();
        if (this.validaPsw(psw, cpsw)) {
            $("#inputNewPsw").parent("div").parent("div").addClass("error");
            $("#inputConfPsw").parent("div").parent("div").addClass("error");
            $("#inputConfPsw").parent("div").find("span").show();
        }
        else {
            PT.VIEW.getInstanceSubjectView().fireEvent("ModificaPassword");
            $("#opzioni").modal("hide");
        }
    }


});
