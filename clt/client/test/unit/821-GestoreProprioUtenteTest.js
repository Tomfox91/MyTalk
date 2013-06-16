describe("GestoreProprioUtente", function(){

    it("test", function() {
        //INIZIO PREPARAZIONE
        var orig_getInstanceRegistro = PT.MODEL.getInstanceRegistro;
        var registro = jasmine.createSpyObj('registro', ['setProprioUtente']);
               
        PT.MODEL.getInstanceRegistro = function() {
            return registro;
        };

        var orig_ga = PT.VIEW.GestoreAccount;
        var gestoreAccount = jasmine.createSpyObj('gestoreAccount', ['effettuaAccesso']);
        PT.VIEW.GestoreAccount = function () {
                return gestoreAccount;
        }
        
        var orig_Utente = PT.MODEL.Utente;
        var e, s, n;
        
        PT.MODEL.Utente = function(email, stato, nome) {
        	e = email;
        	s = stato;
        	n = nome;
        	
			return {q:42};
		}
        
        
        //FINE PREPARAZIONE
                        
        new PT.CONTROLLER.G.GestoreProprioUtente().esegui({
            email: 'ciao@prova.it',
            username: 'pippo',
            stato: 'offline'
        });
        
        expect(e).toBe('ciao@prova.it');
        expect(n).toBe('pippo');
        expect(s).toBe('offline');
        expect(registro.setProprioUtente).toHaveBeenCalledWith({q:42});
        expect(gestoreAccount.effettuaAccesso).toHaveBeenCalled();
         
        //PULIZIA
         
        PT.MODEL.getInstanceRegistro = orig_getInstanceRegistro;
        delete registro;
        
        PT.MODEL.Utente = orig_Utente;
        delete orig_Utente;
        
        PT.VIEW.GestoreAccount = orig_ga;
        delete orig_ga;
        delete updater;
               
    });
        
});


