describe("GestoreNuovaRichiestaAmicizia", function() {

    var orig_getInstanceRegistro = PT.MODEL.getInstanceRegistro;

    var registro = jasmine.createSpyObj('registro', ['addPendente']);

    beforeEach(function() {
        PT.MODEL.getInstanceRegistro = function() {
            return registro;
        };
    });

    it("test", function() {        
        
        var orig_Utente = PT.MODEL.Utente;
        var e, s, n;
        
        PT.MODEL.Utente = function(email, stato, nome) {
        	e = email;
        	n = nome;
        	s = stato;
			return {q:42};
		}
                        
        new PT.CONTROLLER.G.GestoreNuovaRichiestaAmicizia().esegui({
            email: 'ciao@prova.it',
            username: 'pippo',
            stato: 'offline'
        });
        
        expect(e).toBe('ciao@prova.it');
        expect(n).toBe('pippo');
        expect(registro.addPendente).toHaveBeenCalledWith({q:42});
         
        PT.MODEL.getInstanceRegistro = orig_getInstanceRegistro;
        delete registro;
        
        PT.MODEL.Utente = orig_Utente;
        delete orig_Utente;
    });

});


