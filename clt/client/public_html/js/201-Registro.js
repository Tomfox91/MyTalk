/* file:        201-Registro.js
 * package:     PT.MODEL
 * progetto:    5-client
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-27  GF  Prima stesura
 * 2.0  2013-04-04  TF  aggiunta metodi setProprioIP e getProprioIP
 */

/** Classe che realizza il registro che mantiene in memoria i dati di tutti gli
utenti di una particolare sessione del programma. */
PT.MODEL.getInstanceRegistro = function() {
    var instance;
    return function() {
        if (!instance) {
            var Registro = new Class({
                Implements: Events,
                listaUtenti: null,
                listaPendenti: null,
                proprioUtente: null,
                proprioIP: '0.0.0.0',
                //
/** Costruttore privato per l’implementazione del Design pattern Singleton */
                initialize: function() {
                    this.listaUtenti = {};
                    this.listaPendenti = {};
                },
                //
/** imposta l'attributo proprioUtente al parametro utente */
                setProprioUtente: function(utente) {
                    this.proprioUtente = utente;
                },
                //
/** restituisce l'attributo proprioUtente */
                getProprioUtente: function() {
                    return this.proprioUtente;
                },
                //
/** imposta nel Registro l'attributo proprioIP */
                setProprioIP: function(ip) {
                    this.proprioIP = ip;
                    this.fireEvent("notificaIp", ip);
                },
                //
/** restituisce l'attributo proprioIP */
                getProprioIP: function() {
                    return this.proprioIP;
                },
                //
/** restituisce l'attributo nome di proprioUtente se è impostato, altrimenti
restituisce l'attributo proprioIP */
                getProprioNome: function() {
                    if (this.getProprioUtente()) {
                        return this.getProprioUtente().getNome();
                    } else {
                        return this.getProprioIP();
                    }
                },
                //
/** restituisce l'attributo email di proprioUtente se è impostato, altrimenti
restituisce l'attributo proprioIP  */
                getProprioId: function() {
                    if (this.getProprioUtente()) {
                        return this.getProprioUtente().getEmail();
                    } else {
                        return this.getProprioIP();
                    }
                },
                //
/** restituisce la lista degli utenti amici contenuti nel Registro */
                getListaUtenti: function() {
                    return this.listaUtenti;
                },
                //
/** restituisce la lista degli amici pendenti contenuti nel Registro */
                getListaPendenti: function() {
                    return this.listaPendenti;
                },
                //
/** aggiunge Utente alla lista degli amici e lancia un evento per informare
dell'accaduto */
                addUtente: function(utente) { // aggiunge un Utente già esistente nel vettore listaUtenti avente come chiave l'email dell'utente stesso (univoca)
                    this.listaUtenti[utente.email] = utente;

                    this.fireEvent('aggUtenteModel', utente);
                },
                //
/** aggiunge un nuovo utente alla lista degli amici partendo dai dati che lo
descrivono (email, stato e nome)   %RIVEDERE STA COSA */
                setUtente: function(email, stato, nome) { // crea un utente con i parametri in input e lo aggiunge al registro
                    var utente = new PT.MODEL.Utente(email, stato, nome);
                    this.addUtente(utente);

                },
                //
/** rimuove un Utente, identificato dall'attributo univoco email, dalla lista degli
amici */
                rmUtente: function(email) { // rimuove un utente dalla listaUtenti
                    var tmp = this.listaUtenti[email];
                    this.fireEvent('rmUtenteModel', tmp);
                    delete this.listaUtenti[email];
                },
                //
/** aggiunge un nuovo Utente alla lista degli amici pendenti e lancia un evento
interno per informare dell'accaduto */
                addPendente: function(pendente) { // aggiunge un Utente nel vettore listaPendenti avente come chiave l'email dell'utente stesso (univoca)
                    this.listaPendenti[pendente.email] = pendente;

                    this.fireEvent('aggPendenteModel', pendente);
                },
                //
/** rimuove un Utente, identificato dall'attributo univoco email, dalla lista degli
amici pendenti e lancia un evento interno per informare dell'accaduto */
                rmPendente: function(email) { // rimuove un utente dalla listaPendenti
                    var tmp = this.listaPendenti[email];
                    if (tmp) {
                        delete this.listaPendenti[email];
                        this.fireEvent('rmPendenteModel', tmp);
                    }
                },
                //
/** modifica l'username associato all'utente identificato dall'attributo email e
lancia un evento interno per informare dell'accaduto */
                modificaNomeUtente: function(nome, email) {
                    this.listaUtenti[email].nome = nome;
                    this.fireEvent('modificaNomeUtenteModel', this.listaUtenti[email]);
                },
                //
/** modifica lo stato associato all'utente identificato dall'attributo email e
lancia un evento interno per informare dell'accaduto  */
                modificaStatoUtente: function(stato, email) {
                    this.listaUtenti[email].stato = stato;
                    this.fireEvent('modificaStatoUtenteModel', this.listaUtenti[email]);
                }
            });
            instance = new Registro();
        }

        return instance;
    };
}();
