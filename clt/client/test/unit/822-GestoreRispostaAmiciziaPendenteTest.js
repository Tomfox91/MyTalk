describe("RispostaAmiciziaPendente", function(){

   
    it("test", function() {
        // PREPARAZIONE
        //PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
        var orig_dispatcher = PT.CONTROLLER.getInstanceDispatcher;
        
        var t,e,a;
        
        var socket = new Class ({
            
            invia : function(ex) { 
                t = ex.tipo;
                e = ex.evento.email;
                a = ex.evento.accettata;
            }
        
        });    
            
            
            
        var dispatcher = new Class ({
            
            getSocket : function() { 
                return new socket();
            }
      
        });

        PT.CONTROLLER.getInstanceDispatcher = function() {
            return new dispatcher();
        };
            
        // FINE PREPARAZIONE       
        
        var o = {
                email : "Provetta@provamail.com",
                esito : true
                };
        new PT.CONTROLLER.G.RispostaAmiciziaPendente().esegui(o);
        expect(t).toBe("AccettazioneAmicizia");
        expect(e).toBe("Provetta@provamail.com");
        expect(a).toBe(true);
    
        // PULIZIA
        
        PT.CONTROLLER.getInstanceDispatcher = orig_dispatcher;
        delete orig_dispatcher;
        delete dispatcher;
        delete socket;
        delete t;
        delete e;
        delete a;
            
        // FINE PULIZIA
    });
    
        
});

