describe("GestoreOperazione", function() {
    
    it("test", function() {
        var orig_getInstanceUpdater = PT.VIEW.getInstanceUpdater;
        var updater = jasmine.createSpyObj('updater', ['notificaEsito']);
        
        PT.VIEW.getInstanceUpdater = function () {
            return updater;
        };
        
        var go = new PT.CONTROLLER.G.GestoreOperazione();
        
        var evento = {
            originale : "Registrazione",
            riuscita : 1
        };
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Registrazione","Registrazione completata correttamente");
        
        evento.riuscita = 0;
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Registrazione","Registrazione non riuscita. L'email è già presente nel database o i dati inseriti non sono corretti");
        
        evento.originale = "RecuperaPassword";
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Recupero Password","Recupero password non riuscito. Controllare i dati inseriti");
        
        evento.riuscita = 1;
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Recupero Password","Password recuperata con successo");
        
        evento.originale = "NuovaAmicizia";
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Richiesta Nuova Amicizia","Richiesta di amicizia inviata correttamente");
        
        evento.riuscita = 0;
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Richiesta Nuova Amicizia","Impossibile inviare la richiesta di amicizia. Si prega di riprovare");
        
        evento.originale = "ModificaPassword";
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Modifica Password","Cambio password non riuscito. Controllare i dati inseriti");
        
        evento.riuscita = 1;
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Modifica Password","Password cambiata con successo");
        
        evento.originale = "MessaggioSegreteria";
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Messaggio Segreteria","Messaggio di segreteria inviato correttamente");
     
        evento.riuscita = 0;
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Messaggio Segreteria","Invio del messaggio di segreteria non riuscito. Si prega di riprovare");
        
        evento.originale = "ModificaProfilo";
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Modifica Profilo","Modifica profilo non riuscita. Si prega di riprovare");
        
        evento.riuscita = 1;
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Modifica Profilo","Profilo aggiornato correttamente");
       
        evento.originale = "RichiestaDomandaSicurezza";
        evento.riuscita = 0;
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Recupero Password","Richiesta di recupero password fallita, l'email inserita non ha un account associato");

        evento.originale = "Login";
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Login","Login fallito");
        
        evento.originale = "Segnale";
        go.esegui(evento);
        expect(updater.notificaEsito).toHaveBeenCalledWith("Errore di comunicazione","Si è verificato un errore di comunicazione");

      
        PT.VIEW.getInstanceUpdater = orig_getInstanceUpdater;
        delete updater;
    });
        
});
