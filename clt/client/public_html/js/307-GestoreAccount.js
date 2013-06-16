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
 * 2.0  2013-04-24  TF  ggiunta ultimi tre metodi
 */

/** Classe che gestisce le operazioni possibili su un account: login, registrazione,
modifica delle informazioni e recupero password. */
PT.VIEW.GestoreAccount = new Class({

/** metodo che restituisce la domanda di sicurezza inserita in fase di registrazione
durante il recupero password visualizzando il modale corretto. */
    notificaDomanda: function(domanda) {
        $("#dr p").text(domanda);
        $('#insem').hide();
        $('#dr').show();
        $('#newpass').show();
        $('#conferma').hide();
    },
 	
/** metodo che rimuove gli amici in base ai checkox selezionati nell'apposito
modale. Notifica al Controller le rimozioni da effettuare. */
    cancella: function() {
        var a = $("#opzioni").find("#elimina").find("[name='elimina']:checked");
        var sub = PT.VIEW.getInstanceSubjectView();
        for (var i = 0; i < a.length; i++) {
            a.parent("label").parent("li").remove();
            sub.fireEvent("amicoRimosso", a[i].value);
        }
    },

/** metodo che aggiunge l'amico la cui amicizia è appena stata accettata alla lista
degli amici che è possibile eliminare. */
    aggiungiRimozione: function(amico, email) {
        var aggiunta = '<li id="' + email.replace(/[^a-z0-9]+/g, '') + '_elimina"><label class="checkbox inline"><input type="checkbox" name="elimina" value="' + email + '"><p>' + amico + '</p></input></label></li>';
        $("#opzioni").find("#elimina ul").append(aggiunta);
    },

/** metodo invocato quando un utente cambia il proprio stato. Notifica al Controller
la presenza di una modifica del profilo da effettuare. */
    cambioStatoPersonale: function(stato) {
        $('#onoff>span').remove();
        var img = PT.VIEW.getInstanceUpdater().getIcon(stato);
        $('#onoff').append(img);
        PT.MODEL.getInstanceRegistro().getProprioUtente().setStato(stato);
        PT.VIEW.getInstanceSubjectView().fireEvent("ModificaProfilo");
    },
    setNewUsername: function(username) {
        PT.MODEL.getInstanceRegistro().getProprioUtente().setNome(username);
        $('#nome span').text(username);
        PT.VIEW.getInstanceSubjectView().fireEvent("ModificaProfilo");
    },

/** metodo che effettua il login. Nasconde il logo, la form per l'inserimento dei
dati di login resettandoli e il bottone corrispondente disabilitandolo, il bottone
per la registrazione, il link per lo smarrimento della password; mostra lo username,
l'icona che corrisponde allo stato attuale dell'utente, il tasto di modifica
account, la tabAmici, tabCerca, tabPendenti. */
    effettuaAccesso: function() {
        var utente = PT.MODEL.getInstanceRegistro().getProprioUtente();
        var img = PT.VIEW.getInstanceUpdater().getIcon(utente.getStato());
        $('#onoff').append(img);
        $("#ownmail").append("La tua email: " + utente.getEmail());
        $('#nome span').text(utente.getNome());
        $('#log').find('input').val("");
        $('#log').hide();
        $('#logbtn').attr("disabled", "disabled");
        $('#logout').show();
        $('#lst').hide();
        $('#reg').hide();
        $('#tab').show();
        $('#client>li').show();
        $('#opz').show();

    },

/** metodo che effettua il logout. Effettua il refresh della pagina. */
    logout: function() {
        document.location.reload();
    }
});
