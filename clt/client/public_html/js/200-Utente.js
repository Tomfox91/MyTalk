/* file:        200-Utente.js
 * package:     PT.MODEL
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-27  MD  Prima stesura
 */

/** Classe che descrive un generico utente, amico o non, del Client. */
PT.MODEL.Utente = new Class({ //stato è una stringa

    email: null,
    stato: null,
    nome: null,
/** Costruttore per la creazione un Utente a partire dai dati che lo descrivono
*/
    initialize: function(email, stato, nome) {
        this.email = email;
        this.stato = stato; //qui si può salvare lo stato "ndf"
        this.nome = nome;
    },
/** restituisce l'email dell'utente */
    getEmail: function() {
        return this.email;
    },
/** restituisce lo stato dell'utente */
    getStato: function() {
        return this.stato;
    },
/** imposta lo stato dell'utente */
    setStato: function(stato) {
        this.stato = stato;
    },
/** restituisce il nome dell'utente */
    getNome: function() {
        return this.nome;
    },
/** imposta il nome dell'utente */
    setNome: function(nome) {
        this.nome = nome;
    }
});




