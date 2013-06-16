/* file:        820-GestoreOperazione.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-22  GF  Prima stesura
 */


/** Classe che si occupa di gestire le notifiche di operazioni avvenute nel server, portando con se la riuscita o meno delle stesse */
PT.CONTROLLER.G.GestoreOperazione = new Class({
    ultimoAvvisoConnessione: null,
    metodi: function(orig) {
        var that = this;

        var o = {
            Segnale: function(e) {
                if (new Date() - that.ultimoAvvisoConnessione > 5000) {
                    that.ultimoAvvisoConnessione = new Date();
                    var upd = PT.VIEW.getInstanceUpdater();
                    if (!e.riuscita)
                        upd.notificaEsito("Errore di comunicazione", "Si è verificato un errore di comunicazione");
                }
            },
            Registrazione: function(e) {

                var upd = PT.VIEW.getInstanceUpdater();

                if (e.riuscita)
                    upd.notificaEsito("Registrazione", "Registrazione completata correttamente");
                else
                    upd.notificaEsito("Registrazione", "Registrazione non riuscita. L'email è già presente nel database o i dati inseriti non sono corretti");

            },
            RecuperaPassword: function(e) {
                var upd = PT.VIEW.getInstanceUpdater();

                if (e.riuscita)
                    upd.notificaEsito("Recupero Password", "Password recuperata con successo");
                else
                    upd.notificaEsito("Recupero Password", "Recupero password non riuscito. Controllare i dati inseriti");
            },
            NuovaAmicizia: function(e) {
                var upd = PT.VIEW.getInstanceUpdater();

                if (e.riuscita)
                    upd.notificaEsito("Richiesta Nuova Amicizia", "Richiesta di amicizia inviata correttamente");
                else
                    upd.notificaEsito("Richiesta Nuova Amicizia", "Impossibile inviare la richiesta di amicizia. Si prega di riprovare");
            },
            ModificaPassword: function(e) {
                var upd = PT.VIEW.getInstanceUpdater();

                if (e.riuscita)
                    upd.notificaEsito("Modifica Password", "Password cambiata con successo");
                else
                    upd.notificaEsito("Modifica Password", "Cambio password non riuscito. Controllare i dati inseriti");
            },
            MessaggioSegreteria: function(e) {
                var upd = PT.VIEW.getInstanceUpdater();

                if (e.riuscita)
                    upd.notificaEsito("Messaggio Segreteria", "Messaggio di segreteria inviato correttamente");
                else
                    upd.notificaEsito("Messaggio Segreteria", "Invio del messaggio di segreteria non riuscito. Si prega di riprovare");
            },
            ModificaProfilo: function(e) {
                var upd = PT.VIEW.getInstanceUpdater();

                if (e.riuscita)
                    upd.notificaEsito("Modifica Profilo", "Profilo aggiornato correttamente");

                else
                    upd.notificaEsito("Modifica Profilo", "Modifica profilo non riuscita. Si prega di riprovare");
            },
            RichiestaDomandaSicurezza: function(e) {
                var upd = PT.VIEW.getInstanceUpdater();

                if (!e.riuscita)
                    upd.notificaEsito("Recupero Password", "Richiesta di recupero password fallita, l'email inserita non ha un account associato");
            },
            Login: function(e) {
                var upd = PT.VIEW.getInstanceUpdater();
                if (!e.riuscita)
                    upd.notificaEsito("Login", "Login fallito");
            }

        };
        var x = o[orig];
        return x;
    }.protect(),
    /** Vengono eseguite le operazioni appropriate in base all'operazione notificata e all'esito della stessa */
    esegui: function(evento) {
        var x = this.metodi(evento.originale);
        assert(x);
        x(evento);
    }
});

PT.CONTROLLER.getInstanceDispatcher().
        aggiungiGestore("Operazione", new PT.CONTROLLER.G.GestoreOperazione);
