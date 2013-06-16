/* file:        301-Updater.js
 * package:     PT.VIEW
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-24  LDV Prima stesura
 * 1.1  2013-03-25  LDV revisione metodi
 * 1.2  2013-04-10  MD aggiunta evento
 * 2.0  2013-04-11  MD Correzione errori metodi
 */

/** Classe che aggiorna le tab degli amici, delle amicizie pendenti e di ricerca.
*/
PT.VIEW.getInstanceUpdater = function() {
    var instance;
    return function() {
        if (!instance) {
            var Updater = new Class({
                //Dichiarazione Updater
                initialize: function() {
                    var reg = PT.MODEL.getInstanceRegistro();
                    var that = this;
                    reg.addEvent("aggUtenteModel", function(x) {
                        that.aggiuntoAmico(x);
                    });
                    reg.addEvent("aggPendenteModel", function(x) {
                        that.aggiungiPendente(x);
                    });
                    reg.addEvent("rmUtenteModel", function(x) {
                        that.rimuoviAmico(x);
                    });
                    reg.addEvent("modificaStatoUtenteModel", function(x) {
                        that.cambioStato(x);
                    });
                    reg.addEvent("modificaNomeUtenteModel", function(x) {
                        that.cambioUsername(x);
                    });
                    reg.addEvent('rmPendenteModel', function(x) {
                        that.rimuoviPendente(x);
                    });
                    reg.addEvent('notificaIp', function(x) {
                        that.settaIp(x);
                    });
                },
/** metodo utilizzato affinchè l'utente possa visualizzare il proprio indirizzo
ip. */
                settaIp: function(ip) {
                    $("#tuoIP").text("Il tuo IP: " + ip);
                },
/** metodo che invoca il metodo aggiungiAmico per l'aggiunta di un nuovo utente
alla lista di amici. */
                aggiuntoAmico: function(utente) {
                    this.aggiungiAmico(utente.getNome(), utente.getEmail(), utente.getStato());
                },
/** metodo che visualizza l'icona di notifica sulla tabPendenti e che invoca il
metodo aggiungiAmicoPendente. */
                aggiungiPendente: function(utente) {
                    $('#tabamici span').show();
                    this.aggiungiAmicoPendente(utente.getNome(), utente.getEmail());
                },
/** metodo utilizzato per la rimozione di un amico. Rimuove l'amico dalla lista
di rimozione e dalla lista degli amici presente in tabAmici. */
                rimuoviAmico: function(utente) {
                    $('#' + utente.getNome().replace(/ /g, "_") + '_elimina').remove();
                    $('#amici ul>li>a[href="#' + utente.getEmail() + '"]').parents('li').remove();
                },
/** metodo utilizzando nel momento in cui un amico cambia stato. Modifica l'icona
che indica lo stato accanto al nome dell'amico e lo aggiunge alla lista corretta
presente in tabAmici aggiungendovi tutte le classi che possiedono gli amici
appartenenti alla stessa lista. */
                cambioStato: function(utente) {
                    var amico = $('#amici ul>li>a[href="#' + utente.getEmail() + '"]').parents('li');
                    var img = this.getIcon(utente.getStato());
                    amico.find('a').removeClass();
                    amico.find("span.label").replaceWith(img);
                    amico.find('a').addClass("amico " + utente.getStato());
                    amico.remove();
                    $('#amici').find('#' + utente.getStato()).append(amico);
                    this.addClickover(utente.getStato());
                    if (utente.getStato() !== "Offline" && $("[name=unomolti]:checked").val() === "MultiChat") {
                        amico.find("a").addClass("nonlink");
                        amico.find("a").clickover("disable");
                        amico.find("span.checkbox").show();

                    }
                    else {
                        amico.find("span.checkbox").hide();
                    }
                },
/** metodo utilizzato nel caso in cui un amico modifichi il proprio username.
Aggiorna il nome di tale amico sia nella lista presente nella tabAmici che nella
lista di rimozione. */
                cambioUsername: function(utente) {
                    $('#amici ul>li>a[href="#' + utente.getEmail() + '"]').text(utente.getNome());
                    $("#" + utente.getEmail().replace(/[^a-z0-9]+/g, '') + "_elimina p").text(utente.getNome());
                },
/** metodo che rimuove un utente dalla lista presente nella tabPendenti. Nel caso
in cui la lista, in seguito alla rimozione, sia vuota, visualizza una frase che
indica che, al momento, non vi sono richieste di amicizia pendenti. */
                rimuoviPendente: function(utente) {
                    $('#pend ul>li>a[href="#' + utente.getEmail() + '"]').parents('li').remove();
                    if (!$("#pend ul>li").length) {
                        $('#pend p').show();
                    }
                },
              
/** metodo che aggiunge un nuovo amico alla propria lista amici. Esso, inoltre,
visualizza la giusta icona di stato accanto al nome dell'amico. */
                aggiungiAmico: function(utente, email, stato) {
                    var nuovoamico = '<a href="#' + email + '" class="amico ' + stato + '" data-original-title title>' + utente + '</a>';
                    var img = this.getIcon(stato);
                    var checkbox = '<span class="checkbox hide"><input type="checkbox" name="selettore" value="' + email + '"></span>';
                    $('#amici').find('#' + stato).append('<li>' + checkbox + img + nuovoamico + '</li>');
                    var gest = new PT.VIEW.GestoreAccount();
                    gest.aggiungiRimozione(utente, email);
                    this.addClickover(stato);

                }.protect(),
                
/** metodo che aggiunge un nuovo utente alla lista delle amiczie pendenti. Esso,
inoltre, visualizza la giusta icona accanto al nome e nasconde la frase che indica
la mancanza di richieste di amicizia pendenti. */
                aggiungiAmicoPendente: function(utente, email) {
                    var img = this.getIcon("generic");
                    var nuovopendente = '<a href="#' + email + '" class="pendente" data-original-title title>' + utente + '</a>';
                    $('#pend').find('ul').append('<li>' + img + nuovopendente + '</li>');
                    this.addClickover("pendente");
                    $('#pend p').hide();
                }.protect(),
               
/** metodo utlizzato per accettare o rifiutare un'amicizia pendente. Notifica al
Client la risposta a tale richiesta e, nel caso in cui non siano presenti altre
richieste, visualizza la frase di mancanza di ulteriori richieste. */
                trattaRichiestaPendente: function(chi, esito) {
                    var pendente = $(chi).parents('li');
                    var email = $(pendente).find('a').attr("href").substr(1);
                    pendente.remove();
                    if (!$("#pend ul>li").length) {
                        $('#pend p').show();
                    }
                    PT.VIEW.getInstanceSubjectView().fireEvent("rispostaRichiesta", {email: email, esito: esito});
                },
               
/** metodo utilizzato per far apparire una nuova finestra con un messaggio. Esso
infatti visualizza un alert della classe bootbox contenente il tipo e il messaggio
passati come parametri. */
                notificaEsito: function(tipo, esito) {
                    $('body').css('cursor', 'auto');
                    bootbox.alert("<h3>" + tipo + "</h3><p>" + esito + "</p>");
                },
                richiestaChiamata: function(o) {
                    var nomi = '';
                    var listautenti = PT.MODEL.getInstanceRegistro().getListaUtenti();
                    for (var i = 0; i < o.partecipanti.length; i++) {
                        nomi += (i ? ', ' : '') +
                                (listautenti[o.partecipanti[i]] ?
                                        listautenti[o.partecipanti[i]].getNome() :
                                        o.partecipanti[i]);
                    }
                    var messaggio = "Vuoi aprire una comunicazione " + o.tipo +
                            " con " + nomi + "?";

                    bootbox.confirm(messaggio, 'No', 'Sì', o.callback);
                },
              
/** metodo per la visualizzazione di un esito di una ricerca. Nel caso in cui la
ricerca dia dei risultati, visualizza tutti gli utenti ritornati con la corrispondente
icona; nel caso in cui non vi sia alcun risultato, visualizza la frase per notificare
all'utente tale mancanza. */
                visualizzaRicerca: function(esito) {
                    var img = this.getIcon("generic");
                    $("#cerca p").hide();
                    $("#esiti").empty();
                    if (Object.keys(esito).length > 0) {
                        Object.keys(esito).forEach(function(email) {
                            var nuovoutente = '<a href="#' + email + '" class="richiedi" data-original-title title>' + esito[email] + '</a>';
                            $('#cerca').find('#esiti').append('<li>' + img + nuovoutente + ' ; ' + email + '</li>');
                        });
                        this.addClickover("richiedi");
                    }
                    else {
                        $("#cerca p").show();
                    }
                },
                
/** metodo invocato per visualizzare un messaggio di segreteria. Esso visualizza
un modale contenente il mittente e il messaggio. */
                visualizzaMessaggioSegreteria: function(mittente, messaggio) {
                    $("#msg h3").text("Nuovo messaggio di segreteria");
                    $("#msgbody").empty();
                    $("#msgfooter").empty();
                    $("#msgbody").append('<p> Mittente: ' + mittente + '</p>');
                    $("#msgbody").append('<p>' + messaggio + '</p>');
                    var conferma = '<button  type="submit" class="btn btn-primary" data-dismiss="modal">OK</button>';
                    $("#msgfooter").append(conferma);
                    $("#msg").modal("show");
                },
                
/** metodo invocato per scrivere un messaggio di segreteria. Permette all'utente
di scrivere un messaggio di segreteria di massimo 250 caratteri e di inviarlo
all'utente desiderato. */
                scriviMessaggioSegreteria: function(destinatario) {
                    $("#msg h3").text("Scrivi messaggio di segreteria");
                    $("#msgbody").empty();
                    $("#msgfooter").empty();
                    $("#msgbody").append('<p> Destinatario: ' + destinatario + '</p>');
                    $("#msgbody").append('<textarea rows="10" maxlength="250"></textarea>');
                    var invia = new Element('button', {
                        'class': 'btn btn-primary',
                        'html': 'Invia',
                        events: {
                            click: function() {
                                $("#msg").modal("hide");
                                PT.VIEW.getInstanceSubjectView().fireEvent("MessaggioSegreteria");
                            }
                        }
                    });
                    $("#msgfooter").append(invia);
                    $("#msg").modal("show");
                },
              
/** metodo utilizzato per l'aggiunta di un clickover ad un utente contenente diversi
tasti a seconda del tipo passato come parametro. Nel caso in cui l'utente sia
offline verrà visualizzato il tasto per l'invio di un messaggio di segreteria; nel
caso in cui l'utente sia una richiesta di amicizia pendente verranno visualizzati
i tasti per accettare o rifiutare l'amicizia; nel caso in cui l'utente sia il
risultato di una ricerca verrà visualizzato il bottone per l'invio di una richiesta
di amicizia; di default verranno visualizzati i tre pulsanti per i tre diversi
tipi di chiamate. */
                addClickover: function(tipo) {
                    var titolo;
                    var contenuto;
                    switch (tipo) {
                        case "Offline":
                            titolo = '<span class="text-info"><strong>Messaggio Segreteria</strong></span></span>';
                            contenuto = '<center><span class="btn btn-inverse" onclick="nuovoMessaggio(this)" data-dismiss="clickover">Invia</span></span></center>';
                            break;
                        case "pendente":
                            titolo = '<span class="text-info"><strong>Accetta Amicizia</strong></span></span>';
                            contenuto = '<span class="btn btn-inverse" onclick="trattaRichiesta(this,1)" data-dismiss="clickover" data-toggle="tooltip" title="accettando l\'amicizia comparirai nella lista amici di questo utente e viceversa">Accetta</span></span> <span class="btn btn-inverse" onclick="trattaRichiesta(this,0)" data-dismiss="clickover" data-toggle="tooltip" title="rifiutando l\'amicizia l\'utente non potrà visualizzarti nella sua lista amici e viceversa">Rifiuta</span></span> ';
                            break;
                        case "richiedi":
                            titolo = '<span class="text-info"><strong>Richiedi Amicizia</strong></span></span>';
                            contenuto = '<span class="btn btn-inverse" onclick="richiediAmicizia(this)" data-dismiss="clickover">Richiedi Amicizia</span></span>';
                            break;
                        default:
                            titolo = '<span class="text-info"><strong>Chiama Utente</strong></span></span>';
                            contenuto = '<center><span class="btn btn-inverse multi" onclick="newComunicazione(this)" data-dismiss="clickover" value="audio">Audio</span></span> <span class="btn btn-inverse multi" onclick="newComunicazione(this)" data-dismiss="clickover" value="video">Video</span></span> <span class="btn btn-inverse" onclick="newComunicazione(this)" data-dismiss="clickover" value="data">Chat</span></span></center>';
                            break;
                    }
                    $('.' + tipo).clickover({
                        placement: 'right',
                        html: 'true',
                        title: titolo,
                        content: contenuto/*,
                         onShown: function() {
                         if (!($("#vchat").is(":hidden"))) {
                         $('.multi').hide();
                         }
                         }*/
                    });
                }.protect(),
 
/** metodo che restituisce un'icona diversa a seconda del tipo passato come
parametro. Nel caso "Online" l'icona è un segno di spunta su uno sfondo verde ed
indica lo stato online di un utente; nel caso "Offline" l'icona è una croce su uno
sfondo rosso ed indica che l'utente è offline; nel caso "Occupato" l'icona è un
orologio stilizzato su sfondo giallo ed indica che l'utente è occupato; nel caso
generico l'icona è un utente stilizzato su sfondo blu ed è utilizzato in varie
situazioni come il risultato di una ricerca e l'amicizia pendente.  */
                getIcon: function(tipo) {
                    var img;
                    switch (tipo) {
                        case "Online":
                            img = '<span class="label label-success"><i class="icon-ok-circle icon-white"></i></span>';
                            break;

                        case "Offline":
                            img = '<span class="label label-important"><i class="icon-ban-circle icon-white"></i></span>';
                            break;

                        case "Occupato":
                            img = '<span class="label label-warning"><i class="icon-time icon-white"></i></span>';
                            break;
                        case "generic":
                            img = '<span class="label label-info"><i class="icon-user icon-white"></i></span>';
                            break;
                    }
                    return img;
                }

            });
            instance = new Updater();
        }
        return instance;
    };
}();

PT.VIEW.getInstanceUpdater();
