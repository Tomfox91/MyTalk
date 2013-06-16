/* file:        821-GestoreProprioUtente.js
 * package:     PT.CONTROLLER
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-22  TF  Prima stesura
 */

/** Classe che riceve dal server i dettagli dell’utente con cui si effettua il login */
PT.CONTROLLER.G.GestoreProprioUtente = new Class({
/** Viene impostato il registro utente in modo che sia aggiornato secondo i dati
dell’evento ricevuto. */
    esegui: function(evento) {
        var pu = new PT.MODEL.Utente(evento.email, evento.stato, evento.username);
        var reg = PT.MODEL.getInstanceRegistro();
        reg.setProprioUtente(pu);
        (new PT.VIEW.GestoreAccount()).effettuaAccesso();
    }
});

PT.CONTROLLER.getInstanceDispatcher().
        aggiungiGestore("ProprioUtente", new PT.CONTROLLER.G.GestoreProprioUtente());
